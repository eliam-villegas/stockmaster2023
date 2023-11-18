import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
    static String driver = "org.postgresql.Driver";
    static String dbname = "sushi";
    static String url = "jdbc:postgresql://10.4.3.195:5432/" + dbname;
    static String user = "sushi_dev";
    static String password = "5k4xFg6";

    public static void main(String[] args) throws SQLException {
        
        try {
            Class.forName(driver); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        
        try(Connection conData = DriverManager.getConnection(url, user, password)){
        
            //InsertRandomEmpleado(conData, 5); 
            //InsertRandomCliente(conData,25);
            //InsertRandomContactosCliente(conData,3);
            //InsertRandomDireccionContacto(conData,2);
            //InsertRandomProveedor(conData,15);
            //InsertRandomAbastecimiento(conData,50);
            //InsertRandomOrden(conData,70);
            //InsertRandomProducto(conData,40);
            InsertRandomRegistroAbastecimientoProducto(conData,10);
            InsertRandomRegistroVenta(conData,10);
            //InsertRandomRegistroDespacho(conData,10);
            //InsertRandomRegistroVentaDespacho(conData,10);
            //InsertRandomRegistroVentaProducto(conData,10);
            System.out.println("Se Inserto");
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
    
    }

    private static void InsertRandomRegistroVentaProducto(Connection conData, int i) {
    }

    private static void InsertRandomRegistroVentaDespacho(Connection conData, int i) {
    }

    public static void InsertRandomRegistroVenta(Connection connection, int numRecords) {

        Random random = new Random();

        String query = "INSERT INTO registro_de_venta (id_venta, fecha_pago, total, id_orden, iva , neto) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numRecords; i++) {
                int nOrden = random.nextInt(15);
                int saleId = generateUniqueSaleId(connection); // Generar un ID de venta único
                Date date = Date.valueOf(LocalDate.now().minusDays(random.nextInt(30))); // Fecha aleatoria en los últimos 30 días
                
                    // Obtener órdenes de compra existentes
                List<Integer> orderIds = getUnsoldOrderIds(connection, nOrden);

                // Calcular el total de la venta a partir de las órdenes de compra
                double neto = calculateTotalFromOrders(connection, orderIds);
                double iva = neto * 0.19;
                double total = neto + iva;
                while (checkSaleIdExistsInDatabase(connection, saleId)) {
                    // Si el id_venta ya existe, genera uno nuevo hasta encontrar uno que no exista
                    saleId = generateUniqueSaleId(connection);
                }

                try {
                    //statement.setInt(1, saleId);
                    //statement.setDate(2, date);
                    //statement.setDouble(3, total);

                    for (int x = 0; x < orderIds.size(); x++){
                        statement.setInt(1, saleId);
                        statement.setDate(2, date);
                        statement.setDouble(3, neto);
                        int aux = orderIds.get(x);
                        statement.setDouble(4, aux);
                        statement.setDouble(5, aux);
                        statement.setDouble(6, neto);

                        int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Registro de venta agregado: ID Venta: " + saleId + ", Fecha: " + date + ", Total: " + total + ", ID Orden: " + aux);
                    } else {
                        System.out.println("No se pudo agregar el registro de venta");
                    }
                    }
                    
                    
                } catch (SQLException e) {
                    System.out.println("Error al insertar registro de venta: " + e.getMessage());
                    // Realizar acciones de recuperación o registro de errores según sea necesario
                }
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
            // Realizar acciones de recuperación o registro de errores según sea necesario
        }
    }

    public static boolean checkSaleIdExistsInDatabase(Connection connection, int saleId) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM registro_de_venta WHERE id_venta = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, saleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0; // Devuelve verdadero si el id_venta existe en la base de datos
                }
            }
        }
        return false;
    }


    public static List<Integer> getUnsoldOrderIds(Connection connection, int i) {
        List<Integer> unsoldOrderIds = new ArrayList<>(i);
        String query = "SELECT id_orden FROM orden_de_compra WHERE id_orden NOT IN (SELECT id_orden FROM registro_de_venta)";
    
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                unsoldOrderIds.add(resultSet.getInt("id_orden"));
            }
        } catch (SQLException e) {
            System.out.println("Error al recuperar órdenes de compra no vendidas: " + e.getMessage());
            // Realizar acciones de recuperación o registro de errores según sea necesario
        }
    
        return unsoldOrderIds;
    }
    

    // Método para insertar empleados aleatorios
    public static void InsertRandomEmpleado(Connection connection, int numEmpleados) throws SQLException {
        Random random = new Random();
        String[] names = {"Empleado A", "Empleado B", "Empleado C", "Empleado D", "Empleado E", "Empleado F"};

        String query = "INSERT INTO empleado (rut_empleado, nombre, cargo, contrasenia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numEmpleados; i++) {
                String rut = generateUniqueRandomRutCliente(connection);
                String name = names[random.nextInt(names.length)] + i*(random.nextInt(100)) ;
                String role = "Role" + (random.nextInt(5));
                int contra = 12345; 

                statement.setString(1, rut);
                statement.setString(2, name);
                statement.setString(3, role);
                statement.setInt(4, contra);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Empleado agregado: RUT: " + rut + ", Nombre: " + name + ", Rol: " + role + ", Contraseña: " + contra);
                } else {
                    System.out.println("No se pudo agregar el empleado");
                }
            }
        }
    }
     // Método para crear e insertar clientes aleatorios
    public static void InsertRandomCliente(Connection connection, int numClients) throws SQLException {
        Random random = new Random();
        String[] names = {"Cliente A", "Cliente B", "Cliente C", "Cliente D", "Cliente E", "Cliente F"};

        String query = "INSERT INTO cliente (rut_cliente, nombre) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numClients; i++) {
                String rut = generateUniqueRandomRutCliente(connection);
                String name = names[random.nextInt(names.length)] + i*(random.nextInt(100));

                statement.setString(1, rut);
                statement.setString(2, name);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Cliente agregado: RUT: " + rut + ", Nombre: " + name);
                } else {
                    System.out.println("No se pudo agregar el cliente");
                }
            }
        }
    }
    public static String generateUniqueRandomRutCliente(Connection connection) throws SQLException {
        String rut;
        boolean rutExists;
        do {
            rut = generateRandomRut(); // Generar un RUT aleatorio
            rutExists = checkRutExistsProveedor(connection, rut); // Verificar si el RUT ya existe en la base de datos
        } while (rutExists);
        return rut;
    }
    
    public static boolean checkRutExistsCliente(Connection connection, String rut) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM cliente WHERE rut_cliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, rut);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt("count") > 0;
            }
        }
    }
    
    // Método para insertar contactos aleatorios
    public static void InsertRandomContactosCliente(Connection connection, int numContacts) throws SQLException {
        Random random = new Random();

        String query = "INSERT INTO contacto (telefono, rut_cliente) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numContacts; i++) {
                long phoneNumber = 100000000 + random.nextInt(900000000); // Números de teléfono aleatorios de 9 dígitos
                String clientRut = getRandomRutFromCliente(connection); // RUT de cliente ficticio

                statement.setLong(1, phoneNumber);
                statement.setString(2, clientRut);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Contacto agregado: Teléfono: " + phoneNumber + ", RUT Cliente: " + clientRut);
                } else {
                    System.out.println("No se pudo agregar el contacto");
                }
            }
        }
    }
    
    // Método para insertar direcciones aleatorias
    public static void InsertRandomDireccionContacto(Connection connection, int numAddresses) throws SQLException {

        String query = "INSERT INTO direcciones (direccion, rut_cliente) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numAddresses; i++) {
                String address = "Calle " + (100 + i) + ", #" + (10 + i); // Dirección ficticia
                String clientRut = getRandomRutFromCliente(connection); // RUT de cliente ficticio

                statement.setString(1, address);
                statement.setString(2, clientRut);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Dirección agregada: " + address + " - RUT Cliente: " + clientRut);
                } else {
                    System.out.println("No se pudo agregar la dirección");
                }
            }
        }
    }
    
     // Método para insertar proveedores aleatorios
    public static void InsertRandomProveedor(Connection connection, int numSuppliers) throws SQLException {
        
        Random random = new Random();
        String[] supplierNames = {"Proveedor A", "Proveedor B", "Proveedor C", "Proveedor D", "Proveedor E","Proveedor F"};

        String query = "INSERT INTO proveedor (rut_proveedor, nombre, telefono) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numSuppliers; i++) {
                String rutSupplier = generateUniqueRandomRutProveedor(connection);
                String supplierName = supplierNames[random.nextInt(supplierNames.length)] + i*(random.nextInt(100));
                long phoneNumber = 100000000 + random.nextInt(900000000);

                statement.setString(1, rutSupplier);
                statement.setString(2, supplierName);
                statement.setLong(2, phoneNumber);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Proveedor agregado: RUT: " + rutSupplier + ", Nombre: " + supplierName);
                } else {
                    System.out.println("No se pudo agregar el proveedor");
                }
            }
        }
    }
    
    public static String generateUniqueRandomRutProveedor(Connection connection) throws SQLException {
        String rut;
        boolean rutExists;
        do {
            rut = generateRandomRut(); // Generar un RUT aleatorio
            rutExists = checkRutExistsProveedor(connection, rut); // Verificar si el RUT ya existe en la base de datos
        } while (rutExists);
        return rut;
    }
    
    public static boolean checkRutExistsProveedor(Connection connection, String rut) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM proveedor WHERE rut_proveedor = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, rut);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt("count") > 0;
            }
        }
    }

    public static String generateRandomRut() {
        StringBuilder rut = new StringBuilder();
        Random random = new Random();
        rut.append(10000000 + random.nextInt(90000000)); // RUT aleatorio de 8 dígitos

        return rut.toString();
    }
    //generar e insertar registros de abastecimientos random
    public static void InsertRandomAbastecimiento(Connection connection, int numRecords) throws SQLException {
        Random random = new Random();
    
        String query = "INSERT INTO registro_abastecimiento (num_compra, fecha_de_compra, rut_empleado, rut_proveedor) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numRecords; i++) {
                int purchaseNumber = (1000 + i); // Números de compra ficticios
                Date date = Date.valueOf(LocalDate.now().minusDays(random.nextInt(30))); // Fecha aleatoria en los últimos 30 días
                String employeeRut = getRandomRutFromEmpleado(connection); // RUT de empleado ficticio
                String supplierRut = getRandomRutFromProveedor(connection); // RUT de proveedor ficticio
    
                // Verificar si el número de compra ya existe
                while (checkNumCompraExistsInDatabase(connection, purchaseNumber)) {
                    System.out.println("El número de compra " + purchaseNumber + " ya existe. Saltando inserción.");
                    purchaseNumber=purchaseNumber+1;
                    continue;  // Saltar a la próxima iteración del bucle
                }
    
                statement.setInt(1, purchaseNumber);
                statement.setDate(2, date);
                statement.setString(3, employeeRut);
                statement.setString(4, supplierRut);
    
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Registro de abastecimiento agregado: Número Compra: " + purchaseNumber + ", Fecha: " + date + ", RUT Empleado: " + employeeRut + ", RUT Proveedor: " + supplierRut);
                } else {
                    System.out.println("No se pudo agregar el registro de abastecimiento");
                }
            }
        }
    }
    
    public static boolean checkNumCompraExistsInDatabase(Connection connection, int numCompra) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM registro_abastecimiento WHERE num_compra = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, numCompra);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt("count") > 0;
            }
        }
    }
    

 
     // Método para insertar órdenes aleatorias
    public static void InsertRandomOrden(Connection connection, int numOrders) throws SQLException {
        Random random = new Random();

        String query = "INSERT INTO orden_de_compra (id_orden, fecha_de_compra, subtotal, rut_empleado, rut_cliente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numOrders; i++) {
                int orderId = getUniqueRandomIdOrden(connection);
                Date date = Date.valueOf(LocalDate.now().minusDays(random.nextInt(90))); // Fecha aleatoria en los últimos 90 días
                double subtotal = 100 + random.nextInt(10000); // Subtotal entre 100 y 10099
                String employeeRut = getRandomRutFromEmpleado(connection);
                String clientRut = getRandomRutFromCliente(connection);

                statement.setInt(1, orderId);
                statement.setDate(2, date);
                statement.setDouble(3, subtotal);
                statement.setString(4, employeeRut);
                statement.setString(5, clientRut);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Orden de compra agregada: ID: " + orderId + ", Fecha: " + date + ", Subtotal: " + subtotal + ", Rut Empleado: " + employeeRut + ", Rut Cliente: " + clientRut);
                } else {
                    System.out.println("No se pudo agregar la orden de compra");
                }
            }
        }
    }
    
    public static void InsertRandomProducto(Connection connection, int numProducts) throws SQLException {
        Random random = new Random();
        String[] productNames = {"Producto A", "Producto B", "Producto C", "Producto D", "Producto E", "Producto F", "Producto G", "Producto H", "Producto I", "Producto J", "Producto K"};
        String[] units = {"Unidad", "Kilogramo", "Litro", "Lote"};
        String[] types = {"Congelado", "Salsa", "Alimento Perecible", "Alimento no Perecible", "Utensilios Plasticos"};

        String query = "INSERT INTO producto (id_producto, nombre_producto, stock, precio_unitario, unidad_de_medida, fecha_elaboracion, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numProducts; i++) {
                int productId = getUniqueRandomIdProducto(connection);
                String productName = productNames[random.nextInt(productNames.length)] + (random.nextInt(100)) ;
                int stock = 10 + random.nextInt(100); // Stock entre 10 y 109
                double unitPrice = 10 + random.nextInt(900); // Precio unitario entre 10 y 990
                String unit = units[random.nextInt(units.length)];
                Date date = Date.valueOf(LocalDate.now().minusDays(random.nextInt(30))); // Fecha aleatoria en los últimos 30 días
                String type = types[random.nextInt(types.length)];

                statement.setInt(1, productId);
                statement.setString(2, productName);
                statement.setInt(3, stock);
                statement.setDouble(4, unitPrice);
                statement.setString(5, unit);
                statement.setDate(6, date);
                statement.setString(7, type);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Producto agregado: ID: " + productId + ", Nombre: " + productName + ", Stock: " + stock + ", Precio Unitario: " + unitPrice + ", Unidad: " + unit + ", Fecha Elaboración: " + date + ", Tipo: " + type);
                } else {
                    System.out.println("No se pudo agregar el producto");
                }
            }
        }
    }
    public static Integer getUniqueRandomIdProducto(Connection connection) throws SQLException {
        Random random = new Random();
        int maxAttempts = 100;  // Límite de intentos para evitar un bucle infinito
        int attemptCount = 0;
    
        while (attemptCount < maxAttempts) {
            int potentialIdProducto = 1 + random.nextInt(Integer.MAX_VALUE);  // Genera un ID aleatorio
    
            if (!checkIdProductoExistsInDatabase(connection, potentialIdProducto)) {
                // El ID generado no existe, es único
                return potentialIdProducto;
            }
    
            // El ID generado ya existe, intenta nuevamente
            attemptCount++;
        }
    
        throw new SQLException("No se pudo generar un ID único después de " + maxAttempts + " intentos.");
    }
    
    public static boolean checkIdProductoExistsInDatabase(Connection connection, int idProducto) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM producto WHERE id_producto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idProducto);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt("count") > 0;
            }
        }
    }
    
    
    public static void InsertRandomRegistroAbastecimientoProducto(Connection connection, int numRecords) throws SQLException {
        Random random = new Random();
    
        String query = "INSERT INTO registro_abastecimiento_contiene_producto (num_compra, id_producto, cantidad, precio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numRecords; i++) {
                int purchaseNumber = getRandomNumCompraFromRegistro_abastecimiento(connection); // Número de compra ficticio
                int productId = getRandomidProductoFromProducto(connection); // ID de producto ficticio
                int quantity = 1 + random.nextInt(20); // Cantidad entre 1 y 20
                int prices = 100 + random.nextInt(999);
    
                // Verificar si el número de compra existe en la tabla registro_abastecimiento
                while (!checkNumCompraExistsInDatabase(connection, purchaseNumber)) {
                    System.out.println("El número de compra " + purchaseNumber + " no existe. Generando uno nuevo.");
                    purchaseNumber = getRandomNumCompraFromRegistro_abastecimiento(connection);
                }
    
                // Insertar el registro en la tabla registro_abastecimiento_contiene_producto
                statement.setInt(1, purchaseNumber);
                statement.setInt(2, productId);
                statement.setInt(3, quantity);
                statement.setInt(4, prices);
                int rowsInserted = statement.executeUpdate();
    
                if (rowsInserted > 0) {
                    System.out.println("Registro de abastecimiento de producto agregado: Num. Compra: " + purchaseNumber + ", ID Producto: " + productId + ", Cantidad: " + quantity);
                } else {
                    System.out.println("No se pudo agregar el registro de abastecimiento de producto");
                }
            }
        }
    }

    public static boolean checkDuplicate(Connection connection, int numCompra, int idProducto) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM registro_abastecimiento_contiene_producto WHERE num_compra = ? AND id_producto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, numCompra);
            statement.setInt(2, idProducto);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt("count") > 0;
            }
        }
    }
    
    
        
     // Método para insertar registros de venta aleatorios
    public static void InsertRandomRegistroDespacho(Connection connection, int numRecords) throws SQLException {

    String query = "INSERT INTO registro_despacho (num_despacho, fecha, receptor, rut_empleado, direccion) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        for (int i = 0; i < numRecords; i++) {
            int numDespacho = 1000 + i; // IDs de despacho ficticios
            Date date = Date.valueOf("2023-11-03"); // Fecha ficticia
            String receptor = getRandomReceptor();
            String employeeRut = getRandomRutFromEmpleado(connection);
            String direccion = null;

            statement.setInt(1, numDespacho);
            statement.setDate(2, date);
            statement.setString(3, receptor);
            statement.setString(4, employeeRut);
            statement.setString(5, direccion);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Registro de despacho agregado: Número de Despacho: " + numDespacho + ", Fecha: " + date + ", Receptor: " + receptor + ", RUT Empleado: " + employeeRut + ", Dirección: " + direccion);
            } else {
                System.out.println("No se pudo agregar el registro de despacho");
            }
        }
    }
}
    
    // Método de ejemplo para obtener un receptor aleatorio
    public static String getRandomReceptor() {
        String[] receptores = {"Juan Perez", "Humberto Suazo", "Daniel Rojas", "Elsa Pato", "Elvis Tec", "Matias Queroso", "Zacarias Flores del Campo", "Lola Mento"};
        return receptores[new Random().nextInt(receptores.length)];
    }
    
    // Método para obtener un ID de venta único
