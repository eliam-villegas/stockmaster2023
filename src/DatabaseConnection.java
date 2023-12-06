
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

public class DatabaseConnection {

    static String driver = "org.postgresql.Driver";
    static String dbname = "sushi";
    static String url = "jdbc:postgresql://10.4.3.195:5432/" + dbname;
    static String user = "sushi_dev";
    static String password = "5k4xFg6";

    void EliminarEmpleado(String id) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "UPDATE empleado SET activo = ? WHERE rut_empleado = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    public void ModificarEmpleado(String id, String nuevoNombre, String nuevoCargo, String nuevaContrasena) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "UPDATE empleado SET nombre = ?, cargo = ?, contrasenia = ? WHERE rut_empleado = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setString(1, nuevoNombre);
            preparedStatement.setString(2, nuevoCargo);
            preparedStatement.setString(3, nuevaContrasena);
            preparedStatement.setString(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    public void AgregarEmpleado(String id, String nombre, String cargo, String contrasena) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "INSERT INTO empleado (rut_empleado, nombre, cargo, contrasenia, activo) VALUES (?, ?, ?, ?, true)";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, cargo);
            preparedStatement.setString(4, contrasena);

            preparedStatement.executeUpdate();  // Usar executeUpdate en lugar de executeQuery para operaciones de modificación (INSERT, UPDATE, DELETE)

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    public void ModificarCliente(String rut, String nombre, String contacto, String direccion) {
        Connection conn = null;

        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Modificar el nombre del cliente
        if (nombre != null) {
            String updateNombre = "UPDATE cliente SET nombre = ? WHERE rut_cliente = ?";

            try ( PreparedStatement preparedStatement = conn.prepareStatement(updateNombre)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, rut);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "No se pudo modificar el nombre del cliente", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // Modificar la dirección del cliente
        if (direccion != null) {
            String updateDireccion = "UPDATE direcciones SET direccion = ? WHERE rut_cliente = ?";

            try ( PreparedStatement preparedStatement = conn.prepareStatement(updateDireccion)) {
                preparedStatement.setString(1, direccion);
                preparedStatement.setString(2, rut);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "No se pudo modificar la dirección del cliente", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // Modificar el contacto del cliente
        if (contacto != null) {
            String updateContacto = "UPDATE contacto SET telefono = ? WHERE rut_cliente = ?";

            try ( PreparedStatement preparedStatement = conn.prepareStatement(updateContacto)) {
                preparedStatement.setInt(1, Integer.parseInt(contacto));
                preparedStatement.setString(2, rut);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "No se pudo modificar el contacto del cliente", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        closeConnection(conn);
    }

    void EliminarCliente(String rut) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "UPDATE cliente SET activo = ? WHERE rut_cliente = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, rut);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    void AgregarRegistroAbastecimiento(String id_orden, LocalDate fechaActual, String subtotal, String rut_empleado, String rut_cliente) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaActual);

        String consulta = "INSERT INTO registro_abastecimiento (num_compra, fecha_de_compra, total,rut_empleado,rut_proveedor) VALUES (?, ?, ?,?,?)";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setInt(1, Integer.parseInt(id_orden));
            preparedStatement.setDate(2, fechaSQL);
            preparedStatement.setInt(3, Integer.parseInt(subtotal));
            preparedStatement.setInt(4, Integer.parseInt(rut_empleado));
            preparedStatement.setString(5, rut_cliente);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }

    }

    void AgregarProducto_a_registro_abastecimiento(List<Object[]> lista) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "INSERT INTO registro_abastecimiento_contiene_producto (num_compra, id_producto, cantidad,precio) VALUES (?, ?, ?,?)";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            for (var row : lista) {
                preparedStatement.setInt(1, Integer.parseInt(row[0].toString()));
                preparedStatement.setInt(2, Integer.parseInt(row[3].toString()));
                preparedStatement.setInt(3, Integer.parseInt(row[2].toString()));
                preparedStatement.setInt(4, Integer.parseInt(row[1].toString()));
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }

        conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        consulta = "UPDATE producto set stock = stock + ? where id_producto = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            for (var row : lista) {
                preparedStatement.setInt(1, Integer.parseInt(row[2].toString()));
                preparedStatement.setInt(2, Integer.parseInt(row[3].toString()));
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }

    }

    void AgregarProveedor(String id, String nombre, String telefono) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "INSERT INTO proveedor (rut_proveedor, nombre, telefono, activo) VALUES (?, ?, ?, true)";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.setString(2, nombre);
            preparedStatement.setInt(3, Integer.parseInt(telefono));

            preparedStatement.executeUpdate();  // Usar executeUpdate en lugar de executeQuery para operaciones de modificación (INSERT, UPDATE, DELETE)

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    void ModificarProveedor(String id, String nombre, String telefono) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "UPDATE proveedor SET nombre = ?, telefono = ? WHERE rut_proveedor = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, Integer.parseInt(telefono));
            preparedStatement.setString(3, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    void EliminarProveedor(String id) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "UPDATE proveedor SET activo = ? WHERE rut_proveedor = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    static enum VENTAS_POR {
        empleado {
            @Override
            public String toString() {
                return "empleado.rut_empleado";
            }
        },
        cliente {
            @Override
            public String toString() {
                return "cliente.rut_cliente";
            }
        }
    }

    static enum ORDER_BY {
        ASC,
        DESC
    }

    static enum CANT_STOCK {
        Alto,
        Bajo,
        Nada,
        SIN_FILTRO {
            @Override
            public String toString() {
                return "";
            }
        }
    }

    static Connection Getconnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // En lugar de imprimir el mensaje de error, lanzamos la excepción
            System.out.println(e);
            throw new SQLException("Error al conectar a la base de datos", e);
        }
        return conn;
    }

    public List<Object[]> ObtenerTiposProductos() {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String consulta = "SELECT DISTINCT tipo FROM producto";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                String valorColumna1 = resultSet.getString("tipo");
                Object[] row = {valorColumna1};
                rows.add(row);
            }

            return rows;
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> BuscarProducto(String textFieldContent, String tipo, CANT_STOCK stock) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String consulta = "SELECT * FROM producto WHERE nombre_producto ILIKE ? and activo = true";

        if (tipo != null && !tipo.isEmpty()) {
            consulta += " and producto.tipo = " + "'" + tipo + "'";
        }

        if (stock != null) {
            if (stock.equals(CANT_STOCK.Alto)) {
                consulta += " and stock >= 30";
            } else if (stock.equals(CANT_STOCK.Bajo)) {
                consulta += " and stock < 30";
            } else if (stock.equals(CANT_STOCK.Nada)) {
                consulta += " and stock = 0";
            }
        }

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setString(1, "%" + textFieldContent + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                String valorColumna1 = resultSet.getString("id_producto");
                String valorColumna2 = resultSet.getString("nombre_producto");
                String valorColumna3 = resultSet.getString("stock");
                String valorColumna4 = resultSet.getString("precio_unitario");
                String valorColumna5 = resultSet.getString("tipo");
                String valorColumna6 = resultSet.getString("unidad_de_medida");

                Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4, valorColumna5, valorColumna6};
                rows.add(row);
            }

            return rows;

            // Ejecutar la consulta y procesar el resultado si es necesario
            // ...
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> buscarProducto_por_tipo(String filtro) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String consulta = "SELECT * FROM producto WHERE tipo ILIKE ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            preparedStatement.setString(1, "%" + filtro + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                String valorColumna1 = resultSet.getString("id_producto");
                String valorColumna2 = resultSet.getString("nombre_producto");
                String valorColumna3 = resultSet.getString("stock");
                String valorColumna4 = resultSet.getString("precio_unitario");
                String valorColumna5 = resultSet.getString("tipo");
                String valorColumna6 = resultSet.getString("unidad_de_medida");

                Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4, valorColumna5, valorColumna6};
                rows.add(row);
            }

            return rows;

            // Ejecutar la consulta y procesar el resultado si es necesario
            // ...
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> buscarProducto_por_stock(String filtro) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String consulta = "SELECT * FROM producto WHERE 1=1";

        if (filtro.equals("Sobre stock")) {
            consulta += " AND stock >= 30";
        } else if (filtro.equals("Bajo stock")) {
            consulta += " AND stock < 30 AND stock >= 1";
        } else {
            consulta += " AND stock = 0";
        }

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                String valorColumna1 = resultSet.getString("id_producto");
                String valorColumna2 = resultSet.getString("nombre_producto");
                String valorColumna3 = resultSet.getString("stock");
                String valorColumna4 = resultSet.getString("precio_unitario");
                String valorColumna5 = resultSet.getString("tipo");
                String valorColumna6 = resultSet.getString("unidad_de_medida");

                Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4, valorColumna5, valorColumna6};
                rows.add(row);
            }

            return rows;

            // Ejecutar la consulta y procesar el resultado si es necesario
            // ...
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public void ModificarProducto(String id, String nombre, String stock, String precio, String tipo, String unidad) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "UPDATE producto SET nombre_producto = ?, stock = ?, precio_unitario = ?, tipo = ?, unidad_de_medida = ? WHERE id_producto = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, Integer.parseInt(stock));
            preparedStatement.setInt(3, Integer.parseInt(precio));
            preparedStatement.setString(4, tipo);
            preparedStatement.setString(5, unidad);
            preparedStatement.setInt(6, Integer.parseInt(id));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    public void EliminarProducto(String id) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "UPDATE producto SET activo = ? WHERE id_producto = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, Integer.parseInt(id));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    public void AgregarProducto(String id, String nombre, String stock, String precio, String tipo, String unidad) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "INSERT INTO producto (id_producto, nombre_producto, stock,precio_unitario,tipo,unidad_de_medida,activo) VALUES (?, ?, ?,?,?,?,true)";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.setString(2, nombre);
            preparedStatement.setInt(3, Integer.parseInt(stock));
            preparedStatement.setInt(4, Integer.parseInt(precio));
            preparedStatement.setString(5, tipo);
            preparedStatement.setString(6, unidad);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    public void AgregarCliente(String rut, String nombre, String contacto, String direccion) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "INSERT INTO cliente (nombre, rut_cliente,activo) VALUES (?,?,true)";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, rut);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Manejo de la excepción y mostrar tu propio mensaje al usuario
            if (e.getSQLState().equals("23505")) {
                JOptionPane.showMessageDialog(null, "rut ya existe", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } finally {
            closeConnection(conn);
        }

        conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (direccion != null && !direccion.isEmpty()) {
            consulta = "INSERT INTO direcciones (direccion, rut_cliente,activo) VALUES (?,?,true)";

            try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
                preparedStatement.setString(1, direccion);
                preparedStatement.setString(2, rut);

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                // Manejo de la excepción y mostrar tu propio mensaje al usuario
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "no se pudo agregar la direccion", "Error", JOptionPane.INFORMATION_MESSAGE);
            } finally {
                closeConnection(conn);
            }
        }

        conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (contacto != null && !contacto.isEmpty()) {
            consulta = "INSERT INTO contacto (telefono, rut_cliente,activo) VALUES (?,?,true)";

            try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
                preparedStatement.setInt(1, Integer.parseInt(contacto));
                preparedStatement.setString(2, rut);

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                // Manejo de la excepción y mostrar tu propio mensaje al usuario
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "no se pudo agregar el contacto", "Error", JOptionPane.INFORMATION_MESSAGE);
            } finally {
                closeConnection(conn);
            }
        }

    }

    public List<Object[]> BuscarCliente(String nombre) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String consulta = "SELECT cliente.rut_cliente as rut, cliente.nombre AS nombre_cliente, "
                + "contacto.telefono AS numero_contacto, direcciones.direccion AS direccion_cliente "
                + "FROM cliente "
                + "LEFT JOIN contacto ON contacto.rut_cliente = cliente.rut_cliente "
                + "LEFT JOIN direcciones ON direcciones.rut_cliente = cliente.rut_cliente where cliente.nombre ILIKE ? and cliente.activo = true";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            preparedStatement.setString(1, "%" + nombre + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                int valorColumna1 = resultSet.getInt("rut");
                String valorColumna2 = resultSet.getString("nombre_cliente");
                String valorColumna3 = resultSet.getString("numero_contacto");
                String valorColumna4 = resultSet.getString("direccion_cliente");
                Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4};
                rows.add(row);
            }

            return rows;

            // Ejecutar la consulta y procesar el resultado si es necesario
            // ...
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> BuscarProveedor(String nombre) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String consulta = "SELECT * from proveedor where nombre ILIKE ? and activo = true ";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            preparedStatement.setString(1, "%" + nombre + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                int valorColumna1 = resultSet.getInt("rut_proveedor");
                String valorColumna2 = resultSet.getString("nombre");
                String valorColumna3 = resultSet.getString("telefono");
                Object[] row = {valorColumna1, valorColumna2, valorColumna3};
                rows.add(row);
            }

            return rows;

            // Ejecutar la consulta y procesar el resultado si es necesario
            // ...
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> BuscarOrdenDeCompra(String nombre_cliente, String nombre_vendedor, Date fechaInicial, Date fechaFinal, String numOrden) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String sql = "SELECT orden_de_compra.id_orden,orden_de_compra.fecha_de_compra,empleado.nombre as nombre_empleado,cliente.nombre as nombre_cliente "
                + "FROM orden_de_compra "
                + "JOIN empleado ON empleado.rut_empleado = orden_de_compra.rut_empleado "
                + "JOIN cliente ON cliente.rut_cliente = orden_de_compra.rut_cliente "
                + "WHERE 1=1";

        if (nombre_cliente != null && !nombre_cliente.isEmpty()) {
            sql += " AND cliente.nombre ILIKE ?";
        }

        if (nombre_vendedor != null && !nombre_vendedor.isEmpty()) {
            sql += " AND empleado.nombre ILIKE ?";
        }

        if (fechaInicial != null && fechaFinal != null) {
            sql += " AND orden_de_compra.fecha_de_compra <= ? AND orden_de_compra.fecha_de_compra >= ?";
        }

        if (numOrden != null && !numOrden.isEmpty()) {
            sql += " AND orden_de_compra.id_orden = ?";
        }

        // Preparar la declaración SQL
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Establecer los parámetros según los filtros proporcionados
            int parametroIndex = 1;

            if (nombre_cliente != null && !nombre_cliente.isEmpty()) {
                preparedStatement.setString(parametroIndex++, "%" + nombre_cliente + "%");
            }

            if (nombre_vendedor != null && !nombre_vendedor.isEmpty()) {
                preparedStatement.setString(parametroIndex++, "%" + nombre_vendedor + "%");
            }

            if (fechaInicial != null && fechaFinal != null) {
                preparedStatement.setDate(parametroIndex++, new java.sql.Date(fechaFinal.getTime()));
                preparedStatement.setDate(parametroIndex++, new java.sql.Date(fechaInicial.getTime()));
            }

            if (numOrden != null && !numOrden.isEmpty()) {
                preparedStatement.setInt(parametroIndex++, Integer.parseInt(numOrden));
            }

            // Ejecutar la consulta
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Object[]> rows = new ArrayList<>();

                while (resultSet.next()) {
                    int valorColumna1 = resultSet.getInt("id_orden");
                    Date valorColumna2 = resultSet.getDate("fecha_de_compra");
                    String valorColumna3 = resultSet.getString("nombre_empleado");
                    String valorColumna4 = resultSet.getString("nombre_cliente");

                    Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4};
                    rows.add(row);
                    //System.out.println(row[0]);
                }

                return rows;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> ObtenenOrdenDeCompraDetalles(String numOrden) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String sql = "SELECT orden_de_compra.id_orden,orden_de_compra.fecha_de_compra,empleado.nombre as nombre_empleado,cliente.nombre as nombre_cliente, producto.nombre_producto, orden_compra_contiene_producto.cantidad,orden_compra_contiene_producto.precio, orden_de_compra.subtotal "
                + "FROM orden_de_compra "
                + "JOIN orden_compra_contiene_producto ON orden_de_compra.id_orden = orden_compra_contiene_producto.id_orden "
                + "JOIN producto ON orden_compra_contiene_producto.id_producto = producto.id_producto "
                + "JOIN empleado ON empleado.rut_empleado = orden_de_compra.rut_empleado "
                + "JOIN cliente ON cliente.rut_cliente = orden_de_compra.rut_cliente "
                + "WHERE orden_de_compra.id_orden = ?";

        // Preparar la declaración SQL
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Establecer los parámetros según los filtros proporcionados
            preparedStatement.setInt(1, Integer.parseInt(numOrden));

            // Ejecutar la consulta
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Object[]> rows = new ArrayList<>();

                while (resultSet.next()) {
                    int valorColumna1 = resultSet.getInt("id_orden");
                    Date valorColumna2 = resultSet.getDate("fecha_de_compra");
                    String valorColumna3 = resultSet.getString("nombre_empleado");
                    String valorColumna4 = resultSet.getString("nombre_cliente");

                    String valorColumna5 = resultSet.getString("nombre_producto");
                    int valorColumna6 = resultSet.getInt("cantidad");
                    int valorColumna7 = resultSet.getInt("precio");
                    int valorColumna8 = resultSet.getInt("subtotal");

                    Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4, valorColumna5, valorColumna6, valorColumna7, valorColumna8};
                    rows.add(row);
                    System.out.println(row[4]);
                }

                return rows;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }

    }

    public List<Object[]> ObtenenRegistroAbastecimientoDetalles(String numOrden) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String sql = "SELECT registro_abastecimiento.num_compra,registro_abastecimiento.fecha_de_compra,empleado.nombre as nombre_empleado,proveedor.nombre as nombre_proveedor, producto.nombre_producto, registro_abastecimiento_contiene_producto.cantidad,registro_abastecimiento_contiene_producto.precio, registro_abastecimiento.subtotal "
                + "FROM registro_abastecimiento "
                + "JOIN registro_abastecimiento_contiene_producto ON registro_abastecimiento.num_compra = registro_abastecimiento_contiene_producto.num_compra "
                + "JOIN producto ON registro_abastecimiento_contiene_producto.id_producto = producto.id_producto "
                + "JOIN empleado ON empleado.rut_empleado = registro_abastecimiento.rut_empleado "
                + "JOIN proveedor ON proveedor.rut_proveedor = registro_abastecimiento.rut_proveedor "
                + "WHERE registro_abastecimiento.num_compra = ?";

        // Preparar la declaración SQL
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Establecer los parámetros según los filtros proporcionados
            preparedStatement.setInt(1, Integer.parseInt(numOrden));

            // Ejecutar la consulta
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Object[]> rows = new ArrayList<>();

                while (resultSet.next()) {
                    int valorColumna1 = resultSet.getInt("num_compra");
                    Date valorColumna2 = resultSet.getDate("fecha_de_compra");
                    String valorColumna3 = resultSet.getString("nombre_empleado");
                    String valorColumna4 = resultSet.getString("nombre_cliente");

                    String valorColumna5 = resultSet.getString("nombre_producto");
                    int valorColumna6 = resultSet.getInt("cantidad");
                    int valorColumna7 = resultSet.getInt("precio");
                    int valorColumna8 = resultSet.getInt("subtotal");

                    Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4, valorColumna5, valorColumna6, valorColumna7, valorColumna8};
                    rows.add(row);
                    System.out.println(row[4]);
                }

                return rows;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }

    }

    public List<Object[]> ObtenerClientes_para_orden() {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        /*String consulta = "SELECT DISTINCT c.rut_cliente, c.nombre "+
                          "FROM cliente c "+
                          "JOIN orden_de_compra o ON c.rut_cliente = o.rut_cliente";*/
        String consulta = "SELECT rut_cliente,nombre FROM cliente";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                int valorColumna1 = resultSet.getInt("rut_cliente");
                String valorColumna2 = resultSet.getString("nombre");

                Object[] row = {valorColumna1, valorColumna2};
                rows.add(row);
            }

            return rows;

        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> BuscarClientes_para_orden(String textFieldContent) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String consulta = "SELECT rut_cliente,nombre FROM cliente WHERE nombre ILIKE ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            preparedStatement.setString(1, "%" + textFieldContent + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                int valorColumna1 = resultSet.getInt("rut_cliente");
                String valorColumna2 = resultSet.getString("nombre");

                Object[] row = {valorColumna1, valorColumna2};
                rows.add(row);
            }

            return rows;

            // Ejecutar la consulta y procesar el resultado si es necesario
            // ...
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public void agregarRegistroAbastecimiento() {

    }

    public List<Object[]> BuscarRegistroAbastecimiento(String nombre_proveedor, String nombre_empleado, java.util.Date fechaInicial, java.util.Date fechaFinal, String numCompra) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String sql = "SELECT registro_abastecimiento.num_compra,registro_abastecimiento.fecha_de_compra,empleado.nombre as nombre_empleado,proveedor.nombre as nombre_proveedor, registro_abastecimiento.total "
                + "FROM registro_abastecimiento "
                + "JOIN empleado ON empleado.rut_empleado = registro_abastecimiento.rut_empleado "
                + "JOIN proveedor ON proveedor.rut_proveedor = registro_abastecimiento.rut_proveedor "
                + "WHERE 1=1";

        if (nombre_proveedor != null && !nombre_proveedor.isEmpty()) {
            sql += " AND proveedor.nombre ILIKE ?";
        }

        if (nombre_empleado != null && !nombre_empleado.isEmpty()) {
            sql += " AND empleado.nombre ILIKE ?";
        }

        if (fechaInicial != null && fechaFinal != null) {
            sql += " AND registro_abastecimiento.fecha_de_compra <= ? AND registro_abastecimiento.fecha_de_compra >= ?";
        }

        if (numCompra != null && !numCompra.isEmpty()) {
            sql += " AND registro_abastecimiento.num_compra = ?";
        }

        // Preparar la declaración SQL
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Establecer los parámetros según los filtros proporcionados
            int parametroIndex = 1;

            if (nombre_proveedor != null && !nombre_proveedor.isEmpty()) {
                preparedStatement.setString(parametroIndex++, "%" + nombre_proveedor + "%");
            }

            if (nombre_empleado != null && !nombre_empleado.isEmpty()) {
                preparedStatement.setString(parametroIndex++, "%" + nombre_empleado + "%");
            }

            if (fechaInicial != null && fechaFinal != null) {
                preparedStatement.setDate(parametroIndex++, new java.sql.Date(fechaFinal.getTime()));
                preparedStatement.setDate(parametroIndex++, new java.sql.Date(fechaInicial.getTime()));
            }

            if (numCompra != null && !numCompra.isEmpty()) {
                preparedStatement.setInt(parametroIndex++, Integer.parseInt(numCompra));
            }

            // Ejecutar la consulta
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Object[]> rows = new ArrayList<>();

                while (resultSet.next()) {
                    int valorColumna1 = resultSet.getInt("num_compra");
                    java.util.Date valorColumna2 = resultSet.getDate("fecha_de_compra");
                    String valorColumna3 = resultSet.getString("nombre_empleado");
                    String valorColumna4 = resultSet.getString("nombre_proveedor");
                    int valorColumna5 = resultSet.getInt("total");

                    Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4, valorColumna5};
                    rows.add(row);
                    System.out.println(row[0]);
                }

                return rows;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }

    }

    public List<Object[]> ObtenerRegistroAbastecimientoDetalles(String numCompra) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String sql = "SELECT registro_abastecimiento.num_compra,registro_abastecimiento.fecha_de_compra,empleado.nombre as nombre_empleado,proveedor.nombre as nombre_proveedor, registro_abastecimiento.total, producto.nombre_producto, registro_abastecimiento_contiene_producto.cantidad, registro_abastecimiento_contiene_producto.precio "
                + "FROM registro_abastecimiento "
                + "JOIN registro_abastecimiento_contiene_producto ON registro_abastecimiento.num_compra = registro_abastecimiento_contiene_producto.num_compra "
                + "JOIN producto ON registro_abastecimiento_contiene_producto.id_producto = producto.id_producto "
                + "JOIN empleado ON empleado.rut_empleado = registro_abastecimiento.rut_empleado "
                + "JOIN proveedor ON proveedor.rut_proveedor = registro_abastecimiento.rut_proveedor "
                + "WHERE registro_abastecimiento.num_compra = ?";

        // Preparar la declaración SQL
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Establecer los parámetros según los filtros proporcionados
            preparedStatement.setInt(1, Integer.parseInt(numCompra));

            // Ejecutar la consulta
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Object[]> rows = new ArrayList<>();

                while (resultSet.next()) {
                    int valorColumna1 = resultSet.getInt("num_compra");
                    Date valorColumna2 = resultSet.getDate("fecha_de_compra");
                    String valorColumna3 = resultSet.getString("nombre_empleado");
                    String valorColumna4 = resultSet.getString("nombre_proveedor");

                    String valorColumna5 = resultSet.getString("nombre_producto");
                    int valorColumna6 = resultSet.getInt("cantidad");
                    int valorColumna7 = resultSet.getInt("precio");
                    int valorColumna8 = resultSet.getInt("total");

                    Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4, valorColumna5, valorColumna6, valorColumna7, valorColumna8};
                    rows.add(row);

                }

                return rows;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> ObtenerVentasPor(VENTAS_POR por, String nombre, Date fechaInicial, Date fechaFinal, ORDER_BY order_by) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String selectString;
        if (por.equals(VENTAS_POR.cliente)) {
            selectString = "cliente.nombre";
        } else {
            selectString = "empleado.nombre";
        }

        String whereClause = "";

        if (nombre != null && !nombre.isEmpty()) {
            whereClause += " AND " + selectString + " ILIKE ?";
        }

        if (fechaInicial != null && fechaFinal != null) {
            whereClause += " AND registro_abastecimiento.fecha_de_compra <= ? AND registro_abastecimiento.fecha_de_compra >= ?";
        }

        String sql = "select " + por.toString() + " as rut" + "," + selectString + " as nombre" + "," + " sum(orden_de_compra.subtotal) as total "
                + "from orden_de_compra "
                + "join cliente on orden_de_compra.rut_cliente = cliente.rut_cliente "
                + "join empleado on orden_de_compra.rut_empleado = empleado.rut_empleado "
                + "where 1=1" + whereClause + " "
                + "group by " + por.toString() + " order by total " + order_by.toString();

        // Preparar la declaración SQL
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Establecer los parámetros según los filtros proporcionados
            int parametroIndex = 1;

            if (nombre != null && !nombre.isEmpty()) {
                preparedStatement.setString(parametroIndex++, "%" + nombre + "%");
            }

            if (fechaInicial != null && fechaFinal != null) {
                preparedStatement.setDate(parametroIndex++, new java.sql.Date(fechaFinal.getTime()));
                preparedStatement.setDate(parametroIndex++, new java.sql.Date(fechaInicial.getTime()));
            }

            // Ejecutar la consulta
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Object[]> rows = new ArrayList<>();

                while (resultSet.next()) {
                    String valorColumna1 = resultSet.getString("rut");
                    String valorColumna2 = resultSet.getString("nombre");
                    int valorColumna3 = resultSet.getInt("total");

                    Object[] row = {valorColumna1, valorColumna2, valorColumna3};
                    rows.add(row);
                    System.out.println(row[0] + "," + row[1] + "," + row[2]);
                }

                return rows;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> ObtenerEmpleados_para_orden() {

        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String consulta = "SELECT rut_empleado,nombre FROM empleado";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                int valorColumna1 = resultSet.getInt("rut_empleado");
                String valorColumna2 = resultSet.getString("nombre");

                Object[] row = {valorColumna1, valorColumna2};
                rows.add(row);
            }

            return rows;

        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public boolean VerificarIDUnico_orden_de_compra(int id) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        String consulta = "SELECT id_orden FROM orden_de_compra WHERE id_orden = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                int valorColumna1 = resultSet.getInt("id_orden");

                Object[] row = {valorColumna1};
                rows.add(row);
            }

            if (rows.isEmpty()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> ObtenerProducto_para_orden() {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String consulta = "SELECT id_producto,nombre_producto,precio_unitario,stock FROM producto where activo = true";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                String valorColumna1 = resultSet.getString("id_producto");
                String valorColumna2 = resultSet.getString("nombre_producto");
                String valorColumna4 = resultSet.getString("precio_unitario");
                String valorColumna3 = resultSet.getString("stock");

                Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4};
                rows.add(row);
            }

            return rows;

            // Ejecutar la consulta y procesar el resultado si es necesario
            // ...
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public boolean VerificarStockProducto(int cantidad, int id) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        String consulta = "SELECT stock from producto WHERE stock >= ? AND id_producto = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            preparedStatement.setInt(1, cantidad);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                int valorColumna1 = resultSet.getInt("stock");

                Object[] row = {valorColumna1};
                rows.add(row);
            }

            if (rows.isEmpty()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public boolean VerificarRutEmpleadoExiste(String rut) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        String consulta = "SELECT rut_empleado FROM empleado WHERE rut_empleado = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            preparedStatement.setString(1, rut);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                int valorColumna1 = resultSet.getInt("rut_empleado");

                Object[] row = {valorColumna1};
                rows.add(row);
            }

            if (rows.isEmpty()) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public void AgregarProducto_a_orden_de_compra(DefaultTableModel modelo_tabla) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        String consulta = "INSERT INTO orden_compra_contiene_producto (id_orden, id_producto, cantidad,precio) VALUES (?, ?, ?,?)";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            for (int row = 0; row < modelo_tabla.getRowCount(); row++) {
                preparedStatement.setInt(1, Integer.parseInt(modelo_tabla.getValueAt(row, 0).toString()));
                preparedStatement.setInt(2, Integer.parseInt(modelo_tabla.getValueAt(row, 1).toString()));
                preparedStatement.setInt(3, Integer.parseInt(modelo_tabla.getValueAt(row, 2).toString()));
                preparedStatement.setInt(4, Integer.parseInt(modelo_tabla.getValueAt(row, 3).toString()));
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }

        conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        consulta = "UPDATE producto  set stock = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            for (int row = 0; row < modelo_tabla.getRowCount(); row++) {
                preparedStatement.setInt(1, Integer.parseInt(modelo_tabla.getValueAt(row, 0).toString()));
                preparedStatement.setInt(2, Integer.parseInt(modelo_tabla.getValueAt(row, 1).toString()));
                preparedStatement.setInt(3, Integer.parseInt(modelo_tabla.getValueAt(row, 2).toString()));
                preparedStatement.setInt(4, Integer.parseInt(modelo_tabla.getValueAt(row, 3).toString()));
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }

    }

    public void AgregarOrdenDeCompra(String id_orden, LocalDate fecha_actual, String subtotal, String rut_empleado, String rut_cliente) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }

        java.sql.Date fechaSQL = java.sql.Date.valueOf(fecha_actual);

        String consulta = "INSERT INTO orden_de_compra (id_orden, fecha_de_compra, subtotal,rut_empleado,rut_cliente) VALUES (?, ?, ?,?,?)";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {

            preparedStatement.setInt(1, Integer.parseInt(id_orden));
            preparedStatement.setDate(2, fechaSQL);
            preparedStatement.setInt(3, Integer.parseInt(subtotal));
            preparedStatement.setInt(4, Integer.parseInt(rut_empleado));
            preparedStatement.setString(5, rut_cliente);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(conn);
            // Manejar la excepción según tus necesidades
        } finally {
            closeConnection(conn);
        }
    }

    public List<Object[]> BuscarEmpleado(String filtro) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        String consulta = "SELECT * FROM empleado WHERE nombre ILIKE ? and activo = true";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            preparedStatement.setString(1, "%" + filtro + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Object[]> rows = new ArrayList<>();

            while (resultSet.next()) {
                int valorColumna1 = resultSet.getInt("rut_empleado");
                String valorColumna2 = resultSet.getString("nombre");
                String valorColumna3 = resultSet.getString("cargo");
                String valorColumna4 = resultSet.getString("contrasenia");

                Object[] row = {valorColumna1, valorColumna2, valorColumna3, valorColumna4};
                rows.add(row);
            }

            return rows;

            // Ejecutar la consulta y procesar el resultado si es necesario
            // ...
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            closeConnection(conn);
        }
    }

    public String ObtenerNombreEmpleado(String rut) {
        Connection conn = null;
        try {
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        String consulta = "SELECT nombre FROM empleado WHERE rut_empleado = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
            preparedStatement.setString(1, rut);
            ResultSet resultSet = preparedStatement.executeQuery();
            String valor = null;

            while (resultSet.next()) {
                valor = resultSet.getString("nombre");
            }

            return valor;

            // Ejecutar la consulta y procesar el resultado si es necesario
            // ...
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

}
