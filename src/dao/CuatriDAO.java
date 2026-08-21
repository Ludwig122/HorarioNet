package dao;

import conexion.Conexion;
import modelo.Cuatri;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuatriDAO {

    public boolean insertar(Cuatri cuatri) {
        String sql = "INSERT INTO cuatri(num_cuatri) VALUES(?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cuatri.getNumCuatri());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar cuatrimestre: " + e.getMessage());
            return false;
        }
    }

    public List<Cuatri> listar() {
        List<Cuatri> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuatri";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cuatri cuatri = new Cuatri(rs.getInt("id_cuatri"), rs.getInt("num_cuatri"));
                lista.add(cuatri);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar cuatrimestres: " + e.getMessage());
        }
        return lista;
    }

    public Cuatri buscar(int id) {
        String sql = "SELECT * FROM cuatri WHERE id_cuatri=?";
        Cuatri cuatri = null;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cuatri = new Cuatri(rs.getInt("id_cuatri"), rs.getInt("num_cuatri"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cuatrimestre: " + e.getMessage());
        }
        return cuatri;
    }

    public boolean actualizar(Cuatri cuatri) {
        String sql = "UPDATE cuatri SET num_cuatri=? WHERE id_cuatri=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cuatri.getNumCuatri());
            ps.setInt(2, cuatri.getIdCuatri());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cuatrimestre: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM cuatri WHERE id_cuatri=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cuatrimestre: " + e.getMessage());
            return false;
        }
    }
}

// Dani me di cuenta que hiciste una travesurita... antes en el codigo tenías un e-printsatckedtrace cuando querías imprimir datos
//            e.printStackTrace();
//            return false;
// Y luego no me habías cerrado la conexón dani, se me iban a hacer fugas si el programa corría por mucho tiempo
// esas no son las buenas prácticas de la POO bb, te vouy a poner un tachecito en ClickUp :v