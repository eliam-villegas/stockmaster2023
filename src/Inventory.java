import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Inventory extends JFrame{

    private int mousex,mousey;
    
    private JPanel main_tab;
    private JPanel dashBoard_tab;
    private JPanel add_tab;

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

        //a partir de aqui se crea el menu y las pestañas de cada apartado del menu.
        JPanel menu = new JPanel(new GridBagLayout());
        menu.setBackground(Color.MAGENTA);

        GridBagConstraints constraintsMenu = new GridBagConstraints();
        constraintsMenu.gridx = 0;
        constraintsMenu.gridy = 0;
        constraintsMenu.weightx = 1;
        constraintsMenu.weighty = 60;
        constraintsMenu.fill = GridBagConstraints.BOTH;
        constraintsMenu.anchor = GridBagConstraints.NORTHWEST;
        content.add(menu,constraintsMenu);

        GridBagConstraints constraintsBotonMenu = new GridBagConstraints();
        constraintsBotonMenu.insets = new Insets(5, 5, 5, 5);
        constraintsBotonMenu.gridx = 0;
        constraintsBotonMenu.weightx = 1;
        constraintsBotonMenu.weighty = 1;
        constraintsBotonMenu.fill = GridBagConstraints.BOTH;
        constraintsBotonMenu.anchor = GridBagConstraints.NORTHWEST;

        JLabel botonInicio = new JLabel("Inicio");
        botonInicio.setOpaque(true);
        botonInicio.setBackground(Color.pink);
        botonInicio.setHorizontalAlignment(SwingConstants.CENTER);
        botonInicio.setFont(new Font("Arial", Font.BOLD, 12));
        botonInicio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonInicio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 1");
                hideOtherPanels(dashBoard_tab);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botonInicio.setBackground(Color.red); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonInicio.setBackground(Color.orange); // Restablece el color cuando el mouse sale
            }
        });

        constraintsBotonMenu.gridy = 0;
        menu.add(botonInicio,constraintsBotonMenu);

        JLabel botonAgregarProducto = new JLabel("Agregar Producto");
        botonAgregarProducto.setOpaque(true);
        botonAgregarProducto.setBackground(Color.pink);
        botonAgregarProducto.setHorizontalAlignment(SwingConstants.CENTER);
        botonAgregarProducto.setFont(new Font("Arial", Font.BOLD, 12));
        botonAgregarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonAgregarProducto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(main_tab,"opcion 2");
                hideOtherPanels(add_tab);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                botonAgregarProducto.setBackground(Color.red); // Cambia el color cuando el mouse entra
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonAgregarProducto.setBackground(Color.orange); // Restablece el color cuando el mouse sale
            }
        });

        constraintsBotonMenu.gridy = 1;
        menu.add(botonAgregarProducto,constraintsBotonMenu);

        JPanel espacio = new JPanel();
        espacio.setBackground(Color.MAGENTA);

        GridBagConstraints constraintsEspacio = new GridBagConstraints();
        constraintsEspacio.gridx = 0;
        constraintsEspacio.gridy = 3;
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

    private void hideOtherPanels(JPanel selectedPanel) {
        for (Component component : main_tab.getComponents()) {
            if (component instanceof JPanel && component != selectedPanel) {
                component.setVisible(false);
            }
        }
    }

    private void insertMainTab(JPanel main_tab,GridBagConstraints constraintsTab){
        dashBoard_tab = new JPanel();
        dashBoard_tab.setBackground(Color.white);
        main_tab.add(dashBoard_tab,"opcion 1");

        cardLayout.show(main_tab,"opcion 1");
        hideOtherPanels(dashBoard_tab);

    }

    private void insertAddTab(JPanel main_tab,GridBagConstraints constraintsTab){

        add_tab = new JPanel(new GridBagLayout());
        add_tab.setBackground(Color.white);

        GridBagConstraints constraintsElements = new GridBagConstraints();
        constraintsElements.fill = GridBagConstraints.CENTER;
        constraintsElements.anchor = GridBagConstraints.NORTHWEST;
        constraintsElements.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID producto:");
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JTextField idTextField = new JTextField(20);
        ((AbstractDocument) idTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JTextField nombreTextField = new JTextField(20);

        JLabel stockLabel = new JLabel("Stock Inicial:");
        JTextField stockTextField = new JTextField(10);
        ((AbstractDocument) stockTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel unidadMedidaLabel = new JLabel("Unidad de medida:");
        String[] unidadMedidaInsumo = {"Kilogramos", "Gramos", "Miligramos","Litros","Mililitros","Unidad"};
        JComboBox<String> unidadMedidaInsumoLista = new JComboBox<>(unidadMedidaInsumo);

        JLabel precioUnitarioLabel = new JLabel("Precio c/u:");
        JTextField precioUnitarioTextField = new JTextField(10);
        ((AbstractDocument) precioUnitarioTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel tipoLabel = new JLabel("Tipo de Insumo:");
        String[] tiposInsumo = {"Seco", "Congelado", "Plastico"};
        JComboBox<String> tipoComboBox = new JComboBox<>(tiposInsumo);

        JLabel boton_ingresar = new JLabel("Añadir");
        boton_ingresar.setOpaque(true);
        boton_ingresar.setBackground(Color.cyan);
        boton_ingresar.setBorder(new EmptyBorder(10, 20, 10, 20));
        boton_ingresar.setFont(new Font("Arial", Font.BOLD, 12));
        boton_ingresar.setHorizontalAlignment(SwingConstants.CENTER);
        boton_ingresar.setSize(30,40);
        boton_ingresar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        constraintsElements.gridx = 0;
        constraintsElements.gridy = 0;
        add_tab.add(idLabel,constraintsElements);

        constraintsElements.gridx = 1;
        constraintsElements.gridy = 0;
        add_tab.add(idTextField,constraintsElements);

        constraintsElements.gridx = 2;
        constraintsElements.gridy = 0;
        add_tab.add(nombreLabel,constraintsElements);

        constraintsElements.gridx = 3;
        add_tab.add(nombreTextField,constraintsElements);

        constraintsElements.gridx = 0;
        constraintsElements.gridy = 1;
        add_tab.add(stockLabel,constraintsElements);

        constraintsElements.gridx = 1;
        add_tab.add(stockTextField,constraintsElements);

        constraintsElements.gridx = 2;
        constraintsElements.gridy = 1;
        add_tab.add(precioUnitarioLabel,constraintsElements);

        constraintsElements.gridx = 3;
        add_tab.add(precioUnitarioTextField,constraintsElements);

        constraintsElements.gridx = 0;
        constraintsElements.gridy = 2;
        add_tab.add(tipoLabel,constraintsElements);

        constraintsElements.gridx = 1;
        add_tab.add(tipoComboBox,constraintsElements);

        constraintsElements.gridx = 2;
        constraintsElements.gridy = 2;
        add_tab.add(unidadMedidaLabel,constraintsElements);

        constraintsElements.gridx = 3;
        add_tab.add(unidadMedidaInsumoLista,constraintsElements);

        constraintsElements.gridx = 0;
        constraintsElements.gridy = 3;
        constraintsElements.fill = GridBagConstraints.CENTER;
        constraintsElements.anchor = GridBagConstraints.CENTER;
        add_tab.add(boton_ingresar,constraintsElements);

        boton_ingresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //evento con conexion a la base de datos...

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                boton_ingresar.setBackground(Color.blue); // Cambia el color cuando el mouse entra
                boton_ingresar.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton_ingresar.setBackground(Color.cyan); // Restablece el color cuando el mouse sale
                boton_ingresar.setForeground(Color.BLACK);
            }
        });

        main_tab.add(add_tab,"opcion 2");
    }
}
