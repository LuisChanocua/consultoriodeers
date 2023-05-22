package ConsultorioDEERS;

import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author luise Clase para pruebas con la conn de datos
 */
/*TODO LO QUE SE VE AQUI ES UN POCO DEL BACKEND QUE TENDRÁ LA APLICACION*/
public class Principal {

    static Scanner s = new Scanner(System.in);

    //objeto de la conn de datos
    static BaseConexion conn = new BaseConexion();
    static Inserciones insert = new Inserciones();
    static Consultas consultas = new Consultas();
    static Actualizaciones act = new Actualizaciones();

    public static void main(String[] args) throws SQLException {

        String id_datos_per_usuarios = "a";
        String rango = "a";

        while (true) {

            char op;

            System.out.println(
                    "a)Insertar datos per Usuario\n"
                    + "b)Conexion\n"
                    + "c)Recuperar Contraseña\n"
                    + "d)Insertar datos pacientes\n"
                    + "e)Consulta id y rango\n"
                    + "f)Generar Receta\n"
                    + "g)Expedientes Pacientes\n"
                    + "h)Comprobar Existencia Usuario\n"
                    + "i)Generar Receta");

            op = s.next().charAt(0);

            int id = 0;
            switch (op) {

                case 'a':

                    //Registra los datos personales dentro de la base y
                    //agarra la id para registrala en la tabla hija donde contiene los datos para
                    //iniciar sesion del usuario
                    id = insert.insertar_datosPerUsuarios("LUIS", "MORA", "CHANOCUA", 21, "4351207883", "na");
                    //System.out.println(id);
                    insert.insertar_datosIsUsuarios("esteban@correo.com", "1", "Admin", id, "Mama", "Alicia");

                    /*Despues de haber hecho al registo entra*/
                    break;

                //comprueba la conexion
                case 'b':
                    conn.comprobarConexion();
                    break;
                //recupera la pregunta con el correo
                case 'c':
                    System.out.print("Ingresa tu correo: ");
                    String correo = s.next();
                    String pregunta = consultas.consultaPreguntaRecuperacionYRespuesta(correo)[0];
                    String respuesta_pregunta = consultas.consultaPreguntaRecuperacionYRespuesta(correo)[1];

                    try {
                        conn.conn.close();

                    } catch (Exception e) {
                    }

                    System.out.println("Pregunta: " + pregunta);
//                System.out.println("Respuesta: " + respuesta_pregunta);
                    System.out.println("Inserte la Respuesta a la pregunta");
                    String respuesta = s.next();

//                System.out.println(respuesta);
                    if (respuesta_pregunta.equals(respuesta)) {
                        System.out.println("----Genera la nueva contraseña----");

                        String password = s.next();
                        act.actulizarContraseña(correo, password);
                    } else {
                        System.out.println("Respuesta de la pregunta incorrecta");
                    }

                    break;

                case 'd':
                    //insert datos pacientes
                    id = insert.insertar_datosPerPacientes("JESUS", "MORA", "CHANOCUA", 22, "4351207883", "SIN CALLE", "MASCULINO");
                    insert.insertar_datosFisPacientes("58", "na", "98", "1.78", "35", "na", id);
                    break;

                //consulta el rango y la ide del usuario
                case 'e':

                    System.out.println("Correo");
                    correo = s.next();
                    System.out.println("Password");
                    String password = s.next();

                    rango = consultas.consultaIdUsuarioRango(correo, password)[0];
                    id_datos_per_usuarios = consultas.consultaIdUsuarioRango(correo, password)[1];

                    try {
                        conn.conn.close();
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("No se pudo cerrar la base de datos");
                    }
                    break;

                case 'f':

                    RecetaForm receta = new RecetaForm(/*rango, id_datos_per_usuarios*/);
                    receta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    receta.setVisible(true);
                    break;

                case 'g':

                    PacientesExpedientes pacientes = new PacientesExpedientes();
                    pacientes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    pacientes.setVisible(true);

                    break;

                //comprueba que exista el usuario
                case 'h':
                    System.out.println("Ingrese el correo");
                    correo = s.next();

                    System.out.println("Ingrese el password");
                    password = s.next();
                    int i = consultas.comprobarExistenciaUsuario(correo, password);

                    if (i != 0) {
                        System.out.println("Usuario Existe");
                    } else {
                        System.out.println("Usuario No Existe");
                    }
                    break;
                
                    
                    /*generar los datos de receta*/
                case 'i':
//                    insert.insertarDatosReceta(1);
//                    
                    
                    break;
                default:
                    System.out.println("No encontrado");
                    break;
            }

        }

    }

}
