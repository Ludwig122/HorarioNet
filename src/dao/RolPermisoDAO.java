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
import modelo.Rol;
import modelo.RolPermiso;

/** DAO real de la tabla puente ROL_PERMISO: qué permisos tiene cada rol. */
public class RolPermisoDAO {

    // Asignar un permiso a un rol
    public boolean asignar(int idRol, int idPermiso) {

        String sql = "INSERT INTO rol_permiso (id_rol, id_permiso) VALUES (?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idRol);
            ps.setInt(2, idPermiso);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al asignar el permiso: " + e.getMessage());
            return false;
        }
    }

    // Verificar si un rol ya tiene asignado un permiso (para no duplicar)
    public boolean existeAsignacion(int idRol, int idPermiso) {

        String sql = "SELECT 1 FROM rol_permiso WHERE id_rol = ? AND id_permiso = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idRol);
            ps.setInt(2, idPermiso);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar la asignación: " + e.getMessage());
            return false;
        }
    }

    // Listar todas las asignaciones, trayendo el nombre de rol y de permiso
    public List<RolPermiso> listar() {

        List<RolPermiso> lista = new ArrayList<>();

        String sql = "SELECT rp.id_rol_permiso, "
                   + "r.id_rol, r.nombre AS rol_nombre, r.descripcion AS rol_descripcion, "
                   + "p.id_permiso, p.nombre AS permiso_nombre, p.descripcion AS permiso_descripcion "
                   + "FROM rol_permiso rp "
                   + "JOIN rol r ON rp.id_rol = r.id_rol "
                   + "JOIN permiso p ON rp.id_permiso = p.id_permiso "
                   + "ORDER BY r.nombre, p.nombre";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearRolPermiso(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los permisos por rol: " + e.getMessage());
        }

        return lista;
    }

    // Buscar todas las asignaciones de un rol específico
    public List<RolPermiso> listarPorRol(int idRol) {

        List<RolPermiso> lista = new ArrayList<>();

        String sql = "SELECT rp.id_rol_permiso, "
                   + "r.id_rol, r.nombre AS rol_nombre, r.descripcion AS rol_descripcion, "
                   + "p.id_permiso, p.nombre AS permiso_nombre, p.descripcion AS permiso_descripcion "
                   + "FROM rol_permiso rp "
                   + "JOIN rol r ON rp.id_rol = r.id_rol "
                   + "JOIN permiso p ON rp.id_permiso = p.id_permiso "
                   + "WHERE r.id_rol = ? "
                   + "ORDER BY p.nombre";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idRol);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearRolPermiso(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los permisos del rol: " + e.getMessage());
        }

        return lista;
    }

    // Quitar una asignación de permiso a un rol (por su id_rol_permiso)
    public boolean eliminar(int idRolPermiso) {

        String sql = "DELETE FROM rol_permiso WHERE id_rol_permiso = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idRolPermiso);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al quitar el permiso: " + e.getMessage());
            return false;
        }
    }

    // Mapeo auxiliar para no repetir código
    private RolPermiso mapearRolPermiso(ResultSet rs) throws SQLException {

        Rol rol = new Rol();
        rol.setIdRol(rs.getInt("id_rol"));
        rol.setNombreRol(rs.getString("rol_nombre"));
        rol.setDescripcion(rs.getString("rol_descripcion"));

        Permiso permiso = new Permiso();
        permiso.setIdPermiso(rs.getInt("id_permiso"));
        permiso.setNombre(rs.getString("permiso_nombre"));
        permiso.setDescripcion(rs.getString("permiso_descripcion"));

        RolPermiso rolPermiso = new RolPermiso();
        rolPermiso.setIdRolPermiso(rs.getInt("id_rol_permiso"));
        rolPermiso.setRol(rol);
        rolPermiso.setPermiso(permiso);

        return rolPermiso;
    }
}
