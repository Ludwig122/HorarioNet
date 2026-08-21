
package modelo;
//Se adaptó la nomenclatura a snakeCase; No le hace que no sea exactamente igual a la bd
public class Cuatri {
    private int idCuatri;
    private int numCuatri;
    
//-----Se agregó este método constructor por defecto para crear el objeto cuatri
    public Cuatri() {
    }

    public Cuatri(int numCuatri) {
        this.numCuatri = numCuatri;
    }

    public Cuatri(int idCuatri, int numCuatri) {
        this.idCuatri = idCuatri;
        this.numCuatri = numCuatri;
    }

    public int getIdCuatri() {
        return idCuatri;
    }

    public void setIdCuatri(int idCuatri) {
        this.idCuatri = idCuatri;
    }

    public int getNumCuatri() {
        return numCuatri;
    }

    public void setNumCuatri(int numCuatri) {
        this.numCuatri = numCuatri;
    }
//----------Aqui lud metió las manos y agregó este método con el fin de tener--------
//----------un arreglo de números de cuatrimestres en un combobox del form-----------
    @Override
    public String toString() {
        return String.valueOf(numCuatri);
    }
}