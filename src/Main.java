import javax.sql.ConnectionPoolDataSource;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Main {
    static String driver = "org.postgresql.Driver";
    static String dbname = "sushi";
    static String url = "jdbc:postgresql://10.4.3.195:5432/" + dbname;
    static String username = "sushi";
    static String password = "stKim72";
//    static String driver = "org.postgresql.Driver";
//    static String dbname = "sushi_prueba";
//    static String url = "jdbc:postgresql://localhost:5432/" + dbname;
//    static String username = "postgres";
//    static String password = "29805";
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conData;
        Statement stmData;
        ResultSet rsData;
        Class.forName(driver);
        conData = DriverManager.getConnection(url,username,password);
        stmData = conData.createStatement();
        try{
            stmData.execute("SELECT * FROM empleado");
        }
        catch (Exception e){
            System.out.println("No se pudo recuperar la tabla");
        }

        rsData = stmData.executeQuery("SELECT * FROM empleado");
        while(rsData.next()){
            String rut_empleado = rsData.getString("rut_empleado");
            String nombre = rsData.getString("nombre");
            String rol = rsData.getString("rol");
            System.out.println("RUT: " + rut_empleado + " Nombre: " + nombre + " Rol: " + rol);
        }
    }

}