import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class Searchbar extends JPanel{

    public Searchbar(DefaultTableModel modelo){
        setLayout(new BorderLayout());

        Buscar("",modelo);
        JTextField campoBusqueda = new JTextField(30);
        add(campoBusqueda, BorderLayout.CENTER);

        // Crear el botón de búsqueda
        JButton botonBuscar = new JButton("Buscar");
        add(botonBuscar, BorderLayout.WEST);

        JPanel filtros = new JPanel(new BorderLayout());
        filtros.setBackground(Color.white);
        add(filtros,BorderLayout.EAST);

        String[] tipo_insumo = {"Alimento Perecible", "Alimento no Perecible", "Salsa","Utensilios Plasticos","Congelado","Seco"};
        JComboBox<String> filtro_tipo_insumo = new JComboBox<>(tipo_insumo);
        filtros.add(filtro_tipo_insumo, BorderLayout.WEST);

        String[] cant_stock = {"Sobre stock", "Bajo stock", "Sin stock"};
        JComboBox<String> filtro_cant_stock = new JComboBox<>(cant_stock);
        filtros.add(filtro_cant_stock, BorderLayout.CENTER);

        // Agregar un ActionListener al botón
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Buscar(campoBusqueda.getText(),modelo);  
            }
        });

        filtro_tipo_insumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) filtro_tipo_insumo.getSelectedItem();
                Buscar_tipo(selectedItem,modelo);
            }
        });

        filtro_cant_stock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) filtro_cant_stock.getSelectedItem();
                Buscar_stock(selectedItem,modelo);
            }
        });
        
    }

    public static void Buscar(String texto, DefaultTableModel modelo){
        modelo.setRowCount(0);
        var dbc = new DatabaseConnection();
        var rows = dbc.BuscarProducto(texto);   
        for(Object[] row : rows){
            modelo.addRow(row);
        }
        if(modelo.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado productos", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void Buscar_tipo(String filtro,DefaultTableModel modelo){
        modelo.setRowCount(0);
        var dbc = new DatabaseConnection();
        var rows = dbc.buscarProducto_por_tipo(filtro);
        for(Object[] row : rows){
            modelo.addRow(row);
        }
        if(modelo.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado productos", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void Buscar_stock(String filtro,DefaultTableModel modelo){
        modelo.setRowCount(0);
        var dbc = new DatabaseConnection();
        var rows = dbc.buscarProducto_por_stock(filtro);
        for(Object[] row : rows){
            modelo.addRow(row);
        }
        if(modelo.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado productos", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}
