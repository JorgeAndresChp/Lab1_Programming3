package una.ac.cr.Lab1Progra3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "waste_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WasteCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
