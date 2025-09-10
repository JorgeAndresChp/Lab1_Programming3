package una.ac.cr.Lab1Progra3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import una.ac.cr.Lab1Progra3.entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    
    @Query("SELECT d FROM Driver d WHERE d.name LIKE %:name%")
    Page<Driver> findByNameContaining(@Param("name") String name, Pageable pageable);
}
