package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Admin;
import utilidades.Texto;

public class AdminDAO {

    // - 1. Agrega un nuevo Admin: inserta primero en 'usuario', luego en 'admin'
    public boolean agregar(Admin admin) {
        String sqlUsuario = "INSERT INTO usuario (login, contrasena, id_rol) VALUES (?, ?, ?)";
        String sqlAdmin = "INSERT INTO admin (id_usuario, nombre, apellido) VALUES (?, ?, ?)";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false); // inicia transacción

            // 1.1. Insertar en tabla usuario
            try (PreparedStatement psUsuario = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                // El login se guarda en mayúsculas; la contraseña NO se toca.
                psUsuario.setString(1, Texto.mayus(admin.getLogin()));
                psUsuario.setString(2, admin.getContrasena());
                psUsuario.setInt(3, admin.getIdRol()); // asegúrate de setear el idRol antes de llamar a este método

                psUsuario.executeUpdate();

                // 1.2. Obtener el id generado
                try (ResultSet rs = psUsuario.getGeneratedKeys()) {
                    if (rs.next()) {
                        admin.setIdUsuario(rs.getInt(1));
                    }
                }
            }

            // 1.3. Insertar en tabla admin usando el id generado
            try (PreparedStatement psAdmin = con.prepareStatement(sqlAdmin)) {
                psAdmin.setInt(1, admin.getIdUsuario());
                psAdmin.setString(2, Texto.mayus(admin.getNombre()));
                psAdmin.setString(3, Texto.mayus(admin.getApellido()));
                psAdmin.executeUpdate();
            }

            con.commit(); // confirma ambas inserciones
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar administrador: " + e.getMessage());
            if (con != null) {
                try {
                    con.rollback(); // revierte todo si algo falla
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
        return false;
    }

    // - 2. Busca un Admin por ID (hace JOIN con usuario)
    public Admin buscarPorId(int idUsuario) {
        Admin admin = null;
        String sql = """
            SELECT u.id_usuario, u.login, u.contrasena, u.id_rol,
                   a.nombre, a.apellido
            FROM usuario u
            INNER JOIN admin a ON u.id_usuario = a.id_usuario
            WHERE u.id_usuario = ?
            """;
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                admin = mapearAdmin(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return admin;
    }

    // - 3. Busca por nombre (JOIN con usuario)
    public Admin buscarPorNombre(String nombre) {
        Admin admin = null;
        String sql = """
            SELECT u.id_usuario, u.login, u.contrasena, u.id_rol,
                   a.nombre, a.apellido
            FROM usuario u
            INNER JOIN admin a ON u.id_usuario = a.id_usuario
            WHERE a.nombre = ?
            """;
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                admin = mapearAdmin(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return admin;
    }

    // - 4. Actualiza un Admin: actualiza ambas tablas
    // - 3.b. Busca administradores por APELLIDOS (devuelve todos los que coincidan)
    /*
     * Mismo criterio que en DocenteDAO: se busca por apellido porque hay menos
     * duplicados que por nombre, y se devuelve una lista para poder avisar
     * cuántos se encontraron cuando hay más de uno.
     */
    public List<Admin> buscarPorApellido(String apellido) {

        List<Admin> encontrados = new ArrayList<>();

        if (apellido == null || apellido.trim().isEmpty()) {
            return encontrados;
        }

        for (Admin admin : listar()) {
            if (Texto.contiene(admin.getApellido(), apellido)) {
                encontrados.add(admin);
            }
        }

        return encontrados;
    }

    /*
     * OJO con el UPDATE de usuario: antes también actualizaba la contraseña.
     * El formulario deshabilita ese campo al seleccionar un renglón y le pone
     * el texto "(sin cambios)", así que cada vez que se modificaba un
     * administrador se le grababa esa cadena COMO contraseña y ya no podía
     * entrar. Se quitó del UPDATE para que se comporte igual que DocenteDAO y
     * AlumnoDAO, que nunca la tocan.
     */
    public boolean actualizar(Admin admin) {
        String sqlUsuario = "UPDATE usuario SET login=? WHERE id_usuario=?";
        String sqlAdmin = "UPDATE admin SET nombre=?, apellido=? WHERE id_usuario=?";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement psUsuario = con.prepareStatement(sqlUsuario)) {
                psUsuario.setString(1, Texto.mayus(admin.getLogin()));
                psUsuario.setInt(2, admin.getIdUsuario());
                psUsuario.executeUpdate();
            }

            try (PreparedStatement psAdmin = con.prepareStatement(sqlAdmin)) {
                psAdmin.setString(1, Texto.mayus(admin.getNombre()));
                psAdmin.setString(2, Texto.mayus(admin.getApellido()));
                psAdmin.setInt(3, admin.getIdUsuario());
                psAdmin.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar administrador: " + e.getMessage());
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
        return false;
    }

    // - 5. Elimina un Admin (elimina de admin y luego de usuario)
    public boolean eliminar(int idUsuario) {
        String sqlAdmin = "DELETE FROM admin WHERE id_usuario=?";
        String sqlUsuario = "DELETE FROM usuario WHERE id_usuario=?";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement psAdmin = con.prepareStatement(sqlAdmin)) {
                psAdmin.setInt(1, idUsuario);
                psAdmin.executeUpdate();
            }

            try (PreparedStatement psUsuario = con.prepareStatement(sqlUsuario)) {
                psUsuario.setInt(1, idUsuario);
                psUsuario.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar al administrador: " + e.getMessage());
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
        return false;
    }

    // - 6. Lista todos los administradores (JOIN con usuario)
    public List<Admin> listar() {
        List<Admin> lista = new ArrayList<>();
        String sql = """
            SELECT u.id_usuario, u.login, u.contrasena, u.id_rol,
                   a.nombre, a.apellido
            FROM usuario u
            INNER JOIN admin a ON u.id_usuario = a.id_usuario
            ORDER BY a.apellido, a.nombre
            """;
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearAdmin(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }

    // Método auxiliar para no repetir el mapeo de ResultSet -> Admin
    private Admin mapearAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setIdUsuario(rs.getInt("id_usuario"));
        admin.setLogin(rs.getString("login"));
        admin.setContrasena(rs.getString("contrasena"));
        admin.setIdRol(rs.getInt("id_rol"));
        admin.setNombre(rs.getString("nombre"));
        admin.setApellido(rs.getString("apellido"));
        return admin;
    }
}