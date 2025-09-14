package una.ac.cr.Lab1Progra3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import una.ac.cr.Lab1Progra3.dto.RouteDTO;
import una.ac.cr.Lab1Progra3.entity.Route;
import una.ac.cr.Lab1Progra3.repository.RouteRepository;

import java.util.Optional;

@Service
@Transactional
public class RouteService {
    
    @Autowired
    private RouteRepository routeRepository;
    
    public Page<RouteDTO> findAll(Pageable pageable) {
        return routeRepository.findAll(pageable)
                .map(this::toDTO);
    }
    
    public Optional<RouteDTO> findById(Long id) {
        return routeRepository.findById(id)
                .map(this::toDTO);
    }
    
    public RouteDTO save(RouteDTO routeDTO) {
        Route route = toEntity(routeDTO);
        Route saved = routeRepository.save(route);
        return toDTO(saved);
    }
    
    public Optional<RouteDTO> update(Long id, RouteDTO routeDTO) {
    return routeRepository.findById(id)
        .map(existingRoute -> {
            existingRoute.setName(routeDTO.getName());
            existingRoute.setObservation(routeDTO.getObservation());
                    existingRoute.setObservation(routeDTO.getObservation());
                    return toDTO(routeRepository.save(existingRoute));
                });
    }
    
    public boolean deleteById(Long id) {
        if (routeRepository.existsById(id)) {
            routeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Page<RouteDTO> findByNameContaining(String name, Pageable pageable) {
        return routeRepository.findByNameContaining(name, pageable)
                .map(this::toDTO);
    }
    
    private RouteDTO toDTO(Route route) {
        return RouteDTO.builder()
                .id(route.getId())
                .name(route.getName())
                .observation(route.getObservation())
                .build();
    }
    
    private Route toEntity(RouteDTO dto) {
        return Route.builder()
                .id(dto.getId())
                .name(dto.getName())
                .observation(dto.getObservation())
                .build();
    }
}
