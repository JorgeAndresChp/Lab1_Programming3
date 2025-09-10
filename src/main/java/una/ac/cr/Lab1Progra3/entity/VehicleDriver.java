package una.ac.cr.Lab1Progra3.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehicle_drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDriver {
    @EmbeddedId
    private VehicleDriverId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vehicleId")
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("driverId")
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private LocalDate endDate;
}
