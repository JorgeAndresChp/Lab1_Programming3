package una.ac.cr.Lab1Progra3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.Lab1Progra3.dto.WasteCategoryDTO;
import una.ac.cr.Lab1Progra3.service.WasteCategoryService;
import java.util.List;

@RestController
@RequestMapping("/api/wastecategories")
public class WasteCategoryController {
    @Autowired
    private WasteCategoryService wasteCategoryService;

    @GetMapping
    public List<WasteCategoryDTO> getAllWasteCategories() {
        return wasteCategoryService.getAllWasteCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteCategoryDTO> getWasteCategoryById(@PathVariable Long id) {
        WasteCategoryDTO category = wasteCategoryService.getWasteCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public WasteCategoryDTO createWasteCategory(@RequestBody WasteCategoryDTO wasteCategoryDTO) {
        return wasteCategoryService.createWasteCategory(wasteCategoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WasteCategoryDTO> updateWasteCategory(@PathVariable Long id, @RequestBody WasteCategoryDTO wasteCategoryDTO) {
        WasteCategoryDTO updated = wasteCategoryService.updateWasteCategory(id, wasteCategoryDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWasteCategory(@PathVariable Long id) {
        boolean deleted = wasteCategoryService.deleteWasteCategory(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
