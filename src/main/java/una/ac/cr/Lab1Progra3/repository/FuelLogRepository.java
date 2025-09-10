package una.ac.cr.Lab1Progra3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import una.ac.cr.Lab1Progra3.entity.FuelLog;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuelLogRepository extends JpaRepository<FuelLog, Long> {
    
    @Query("SELECT f FROM FuelLog f WHERE f.vehicle.id = :vehicleId")
    Page<FuelLog> findByVehicleId(@Param("vehicleId") Long vehicleId, Pageable pageable);
    
    @Query("SELECT f FROM FuelLog f WHERE f.month = :month AND f.year = :year")
    Page<FuelLog> findByMonthAndYear(@Param("month") Short month, @Param("year") Short year, Pageable pageable);
    
    @Query("SELECT f FROM FuelLog f WHERE f.vehicle.id = :vehicleId AND f.month = :month AND f.year = :year")
    List<FuelLog> findByVehicleIdAndMonthAndYear(@Param("vehicleId") Long vehicleId, @Param("month") Short month, @Param("year") Short year);
    
    @Query("SELECT f FROM FuelLog f WHERE f.date BETWEEN :startDate AND :endDate")
    Page<FuelLog> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
    
    @Query("SELECT f.vehicle.plate, f.month, f.year, SUM(f.liters), SUM(f.cost), SUM(f.kilometers) " +
           "FROM FuelLog f " +
           "WHERE f.year = :year " +
           "GROUP BY f.vehicle.plate, f.month, f.year " +
           "ORDER BY f.vehicle.plate, f.month")
    List<Object[]> getFuelSummaryByVehicleAndMonth(@Param("year") Short year);
}
