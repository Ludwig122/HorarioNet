package utilidades;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

public class DesktopFondo extends JDesktopPane {

    private Image imagen;

    public DesktopFondo() {
        imagen = new ImageIcon(getClass().getResource("/imagenes/fondoHorarioNet1920x1080v2.png")).getImage();
    }

 @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}