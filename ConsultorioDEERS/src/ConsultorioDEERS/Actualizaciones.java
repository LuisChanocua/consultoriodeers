package ConsultorioDEERS;

import java.sql.*;

/**
 * Es clase se encargará de hacer updates de datos
 *
 * @author luise
 */
public class Actualizaciones extends BaseConexion {

    public void actulizarContraseña(String correo, String password) {

        try {
            comprobarConexion();

            String sql = "UPDATE datos_is_usuarios SET password=? WHERE correo=?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, correo);

            int filasActualizadas = st.executeUpdate();

            System.out.println("Datos modificados: " + filasActualizadas);

            st.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No se puedo actualizar la contraseña");
        }

    }

    /*ESTE METODO PERMITIRAR ACTUALIZAR LOS DATOS PERSONALES DE LOS USUARIOS*/
    public int actualizar_datosPerUsuarios(String nombre, String ap_paterno, String ap_materno, int edad, String tel, String direccion) throws SQLException {

        //comprombamos que este abierta
        comprobarConexion();
        int i = 1;

        /*se hace la consulta dentro del try catch*/
        try {
            //consulta sql -falta modificar la consulta--
            String sql = "UPDATE datos_per_usuarios (nombre, ap_paterno, ap_materno, edad, tel, direccion) VALUES (?, ?, ?, ?, ?, ?)";

            //se prepara el statment
            PreparedStatement st = conn.prepareStatement(sql);

            //se mandan los parametros
            st.setString(1, nombre);
            st.setString(2, ap_paterno);
            st.setString(3, ap_materno);
            st.setInt(4, edad);
            st.setString(5, tel);
            st.setString(6, direccion);

            //se comprueba que si se haya registrado
            if (st.executeUpdate() > 0) {
                System.out.println("Datos Registrados correctamente");
                i = 1;
            } else {
                System.out.println("No se pudieron actualizar los datos");
                i = 0;
            }

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Error!, la llamada no pudo ser agregada a la base de datos.");
        }

        conn.close();
        return i;
    }

    /*ESTE METODO PERMITIRA ACTUALIZAR LOS DATOS DE IS DE SESION DE LOS USUARIOS*/
    public void actualizar_datosIsUsuarios(String correo, String password, String rango, int id_datos_per_usuarios, String pregunta, String respuesta_pregunta) throws SQLException {

        try {

            //se comprueba la conexion con la base de datos
            comprobarConexion();

            /*se realiza la consulta para hacer el registro el tabla hija*/
            String sql = "INSERT INTO datos_is_usuarios (correo, password, rango, id_datos_per_usuarios, pregunta, respuesta_pregunta) VALUES (?,?,?,?,?,?)";

            //se prepara la consulta
            PreparedStatement st = conn.prepareStatement(sql);

            //mandamos los parametros
            st.setString(1, correo);
            st.setString(2, password);
            st.setString(3, rango);
            st.setInt(4, id_datos_per_usuarios);
            st.setString(5, pregunta);
            st.setString(6, respuesta_pregunta);

            //comprobamos que se haga el registro
            if (st.executeUpdate() > 0) {
                System.out.println("Registro Completado Correctamente");

            } else {
                System.out.println("No se pudieron registrar los datos is usuario");
            }

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Error!, la llamada no pudo ser agregada a la base de datos.");
        }

        /*y como se termina las consultas padre hija ahora si se cierra la conexion*/
        conn.close();
    }
    
    
    /*ESTE METODO ACTUALIZA LOS DATOS PERSONALES DE LOS PACIENTES*/
    
}
