-- Insertar datos de prueba para las rutas
INSERT INTO routes (name, observation) VALUES 
('Ruta Centro', 'Recolección de residuos del centro de la ciudad'),
('Ruta Norte', 'Cobertura de los barrios del norte'),
('Ruta Sur', 'Recolección en la zona sur industrial'),
('Ruta Este', 'Barrios residenciales del este'),
('Ruta Oeste', 'Zona comercial y residencial oeste');

-- Insertar datos de prueba para las categorías de residuos  
INSERT INTO waste_categories (name, description) VALUES
('Orgánico', 'Residuos biodegradables y compostables'),
('Plástico', 'Envases y productos plásticos reciclables'),
('Papel', 'Papel, cartón y productos de papel'),
('Vidrio', 'Botellas y envases de vidrio'),
('Metal', 'Latas y productos metálicos reciclables');

-- Insertar datos de prueba para vehículos
INSERT INTO vehicles (license_plate, capacity, fuel_type, observation) VALUES
('ABC-123', 1500.00, 'Diesel', 'Camión recolector principal'),
('DEF-456', 1200.00, 'Diesel', 'Vehículo de respaldo zona norte'),
('GHI-789', 1800.00, 'Diesel', 'Camión de alta capacidad'),
('JKL-012', 1000.00, 'Gasolina', 'Vehículo para rutas pequeñas');

-- Insertar datos de prueba para conductores
INSERT INTO drivers (name, license_number, phone, hire_date) VALUES
('Juan Pérez', 'LIC123456', '8888-1234', '2023-01-15'),
('María González', 'LIC789012', '8888-5678', '2023-03-20'),
('Carlos Rodríguez', 'LIC345678', '8888-9012', '2023-02-10'),
('Ana López', 'LIC901234', '8888-3456', '2023-04-05');
