package controlador;

import dao.AlumnoDAO;
import dao.HistorialDAO;
import dao.HorarioDAO;
import modelo.Alumno;
import modelo.Historial;
import modelo.Horario;
import java.util.List;

/**
 * Controlador de alumnos.
 *
 * Además del CRUD, este controlador es el responsable de mantener el
 * HISTORIAL ACADÉMICO: cada vez que un alumno se da de alta o se le cambia el
 * grupo, el cuatrimestre o la carrera, se agrega un renglón al historial con
 * la fecha del movimiento.
 *
 * Así el administrador puede ver el recorrido completo de un alumno (por
 * ejemplo, que estuvo una semana en el grupo B y luego pasó al A) sin que
 * nadie tenga que capturarlo a mano.
 */
public class ControladorAlumno {

    private AlumnoDAO dao;
    private HistorialDAO historialDAO;
    private HorarioDAO horarioDAO;

    public ControladorAlumno() {
        dao = new AlumnoDAO();
        historialDAO = new HistorialDAO();
        horarioDAO = new HorarioDAO();
    }

    public boolean guardar(String login, String contrasena,
                            String nombre, String apellido, int idCarrera, int idGrupo, int idCuatri) {

        Alumno alumno = new Alumno();
        alumno.setLogin(login);
        alumno.setContrasena(contrasena);
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setIdCarrera(idCarrera);
        alumno.setIdGrupo(idGrupo);
        alumno.setIdCuatri(idCuatri);

        boolean registrado = dao.registrar(alumno);

        if (registrado) {
            // El DAO deja el id generado dentro del objeto
            registrarEnHistorial(alumno.getIdUsuario(), idCarrera, idGrupo, idCuatri);
        }

        return registrado;
    }

    public boolean modificar(int idUsuario, String login,
                              String nombre, String apellido, int idCarrera, int idGrupo, int idCuatri) {

        // Se consulta cómo estaba ANTES para saber si de verdad hubo un cambio
        // de grupo o cuatrimestre. Si el administrador solo corrigió un
        // apellido, no tiene caso ensuciar el historial.
        boolean huboCambio = cambioDeAdscripcion(idUsuario, idGrupo, idCuatri);

        Alumno alumno = new Alumno();
        alumno.setIdUsuario(idUsuario);
        alumno.setLogin(login);
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setIdCarrera(idCarrera);
        alumno.setIdGrupo(idGrupo);
        alumno.setIdCuatri(idCuatri);

        boolean actualizado = dao.actualizar(alumno);

        if (actualizado && huboCambio) {
            registrarEnHistorial(idUsuario, idCarrera, idGrupo, idCuatri);
        }

        return actualizado;
    }

    public boolean eliminar(int idUsuario) {
        return dao.eliminar(idUsuario);
    }

    public Alumno buscar(String login) {
        return dao.buscarPorLogin(login);
    }

    public Alumno buscarPorId(int idUsuario) {
        return dao.buscarPorId(idUsuario);
    }

    public List<Alumno> listar() {
        return dao.listar();
    }

    // ----------------------------------------------------------
    // Historial académico
    // ----------------------------------------------------------

    /**
     * ¿El grupo o el cuatrimestre son distintos de los que ya tenía?
     * Si el alumno todavía no tiene ningún movimiento registrado, se
     * considera que sí hay cambio para dejar constancia del estado inicial.
     */
    private boolean cambioDeAdscripcion(int idUsuario, int idGrupo, int idCuatri) {

        Historial ultimo = historialDAO.ultimoDeUsuario(idUsuario);

        if (ultimo == null) {
            return true;
        }

        return ultimo.getIdGrupo() != idGrupo || ultimo.getIdCuatri() != idCuatri;
    }

    /**
     * Agrega el movimiento al historial. Si todavía no existe el horario de
     * esa carrera/grupo/cuatrimestre, el registro se guarda igual con el
     * horario en null: lo importante es dejar constancia del cambio.
     */
    private void registrarEnHistorial(int idUsuario, int idCarrera, int idGrupo, int idCuatri) {

        Integer idHorario = null;

        if (idCarrera > 0) {
            Horario horario = horarioDAO.buscarPorCarreraGrupoCuatri(idCarrera, idGrupo, idCuatri);

            if (horario != null) {
                idHorario = horario.getIdHorario();
            }
        }

        Historial movimiento = new Historial(idUsuario, idGrupo, idCuatri, idHorario);

        if (!historialDAO.insertar(movimiento)) {
            // No se interrumpe la operación principal: el alumno ya quedó
            // guardado. Solo se avisa en consola para poder revisarlo.
            System.out.println("Aviso: no se pudo registrar el movimiento en el historial "
                    + "del alumno " + idUsuario);
        }
    }
}
