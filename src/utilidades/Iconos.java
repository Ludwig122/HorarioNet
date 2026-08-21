package utilidades;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * Ícono de la aplicación, en un solo lugar.
 *
 * Windows usa tamaños distintos según dónde se muestre el ícono (la esquina
 * de la ventana, la barra de tareas, Alt+Tab). Por eso se entregan las dos
 * medidas y el sistema elige la que le acomode: así se ve nítido en todos
 * lados en vez de escalado y borroso.
 *
 * Si algún día cambia el logo, se cambia aquí y se actualiza en toda la
 * aplicación.
 */
public class Iconos {

    private static final String RUTA_48 = "/imagenes/LogoColor48x48.png";
    private static final String RUTA_64 = "/imagenes/LogoColor64x64.png";

    /**
     * Las dos versiones del logo, para setIconImages() de las ventanas.
     */
    public static List<Image> deLaAplicacion() {

        List<Image> imagenes = new ArrayList<>();

        Image img48 = cargar(RUTA_48);
        Image img64 = cargar(RUTA_64);

        if (img48 != null) {
            imagenes.add(img48);
        }

        if (img64 != null) {
            imagenes.add(img64);
        }

        return imagenes;
    }

    /**
     * Versión chica, para el ícono de las ventanas internas (JInternalFrame),
     * que solo acepta una.
     */
    public static ImageIcon pequeno() {
        java.net.URL url = Iconos.class.getResource(RUTA_48);
        return url != null ? new ImageIcon(url) : null;
    }

    /**
     * Le pone el ícono a una ventana. Si el archivo no estuviera, la ventana
     * abre igual con el ícono por defecto de Java.
     */
    public static void aplicarA(java.awt.Window ventana) {

        List<Image> imagenes = deLaAplicacion();

        if (!imagenes.isEmpty()) {
            ventana.setIconImages(imagenes);
        }
    }

    /**
     * Le pone el ícono a una ventana interna.
     */
    public static void aplicarA(javax.swing.JInternalFrame ventana) {

        ImageIcon icono = pequeno();

        if (icono != null) {
            ventana.setFrameIcon(icono);
        }
    }

    private static Image cargar(String ruta) {
        java.net.URL url = Iconos.class.getResource(ruta);
        return url != null ? new ImageIcon(url).getImage() : null;
    }
}
