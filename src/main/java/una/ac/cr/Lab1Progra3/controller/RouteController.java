package una.ac.cr.Lab1Progra3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import una.ac.cr.Lab1Progra3.dto.RouteDTO;
import una.ac.cr.Lab1Progra3.service.RouteService;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
@Tag(name = "Rutas", description = "API para gestión de rutas de recolección")
public class RouteController {
    
    private final RouteService routeService;
    
    @GetMapping
    @Operation(summary = "Obtener todas las rutas", description = "Retorna una lista paginada de todas las rutas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de rutas obtenida exitosamente")
    })
    public ResponseEntity<Page<RouteDTO>> getAllRoutes(
            @Parameter(description = "Número de página (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenar", example = "name")
            @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Dirección de ordenamiento", example = "asc")
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RouteDTO> routes = routeService.findAll(pageable);
        return ResponseEntity.ok(routes);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener ruta por ID", description = "Retorna una ruta específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ruta encontrada"),
        @ApiResponse(responseCode = "404", description = "Ruta no encontrada")
    })
    public ResponseEntity<RouteDTO> getRouteById(
            @Parameter(description = "ID de la ruta", example = "1")
            @PathVariable Long id) {
        return routeService.findById(id)
                .map(route -> ResponseEntity.ok(route))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Crear nueva ruta", description = "Crea una nueva ruta de recolección")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ruta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<RouteDTO> createRoute(
            @Parameter(description = "Datos de la nueva ruta")
            @Valid @RequestBody RouteDTO routeDTO) {
        RouteDTO savedRoute = routeService.save(routeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoute);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar ruta", description = "Actualiza una ruta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ruta actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Ruta no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<RouteDTO> updateRoute(
            @Parameter(description = "ID de la ruta a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la ruta")
            @Valid @RequestBody RouteDTO routeDTO) {
        return routeService.update(id, routeDTO)
                .map(route -> ResponseEntity.ok(route))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ruta", description = "Elimina una ruta por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ruta eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Ruta no encontrada")
    })
    public ResponseEntity<Void> deleteRoute(
            @Parameter(description = "ID de la ruta a eliminar", example = "1")
            @PathVariable Long id) {
        if (routeService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/search")
    @Operation(summary = "Buscar rutas por nombre", description = "Busca rutas que contengan el texto especificado en el nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<Page<RouteDTO>> searchRoutesByName(
            @Parameter(description = "Texto a buscar en el nombre", example = "Ruta")
            @RequestParam String name,
            @Parameter(description = "Número de página (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<RouteDTO> routes = routeService.findByNameContaining(name, pageable);
        return ResponseEntity.ok(routes);
    }
}
