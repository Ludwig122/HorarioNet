package controlador;

import dao.HorarioDAO;
import modelo.Horario;
import java.util.List;

/**
 * Controlador para la gestión de horarios (opciones "Editar Horario" y
 * "Consultar Horarios existentes" del menú de administrador, además de la
 * consulta de horario propio que usan alumnos y docentes).
 */
public class ControladorHorario {

    private HorarioDAO horarioDAO = new HorarioDAO();

    // Publica un horario nuevo para una carrera/grupo/cuatri.
    // Devuelve false si ya existe un horario para esa combinación
    // (restricción UNIQUE) o si ocurre un error al guardar.
    public boolean guardar(int idCarrera, int idGrupo, int idCuatri, String imagen) {
        Horario horario = new Horario(idCarrera, idGrupo, idCuatri, imagen);
        return horarioDAO.insertar(horario);
    }

    // Modifica un horario existente (carrera/grupo/cuatri e imagen)
    public boolean modificar(int idHorario, int idCarrera, int idGrupo, int idCuatri, String imagen) {
        Horario horario = new Horario(idHorario, idCarrera, idGrupo, idCuatri, imagen);
        return horarioDAO.actualizar(horario);
    }

    // Reemplaza únicamente el archivo de imagen de un horario ya existente
    public boolean actualizarImagen(int idHorario, String imagen) {
        return horarioDAO.actualizarImagen(idHorario, imagen);
    }

    public boolean eliminar(int idHorario) {
        return horarioDAO.eliminar(idHorario);
    }

    public Horario buscarPorId(int idHorario) {
        return horarioDAO.buscarPorId(idHorario);
    }

    // Consulta el horario propio de un alumno/docente según su
    // carrera, grupo y cuatrimestre.
    public Horario consultarHorario(int idCarrera, int idGrupo, int idCuatri) {
        return horarioDAO.buscarPorCarreraGrupoCuatri(idCarrera, idGrupo, idCuatri);
    }

    public List<Horario> listar() {
        return horarioDAO.listar();
    }

    public List<Horario> listarPorCarrera(int idCarrera) {
        return horarioDAO.listarPorCarrera(idCarrera);
    }
}
