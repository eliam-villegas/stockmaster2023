
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.*;


public class Reportes {


    private final String ruta;

    public Reportes() {
        ruta = System.getProperty("user.home") + "/Desktop/";
    }
    
    public void reportVenta() throws FileNotFoundException, DocumentException{
        
        String nombreArchivo = "RegistroVentas_" + generarFecha() + ".pdf";
        String rutaCompleta = ruta + nombreArchivo;

        if (archivoExiste(rutaCompleta)) {
            JOptionPane.showMessageDialog(null, "El archivo ya existe. Cambie el nombre o elimine el archivo existente antes de generar uno nuevo.");
            return;
        }
        
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(rutaCompleta));
        doc.open();
         try {
            
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Informe Registro de Ventas " + generarFecha(), fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph(20f, " "));
            
            PdfPTable tabla = new PdfPTable(7);
            
            tabla.addCell("id venta");
            tabla.addCell("total");
            tabla.addCell("fecha de pago");
            tabla.addCell("iva");
            tabla.addCell("neto");
            tabla.addCell("Rut Cliente");
            tabla.addCell("Nombre Cliente");

            try {
                Connection cn = DatabaseConnection.Getconnection();
                Statement statement = cn.createStatement(); 
                String query = "SELECT DISTINCT rv.id_venta, rv.total, rv.fecha_pago, rv.iva, rv.neto, oc.rut_cliente, c.nombre FROM registro_de_venta rv " +
                                "JOIN orden_de_compra oc ON rv.id_venta = oc.id_venta " + 
                                "JOIN cliente c ON oc.rut_cliente = c.rut_cliente " + 
                                "WHERE rv.activo = true";
                ResultSet rs = statement.executeQuery(query);

                if(rs.next()){

                    do{
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                        tabla.addCell(rs.getString(6));
                        tabla.addCell(rs.getString(7));

                    }while(rs.next());

                    doc.add(tabla);
                }
                
                doc.close();
                JOptionPane.showMessageDialog(null, "PDF generado correctamente.");
            } catch (DocumentException | SQLException e) {
                System.out.println(e);
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }
    private boolean archivoExiste(String rutaCompleta) {
        File archivo = new File(rutaCompleta);
        return archivo.exists();
    }

    private String generarFecha() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
        LocalDateTime fechaActual = LocalDateTime.now();
        return fechaActual.format(formatter);
    }
    
    public void reportOrdenCompra(int id_orden) throws FileNotFoundException, DocumentException{
          
        String nombreArchivo = "OrdenCompra_Num"+ id_orden + generarFecha() + ".pdf";
        String rutaCompleta = ruta + nombreArchivo;

        if (archivoExiste(rutaCompleta)) {
            JOptionPane.showMessageDialog(null, "El archivo ya existe. Cambie el nombre o elimine el archivo existente antes de generar uno nuevo.");
            return;
        }
        
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(rutaCompleta));
        doc.open();

         try {
            Connection cn = DatabaseConnection.Getconnection();
            
            //LABEL DATOS DE LA EMPRESA
            Font fontEmpresa = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
            Paragraph nombreEmpresa = new Paragraph("Distribuidora Claudio Olivares\n RUT 123456789\n Giro Comercial\n FONO +56 912345678\n distribuidoraclaudioolivares@gmail.com ", fontEmpresa);
            nombreEmpresa.setAlignment(Element.ALIGN_TOP);
            doc.add(nombreEmpresa);
            
            //LABEL TITULO
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.UNDERLINE);
            Paragraph titulo = new Paragraph("Voucher Orden De Compra Num " + id_orden, fontTitulo);
            titulo.setAlignment(Element.TITLE);
            doc.add(titulo);
            doc.add(new Paragraph(20f, " "));
            
            //LABEL FECHA DE EMISION
            Font fontfecha = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);
            Paragraph fecha = new Paragraph("Fecha de Emision: " + generarFecha(), fontfecha);
            titulo.setAlignment(Element.SUBJECT);
            doc.add(fecha);
            doc.add(new Paragraph(2f, " "));

            //LABEL FECHA DE ORDEN DE COMPRA
            Date fechaOrden = null;

            String consulta = "SELECT fecha_de_compra " +
                                "FROM orden_de_compra " +                                
                                "WHERE id_orden = ?";

            PreparedStatement stat = cn.prepareStatement(consulta);
            stat.setInt(1, id_orden);
            ResultSet result = stat.executeQuery();
               if (result.next()) {
                    fechaOrden = result.getDate("fecha_de_compra");
                }
            
            Font fontfechacompra = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);
            Paragraph fechacompra = new Paragraph("Fecha de Orden de Compra: " + fechaOrden, fontfechacompra);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(fechacompra);
            doc.add(new Paragraph(10f, " "));

            PdfPTable tabla = new PdfPTable(7);
            
            tabla.addCell("Id Producto");
            tabla.addCell("Nombre Del Producto");
            tabla.addCell("Precio unitario neto");
            tabla.addCell("Cantidad");
            tabla.addCell("Subtotal");
            tabla.addCell("IVA");
            tabla.addCell("Total");

            try {
                String query = "SELECT opc.id_producto, p.nombre_producto, opc.precio, opc.cantidad, (opc.precio * opc.cantidad) AS subtotal, (opc.precio * opc.cantidad) * 0.19 AS IVA, (opc.precio * opc.cantidad) * 1.19 AS total " +
                                    "FROM orden_compra_contiene_producto opc " +
                                    "INNER JOIN producto p ON opc.id_producto = p.id_producto " +                                   
                                    "WHERE opc.id_orden = ?";

                PreparedStatement statement = cn.prepareStatement(query);
                statement.setInt(1, id_orden);
               ResultSet rs = statement.executeQuery();

                if(rs.next()){

                    do{
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                        tabla.addCell(rs.getString(6));
                        tabla.addCell(rs.getString(7));
                    }while(rs.next());

                    doc.add(tabla);
                }

            // LABEL TOTAL IVA 
            String consulta2 = "SELECT subtotal " +
                                "FROM orden_de_compra " +                                
                                "WHERE id_orden = ?";

            PreparedStatement state = cn.prepareStatement(consulta2);
            state.setInt(1, id_orden);
            ResultSet result2 = state.executeQuery();
            int montosubtotal = 0;
               if (result2.next()) {
                    montosubtotal = result2.getInt("subtotal");
                }
            double iva = montosubtotal * 0.19;
            double total = montosubtotal + iva;
            
            doc.add(new Paragraph(10f, " "));
            Font fontTotal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Paragraph pgTotal = new Paragraph("Subtotal: " + montosubtotal + "\n IVA: " + iva + "\n Total: " + total, fontTotal);
            titulo.setAlignment(Element.ANNOTATION);
            doc.add(pgTotal);
       
            
                
            doc.close();
            JOptionPane.showMessageDialog(null, "PDF generado correctamente.");
            } catch (DocumentException | SQLException e) {
                System.out.println(e);
            }

        }catch (Exception e){
            System.out.println(e);
        }
        
    }
    

}




