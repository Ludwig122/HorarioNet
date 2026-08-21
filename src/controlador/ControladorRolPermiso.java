package controlador;

import dao.RolDAO;
import dao.PermisoDAO;
import dao.RolPermisoDAO;
import modelo.Rol;
import modelo.Permiso;
import modelo.RolPermiso;
import java.util.List;

public class ControladorRolPermiso {

    private RolDAO rolDAO = new RolDAO();
    private PermisoDAO permisoDAO = new PermisoDAO();
    private RolPermisoDAO rolPermisoDAO = new RolPermisoDAO();

    public List<Rol> obtenerRoles() {
        return rolDAO.listar();
    }

    public List<Permiso> obtenerPermisos() {
        return permisoDAO.listar();
    }

    public List<RolPermiso> listar() {
        return rolPermisoDAO.listar();
    }

    public List<RolPermiso> listarPorRol(int idRol) {
        return rolPermisoDAO.listarPorRol(idRol);
    }

    public boolean guardar(Rol rol, Permiso permiso) {
        if (rolPermisoDAO.existeAsignacion(rol.getIdRol(), permiso.getIdPermiso())) {
            return false; // ya existe -> tu form muestra "Ese permiso ya existe!"
        }
        return rolPermisoDAO.asignar(rol.getIdRol(), permiso.getIdPermiso());
    }

    public boolean eliminar(Rol rol, Permiso permiso) {
        // RolPermisoDAO.eliminar() pide el id_rol_permiso, no rol+permiso,
        // así que primero hay que encontrarlo
        List<RolPermiso> asignaciones = rolPermisoDAO.listarPorRol(rol.getIdRol());
        for (RolPermiso rp : asignaciones) {
            if (rp.getPermiso().getIdPermiso() == permiso.getIdPermiso()) {
                return rolPermisoDAO.eliminar(rp.getIdRolPermiso());
            }
        }
        return false;
    }
    // En ControladorRolPermiso
public boolean existeAsignacion(Rol rol, Permiso permiso) {
    return rolPermisoDAO.existeAsignacion(rol.getIdRol(), permiso.getIdPermiso());
}
}