package modelo;

public class Grupo {

    private int idGrupo;
    private String letra;

    // Constructor vacío
    public Grupo() {
    }

    // Para registrar un grupo nuevo
    public Grupo(String letra) {
        this.letra = letra;
    }

    // Para recuperar un grupo desde la base de datos
    public Grupo(int idGrupo, String letra) {
        this.idGrupo = idGrupo;
        this.letra = letra;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    @Override
    public String toString() {
        return letra;
    }
}