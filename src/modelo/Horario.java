package modelo;

/**
 * Representa un horario publicado: es la imagen del horario ya armado
 * (captura/JPG/PNG) que corresponde a UNA combinación de carrera,
 * cuatrimestre y grupo (esa combinación es UNIQUE en la base de datos).
 *
 * No se guardan materias/días/horas sueltos: el horario completo vive
 * dentro del archivo de imagen; aquí solo referenciamos su ruta.
 */
public class Horario {

    private int idHorario;
    private int idCarrera;
    private int idGrupo;
    private int idCuatri;
    private String imagen; // ruta o nombre del archivo de imagen del horario

    // Constructor vacío
    public Horario() {
    }

    // Constructor sin ID, usado para registrar un horario nuevo
    public Horario(int idCarrera, int idGrupo, int idCuatri, String imagen) {
        this.idCarrera = idCarrera;
        this.idGrupo = idGrupo;
        this.idCuatri = idCuatri;
        this.imagen = imagen;
    }

    // Constructor con ID, usado para reconstruir un horario desde la base de datos
    public Horario(int idHorario, int idCarrera, int idGrupo, int idCuatri, String imagen) {
        this.idHorario = idHorario;
        this.idCarrera = idCarrera;
        this.idGrupo = idGrupo;
        this.idCuatri = idCuatri;
        this.imagen = imagen;
    }

    // getters y setters
    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdCuatri() {
        return idCuatri;
    }

    public void setIdCuatri(int idCuatri) {
        this.idCuatri = idCuatri;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Horario{"
                + "idHorario=" + idHorario
                + ", idCarrera=" + idCarrera
                + ", idGrupo=" + idGrupo
                + ", idCuatri=" + idCuatri
                + ", imagen='" + imagen + '\''
                + '}';
    }
}
