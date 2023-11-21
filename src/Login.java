import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class Login extends JFrame{
    private int mousex,mousey;
    private JTextField userField;
    private JPasswordField passwordField;

    String rutEmpleado;
    String contrasenia;


    public Login() throws SQLException, ClassNotFoundException {
        //setSize(200,100);
        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        JPanel login_background = new JPanel(new GridBagLayout());
        add(login_background);

        titlebar(login_background);
        login_content(login_background);

        pack();
        setLocationRelativeTo(null);
    }

    private void titlebar(JPanel login_background){
        JPanel titlebar = new JPanel(new GridBagLayout());
        titlebar.setBackground(Color.orange);

        GridBagConstraints constraintsTitleBar = new GridBagConstraints();
        constraintsTitleBar.gridx = 0;
        constraintsTitleBar.gridy = 0;
        constraintsTitleBar.weightx = 1;
        constraintsTitleBar.weighty = 1;
        constraintsTitleBar.fill = GridBagConstraints.BOTH;
        constraintsTitleBar.anchor = GridBagConstraints.NORTHWEST;
        login_background.add(titlebar,constraintsTitleBar);

        titlebar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousex = e.getX();
                mousey = e.getY();
            }
        });
        titlebar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - mousex, y - mousey);
            }
        });

        title_elements(titlebar);
    }

    private void title_elements(JPanel titlebar){

        JLabel exit_button = new JLabel("X");
        exit_button.setOpaque(true);
        exit_button.setBackground(Color.orange);
        exit_button.setHorizontalAlignment(SwingConstants.CENTER);
        exit_button.setFont(new Font("Arial", Font.BOLD, 12));
        exit_button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        GridBagConstraints constraintsExitButton = new GridBagConstraints();
        constraintsExitButton.gridx = 9;
        constraintsExitButton.gridy = 0;
        constraintsExitButton.weightx = 1;
        constraintsExitButton.weighty = 1;
        constraintsExitButton.fill = GridBagConstraints.BOTH;
        constraintsExitButton.anchor = GridBagConstraints.CENTER;
        titlebar.add(exit_button,constraintsExitButton);

        exit_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // Cierra la ventana
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                exit_button.setBackground(Color.red); // Cambia el color cuando el mouse entra
                titlebar.setBackground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exit_button.setBackground(Color.orange); // Restablece el color cuando el mouse sale
                titlebar.setBackground(Color.orange);
            }
        });

        JLabel title = new JLabel("StockMaster APP");
        title.setOpaque(true);
        title.setBackground(Color.orange);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints constraintsTitle = new GridBagConstraints();
        constraintsTitle.gridx = 1;
        constraintsTitle.gridy = 0;
        constraintsTitle.weightx = 8;
        constraintsTitle.weighty = 1;
        constraintsTitle.fill = GridBagConstraints.BOTH;
        constraintsTitle.anchor = GridBagConstraints.NORTHWEST;
        titlebar.add(title,constraintsTitle);

        JLabel icon_image = new JLabel();
        icon_image.setOpaque(true);
        icon_image.setBackground(Color.orange);

        GridBagConstraints constraintsIcon = new GridBagConstraints();
        constraintsIcon.gridx = 0;
        constraintsIcon.gridy = 0;
        constraintsIcon.weightx = 1;
        constraintsIcon.weighty = 1;
        constraintsIcon.fill = GridBagConstraints.BOTH;
        constraintsIcon.anchor = GridBagConstraints.CENTER;
        titlebar.add(icon_image,constraintsIcon);
    }

    private void login_content(JPanel login_background) throws SQLException, ClassNotFoundException {

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.white);

        GridBagConstraints constraintsContent = new GridBagConstraints();
        constraintsContent.gridx = 0;
        constraintsContent.gridy = 1;
        constraintsContent.weightx = 1;
        constraintsContent.weighty = 1;
        constraintsContent.fill = GridBagConstraints.BOTH;
        constraintsContent.anchor = GridBagConstraints.NORTHWEST;

        content_elements(content);

        login_background.add(content,constraintsContent);

    }

    private void content_elements(JPanel content) throws ClassNotFoundException, SQLException {

        Connection conData;
        conData = DatabaseConnection.connect();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Nombre de user:");
        userField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField(20);

        JLabel loginButton = new JLabel(" Iniciar Sesión ");
        loginButton.setOpaque(true);
        loginButton.setBackground(Color.CYAN);
        loginButton.setForeground(Color.BLACK);
        loginButton.setBorder(new LineBorder(Color.BLACK, 2, true));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                rutEmpleado = userField.getText();
                contrasenia = new String(passwordField.getPassword());
                try {
                    PreparedStatement statement = conData.prepareStatement("SELECT rut_empleado, contrasenia FROM empleado WHERE rut_empleado = ? AND contrasenia = ?");
                    statement.setString(1, rutEmpleado);
                    statement.setString(2, contrasenia);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(Login.this, "Inicio de sesión exitoso");
                        dispose();
                        Inventory inventory = new Inventory();
                        inventory.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Inicio de sesión fallido. Verifica tus credenciales.");
                    }

                    resultSet.close();
                    statement.close();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add(userLabel, constraints);

        constraints.gridx = 1;
        content.add(userField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        content.add(passwordLabel, constraints);

        constraints.gridx = 1;
        content.add(passwordField, constraints);

        constraints.gridy = 2;
        constraints.gridwidth = 2;
        content.add(loginButton, constraints);
    }
}
