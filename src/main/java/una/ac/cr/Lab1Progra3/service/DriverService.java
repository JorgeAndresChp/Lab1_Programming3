package una.ac.cr.Lab1Progra3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import una.ac.cr.Lab1Progra3.dto.DriverDTO;
import una.ac.cr.Lab1Progra3.entity.Driver;
import una.ac.cr.Lab1Progra3.repository.DriverRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public DriverDTO getDriverById(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        return driver.map(this::toDTO).orElse(null);
    }

    public DriverDTO createDriver(DriverDTO dto) {
        Driver driver = toEntity(dto);
        return toDTO(driverRepository.save(driver));
    }

    public DriverDTO updateDriver(Long id, DriverDTO dto) {
        Optional<Driver> optional = driverRepository.findById(id);
        if (optional.isPresent()) {
            Driver driver = optional.get();
            driver.setName(dto.getName());
            return toDTO(driverRepository.save(driver));
        }
        return null;
    }

    public boolean deleteDriver(Long id) {
        if (driverRepository.existsById(id)) {
            driverRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private DriverDTO toDTO(Driver driver) {
        DriverDTO dto = new DriverDTO();
        dto.setId(driver.getId());
        dto.setName(driver.getName()); // Ajusta según atributos
        // ...otros setters
        return dto;
    }

    private Driver toEntity(DriverDTO dto) {
        Driver driver = new Driver();
        driver.setId(dto.getId());
        driver.setName(dto.getName()); // Ajusta según atributos
        // ...otros setters
        return driver;
    }
}
