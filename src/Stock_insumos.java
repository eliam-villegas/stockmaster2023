import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;


public class Stock_insumos extends JPanel{

    //private CardLayout cardLayout = new CardLayout();
    public DefaultTableModel modelo;
    public JScrollPane tabla;
    public JTable tabla_real;

    //private JPanel cardPanel= new JPanel(cardLayout);;
    private JTabbedPane pestanas = new JTabbedPane();
    
    public Stock_insumos(){
        
        String[] columnas = {"Id", "Nombre", "Stock","Precio unitario","Tipo","Unidad de medida"};
        modelo = new DefaultTableModel(null, columnas);
        
        setLayout(new GridBagLayout());
        setBackground(Color.white);

        tabla = create_table();
        tabla_real = getTableFromScrollPane(tabla);
        Searchbar searchbar = new Searchbar(modelo);
        TableColumn cantidadColumn = tabla_real.getColumn("Stock");
        cantidadColumn.setCellRenderer(new ColorRenderer());

        crear_panel_agregar();
        crear_panel_modificar(tabla);
        crear_panel_eliminar(tabla);
        
        //pestanas.add(cardPanel);
        add(pestanas,gridBagConstraints(0,0,3,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        
        
        add(searchbar,gridBagConstraints(0, 1,3,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        add(tabla,gridBagConstraints(0, 2,3,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));


        

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



    //la funcion crea el panel para ingresar productos a la base de datos
    //los nuevos productos seran visibles en la tabla actualizada
    private void crear_panel_agregar(){
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.white);
        container.setBorder(BorderFactory.createTitledBorder("Agregar"));

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
        String[] unidadMedidaInsumo = {"Kilogramos", "Gramos", "Miligramos","Litros","Mililitros","Unidad","Lote"};
        JComboBox<String> unidadMedidaInsumoLista = new JComboBox<>(unidadMedidaInsumo);

        JLabel precioUnitarioLabel = new JLabel("Precio c/u:");
        JTextField precioUnitarioTextField = new JTextField(10);
        ((AbstractDocument) precioUnitarioTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel tipoLabel = new JLabel("Tipo de Insumo:");
        String[] tiposInsumo = {"Alimento Perecible", "Alimento no Perecible", "Salsa","Utensilios Plasticos","Congelado","Seco"};
        JComboBox<String> tipoComboBox = new JComboBox<>(tiposInsumo);

        JButton boton_ingresar = new JButton("Ingresar");

        container.add(idLabel,gridBagConstraints(0,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(idTextField,gridBagConstraints(1,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(nombreLabel,gridBagConstraints(2,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(nombreTextField,gridBagConstraints(3,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(stockLabel,gridBagConstraints(0,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(stockTextField,gridBagConstraints(1,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(precioUnitarioLabel,gridBagConstraints(2,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(precioUnitarioTextField,gridBagConstraints(3,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(tipoLabel,gridBagConstraints(0,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(tipoComboBox,gridBagConstraints(1,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        container.add(unidadMedidaLabel,gridBagConstraints(2,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(unidadMedidaInsumoLista,gridBagConstraints(3,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        container.add(boton_ingresar,gridBagConstraints(0,4,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        pestanas.addTab("Agregar", container);
        
        boton_ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //aqui hace coneccion con la BD para ingresar los datos a la tabla.
                try {
                    var dbc2 = new DatabaseConnection();
                    
                    dbc2.AgregarProducto(idTextField.getText(), nombreTextField.getText(), stockTextField.getText(),precioUnitarioTextField.getText(),tipoComboBox.getSelectedItem().toString(), unidadMedidaInsumoLista.getSelectedItem().toString());
                    TableColumn cantidadColumn = tabla_real.getColumn("Stock");
                    cantidadColumn.setCellRenderer(new ColorRenderer());
                    Searchbar.Buscar("",null,null, modelo);
            
                } catch (NumberFormatException x) {
                    JOptionPane.showMessageDialog(Stock_insumos.this, "Atributos de ingreso vacios o no validos.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }


    /*
        Crea el panel con las opcines para modificar un producto de la tabla
            -el boton guardar cambia los datos en la tabla y la base de datos
            -el de cancelar cancela la operacion y mantiene los valores de la tabla
    */
    private void crear_panel_modificar(JScrollPane tabla){
        JPanel panel_modificar = new JPanel(new GridBagLayout());
        panel_modificar.setBackground(Color.white);
        panel_modificar.setBorder(BorderFactory.createTitledBorder("Modificar"));
        panel_modificar.setName("panel_modificar");

        JLabel idLabel = new JLabel("ID producto:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField idTextField = new JTextField(20);
        idTextField.setEditable(false);
        ((AbstractDocument) idTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField nombreTextField = new JTextField(20);

        JLabel stockLabel = new JLabel("Stock Inicial:");
        JTextField stockTextField = new JTextField(10);
        ((AbstractDocument) stockTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel unidadMedidaLabel = new JLabel("Unidad de medida:");
        String[] unidadMedidaInsumo = {"Kilogramos", "Gramos", "Miligramos","Litros","Mililitros","Unidad","Lote"};
        JComboBox<String> unidadMedidaInsumoLista = new JComboBox<>(unidadMedidaInsumo);

        JLabel precioUnitarioLabel = new JLabel("Precio c/u:");
        JTextField precioUnitarioTextField = new JTextField(10);
        ((AbstractDocument) precioUnitarioTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel tipoLabel = new JLabel("Tipo de Insumo:");
        String[] tiposInsumo = {"Alimento Perecible", "Alimento no Perecible", "Salsa","Utensilios Plasticos","Congelado","Seco"};
        JComboBox<String> tipoComboBox = new JComboBox<>(tiposInsumo);

        JButton boton_guardar = new JButton("Guardar");

        panel_modificar.add(idLabel,gridBagConstraints(0,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(idTextField,gridBagConstraints(1,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(nombreLabel,gridBagConstraints(2,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(nombreTextField,gridBagConstraints(3,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(stockLabel,gridBagConstraints(0,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(stockTextField,gridBagConstraints(1,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(precioUnitarioLabel,gridBagConstraints(2,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(precioUnitarioTextField,gridBagConstraints(3,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(tipoLabel,gridBagConstraints(0,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(tipoComboBox,gridBagConstraints(1,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(unidadMedidaLabel,gridBagConstraints(2,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(unidadMedidaInsumoLista,gridBagConstraints(3,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(boton_guardar,gridBagConstraints(0,4,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        pestanas.addTab("Modificar", panel_modificar);

        JTable tabla_1 = getTableFromScrollPane(tabla);

        ListSelectionModel selectionModel = tabla_1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Verificar si la selección ha cambiado y no está en proceso de ajuste
                if (!e.getValueIsAdjusting()) {
                    // Obtener la fila seleccionada
                    int selectedRow = tabla_1.getSelectedRow();
                    if(selectedRow != -1){
                        Object id = tabla_1.getValueAt(selectedRow, 0);
                        Object nombre = tabla_1.getValueAt(selectedRow, 1);
                        Object stock = tabla_1.getValueAt(selectedRow, 2);
                        Object precio = tabla_1.getValueAt(selectedRow, 3);
                        Object tipo = tabla_1.getValueAt(selectedRow, 4);
                        Object unidad = tabla_1.getValueAt(selectedRow, 5);

                        idTextField.setText(id.toString());
                        nombreTextField.setText(nombre.toString());
                        stockTextField.setText(stock.toString());
                        precioUnitarioTextField.setText(precio.toString());
                        if (comboBoxContainsValue(tipoComboBox, tipo)){tipoComboBox.setSelectedItem(tipo);}
                        if (comboBoxContainsValue(unidadMedidaInsumoLista, unidad)){unidadMedidaInsumoLista.setSelectedItem(unidad);}
                    }
                }
            }
        });
        
        boton_guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idtext = idTextField.getText();
                
                var dbc = new DatabaseConnection();
                if(idtext.isEmpty()){
                    JOptionPane.showMessageDialog(Stock_insumos.this, "Seleccione elemento a modificar.");
                }
                else{
                    dbc.ModificarProducto(idTextField.getText(), nombreTextField.getText(), stockTextField.getText(), precioUnitarioTextField.getText(), tipoComboBox.getSelectedItem().toString(), unidadMedidaInsumoLista.getSelectedItem().toString());
                    Searchbar.Buscar("",null,null, modelo);
                }
            }
        });
    }

    private void crear_panel_eliminar(JScrollPane tabla){
        JPanel panel_modificar = new JPanel(new GridBagLayout());
        panel_modificar.setBackground(Color.white);
        panel_modificar.setBorder(BorderFactory.createTitledBorder("Eliminar"));
        panel_modificar.setName("panel_eliminar");

        JLabel idLabel = new JLabel("ID producto:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField idTextField = new JTextField(20);
        idTextField.setEditable(false);
        ((AbstractDocument) idTextField.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JTextField nombreTextField = new JTextField(20);
        nombreTextField.setEditable(false);
        
        JLabel stockLabel = new JLabel("Stock Inicial:");
        JTextField stockTextField = new JTextField(10);
        ((AbstractDocument) stockTextField.getDocument()).setDocumentFilter(new NumberFilter());
        stockTextField.setEditable(false);
        
        JLabel unidadMedidaLabel = new JLabel("Unidad de medida:");
        String[] unidadMedidaInsumo = {"Kilogramos", "Gramos", "Miligramos","Litros","Mililitros","Unidad","Lote"};
        JComboBox<String> unidadMedidaInsumoLista = new JComboBox<>(unidadMedidaInsumo);
        unidadMedidaInsumoLista.setEditable(false);
        
        JLabel precioUnitarioLabel = new JLabel("Precio c/u:");
        JTextField precioUnitarioTextField = new JTextField(10);
        ((AbstractDocument) precioUnitarioTextField.getDocument()).setDocumentFilter(new NumberFilter());
        precioUnitarioTextField.setEditable(false);
        
        JLabel tipoLabel = new JLabel("Tipo de Insumo:");
        String[] tiposInsumo = {"Alimento Perecible", "Alimento no Perecible", "Salsa","Utensilios Plasticos","Congelado","Seco"};
        JComboBox<String> tipoComboBox = new JComboBox<>(tiposInsumo);
        tipoComboBox.setEditable(false);
        
        JButton boton_guardar = new JButton("Eliminar");

        panel_modificar.add(idLabel,gridBagConstraints(0,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(idTextField,gridBagConstraints(1,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(nombreLabel,gridBagConstraints(2,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(nombreTextField,gridBagConstraints(3,0,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(stockLabel,gridBagConstraints(0,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(stockTextField,gridBagConstraints(1,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(precioUnitarioLabel,gridBagConstraints(2,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(precioUnitarioTextField,gridBagConstraints(3,1,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(tipoLabel,gridBagConstraints(0,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(tipoComboBox,gridBagConstraints(1,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(unidadMedidaLabel,gridBagConstraints(2,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
        panel_modificar.add(unidadMedidaInsumoLista,gridBagConstraints(3,2,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        panel_modificar.add(boton_guardar,gridBagConstraints(0,4,1,1,1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        pestanas.addTab("Eliminar", panel_modificar);

        JTable tabla_1 = getTableFromScrollPane(tabla);

        ListSelectionModel selectionModel = tabla_1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Verificar si la selección ha cambiado y no está en proceso de ajuste
                if (!e.getValueIsAdjusting()) {
                    // Obtener la fila seleccionada
                    int selectedRow = tabla_1.getSelectedRow();
                    if(selectedRow != -1){
                        Object id = tabla_1.getValueAt(selectedRow, 0);
                        Object nombre = tabla_1.getValueAt(selectedRow, 1);
                        Object stock = tabla_1.getValueAt(selectedRow, 2);
                        Object precio = tabla_1.getValueAt(selectedRow, 3);
                        Object tipo = tabla_1.getValueAt(selectedRow, 4);
                        Object unidad = tabla_1.getValueAt(selectedRow, 5);

                        idTextField.setText(id.toString());
                        nombreTextField.setText(nombre.toString());
                        stockTextField.setText(stock.toString());
                        precioUnitarioTextField.setText(precio.toString());
                        if (comboBoxContainsValue(tipoComboBox, tipo)){tipoComboBox.setSelectedItem(tipo);}
                        if (comboBoxContainsValue(unidadMedidaInsumoLista, unidad)){unidadMedidaInsumoLista.setSelectedItem(unidad);}
                    }
                }
            }
        });
        
        boton_guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idtext = idTextField.getText();
                
                var dbc = new DatabaseConnection();
                if(idtext.isEmpty()){
                    JOptionPane.showMessageDialog(Stock_insumos.this, "Seleccione elemento a eliminar.");
                }
                else{
                    // se elimina el producto
                    dbc.EliminarProducto(idTextField.getText());
                    Searchbar.Buscar("",null,null, modelo);
                }
            }
        });
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

    private JScrollPane create_table(){
        JTable tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tabla_productos = new JScrollPane(tabla);

        return tabla_productos;
    }

    private static JTable getTableFromScrollPane(JScrollPane scrollPane) {
        // Obtener la vista del JScrollPane y verificar si es un JTable
        Component view = scrollPane.getViewport().getView();
        if (view instanceof JTable) {
            return (JTable) view;
        }
        return null;
    }

    private static boolean comboBoxContainsValue(JComboBox<String> comboBox, Object value) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(value)) {
                return true;
            }
        }
        return false;
    }
    
    
    
}