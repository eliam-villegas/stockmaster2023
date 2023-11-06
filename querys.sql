--QUERIES NO TRIVIALES DE BUSQUEDA DE INFORMACION (no testeadas porque no funciona el server de la u)

--GENERAR VOUCHER

SELECT orden_compra.id_orden,orden_compra.fecha,empleado.nombre,cliente.nombre,producto.nombre,venta_producto.cantidad
FROM orden_compra 
JOIN venta_producto ON orden_compra.id_orden = venta_producto.id_orden
JOIN producto ON venta_producto.id_producto = producto.id_producto
JOIN empleado ON empleado.rut_empleado = orden_compra.rut_empleado
JOIN cliente ON cliente.rut_cliente = orden_compra.rut_cliente
WHERE id_orden = 'la id de la orden'

---------------------------------------------------

--BUSCAR BOLETAS

--por empleado vendedor

SELECT orden_compra.id_orden
FROM orden_compra 
JOIN empleado ON empleado.rut_empleado = orden_compra.rut_empleado
WHERE empleado.nombre = 'nombre del vendedor'

--por cliente

SELECT orden_compra.id_orden
FROM orden_compra 
JOIN cliente ON cliente.rut_cliente = orden_compra.rut_cliente
WHERE cliente.nombre = 'nombre del cliente'

--por rango de fecha

SELECT orden_compra.id_orden
FROM orden_compra 
WHERE fecha > 'limite inf' AND fecha < 'limite sup'


------------------------------------------------------

--OBTENER ORDENES DE COMPRA NO PAGADAS

SELECT orden_compra.id_orden
FROM orden_compra 
LEFT JOIN registro_venta ON orden_compra.id_orden = registro_venta.id_orden
WHERE registro_venta is NULL

--OBTENER ORDENES DE COMPRA PAGADAS

SELECT orden_compra.id_orden
FROM orden_compra 
JOIN registro_venta ON orden_compra.id_orden = registro_venta.id_orden

------------------------------------------------------------

