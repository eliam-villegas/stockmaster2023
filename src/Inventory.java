import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Inventory extends JFrame{

    private int mousex,mousey;

    private JPanel main_tab;
    private JPanel dashBoard_tab;
     private Color colorNormal = Color.BLACK;
    private Color colorSeleccionado = Color.RED;
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
        constraintsTitleBar.weighty = 10;
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
        background.add(content,constraints(0, 1, 1, 60, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        //a partir de aqui se crea el menu y las pestañas de cada apartado del menu.
        JPanel menu = new JPanel(new GridBagLayout());
        menu.setBackground(new Color(51,51,51));
        content.add(menu,constraints(0, 0, 1, 60,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        JLabel label_menu = new JLabel("Menu de navegacion");
        label_menu.setOpaque(true);
        label_menu.setBackground(new Color(36,36,36));
        label_menu.setForeground(Color.white);
        label_menu.setFont(new Font("Arial", Font.BOLD, 12));
        label_menu.setHorizontalAlignment(SwingConstants.CENTER);
        menu.add(label_menu,constraints(0, 0, 1, 1,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        ImageIcon icon_dashboard = new ImageIcon("imagenes/dashboard.png");
        JLabel botonInicio = new JLabel(" Dashboard", icon_dashboard, JLabel.LEFT);
        botonInicio.setBorder(new EmptyBorder(0, 10, 0, 0));
        botonInicio.setOpaque(true);
        botonInicio.setBackground(new Color(51,51,51));
        botonInicio.setForeground(Color.white);
        botonInicio.setHorizontalAlignment(SwingConstants.LEFT);
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
        menu.add(botonInicio,constraints(0, 1, 1, 1,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        ImageIcon icon_producto = new ImageIcon("imagenes/menu_productos.png");
        JLabel botonProducto = new JLabel(" Productos", icon_producto, JLabel.LEFT);
        botonProducto.setBorder(new EmptyBorder(0, 10, 0, 0));
        botonProducto.setOpaque(true);
        botonProducto.setBackground(new Color(51,51,51));
        botonProducto.setForeground(Color.white);
        botonProducto.setHorizontalAlignment(SwingConstants.LEFT);
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
        botonProducto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                actualizarEstilo(botonProducto);
            }
        
            @Override
            public void focusLost(FocusEvent e) {
                actualizarEstilo(botonProducto);
            }
        });

        menu.add(botonProducto,constraints(0, 2, 1, 1,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        ImageIcon icon_compra = new ImageIcon("imagenes/compras.png");
        JLabel botonCompra = new JLabel(" Reposición", icon_compra, JLabel.LEFT);
        botonCompra.setBorder(new EmptyBorder(0, 10, 0, 0));
        botonCompra.setOpaque(true);
        botonCompra.setBackground(new Color(51,51,51));
        botonCompra.setForeground(Color.white);
        botonCompra.setHorizontalAlignment(SwingConstants.LEFT);
        botonCompra.setFont(new Font("Arial", Font.BOLD, 12));
        botonCompra.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCompra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 10");
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

        menu.add(botonCompra,constraints(0, 3, 1, 1,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        ImageIcon icon_ord_compra = new ImageIcon("imagenes/ordene_compras.png");
        JLabel botonOrdenCompra = new JLabel("Ords. Compra", icon_ord_compra, JLabel.LEFT);
        botonOrdenCompra.setBorder(new EmptyBorder(0, 10, 0, 0));
        botonOrdenCompra.setOpaque(true);
        botonOrdenCompra.setBackground(new Color(51,51,51));
        botonOrdenCompra.setForeground(Color.white);
        botonOrdenCompra.setHorizontalAlignment(SwingConstants.LEFT);
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

        
        botonOrdenCompra.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                actualizarEstilo(botonOrdenCompra);
            }

            @Override
            public void focusLost(FocusEvent e) {
                actualizarEstilo(botonOrdenCompra);
            }
        });

        menu.add(botonOrdenCompra,constraints(0, 4, 1, 1,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        ImageIcon icon_ventas = new ImageIcon("imagenes/ventas.png");
        JLabel botonVentas = new JLabel("Reg. Ventas", icon_ventas, JLabel.LEFT);
        botonVentas.setBorder(new EmptyBorder(0, 10, 0, 0));
        botonVentas.setOpaque(true);
        botonVentas.setBackground(new Color(51,51,51));
        botonVentas.setForeground(Color.white);
        botonVentas.setHorizontalAlignment(SwingConstants.LEFT);
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
        menu.add(botonVentas,constraints(0, 6, 1, 1,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));
        

        ImageIcon icon_proveedor = new ImageIcon("imagenes/proveedores.png");
        JLabel botonproveedor = new JLabel("Proveedores", icon_proveedor, JLabel.LEFT);
        botonproveedor.setBorder(new EmptyBorder(0, 10, 0, 0));
        botonproveedor.setOpaque(true);
        botonproveedor.setBackground(new Color(51,51,51));
        botonproveedor.setForeground(Color.white);
        botonproveedor.setHorizontalAlignment(SwingConstants.LEFT);
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
        menu.add(botonproveedor,constraints(0, 7, 1, 1,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));


        ImageIcon icon_clientes = new ImageIcon("imagenes/clientes.png");
        JLabel botoncliente = new JLabel("Clientes", icon_clientes, JLabel.LEFT);
        botoncliente.setBorder(new EmptyBorder(0, 10, 0, 0));
        botoncliente.setOpaque(true);
        botoncliente.setBackground(new Color(51,51,51));
        botoncliente.setForeground(Color.white);
        botoncliente.setHorizontalAlignment(SwingConstants.LEFT);
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
        menu.add(botoncliente,constraints(0, 8, 1, 1,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        ImageIcon icon_empleados = new ImageIcon("imagenes/empleados.png");
        JLabel botonempleados = new JLabel("Empleados", icon_empleados, JLabel.LEFT);
        botonempleados.setBorder(new EmptyBorder(0, 10, 0, 0));
        botonempleados.setOpaque(true);
        botonempleados.setBackground(new Color(51,51,51));
        botonempleados.setForeground(Color.white);
        botonempleados.setHorizontalAlignment(SwingConstants.LEFT);
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
        menu.add(botonempleados,constraints(0, 9, 1, 1,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));


        /*JLabel botonreportes = new JLabel("Reportes");
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
        });*/

        /*ImageIcon icon_despacho = new ImageIcon("imagenes/despacho.png");
        JLabel botondespacho = new JLabel("Despacho", icon_despacho, JLabel.LEFT);
        botondespacho.setBorder(new EmptyBorder(0, 10, 0, 0));
        botondespacho.setOpaque(true);
        botondespacho.setBackground(new Color(51,51,51));
        botondespacho.setForeground(Color.white);
        botondespacho.setHorizontalAlignment(SwingConstants.LEFT);
        botondespacho.setFont(new Font("Arial", Font.BOLD, 12));
        botondespacho.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botondespacho.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 5");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botondespacho.setBackground(Color.darkGray); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botondespacho.setBackground(new Color(51,51,51)); // Restablece el color cuando el mouse sale
            }
        });
        menu.add(botondespacho,constraints(0, 5, 1, 1,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));*/
        

        JPanel espacio = new JPanel();
        espacio.setBackground(new Color(51,51,51));
        menu.add(espacio,constraints(0, 10, 1, 10,GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));
        

        main_tab = new JPanel(cardLayout);
        main_tab.setBackground(Color.white);

        insertMainTab(main_tab,constraints(1, 0, 10, 60, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));
        insertAddTab(main_tab,constraints(1, 0, 10, 60, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));


        content.add(main_tab,constraints(1, 0, 10, 60, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));
    }

    private void insertMainTab(JPanel main_tab,GridBagConstraints constraintsTab){
        dashBoard_tab = new JPanel();
        dashBoard_tab.setBackground(Color.white);
        main_tab.add(dashBoard_tab,"opcion 1");

        cardLayout.show(main_tab,"opcion 1");

        /*JPanel panel = Graficos.createVentasPorClienteChart();
        JPanel panel2 = Graficos.graficoTorta();
        JPanel panel3 = Graficos.tablaStock();
        JPanel panel4 = Graficos.createChartPanel();

        dashBoard_tab.setLayout(new GridLayout(2, 2)); // Dos columnas

        dashBoard_tab.add(panel);
        dashBoard_tab.add(panel2);
        dashBoard_tab.add(panel3);
        dashBoard_tab.add(panel4);*/

    }

    private void insertAddTab(JPanel main_tab,GridBagConstraints constraintsTab){
        Stock_insumos stock_tab = new Stock_insumos();
        main_tab.add(stock_tab,"opcion 2");

        Orden_de_compra orden_compra_tab = new Orden_de_compra();
        main_tab.add(orden_compra_tab,"opcion 3");

        /*Despacho despacho_tab = new Despacho();
        main_tab.add(despacho_tab,"opcion 5");*/

        Clientes client_tab = new Clientes();
        main_tab.add(client_tab,"opcion 6");

        Empleados empleados_tab = new Empleados();
        main_tab.add(empleados_tab,"opcion 7");

        Registro_venta ventas_tab = new Registro_venta();
        main_tab.add(ventas_tab,"opcion 4");

        Registro_abastecimiento abastecimiento_tab  = new Registro_abastecimiento();
        main_tab.add(abastecimiento_tab,"opcion 10");

        Proveedores proveedores_tab = new Proveedores();
        main_tab.add(proveedores_tab,"opcion 5");

    }

    private GridBagConstraints constraints(int x,int y,int wx,int wy,int fill,int anchor){
        GridBagConstraints constraintsContent = new GridBagConstraints();
        constraintsContent.gridx = x;
        constraintsContent.gridy = y;
        constraintsContent.weightx = wx;
        constraintsContent.weighty = wy;
        constraintsContent.fill = fill;
        constraintsContent.anchor = anchor;
        return constraintsContent;
    }
    private void actualizarEstilo(JLabel label) {
        if (label.hasFocus()) {
            label.setForeground(colorSeleccionado);
        } else {
            label.setForeground(colorNormal);
        }
    }

}