package una.ac.cr.Lab1Progra3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import una.ac.cr.Lab1Progra3.dto.FuelLogDTO;
import una.ac.cr.Lab1Progra3.entity.FuelLog;
import una.ac.cr.Lab1Progra3.repository.FuelLogRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuelLogService {
    @Autowired
    private FuelLogRepository fuelLogRepository;

    public List<FuelLogDTO> getAllFuelLogs() {
        return fuelLogRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public FuelLogDTO getFuelLogById(Long id) {
        Optional<FuelLog> fuelLog = fuelLogRepository.findById(id);
        return fuelLog.map(this::toDTO).orElse(null);
    }

    public FuelLogDTO createFuelLog(FuelLogDTO dto) {
        FuelLog fuelLog = toEntity(dto);
        return toDTO(fuelLogRepository.save(fuelLog));
    }

    public FuelLogDTO updateFuelLog(Long id, FuelLogDTO dto) {
        Optional<FuelLog> optional = fuelLogRepository.findById(id);
        if (optional.isPresent()) {
            FuelLog fuelLog = optional.get();
            fuelLog.setDate(dto.getDate());
            fuelLog.setMonth(dto.getMonth());
            fuelLog.setYear(dto.getYear());
            fuelLog.setKilometers(dto.getKilometers());
            fuelLog.setLiters(dto.getLiters());
            fuelLog.setCost(dto.getCost());
            // Si hay relación con vehicle:
            // Aquí deberías buscar el Vehicle por ID si es necesario
            // Ejemplo: vehicleRepository.findById(dto.getVehicleId()).ifPresent(fuelLog::setVehicle);
            return toDTO(fuelLogRepository.save(fuelLog));
        }
        return null;
    }

    public boolean deleteFuelLog(Long id) {
        if (fuelLogRepository.existsById(id)) {
            fuelLogRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private FuelLogDTO toDTO(FuelLog fuelLog) {
        FuelLogDTO dto = new FuelLogDTO();
        dto.setId(fuelLog.getId());
        dto.setDate(fuelLog.getDate());
        dto.setMonth(fuelLog.getMonth());
        dto.setYear(fuelLog.getYear());
        dto.setKilometers(fuelLog.getKilometers());
        dto.setLiters(fuelLog.getLiters());
        dto.setCost(fuelLog.getCost());
        // Puedes mapear el ID y la placa si es necesario
        if (fuelLog.getVehicle() != null) {
            dto.setVehicleId(fuelLog.getVehicle().getId());
            dto.setVehiclePlate(fuelLog.getVehicle().getPlate());
        }
        return dto;
    }

    private FuelLog toEntity(FuelLogDTO dto) {
        FuelLog fuelLog = new FuelLog();
        fuelLog.setId(dto.getId());
        fuelLog.setDate(dto.getDate());
        fuelLog.setMonth(dto.getMonth());
        fuelLog.setYear(dto.getYear());
        fuelLog.setKilometers(dto.getKilometers());
        fuelLog.setLiters(dto.getLiters());
        fuelLog.setCost(dto.getCost());
    // Aquí deberías buscar el Vehicle por ID si es necesario
    // Ejemplo: vehicleRepository.findById(dto.getVehicleId()).ifPresent(fuelLog::setVehicle);
        return fuelLog;
    }
}
