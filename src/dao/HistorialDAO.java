package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.Historial;

/**
 * HistorialDAO gestiona el historial académico de los alumnos.
 *
 * La tabla historial guarda id_usuario (PK de usuario/alumno), NO la
 * matrícula: el login sí se puede editar y dejaría los registros huérfanos.
 * Para que las consultas por matrícula sigan funcionando se hace un JOIN
 * con usuario, y de ahí se trae también el login para mostrarlo en pantalla.
 */
public class HistorialDAO {

    // Todos los ORDER BY desempatan con id_historial: fecha_registro es
    // datetime (precisión de segundos), así que dos movimientos hechos en el
    // mismo segundo empatarían y el orden quedaría al azar.

    // SELECT base reutilizado por todas las consultas.
    // Trae u.login como "matricula" para poder mostrarla sin consultas extra.
    private static final String SELECT_BASE
            = "SELECT h.id_historial, h.id_usuario, u.login AS matricula, "
            + "h.id_grupo, h.id_cuatri, h.id_horario, h.fecha_registro "
            + "FROM historial h "
            + "INNER JOIN usuario u ON u.id_usuario = h.id_usuario ";

    // Registrar un movimiento nuevo en el historial
    public boolean insertar(Historial historial) {

        String sql = "INSERT INTO historial "
                + "(id_usuario, id_grupo, id_cuatri, "
                + "id_horario, fecha_registro) "
                + "VALUES (?, ?, ?, ?, ?)";

        LocalDateTime fecha = historial.getFechaRegistro();

        if (fecha == null) {
            fecha = LocalDateTime.now();
            historial.setFechaRegistro(fecha);
        }

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, historial.getIdUsuario());
            ps.setInt(2, historial.getIdGrupo());
            ps.setInt(3, historial.getIdCuatri());

            // El horario puede no existir todavía para esa combinación
            if (historial.getIdHorario() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, historial.getIdHorario());
            }

            ps.setTimestamp(5, Timestamp.valueOf(fecha));

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                try (ResultSet claves = ps.getGeneratedKeys()) {

                    if (claves.next()) {
                        historial.setIdHistorial(claves.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al insertar el historial: "
                    + e.getMessage()
            );
        }

        return false;
    }

    // Obtener todos los registros del historial
    public List<Historial> listar() {

        List<Historial> registros = new ArrayList<>();

        String sql = SELECT_BASE + "ORDER BY h.fecha_registro DESC, h.id_historial DESC";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                registros.add(crearHistorial(rs));
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar el historial: "
                    + e.getMessage()
            );
        }

        return registros;
    }

    // Buscar un registro por su ID
    public Historial buscarPorId(int idHistorial) {

        String sql = SELECT_BASE + "WHERE h.id_historial = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idHistorial);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return crearHistorial(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al buscar el historial: "
                    + e.getMessage()
            );
        }

        return null;
    }

    /**
     * Obtener los registros de un alumno a partir de su matrícula.
     * La matrícula es el login del usuario, por eso se filtra sobre el JOIN.
     * Se conserva esta firma para no tocar FrmHistorial.
     */
    public List<Historial> listarPorMatricula(String matricula) {

        List<Historial> registros = new ArrayList<>();

        String sql = SELECT_BASE
                + "WHERE u.login = ? "
                + "ORDER BY h.fecha_registro DESC, h.id_historial DESC";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, matricula);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    registros.add(crearHistorial(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar el historial del alumno: "
                    + e.getMessage()
            );
        }

        return registros;
    }

    /**
     * Igual que listarPorMatricula pero con el ID directo. Conviene usar este
     * cuando ya se tiene el objeto Alumno en la mano: se ahorra el filtro
     * por login.
     */
    public List<Historial> listarPorUsuario(int idUsuario) {

        List<Historial> registros = new ArrayList<>();

        String sql = SELECT_BASE
                + "WHERE h.id_usuario = ? "
                + "ORDER BY h.fecha_registro DESC, h.id_historial DESC";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    registros.add(crearHistorial(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar el historial del alumno: "
                    + e.getMessage()
            );
        }

        return registros;
    }

    /**
     * Devuelve el último movimiento registrado de un alumno. Sirve para
     * comparar antes de insertar: si el grupo y el cuatrimestre son los
     * mismos, no hay cambio que registrar.
     */
    public Historial ultimoDeUsuario(int idUsuario) {

        String sql = SELECT_BASE
                + "WHERE h.id_usuario = ? "
                + "ORDER BY h.fecha_registro DESC, h.id_historial DESC "
                + "LIMIT 1";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return crearHistorial(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al obtener el último historial: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // Modificar un registro
    public boolean actualizar(Historial historial) {

        String sql = "UPDATE historial "
                + "SET id_usuario = ?, "
                + "id_grupo = ?, "
                + "id_cuatri = ?, "
                + "id_horario = ?, "
                + "fecha_registro = ? "
                + "WHERE id_historial = ?";

        LocalDateTime fecha = historial.getFechaRegistro();

        if (fecha == null) {
            fecha = LocalDateTime.now();
            historial.setFechaRegistro(fecha);
        }

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, historial.getIdUsuario());
            ps.setInt(2, historial.getIdGrupo());
            ps.setInt(3, historial.getIdCuatri());

            if (historial.getIdHorario() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, historial.getIdHorario());
            }

            ps.setTimestamp(5, Timestamp.valueOf(fecha));
            ps.setInt(6, historial.getIdHistorial());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al actualizar el historial: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // Eliminar un registro
    public boolean eliminar(int idHistorial) {

        String sql = "DELETE FROM historial "
                   + "WHERE id_historial = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idHistorial);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al eliminar el historial: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // Crear un objeto Historial con los datos del ResultSet
    private Historial crearHistorial(ResultSet rs)
            throws SQLException {

        Timestamp fechaSQL = rs.getTimestamp("fecha_registro");

        LocalDateTime fechaRegistro = null;

        if (fechaSQL != null) {
            fechaRegistro = fechaSQL.toLocalDateTime();
        }

        Historial historial = new Historial();

        historial.setIdHistorial(
                rs.getInt("id_historial")
        );

        historial.setIdUsuario(
                rs.getInt("id_usuario")
        );

        // Viene del JOIN con usuario, no de la tabla historial
        historial.setMatricula(
                rs.getString("matricula")
        );

        historial.setIdGrupo(
                rs.getInt("id_grupo")
        );

        historial.setIdCuatri(
                rs.getInt("id_cuatri")
        );

        // getInt regresa 0 cuando la columna es NULL, hay que preguntar
        int idHorario = rs.getInt("id_horario");
        historial.setIdHorario(rs.wasNull() ? null : idHorario);

        historial.setFechaRegistro(fechaRegistro);

        return historial;
    }
}
