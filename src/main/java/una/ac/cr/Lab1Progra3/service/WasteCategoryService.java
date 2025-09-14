package una.ac.cr.Lab1Progra3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import una.ac.cr.Lab1Progra3.dto.WasteCategoryDTO;
import una.ac.cr.Lab1Progra3.entity.WasteCategory;
import una.ac.cr.Lab1Progra3.repository.WasteCategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WasteCategoryService {
    @Autowired
    private WasteCategoryRepository wasteCategoryRepository;

    public List<WasteCategoryDTO> getAllWasteCategories() {
        return wasteCategoryRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public WasteCategoryDTO getWasteCategoryById(Long id) {
        Optional<WasteCategory> category = wasteCategoryRepository.findById(id);
        return category.map(this::toDTO).orElse(null);
    }

    public WasteCategoryDTO createWasteCategory(WasteCategoryDTO dto) {
        WasteCategory category = toEntity(dto);
        return toDTO(wasteCategoryRepository.save(category));
    }

    public WasteCategoryDTO updateWasteCategory(Long id, WasteCategoryDTO dto) {
        Optional<WasteCategory> optional = wasteCategoryRepository.findById(id);
        if (optional.isPresent()) {
            WasteCategory category = optional.get();
            category.setName(dto.getName());
            return toDTO(wasteCategoryRepository.save(category));
        }
        return null;
    }

    public boolean deleteWasteCategory(Long id) {
        if (wasteCategoryRepository.existsById(id)) {
            wasteCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private WasteCategoryDTO toDTO(WasteCategory category) {
        WasteCategoryDTO dto = new WasteCategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName()); // Ajusta según atributos
        // ...otros setters
        return dto;
    }

    private WasteCategory toEntity(WasteCategoryDTO dto) {
        WasteCategory category = new WasteCategory();
        category.setId(dto.getId());
        category.setName(dto.getName()); // Ajusta según atributos
        // ...otros setters
        return category;
    }
}
