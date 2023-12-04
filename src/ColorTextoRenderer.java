import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorTextoRenderer extends DefaultTableCellRenderer{

    private static final int LIMITE_STOCK = 30;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Obtener el valor en la celda
            int stock = Integer.parseInt(value.toString());

            // Cambiar el color del texto si el stock es menor o igual al límite
            if (stock <= LIMITE_STOCK) {
                c.setForeground(Color.RED);
            } else {
                c.setForeground(table.getForeground());
            }

            return c;
        }
    
}
