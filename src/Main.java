import javax.swing.SwingUtilities;
import java.sql.*;
public class Main {

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> {
            Login loginFrame;

            try {
                loginFrame = new Login();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            loginFrame.setVisible(true);
            loginFrame.setResizable(false);
        });

    }

}
