package modelo;

import java.time.LocalDateTime;

public class Usuario {
    private int idUsuario;
    private String login;
    private String contrasena;
    private int idRol;
    private String tipoUsuario; // <- Esto no es un atributo en la bd porque sería redundante, pero es aconsejable usarlo para tener un mejor control."administrador", "docente", "alumno" — se llena después del login
    private LocalDateTime ultimoAcceso;
    private String ultimaIp;

    public Usuario() {
    }

    public Usuario(int idUsuario, String login, String contrasena, int idRol) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.contrasena = contrasena;
        this.idRol = idRol;
    }

    // Getters y setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public LocalDateTime getUltimoAcceso() { return ultimoAcceso; }
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) { this.ultimoAcceso = ultimoAcceso; }

    public String getUltimaIp() { return ultimaIp; }
    public void setUltimaIp(String ultimaIp) { this.ultimaIp = ultimaIp; }
}
