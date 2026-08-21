package dao;

import conexion.Conexion;        // Clase que establece la conexión con la base de datos.
import java.sql.Connection;        // Representa una conexión con la base de datos.
import java.sql.PreparedStatement; // Permite ejecutar consultas SQL parametrizadas.
import java.sql.ResultSet;         // Almacena los resultados de una consulta.
import java.sql.SQLException;      // Maneja los errores relacionados con SQL.
import java.util.ArrayList;        // Implementación de una lista dinámica.
import java.util.List;             // Interfaz para trabajar con listas.
import modelo.Rol;
import utilidades.Texto;                 // Modelo que representa un rol del sistema.

public class RolDAO {
    public Rol buscarPorId(int id_rol) {
        Rol rol = null;
        // Consulta SQL para buscar un rol.
        String sql = "SELECT * FROM rol WHERE id_rol=?";
        //Hacemos uso del metodo de conectar() que esta en la clase ConexionBD, se crea
        //el objeto conn  y el objeto ps para armar la sentencia SQL
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
          // establece  el idRol como parte de la consulta.
            ps.setInt(1, id_rol);
            // Ejecuta la consulta.
            ResultSet rs = ps.executeQuery();
            // Si encuentra el registro, crea el objeto Rol.
            if (rs.next()) {
                rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre"));
                rol.setDescripcion(rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return rol;
    }
    
    
    
    /** Registra un nuevo rol en la base de datos.
     * @param rol Objeto con la información del rol.
     * @return true si el registro fue exitoso.
     */
    public boolean registrar(Rol rol) {
        // Consulta SQL para insertar un nuevo rol.
        String sql = """
                INSERT INTO rol(nombre, descripcion)
                VALUES(?,?)
                """;
         //se establece conexion con la base de datos
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Asigna los valores del objeto Rol a la consulta.
            ps.setString(1, Texto.mayus(rol.getNombreRol()));
            ps.setString(2, Texto.mayus(rol.getDescripcion()));

            // Ejecuta la inserción.
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {

            // Muestra el error ocurrido.
            System.out.println("Error al registrar rol: " + e.getMessage());
        }
        return false;
    }

    /** Actualiza la información de un rol.
     * @param rol Rol con los datos actualizados.
     * @return true si la actualización fue exitosa. */
    public boolean actualizar(Rol rol) {

        // Consulta SQL para actualizar un rol.
        String sql = """
                UPDATE rol  SET nombre=?,
                    descripcion=?
                WHERE id_rol=?
                """;
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Asigna los nuevos valores.
            ps.setString(1, Texto.mayus(rol.getNombreRol()));
            ps.setString(2, Texto.mayus(rol.getDescripcion()));
            ps.setInt(3, rol.getIdRol());
            // Ejecuta la actualización.
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar rol: " + e.getMessage());
        }
        return false;
    }

    /** Elimina un rol utilizando su identificador.
     * @param idRol Identificador del rol.
     * @return true si el rol fue eliminado.     */
    public boolean eliminar(int idRol) {

        // Consulta SQL para eliminar un rol.
        String sql = "DELETE FROM rol WHERE id_rol=?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            // Ejecuta la eliminación.
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar rol: " + e.getMessage());
        }
        return false;
    }

   

    /** Busca un rol utilizando su nombre.
     * @param nombre Nombre del rol.
     * @return Objeto Rol si existe; en caso contrario, null.
     */
    public Rol buscarPorNombre(String nombre) {
        Rol rol = null;
        // Consulta SQL para buscar un rol por nombre.
        String sql = "SELECT * FROM rol WHERE nombre=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Se busca en mayúsculas, que es como quedó guardado el nombre.
            ps.setString(1, Texto.mayus(nombre));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre"));
                rol.setDescripcion(rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return rol;

    }

    /** Obtiene todos los roles registrados en la base de datos.
     * @return Lista de objetos Rol.  */
    public List<Rol> listar() {
        // Lista donde se almacenarán los roles.
        List<Rol> lista = new ArrayList<>();
        // Consulta SQL para obtener todos los roles.
        String sql = "SELECT * FROM rol ORDER BY nombre";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Recorre todos los registros encontrados.
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre"));
                rol.setDescripcion(rs.getString("descripcion"));
                // Agrega el rol a la lista.
                lista.add(rol);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Devuelve la lista de roles.
        return lista;
    }
        }