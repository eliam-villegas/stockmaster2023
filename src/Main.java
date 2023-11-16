import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) throws Exception {

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
