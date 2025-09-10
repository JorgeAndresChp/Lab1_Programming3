package una.ac.cr.Lab1Progra3.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunDriverId implements Serializable {
    private Long runId;
    private Long driverId;
}
