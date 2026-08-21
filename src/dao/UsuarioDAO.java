package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import conexion.Conexion;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import utilidades.Texto;

public class UsuarioDAO {

    public Usuario iniciarSesion(String login, String contrasena) {
        String sql = "SELECT id_usuario, login, contrasena, id_rol, ultimo_acceso, ultima_ip "
                    + "FROM usuario WHERE login = ? AND contrasena = ?";
        Usuario usuario = null;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // El login se normaliza a mayúsculas para que empate con lo
            // guardado; la contraseña se compara tal cual la escribió el usuario.
            ps.setString(1, Texto.mayus(login));
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setIdRol(rs.getInt("id_rol"));

                    Timestamp ts = rs.getTimestamp("ultimo_acceso");
                    if (ts != null) {
                        usuario.setUltimoAcceso(ts.toLocalDateTime());
                    }
                    usuario.setUltimaIp(rs.getString("ultima_ip"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }

        return usuario;
    }

    public void actualizarUltimoAcceso(int idUsuario, String host) {
        String sql = "UPDATE usuario SET ultimo_acceso = ?, ultima_ip = ? WHERE id_usuario = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, host);
            ps.setInt(3, idUsuario);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar último acceso: " + e.getMessage());
        }
    }
//---------------------------------------------------------------------------------------------------
// MÉTODOS RELACIONADOS AL FORMULARIO CRUD
    /** Registra un nuevo usuario en la base de datos.
 * @param usuario Objeto con la información del usuario.
 * @return true si el registro fue exitoso.
 */
public boolean registrar(Usuario usuario) {
    String sql = "INSERT INTO usuario(login, contrasena, id_rol) VALUES(?,?,?)";

    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, Texto.mayus(usuario.getLogin()));
        ps.setString(2, usuario.getContrasena());
        ps.setInt(3, usuario.getIdRol());

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Error al registrar usuario: " + e.getMessage());
    }
    return false;
}

/** Actualiza la información de un usuario.
 * @param usuario Usuario con los datos actualizados.
 * @return true si la actualización fue exitosa.
 */
public boolean actualizar(Usuario usuario) {
    String sql = "UPDATE usuario SET login=?, contrasena=?, id_rol=? WHERE id_usuario=?";

    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, Texto.mayus(usuario.getLogin()));
        ps.setString(2, usuario.getContrasena());
        ps.setInt(3, usuario.getIdRol());
        ps.setInt(4, usuario.getIdUsuario());

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Error al actualizar usuario: " + e.getMessage());
    }
    return false;
}

/** Elimina un usuario utilizando su identificador.
 * @param idUsuario Identificador del usuario.
 * @return true si el usuario fue eliminado.
 */
public boolean eliminar(int idUsuario) {
    String sql = "DELETE FROM usuario WHERE id_usuario=?";

    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Error al eliminar usuario: " + e.getMessage());
    }
    return false;
}

/** Busca un usuario utilizando su login.
 * @param login Login del usuario.
 * @return Objeto Usuario si existe; en caso contrario, null.
 */
public Usuario buscarPorLogin(String login) {
    Usuario usuario = null;
    String sql = "SELECT * FROM usuario WHERE login=?";

    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, Texto.mayus(login));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setLogin(rs.getString("login"));
            usuario.setContrasena(rs.getString("contrasena"));
            usuario.setIdRol(rs.getInt("id_rol"));
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
    return usuario;
}

/** Obtiene todos los usuarios registrados en la base de datos.
 * @return Lista de objetos Usuario.
 */
public List<Usuario> listar() {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT * FROM usuario ORDER BY login";

    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setLogin(rs.getString("login"));
            usuario.setContrasena(rs.getString("contrasena"));
            usuario.setIdRol(rs.getInt("id_rol"));
            lista.add(usuario);
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
    return lista;
}
}