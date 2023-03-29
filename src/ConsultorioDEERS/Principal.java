package ConsultorioDEERS;

import java.util.Scanner;

/**
 *
 * @author luise Clase para pruebas con la base de datos
 */
public class Principal {

    static Scanner s = new Scanner(System.in);

    //objeto de la base de datos
    static BaseConexion base = new BaseConexion();

    public static void main(String[] args) {
        char op;

        System.out.println("a)Insertar Usuario\n"
                + "b)Conexion");

        op = s.next().charAt(0);

        switch (op) {

            case 'a':
                break;

            case 'b':
                base.comprobarConexion();
                break;

            default:
                System.out.println("No encontrado");
                break;
        }
    }

}
