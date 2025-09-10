package una.ac.cr.Lab1Progra3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import una.ac.cr.Lab1Progra3.entity.Route;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    
    Optional<Route> findByName(String name);
    
    @Query("SELECT r FROM Route r WHERE r.name LIKE %:name%")
    Page<Route> findByNameContaining(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT r FROM Route r WHERE r.observation LIKE %:observation%")
    Page<Route> findByObservationContaining(@Param("observation") String observation, Pageable pageable);
}
