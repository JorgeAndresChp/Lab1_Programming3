package una.ac.cr.Lab1Progra3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "collection_records",
        uniqueConstraints = @UniqueConstraint(name = "uk_record_route_waste_month_year", columnNames = {"route_id","waste_cat_id","month","year"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "run_id")
    private CollectionRun run;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_cat_id")
    private WasteCategory wasteCategory;

    @Column(nullable = false)
    private Short month;

    @Column(nullable = false)
    private Short year;

    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal tons;
}
