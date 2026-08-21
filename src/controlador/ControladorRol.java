package controlador;

import dao.RolDAO;
import modelo.Rol;
import java.util.List;

public class ControladorRol {

    private RolDAO rolDAO = new RolDAO();

    public boolean guardar(Rol rol) {
        // evita duplicar un rol con el mismo nombre
        if (rolDAO.buscarPorNombre(rol.getNombreRol()) != null) {
            return false;
        }
        return rolDAO.registrar(rol);
    }

    public boolean modificar(Rol rol) {
        return rolDAO.actualizar(rol);
    }

    public boolean eliminar(int idRol) {
        return rolDAO.eliminar(idRol);
    }

    public Rol buscarPorId(int idRol) {
        return rolDAO.buscarPorId(idRol);
    }

    public Rol buscarPorNombre(String nombre) {
        return rolDAO.buscarPorNombre(nombre);
    }

    public List<Rol> listar() {
        return rolDAO.listar();
    }
}
