package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Grupo;
import utilidades.Texto;

public class GrupoDAO {

    // Registrar un grupo nuevo
    public boolean insertar(Grupo grupo) {

        String sql = "INSERT INTO grupo (letra) VALUES (?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, Texto.mayus(grupo.getLetra()));

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                try (ResultSet claves = ps.getGeneratedKeys()) {

                    if (claves.next()) {
                        grupo.setIdGrupo(claves.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al insertar el grupo: " + e.getMessage()
            );
        }

        return false;
    }

    // Obtener todos los grupos
    public List<Grupo> listar() {

        List<Grupo> grupos = new ArrayList<>();

        String sql = "SELECT id_grupo, letra "
                   + "FROM grupo "
                   + "ORDER BY letra";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Grupo grupo = new Grupo();

                grupo.setIdGrupo(rs.getInt("id_grupo"));
                grupo.setLetra(rs.getString("letra"));

                grupos.add(grupo);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar los grupos: " + e.getMessage()
            );
        }

        return grupos;
    }

    // Buscar un grupo por ID
    public Grupo buscarPorId(int idGrupo) {

        String sql = "SELECT id_grupo, letra "
                   + "FROM grupo "
                   + "WHERE id_grupo = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Grupo grupo = new Grupo();

                    grupo.setIdGrupo(rs.getInt("id_grupo"));
                    grupo.setLetra(rs.getString("letra"));

                    return grupo;
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al buscar el grupo: " + e.getMessage()
            );
        }

        return null;
    }

    // Buscar un grupo por su letra
    public Grupo buscarPorLetra(String letra) {

        String sql = "SELECT id_grupo, letra "
                   + "FROM grupo "
                   + "WHERE letra = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, Texto.mayus(letra));

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Grupo grupo = new Grupo();

                    grupo.setIdGrupo(rs.getInt("id_grupo"));
                    grupo.setLetra(rs.getString("letra"));

                    return grupo;
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al buscar el grupo por letra: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // Modificar un grupo
    public boolean actualizar(Grupo grupo) {

        String sql = "UPDATE grupo "
                   + "SET letra = ? "
                   + "WHERE id_grupo = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, Texto.mayus(grupo.getLetra()));
            ps.setInt(2, grupo.getIdGrupo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al actualizar el grupo: " + e.getMessage()
            );

            return false;
        }
    }

    // Eliminar un grupo
    public boolean eliminar(int idGrupo) {

        String sql = "DELETE FROM grupo WHERE id_grupo = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al eliminar el grupo: " + e.getMessage()
            );

            return false;
        }
    }
}