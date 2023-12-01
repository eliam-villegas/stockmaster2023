import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Llamada al método del renderizador predeterminado para configurar la apariencia básica
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Comprobación de la condición para cambiar el color del texto
             int cantidad = Integer.parseInt((String) value);  // Asumiendo que la columna "Cantidad" contiene valores enteros

             if (cantidad < 30 && cantidad >=1) {
                setForeground(new Color(255, 180, 0));
            } else if(cantidad == 0) {
                setForeground(Color.red);
            }
            else{
                setForeground(new Color(50, 205, 50));
            }

        return this;
    }
}
