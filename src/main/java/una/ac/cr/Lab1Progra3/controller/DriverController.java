package una.ac.cr.Lab1Progra3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.Lab1Progra3.dto.DriverDTO;
import una.ac.cr.Lab1Progra3.service.DriverService;
import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping
    public List<DriverDTO> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriverById(@PathVariable Long id) {
        DriverDTO driver = driverService.getDriverById(id);
        return driver != null ? ResponseEntity.ok(driver) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public DriverDTO createDriver(@RequestBody DriverDTO driverDTO) {
        return driverService.createDriver(driverDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> updateDriver(@PathVariable Long id, @RequestBody DriverDTO driverDTO) {
        DriverDTO updated = driverService.updateDriver(id, driverDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        boolean deleted = driverService.deleteDriver(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
