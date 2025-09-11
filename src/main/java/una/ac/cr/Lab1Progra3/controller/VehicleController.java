package una.ac.cr.Lab1Progra3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.Lab1Progra3.entity.Vehicle;
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
