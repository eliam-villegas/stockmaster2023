-- Necesidad de informacion:
-- Se desea conocer la cantidad total de productos vendidos de cada tipo (campo 'tipo' en la tabla 'producto').

SELECT p.tipo, SUM(rvp.cantidad) AS cantidad_total_vendida
FROM public.producto p
LEFT JOIN public.registro_venta_contiene_producto rvp ON p.id_producto = rvp.id_producto
GROUP BY p.tipo

-- Este query resulta una tabla que muestra el tipo de producto y la cantidad total vendida para cada categoria de producto

-- Necesidad de informacion:
-- Queremos saber el total de ventas realizadas por cada empleado (campo 'rut_empleado' 
-- en la tabla 'registro_de_venta') durante un período específico de tiempo.

SELECT rdv.rut_empleado, e.nombre AS nombre_empleado, COUNT(rdv.id_venta) AS total_ventas
FROM public.registro_de_venta rdv
INNER JOIN public.empleado e ON rdv.rut_empleado = e.rut_empleado
WHERE rdv.fecha_de_pago BETWEEN '2023-01-01' AND '2023-12-31'
GROUP BY rdv.rut_empleado, e.nombre
ORDER BY total_ventas DESC;

-- Con este query obtenemos una tabla que muestra el rut del empleado, ademas de su nombre y las ventas realizadas por
-- cada uno de los empleados durante un periodo de tiempo especificado, ordenado de mayor a menor cantidad de ventas
