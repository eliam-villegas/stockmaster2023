
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author marti
 */
public class DetallesRegistroAbastecimiento extends JPanel {
    private JLabel lblNumeroCompra = new JLabel("Número de Compra:");
    private JLabel lblProveedor = new JLabel("Nombre del Proveedor:");
    private JLabel lblEmpleadoComprador = new JLabel("Nombre del Empleado Comprador:");
    private JLabel lblFecha = new JLabel("Fecha:");

    private JTable tableProductos;
    public DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    private JLabel lblTotal = new JLabel("Total: $0.00");

    private JButton btnVolver = new JButton("Volver");

    public DetallesRegistroAbastecimiento(Registro_abastecimiento parent) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Agregar componentes al panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblNumeroCompra, gbc);

        gbc.gridy = 1;
        add(lblProveedor, gbc);

        gbc.gridy = 2;
        add(lblEmpleadoComprador, gbc);

        gbc.gridy = 3;
        add(lblFecha, gbc);

        // Configurar la tabla de productos
        tableModel = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Precio"}, 0);
        tableProductos = new JTable(tableModel);
        scrollPane = new JScrollPane(tableProductos);
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        // Total
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0.0;
        add(lblTotal, gbc);

        // Botón Volver
        gbc.gridy = 6;
        add(btnVolver, gbc);
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.cardLayout.show(parent.card_panel, "opcion 1");
            }
        });
        
    }

    // Método para agregar productos a la tabla
    public void agregarProducto(String producto, int cantidad, int precio) {
        Object[] row = {producto, cantidad, precio};
        tableModel.addRow(row);
    }

    // Método para actualizar el total
    public void actualizarTotal(int total) {
        lblTotal.setText("Total: $" + String.format("%d", total));
    }
        // Método para actualizar el número de compra
    public void actualizarNumeroCompra(String numeroCompra) {
        lblNumeroCompra.setText("Número de Compra: " + numeroCompra);
    }

    // Método para actualizar el nombre del proveedor
    public void actualizarProveedor(String proveedor) {
        lblProveedor.setText("Nombre del Proveedor: " + proveedor);
    }

    // Método para actualizar el nombre del empleado comprador
    public void actualizarEmpleadoComprador(String empleadoComprador) {
        lblEmpleadoComprador.setText("Nombre del Empleado Comprador: " + empleadoComprador);
    }

    // Método para actualizar la fecha
    public void actualizarFecha(String fecha) {
        lblFecha.setText("Fecha: " + fecha);
    }
    
}