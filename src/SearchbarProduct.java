import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchbarProduct extends JPanel{

    public SearchbarProduct(){
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
                // Aquí puedes realizar la lógica de búsqueda con la consulta ingresada
            }
        });
    }

    protected void realizarBusqueda(String consulta) {
        // Realizar la consulta a la base de datos
        try {
            Connection conn = null;
            conn = DatabaseConnection.connect();
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM producto WHERE nombre_producto LIKE '%" + consulta + "%'";
            ResultSet resultSet = statement.executeQuery(query);

            // Procesar los resultados de la consulta
            while (resultSet.next()) {
                // Obtener los datos de la consulta
                String resultado = resultSet.getString("nombre_producto");
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
}
    

