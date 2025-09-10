package una.ac.cr.Lab1Progra3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import una.ac.cr.Lab1Progra3.entity.WasteCategory;

import java.util.Optional;

@Repository
public interface WasteCategoryRepository extends JpaRepository<WasteCategory, Long> {
    
    Optional<WasteCategory> findByName(String name);
    
    @Query("SELECT w FROM WasteCategory w WHERE w.name LIKE %:name%")
    Page<WasteCategory> findByNameContaining(@Param("name") String name, Pageable pageable);
}
