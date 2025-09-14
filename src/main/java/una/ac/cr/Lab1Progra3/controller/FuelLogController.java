package una.ac.cr.Lab1Progra3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.Lab1Progra3.dto.FuelLogDTO;
import una.ac.cr.Lab1Progra3.service.FuelLogService;
import java.util.List;

@RestController
@RequestMapping("/api/fuellogs")
public class FuelLogController {
    @Autowired
    private FuelLogService fuelLogService;

    @GetMapping
    public List<FuelLogDTO> getAllFuelLogs() {
        return fuelLogService.getAllFuelLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelLogDTO> getFuelLogById(@PathVariable Long id) {
        FuelLogDTO fuelLog = fuelLogService.getFuelLogById(id);
        return fuelLog != null ? ResponseEntity.ok(fuelLog) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public FuelLogDTO createFuelLog(@RequestBody FuelLogDTO fuelLogDTO) {
        return fuelLogService.createFuelLog(fuelLogDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuelLogDTO> updateFuelLog(@PathVariable Long id, @RequestBody FuelLogDTO fuelLogDTO) {
        FuelLogDTO updated = fuelLogService.updateFuelLog(id, fuelLogDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelLog(@PathVariable Long id) {
        boolean deleted = fuelLogService.deleteFuelLog(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
