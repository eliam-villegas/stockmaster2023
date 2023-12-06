import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class FiltrosAbastecimiento extends JDialog {
    private JTextField clienteField;
    private JTextField empleadoField;
    private JSpinner fechaSpinner1;
    private JSpinner fechaSpinner2;

    private SearchbarAbastecimiento parent;

    public FiltrosAbastecimiento(SearchbarAbastecimiento parent) {
        this.parent = parent;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Agrega espacio entre componentes

        clienteField = new JTextField(10);
        if(parent.proveedor != null)
            clienteField.setText(parent.proveedor);

        empleadoField = new JTextField(10);
        if(parent.empleado != null)
            empleadoField.setText(parent.empleado);

        fechaSpinner1 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor1 = new JSpinner.DateEditor(fechaSpinner1, "yyyy-MM-dd");
        fechaSpinner1.setEditor(dateEditor1);

        if(parent.fechaInicial!=null)
            fechaSpinner1.setValue(parent.fechaInicial);

        fechaSpinner2 = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(fechaSpinner2, "yyyy-MM-dd");
        fechaSpinner2.setEditor(dateEditor2);
        if(parent.fechaFinal!=null)
            fechaSpinner2.setValue(parent.fechaFinal);


        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Proveedor: "), gbc);
        gbc.gridx = 1;
        add(clienteField, gbc);
        gbc.gridx = 2;


        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Empleado: "), gbc);
        gbc.gridx = 1;
        add(empleadoField, gbc);
        gbc.gridx = 2;


        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Fecha inicio: "), gbc);
        gbc.gridx = 1;
        add(fechaSpinner1, gbc);
        gbc.gridx = 2;


        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Fecha fin: "), gbc);
        gbc.gridx = 1;
        add(fechaSpinner2, gbc);
        gbc.gridx = 2;

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        JButton buscarButton = new JButton("Actualizar filtros");
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActualizarFiltros();
                dispose();
            }
        });
        add(buscarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JButton cerrarButton = new JButton("Cerrar");
        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana de filtros
            }
        });
        add(cerrarButton, gbc);

        pack();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    }


    private void ActualizarFiltros() {
        // Obtener los valores de los campos de texto
        parent.proveedor = clienteField.getText();
        parent.empleado = empleadoField.getText();
        parent.fechaInicial = (Date) fechaSpinner1.getValue();
        parent.fechaFinal = (Date) fechaSpinner2.getValue();
    }
}
