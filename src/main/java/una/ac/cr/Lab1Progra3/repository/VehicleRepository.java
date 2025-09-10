package una.ac.cr.Lab1Progra3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import una.ac.cr.Lab1Progra3.entity.Vehicle;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    Optional<Vehicle> findByPlate(String plate);
    
    @Query("SELECT v FROM Vehicle v WHERE v.plate LIKE %:plate%")
    Page<Vehicle> findByPlateContaining(@Param("plate") String plate, Pageable pageable);
    
    @Query("SELECT v FROM Vehicle v WHERE v.type = :type")
    Page<Vehicle> findByType(@Param("type") String type, Pageable pageable);
    
    @Query("SELECT v FROM Vehicle v WHERE v.year = :year")
    Page<Vehicle> findByYear(@Param("year") Short year, Pageable pageable);
    
    @Query("SELECT v FROM Vehicle v WHERE v.capacityKg >= :minCapacity")
    Page<Vehicle> findByCapacityKgGreaterThanEqual(@Param("minCapacity") Integer minCapacity, Pageable pageable);
}
