import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Inventory extends JFrame{

    private int mousex,mousey;
    
    public Inventory(){
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Color.white);
        add(background);

        title_bar(background);
        content(background);

        setLocationRelativeTo(null);
    }

    private void title_bar(JPanel background){
        
        JPanel titlebar = new JPanel(new GridBagLayout());
        titlebar.setBackground(Color.orange);

        GridBagConstraints constraintsTitleBar = new GridBagConstraints();
        constraintsTitleBar.gridx = 0;
        constraintsTitleBar.gridy = 0;
        constraintsTitleBar.weightx = 1;
        constraintsTitleBar.weighty = 1;
        constraintsTitleBar.fill = GridBagConstraints.BOTH;
        constraintsTitleBar.anchor = GridBagConstraints.NORTHWEST;
        background.add(titlebar,constraintsTitleBar);

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
        constraintsExitButton.gridx = 29;
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
        constraintsTitle.weightx = 28;
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

    private void content(JPanel background){

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.white);

        GridBagConstraints constraintsContent = new GridBagConstraints();
        constraintsContent.gridx = 0;
        constraintsContent.gridy = 1;
        constraintsContent.weightx = 1;
        constraintsContent.weighty = 60;
        constraintsContent.fill = GridBagConstraints.BOTH;
        constraintsContent.anchor = GridBagConstraints.NORTHWEST;
        background.add(content,constraintsContent);

        JLabel label = new JLabel("Inserte contenido del software aqui.");

        GridBagConstraints constraintsLabel = new GridBagConstraints();
        constraintsLabel.gridx = 0;
        constraintsLabel.gridy = 0;
        content.add(label,constraintsLabel);

    }
}
