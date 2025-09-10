package una.ac.cr.Lab1Progra3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import una.ac.cr.Lab1Progra3.entity.CollectionRecord;

import java.util.List;

@Repository
public interface CollectionRecordRepository extends JpaRepository<CollectionRecord, Long> {
    
    @Query("SELECT c FROM CollectionRecord c WHERE c.route.id = :routeId")
    Page<CollectionRecord> findByRouteId(@Param("routeId") Long routeId, Pageable pageable);
    
    @Query("SELECT c FROM CollectionRecord c WHERE c.wasteCategory.id = :wasteCatId")
    Page<CollectionRecord> findByWasteCategoryId(@Param("wasteCatId") Long wasteCatId, Pageable pageable);
    
    @Query("SELECT c FROM CollectionRecord c WHERE c.month = :month AND c.year = :year")
    Page<CollectionRecord> findByMonthAndYear(@Param("month") Short month, @Param("year") Short year, Pageable pageable);
    
    @Query("SELECT c FROM CollectionRecord c WHERE c.route.id = :routeId AND c.wasteCategory.id = :wasteCatId AND c.month = :month AND c.year = :year")
    List<CollectionRecord> findByRouteIdAndWasteCategoryIdAndMonthAndYear(
        @Param("routeId") Long routeId, 
        @Param("wasteCatId") Long wasteCatId, 
        @Param("month") Short month, 
        @Param("year") Short year);
    
    @Query("SELECT c.route.name, c.wasteCategory.name, c.month, c.year, SUM(c.tons) " +
           "FROM CollectionRecord c " +
           "WHERE c.year = :year " +
           "GROUP BY c.route.name, c.wasteCategory.name, c.month, c.year " +
           "ORDER BY c.route.name, c.wasteCategory.name, c.month")
    List<Object[]> getCollectionSummaryByRouteAndWasteType(@Param("year") Short year);
    
    @Query("SELECT c.route.name, c.month, SUM(c.tons) " +
           "FROM CollectionRecord c " +
           "WHERE c.wasteCategory.name = :wasteTypeName AND c.year = :year " +
           "GROUP BY c.route.name, c.month " +
           "ORDER BY c.route.name, c.month")
    List<Object[]> getCollectionByWasteTypeAndYear(@Param("wasteTypeName") String wasteTypeName, @Param("year") Short year);
}
