import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            loginFrame.setResizable(false);
        });
    }
}
