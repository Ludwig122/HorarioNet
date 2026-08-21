package utilidades;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {

    private Image imagen;

    public PanelFondo() {
        imagen = new ImageIcon(getClass().getResource("/imagenes/fondoHorarioNet1920x1080.jpg")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}