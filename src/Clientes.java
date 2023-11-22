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

public class Clientes extends JPanel{

    private CardLayout cardLayout = new CardLayout();


    public Clientes(){
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

        JLabel RUTLabel = new JLabel("RUT:");
        //idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        RUTLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField RUTTextField = new JTextField(20);
        ((AbstractDocument) RUTTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel nombreLabel = new JLabel("Nombre:");
        //nombreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField nombreTextField = new JTextField(20);



        JButton boton_ingresar = new JButton("Ingresar");

        container.add(RUTLabel,gridBagConstraints(0,0,1,1));
        container.add(RUTTextField,gridBagConstraints(1,0,1,1));

        container.add(nombreLabel,gridBagConstraints(2,0,1,1));
        container.add(nombreTextField,gridBagConstraints(3,0,1,1));



        container.add(boton_ingresar,gridBagConstraints(0,4,1,1));

        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.add(container,"opcion 1");
        cardPanel.add(container1,"opcion 2");
        cardPanel.add(container2,"opcion 3");

        SearchbarClient searchbar = new SearchbarClient();

        add(cardPanel,gridBagConstraints(0,0,3,1));
        add(searchbar,gridBagConstraints(0, 1,3,1));
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

    private JScrollPane create_table() {
        String[] columnas = {"Rut", "Nombre Cliente", "Número Contacto", "Dirección"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        try {
            // Conectar a la base de datos
            Connection conn = DatabaseConnection.Getconnection();

            // Ejecutar la consulta SQL para obtener los datos de clientes y sus contactos y direcciones
            String query = "SELECT cliente.rut_cliente as rut, cliente.nombre AS nombre_cliente, " +
                    "contacto.telefono AS numero_contacto, direcciones.direccion AS direccion_cliente " +
                    "FROM cliente " +
                    "LEFT JOIN contacto ON contacto.rut_cliente = cliente.rut_cliente " +
                    "LEFT JOIN direcciones ON direcciones.rut_cliente = cliente.rut_cliente";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Procesar los resultados y añadirlos al modelo de la tabla
            while (rs.next()) {
                Object[] fila = {
                        rs.getString("rut"),
                        rs.getString("nombre_cliente"),
                        rs.getString("numero_contacto"),
                        rs.getString("direccion_cliente")
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
        JScrollPane tablaClientes = new JScrollPane(tabla);
        return tablaClientes;
    }




}