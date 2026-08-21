package utilidades;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;

/**
 * Maneja los archivos de horario (PDF o imagen) en el disco.
 *
 * ¿Por qué existe esta clase? Antes se guardaba en la base de datos la ruta
 * absoluta del archivo, algo como C:\Users\Lud\Escritorio\horario.pdf. Eso
 * solo funciona en la máquina donde se subió: en cualquier otra, el archivo
 * no está en esa ruta y el horario no se puede abrir.
 *
 * La solución es que la aplicación copie el archivo a su propia carpeta
 * "horarios" y guarde en la BD nada más el nombre del archivo. Así la ruta
 * se arma en tiempo de ejecución y el proyecto se puede mover completo
 * (carpeta horarios incluida) sin romper nada.
 */
public class Almacenamiento {

    // Carpeta donde viven los horarios, relativa a donde se ejecuta la app.
    // Al correr desde NetBeans queda dentro de la carpeta del proyecto.
    private static final String CARPETA = "horarios";

    /**
     * Devuelve la carpeta de horarios, creándola si no existe.
     */
    public static File carpeta() {

        File dir = new File(CARPETA);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    /**
     * Copia el archivo elegido por el administrador a la carpeta de horarios
     * y devuelve el NOMBRE con el que quedó guardado. Ese nombre es lo que se
     * guarda en la columna imagen de la tabla horario.
     *
     * El nombre se arma con carrera, cuatrimestre y grupo, así que siempre es
     * el mismo para la misma combinación: si el administrador vuelve a subir
     * el horario de TICs cuatri 3 grupo A, se reemplaza el anterior en vez de
     * acumular archivos sueltos.
     *
     * Devuelve null si algo falla al copiar.
     */
    public static String guardar(File origen, String carrera, int cuatri, String grupo) {

        if (origen == null || !origen.exists()) {
            System.out.println("El archivo de origen no existe.");
            return null;
        }

        String nombre = construirNombre(carrera, cuatri, grupo, extension(origen.getName()));

        Path destino = carpeta().toPath().resolve(nombre);

        try {
            // Si el origen ya es el archivo que está en la carpeta (por ejemplo
            // porque se le dio Modificar sin cambiar el PDF), no hay nada que copiar.
            if (origen.toPath().toAbsolutePath().equals(destino.toAbsolutePath())) {
                return nombre;
            }

            Files.copy(origen.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

            return nombre;

        } catch (IOException e) {
            System.out.println("Error al copiar el horario: " + e.getMessage());
            return null;
        }
    }

    /**
     * Convierte lo que está guardado en la BD en un File utilizable.
     *
     * Acepta los dos formatos por compatibilidad: los registros nuevos traen
     * solo el nombre del archivo y se resuelven contra la carpeta horarios;
     * los registros viejos que todavía tengan una ruta absoluta se usan tal
     * cual, para que no se rompan mientras se migran.
     */
    public static File obtener(String valorGuardado) {

        if (valorGuardado == null || valorGuardado.trim().isEmpty()) {
            return null;
        }

        String valor = valorGuardado.trim();

        File comoRuta = new File(valor);

        // Ruta absoluta o con carpetas: es un registro viejo
        if (comoRuta.isAbsolute() || valor.contains("/") || valor.contains("\\")) {
            return comoRuta;
        }

        return new File(carpeta(), valor);
    }

    /**
     * ¿El archivo de este horario está disponible en el disco?
     */
    public static boolean existe(String valorGuardado) {
        File archivo = obtener(valorGuardado);
        return archivo != null && archivo.exists();
    }

    /**
     * Abre el horario con el programa que el sistema tenga asociado: el lector
     * de PDF o el visor de imágenes. Desde ahí el usuario puede imprimirlo.
     *
     * Se usa esto porque Swing no sabe dibujar un PDF por sí solo.
     */
    public static boolean abrir(String valorGuardado) {

        File archivo = obtener(valorGuardado);

        if (archivo == null || !archivo.exists()) {
            System.out.println("El archivo del horario no está disponible.");
            return false;
        }

        if (!Desktop.isDesktopSupported()) {
            System.out.println("El sistema no permite abrir archivos desde la aplicación.");
            return false;
        }

        try {
            Desktop.getDesktop().open(archivo);
            return true;

        } catch (IOException e) {
            System.out.println("Error al abrir el horario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Copia el horario a donde el usuario haya elegido guardarlo.
     * Es lo que usa el botón "Descargar" del alumno.
     */
    public static boolean descargar(String valorGuardado, File destino) {

        File origen = obtener(valorGuardado);

        if (origen == null || !origen.exists()) {
            System.out.println("El archivo del horario no está disponible.");
            return false;
        }

        try {
            Files.copy(origen.toPath(), destino.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            return true;

        } catch (IOException e) {
            System.out.println("Error al descargar el horario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Borra el archivo del disco. Se llama cuando el administrador elimina un
     * horario, para no dejar archivos huérfanos en la carpeta.
     */
    public static boolean eliminar(String valorGuardado) {

        File archivo = obtener(valorGuardado);

        if (archivo == null || !archivo.exists()) {
            return false;
        }

        return archivo.delete();
    }

    /**
     * ¿Es un PDF? Sirve para decidir si se puede mostrar vista previa
     * (las imágenes sí, los PDF no).
     */
    public static boolean esPdf(String valorGuardado) {

        if (valorGuardado == null) {
            return false;
        }

        return valorGuardado.toLowerCase().endsWith(".pdf");
    }

    /**
     * Nombre sugerido para cuando el alumno descarga su horario, por ejemplo:
     * Horario_UTM251000PE_TICs_C3_A.pdf
     */
    public static String nombreDescarga(String matricula, String carrera,
            int cuatri, String grupo, String valorGuardado) {

        String ext = esPdf(valorGuardado) ? "pdf" : extension(valorGuardado);

        return "Horario_" + limpiar(matricula) + "_"
                + limpiar(carrera) + "_C" + cuatri + "_"
                + limpiar(grupo) + "." + ext;
    }

    // ----------------------------------------------------------
    // Apoyo interno
    // ----------------------------------------------------------

    private static String construirNombre(String carrera, int cuatri,
            String grupo, String extension) {

        return limpiar(carrera) + "_C" + cuatri + "_" + limpiar(grupo)
                + "." + extension;
    }

    /**
     * Quita acentos, espacios y cualquier carácter que pueda dar problemas en
     * un nombre de archivo. "Cinematografía" queda como "Cinematografia".
     */
    private static String limpiar(String texto) {

        if (texto == null || texto.trim().isEmpty()) {
            return "SinDato";
        }

        String sinAcentos = Normalizer
                .normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        String limpio = sinAcentos.replaceAll("[^a-zA-Z0-9]+", "");

        return limpio.isEmpty() ? "SinDato" : limpio;
    }

    private static String extension(String nombreArchivo) {

        if (nombreArchivo == null) {
            return "pdf";
        }

        int punto = nombreArchivo.lastIndexOf('.');

        if (punto == -1 || punto == nombreArchivo.length() - 1) {
            return "pdf";
        }

        return nombreArchivo.substring(punto + 1).toLowerCase();
    }

    /**
     * Ruta completa de la carpeta, por si la quieren mostrar en pantalla o en
     * un mensaje de error ("el archivo debería estar en...").
     */
    public static String rutaCarpeta() {
        return Paths.get(CARPETA).toAbsolutePath().toString();
    }
}
