import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

public class Clientes extends JPanel {

    private JTabbedPane pestanas = new JTabbedPane();
    public DefaultTableModel modelo;
    public JScrollPane tabla;
    public JTable tabla_real;

    public Clientes() {
        String[] columnas = {"Rut", "Nombre", "Numero Contacto", "Direccion"};
        modelo = new DefaultTableModel(null, columnas);

        setLayout(new GridBagLayout());
        setBackground(Color.white);

        tabla = create_table();
        tabla_real = getTableFromScrollPane(tabla);
        SearchbarClient searchbar = new SearchbarClient(modelo);

        crear_panel_agregar();
        crear_panel_modificar(tabla);
        crear_panel_eliminar(tabla);

        //pestanas.add(cardPanel);
        add(pestanas, gridBagConstraints(0, 0, 3, 1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        add(searchbar, gridBagConstraints(0, 1, 3, 1,0,0,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,0,5));
        add(tabla, gridBagConstraints(0, 2, 3, 1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,0,5,5,5));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        setVisible(true);
    }

    private GridBagConstraints gridBagConstraints(int x,int y,int gw,int gh,int wx,int wy,int fill,int anchor,int top,int left,int bottom,int right){
        GridBagConstraints constraintsElements = new GridBagConstraints();
        constraintsElements.fill = fill;
        constraintsElements.anchor = anchor;
        constraintsElements.weightx = wx;
        constraintsElements.weighty = wy;
        constraintsElements.insets = new Insets(top, left, bottom, right);
        constraintsElements.gridx = x;
        constraintsElements.gridy = y;
        constraintsElements.gridwidth = gw;
        constraintsElements.gridheight = gh;
        return constraintsElements;
    }

    private static JTable getTableFromScrollPane(JScrollPane scrollPane) {
        // Obtener la vista del JScrollPane y verificar si es un JTable
        Component view = scrollPane.getViewport().getView();
        if (view instanceof JTable) {
            return (JTable) view;
        }
        return null;
    }

    private JScrollPane create_table() {
        JTable tabla = new JTable(modelo);
        SearchbarClient.Buscar("", modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tabla_productos = new JScrollPane(tabla);

        return tabla_productos;
    }

    private void crear_panel_agregar() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.white);

        JLabel idLabel = new JLabel("Rut:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField idTextField = new JTextField(10);
        ((AbstractDocument) idTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField nombreTextField = new JTextField(20);

        JLabel cargoLabel = new JLabel("Numero Contacto:");
        JTextField cargoTextField = new JTextField(10);

        JLabel contrasenaLabel = new JLabel("Direccion:");
        JTextField contrasenaTextField = new JTextField(30);

        JButton boton_ingresar = new JButton("Ingresar");

        container.add(idLabel, gridBagConstraints(0, 0, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(idTextField, gridBagConstraints(1, 0, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(nombreLabel, gridBagConstraints(2, 0, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(nombreTextField, gridBagConstraints(3, 0, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(cargoLabel, gridBagConstraints(0, 1, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(cargoTextField, gridBagConstraints(1, 1, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(contrasenaLabel, gridBagConstraints(2, 1, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(contrasenaTextField, gridBagConstraints(3, 1, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(boton_ingresar, gridBagConstraints(0, 4, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.SOUTHWEST,5,5,5,5));

        pestanas.addTab("Agregar", container);

        boton_ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //aqui hace coneccion con la BD para ingresar los datos a la tabla.
                try {
                    var dbc2 = new DatabaseConnection();

                    dbc2.AgregarCliente(idTextField.getText(), nombreTextField.getText(), cargoTextField.getText(), contrasenaTextField.getText());
                    SearchbarClient.Buscar("", modelo);

                } catch (NumberFormatException x) {
                    JOptionPane.showMessageDialog(Clientes.this, "Atributos de ingreso vacios o no validos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }


    /*
        Crea el panel con las opcines para modificar un producto de la tabla
            -el boton guardar cambia los datos en la tabla y la base de datos
            -el de cancelar cancela la operacion y mantiene los valores de la tabla
     */
    private void crear_panel_modificar(JScrollPane tabla) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.white);

        JLabel idLabel = new JLabel("Rut:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField idTextField = new JTextField(20);
        ((AbstractDocument) idTextField.getDocument()).setDocumentFilter(new NumberFilter());
        idTextField.setEditable(false);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField nombreTextField = new JTextField(20);

        JLabel cargoLabel = new JLabel("Numero Contacto:");
        JTextField cargoTextField = new JTextField(10);

        JLabel contrasenaLabel = new JLabel("Direccion:");
        JTextField contrasenaTextField = new JTextField(30);

        JButton boton_guardar = new JButton("Guardar");

        container.add(idLabel, gridBagConstraints(0, 0, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(idTextField, gridBagConstraints(1, 0, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(nombreLabel, gridBagConstraints(2, 0, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(nombreTextField, gridBagConstraints(3, 0, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(cargoLabel, gridBagConstraints(0, 1, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(cargoTextField, gridBagConstraints(1, 1, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(contrasenaLabel, gridBagConstraints(2, 1, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(contrasenaTextField, gridBagConstraints(3, 1, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(boton_guardar, gridBagConstraints(0, 4, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.SOUTHWEST,5,5,5,5));

        pestanas.addTab("Modificar", container);

        JTable tabla_1 = getTableFromScrollPane(tabla);

        ListSelectionModel selectionModel = tabla_1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Verificar si la selección ha cambiado y no está en proceso de ajuste
                if (!e.getValueIsAdjusting()) {
                    // Obtener la fila seleccionada
                    int selectedRow = tabla_1.getSelectedRow();
                    if (selectedRow != -1) {
                        Object id = tabla_1.getValueAt(selectedRow, 0);
                        Object nombre = tabla_1.getValueAt(selectedRow, 1);
                        Object cargo = tabla_1.getValueAt(selectedRow, 2);
                        Object contrasena = tabla_1.getValueAt(selectedRow, 3);

                        idTextField.setText(id.toString());
                        nombreTextField.setText(nombre.toString());
                        cargoTextField.setText(cargo != null ? cargo.toString() : "");
                        contrasenaTextField.setText(contrasena != null ? contrasena.toString() : "");

                    }
                }
            }
        });

        boton_guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idtext = idTextField.getText();

                var dbc = new DatabaseConnection();
                if (idtext.isEmpty()) {
                    JOptionPane.showMessageDialog(Clientes.this, "Seleccione elemento a modificar.");
                } else {
                    dbc.ModificarCliente(idTextField.getText(), nombreTextField.getText(), cargoTextField.getText(), contrasenaTextField.getText());
                    SearchbarClient.Buscar("", modelo);
                }
            }
        });
    }

    private void crear_panel_eliminar(JScrollPane tabla) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.white);

        JLabel idLabel = new JLabel("Rut:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField idTextField = new JTextField(20);
        idTextField.setEditable(false);
        ((AbstractDocument) idTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField nombreTextField = new JTextField(20);
        nombreTextField.setEditable(false);

        JLabel cargoLabel = new JLabel("Numero Contacto:");
        JTextField cargoTextField = new JTextField(10);
        cargoTextField.setEditable(false);

        JLabel contrasenaLabel = new JLabel("Direccion:");
        JTextField contrasenaTextField = new JTextField(30);
        contrasenaTextField.setEditable(false);

        JButton boton_eliminar = new JButton("Eliminar");

        container.add(idLabel, gridBagConstraints(0, 0, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(idTextField, gridBagConstraints(1, 0, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(nombreLabel, gridBagConstraints(2, 0, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(nombreTextField, gridBagConstraints(3, 0, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(cargoLabel, gridBagConstraints(0, 1, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(cargoTextField, gridBagConstraints(1, 1, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(contrasenaLabel, gridBagConstraints(2, 1, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(contrasenaTextField, gridBagConstraints(3, 1, 1, 1,1,1,GridBagConstraints.HORIZONTAL,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(boton_eliminar, gridBagConstraints(0, 4, 1, 1,1,1,GridBagConstraints.NONE,GridBagConstraints.SOUTHWEST,5,5,5,5));

        pestanas.addTab("Eliminar", container);

        JTable tabla_1 = getTableFromScrollPane(tabla);

        ListSelectionModel selectionModel = tabla_1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Verificar si la selección ha cambiado y no está en proceso de ajuste
                if (!e.getValueIsAdjusting()) {
                    // Obtener la fila seleccionada
                    int selectedRow = tabla_1.getSelectedRow();
                    if (selectedRow != -1) {
                        Object id = tabla_1.getValueAt(selectedRow, 0);
                        Object nombre = tabla_1.getValueAt(selectedRow, 1);
                        Object cargo = tabla_1.getValueAt(selectedRow, 2);
                        Object contrasena = tabla_1.getValueAt(selectedRow, 3);

                        idTextField.setText(id.toString());
                        nombreTextField.setText(nombre.toString());
                        cargoTextField.setText(cargo != null ? cargo.toString() : "");
                        contrasenaTextField.setText(contrasena != null ? contrasena.toString() : "");

                    }
                }
            }
        });

        boton_eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idtext = idTextField.getText();

                var dbc = new DatabaseConnection();
                if (idtext.isEmpty()) {
                    JOptionPane.showMessageDialog(Clientes.this, "Seleccione elemento a eliminar.");
                } else {
                    // se elimina el producto
                    dbc.EliminarCliente(idTextField.getText());
                    SearchbarClient.Buscar("", modelo);
                }
            }
        });
    }

}