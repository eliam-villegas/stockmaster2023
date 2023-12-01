import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.time.LocalDate;

public class Orden_de_compra extends JPanel{

    private DefaultTableModel modelo;
    private CardLayout cardLayout = new CardLayout();
    private LocalDate fechaActual;

    public Orden_de_compra(){

        String[] columnas = {"Id orden", "Fecha de compra","Empleado","Cliente"};
        modelo = new DefaultTableModel(null, columnas);

        JPanel card_panel = new JPanel(cardLayout);

        setLayout(new GridBagLayout());
        setBackground(Color.white);

        SearchbarCompras searchbar_ord_compra = new SearchbarCompras(modelo);

        JScrollPane tabla = create_table(modelo);

        JPanel opcines_orden_compra = new JPanel(new GridBagLayout());
        opcines_orden_compra.setBackground(Color.white);
        opcines_orden_compra.setBorder(BorderFactory.createTitledBorder(""));
        opcines_orden_compra.add(searchbar_ord_compra,gridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));
        opcines_orden_compra.add(tabla,gridBagConstraints(0, 1, 1, 1, 1, 3,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));
        card_panel.add(opcines_orden_compra,"opcion 1");
        cardLayout.show(card_panel, "opcion 1");

        JPanel opcines_orden_compra2 = new JPanel(new GridBagLayout());
        opcines_orden_compra2.setBackground(Color.white);
        opcines_orden_compra2.setBorder(BorderFactory.createTitledBorder("Opciones"));

