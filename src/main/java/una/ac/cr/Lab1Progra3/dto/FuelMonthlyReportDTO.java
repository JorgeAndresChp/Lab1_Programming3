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
@Schema(description = "DTO para reporte mensual de combustible por vehículo")
public class FuelMonthlyReportDTO {
    
    @Schema(description = "Placa del vehículo", example = "SM-1234")
    private String vehiclePlate;
    
    @Schema(description = "Mes", example = "1")
    private Short month;
    
    @Schema(description = "Año", example = "2027")
    private Short year;
    
    @Schema(description = "Total de litros consumidos", example = "1309.68")
    private BigDecimal totalLiters;
    
    @Schema(description = "Costo total", example = "75000.00")
    private BigDecimal totalCost;
    
    @Schema(description = "Total de kilómetros", example = "2652.50")
    private BigDecimal totalKilometers;
}