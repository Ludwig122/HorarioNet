package controlador;

import dao.DocenteDAO;
import modelo.Docente;
import java.util.List;

public class ControladorDocente {
    private DocenteDAO dao;

    public ControladorDocente() {
        dao = new DocenteDAO();
    }

    // Para un registro nuevo: no se pide idUsuario, se genera en la BD
    public boolean guardar(String login, String contrasena, String nombre, String apellido) {
        Docente docente = new Docente();
        docente.setLogin(login);
        docente.setContrasena(contrasena);
        docente.setNombre(nombre);
        docente.setApellido(apellido);
        return dao.agregar(docente);
    }

    public boolean modificar(int idUsuario, String login, String contrasena, String nombre, String apellido) {
        Docente docente = new Docente();
        docente.setIdUsuario(idUsuario);
        docente.setLogin(login);
        docente.setContrasena(contrasena);
        docente.setNombre(nombre);
        docente.setApellido(apellido);
        return dao.actualizar(docente);
    }

    public boolean eliminar(int idUsuario) {
        return dao.eliminar(idUsuario);
    }

    public Docente buscar(int idUsuario) {
        return dao.buscarPorId(idUsuario);
    }

    public Docente buscarPorNombre(String nombre) {
        return dao.buscarPorNombre(nombre);
    }

    /**
     * Busca por apellidos. Devuelve una lista porque puede haber hermanos
     * con los mismos dos apellidos; el formulario avisa cuántos salieron.
     */
    public List<Docente> buscarPorApellido(String apellido) {
        return dao.buscarPorApellido(apellido);
    }

    public List<Docente> listar() {
        return dao.listar();
    }
}