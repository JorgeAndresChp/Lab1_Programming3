package una.ac.cr.Lab1Progra3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.Lab1Progra3.dto.CollectionRecordDTO;
import una.ac.cr.Lab1Progra3.service.CollectionRecordService;
import java.util.List;

@RestController
@RequestMapping("/api/collectionrecords")
public class CollectionRecordController {
    @Autowired
    private CollectionRecordService collectionRecordService;

    @GetMapping
    public List<CollectionRecordDTO> getAllCollectionRecords() {
        return collectionRecordService.getAllCollectionRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionRecordDTO> getCollectionRecordById(@PathVariable Long id) {
        CollectionRecordDTO record = collectionRecordService.getCollectionRecordById(id);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public CollectionRecordDTO createCollectionRecord(@RequestBody CollectionRecordDTO recordDTO) {
        return collectionRecordService.createCollectionRecord(recordDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionRecordDTO> updateCollectionRecord(@PathVariable Long id, @RequestBody CollectionRecordDTO recordDTO) {
        CollectionRecordDTO updated = collectionRecordService.updateCollectionRecord(id, recordDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionRecord(@PathVariable Long id) {
        boolean deleted = collectionRecordService.deleteCollectionRecord(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
