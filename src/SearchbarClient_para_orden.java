import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SearchbarClient_para_orden extends JPanel{

    public SearchbarClient_para_orden(DefaultTableModel modelo){
        setLayout(new BorderLayout());

        JTextField campoBusqueda = new JTextField(30);
        add(campoBusqueda, BorderLayout.CENTER);

        // Crear el botón de búsqueda
        JButton botonBuscar = new JButton("Buscar");
        add(botonBuscar, BorderLayout.WEST);

        // Agregar un ActionListener al botón
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = campoBusqueda.getText();
                realizarBusqueda(consulta,modelo);
                // Aquí puedes realizar la lógica de búsqueda con la consulta ingresada
            }
        });
    }

    protected void realizarBusqueda(String filtro,DefaultTableModel modelo) {
        modelo.setRowCount(0);
        var dbc = new DatabaseConnection();
        var rows = dbc.BuscarClientes_para_orden(filtro);
        for(Object[] row : rows){
            modelo.addRow(row);
        }
        if(modelo.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado clientes", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
    
