
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");
        LocalDateTime fechaActual = LocalDateTime.now();
        return fechaActual.format(formatter);
    }
    
    public void reportOrdenCompra(){
        
    }
    

}




