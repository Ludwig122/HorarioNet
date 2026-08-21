package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Permiso;
import utilidades.Texto;

public class PermisoDAO {

    // Registrar un permiso nuevo
    public boolean insertar(Permiso permiso) {

        String sql = "INSERT INTO permiso (nombre, descripcion) VALUES (?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, Texto.mayus(permiso.getNombre()));
            ps.setString(2, Texto.mayus(permiso.getDescripcion()));

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet claves = ps.getGeneratedKeys()) {
                    if (claves.next()) {
                        permiso.setIdPermiso(claves.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar el permiso: " + e.getMessage());
        }

        return false;
    }

    // Obtener todos los permisos
    public List<Permiso> listar() {

        List<Permiso> permisos = new ArrayList<>();

        String sql = "SELECT id_permiso, nombre, descripcion "
                   + "FROM permiso "
                   + "ORDER BY nombre";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Permiso permiso = new Permiso();
                permiso.setIdPermiso(rs.getInt("id_permiso"));
                permiso.setNombre(rs.getString("nombre"));
                permiso.setDescripcion(rs.getString("descripcion"));
                permisos.add(permiso);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los permisos: " + e.getMessage());
        }

        return permisos;
    }

    // Buscar un permiso por ID
    public Permiso buscarPorId(int idPermiso) {

        String sql = "SELECT id_permiso, nombre, descripcion "
                   + "FROM permiso "
                   + "WHERE id_permiso = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPermiso);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Permiso permiso = new Permiso();
                    permiso.setIdPermiso(rs.getInt("id_permiso"));
                    permiso.setNombre(rs.getString("nombre"));
                    permiso.setDescripcion(rs.getString("descripcion"));
                    return permiso;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el permiso: " + e.getMessage());
        }

        return null;
    }

    // Modificar un permiso
    public boolean actualizar(Permiso permiso) {

        String sql = "UPDATE permiso "
                   + "SET nombre = ?, descripcion = ? "
                   + "WHERE id_permiso = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, Texto.mayus(permiso.getNombre()));
            ps.setString(2, Texto.mayus(permiso.getDescripcion()));
            ps.setInt(3, permiso.getIdPermiso());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el permiso: " + e.getMessage());
            return false;
        }
    }

    // Eliminar un permiso
    public boolean eliminar(int idPermiso) {

        String sql = "DELETE FROM permiso WHERE id_permiso = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPermiso);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el permiso: " + e.getMessage());
            return false;
        }
    }
}
