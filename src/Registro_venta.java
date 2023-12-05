import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.DocumentException;

public class Registro_venta extends JPanel{

    private DefaultTableModel modelo;
    
    public Registro_venta(){

        String[] columnas = {"Id", "Id de orden", "Fecha de pago","Precio Neto","IVA","Precio total"};
        modelo = new DefaultTableModel(null, columnas);
        
        setLayout(new GridBagLayout());
        setBackground(Color.white);

        JPanel principal = new JPanel(new GridBagLayout());
        principal.setBackground(Color.white);
        principal.setBorder(BorderFactory.createTitledBorder(""));

        SearchbarVentas searchbar_ventas = new SearchbarVentas(modelo);
        principal.add(searchbar_ventas,gridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));

        JScrollPane tabla = create_table();
        principal.add(tabla,gridBagConstraints(0, 1, 1, 1,1,3,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));

        add(principal,gridBagConstraints(0, 0, 1, 1, 1,10,GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));

        JPanel vista_venta = new JPanel(new GridBagLayout());
        vista_venta.setBackground(Color.white);
        vista_venta.setBorder(BorderFactory.createTitledBorder("Venta"));
        vista_venta.setName("panel_reg_ventas");
        contenido_vista_ventas(vista_venta);
        add(vista_venta,gridBagConstraints(1, 0, 1, 3, 2, 3, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));

        JPanel opcines_venta = new JPanel(new GridBagLayout());
        opcines_venta.setBackground(Color.white);
        opcines_venta.setBorder(BorderFactory.createTitledBorder("Opciones"));
        opcines_venta.setName("panel_opc_ventas");
        botones_opciones_venta(opcines_venta);
        add(opcines_venta,gridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST));
    }

    private void botones_opciones_venta(JPanel panel){

        JButton detalles = new JButton("Detalles Venta");
        panel.add(detalles,gridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.WEST));

        JButton generar_registro = new JButton("Generar Registro");
        panel.add(generar_registro,gridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.WEST));

         JButton generar_reporte = new JButton("Generar Reporte");
        panel.add(generar_reporte,gridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.WEST));
        generar_reporte.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {

                try {
                    Reportes report = new Reportes();
                    report.reportVenta();
                } catch (FileNotFoundException | DocumentException  e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } 
            }

        });
    }

    private void contenido_vista_ventas(JPanel panel){

    }

    private JScrollPane create_table(){
        JTable tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tabla_productos = new JScrollPane(tabla);

        return tabla_productos;
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

    /*private static JTable getTableFromScrollPane(JScrollPane scrollPane) {
        // Obtener la vista del JScrollPane y verificar si es un JTable
        Component view = scrollPane.getViewport().getView();
        if (view instanceof JTable) {
            return (JTable) view;
        }
        return null;
    }*/
}
