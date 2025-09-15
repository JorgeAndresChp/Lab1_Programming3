package una.ac.cr.Lab1Progra3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.Lab1Progra3.dto.DriverDTO;
import una.ac.cr.Lab1Progra3.entity.Driver;
import una.ac.cr.Lab1Progra3.repository.DriverRepository;
import una.ac.cr.Lab1Progra3.service.DriverService;



@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepository driverRepository;

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriverById(@PathVariable Long id) {
        DriverDTO driver = driverService.getDriverById(id);
        return driver != null ? ResponseEntity.ok(driver) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) {
        return driverService.createDriver(driverDTO);
    }

    // Endpoint paginado con filtro opcional por nombre
    @GetMapping
    public ResponseEntity<Page<DriverDTO>> list(
        @RequestParam(required = false) String name,
        @PageableDefault(size = 20, sort = "id") Pageable pageable) {

    Page<Driver> page = (name != null && !name.isBlank())
        ? driverRepository.findByNameContainingIgnoreCase(name, pageable)
        : driverRepository.findAll(pageable);

    Page<DriverDTO> dtoPage = page.map(d ->
        DriverDTO.builder()
            .id(d.getId())
            .name(d.getName())
            .build()
    );
    return ResponseEntity.ok(dtoPage);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> updateDriver(@PathVariable Long id, @Valid @RequestBody DriverDTO driverDTO) {
        DriverDTO updated = driverService.updateDriver(id, driverDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        boolean deleted = driverService.deleteDriver(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
