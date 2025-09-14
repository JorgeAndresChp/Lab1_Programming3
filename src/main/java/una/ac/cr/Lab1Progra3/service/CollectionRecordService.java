package una.ac.cr.Lab1Progra3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import una.ac.cr.Lab1Progra3.dto.CollectionRecordDTO;
import una.ac.cr.Lab1Progra3.entity.CollectionRecord;
import una.ac.cr.Lab1Progra3.repository.CollectionRecordRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectionRecordService {
    @Autowired
    private CollectionRecordRepository collectionRecordRepository;

    public List<CollectionRecordDTO> getAllCollectionRecords() {
        return collectionRecordRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CollectionRecordDTO getCollectionRecordById(Long id) {
        Optional<CollectionRecord> record = collectionRecordRepository.findById(id);
        return record.map(this::toDTO).orElse(null);
    }

    public CollectionRecordDTO createCollectionRecord(CollectionRecordDTO dto) {
        CollectionRecord record = toEntity(dto);
        return toDTO(collectionRecordRepository.save(record));
    }

    public CollectionRecordDTO updateCollectionRecord(Long id, CollectionRecordDTO dto) {
        Optional<CollectionRecord> optional = collectionRecordRepository.findById(id);
        if (optional.isPresent()) {
            CollectionRecord record = optional.get();
            record.setMonth(dto.getMonth());
            record.setYear(dto.getYear());
            record.setTons(dto.getTons());
            // Si hay relación con route, wasteCategory, run:
            // record.setRoute(...), record.setWasteCategory(...), record.setRun(...)
            return toDTO(collectionRecordRepository.save(record));
        }
        return null;
    }

    public boolean deleteCollectionRecord(Long id) {
        if (collectionRecordRepository.existsById(id)) {
            collectionRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private CollectionRecordDTO toDTO(CollectionRecord record) {
        CollectionRecordDTO dto = new CollectionRecordDTO();
        dto.setId(record.getId());
        dto.setMonth(record.getMonth());
        dto.setYear(record.getYear());
        dto.setTons(record.getTons());
        if (record.getRoute() != null) {
            dto.setRouteId(record.getRoute().getId());
            dto.setRouteName(record.getRoute().getName());
        }
        if (record.getWasteCategory() != null) {
            dto.setWasteCatId(record.getWasteCategory().getId());
            dto.setWasteCategoryName(record.getWasteCategory().getName());
        }
        if (record.getRun() != null) {
            dto.setRunId(record.getRun().getId());
        }
        return dto;
    }

    private CollectionRecord toEntity(CollectionRecordDTO dto) {
        CollectionRecord record = new CollectionRecord();
        record.setId(dto.getId());
        record.setMonth(dto.getMonth());
        record.setYear(dto.getYear());
        record.setTons(dto.getTons());
        // Si hay relación con route, wasteCategory, run:
        // record.setRoute(...), record.setWasteCategory(...), record.setRun(...)
        return record;
    }
}
