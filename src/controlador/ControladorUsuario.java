package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;
import java.util.List;

public class ControladorUsuario {
    private UsuarioDAO dao;

    public ControladorUsuario() {
        dao = new UsuarioDAO();
    }

    public boolean guardar(String login, String contrasena, int idRol) {
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setContrasena(contrasena);
        usuario.setIdRol(idRol);

        return dao.registrar(usuario);
    }

    public boolean modificar(int idUsuario, String login, String contrasena, int idRol) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setLogin(login);
        usuario.setContrasena(contrasena);
        usuario.setIdRol(idRol);

        return dao.actualizar(usuario);
    }

    public boolean eliminar(int idUsuario) {
        return dao.eliminar(idUsuario);
    }

    public Usuario buscar(String login) {
        return dao.buscarPorLogin(login);
    }

    public List<Usuario> listar() {
        return dao.listar();
    }
}