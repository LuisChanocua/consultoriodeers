package ConsultorioDEERS;

import java.sql.*;
import java.util.HashSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
DOCUMENTACION - PROBLEMAS DEL CODIGO

METODO cargarPacientes();

A LA HORA DE RECARGAR CON EL BOTON DE ACTUALIZAR DEL JFRAMDE DE
EXPEDIENTE DE PACIENTES MANDAR ERROR, POR QUE DICE 
QUE LA BASE DE DATO ESTA CERRADA, PARA ESO SE OPTO POR MANTENER LA BASE ABIERTA


 */
/**
 *
 * Esta clase será la encargada de generar las consultas para obtencion de datos
 * o mostrarlos
 */
public class Consultas extends BaseConexion {

    /*Este metodo verificará que el ususario exista en la base de datos*/
    public int comprobarExistenciaUsuario(String correo, String password) throws SQLException {
        int i = 0;

        try {
            //arbrimos la conexion
            comprobarConexion();

            //se crea la consulta
            String sql = "SELECT * FROM datos_is_usuarios WHERE correo=? and password=?";

//            ejecutamos y mandamos los paramaetros de la consulta
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, correo);
            st.setString(2, password);

            //ejectutamos un result set para si es que encuentra resultados quiere
//            decir que si existe el usuario
            ResultSet rs = st.executeQuery();

            /*con la condicion si encontro resultados mandamos 
            un 1 como confirmacion, de lo contrario si no encontró manda un 0*/
            if (rs.next()) {

                return 1;
            } else {
                return 0;

            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error de consulta");
        }

        conn.close();
        return i;
    }

    /*Es metodo consultará la id y rango del usario que estará manejando el sistema 
    y asi mismo darle ciertos rangos*/
    public String[] consultaIdUsuarioRango(String correo, String password) throws SQLException {
        String[] datos = new String[2];

        try {

            //comprueba la conexion con la base de datos
            comprobarConexion();
            //consulta para extraer el rango y la id del usuario para que tenga permisos
            String sql = "SELECT rango, id_datos_per_usuarios FROM datos_is_usuarios WHERE correo=? and password=?";

            //mandamos los parametros y se prepara la consulta
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, correo);
            st.setString(2, password);

            //se ejecuta la consulta
            ResultSet rs = st.executeQuery();

            //si encuentra datos los manda y los alojamos en el string de retorno
            if (rs.next()) {
                datos[0] = rs.getString("rango");
                datos[1] = Integer.toString(rs.getInt("id_datos_per_usuarios"));

//                System.out.println("Rango: "+datos[0]);
//                System.out.println("Id: "+datos[1]);
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No exite el usuario, no se pudo consultar la Id y el rango del usuario");
        }

//        conn.close();
        return datos;
    }

    /*Este metodo mostrará la miVariable para la recuperacion de la contraseña, con lo
    cual la consultará y la retornora*/
    public String[] consultaPreguntaRecuperacionYRespuesta(String correo) throws SQLException {
        String[] datos = new String[2]; // Un arreglo para almacenar los dos datos

        try {
            comprobarConexion();

            String sql = "SELECT pregunta, respuesta_pregunta FROM datos_is_usuarios WHERE correo=?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, correo);

            ResultSet rs = st.executeQuery();
            // Obtener los datos de las dos columnas del primer resultado
            if (rs.next()) {
                String pregunta = rs.getString("pregunta");
                String respuesta = rs.getString("respuesta_pregunta");
                // Asignar los datos a las variables del arreglo
                datos[0] = pregunta;
                datos[1] = respuesta;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No se pudieron consultar los datos");
        }

        return datos;
    }

    /*Es metodo cargará los datos de los pacientes en una 
    jTable para que los usuarios los pueda visualizar*/
    public DefaultTableModel cargarDatosPacientes(JTable tabla) throws SQLException {

        //CREAMOS LA TABLA QUE VAMOS A RETORNAR
        DefaultTableModel model = new DefaultTableModel();

        try {
            //SE COMPRUEBA LA CONEXION
            comprobarConexion();

            //GENERAMOS LAS COLUMNAS PARA LA TABLA
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Ap. Paterno");
            model.addColumn("Ap. Materno");
            model.addColumn("Sexo");

            //CREAMOS NUESTRA CONSULTA
            String sql = "SELECT id_datos_per_pacientes, nombre, ap_paterno, ap_materno, edad, sexo FROM datos_per_pacientes";

//            EJECUTAMOS LAS CONSULTAS
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet result = st.executeQuery();

            /*CON LA AYUDA DE UN NEXT VAMOS
            ALOJANDO LOS RESULTADOS EN EL ARREGLO*/
            while (result.next()) {

                /*OBJETO QUE OBTIENE LOS DATOS DE LA BASE DE 
                DATOS*/
                Object[] row = new Object[5];
                row[0] = result.getObject("id_datos_per_pacientes");
                row[1] = result.getObject("nombre");
                row[2] = result.getObject("ap_paterno");
                row[3] = result.getObject("ap_materno");
                row[4] = result.getObject("sexo");

                //SE AÑADADE LA FILA AL MODELO DE LA TABLA
                model.addRow(row);
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No se pudieron mostrar los datos en la tabla");
        }

        //SE RETORNA EL MODELO
        return model;
    }

    /*ESTE METODO FUNCIONA COMO FILTRO PARA REALIZAR UNA
    BUSQUEDA DEL PACIENTE CON AYUDA DEL NOMBRE O APELLIDOS ,ID
    
     */
    public DefaultTableModel busquedaPaciente(String busqueda, JTable tabla) {

        DefaultTableModel model = new DefaultTableModel();

        try {
            comprobarConexion();

            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Ap. Paterno");
            model.addColumn("Ap. Materno");
            model.addColumn("Sexo");

            String sql = "SELECT id_datos_per_pacientes, nombre, ap_paterno, ap_materno, sexo FROM datos_per_pacientes WHERE nombre LIKE ? OR ap_paterno LIKE ? OR ap_materno LIKE ? OR id_datos_per_pacienteS LIKE ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, "%" + busqueda + "%");
            st.setString(2, "%" + busqueda + "%");
            st.setString(3, "%" + busqueda + "%");
            st.setString(4, "%" + busqueda + "%");

            ResultSet result = st.executeQuery();

            /*CON LA AYUDA DE UN NEXT VAMOS
            ALOJANDO LOS RESULTADOS EN EL ARREGLO*/
            while (result.next()) {

                /*OBJETO QUE OBTIENE LOS DATOS DE LA BASE DE 
                DATOS*/
                Object[] row = new Object[5];
                row[0] = result.getObject("id_datos_per_pacientes");
                row[1] = result.getObject("nombre");
                row[2] = result.getObject("ap_paterno");
                row[3] = result.getObject("ap_materno");
                row[4] = result.getObject("sexo");

                //SE AÑADADE LA FILA AL MODELO DE LA TABLA
                model.addRow(row);
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No se pudieron mostrar los datos en la tabla");
        }

        //se retorna le modelo de la tabla
        return model;
    }

}
