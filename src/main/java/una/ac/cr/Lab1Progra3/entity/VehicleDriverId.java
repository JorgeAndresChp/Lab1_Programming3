package una.ac.cr.Lab1Progra3.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDriverId implements Serializable {
    private Long vehicleId;
    private Long driverId;
    private LocalDate startDate;
}
