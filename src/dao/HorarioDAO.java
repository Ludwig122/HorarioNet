package dao;

import conexion.Conexion;
import modelo.Horario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// HorarioDAO gestiona las operaciones de persistencia y consultas en la tabla horario.
// Cada horario es la imagen ya armada de una carrera+cuatri+grupo (combinación UNIQUE en la BD).

public class HorarioDAO {

    // Registrar un horario nuevo (sube la imagen para una carrera/cuatri/grupo)
    public boolean insertar(Horario horario) {

        String sql = "INSERT INTO horario (id_carrera, id_grupo, id_cuatri, imagen) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, horario.getIdCarrera());
            ps.setInt(2, horario.getIdGrupo());
            ps.setInt(3, horario.getIdCuatri());
            ps.setString(4, horario.getImagen());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                try (ResultSet claves = ps.getGeneratedKeys()) {

                    if (claves.next()) {
                        horario.setIdHorario(claves.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            // Si ya existe un horario para esa carrera+cuatri+grupo, salta aquí
            // por la restricción UNIQUE (uq_horario_combo).
            System.out.println("Error al insertar el horario: " + e.getMessage());
        }

        return false;
    }

    // Obtener todos los horarios registrados
    public List<Horario> listar() {

        List<Horario> horarios = new ArrayList<>();

        String sql = "SELECT id_horario, id_carrera, id_grupo, id_cuatri, imagen "
                   + "FROM horario "
                   + "ORDER BY id_carrera, id_cuatri, id_grupo";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                horarios.add(crearHorario(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los horarios: " + e.getMessage());
        }

        return horarios;
    }

    // Buscar un horario por su ID
    public Horario buscarPorId(int idHorario) {

        String sql = "SELECT id_horario, id_carrera, id_grupo, id_cuatri, imagen "
                   + "FROM horario "
                   + "WHERE id_horario = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idHorario);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return crearHorario(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el horario: " + e.getMessage());
        }

        return null;
    }

    // Buscar el horario propio de un alumno/docente a partir de su
    // carrera, grupo y cuatrimestre (esa combinación es UNIQUE en la BD,
    // así que como máximo regresa un solo horario).
    public Horario buscarPorCarreraGrupoCuatri(int idCarrera, int idGrupo, int idCuatri) {

        String sql = "SELECT id_horario, id_carrera, id_grupo, id_cuatri, imagen "
                   + "FROM horario "
                   + "WHERE id_carrera = ? AND id_grupo = ? AND id_cuatri = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCarrera);
            ps.setInt(2, idGrupo);
            ps.setInt(3, idCuatri);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return crearHorario(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el horario: " + e.getMessage());
        }

        return null;
    }

    // Listar todos los horarios que pertenecen a una carrera (útil para
    // llenar tablas/consultas filtradas por carrera en la vista de administrador).
    public List<Horario> listarPorCarrera(int idCarrera) {

        List<Horario> horarios = new ArrayList<>();

        String sql = "SELECT id_horario, id_carrera, id_grupo, id_cuatri, imagen "
                   + "FROM horario "
                   + "WHERE id_carrera = ? "
                   + "ORDER BY id_cuatri, id_grupo";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCarrera);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    horarios.add(crearHorario(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los horarios de la carrera: " + e.getMessage());
        }

        return horarios;
    }

    // Modificar un horario existente (reasigna carrera/grupo/cuatri y/o la imagen)
    public boolean actualizar(Horario horario) {

        String sql = "UPDATE horario "
                   + "SET id_carrera = ?, id_grupo = ?, id_cuatri = ?, imagen = ? "
                   + "WHERE id_horario = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, horario.getIdCarrera());
            ps.setInt(2, horario.getIdGrupo());
            ps.setInt(3, horario.getIdCuatri());
            ps.setString(4, horario.getImagen());
            ps.setInt(5, horario.getIdHorario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el horario: " + e.getMessage());
            return false;
        }
    }

    // Reemplazar solo la imagen de un horario ya existente (caso típico:
    // "Editar Horario" solo vuelve a subir el archivo, sin tocar carrera/grupo/cuatri)
    public boolean actualizarImagen(int idHorario, String imagen) {

        String sql = "UPDATE horario SET imagen = ? WHERE id_horario = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, imagen);
            ps.setInt(2, idHorario);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar la imagen del horario: " + e.getMessage());
            return false;
        }
    }

    // Eliminar un horario por su ID
    public boolean eliminar(int idHorario) {

        String sql = "DELETE FROM horario WHERE id_horario = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idHorario);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el horario: " + e.getMessage());
            return false;
        }
    }

    // Método de apoyo: arma un objeto Horario a partir de una fila del ResultSet
    private Horario crearHorario(ResultSet rs) throws SQLException {

        Horario horario = new Horario();

        horario.setIdHorario(rs.getInt("id_horario"));
        horario.setIdCarrera(rs.getInt("id_carrera"));
        horario.setIdGrupo(rs.getInt("id_grupo"));
        horario.setIdCuatri(rs.getInt("id_cuatri"));
        horario.setImagen(rs.getString("imagen"));

        return horario;
    }
}
