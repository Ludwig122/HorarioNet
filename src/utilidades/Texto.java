package utilidades;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Normalización de texto, en un solo lugar.
 *
 * Hay DOS operaciones distintas y es importante no confundirlas:
 *
 * 1. mayus() — lo que se GUARDA en la base de datos.
 *    Sube a mayúsculas y respeta los acentos y la eñe: "Pérez" queda
 *    "PÉREZ", "Muñoz" queda "MUÑOZ". El dato sigue estando bien escrito,
 *    nada más que en mayúsculas, así que se ve correcto en pantalla y en
 *    los reportes.
 *
 * 2. clave() — lo que se usa para COMPARAR al buscar.
 *    Sube a mayúsculas Y quita los acentos, pero NUNCA toca la eñe.
 *    Sirve para que "alvarez", "Alvarez" y "Álvarez" den todos la misma
 *    clave ("ALVAREZ") y el usuario encuentre al docente aunque no ponga
 *    el acento.
 *
 * La eñe se protege aparte porque en español NO es una N con acento: es
 * otra letra. Si se dejara pasar por el mismo filtro, "MUÑOZ" se
 * convertiría en "MUNOZ" y estaríamos empatando dos apellidos distintos.
 *
 * Ojo: mayus() NO debe aplicarse a dos cosas.
 *
 * A las CONTRASEÑAS, porque le quitaría fuerza a la clave: una que mezcla
 * mayúsculas y minúsculas tiene muchas más combinaciones posibles que una
 * de puras mayúsculas. (Se comprobó además que la colación actual de la
 * base, utf8mb4_general_ci, ya compara las contraseñas SIN distinguir
 * mayúsculas — o sea que "Clave123" y "CLAVE123" entran igual. Eso es un
 * problema aparte, anotado en CAMBIOS.md, pero es una razón más para no
 * tocarlas desde aquí.)
 *
 * Y a los NOMBRES DE ARCHIVO, porque en Linux "TICs_C3_A.pdf" y
 * "TICS_C3_A.PDF" son archivos diferentes y el sistema ya no encontraría
 * el horario.
 */
public class Texto {

    // Locale explícito: sin él, en una máquina configurada en turco la
    // "i" minúscula sube a "İ" y las comparaciones dejarían de cuadrar.
    private static final Locale ES = Locale.forLanguageTag("es");

    /** Marcador temporal para sacar la eñe del camino mientras se quitan
     *  los acentos. Se usa un carácter que nadie va a teclear. */
    private static final String MARCA_N = "\u0001";
    private static final String MARCA_n = "\u0002";

    /**
     * Lo que se guarda en la base de datos: sin espacios sobrantes, con
     * los espacios internos colapsados a uno solo y todo en mayúsculas.
     *
     * @param valor texto capturado por el usuario
     * @return el mismo texto en mayúsculas, o null si venía null
     */
    public static String mayus(String valor) {

        if (valor == null) {
            return null;
        }

        return valor.trim().replaceAll("\\s+", " ").toUpperCase(ES);
    }

    /**
     * Lo que se usa para comparar en las búsquedas: mayúsculas y sin
     * acentos, conservando la eñe.
     *
     * @param valor texto a normalizar
     * @return la clave de comparación; cadena vacía si venía null
     */
    public static String clave(String valor) {

        if (valor == null) {
            return "";
        }

        String texto = mayus(valor);

        // Se aparta la eñe antes de descomponer, para que el filtro de
        // acentos no se la lleve.
        texto = texto.replace("Ñ", MARCA_N).replace("ñ", MARCA_n);

        // NFD separa cada letra de su acento ("É" -> "E" + tilde suelta);
        // el reemplazo borra los acentos ya separados.
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return texto.replace(MARCA_N, "Ñ").replace(MARCA_n, "Ñ");
    }

    /**
     * ¿Estos dos textos son el mismo apellido/nombre, ignorando acentos y
     * mayúsculas? Se usa en las búsquedas por apellidos.
     */
    public static boolean coincide(String a, String b) {
        return clave(a).equals(clave(b));
    }

    /**
     * ¿El texto completo contiene lo que se buscó? Igual que coincide(),
     * pero permite búsquedas parciales: "ALVAREZ" encuentra a
     * "ALVAREZ LOPEZ".
     */
    public static boolean contiene(String textoCompleto, String buscado) {

        String buscadoClave = clave(buscado);

        if (buscadoClave.isEmpty()) {
            return false;
        }

        return clave(textoCompleto).contains(buscadoClave);
    }
}
