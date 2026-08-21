package utilidades;

import modelo.Usuario;

public class Sesion {
    private static Usuario usuarioActivo;

    public static void setUsuario(Usuario usuario) {
        usuarioActivo = usuario;
    }

    public static Usuario getUsuario() {
        return usuarioActivo;
    }

    public static void cerrarSesion() {
        usuarioActivo = null;
    }
}