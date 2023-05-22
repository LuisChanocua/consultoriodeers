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

}
