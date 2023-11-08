-- Necesidad de informacion:
-- Se desea conocer la cantidad total de productos vendidos de cada tipo (campo 'tipo' en la tabla 'producto').

SELECT p.tipo, SUM(rvp.cantidad) AS cantidad_total_vendida
FROM public.producto p
LEFT JOIN public.registro_venta_contiene_producto rvp ON p.id_producto = rvp.id_producto
GROUP BY p.tipo

-- Este query resulta una tabla que muestra el tipo de producto y la cantidad total vendida para cada categoria de producto

-- Necesidad de informacion:
-- Queremos saber el total de ordenes de venta realizadas por cada empleado (campo 'rut_empleado' 
-- en la tabla 'orden_de_compra') durante un período específico de tiempo.

SELECT odc.rut_empleado, e.nombre AS nombre_empleado, COUNT(odc.id_orden) AS total_ventas
FROM public.orden_de_compra odc
INNER JOIN public.empleado e ON odc.rut_empleado = e.rut_empleado
WHERE odc.fecha BETWEEN '2023-01-01' AND '2023-12-31'
GROUP BY odc.rut_empleado, e.nombre
ORDER BY total_ventas DESC;

-- Con este query obtenemos una tabla que muestra el rut del empleado, ademas de su nombre y las ordenes de compra realizadas por
-- cada uno de los empleados durante un periodo de tiempo especificado, ordenado de mayor a menor cantidad de ordenes
