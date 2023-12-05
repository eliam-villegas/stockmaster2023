import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SearchbarClient extends JPanel{

     public SearchbarClient(DefaultTableModel modelo){
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
                realizarBusqueda(consulta);
                Buscar(campoBusqueda.getText(),modelo);
                // Aquí puedes realizar la lógica de búsqueda con la consulta ingresada
            }
        });
    }

    protected void realizarBusqueda(String consulta) {
        // Realizar la consulta a la base de datos
        try {
            Connection conn = null;
            conn = DatabaseConnection.Getconnection();
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM cliente WHERE nombre LIKE '%" + consulta + "%'";
            ResultSet resultSet = statement.executeQuery(query);

            // Procesar los resultados de la consulta
            while (resultSet.next()) {
                // Obtener los datos de la consulta
                String resultado = resultSet.getString("nombre");
                // Hacer algo con el resultado (mostrarlo en una tabla, en un JOptionPane, etc.)

                System.out.println(resultado);
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error al realizar la búsqueda: " + e.getMessage());
        }
    }

    public static void Buscar(String texto, DefaultTableModel modelo){
        modelo.setRowCount(0);
        var dbc = new DatabaseConnection();
        var rows = dbc.BuscarCliente(texto);   
        for(Object[] row : rows){
            modelo.addRow(row);
        }
        if(modelo.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado Clientes", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
    
