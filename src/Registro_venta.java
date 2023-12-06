import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

public class Registro_venta extends JPanel{

    private DefaultTableModel modelo;
    private CardLayout cardLayout = new CardLayout();

    public Registro_venta(){

        var dbc = new DatabaseConnection();

        String[] columnas = {"Id", "Fecha de pago","Precio Neto","IVA","Precio total"};
        modelo = new DefaultTableModel(null, columnas);

        setLayout(new GridBagLayout());
        setBackground(Color.white);

        JPanel card_panel = new JPanel(cardLayout);

        JPanel principal = new JPanel(new GridBagLayout());
        principal.setBackground(Color.white);
        principal.setBorder(BorderFactory.createTitledBorder(""));

        JScrollPane tabla = create_table(modelo);
        var rows = dbc.ObtenerRegistroVenta();
        for(Object[] row: rows){
            modelo.addRow(row);
        }
        principal.add(tabla,gridBagConstraints(0, 0, 1, 1,1,10,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,0,5,5,5));

        add(principal,gridBagConstraints(0, 0, 1, 1, 1,1,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        /*JPanel vista_venta = new JPanel(new GridBagLayout());
        vista_venta.setBackground(Color.white);
        vista_venta.setBorder(BorderFactory.createTitledBorder("Venta"));
        vista_venta.setName("panel_reg_ventas");
        contenido_vista_ventas(vista_venta);
        add(vista_venta,gridBagConstraints(1, 0, 1, 3, 2, 3, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));*/

        JPanel opcines_venta = new JPanel(new GridBagLayout());
        opcines_venta.setBackground(Color.white);
        opcines_venta.setBorder(BorderFactory.createTitledBorder("Opciones"));
        opcines_venta.setName("panel_opc_ventas");
        JButton generar_registro = new JButton("Generar Registro");
        opcines_venta.add(generar_registro,gridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.WEST,5,5,5,5));

        JButton generar_reporte = new JButton("Generar Reporte");
        opcines_venta.add(generar_reporte,gridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.WEST,5,5,5,5));

        generar_registro(card_panel);

        generar_reporte.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                    Reportes report = new Reportes();

                    SelectorFechaVoucher selectorFecha = new SelectorFechaVoucher(report);
                    selectorFecha.setVisible(true);

            }

        });

        generar_registro.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                    cardLayout.show(card_panel, "opcion 2");
            }

        });
        principal.add(opcines_venta,gridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));

        card_panel.add(principal,"opcion 1");
        cardLayout.show(card_panel, "opcion 1");
        add(card_panel,gridBagConstraints(0, 0, 1, 1, 1,10,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,5,5,5,5));
    }

    private void generar_registro(JPanel card_panel) {

        var dbc = new DatabaseConnection();

        JPanel panel_segundario = new JPanel(new GridBagLayout());
        panel_segundario.setBackground(Color.white);

        JPanel panel_tabla_cliente = new JPanel(new GridBagLayout());
        panel_tabla_cliente.setBackground(Color.white);

        JPanel panel_tabla_orden = new JPanel(new GridBagLayout());
        panel_tabla_orden.setBackground(Color.white);

        String[] columna = {"rut cliente","nombre cliente"};
        DefaultTableModel modelo_cliente = new DefaultTableModel(null,columna);
        SearchbarClient_para_orden searchbarClient = new SearchbarClient_para_orden(modelo_cliente);
        JScrollPane tabla_scroll = create_table(modelo_cliente);
        panel_tabla_cliente.add(searchbarClient,gridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 5, 5, 0, 5));
        panel_tabla_cliente.add(tabla_scroll,gridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 0, 5, 5, 5));
        searchbarClient.realizarBusqueda("", modelo_cliente);

        JLabel id_venta = new JLabel("Id de venta: ");
        JTextField texto_id_venta = new JTextField(10);
        ((AbstractDocument) texto_id_venta.getDocument()).setDocumentFilter(new NumberFilter());
        panel_tabla_orden.add(id_venta,gridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 5, 5, 5, 5));
        panel_tabla_orden.add(texto_id_venta,gridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 5, 5, 5, 5));

        String[] columna2 = {"id orden","fecha_de_orden","rut empleado","rut cliente","total"};
        DefaultTableModel modelo_orden = new DefaultTableModel(null,columna2);
        JScrollPane tabla_scroll_orden = create_table(modelo_orden);
        panel_tabla_orden.add(tabla_scroll_orden,gridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 5, 5, 5, 5));

        JButton generar = new JButton("Generar");
        JButton cancelar = new JButton("Cancelar");

        panel_tabla_orden.add(generar,gridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, 5, 5));
        panel_tabla_orden.add(cancelar,gridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 5, 5, 5, 5));

        panel_segundario.add(panel_tabla_cliente,gridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 5, 5, 5, 5));
        panel_segundario.add(panel_tabla_orden,gridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 5, 5, 5, 5));

        card_panel.add(panel_segundario,"opcion 2");

        JTable tabla = getTableFromScrollPane(tabla_scroll);
        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {  // Evitar eventos duplicados
                    int selectedRow = tabla.getSelectedRow();
                    if (selectedRow != -1) {
                        modelo_orden.setRowCount(0);
                        var rows = dbc.ObtenerOrden_para_registro(tabla.getValueAt(selectedRow, 0).toString());
                        for(Object[] row:rows){
                            modelo_orden.addRow(row);
                        }
                    }
                }
            }
        });

        generar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JTable tabla = getTableFromScrollPane(tabla_scroll);
                int selectedRow = tabla.getSelectedRow();
                if(selectedRow != -1 && modelo_orden.getRowCount() != 0 && !texto_id_venta.getText().isEmpty()){
                    if(dbc.IdVentaUnico(texto_id_venta.getText())){
                        int neto = 0;
                        for(int i = 0; i< modelo_orden.getRowCount();i++){
                            neto = neto +Integer.parseInt(modelo_orden.getValueAt(i, 4).toString());
                        }

                        Double iva = neto * 0.19;

                        Double total = neto + iva;

                        Date fechaActual = new Date();

                        dbc.GenerarRegistroDeVenta(texto_id_venta.getText(),fechaActual,neto,iva,(int) Math.round(total));
                        dbc.AñadirVentaOrdenDeCompra(texto_id_venta.getText(),tabla.getValueAt(selectedRow, 0).toString());

                        modelo.setRowCount(0);
                        var rows = dbc.ObtenerRegistroVenta();
                        for(Object[] row: rows){
                            modelo.addRow(row);
                        }

                        texto_id_venta.setText("");
                        modelo_orden.setRowCount(0);

                        cardLayout.show(card_panel, "opcion 1");
                    }
                    else{
                        JOptionPane.showMessageDialog(Registro_venta.this, "El id utilizado ya esta registrado.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(Registro_venta.this, "Seleccione cliente o rellene todos los campos primero.");
                }
            }

        });

        cancelar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                    cardLayout.show(card_panel, "opcion 1");
            }

        });

    }

    private JScrollPane create_table(DefaultTableModel modelo){
        JTable tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tabla_productos = new JScrollPane(tabla);

        return tabla_productos;
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
}