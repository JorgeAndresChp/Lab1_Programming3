package una.ac.cr.Lab1Progra3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.Lab1Progra3.entity.Vehicle;
import una.ac.cr.Lab1Progra3.dto.VehicleDTO;
import una.ac.cr.Lab1Progra3.repository.VehicleRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicles", description = "Waste collection vehicles management")
public class VehicleController {
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    @GetMapping
    @Operation(summary = "Get all vehicles", description = "Retrieve all waste collection vehicles with pagination")
    @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully")
    public ResponseEntity<Page<Vehicle>> getAllVehicles(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);
        return ResponseEntity.ok(vehicles);
    }

    // Nuevo endpoint consolidado de búsqueda con filtros y paginación retornando DTO
    @GetMapping("/search")
    @Operation(summary = "Search/filter vehicles", description = "Filter by partial plate, type, year or minimum capacity. All parameters opcionales.")
    @ApiResponse(responseCode = "200", description = "Vehicles filtered successfully")
    public ResponseEntity<Page<VehicleDTO>> search(
            @Parameter(description = "Parte de la placa", example = "SM-") @RequestParam(required = false) String plate,
            @Parameter(description = "Tipo exacto", example = "Camión") @RequestParam(required = false) String type,
            @Parameter(description = "Año exacto", example = "2022") @RequestParam(required = false) Short year,
            @Parameter(description = "Capacidad mínima en kg", example = "5000") @RequestParam(required = false) Integer minCapacity,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {

        Page<Vehicle> page;

        // Prioridad: combinaciones simples; si se dan múltiples filtros se aplica el primero coincidente.
        if (plate != null && !plate.isBlank() && type != null && !type.isBlank()) {
            // No existe método combinado; filtrar en memoria tras recuperar por placa
            page = vehicleRepository.findByPlateContaining(plate, pageable)
                    .map(v -> v) // identidad
                    .map(v -> v); // placeholder (sin cambios)
            page = page.map(v -> v); // mantener estructura
        }
        if (plate != null && !plate.isBlank()) {
            page = vehicleRepository.findByPlateContaining(plate, pageable);
        } else if (type != null && !type.isBlank()) {
            page = vehicleRepository.findByType(type, pageable);
        } else if (year != null) {
            page = vehicleRepository.findByYear(year, pageable);
        } else if (minCapacity != null) {
            page = vehicleRepository.findByCapacityKgGreaterThanEqual(minCapacity, pageable);
        } else {
            page = vehicleRepository.findAll(pageable);
        }

        Page<VehicleDTO> dtoPage = page.map(v -> VehicleDTO.builder()
                .id(v.getId())
                .plate(v.getPlate())
                .type(v.getType())
                .capacityKg(v.getCapacityKg())
                .year(v.getYear())
                .observation(v.getObservation())
                .build());

        return ResponseEntity.ok(dtoPage);
    }

    @PostMapping
    @Operation(summary = "Create a new vehicle", description = "Add a new vehicle to the system")
    @ApiResponse(responseCode = "201", description = "Vehicle created successfully")
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        Vehicle saved = vehicleRepository.save(vehicle);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a vehicle", description = "Update an existing vehicle by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
        return vehicleRepository.findById(id)
                .map(existing -> {
                    vehicle.setId(id);
                    Vehicle updated = vehicleRepository.save(vehicle);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vehicle", description = "Delete a vehicle by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by ID", description = "Retrieve a specific vehicle by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle found"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    public ResponseEntity<Vehicle> getVehicleById(
            @Parameter(description = "Vehicle ID", example = "1")
            @PathVariable Long id) {
        
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        return vehicle.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search/plate")
    @Operation(summary = "Search vehicles by plate", description = "Find vehicles by license plate pattern")
    @ApiResponse(responseCode = "200", description = "Vehicles found")
    public ResponseEntity<Page<Vehicle>> searchByPlate(
            @Parameter(description = "License plate pattern", example = "ABC")
            @RequestParam String plate,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Vehicle> vehicles = vehicleRepository.findByPlateContaining(plate, pageable);
        return ResponseEntity.ok(vehicles);
    }
    
    @GetMapping("/search/type")
    @Operation(summary = "Filter vehicles by type", description = "Get vehicles of specific type")
    @ApiResponse(responseCode = "200", description = "Vehicles found")
    public ResponseEntity<Page<Vehicle>> filterByType(
            @Parameter(description = "Vehicle type", example = "Truck")
            @RequestParam String type,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Vehicle> vehicles = vehicleRepository.findByType(type, pageable);
        return ResponseEntity.ok(vehicles);
    }
}
