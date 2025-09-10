package una.ac.cr.Lab1Progra3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "route_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCategory {
    @EmbeddedId
    private RouteCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("routeId")
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("wasteCatId")
    @JoinColumn(name = "waste_cat_id")
    private WasteCategory wasteCategory;
}
