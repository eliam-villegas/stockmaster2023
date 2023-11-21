import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) throws Exception {

        Login loginFrame = null;
            try {
                loginFrame = new Login();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            loginFrame.setVisible(true);
            loginFrame.setResizable(false);


    }
}
