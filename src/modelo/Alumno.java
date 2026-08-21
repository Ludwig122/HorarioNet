package modelo;

public class Alumno extends Usuario {
    private String nombre;
    private String apellido;
    private int idCarrera;
    private int idGrupo;
    private int idCuatri;

    public Alumno() {
        super();
    }

    public int getIdCarrera() { return idCarrera; }
    public void setIdCarrera(int idCarrera) { this.idCarrera = idCarrera; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }

    public int getIdCuatri() { return idCuatri; }
    public void setIdCuatri(int idCuatri) { this.idCuatri = idCuatri; }
}