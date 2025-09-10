package una.ac.cr.Lab1Progra3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para crear/actualizar registros de combustible")
public class FuelLogDTO {
    
    @Schema(description = "ID del registro", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @NotNull(message = "El ID del vehículo es obligatorio")
    @Schema(description = "ID del vehículo", example = "1", required = true)
    private Long vehicleId;
    
    @Schema(description = "Placa del vehículo", accessMode = Schema.AccessMode.READ_ONLY)
    private String vehiclePlate;
    
    @Schema(description = "Fecha del registro", example = "2027-01-15")
    private LocalDate date;
    
    @Min(value = 1, message = "El mes debe estar entre 1 y 12")
    @Max(value = 12, message = "El mes debe estar entre 1 y 12")
    @Schema(description = "Mes del registro", example = "1")
    private Short month;
    
    @Min(value = 2020, message = "El año debe ser mayor a 2020")
    @Schema(description = "Año del registro", example = "2027")
    private Short year;
    
    @DecimalMin(value = "0.0", message = "Los kilómetros deben ser mayor o igual a 0")
    @Schema(description = "Kilómetros recorridos", example = "226.50")
    private BigDecimal kilometers;
    
    @DecimalMin(value = "0.0", message = "Los litros deben ser mayor o igual a 0")
    @Schema(description = "Litros de combustible", example = "67.50")
    private BigDecimal liters;
    
    @DecimalMin(value = "0.0", message = "El costo debe ser mayor o igual a 0")
    @Schema(description = "Costo del combustible", example = "42500.00")
    private BigDecimal cost;
}
