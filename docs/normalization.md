# Normalización (1FN → 3FN)

Fuente: Libro Excel (imágenes adjuntas). Hojas: RESUMEN, RUTAS, Combustible-KM, Cárnico, Reciclaje, Ordinarios (no aprovechable), Orgánico, Análisis.

## Objetivo del modelo
Registrar operaciones de recolección de residuos por ruta/tipo, pesajes y logística (vehículos, conductores), y abastecimiento de combustible. Los tableros mensuales del Excel deben ser consultables a partir de datos atómicos.

## Entidades candidatas (desde el Excel)
- Vehículo (PLACA, tipo, capacidad, año, observación) y su historial de conductores/estado.
- Conductor (nombre).
- Ruta (1..5, Comercial) con kms del recorrido.
- Tipo de residuo: Orgánico, Reciclaje (incluye campañas como Trueque Verde/Vidrio), Cárnico, No aprovechable, Otros.
- Centro de acopio (p.ej. “EL GENERAL”).
- Abastecimiento de combustible (N° factura, placa, fecha, litros, monto, comprador, OC, cupón).
- Recorridos/KM por vehículo (salida/entrada km, total km), horas en ruta, cuadrilla, observaciones.
- Pesajes/recolecciones (fecha, toneladas o kg, ruta, placa, tipo de residuo, hora de pesaje, centro de acopio, “otros no aprovechable”, producto en no aprovechable).
- Programación de recolección por ruta/tipo (días/semana).

## Supuestos
- Un pesaje pertenece a un “viaje/recorrido” (Trip) realizado por un vehículo en una ruta en una fecha. Un viaje puede tener uno o más pesajes (por ejemplo, varios pesajes en el día).
- Un conductor puede cambiar de vehículo; se modela con asignaciones fechadas.
- Unidades: se modela peso en kilogramos (`weight_kg`) para uniformidad; toneladas se derivan (kg/1000). Los KM son enteros.
- “Comercial/Comercio” se modela como una ruta con flag `is_commercial`.
- “Campaña Trueque Verde (Vidrio)” se modela como un tipo de residuo específico.

## Claves y Dependencias Funcionales (DF)
- Vehículo: PLACA → tipo, capacidad, año, observación, activo
- Conductor: ID → nombre, activo
- Ruta: CODE/NAME → km_lineales, is_commercial
- Asignación Vehículo-Conductor: (vehículo, inicio) → conductor, fin, observación
- TipoResiduo: CODE → nombre, categoría
- Programación Ruta-Tipo: (ruta, tipo) → días_por_semana
- Viaje (Trip): ID → vehículo, conductor, ruta, tipo_residuo, fecha, horas, km_salida, km_entrada, total_km, cuadrilla, observaciones
- Pesaje/Recolecta (Collection): ID → trip, facility, fecha/hora, weight_kg, producto, otros_no_aprov_kg
- Combustible: ID → vehículo, fecha, litros, monto, comprador, factura, oc, cupon

Estas DF motivan relaciones 1:N entre master data (vehículo, ruta, tipo_residuo, facility) y hechos (viajes, pesajes, combustible).

## Normalización
### 1FN
- Elimina celdas con totales/meses combinados y porcentajes; reemplaza por registros atómicos (un pesaje por fila, un abastecimiento por fila, un km-lectura por viaje, etc.).
- Cada atributo es atómico: fechas, cantidades (kg, litros), importes, llaves foráneas.

### 2FN
- Separamos atributos que dependen parcialmente de composiciones. Ejemplo: si identificáramos Viaje por (vehículo, fecha), entonces conductor y ruta dependen del viaje completo, no solo de vehículo; por eso el Viaje tiene su propia PK (`trip_id`) y FKs a vehículo/ruta/conductor/tipo.
- Los pesajes no dependen parcialmente de (ruta, mes) ni (vehículo, mes); dependen de un Viaje específico → `collection.trip_id`.

### 3FN
- Removemos dependencias transitivas: el nombre del conductor no depende del vehículo; se separa `driver` y `vehicle_driver_assignment` con vigencias.
- “Centro de acopio” no depende del tipo de residuo sino de la operación concreta; se separa `facility` y se referencia desde `collection`.
- Programación `días_por_semana` depende de (ruta, tipo), no del Viaje ni del Vehículo; se normaliza en su propia relación.

## Modelo lógico resultante (resumen)
- Master: `vehicle`, `driver`, `vehicle_driver_assignment`, `route`, `waste_type`, `facility`.
- Operacional: `trip` (recorridos + horas + km), `collection` (pesajes/toneladas por viaje), `fuel_purchase`.
- Planeación: `route_waste_schedule` (días/semana por ruta y tipo).

Consultas como las del Excel (toneladas mensuales por ruta/tipo, promedios, totales, costos por combustible, km por vehículo/mes) se obtienen por agregaciones sobre `collection`, `trip` y `fuel_purchase`.

## Índices sugeridos
- Búsquedas por periodo: índices en `trip(date)`, `collection(measured_at)`, `fuel_purchase(date)`.
- Filtros por relación: `trip(route_id)`, `trip(waste_type_id)`, `collection(trip_id)`, `fuel_purchase(vehicle_id)`.

## Siguientes pasos
- Ver DDL en `docs/schema.sql` y diagrama en `docs/erd.mmd` (exportar a PNG como `docs/erd.png`).
