/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConsultorioDEERS;

//librerias para la conexion de la base
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * Esta base se encarga de hacer la conexion con la base de datos asi como
 * abrir, cerrar y comprobar si está abierta
 */
public class BaseConexion {

    //comprueba la conexion
    Connection conn = null;

    public BaseConexion() {

    }
    //variables que identifican la base de datos
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://us-east.connect.psdb.cloud:3306/consultorio_deers";
    String usuario = "ssgl1fdcc8ztsd4m9s47";
    String password = "pscale_pw_ozDFgPOWr9uGJsSJe50uQkb0DRq7c17ppozMlYieS68";

    //Hace la conexion con la base de datos en la nube
    public void conexion() {
        try {
            conn = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
        }

    }

    //El metodo comprueba que la base de datos este abierta
    public void comprobarConexion() {
        
         //comprueba que la conexion no sea nula
        if (conn != null) {

            System.out.println("Conexion abierta");
        } 
        //si es nula, manda a llamar a la funcion que la abre
        else {
            System.out.println("Conexion Cerrada- Abriendoooooo");
            conexion();
        }
    }

}
