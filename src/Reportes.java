import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Reportes {
    
    public Reportes(){

        Document doc = new Document();

        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(doc, new FileOutputStream(ruta + "/Desktop/reporte_SM.pdf"));
            doc.open();

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("id venta");
            tabla.addCell("total");
            tabla.addCell("fecha de pago");
            tabla.addCell("iva");
            tabla.addCell("neto");
            try {
                Connection cn = DatabaseConnection.Getconnection();
                Statement statement = cn.createStatement(); 
                String query = "SELECT id_venta, total, fecha_pago, iva, neto FROM registro_de_venta WHERE activo = true ";
                ResultSet rs = statement.executeQuery(query);

                if(rs.next()){

                    do{
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));

                    }while(rs.next());

                    doc.add(tabla);
                }
                
                doc.close();
            } catch (DocumentException | SQLException e) {
                System.out.println(e);
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }   
}




