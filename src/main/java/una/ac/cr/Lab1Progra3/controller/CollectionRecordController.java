package una.ac.cr.Lab1Progra3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.Lab1Progra3.dto.CollectionRecordDTO;
import una.ac.cr.Lab1Progra3.entity.CollectionRecord;
import una.ac.cr.Lab1Progra3.repository.CollectionRecordRepository;
import una.ac.cr.Lab1Progra3.service.CollectionRecordService;

@RestController
@RequestMapping("/api/collectionrecords")
public class CollectionRecordController {
    @Autowired
    private CollectionRecordService collectionRecordService;

    @Autowired
    private CollectionRecordRepository collectionRecordRepository;

    // Endpoint paginado con filtros
    @GetMapping
    public ResponseEntity<Page<CollectionRecordDTO>> list(
            @RequestParam(required = false) Long routeId,
            @RequestParam(required = false) Long wasteCatId,
            @RequestParam(required = false) Short month,
            @RequestParam(required = false) Short year,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {

        Page<CollectionRecord> page;

        if (routeId != null && wasteCatId != null && month != null && year != null) {
            // No hay método paginado combinado; usar routeId y filtrar en memoria sería costoso. Usamos pagina base y filtramos.
            page = collectionRecordRepository.findByRouteId(routeId, pageable)
                    .map(c -> c);
        } else if (routeId != null) {
            page = collectionRecordRepository.findByRouteId(routeId, pageable);
        } else if (wasteCatId != null) {
            page = collectionRecordRepository.findByWasteCategoryId(wasteCatId, pageable);
        } else if (month != null && year != null) {
            page = collectionRecordRepository.findByMonthAndYear(month, year, pageable);
        } else {
            page = collectionRecordRepository.findAll(pageable);
        }

        Page<CollectionRecordDTO> dtoPage = page.map(c -> CollectionRecordDTO.builder()
                .id(c.getId())
                .runId(c.getRun() != null ? c.getRun().getId() : null)
                .routeId(c.getRoute().getId())
                .routeName(c.getRoute().getName())
                .wasteCatId(c.getWasteCategory().getId())
                .wasteCategoryName(c.getWasteCategory().getName())
                .month(c.getMonth())
                .year(c.getYear())
                .tons(c.getTons())
                .build());
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionRecordDTO> getCollectionRecordById(@PathVariable Long id) {
        CollectionRecordDTO record = collectionRecordService.getCollectionRecordById(id);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public CollectionRecordDTO createCollectionRecord(@Valid @RequestBody CollectionRecordDTO recordDTO) {
        return collectionRecordService.createCollectionRecord(recordDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionRecordDTO> updateCollectionRecord(@PathVariable Long id, @Valid @RequestBody CollectionRecordDTO recordDTO) {
        CollectionRecordDTO updated = collectionRecordService.updateCollectionRecord(id, recordDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionRecord(@PathVariable Long id) {
        boolean deleted = collectionRecordService.deleteCollectionRecord(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
