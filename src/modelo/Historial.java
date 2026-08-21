package modelo;

import java.time.LocalDateTime;

/**
 * Representa un registro del historial académico de un alumno: la carrera,
 * grupo y cuatrimestre que cursó a partir de cierta fecha.
 *
 * Se guarda un renglón nuevo cada vez que el administrador da de alta al
 * alumno o le cambia grupo/cuatrimestre. Ordenando por fecha_registro se
 * reconstruye la línea de tiempo (el fin de un periodo es el inicio del
 * siguiente).
 *
 * OJO con el campo matricula: NO se guarda en la tabla historial. La tabla
 * almacena id_usuario (la PK, que nunca cambia); la matrícula es el login
 * del usuario y se trae con un JOIN solamente para mostrarla en pantalla.
 * Por eso es un campo de solo lectura: llenarlo no afecta lo que se inserta.
 */
public class Historial {

    private int idHistorial;
    private int idUsuario;          // sí se persiste (FK a alumno)
    private String matricula;       // solo lectura: usuario.login, viene del JOIN
    private int idGrupo;
    private int idCuatri;
    private Integer idHorario;      // puede ser null: el horario aún no se sube
    private LocalDateTime fechaRegistro;

    // Constructor vacío
    public Historial() {
    }

    // Constructor para registrar un movimiento nuevo
    public Historial(int idUsuario, int idGrupo,
            int idCuatri, Integer idHorario) {

        this.idUsuario = idUsuario;
        this.idGrupo = idGrupo;
        this.idCuatri = idCuatri;
        this.idHorario = idHorario;
        this.fechaRegistro = LocalDateTime.now();
    }

    // Constructor para recuperar registros de la base de datos
    public Historial(int idHistorial, int idUsuario,
            int idGrupo, int idCuatri, Integer idHorario,
            LocalDateTime fechaRegistro) {

        this.idHistorial = idHistorial;
        this.idUsuario = idUsuario;
        this.idGrupo = idGrupo;
        this.idCuatri = idCuatri;
        this.idHorario = idHorario;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Matrícula del alumno (usuario.login). Campo de solo lectura que llena
     * el DAO desde el JOIN; no se escribe en la tabla historial.
     */
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    /**
     * Puede ser null cuando todavía no existe el horario de esa
     * carrera/cuatri/grupo al momento de mover al alumno.
     */
    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public boolean tieneHorario() {
        return idHorario != null && idHorario > 0;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Historial{"
                + "idHistorial=" + idHistorial
                + ", idUsuario=" + idUsuario
                + ", matricula='" + matricula + '\''
                + ", idGrupo=" + idGrupo
                + ", idCuatri=" + idCuatri
                + ", idHorario=" + idHorario
                + ", fechaRegistro=" + fechaRegistro
                + '}';
    }
}
