import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;

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
//import java.time.LocalDate;

public class Orden_de_compra extends JPanel{

    private DefaultTableModel modelo;
    private CardLayout cardLayout = new CardLayout();
    private JTable tabla;
    JTable tabla_productos;
   //private LocalDate fechaActual;

    public Orden_de_compra(){

        setLayout(new GridBagLayout());
        setBackground(Color.white);

        JPanel card_panel = new JPanel(cardLayout);

        JPanel orden_de_compra = new JPanel(new GridBagLayout());
        orden_de_compra.setBackground(Color.white);
        orden_de_compra.setBorder(BorderFactory.createTitledBorder(""));

        JPanel ordenes_de_compra = new JPanel(new GridBagLayout());
        ordenes_de_compra.setBackground(Color.white);
        ordenes_de_compra.setBorder(BorderFactory.createTitledBorder("Ordenes de compra"));

        String[] columnas = {"Id orden", "Fecha de compra","Empleado","Cliente"};
        modelo = new DefaultTableModel(null, columnas);

        SearchbarCompras searchbar_ord_compra = new SearchbarCompras(modelo);
        JScrollPane tabla_ordenes = create_table(modelo);

        ordenes_de_compra.add(searchbar_ord_compra,gridBagConstraints(0, 0, 4, 1, 1, 0, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));
        ordenes_de_compra.add(tabla_ordenes,gridBagConstraints(0, 1, 4, 1, 1, 3,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));

        JButton detalles_orden = new JButton("Detalles de orden");
        ordenes_de_compra.add(detalles_orden,gridBagConstraints(0, 2, 1, 1, 0, 0,GridBagConstraints.BOTH,GridBagConstraints.WEST));

        orden_de_compra.add(ordenes_de_compra,gridBagConstraints(1, 0, 1, 1, 10, 3,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));


        //panel con los clientes asociados a las ordenes de compra
        JPanel vista_clientes = new JPanel(new GridBagLayout());
        vista_clientes.setBackground(Color.white);
        vista_clientes.setBorder(BorderFactory.createTitledBorder("Clientes asociados"));

        String[] columna_cliente = {"Rut","Cliente"};
        DefaultTableModel modelo_cliente = new DefaultTableModel(null,columna_cliente);
        SearchbarClient_para_orden searchbar_clientes = new SearchbarClient_para_orden(modelo_cliente);
        JScrollPane tabla_clientes = create_table(modelo_cliente);
        obtener_clientes(modelo_cliente);

        //se añaden los botones y tablas al panel para cliente
        vista_clientes.add(searchbar_clientes,gridBagConstraints(0, 0, 4, 1, 0, 0,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));
        vista_clientes.add(tabla_clientes,gridBagConstraints(0, 1, 4, 1, 1, 1,GridBagConstraints.BOTH,GridBagConstraints.CENTER));

        JButton mostrar_orden = new JButton("Mostrar Ordenes");
        vista_clientes.add(mostrar_orden,gridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.BOTH,GridBagConstraints.WEST));

