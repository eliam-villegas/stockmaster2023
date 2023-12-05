import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SearchbarAbastecimiento extends JPanel{

    //filtros
    public String proveedor;
    public String empleado;
    public Date fechaInicial;
    public Date fechaFinal;
    
    public SearchbarAbastecimiento(DefaultTableModel modelo){
        
        setLayout(new BorderLayout());
        Buscar(null,null,null,null,"",modelo);

        JTextField campoBusqueda = new JTextField(30);
        add(campoBusqueda, BorderLayout.CENTER);

        // Crear el botón de búsqueda
        JButton botonBuscar = new JButton("Buscar");
        add(botonBuscar, BorderLayout.WEST);

        // Agregar botón de filtros
        JButton filtrosButton = new JButton("Filtros");
        filtrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaFiltros(SearchbarAbastecimiento.this);
            }
        });
        
        add(filtrosButton, BorderLayout.EAST);
        
        // Agregar un ActionListener al botón
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Buscar(proveedor,empleado,fechaInicial,fechaFinal,campoBusqueda.getText(),modelo);  
            }
        });
        
    }

    public static void Buscar(String proveedor,String empleado,Date fechaInicial,Date fechaFinal,String id, DefaultTableModel modelo){
        modelo.setRowCount(0);
        var dbc = new DatabaseConnection();
        var rows = dbc.BuscarRegistroAbastecimiento(proveedor, empleado, fechaInicial, fechaFinal, id);
        for(Object[] row : rows){
            modelo.addRow(row);
        }
        if(modelo.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado ordenes de compra", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void mostrarVentanaFiltros(SearchbarAbastecimiento parent) {
        // Crear y mostrar la ventana de filtros
        FiltrosAbastecimiento filtrosVentana = new FiltrosAbastecimiento(parent);
        filtrosVentana.setVisible(true);
    }
    
}
