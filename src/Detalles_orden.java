import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class Detalles_orden extends JFrame{

    private String nombre_cliente;
    private String nombre_encargado;

    private String rut_cliente;
    private String rut_empleado;

    private String fecha;
    private String total_orden;
    private DefaultTableModel modelo_tabla;

    public Detalles_orden(String id_orden){
        setLayout(new GridBagLayout());
        setSize(600, 400);

        obtener_datos_orden(id_orden);

        JLabel titulo = new JLabel("Orden de compra");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo,gridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 2, 5, 2, 5));

        JLabel id_fecha = new JLabel("Id Orden: " + id_orden + "    " + "Fecha: " + fecha);
        add(id_fecha,gridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 2, 5, 2, 5));

        JLabel cliente = new JLabel("Rut cliente: " + rut_cliente + "    " + "Nombre cliente: " + nombre_cliente);
        add(cliente,gridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 2, 5, 2, 5));

        JLabel empleado = new JLabel("Rut encargado: " + rut_empleado + "    " + "Nombre encargado: " + nombre_encargado);
        add(empleado,gridBagConstraints(0, 3, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 2, 5, 2, 5));

        JScrollPane tabla = create_table(modelo_tabla);
        add(tabla,gridBagConstraints(0, 4, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 2, 5, 2, 5));

        JLabel total = new JLabel("Total de la orden: " + total_orden);
        add(total,gridBagConstraints(0, 5, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 2, 5, 2, 5));




        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void obtener_datos_orden(String id_orden){

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String[] columna = {"producto","cantidad","precio","total"};
        modelo_tabla = new DefaultTableModel(null,columna);

        var dbc = new DatabaseConnection();
        var rows = dbc.ObtenenOrdenDeCompraDetalles(id_orden);
        if(!rows.isEmpty()){
            Object[] firts_row = rows.get(0);

            nombre_cliente = (String) firts_row[4];
            nombre_encargado = (String) firts_row[3];
            rut_cliente = String.valueOf(firts_row[0]);
            rut_empleado = String.valueOf(firts_row[1]);
            total_orden = String.valueOf(firts_row[9]);
            fecha = formato.format(firts_row[2]);

            for(Object[] row : rows){
                Object[] seleccionados = {row[5],row[6],row[7],row[8]};
                modelo_tabla.addRow(seleccionados);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "No se han encontrado detalles", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
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

    private JScrollPane create_table(DefaultTableModel modelo){
        JTable tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tabla_productos = new JScrollPane(tabla);

        return tabla_productos;
    }
    
}

