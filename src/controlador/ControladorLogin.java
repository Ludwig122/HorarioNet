package controlador;

import java.net.InetAddress;
import dao.UsuarioDAO;
import modelo.Usuario;
import utilidades.Sesion;

public class ControladorLogin {

    private UsuarioDAO usuarioDAO;

    public ControladorLogin() {
        usuarioDAO = new UsuarioDAO();
    }

    public boolean iniciarSesion(String login, String contrasena) {
        //Este punto 0 es nuestra validación del sprint 1
                // 0. Validar que los campos no vengan vacíos (antes vivía en la Vista)
        if (login == null || login.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            System.out.println("Fallo la validación: login o contraseña vacíos");
            return false;
        }
        
        
        // 1. Buscar el usuario por login y contraseña
        Usuario usuario = usuarioDAO.iniciarSesion(login, contrasena);

        if (usuario == null) {
            System.out.println("Fallo la autenticación: credenciales incorrectas");
            return false;
        }

        // 2. Traducir id_rol a un tipo legible
        switch (usuario.getIdRol()) {
            case 1:
                usuario.setTipoUsuario("administrador");
                break;
            case 2:
                usuario.setTipoUsuario("docente");
                break;
            case 3:
                usuario.setTipoUsuario("alumno");
                break;
            default:
                System.out.println("Rol no reconocido: " + usuario.getIdRol());
                return false;
        }

        // 3. Guardar la sesión activa
        Sesion.setUsuario(usuario);

        // 4. Actualizar último acceso y host (esto dispara el trigger en BD)
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            usuarioDAO.actualizarUltimoAcceso(usuario.getIdUsuario(), host);
        } catch (Exception e) {
            System.out.println("No se pudo obtener el host: " + e.getMessage());
        }

        return true;
    }

}