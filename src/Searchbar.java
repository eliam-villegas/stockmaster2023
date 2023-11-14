import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Searchbar extends JPanel{

    public Searchbar(){
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
                //String consulta = campoBusqueda.getText();
                // Aquí puedes realizar la lógica de búsqueda con la consulta ingresada
            }
        });
    }
    
}
