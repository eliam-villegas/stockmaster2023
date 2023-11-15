import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

public class Stock_insumos extends JPanel{

    private CardLayout cardLayout = new CardLayout();
    

    public Stock_insumos(){
        setLayout(new GridBagLayout());
        setBackground(Color.white);

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.white);
        container.setBorder(BorderFactory.createTitledBorder("Agregar"));

        JPanel container1 = new JPanel(new GridBagLayout());
        container1.setBackground(Color.white);
        container1.setBorder(BorderFactory.createTitledBorder("Actualizar"));

        JPanel container2 = new JPanel(new GridBagLayout());
        container2.setBackground(Color.white);
        container2.setBorder(BorderFactory.createTitledBorder("Eliminar"));

        JLabel idLabel = new JLabel("ID producto:");
        //idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField idTextField = new JTextField(20);
        ((AbstractDocument) idTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel nombreLabel = new JLabel("Nombre:");
        //nombreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField nombreTextField = new JTextField(20);

        JLabel stockLabel = new JLabel("Stock Inicial:");
        JTextField stockTextField = new JTextField(10);
        ((AbstractDocument) stockTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel unidadMedidaLabel = new JLabel("Unidad de medida:");
        String[] unidadMedidaInsumo = {"Kilogramos", "Gramos", "Miligramos","Litros","Mililitros","Unidad","lote"};
        JComboBox<String> unidadMedidaInsumoLista = new JComboBox<>(unidadMedidaInsumo);

        JLabel precioUnitarioLabel = new JLabel("Precio c/u:");
        JTextField precioUnitarioTextField = new JTextField(10);
        ((AbstractDocument) precioUnitarioTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel tipoLabel = new JLabel("Tipo de Insumo:");
        String[] tiposInsumo = {"Perecible", "No perecible", "Seco", "Congelado", "Plastico"};
        JComboBox<String> tipoComboBox = new JComboBox<>(tiposInsumo);

        JButton boton_ingresar = new JButton("Ingresar");

        container.add(idLabel,gridBagConstraints(0,0,1,1));
        container.add(idTextField,gridBagConstraints(1,0,1,1));

        container.add(nombreLabel,gridBagConstraints(2,0,1,1));
        container.add(nombreTextField,gridBagConstraints(3,0,1,1));

        container.add(stockLabel,gridBagConstraints(0,1,1,1));
        container.add(stockTextField,gridBagConstraints(1,1,1,1));

        container.add(precioUnitarioLabel,gridBagConstraints(2,1,1,1));
        container.add(precioUnitarioTextField,gridBagConstraints(3,1,1,1));

        container.add(tipoLabel,gridBagConstraints(0,2,1,1));
        container.add(tipoComboBox,gridBagConstraints(1,2,1,1));

        container.add(unidadMedidaLabel,gridBagConstraints(2,2,1,1));
        container.add(unidadMedidaInsumoLista,gridBagConstraints(3,2,1,1));

        container.add(boton_ingresar,gridBagConstraints(0,4,1,1));

        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.add(container,"opcion 1");
        cardPanel.add(container1,"opcion 2");
        cardPanel.add(container2,"opcion 3");

        SearchbarProduct searchbarproducto = new SearchbarProduct();

        add(cardPanel,gridBagConstraints(0,0,3,1));
        add(searchbarproducto,gridBagConstraints(0, 1,3,1));
        add(create_table(),gridBagConstraints(0, 2,3,1));

        JPanel opciones = new JPanel(new GridBagLayout());
        opciones.setBackground(Color.white);
        opciones.setBorder(BorderFactory.createTitledBorder("Opciones"));

        JButton agregar = new JButton("Agregar");
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "opcion 1");
            }
        });
        JButton actualizar = new JButton("Actualizar");
        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "opcion 2");
            }
        });
        JButton eliminar = new JButton("Eliminar");
        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "opcion 3");
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        opciones.add(agregar,constraints);
        constraints.gridx = 1;
        opciones.add(actualizar,constraints);
        constraints.gridx = 2;
        opciones.add(eliminar,constraints);

        add(opciones,gridBagConstraints(0, 3, 3, 1));

        boton_ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //aqui hace coneccion con la BD para ingresar los datos a la tabla.
            }
        });

        setVisible(true);
    }

    private GridBagConstraints gridBagConstraints(int x,int y,int gw,int gh){
        GridBagConstraints constraintsElements = new GridBagConstraints();
        constraintsElements.fill = GridBagConstraints.BOTH;
        constraintsElements.anchor = GridBagConstraints.NORTHWEST;
        constraintsElements.weightx = 1;
        constraintsElements.insets = new Insets(5, 10, 10, 5);
        constraintsElements.gridx = x;
        constraintsElements.gridy = y;
        constraintsElements.gridwidth = gw;
        constraintsElements.gridheight = gh;
        return constraintsElements;
    }

    private JScrollPane create_table(){

        String[] columnas = {"Id", "Nombre", "Stock", "Precio unitario", "Tipo", "Unidad de medida"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        try {
            // Conectar a la base de datos
            Connection conn = DatabaseConnection.connect();

            // Ejecutar la consulta SQL para obtener los datos
            String query = "SELECT id_producto, nombre_producto, stock, precio_unitario, tipo, unidad_de_medida FROM producto";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Procesar los resultados y añadirlos al modelo de la tabla
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("stock"),
                    rs.getString("precio_unitario"),
                    rs.getString("tipo"),
                    rs.getString("unidad_de_medida")
                };
                modelo.addRow(fila);
            }

            // Cerrar recursos
            rs.close();
            stmt.close();
            

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción adecuadamente
        }

        JTable tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        JScrollPane tablaProductos = new JScrollPane(tabla);
        return tablaProductos;
    }
    
}
