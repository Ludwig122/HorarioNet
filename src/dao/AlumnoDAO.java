package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Alumno;
import utilidades.Texto;
/*Usamos herencia ya que alumno, docente y admin son tipos de ususario, una especialización.
Esto no quiere decir que los DAO sean hijos, ya que cada uno tiene sus propios métodos
que no requieren al papá.
si así fuera usariamos el formmulario de ususario y ese formulario no encaja para nada.

Si tanto el modelo como los DAOs usaran herencia se llama "Herencia por convivencia"
y eso es una mala implementación --- según Claude, pues
*/

 //CADA DAO ADMINISTRA SU PROPIO CONJUNTO DE TABLAS
public class AlumnoDAO {

    /** Registra un nuevo alumno: primero en usuario, luego en alumno,
     * usando el mismo id_usuario para ambos (transacción todo-o-nada).
     */
    public boolean registrar(Alumno alumno) {
        String sqlUsuario = "INSERT INTO usuario(login, contrasena, id_rol) VALUES(?,?,3)";
        String sqlAlumno = "INSERT INTO alumno(id_usuario, nombre, apellido, id_carrera, id_grupo, id_cuatri) VALUES(?,?,?,?,?,?)";

        try (Connection con = Conexion.conectar()) {
            //con la instrucción de abajo le decimos "Esperame.no guardes nada todavía en automático, espera mis instrucciones
            con.setAutoCommit(false);

            // 1. Insertar en usuario (campos heredados de Usuario)
            int idUsuarioGenerado;
            //el PreparedStatement son RECURSOS, es decir... primero calamos con los recursos(o atributos) de ususario que son login 
            
            try (PreparedStatement ps1 = con.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS)) {
                // La matrícula se guarda en mayúsculas; la contraseña NO se toca.
                ps1.setString(1, Texto.mayus(alumno.getLogin()));
                ps1.setString(2, alumno.getContrasena());
                ps1.executeUpdate();

                ResultSet rs = ps1.getGeneratedKeys();
                if (rs.next()) {
                    idUsuarioGenerado = rs.getInt(1);
                } else {
                    con.rollback(); //Si la consulta está ocmpleta seguimos, si no, truncamos el proceso
                    return false;
                }
            }

            // 2. Insertar en alumno (campos propios), usando el id generado
            try (PreparedStatement ps2 = con.prepareStatement(sqlAlumno)) {
                ps2.setInt(1, idUsuarioGenerado);
                ps2.setString(2, Texto.mayus(alumno.getNombre()));
                ps2.setString(3, Texto.mayus(alumno.getApellido()));
                ps2.setInt(4, alumno.getIdCarrera());
                ps2.setInt(5, alumno.getIdGrupo());
                ps2.setInt(6, alumno.getIdCuatri());
                ps2.executeUpdate();
            }

            con.commit(); //Después d haber generado un ususario que es un alumno ahora si, le mandamos el commit
            alumno.setIdUsuario(idUsuarioGenerado); // dejamos el id ya asignado en el objeto
            return true;
            

        } catch (SQLException e) {
            System.out.println("Error al registrar alumno: " + e.getMessage());
        }
        return false;
    }

    /** Actualiza login/contraseña (en usuario) y datos propios (en alumno). */
    public boolean actualizar(Alumno alumno) {
        String sqlUsuario = "UPDATE usuario SET login=? WHERE id_usuario=?";
        String sqlAlumno = "UPDATE alumno SET nombre=?, apellido=?, id_carrera=?, id_grupo=?, id_cuatri=? WHERE id_usuario=?";

        try (Connection con = Conexion.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(sqlUsuario)) {
                ps1.setString(1, Texto.mayus(alumno.getLogin()));
                ps1.setInt(2, alumno.getIdUsuario());
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlAlumno)) {
                ps2.setString(1, Texto.mayus(alumno.getNombre()));
                ps2.setString(2, Texto.mayus(alumno.getApellido()));
                ps2.setInt(3, alumno.getIdCarrera());
                ps2.setInt(4, alumno.getIdGrupo());
                ps2.setInt(5, alumno.getIdCuatri());
                ps2.setInt(6, alumno.getIdUsuario());
                ps2.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar alumno: " + e.getMessage());
        }
        return false;
    }

    /** Elimina el alumno; al borrar en usuario, la FK en alumno se va detrás
     * SOLO si tienen ON DELETE CASCADE configurado. Si no, hay que borrar
     * primero de alumno y luego de usuario.
     */
    public boolean eliminar(int idUsuario) {
        String sqlAlumno = "DELETE FROM alumno WHERE id_usuario=?";
        String sqlUsuario = "DELETE FROM usuario WHERE id_usuario=?";

        try (Connection con = Conexion.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(sqlAlumno)) {
                ps1.setInt(1, idUsuario);
                ps1.executeUpdate();
            }
            try (PreparedStatement ps2 = con.prepareStatement(sqlUsuario)) {
                ps2.setInt(1, idUsuario);
                ps2.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar alumno: " + e.getMessage());
        }
        return false;
    }

    /** Busca un alumno por id_usuario (login/matrícula), uniendo usuario + alumno. */
    public Alumno buscarPorId(int idUsuario) {
        Alumno alumno = null;
        String sql = "SELECT u.id_usuario, u.login, u.contrasena, "
                   + "a.nombre, a.apellido, a.id_carrera, a.id_grupo, a.id_cuatri "
                   + "FROM usuario u JOIN alumno a ON u.id_usuario = a.id_usuario "
                   + "WHERE u.id_usuario = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alumno = mapearAlumno(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return alumno;
    }

    /** Busca un alumno por login (el login funciona como su identificador visible, ex-matrícula). */
    public Alumno buscarPorLogin(String login) {
        Alumno alumno = null;
        String sql = "SELECT u.id_usuario, u.login, u.contrasena, "
                   + "a.nombre, a.apellido, a.id_carrera, a.id_grupo, a.id_cuatri "
                   + "FROM usuario u JOIN alumno a ON u.id_usuario = a.id_usuario "
                   + "WHERE u.login = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Se busca en mayúsculas, que es como quedó guardada la matrícula.
            ps.setString(1, Texto.mayus(login));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alumno = mapearAlumno(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return alumno;
    }

    /** Lista todos los alumnos, uniendo usuario + alumno. */
    public List<Alumno> listar() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.login, u.contrasena, "
                   + "a.nombre, a.apellido, a.id_carrera, a.id_grupo, a.id_cuatri "
                   + "FROM usuario u JOIN alumno a ON u.id_usuario = a.id_usuario "
                   + "ORDER BY a.nombre";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearAlumno(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }

    /** Método de apoyo: arma un objeto Alumno a partir de una fila del ResultSet. */
    private Alumno mapearAlumno(ResultSet rs) throws SQLException {
        Alumno alumno = new Alumno();
        alumno.setIdUsuario(rs.getInt("id_usuario"));
        alumno.setLogin(rs.getString("login"));
        alumno.setContrasena(rs.getString("contrasena"));
        alumno.setNombre(rs.getString("nombre"));
        alumno.setApellido(rs.getString("apellido"));
        alumno.setIdCarrera(rs.getInt("id_carrera"));
        alumno.setIdGrupo(rs.getInt("id_grupo"));
        alumno.setIdCuatri(rs.getInt("id_cuatri"));
        return alumno;
    }
}