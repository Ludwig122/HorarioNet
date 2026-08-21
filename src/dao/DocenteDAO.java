package dao;                            // Nombre del paquete.

import conexion.Conexion;               // Clase que realiza la conexión con la Base de Datos.
import java.sql.Connection;             // Representa una Conexión con la Base de Datos.
import java.sql.PreparedStatement;      // Ejecuta consultas SQL parametrizadas.
import java.sql.ResultSet;              // Almacena los Resultados de una consulta SQL.
import java.sql.SQLException;           // Maneja los Errores relacionados con SQL.
import java.util.ArrayList;             // Implementación dinámica de una Lista.
import java.util.List;                  // Interfaz para trabajar con Listas.
import modelo.Docente;                  // Modelo que representa un Docente.
import utilidades.Texto;                // Normaliza el texto antes de guardarlo.

// Realizara las operaciones CRUD de la tabla Docente.
public class DocenteDAO {
    
    // - 1. Metodo que registra a un nuevo docente en la base de datos: 
    public boolean agregar(Docente docente) { 
        // 1.1. Consulta SQL para insertar a un nuevo docente:
        String sqlUsuario = "INSERT INTO usuario(login, contrasena, id_rol) VALUES(?,?,2)";
        String sqlDocente = "INSERT INTO docente(id_usuario, nombre, apellido) VALUES(?,?,?)";

        try (Connection con = Conexion.conectar()) {
            //con la instrucción de abajo le decimos "Esperame.no guardes nada todavía en automático, espera mis instrucciones
            con.setAutoCommit(false);

            // 1. Insertar en usuario (campos heredados de Usuario)
            int idUsuarioGenerado;
            //el PreparedStatement son RECURSOS, es decir... primero calamos con los recursos(o atributos) de ususario que son login 
            
            try (PreparedStatement ps1 = con.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS)) {
                // El login se guarda en mayúsculas; la contraseña NO se toca.
                ps1.setString(1, Texto.mayus(docente.getLogin()));
                ps1.setString(2, docente.getContrasena());
                ps1.executeUpdate();

                ResultSet rs = ps1.getGeneratedKeys();
                if (rs.next()) {
                    idUsuarioGenerado = rs.getInt(1);
                } else {
                    con.rollback(); //Si la consulta está ocmpleta seguimos, si no, truncamos el proceso
                    return false;
                }
            }

            // 2. Insertar en docente (campos propios), usando el id generado
            try (PreparedStatement ps2 = con.prepareStatement(sqlDocente)) {
                ps2.setInt(1, idUsuarioGenerado);
                ps2.setString(2, Texto.mayus(docente.getNombre()));
                ps2.setString(3, Texto.mayus(docente.getApellido()));
                ps2.executeUpdate();
            }

            con.commit(); //Después d haber generado un ususario que es un docente ahora si, le mandamos el commit
            docente.setIdUsuario(idUsuarioGenerado); // dejamos el id ya asignado en el objeto
            return true;
            

        } catch (SQLException e) {
            System.out.println("Error al registrar docente: " + e.getMessage());
        }
        return false;
    
    }                                              // Cierre de Agregar Docente.
    
    
    
    // - 2. Metodo que busca un Docente utilizando su ID (id_usuario):
    public Docente buscarPorId(int idUsuario) { 
        // 2.1. Se inicializa el objeto docente a null:
        Docente docente = null; 
        // 2.2. Consulta SQL (JOIN con usuario para traer login y contrasena):
        String sql = "SELECT u.id_usuario, u.login, u.contrasena, d.nombre, d.apellido "
                   + "FROM usuario u JOIN docente d ON u.id_usuario = d.id_usuario "
                   + "WHERE u.id_usuario = ?"; 
        // 2.3. Conexión a la BD y prepara la consulta SQL:
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) { 
            // 2.4. Establece el idUsuario como parte de la consulta:
            ps.setInt(1, idUsuario); 
            // 2.5. Ejecuta la consulta:
            ResultSet rs = ps.executeQuery();  
            // 2.6. Si existe un registro...
            if (rs.next()) { 
                docente = new Docente();           // Se crea el objeto docente.
                // 2.7. Se pasan los valores obtenidos de la BD al objeto docente:
                docente.setIdUsuario(rs.getInt("id_usuario"));
                docente.setLogin(rs.getString("login"));
                docente.setContrasena(rs.getString("contrasena"));
                docente.setNombre(rs.getString("nombre"));
                docente.setApellido(rs.getString("apellido"));
                
            } 
        // 2.8. Mensjae del error ocurrido:    
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        } 
        return docente;                         // Devuelve el docente encontrado.
    }                                                // Cierre de Buscar por ID.
    
    
    
    // - 3. Busca a un docente utilizando su nombre:
    public Docente buscarPorNombre(String nombre) { 
        // 3.1. Se inicializa el objeto docente a null:
        Docente docente = null;
        
        // 3.2. Consulta SQL para buscar a un docente por nombre (JOIN con usuario):
        String sql = "SELECT u.id_usuario, u.login, u.contrasena, d.nombre, d.apellido "
                   + "FROM usuario u JOIN docente d ON u.id_usuario = d.id_usuario "
                   + "WHERE d.nombre = ?"; 
        // 3.3. Conexion a la BD y prepara la consulta SQL:
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) { 
            
            //3.3. Establece el nombre como parte de la consulta:
            ps.setString(1, nombre); 
            // 3.4. Ejecuta la consulta:
            ResultSet rs = ps.executeQuery();
            
            // 3.5. Si existe un nombre...
            if (rs.next()) {
                docente = new Docente();
                docente.setIdUsuario(rs.getInt("id_usuario"));
                docente.setLogin(rs.getString("login"));
                docente.setContrasena(rs.getString("contrasena"));
                docente.setNombre(rs.getString("nombre")); 
                docente.setApellido(rs.getString("apellido")); 
               
            } 
        // 3.6. Mensaje de error:
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return docente;        // Objeto docente si existe, de lo contrario: null. 
    }                                            // Cierre de Buscar por Nombre.
    
    
    
    // - 3.b. Busca docentes por APELLIDOS (devuelve todos los que coincidan):
    /*
     * Se prefiere el apellido sobre el nombre porque hay muchísimos menos
     * duplicados: "José Juan" se repite un montón, "ÁLVAREZ SANDOVAL" casi
     * nunca. Aun así puede haber hermanos trabajando en la institución, y por
     * eso este método devuelve una LISTA en vez de un solo docente: el
     * formulario avisa cuántos encontró y deja escoger.
     *
     * El filtrado se hace en Java, no en SQL, por dos razones:
     *  - Se controla el acento sin depender de cómo esté configurada la
     *    colación de MariaDB (Texto.clave hace que "alvarez" encuentre a
     *    "ÁLVAREZ" sin confundir la Ñ con la N).
     *  - El catálogo de docentes es chico; traerlo completo no cuesta nada.
     */
    public List<Docente> buscarPorApellido(String apellido) {

        List<Docente> encontrados = new ArrayList<>();

        if (apellido == null || apellido.trim().isEmpty()) {
            return encontrados;
        }

        for (Docente docente : listar()) {
            if (Texto.contiene(docente.getApellido(), apellido)) {
                encontrados.add(docente);
            }
        }

        return encontrados;
    }                                       // Cierre de Buscar por Apellido.
    
    
    // - 4. Actualiza la información de un docente:
    public boolean actualizar(Docente docente) { 
        String sqlUsuario = "UPDATE usuario SET login=? WHERE id_usuario=?";
        String sqlDocente = "UPDATE docente SET nombre=?, apellido=? WHERE id_usuario=?";

        try (Connection con = Conexion.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(sqlUsuario)) {
                ps1.setString(1, Texto.mayus(docente.getLogin()));
                ps1.setInt(2, docente.getIdUsuario());
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlDocente)) {
                ps2.setString(1, Texto.mayus(docente.getNombre()));
                ps2.setString(2, Texto.mayus(docente.getApellido()));
                ps2.setInt(3, docente.getIdUsuario());
                ps2.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar docente: " + e.getMessage());
        }
        return false;
    }                                                   // Cierre de Actualizar.
    
    
    
    // - 5. Elimina a un Docente utilizando su ID:
    public boolean eliminar(int idUsuario) { 
        // 5.1. Consulta SQL para eliminar un docente:
        String sqlDocente = "DELETE FROM docente WHERE id_usuario=?";
        String sqlUsuario = "DELETE FROM usuario WHERE id_usuario=?";

        try (Connection con = Conexion.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(sqlDocente)) {
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
            System.out.println("Error al eliminar docente: " + e.getMessage());
        }
        return false;
    }                                                     // Cierre de Eliminar.
    
    
    
    // - 6. Obtiene todos los docentes registrados en la BD: 
    public List<Docente> listar() { 
        // 6.1. Lista donde se almacenarán los docentes:
        List<Docente> lista = new ArrayList<>();
        
        // 6.2. Consulta SQL para obtener todos los docentes (JOIN con usuario):
        String sql = "SELECT u.id_usuario, u.login, u.contrasena, d.nombre, d.apellido "
                   + "FROM usuario u JOIN docente d ON u.id_usuario = d.id_usuario "
                   + "ORDER BY d.apellido, d.nombre";
        // 6.3. Conexion a la BD y prepara la consulta SQL:
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
                
             // 6.4. Ejecuta la consulta:
             ResultSet rs = ps.executeQuery()) {
            // 6.5. Recorre todos los registros encontrados:
            while (rs.next()) {
                Docente docente = new Docente();
                docente.setIdUsuario(rs.getInt("id_usuario"));
                docente.setLogin(rs.getString("login"));
                docente.setContrasena(rs.getString("contrasena"));
                docente.setNombre(rs.getString("nombre"));
                docente.setApellido(rs.getString("apellido"));
                
                // 6.6. Agrega al docente a la lista:
                lista.add(docente);
            }
        // 6.7. Mensaje del error ocurrido:
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // 6.8. Devuelve la lista de docentes:
        return lista;
    }                                            // Cierre de Lista de Docentes.
    
}                                                          // Fin de DocenteDAO.