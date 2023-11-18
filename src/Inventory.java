import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Inventory extends JFrame{

    private int mousex,mousey;
    
    private JPanel main_tab;
    private JPanel dashBoard_tab;

    private CardLayout cardLayout = new CardLayout();
    
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
        titlebar.setBackground(new Color(70,130,180));
        
        GridBagConstraints constraintsTitleBar = new GridBagConstraints();
        constraintsTitleBar.gridx = 0;
        constraintsTitleBar.gridy = 0;
        constraintsTitleBar.weightx = 1;
        constraintsTitleBar.weighty = 5;
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
        exit_button.setForeground(Color.black);
        exit_button.setOpaque(true);
        exit_button.setBackground(new Color(70,130,180));
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
                exit_button.setBackground(new Color(70,130,180)); // Restablece el color cuando el mouse sale
                titlebar.setBackground(new Color(70,130,180));
            }
        });

        JLabel title = new JLabel("StockMaster APP");
        title.setForeground(Color.black);
        title.setOpaque(true);
        title.setBackground(new Color(70,130,180));
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
        icon_image.setBackground(new Color(70,130,180));

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

        //a partir de aqui se crea el menu y las pestañas de cada apartado del menu.
        JPanel menu = new JPanel(new GridBagLayout());
        menu.setBackground(new Color(51,51,51));

        GridBagConstraints constraintsMenu = new GridBagConstraints();
        constraintsMenu.gridx = 0;
        constraintsMenu.gridy = 0;
        constraintsMenu.weightx = 1;
        constraintsMenu.weighty = 60;
        constraintsMenu.fill = GridBagConstraints.BOTH;
        constraintsMenu.anchor = GridBagConstraints.NORTHWEST;
        content.add(menu,constraintsMenu);

        GridBagConstraints constraintsBotonMenu = new GridBagConstraints();
        constraintsBotonMenu.gridx = 0;
        constraintsBotonMenu.weightx = 1;
        constraintsBotonMenu.weighty = 1;
        constraintsBotonMenu.fill = GridBagConstraints.BOTH;
        constraintsBotonMenu.anchor = GridBagConstraints.NORTHWEST;

        JLabel botonInicio = new JLabel("Inicio");
        botonInicio.setOpaque(true);
        botonInicio.setBackground(new Color(51,51,51));
        botonInicio.setForeground(Color.white);
        botonInicio.setHorizontalAlignment(SwingConstants.CENTER);
        botonInicio.setFont(new Font("Arial", Font.BOLD, 12));
        botonInicio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonInicio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 1");
                //hideOtherPanels(dashBoard_tab);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botonInicio.setBackground(Color.darkGray); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonInicio.setBackground(new Color(51,51,51)); // Restablece el color cuando el mouse sale
            }
        });

        constraintsBotonMenu.gridy = 0;
        menu.add(botonInicio,constraintsBotonMenu);

        JLabel botonProducto = new JLabel("Productos");
        botonProducto.setOpaque(true);
        botonProducto.setBackground(new Color(51,51,51));
        botonProducto.setForeground(Color.white);
        botonProducto.setHorizontalAlignment(SwingConstants.CENTER);
        botonProducto.setFont(new Font("Arial", Font.BOLD, 12));
        botonProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonProducto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 2");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botonProducto.setBackground(Color.darkGray); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonProducto.setBackground(new Color(51,51,51)); // Restablece el color cuando el mouse sale
            }
        });

        constraintsBotonMenu.gridy = 1;
        menu.add(botonProducto,constraintsBotonMenu);

        JLabel botonCompra = new JLabel("Compras");
        botonCompra.setOpaque(true);
        botonCompra.setBackground(new Color(51,51,51));
        botonCompra.setForeground(Color.white);
        botonCompra.setHorizontalAlignment(SwingConstants.CENTER);
        botonCompra.setFont(new Font("Arial", Font.BOLD, 12));
        botonCompra.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCompra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 3");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botonCompra.setBackground(Color.darkGray); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonCompra.setBackground(new Color(51,51,51)); // Restablece el color cuando el mouse sale
            }
        });

        constraintsBotonMenu.gridy = 2;
        menu.add(botonCompra,constraintsBotonMenu);

        JLabel botonOrdenCompra = new JLabel("Ordenes de Compra");
        botonOrdenCompra.setOpaque(true);
        botonOrdenCompra.setBackground(new Color(51,51,51));
        botonOrdenCompra.setForeground(Color.white);
        botonOrdenCompra.setHorizontalAlignment(SwingConstants.CENTER);
        botonOrdenCompra.setFont(new Font("Arial", Font.BOLD, 12));
        botonOrdenCompra.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonOrdenCompra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 3");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botonOrdenCompra.setBackground(Color.darkGray); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonOrdenCompra.setBackground(new Color(51,51,51)); // Restablece el color cuando el mouse sale
            }
        });

        constraintsBotonMenu.gridy = 3;
        menu.add(botonOrdenCompra,constraintsBotonMenu);

        JLabel botonVentas = new JLabel("Registro de Ventas");
        botonVentas.setOpaque(true);
        botonVentas.setBackground(new Color(51,51,51));
        botonVentas.setForeground(Color.white);
        botonVentas.setHorizontalAlignment(SwingConstants.CENTER);
        botonVentas.setFont(new Font("Arial", Font.BOLD, 12));
        botonVentas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonVentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 4");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botonVentas.setBackground(Color.darkGray); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonVentas.setBackground(new Color(51,51,51)); // Restablece el color cuando el mouse sale
            }
        });

        constraintsBotonMenu.gridy = 4;
        menu.add(botonVentas,constraintsBotonMenu);

        JLabel botonproveedor = new JLabel("Proveedores");
        botonproveedor.setOpaque(true);
        botonproveedor.setBackground(new Color(51,51,51));
        botonproveedor.setForeground(Color.white);
        botonproveedor.setHorizontalAlignment(SwingConstants.CENTER);
        botonproveedor.setFont(new Font("Arial", Font.BOLD, 12));
        botonproveedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonproveedor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 5");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botonproveedor.setBackground(Color.darkGray); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonproveedor.setBackground(new Color(51,51,51)); // Restablece el color cuando el mouse sale
            }
        });

        constraintsBotonMenu.gridy = 5;
        menu.add(botonproveedor,constraintsBotonMenu);

        JLabel botoncliente = new JLabel("Clientes");
        botoncliente.setOpaque(true);
        botoncliente.setBackground(new Color(51,51,51));
        botoncliente.setForeground(Color.white);
        botoncliente.setHorizontalAlignment(SwingConstants.CENTER);
        botoncliente.setFont(new Font("Arial", Font.BOLD, 12));
        botoncliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botoncliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 6");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botoncliente.setBackground(Color.darkGray); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botoncliente.setBackground(new Color(51,51,51)); // Restablece el color cuando el mouse sale
            }
        });

        JLabel botonempleados = new JLabel("Empleados");
        botonempleados.setOpaque(true);
        botonempleados.setBackground(new Color(51,51,51));
        botonempleados.setForeground(Color.white);
        botonempleados.setHorizontalAlignment(SwingConstants.CENTER);
        botonempleados.setFont(new Font("Arial", Font.BOLD, 12));
        botonempleados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonempleados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 7");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botonempleados.setBackground(Color.darkGray); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonempleados.setBackground(new Color(51,51,51)); // Restablece el color cuando el mouse sale
            }
        });

        constraintsBotonMenu.gridy = 7;
        menu.add(botonempleados,constraintsBotonMenu);

        JLabel botonreportes = new JLabel("Reportes");
        botonreportes.setOpaque(true);
        botonreportes.setBackground(new Color(51,51,51));
        botonreportes.setForeground(Color.white);
        botonreportes.setHorizontalAlignment(SwingConstants.CENTER);
        botonreportes.setFont(new Font("Arial", Font.BOLD, 12));
        botonreportes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonreportes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 8");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botoncliente.setBackground(Color.darkGray); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botoncliente.setBackground(new Color(51,51,51)); // Restablece el color cuando el mouse sale
            }
        });

        constraintsBotonMenu.gridy = 8;
        menu.add(botoncliente,constraintsBotonMenu);


        JPanel espacio = new JPanel();
        espacio.setBackground(new Color(51,51,51));

        GridBagConstraints constraintsEspacio = new GridBagConstraints();
        constraintsEspacio.gridx = 0;
        constraintsEspacio.gridy = 9;
        constraintsEspacio.weightx = 1;
        constraintsEspacio.weighty = 10;
        constraintsEspacio.fill = GridBagConstraints.BOTH;
        constraintsEspacio.anchor = GridBagConstraints.NORTHWEST;
        menu.add(espacio,constraintsEspacio);

        GridBagConstraints constraintsTab = new GridBagConstraints();
        constraintsTab.gridx = 1;
        constraintsTab.gridy = 0;
        constraintsTab.weightx = 10;
        constraintsTab.weighty = 60;
        constraintsTab.fill = GridBagConstraints.BOTH;
        constraintsTab.anchor = GridBagConstraints.NORTHWEST;

        main_tab = new JPanel(cardLayout);
        main_tab.setBackground(Color.white);

        insertMainTab(main_tab,constraintsTab);
        insertAddTab(main_tab,constraintsTab);


        content.add(main_tab,constraintsTab);
    }

    private void insertMainTab(JPanel main_tab,GridBagConstraints constraintsTab){
        dashBoard_tab = new JPanel();
        dashBoard_tab.setBackground(Color.white);
        main_tab.add(dashBoard_tab,"opcion 1");

        cardLayout.show(main_tab,"opcion 1");

    }

    private void insertAddTab(JPanel main_tab,GridBagConstraints constraintsTab){
        Stock_insumos stock_tab = new Stock_insumos();
        main_tab.add(stock_tab,"opcion 2");

        Clientes client_tab = new Clientes();
        main_tab.add(client_tab,"opcion 6");

    }
}
