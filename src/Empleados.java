import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class Empleados extends JPanel{

    public Empleados(){
        setLayout(new GridBagLayout());
        setBackground(Color.white);

        JPanel panel_principal = new JPanel(new GridBagLayout());
        panel_principal.setBackground(Color.white);
        panel_principal.setBorder(BorderFactory.createTitledBorder(""));

        JPanel acciones = new JPanel(new GridBagLayout());
        acciones.setBackground(Color.white);
        acciones.setBorder(BorderFactory.createTitledBorder("Acciones"));
        panel_principal.add(acciones,gridBagConstraints(0, 0, 1, 1, 1, 1,  GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST,5,5,5,5));

        JPanel tabla = new JPanel(new GridBagLayout());
        tabla.setBackground(Color.white);
        tabla.setBorder(BorderFactory.createTitledBorder("Tabla de Empleados"));
        panel_principal.add(tabla,gridBagConstraints(0, 1, 1, 1, 1, 4,  GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST,5,5,5,5));

        String[] columna_empleados = {"Rut","Nombre","Cargo","Contraseña"};
        DefaultTableModel modelo_empleados = new DefaultTableModel(null,columna_empleados);
        JScrollPane tabla_empleados = create_table(modelo_empleados);
        tabla.add(tabla_empleados,gridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST,0,5,5,5));

        SearchbarEmpleado searchbar = new SearchbarEmpleado(modelo_empleados);
        tabla.add(searchbar,gridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST,1,5,0,5));
        SearchbarEmpleado.Buscar_empleado("",modelo_empleados);

        JPanel opciones = new JPanel(new GridBagLayout());
        opciones.setBackground(Color.white);
        opciones.setBorder(BorderFactory.createTitledBorder("Opciones"));
        panel_principal.add(opciones,gridBagConstraints(0, 2, 1, 1, 1, 1,  GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST,5,5,5,5));

        add(panel_principal,gridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST,5,5,5,5));
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

    private static JTable getTableFromScrollPane(JScrollPane scrollPane) {
        // Obtener la vista del JScrollPane y verificar si es un JTable
        Component view = scrollPane.getViewport().getView();
        if (view instanceof JTable) {
            return (JTable) view;
        }
        return null;
    }
    
}
