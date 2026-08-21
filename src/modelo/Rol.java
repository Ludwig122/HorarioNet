package modelo;

import java.util.ArrayList;
import java.util.List;

public class Rol {
    //zona de declaracion de atributos privados
    private int idRol;
    private String nombre;
    private String descripcion;

    // Un rol tiene muchos permisos
    private List<Permiso> permisos;

    public Rol() {
        permisos = new ArrayList<>();
    }

    public Rol(int id_rol, String nombre, String descripcion) {

        this.idRol = id_rol;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.permisos = new ArrayList<>();
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombre;
    }

    public void setNombreRol(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    // Agregar un permiso al rol
   public void agregarPermiso(Permiso permiso) {
      permisos.add(permiso);
    }

    @Override
    public String toString() {
        return nombre;
    }

    
}