public static int getUniqueRandomIdVenta(Connection connection) throws SQLException {
    Random random = new Random();
    int maxAttempts = 100;  // Límite de intentos para evitar un bucle infinito
    int attemptCount = 0;

    while (attemptCount < maxAttempts) {
        int potentialIdVenta = 1 + random.nextInt(Integer.MAX_VALUE);  // Genera un ID aleatorio

        if (!checkIdVentaExistsInDatabase(connection, potentialIdVenta)) {
            // El ID generado no existe, es único
            return potentialIdVenta;
        }

        // El ID generado ya existe, intenta nuevamente
        attemptCount++;
    }

    throw new SQLException("No se pudo generar un ID de venta único después de " + maxAttempts + " intentos.");
}
// Método para verificar si un ID de venta existe en la base de datos
    public static boolean checkIdVentaExistsInDatabase(Connection connection, int idVenta) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM registro_de_venta WHERE id_venta = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idVenta);
            try (var resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt("count") > 0;
            }
        }
    }
    
    public static int generateUniqueSaleId(Connection connection) throws SQLException {
    int saleId;
    do {
        saleId = 1000 + new Random().nextInt(1000); // Genera un ID de venta ficticio
    } while (saleIdExistsInRegistroDeVenta(connection, saleId));
    return saleId;
}

    public static boolean saleIdExistsInRegistroDeVenta(Connection connection, int saleId) throws SQLException {
        String query = "SELECT id_venta FROM registro_de_venta WHERE id_venta = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, saleId);
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next(); // Devuelve true si el ID de venta existe en la tabla registro_de_venta
        }
    }
}
    
    
    // Método para obtener los IDs de orden desde una tabla
    public static int[] getOrderIdsFromIdOrden(Connection connection) throws SQLException {
    List<Integer> orderIds = new ArrayList<>();

    String query = "SELECT id_orden FROM orden_de_compra";
    String selectQuery = "SELECT id_orden FROM registro_de_venta WHERE id_orden = ?";
    
    try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            int orderId = resultSet.getInt("id_orden");
            
            // Verificar si el id_orden ya tiene un registro de venta asignado
            selectStatement.setInt(1, orderId);
            try (ResultSet selectResultSet = selectStatement.executeQuery()) {
                if (!selectResultSet.next()) {
                    orderIds.add(orderId); // Si no hay registro de venta para esta orden, agregarla a la lista
                }
            }
        }
    }

    return orderIds.stream().mapToInt(i -> i).toArray(); // Convertir la lista a un array de enteros
}

    // Método para calcular el total a partir de las órdenes
    public static int calculateTotalFromOrders(Connection connection, List<Integer> orderIds) throws SQLException {
        int total=0;
        String query = "SELECT subtotal FROM orden_de_compra WHERE id_orden = ?";

        for (int orderId : orderIds) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, orderId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        total += resultSet.getInt("subtotal");
                    }
                }
            }
        }
            return total;
    }

    // Métodos para obtener un valor aleatorio de una tabla existente
    public static String getRandomRutFromEmpleado(Connection connection) throws SQLException {
        String query = "SELECT rut_empleado FROM empleado ORDER BY random() LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("rut_empleado");
            }
        }
        return "RUT_NO_ENCONTRADO"; // Si no se encuentra un RUT
    }
    
    
    public static String getRandomRutFromCliente(Connection connection) throws SQLException {
        String query = "SELECT rut_cliente FROM cliente ORDER BY random() LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("rut_cliente");
            }
        }
        return "RUT_NO_ENCONTRADO"; // Si no se encuentra un RUT
    }
    
    
    public static String getRandomRutFromProveedor(Connection connection) throws SQLException {
        String query = "SELECT rut_proveedor FROM proveedor ORDER BY random() LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("rut_proveedor");
            }
        }
        return "RUT_NO_ENCONTRADO"; // Si no se encuentra un RUT
    }
    
    
    public static Integer getRandomNumCompraFromRegistro_abastecimiento(Connection connection) throws SQLException {
        String query = "SELECT num_compra FROM registro_abastecimiento ORDER BY RANDOM() LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("num_compra");
            }
        }
        return -1; // Si no se encuentra un numero de compra
    }
   
    
    public static Integer getUniqueRandomIdOrden(Connection connection) throws SQLException {
        Random random = new Random();
        int maxAttempts = 100;  // Límite de intentos para evitar un bucle infinito
        int attemptCount = 0;
    
        while (attemptCount < maxAttempts) {
            int potentialIdOrden = 1 + random.nextInt(Integer.MAX_VALUE);  // Genera un ID aleatorio
    
            if (!checkIdOrdenExistsInDatabase(connection, potentialIdOrden)) {
                // El ID generado no existe, es único
                return potentialIdOrden;
            }
    
            // El ID generado ya existe, intenta nuevamente
            attemptCount++;
        }
    
        throw new SQLException("No se pudo generar un ID único después de " + maxAttempts + " intentos.");
    }
    
    
    public static boolean checkIdOrdenExistsInDatabase(Connection connection, int idOrden) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM orden_de_compra WHERE id_orden = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idOrden);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt("count") > 0;
            }
        }
    }
     
    
    public static int getRandomidProductoFromProducto(Connection connection) throws SQLException {
        int RandomId = -1;// -1 representa que no se a encontrado valores
        String query = "SELECT id_producto FROM producto ORDER BY random() LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                RandomId = resultSet.getInt("id_producto");
            }
        }catch (SQLException e) {
        // Manejo adecuado de la excepción
        e.printStackTrace();
    }
    return RandomId; 
    }
     
    
    public static int getRandomidOrdenFromOrden(Connection connection) throws SQLException {
        String query = "SELECT id_orden FROM orden ORDER BY random() LIMIT 1";
        int RandomId = -1;
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                 RandomId = resultSet.getInt("id_orden");
            }
        } catch (SQLException e) {
        // Manejo adecuado de la excepción
        e.printStackTrace();
    }
    return RandomId;   
    }    
}      