        JButton mostrar_orden = new JButton("Mostrar Ordenes");
        opcines_orden_compra2.add(mostrar_orden,gridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));

        JButton crear_orden = new JButton("Crear Orden");
        opcines_orden_compra2.add(crear_orden,gridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));

        add(opcines_orden_compra2,gridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));
        
        panel_crear_orden(card_panel);

        mostrar_orden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(card_panel, "opcion 1");
            }
        });

        crear_orden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(card_panel, "opcion 2");
            }
        });

        add(card_panel,gridBagConstraints(0, 0, 1, 1, 1, 12, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));

    }

    private void panel_crear_orden(JPanel card_Panel){

        JPanel panel_orden = new JPanel(new GridBagLayout());
        panel_orden.setBackground(Color.white);
        panel_orden.setBorder(BorderFactory.createTitledBorder(""));

        JPanel panel_seleccionar_cliente = new JPanel(new GridBagLayout());
        panel_seleccionar_cliente.setBorder(BorderFactory.createTitledBorder("Seleccione un Cliente"));

        JPanel panel_seleccionar_empleado = new JPanel(new GridBagLayout());
        panel_seleccionar_empleado.setBorder(BorderFactory.createTitledBorder("Seleccione un Encargado"));

        var dbc = new DatabaseConnection();

        String[] columnas = {"Rut","Cliente"};
        DefaultTableModel modelo_cliente = new DefaultTableModel(null, columnas);
        JScrollPane tabla_orden_cliente = create_table(modelo_cliente);

        var rows = dbc.ObtenerClientes_con_orden();
        for(Object[] row : rows){
            modelo_cliente.addRow(row);
        }
        if(modelo_cliente.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado clientes asociados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        panel_seleccionar_cliente.add(tabla_orden_cliente,gridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        String[] columnas_empleado = {"Rut","Empleado"};
        DefaultTableModel modelo_empleado = new DefaultTableModel(null, columnas_empleado);
        JScrollPane tabla_orden_empleado = create_table(modelo_empleado);

        var rows2 = dbc.ObtenerEmpleados_para_orden();
        for(Object[] row : rows2){
            modelo_empleado.addRow(row);
        }
        if(modelo_cliente.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado Empleados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        panel_seleccionar_empleado.add(tabla_orden_empleado,gridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        JPanel crear_ord_cont_produc = new JPanel(new GridBagLayout());
        crear_ord_cont_produc.setBorder(BorderFactory.createTitledBorder("Generar datos de orden"));

        JLabel id_orden = new JLabel("Id orden:");
        JTextField id_orden_text = new JTextField(10);
        ((AbstractDocument) id_orden_text.getDocument()).setDocumentFilter(new NumberFilter());
        crear_ord_cont_produc.add(id_orden,gridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        crear_ord_cont_produc.add(id_orden_text,gridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        JLabel id_cliente = new JLabel("Rut cliente:");
        JTextField id_cliente_text = new JTextField(10);
        id_cliente_text.setEditable(false);
        crear_ord_cont_produc.add(id_cliente,gridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        crear_ord_cont_produc.add(id_cliente_text,gridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        JLabel nombre_cliente = new JLabel("Nombre cliente:");
        JTextField nombre_cliente_text = new JTextField(10);
        nombre_cliente_text.setEditable(false);
        crear_ord_cont_produc.add(nombre_cliente,gridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        crear_ord_cont_produc.add(nombre_cliente_text,gridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        JLabel fecha = new JLabel("Fecha:");
        JTextField fecha_text = new JTextField(10);
        fecha_text.setEditable(false);
        //fechaActual = LocalDate.now();
        crear_ord_cont_produc.add(fecha,gridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        crear_ord_cont_produc.add(fecha_text,gridBagConstraints(3, 1, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        JLabel seleccionar_producto = new JLabel("Seleccione productos:");
        crear_ord_cont_produc.add(seleccionar_producto,gridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        String[] columnas_ord_cont_produ = {"Id","Producto","Precio","Stock"};
        DefaultTableModel modelo_ord_cont_produc = new DefaultTableModel(null ,columnas_ord_cont_produ);
        JScrollPane tabla_ord_cont_produc = create_table(modelo_ord_cont_produc);
        crear_ord_cont_produc.add(tabla_ord_cont_produc,gridBagConstraints(0, 3, 4, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        JLabel cantidad = new JLabel("Cantidad:");
        JTextField cantidad_text = new JTextField(10);
        ((AbstractDocument) cantidad_text.getDocument()).setDocumentFilter(new NumberFilter());
        crear_ord_cont_produc.add(cantidad,gridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        crear_ord_cont_produc.add(cantidad_text,gridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        JLabel precio = new JLabel("precio:");
        JTextField precio_text = new JTextField(10);
        precio_text.setEditable(false);
        crear_ord_cont_produc.add(precio,gridBagConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        crear_ord_cont_produc.add(precio_text,gridBagConstraints(3, 4, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        JLabel subtotal = new JLabel("Subtotal:");
        JTextField subtotal_text = new JTextField(10);
        subtotal_text.setEditable(false);
        crear_ord_cont_produc.add(subtotal,gridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        crear_ord_cont_produc.add(subtotal_text,gridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        JLabel empleado_a_cargo = new JLabel("Encargado:");
        JTextField empleado_a_cargo_text = new JTextField(10);
        crear_ord_cont_produc.add(empleado_a_cargo,gridBagConstraints(2, 5, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        crear_ord_cont_produc.add(empleado_a_cargo_text,gridBagConstraints(3, 5, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        panel_orden.add(panel_seleccionar_cliente,gridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.WEST));
        panel_orden.add(panel_seleccionar_empleado,gridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.WEST));
        panel_orden.add(crear_ord_cont_produc,gridBagConstraints(1, 0, 1, 2, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.WEST));

        card_Panel.add(panel_orden,"opcion 2");

    }

    private GridBagConstraints gridBagConstraints(int x,int y,int gw,int gh,int wx,int wy,int fill,int anchor){
        GridBagConstraints constraintsElements = new GridBagConstraints();
        constraintsElements.fill = fill;
        constraintsElements.anchor = anchor;
        constraintsElements.weightx = wx;
        constraintsElements.weighty = wy;
        constraintsElements.insets = new Insets(5, 5, 5, 5);
        constraintsElements.gridx = x;
        constraintsElements.gridy = y;
        constraintsElements.gridwidth = gw;
        constraintsElements.gridheight = gh;
        return constraintsElements;
    }

    private JScrollPane create_table(DefaultTableModel modelo){
        JTable tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tabla_productos = new JScrollPane(tabla);

        return tabla_productos;
    }

    private Integer[] crearListaNumeros(int inicio, int fin) {
        // Crear una lista de números enteros desde 'inicio' hasta 'fin'
        Integer[] lista = new Integer[fin - inicio + 1];
        for (int i = 0; i < lista.length; i++) {
            lista[i] = inicio + i;
        }
        return lista;
    }
}
