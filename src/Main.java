import javax.sql.ConnectionPoolDataSource;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Main {
    static String driver = "org.postgresql.Driver";
    static String dbname = "sushi";
    static String url = "jdbc:postgresql://10.4.3.195:5432/" + dbname;
    static String username = "eliam.villegas";
    static String password = "29805641";
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

    }

}