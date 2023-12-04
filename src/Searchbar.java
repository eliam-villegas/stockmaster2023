<<<<<<< HEAD
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

    public String filtroTipo;
    public DatabaseConnection.CANT_STOCK filtroStock;
    
    public Searchbar(DefaultTableModel modelo){
        setLayout(new BorderLayout());

        Buscar("",null,null,modelo);
        JTextField campoBusqueda = new JTextField(30);
        add(campoBusqueda, BorderLayout.CENTER);

        // Crear el botón de búsqueda
        JButton botonBuscar = new JButton("Buscar");
        add(botonBuscar, BorderLayout.WEST);

        JPanel filtros = new JPanel(new BorderLayout());
        filtros.setBackground(Color.white);
        add(filtros,BorderLayout.EAST);

        var dbc = new DatabaseConnection();
        var rows = dbc.ObtenerTiposProductos();           
        String[] tipo_insumo = new String[rows.size() + 1];
        
        for(int i = 0; i<rows.size();i++){
            tipo_insumo[i] = rows.get(i)[0].toString();
        }
        
        JComboBox<String> filtro_tipo_insumo = new JComboBox<>(tipo_insumo);
        filtro_tipo_insumo.setSelectedItem(tipo_insumo[rows.size()]);
        filtros.add(filtro_tipo_insumo, BorderLayout.WEST);
        
        
        
        JComboBox<DatabaseConnection.CANT_STOCK> filtro_cant_stock = new JComboBox<>(DatabaseConnection.CANT_STOCK.values());
        filtros.add(filtro_cant_stock, BorderLayout.CENTER);
        filtro_cant_stock.setSelectedIndex(3);
        
        // Agregar un ActionListener al botón
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Buscar(campoBusqueda.getText(),filtroTipo,filtroStock,modelo);  
            }
        });

        filtro_tipo_insumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtroTipo = (String) filtro_tipo_insumo.getSelectedItem();
                Buscar(campoBusqueda.getText(),filtroTipo,filtroStock,modelo); 
            }
        });

        filtro_cant_stock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtroStock = (DatabaseConnection.CANT_STOCK) filtro_cant_stock.getSelectedItem();
                Buscar(campoBusqueda.getText(),filtroTipo,filtroStock,modelo); 
            }
        });
        
    }

    public static void Buscar(String texto,String filtroTipo,DatabaseConnection.CANT_STOCK filtroStock, DefaultTableModel modelo){
        modelo.setRowCount(0);
        var dbc = new DatabaseConnection();
        var rows = dbc.BuscarProducto(texto,filtroTipo,filtroStock);   
        for(Object[] row : rows){
            modelo.addRow(row);
        }
        if(modelo.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado productos", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}
=======
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

    public String filtroTipo;
    public DatabaseConnection.CANT_STOCK filtroStock;
    
    public Searchbar(DefaultTableModel modelo){
        setLayout(new BorderLayout());

        Buscar("",null,null,modelo);
        JTextField campoBusqueda = new JTextField(30);
        add(campoBusqueda, BorderLayout.CENTER);

        // Crear el botón de búsqueda
        JButton botonBuscar = new JButton("Buscar");
        add(botonBuscar, BorderLayout.WEST);

        JPanel filtros = new JPanel(new BorderLayout());
        filtros.setBackground(Color.white);
        add(filtros,BorderLayout.EAST);

        var dbc = new DatabaseConnection();
        var rows = dbc.ObtenerTiposProductos();           
        String[] tipo_insumo = new String[rows.size() + 1];
        
        for(int i = 0; i<rows.size();i++){
            tipo_insumo[i] = rows.get(i)[0].toString();
        }
        
        JComboBox<String> filtro_tipo_insumo = new JComboBox<>(tipo_insumo);
        filtro_tipo_insumo.setSelectedItem(tipo_insumo[rows.size()]);
        filtros.add(filtro_tipo_insumo, BorderLayout.WEST);
        
        
        
        JComboBox<DatabaseConnection.CANT_STOCK> filtro_cant_stock = new JComboBox<>(DatabaseConnection.CANT_STOCK.values());
        filtros.add(filtro_cant_stock, BorderLayout.CENTER);
        filtro_cant_stock.setSelectedIndex(3);
        
        // Agregar un ActionListener al botón
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Buscar(campoBusqueda.getText(),filtroTipo,filtroStock,modelo);  
            }
        });

        filtro_tipo_insumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtroTipo = (String) filtro_tipo_insumo.getSelectedItem();
                Buscar(campoBusqueda.getText(),filtroTipo,filtroStock,modelo); 
            }
        });

        filtro_cant_stock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtroStock = (DatabaseConnection.CANT_STOCK) filtro_cant_stock.getSelectedItem();
                Buscar(campoBusqueda.getText(),filtroTipo,filtroStock,modelo); 
            }
        });
        
    }

    public static void Buscar(String texto,String filtroTipo,DatabaseConnection.CANT_STOCK filtroStock, DefaultTableModel modelo){
        modelo.setRowCount(0);
        var dbc = new DatabaseConnection();
        var rows = dbc.BuscarProducto(texto,filtroTipo,filtroStock);   
        for(Object[] row : rows){
            modelo.addRow(row);
        }
        if(modelo.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "No se han encontrado productos", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}
>>>>>>> 8c587ce032321e578809d8ad78edec591b0ae3ac
