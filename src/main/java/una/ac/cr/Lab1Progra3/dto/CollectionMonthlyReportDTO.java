package una.ac.cr.Lab1Progra3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para reporte mensual de recolección por ruta y tipo de residuo")
public class CollectionMonthlyReportDTO {
    
    @Schema(description = "Nombre de la ruta", example = "Ruta 1")
    private String routeName;
    
    @Schema(description = "Tipo de residuo", example = "Orgánico")
    private String wasteType;
    
    @Schema(description = "Mes", example = "1")
    private Short month;
    
    @Schema(description = "Año", example = "2027")
    private Short year;
    
    @Schema(description = "Total de toneladas recolectadas", example = "140.06")
    private BigDecimal totalTons;
}
