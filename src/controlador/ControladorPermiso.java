package controlador;

import dao.PermisoDAO;
import modelo.Permiso;
import java.util.List;

public class ControladorPermiso {

    private PermisoDAO permisoDAO = new PermisoDAO();

    public boolean guardar(Permiso permiso) {
        return permisoDAO.insertar(permiso);
    }

    public boolean modificar(Permiso permiso) {
        return permisoDAO.actualizar(permiso);
    }

    public boolean eliminar(int idPermiso) {
        return permisoDAO.eliminar(idPermiso);
    }

    public Permiso buscarPorId(int idPermiso) {
        return permisoDAO.buscarPorId(idPermiso);
    }

    public List<Permiso> listar() {
        return permisoDAO.listar();
    }
}
