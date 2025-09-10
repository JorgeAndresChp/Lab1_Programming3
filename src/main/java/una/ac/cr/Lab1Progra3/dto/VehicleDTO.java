package una.ac.cr.Lab1Progra3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para crear/actualizar vehículos")
public class VehicleDTO {
    
    @Schema(description = "ID del vehículo", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @NotBlank(message = "La placa es obligatoria")
    @Size(min = 1, max = 20, message = "La placa debe tener entre 1 y 20 caracteres")
    @Schema(description = "Placa del vehículo", example = "SM-1234", required = true)
    private String plate;
    
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    @Schema(description = "Tipo de vehículo", example = "Camión")
    private String type;
    
    @Min(value = 0, message = "La capacidad debe ser mayor o igual a 0")
    @Schema(description = "Capacidad en kilogramos", example = "12500")
    private Integer capacityKg;
    
    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    @Max(value = 2030, message = "El año debe ser menor a 2030")
    @Schema(description = "Año del vehículo", example = "2020")
    private Short year;
    
    @Size(max = 500, message = "La observación no puede exceder 500 caracteres")
    @Schema(description = "Observaciones del vehículo", example = "Vehículo en buenas condiciones")
    private String observation;
}
