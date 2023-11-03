import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            loginFrame.setResizable(false);
        });
    }
}
