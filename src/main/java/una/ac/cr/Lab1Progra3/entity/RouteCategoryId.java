package una.ac.cr.Lab1Progra3.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCategoryId implements Serializable {
    private Long routeId;
    private Long wasteCatId;
}
