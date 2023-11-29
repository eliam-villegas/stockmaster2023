import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Graficos extends JPanel {

    public Graficos() {
        // Crear el conjunto de datos a partir de la consulta SQL
        CategoryDataset dataset = createDataset();

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createBarChart(
                "Ventas por Cliente",  // Título del gráfico
                "Clientes",            // Etiqueta del eje X
                "Total Ventas",        // Etiqueta del eje Y
                dataset,               // Conjunto de datos
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Personalizar la apariencia del gráfico si es necesario
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        // Crear un panel de gráfico y agregarlo al JPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
        add(chartPanel);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Establecer la conexión a la base de datos y ejecutar la consulta SQL
        try (Connection connection = DatabaseConnection.Getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT cliente.nombre, cliente.rut_cliente, COUNT(registro_de_venta.id_venta) AS total_ventas " +
                             "FROM cliente " +
                             "JOIN orden_de_compra ON cliente.rut_cliente = orden_de_compra.rut_cliente " +
                             "JOIN registro_de_venta ON orden_de_compra.id_venta = registro_de_venta.id_venta " +
                             "GROUP BY cliente.nombre, cliente.rut_cliente")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            // Agregar los resultados al conjunto de datos del gráfico
            while (resultSet.next()) {
                String nombreCliente = resultSet.getString("nombre");
                int totalVentas = resultSet.getInt("total_ventas");
                dataset.addValue(totalVentas, "Ventas", nombreCliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }
}
