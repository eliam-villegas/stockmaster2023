import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Connection conn;
        try {
            conn = DatabaseConnection.connect();
            // Hacer operaciones con la conexión
            SwingUtilities.invokeLater(() -> {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            loginFrame.setResizable(false);
            Inventory InvFrame = new Inventory();
            InvFrame.setVisible(true);
            });
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    DatabaseConnection.closeConnection(conn);
                }
            });

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
            
    }
}
