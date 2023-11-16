import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class DatabaseConnection {
    static String driver = "org.postgresql.Driver";
    static String dbname = "sushi";
    static String url = "jdbc:postgresql://10.4.3.195:5432/" + dbname;
    static String user = "sushi_dev";
    static String password = "5k4xFg6";

    private Connection Getconnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos!");
        } catch (SQLException e) {
            // En lugar de imprimir el mensaje de error, lanzamos la excepción
            System.out.println(e);
            throw new SQLException("Error al conectar a la base de datos", e);
        }
        return conn;
    }
    
    public List<Object[]> BotonBuscarQuery(String textFieldContent){
        Connection conn = null;
        try{
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;  
        }
        
        String consulta = "SELECT * FROM producto WHERE nombre_producto ILIKE ?";
        
            try (PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
                preparedStatement.setString(1, "%"+textFieldContent+"%");
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Object[]> rows = new ArrayList<>();
                
                while (resultSet.next()) {
                    String valorColumna1 = resultSet.getString("id_producto");
                    String valorColumna2 = resultSet.getString("nombre_producto");
                    String valorColumna3 = resultSet.getString("stock");
                    String valorColumna4 = resultSet.getString("precio_unitario");
                    String valorColumna5 = resultSet.getString("tipo");
                    String valorColumna6 = resultSet.getString("unidad_de_medida");
                      
                    Object[] row = {valorColumna1,valorColumna2,valorColumna3,valorColumna4,valorColumna5,valorColumna6};
                    rows.add(row);
                    }                 
                
                return rows;
                
                
                
                // Ejecutar la consulta y procesar el resultado si es necesario
                // ...
            } catch (SQLException e) {
                System.out.println(e);
                closeConnection(conn);
                // Manejar la excepción según tus necesidades
            }finally{
                closeConnection(conn);
            }
        
        
        return null;
        
        
    }
    
    public void BotonIngresar(String id,String nombre,String stock,String precio,String tipo,String unidad){
        Connection conn = null;
        try{
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
        
        String consulta = "INSERT INTO producto (id_producto, nombre_producto, stock,precio_unitario,tipo,unidad_de_medida) VALUES (?, ?, ?,?,?,?)";
        
            try (PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
                
                preparedStatement.setInt(1,Integer.parseInt(id));
                preparedStatement.setString(2,nombre);
                preparedStatement.setInt(3,Integer.parseInt(stock));
                preparedStatement.setInt(4,Integer.parseInt(precio));
                preparedStatement.setString(5,tipo);
                preparedStatement.setString(6,unidad);
                
                
                preparedStatement.executeQuery();

            } catch (SQLException e) {
                System.out.println(e);
                closeConnection(conn);
                // Manejar la excepción según tus necesidades
            }finally{
                closeConnection(conn);
            }
    }
    
    public void AgregarCliente(String nombre,String rut,String direccion,String contacto){
        Connection conn = null;
        try{
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
        
        String consulta = "INSERT INTO cliente (nombre, rut_cliente) VALUES (?,?)";
        
            try (PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
                preparedStatement.setString(1,nombre);
                preparedStatement.setString(2,rut);
                
                
                preparedStatement.executeQuery();

            }
            catch (SQLException e) {
                // Manejo de la excepción y mostrar tu propio mensaje al usuario
                if(e.getSQLState().equals("23505")){
                    JOptionPane.showMessageDialog(null, "rut ya existe", "Error", JOptionPane.INFORMATION_MESSAGE);   
                }
            }finally{
                closeConnection(conn);
            }
    }
    
    public void AgregarProveedor(String nombre,String rut,String telefono){
        Connection conn = null;
        try{
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
        
        String consulta = "INSERT INTO proveedor (nombre, rut_proveedor,telefono) VALUES (?,?,?)";
        
            try (PreparedStatement preparedStatement = conn.prepareStatement(consulta)) {
                preparedStatement.setString(1,nombre);
                preparedStatement.setString(2,rut);
                preparedStatement.setString(3,telefono);
                
                preparedStatement.executeQuery();

            }
            catch (SQLException e) {
                // Manejo de la excepción y mostrar tu propio mensaje al usuario
                if(e.getSQLState().equals("23505")){
                    JOptionPane.showMessageDialog(null, "rut ya existe", "Error", JOptionPane.INFORMATION_MESSAGE);   
                }
            }finally{
                closeConnection(conn);
            }
    }
    
    public List<Object[]> BuscarOrdenDeCompra(String nombre_cliente,Date fechaInicial,Date fechaFinal,String numOrden){
        Connection conn = null;
        try{
            conn = Getconnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        
        String sql = "SELECT orden_de_compra.id_orden,orden_de_compra.fecha_de_compra,empleado.nombre as nombre_empleado,cliente.nombre as nombre_cliente " +
                    "FROM orden_de_compra " +
                    "JOIN empleado ON empleado.rut_empleado = orden_de_compra.rut_empleado " +
                    "JOIN cliente ON cliente.rut_cliente = orden_de_compra.rut_cliente " +
                    "WHERE 1=1";
            
        if (nombre_cliente != null && !nombre_cliente.isEmpty()) {
            sql += " AND cliente.nombre ILIKE ?";
        }
            
        if (fechaInicial != null && fechaFinal != null) {
                sql += " AND orden_de_compra.fecha <= ? AND orden_compra.fecha >= ?";
        }
            
        if (numOrden != null && !numOrden.isEmpty()) {
            sql += " AND orden_de_compra.id_orden = ?";
        }

        // Preparar la declaración SQL
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                // Establecer los parámetros según los filtros proporcionados
            int parametroIndex = 1;

            if (nombre_cliente != null && !nombre_cliente.isEmpty()) {
                preparedStatement.setString(parametroIndex++, "%" + nombre_cliente + "%");
            }

            if (fechaInicial != null && fechaFinal != null) {
                preparedStatement.setDate(parametroIndex++, fechaInicial);
                preparedStatement.setDate(parametroIndex++, fechaFinal);
            }

            if (numOrden != null && !numOrden.isEmpty()) {
                preparedStatement.setInt(parametroIndex++, Integer.parseInt(numOrden));
            }

                // Ejecutar la consulta
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Object[]> rows = new ArrayList<>();
                
                while (resultSet.next()) {
                    int valorColumna1 = resultSet.getInt("id_orden");
                    Date valorColumna2 = resultSet.getDate("fecha_de_compra");
                    String valorColumna3 = resultSet.getString("nombre_empleado");
                    String valorColumna4 = resultSet.getString("nombre_cliente");
                      
                    Object[] row = {valorColumna1,valorColumna2,valorColumna3,valorColumna4};
                    rows.add(row);
                    System.out.println(row);
                    }                 
                
                return rows;
            }        
        }catch (SQLException e){
            System.out.println(e);
        }finally{
                closeConnection(conn);
        }
        
        
        return null;
    }
    
    
    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada exitosamente");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    
    
    
    
}
