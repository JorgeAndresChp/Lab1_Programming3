package una.ac.cr.Lab1Progra3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para crear/actualizar rutas")
public class RouteDTO {
    
    @Schema(description = "ID de la ruta", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @NotBlank(message = "El nombre de la ruta es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    @Schema(description = "Nombre de la ruta", example = "Ruta 1", required = true)
    private String name;
    
    @Size(max = 500, message = "La observación no puede exceder 500 caracteres")
    @Schema(description = "Observaciones sobre la ruta", example = "Ruta residencial con alta recolección orgánica")
    private String observation;
}
