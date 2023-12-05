import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SearchbarVentas extends JPanel{
    
    public SearchbarVentas(DefaultTableModel modelo){
        
        setLayout(new BorderLayout());
        //Buscar("",modelo);

        JTextField campoBusqueda = new JTextField(30);
        add(campoBusqueda, BorderLayout.CENTER);

        // Crear el botón de búsqueda
        JButton botonBuscar = new JButton("Buscar");
        add(botonBuscar, BorderLayout.WEST);

        // Agregar un ActionListener al botón
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //Buscar(campoBusqueda.getText(),modelo);  
            }
        });
        
    }

    public static void Buscar(String texto, DefaultTableModel modelo){
        modelo.setRowCount(0);
        var dbc = new DatabaseConnection();
        var rows = dbc.BuscarProducto(texto,null,null);   
        for(Object[] row : rows){
            modelo.addRow(row);
        }
        if(modelo.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado productos", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
