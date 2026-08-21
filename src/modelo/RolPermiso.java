package modelo;

/** Tabla puente ROL_PERMISO: qué permisos tiene cada rol. */
public class RolPermiso {
    private int idRolPermiso;
    private Rol rol;
    private Permiso permiso;

    public RolPermiso() {}

    public RolPermiso(int idRolPermiso, Rol rol, Permiso permiso) {
        this.idRolPermiso = idRolPermiso;
        this.rol = rol;
        this.permiso = permiso;
    }

    public int getIdRolPermiso() { return idRolPermiso; }
    public void setIdRolPermiso(int idRolPermiso) { this.idRolPermiso = idRolPermiso; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public Permiso getPermiso() { return permiso; }
    public void setPermiso(Permiso permiso) { this.permiso = permiso; }
}