        JButton crear_orden = new JButton("Crear Orden");
        vista_clientes.add(crear_orden,gridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.BOTH,GridBagConstraints.WEST));

        orden_de_compra.add(vista_clientes,gridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));

        mostrar_orden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        crear_orden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabla = getTableFromScrollPane(tabla_clientes);
                int filaSeleccionada = tabla.getSelectedRow();
                if(filaSeleccionada != -1){
                    cardLayout.show(card_panel, "opcion 2");
                }
                else{
                    JOptionPane.showMessageDialog(Orden_de_compra.this, "No hay ningun cliente seleccionado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        card_panel.add(orden_de_compra,"opcion 1");
        cardLayout.show(card_panel, "opcion 1");

        panel_crear_orden(card_panel);

        add(card_panel,gridBagConstraints(0, 0, 1, 1, 1, 12, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));

    }

    /*private void panel_crear_orden(JPanel card_Panel){

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

    }*/

    private void panel_crear_orden(JPanel card_Panel){

        var dbc = new DatabaseConnection();
        //se crea el panel principal del apartado de creacion de orden de compra
        JPanel panel_crear_orden = new JPanel(new GridBagLayout());
        panel_crear_orden.setBackground(Color.white);
        panel_crear_orden.setBorder(BorderFactory.createTitledBorder(""));

        //informacion del cliente
        JLabel id_cliente = new JLabel("Rut cliente:");
        JTextField id_cliente_text = new JTextField(5);
        id_cliente_text.setEditable(false);
        panel_crear_orden.add(id_cliente,gridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        panel_crear_orden.add(id_cliente_text,gridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        JLabel nombre_cliente = new JLabel("Nombre cliente:");
        JTextField nombre_cliente_text = new JTextField(5);
        nombre_cliente_text.setEditable(false);
        panel_crear_orden.add(nombre_cliente,gridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        panel_crear_orden.add(nombre_cliente_text,gridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        //informacion orden de compra a crear
        // id de la orden
        JLabel id_orden = new JLabel("Id orden:");
        JTextField id_orden_text = new JTextField(5);
        ((AbstractDocument) id_orden_text.getDocument()).setDocumentFilter(new NumberFilter());
        panel_crear_orden.add(id_orden,gridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        panel_crear_orden.add(id_orden_text,gridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        //fecha de creacion de la orden
        JLabel fecha = new JLabel("Fecha de la orden:");
        JTextField fecha_text = new JTextField(5);
        fecha_text.setEditable(false);
        LocalDate fechaActual = LocalDate.now();
        fecha_text.setText(fechaActual.toString());
        panel_crear_orden.add(fecha,gridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        panel_crear_orden.add(fecha_text,gridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        //tablas de productos disponibles y ya ordenados
        JLabel seleccionar_productos = new JLabel("Seleccione los productos:");
        JLabel seleccionar_ordenados = new JLabel("Productos ordenados:");
        String[] columna_productos = {"Id","Producto","Precio","Stock"};
        DefaultTableModel modelo_product_disp = new DefaultTableModel(null,columna_productos);
        var rows = dbc.ObtenerProducto_para_orden();
        for(Object[] row : rows){modelo_product_disp.addRow(row);}
        if(modelo_product_disp.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado clientes asociados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        JScrollPane productos_diponibles = create_table(modelo_product_disp);

        String[] columna_productos_ordenados = {"Id orden","Id Producto","Cantidad","Precio"};
        DefaultTableModel modelo_product_ord = new DefaultTableModel(null,columna_productos_ordenados);
        JScrollPane productos_ordenados = create_table(modelo_product_ord);

        panel_crear_orden.add(seleccionar_productos,gridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        panel_crear_orden.add(seleccionar_ordenados,gridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        panel_crear_orden.add(productos_diponibles,gridBagConstraints(0, 3, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));
        panel_crear_orden.add(productos_ordenados,gridBagConstraints(2, 3, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        //opcines cantidad y precio de la compra y boton de guardar

        JLabel cantidad = new JLabel("Cantidad:");
        JTextField cantidad_text = new JTextField("1");
        ((AbstractDocument) cantidad_text.getDocument()).setDocumentFilter(new NumberFilter());

        /*JLabel precio = new JLabel("Subtotal:");
        JTextField precio_text = new JTextField(10);
        precio_text.setEditable(false);*/

        panel_crear_orden.add(cantidad,gridBagConstraints(2, 4, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        //panel_crear_orden.add(precio,gridBagConstraints(2, 4, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        panel_crear_orden.add(cantidad_text,gridBagConstraints(3, 4, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
       // panel_crear_orden.add(precio_text,gridBagConstraints(3, 4, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        JButton guardar = new JButton("Guardar");
        panel_crear_orden.add(guardar,gridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        JLabel precio_total = new JLabel("Total:");
        JTextField precio_total_text = new JTextField("0");
        precio_total_text.setEditable(false);
        panel_crear_orden.add(precio_total,gridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));
        panel_crear_orden.add(precio_total_text,gridBagConstraints(1, 6, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST));

        //empleado encargado de la orden
        JLabel encargado = new JLabel("Rut encargado:");
        JTextField rut_encargado = new JTextField(5);
        ((AbstractDocument) rut_encargado.getDocument()).setDocumentFilter(new NumberFilter());

        panel_crear_orden.add(encargado,gridBagConstraints(0, 7, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));
        panel_crear_orden.add(rut_encargado,gridBagConstraints(1, 7, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));

        /*var rows = dbc.ObtenerEmpleados_para_orden();
        String[] columna_encargados = {"Rut","Encargado"};
        DefaultTableModel modelo_encargados = new DefaultTableModel(null,columna_encargados);
        JScrollPane tabla_encargados = create_table(modelo_encargados);
        for(Object[] row : rows){modelo_encargados.addRow(row);}
        if(modelo_encargados.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado clientes asociados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        panel_crear_orden.add(tabla_encargados,gridBagConstraints(0, 6, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST));
        */

        //botones de crear y cancelar
        JButton generar_orden = new JButton("Generar Orden");
        JButton cancelar = new JButton("Cancelar");

        panel_crear_orden.add(generar_orden,gridBagConstraints(0, 8 ,1, 1, 0, 5, GridBagConstraints.NONE, GridBagConstraints.SOUTHWEST));
         panel_crear_orden.add(cancelar,gridBagConstraints(2, 8, 1, 1, 0, 5, GridBagConstraints.NONE, GridBagConstraints.SOUTHWEST));


        //se añade el panel final con el contenido a las opciones de paneles a mostrar
        card_Panel.add(panel_crear_orden,"opcion 2");

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //hola
                tabla_productos = getTableFromScrollPane(productos_diponibles);

                if(id_orden_text.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un id para la orden", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    if(dbc.VerificarIDUnico_orden_de_compra(Integer.parseInt(id_orden_text.getText())) == true){
                        int fila_seleccionada = tabla_productos.getSelectedRow();
                        if(fila_seleccionada != -1){

                            id_orden_text.setEditable(false);
                            int num_cantidad = Integer.parseInt(cantidad_text.getText());
                            int num_precio_producto = Integer.parseInt(tabla_productos.getValueAt(fila_seleccionada, 3).toString());

                            Object[] nuevaFila = {
                                Integer.parseInt(id_orden_text.getText()), 
                                tabla_productos.getValueAt(fila_seleccionada, 0),
                                num_cantidad,
                                (num_precio_producto * num_cantidad),
                            };

                            modelo_product_ord.addRow(nuevaFila);
                            precio_total_text.setText(Integer.toString(Integer.parseInt(precio_total_text.getText()) + (num_precio_producto * num_cantidad)));
                            
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Seleccione un producto.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "El Id de orden que a ingresado ya existe", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        generar_orden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                id_orden_text.setEditable(true);
                id_orden_text.setText("");
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id_orden_text.setEditable(true);
                id_orden_text.setText("");
                cardLayout.show(card_Panel, "opcion 1");
            }
        });
        cantidad_text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // El texto original se mantiene cuando el componente obtiene el foco
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Si el texto se deja vacío, se restaura el mensaje
                if (cantidad_text.getText().isEmpty()) {
                    cantidad_text.setText("1");
                }
            }
        });

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

    private void obtener_clientes(DefaultTableModel modelo){
        var dbc = new DatabaseConnection();
        var rows2 = dbc.ObtenerClientes_para_orden();
        for(Object[] row : rows2){
            modelo.addRow(row);
        }
        if(modelo.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado Empleados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static JTable getTableFromScrollPane(JScrollPane scrollPane) {
        // Obtener la vista del JScrollPane y verificar si es un JTable
        Component view = scrollPane.getViewport().getView();
        if (view instanceof JTable) {
            return (JTable) view;
        }
        return null;
    }
}
