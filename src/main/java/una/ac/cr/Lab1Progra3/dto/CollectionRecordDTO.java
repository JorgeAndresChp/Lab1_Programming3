package una.ac.cr.Lab1Progra3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para crear/actualizar registros de recolección")
public class CollectionRecordDTO {
    
    @Schema(description = "ID del registro", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @Schema(description = "ID del viaje de recolección", example = "1")
    private Long runId;
    
    @NotNull(message = "El ID de la ruta es obligatorio")
    @Schema(description = "ID de la ruta", example = "1", required = true)
    private Long routeId;
    
    @Schema(description = "Nombre de la ruta", accessMode = Schema.AccessMode.READ_ONLY)
    private String routeName;
    
    @NotNull(message = "El ID de la categoría de residuo es obligatorio")
    @Schema(description = "ID de la categoría de residuo", example = "1", required = true)
    private Long wasteCatId;
    
    @Schema(description = "Nombre de la categoría de residuo", accessMode = Schema.AccessMode.READ_ONLY)
    private String wasteCategoryName;
    
    @NotNull(message = "El mes es obligatorio")
    @Min(value = 1, message = "El mes debe estar entre 1 y 12")
    @Max(value = 12, message = "El mes debe estar entre 1 y 12")
    @Schema(description = "Mes de recolección", example = "1", required = true)
    private Short month;
    
    @NotNull(message = "El año es obligatorio")
    @Min(value = 2020, message = "El año debe ser mayor a 2020")
    @Schema(description = "Año de recolección", example = "2027", required = true)
    private Short year;
    
    @NotNull(message = "Las toneladas son obligatorias")
    @DecimalMin(value = "0.0", message = "Las toneladas deben ser mayor o igual a 0")
    @Schema(description = "Toneladas recolectadas", example = "140.06", required = true)
    private BigDecimal tons;
}
