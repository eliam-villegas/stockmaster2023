import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

public class Graficos extends JPanel {

    public static JPanel createVentasPorClienteChart() {
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

        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(460, 370));

        JPanel panel = new JPanel();
        panel.add(chartPanel);

        return panel;
    }

    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection connection = DatabaseConnection.Getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT cliente.nombre, cliente.rut_cliente, COUNT(registro_de_venta.id_venta) AS total_ventas " +
                             "FROM cliente " +
                             "JOIN orden_de_compra ON cliente.rut_cliente = orden_de_compra.rut_cliente " +
                             "JOIN registro_de_venta ON orden_de_compra.id_venta = registro_de_venta.id_venta " +
                             "GROUP BY cliente.nombre, cliente.rut_cliente ORDER BY total_ventas DESC;")) {

            ResultSet resultSet = preparedStatement.executeQuery();

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

    public static JPanel graficoTorta(){
        DefaultPieDataset dataset = createPieDataset();

        JFreeChart chart = ChartFactory.createPieChart(
                "Productos Más Vendidos",
                dataset,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(460, 370));

        JPanel panel = new JPanel();
        panel.add(chartPanel);
        return panel;
    }

    private static DefaultPieDataset createPieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try (Connection connection = DatabaseConnection.Getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT producto.nombre_producto, COUNT(*) AS total_productos " +
                             "FROM orden_compra_contiene_producto " +
                             "JOIN producto ON orden_compra_contiene_producto.id_producto = producto.id_producto " +
                             "JOIN orden_de_compra ON orden_compra_contiene_producto.id_orden = orden_de_compra.id_orden " +
                             "JOIN registro_de_venta ON orden_de_compra.id_venta = registro_de_venta.id_venta " +
                             "WHERE registro_de_venta.fecha_pago IS NOT NULL " +
                             "GROUP BY producto.nombre_producto")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nombreProducto = resultSet.getString("nombre_producto");
                int totalProductos = resultSet.getInt("total_productos");
                dataset.setValue(nombreProducto + " (" + totalProductos + ")", totalProductos);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public static JPanel tablaStock() {
        String[] columnas = {"ID", "Producto", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        try (Connection connection = DatabaseConnection.Getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_producto, nombre_producto, stock FROM producto " +
                             "ORDER BY stock;")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idProducto = resultSet.getInt("id_producto");
                String nombreProducto = resultSet.getString("nombre_producto");
                int stock = resultSet.getInt("stock");

                modelo.addRow(new Object[]{idProducto, nombreProducto, stock});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JPanel panel = new JPanel();

        JLabel etiqueta = new JLabel("Stock de productos");

        panel.add(etiqueta);
        scrollPane.setPreferredSize(new java.awt.Dimension(450,350));
        panel.add(scrollPane);
        panel.setPreferredSize(new java.awt.Dimension(460, 360));
        return panel;
    }

    public static JPanel createChartPanel() {
        CategoryDataset dataset = createDatasetCategory();

        JFreeChart chart = ChartFactory.createLineChart(
                "Ventas por Mes",
                "Mes",
                "Total Ventas",
                dataset
        );

        NumberAxis yAxis = (NumberAxis) chart.getCategoryPlot().getRangeAxis();
        yAxis.setNumberFormatOverride(NumberFormat.getNumberInstance());

        CategoryAxis xAxis = (CategoryAxis) chart.getCategoryPlot().getDomainAxis();
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(460,370));
        return chartPanel;
    }

    private static CategoryDataset createDatasetCategory() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection connection = DatabaseConnection.Getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT to_char(registro_de_venta.fecha_pago, 'Month') AS nombre_mes, " +
                             "COUNT(*) AS total_ventas " +
                             "FROM orden_compra_contiene_producto " +
                             "JOIN orden_de_compra ON orden_compra_contiene_producto.id_orden = orden_de_compra.id_orden " +
                             "JOIN registro_de_venta ON orden_de_compra.id_venta = registro_de_venta.id_venta " +
                             "WHERE registro_de_venta.fecha_pago IS NOT NULL " +
                             "GROUP BY nombre_mes")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nombreMes = resultSet.getString("nombre_mes");
                int totalVentas = resultSet.getInt("total_ventas");
                dataset.addValue(totalVentas, "Ventas", nombreMes);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dataset;
    }

}
