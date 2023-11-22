import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {

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


    }
}