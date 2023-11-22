import java.sql.Connection;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) throws Exception {

            var dbc = new DatabaseConnection();
            //dbc.AgregarProveedor("proveedor test", "751155223","+569123456");
            //dbc.BuscarOrdenDeCompra(null, null, null,null, "389003303");
            //dbc.BuscarRegistroAbastecimiento(null, null, null,null, "1047");
            //dbc.ObtenenDeCompraDetalles("389003303");
            //dbc.ObtenenRegistroAbastecimientoDetalles("1047");
            dbc.ObtenerVentasPor(DatabaseConnection.VENTAS_POR.cliente, "f", null, null, DatabaseConnection.ORDER_BY.DESC);
            
            // Hacer operaciones con la conexión
            SwingUtilities.invokeLater(() -> {
            //Login loginFrame = new Login();
            //loginFrame.setVisible(true);
            //loginFrame.setResizable(false);
            Inventory InvFrame = new Inventory();
            InvFrame.setVisible(true);
            });

            
    }
}
