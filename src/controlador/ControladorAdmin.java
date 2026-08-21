package controlador;

import dao.AdminDAO;
import modelo.Admin;
import java.util.List;

public class ControladorAdmin {
    private AdminDAO dao;

    public ControladorAdmin() {
        dao = new AdminDAO();
    }

    // Para un registro nuevo: no se pide idUsuario, se genera en la BD
    public boolean guardar(String login, String contrasena, String nombre, String apellido) {
        Admin admin = new Admin();
        admin.setLogin(login);
        admin.setContrasena(contrasena);
        admin.setNombre(nombre);
        admin.setApellido(apellido);
        admin.setIdRol(1); // 1 = Administrador (según tu tabla rol)
        return dao.agregar(admin);
    }

    public boolean modificar(int idUsuario, String login, String contrasena, String nombre, String apellido) {
        Admin admin = new Admin();
        admin.setIdUsuario(idUsuario);
        admin.setLogin(login);
        admin.setContrasena(contrasena);
        admin.setNombre(nombre);
        admin.setApellido(apellido);
        return dao.actualizar(admin);
    }

    public boolean eliminar(int idUsuario) {
        return dao.eliminar(idUsuario);
    }

    public Admin buscar(int idUsuario) {
        return dao.buscarPorId(idUsuario);
    }

    /**
     * Busca por apellidos. Devuelve una lista por si hay más de un
     * administrador con los mismos apellidos.
     */
    public List<Admin> buscarPorApellido(String apellido) {
        return dao.buscarPorApellido(apellido);
    }

    public List<Admin> listar() {
        return dao.listar();
    }
}