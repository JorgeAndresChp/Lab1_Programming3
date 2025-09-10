package una.ac.cr.Lab1Progra3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "run_drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunDriver {
    @EmbeddedId
    private RunDriverId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("runId")
    @JoinColumn(name = "run_id")
    private CollectionRun run;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("driverId")
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private String role;
}
