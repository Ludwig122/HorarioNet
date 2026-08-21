package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Carrera;
import utilidades.Texto;

public class CarreraDAO {

    // Registrar una carrera nueva
    public boolean insertar(Carrera carrera) {

        String sql = "INSERT INTO carrera (nombre) VALUES (?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, Texto.mayus(carrera.getNombre()));

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                try (ResultSet claves = ps.getGeneratedKeys()) {

                    if (claves.next()) {
                        carrera.setIdCarrera(claves.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al insertar la carrera: " + e.getMessage()
            );
        }

        return false;
    }

    // Obtener todas las carreras
    public List<Carrera> listar() {

        List<Carrera> carreras = new ArrayList<>();

        String sql = "SELECT id_carrera, nombre "
                   + "FROM carrera "
                   + "ORDER BY nombre";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Carrera carrera = new Carrera();

                carrera.setIdCarrera(rs.getInt("id_carrera"));
                carrera.setNombre(rs.getString("nombre"));

                carreras.add(carrera);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar las carreras: " + e.getMessage()
            );
        }

        return carreras;
    }

    // Buscar una carrera por ID
    public Carrera buscarPorId(int idCarrera) {

        String sql = "SELECT id_carrera, nombre "
                   + "FROM carrera "
                   + "WHERE id_carrera = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCarrera);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Carrera carrera = new Carrera();

                    carrera.setIdCarrera(rs.getInt("id_carrera"));
                    carrera.setNombre(rs.getString("nombre"));

                    return carrera;
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al buscar la carrera: " + e.getMessage()
            );
        }

        return null;
    }

    // Modificar una carrera
    public boolean actualizar(Carrera carrera) {

        String sql = "UPDATE carrera "
                   + "SET nombre = ? "
                   + "WHERE id_carrera = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, Texto.mayus(carrera.getNombre()));
            ps.setInt(2, carrera.getIdCarrera());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al actualizar la carrera: " + e.getMessage()
            );

            return false;
        }
    }

    // Eliminar una carrera
    public boolean eliminar(int idCarrera) {

        String sql = "DELETE FROM carrera WHERE id_carrera = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCarrera);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al eliminar la carrera: " + e.getMessage()
            );

            return false;
        }
    }
}
