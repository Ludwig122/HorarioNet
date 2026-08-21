package modelo;

public class Docente extends Usuario {

    private String nombre;
    private String apellido;

// 2.2. NOMBRE:
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
// 2.3. APELLIDO:

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
