/*
Fecha: 25/06/2026
Autor: Dani
*/
package conexion;
//Clases java.sql para la conexion a la base de datos,driver de conexion y sentencias SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    //Constantes de conexion 
    private static final String URL = "jdbc:mysql://localhost:3306/horarionet?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /*Metodo que realiza la conexion a la base de datos, lo que retorna es el objeto
    de conexion realizado */
    
    public static  Connection conectar() {
        Connection conn = null;  //objeto de conexion llamado conn
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(  URL, USER, PASSWORD );  //aqui se hace la conexíon con mysql
            System.out.println("Conectado");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());   //si hay error nos avisa que paso
        }

        return conn;  
    }

    public Connection getConexion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
