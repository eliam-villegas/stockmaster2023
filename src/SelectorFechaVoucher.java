
import com.itextpdf.text.DocumentException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author marti
 */
public class SelectorFechaVoucher extends JDialog {
    private JTextField clienteField;
    private JTextField empleadoField;
    private JSpinner fechaSpinner1;
    private JSpinner fechaSpinner2;

    private Reportes parent;

    public SelectorFechaVoucher(Reportes parent) {
        this.parent = parent;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Agrega espacio entre componentes


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
        JButton buscarButton = new JButton("Aceptar");
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GetFechas();
                dispose();
                
                try {
                    parent.reportVenta();   
                } catch (FileNotFoundException | DocumentException  e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } 
                
                
            }
        });
        add(buscarButton, gbc);

        pack();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    }


    private void GetFechas() {
        parent.fechaInicial = (Date) fechaSpinner1.getValue();
        parent.fechaFinal = (Date) fechaSpinner2.getValue();
    }
}
