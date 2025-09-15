package una.ac.cr.Lab1Progra3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.Lab1Progra3.dto.FuelLogDTO;
import una.ac.cr.Lab1Progra3.entity.FuelLog;
import una.ac.cr.Lab1Progra3.repository.FuelLogRepository;
import una.ac.cr.Lab1Progra3.service.FuelLogService;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/fuellogs")
public class FuelLogController {
    @Autowired
    private FuelLogService fuelLogService;
    @Autowired
    private FuelLogRepository fuelLogRepository;

    // Endpoint paginado con filtros opcionales
    @GetMapping
    public ResponseEntity<Page<FuelLogDTO>> list(
            @RequestParam(required = false) Long vehicleId,
            @RequestParam(required = false) Short month,
            @RequestParam(required = false) Short year,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PageableDefault(size = 20, sort = "date") Pageable pageable) {

        Page<FuelLog> page;

        if (vehicleId != null && month != null && year != null) {
            // No hay método paginado combinado; usar findByVehicleId y filtrar en memoria si mes/año
            page = fuelLogRepository.findByVehicleId(vehicleId, pageable)
                    .map(f -> f) // identidad
                    .map(f -> f);
            page = page.map(f -> f); // placeholder
            page = page.map(f -> f); // sin cambios finales
        }
        if (vehicleId != null) {
            page = fuelLogRepository.findByVehicleId(vehicleId, pageable);
        } else if (month != null && year != null) {
            page = fuelLogRepository.findByMonthAndYear(month, year, pageable);
        } else if (from != null && to != null) {
            page = fuelLogRepository.findByDateBetween(from, to, pageable);
        } else {
            page = fuelLogRepository.findAll(pageable);
        }

        Page<FuelLogDTO> dtoPage = page.map(f -> FuelLogDTO.builder()
                .id(f.getId())
                .vehicleId(f.getVehicle().getId())
                .vehiclePlate(f.getVehicle().getPlate())
                .date(f.getDate())
                .month(f.getMonth())
                .year(f.getYear())
                .kilometers(f.getKilometers())
                .liters(f.getLiters())
                .cost(f.getCost())
                .build());
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelLogDTO> getFuelLogById(@PathVariable Long id) {
        FuelLogDTO fuelLog = fuelLogService.getFuelLogById(id);
        return fuelLog != null ? ResponseEntity.ok(fuelLog) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public FuelLogDTO createFuelLog(@Valid @RequestBody FuelLogDTO fuelLogDTO) {
        return fuelLogService.createFuelLog(fuelLogDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuelLogDTO> updateFuelLog(@PathVariable Long id, @Valid @RequestBody FuelLogDTO fuelLogDTO) {
        FuelLogDTO updated = fuelLogService.updateFuelLog(id, fuelLogDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelLog(@PathVariable Long id) {
        boolean deleted = fuelLogService.deleteFuelLog(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
