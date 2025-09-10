package una.ac.cr.Lab1Progra3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import una.ac.cr.Lab1Progra3.entity.CollectionRun;

import java.time.LocalDate;

@Repository
public interface CollectionRunRepository extends JpaRepository<CollectionRun, Long> {
    
    @Query("SELECT c FROM CollectionRun c WHERE c.route.id = :routeId")
    Page<CollectionRun> findByRouteId(@Param("routeId") Long routeId, Pageable pageable);
    
    @Query("SELECT c FROM CollectionRun c WHERE c.vehicle.id = :vehicleId")
    Page<CollectionRun> findByVehicleId(@Param("vehicleId") Long vehicleId, Pageable pageable);
    
    @Query("SELECT c FROM CollectionRun c WHERE c.date BETWEEN :startDate AND :endDate")
    Page<CollectionRun> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
    
    @Query("SELECT c FROM CollectionRun c WHERE c.month = :month AND c.year = :year")
    Page<CollectionRun> findByMonthAndYear(@Param("month") Short month, @Param("year") Short year, Pageable pageable);
}