package modelo;

public class Admin extends Usuario {

    private String nombre;
    private String apellido;

    public Admin() {
    }

    public Admin(int idUsuario, String login, String contrasena, int idRol,
                 String nombre, String apellido) {
        super(idUsuario, login, contrasena, idRol); // usa el constructor del padre
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return nombre;
    }
}