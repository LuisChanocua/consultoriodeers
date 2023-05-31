package ConsultorioDEERS;

//librerias para las base de datos
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.sql.SQLException;
import java.util.Calendar;

public class Inserciones extends BaseConexion {

    PreparedStatement st;
    String sql;
    /*obtenemos la fecha actual*/
    java.sql.Date fechaActual = new java.sql.Date(Calendar.getInstance().getTime().getTime());

    //Registra los datos personales usuarios que manejaran el sistema
    public int insertar_datosPerUsuarios(String nombre, String ap_paterno, String ap_materno, int edad, String tel, String direccion) throws SQLException {

        //comprombamos que este abierta
        comprobarConexion();
        int lastId = -1;

        /*se hace la consulta dentro del try catch*/
        try {
            //consulta sql
            String sql = "INSERT INTO datos_per_usuarios (nombre, ap_paterno, ap_materno, edad, tel, direccion) VALUES (?, ?, ?, ?, ?, ?)";

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
            } else {
                System.out.println("No se pudieron actualizar los datos");
            }

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Error!, la llamada no pudo ser agregada a la base de datos.");
        }

        // Obtener el último ID insertado en la tabla 1
        ResultSet rs;

        //consulta para obtener el ultimo id
        String queryLastId = "SELECT LAST_INSERT_ID()";
        PreparedStatement stmtLastId = conn.prepareStatement(queryLastId);

        //si se ejecuto lo aloja en la variables rs
        if (stmtLastId.execute()) {
            rs = stmtLastId.getResultSet();

            //se aloja el valor de rs en la variable de retorno
            if (rs.next()) {
                lastId = rs.getInt(1);
            }
        }
        //se cierra la conexion de statment
        stmtLastId.close();

        /*esta vez no se cierra la conexion con la base porque vamos a consultar
        otro dato seguido de la llamda de este metodo*/
        //se regresa el ultimo id registrado para mandarlo a la tabla hija
        return lastId;
    }

    //Registra los datos en la tabla datos_is_usuarios para iniciar su sesion
    public void insertar_datosIsUsuarios(String correo, String password, String rango, int id_datos_per_usuarios, String pregunta, String respuesta_pregunta) throws SQLException {

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

    /*Registra los pacientes en la tabla datos_per_pacientes*/
 /*Las dos funciones a continuacion tienen la misma estructura padre hija de las 
    funciones insertar_datosPerPacientes, insertar datosFisPacientes*/
    public int insertar_datosPerPacientes(String nombre, String ap_paterno,
            String ap_materno, int edad, String tel, String direccion, String sexo) throws SQLException {
        int lastId = 0;
        try {
            comprobarConexion();

            String sql = "INSERT INTO datos_per_pacientes (nombre, ap_paterno, ap_materno, edad, tel, direccion, sexo) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, nombre);
            st.setString(2, ap_paterno);
            st.setString(3, ap_materno);
            st.setInt(4, edad);
            st.setString(5, tel);
            st.setString(6, direccion);
            st.setString(7, sexo);

            if (st.executeUpdate() > 0) {
                System.out.println("Datos Perosonales Pacientes Registrados correctamente");
            }
        } catch (Exception e) {
            System.out.println(e + " Error de insercion");
        }

        // Obtener el último ID insertado en la tabla 1
        ResultSet rs;
//        int rs =0;
        String queryLastId = "SELECT LAST_INSERT_ID()";
        PreparedStatement stmtLastId = conn.prepareStatement(queryLastId);

        if (stmtLastId.execute()) {
            rs = stmtLastId.getResultSet();

            if (rs.next()) {
                lastId = rs.getInt(1);
            }
        }
        stmtLastId.close();

        return lastId;

    }

    /*Es metodo insertará los datos fisicos de los pacientes*/
    public void insertar_datosFisPacientes(String peso, String presion_arterial, String oxigenacion, String estatura, String temperatura, String observaciones, int id_datos_per_pacientes) throws SQLException {

        try {

            comprobarConexion();

            String sql = "INSERT INTO datos_fis_pacientes (peso, presion_arterial, oxigenacion, estatura, temperatura, observaciones, id_datos_per_pacientes) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, peso);
            st.setString(2, presion_arterial);
            st.setString(3, oxigenacion);
            st.setString(4, estatura);
            st.setString(5, temperatura);
            st.setString(6, observaciones);
            st.setInt(7, id_datos_per_pacientes);

            if (st.executeUpdate() > 0) {
                System.out.println("Registrado Correctamente datos Fisicos del paciente");
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error de registro Datos Fisicos pacientes");
        }
        conn.close();
    }

    /*ESTE METODO SE ENCARGARÁ DE GUARDAR LAS RECETAS EN LAS BASE DE
    DATOS, DONDE RECIBE UN ARCHIVO FILE*/
    public int insertarReceta(File archivo) {
        /*variable que comprobara que si se haya registrado en la base*/
        int i = 0;

        System.out.println(fechaActual);

        //preparamos las variables para las inserciones
        PreparedStatement st = null;
        FileInputStream fis = null;

        //comienza la insercion de los datos
        try {
            //comprueba la conexion de la base de datos
            comprobarConexion();

            //se crea la consulta
            String sql = "INSERT INTO recetas (receta, fecha_anadido, id_datos_per_pacientes) VALUES (?,?,?)";
            st = conn.prepareStatement(sql);

            //mandamos los datos
            st.setString(1, archivo.getName());
            st.setDate(2, fechaActual);
            st.setInt(3, 1);

            // Lectura del archivo
            fis = new FileInputStream(archivo);
            st.setBinaryStream(1, fis, archivo.length());

            /*si se registran quiere decir que si se registró*/
            int filas_act = st.executeUpdate();
            if (filas_act > 0) {
                System.out.println("Archivo guardado en la base de datos");
                return 1;
            } else {
                System.out.println("No se puedo insertar el archivo");
            }
//            JOptionPane.showMessageDialog(this, "Archivo guardado en la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } //Cerramos todas la conexiones
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    /*ESTE METODO REGISTRARA LOS DATOS DE CREACION DE LA RECETA*/
    public int insertarDatosReceta(int id_datos_per_pacientes) throws SQLException {
        int i = 0;

        try {

            //comprobamos la conexion
            comprobarConexion();
            //creamos la consulta
            String sql = "INSERT INTO recetas (fecha_anadido, id_datos_per_pacientes) VALUES (?, ?)";

            /*EN ESTA CONSULTA LO QUE SE HACE ES HACER LA CONSULTA Y ADEMAS
            RETORNAR LA ULTIMA ID QUE SE REGISTRÓ*/
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //MANDAMOS LOS DATOS
            st.setDate(1, fechaActual);
            st.setInt(2, id_datos_per_pacientes);

            /*SI SE REGISTRA CORRECTAMENTE OBTENEMOS LA ULTIMA ID
            REGISTRADA*/
            if (st.executeUpdate() > 0) {
                System.out.println("Registro correctamente");

                /*EN EL RESULT SET OBTENEMOS LA ID*/
                ResultSet generatedKeys = st.getGeneratedKeys();

                //se obtiene la id
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    // Aquí tienes el ID del último registro insertado
                    System.out.println("Ultimo ID Registrado: " + id);
                }
            }

            return 1;

        } catch (Exception e) {
            System.out.println("No se pudieron insertar los datos");
        }

        conn.close();
        return i;
    }

}
