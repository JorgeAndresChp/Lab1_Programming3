package una.ac.cr.Lab1Progra3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
public class RouteController {
    
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllRoutes() {
        List<Map<String, Object>> routes = new ArrayList<>();
        
        Map<String, Object> route1 = new HashMap<>();
        route1.put("id", 1L);
        route1.put("name", "Ruta Centro");
        route1.put("observation", "Ruta del centro de la ciudad");
        routes.add(route1);
        
        Map<String, Object> route2 = new HashMap<>();
        route2.put("id", 2L);
        route2.put("name", "Ruta Norte");
        route2.put("observation", "Ruta de la zona norte");
        routes.add(route2);
        
        return ResponseEntity.ok(routes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRouteById(@PathVariable Long id) {
        Map<String, Object> route = new HashMap<>();
        route.put("id", id);
        route.put("name", "Ruta " + id);
        route.put("observation", "Descripción de la ruta " + id);
        return ResponseEntity.ok(route);
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> createRoute(@RequestBody Map<String, Object> routeData) {
        Map<String, Object> createdRoute = new HashMap<>();
        createdRoute.put("id", 3L);
        createdRoute.put("name", routeData.get("name"));
        createdRoute.put("observation", routeData.get("observation"));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoute);
    }
}
