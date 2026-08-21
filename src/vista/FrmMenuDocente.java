package vista;

import javax.swing.JOptionPane;
import modelo.Usuario;
import utilidades.DesktopFondo;
import utilidades.Sesion;

/**
 * Menú del docente.
 *
 * El docente solo consulta horarios: no da de alta, no modifica y no elimina
 * nada. Por eso su menú trae únicamente la opción de consulta, que abre la
 * misma ventana FrmConsultaHorario que usa el administrador.
 *
 * Esa ventana es de solo lectura por diseño (no tiene botones de guardar,
 * modificar ni eliminar), así que no hace falta bloquear nada aquí: lo que
 * el docente no puede hacer, simplemente no existe en su pantalla.
 */
public final class FrmMenuDocente extends javax.swing.JFrame {

    private final DesktopFondo escritorio = new DesktopFondo();

    public FrmMenuDocente() {
        initComponents();
        // Ícono de la aplicación (esquina de la ventana y barra de tareas)
        utilidades.Iconos.aplicarA(this);
        this.setContentPane(escritorio);
        this.setExtendedState(MAXIMIZED_BOTH);

        menuBar.setOpaque(true);
        menuBar.setBackground(new java.awt.Color(97, 18, 51));
        menuBar.setForeground(new java.awt.Color(255, 255, 255));

        mostrarDocenteEnTitulo();
    }

    private void mostrarDocenteEnTitulo() {
        Usuario usuario = Sesion.getUsuario();

        if (usuario != null) {
            setTitle("HorarioNet - Docente: " + usuario.getLogin());
        }
    }

    private void abrirConsulta() {
        FrmConsultaHorario frm = new FrmConsultaHorario();
        escritorio.add(frm);
        frm.setVisible(true);

        try {
            frm.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            // No pasa nada si no se puede enfocar
        }
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                this, "¿Deseas cerrar tu sesión?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            Sesion.cerrarSesion();
            new Login().setVisible(true);
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dskEscritorio = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        opcHorario = new javax.swing.JMenu();
        itmConsultarHorario = new javax.swing.JMenuItem();
        opcSalir = new javax.swing.JMenu();
        itmSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HorarioNet - Docente");

        opcHorario.setBackground(new java.awt.Color(97, 18, 51));
        opcHorario.setForeground(new java.awt.Color(255, 255, 255));
        opcHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Horario48.png"))); // NOI18N
        opcHorario.setText("Horarios");
        opcHorario.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N

        itmConsultarHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/HorarioCheck24x24.png"))); // NOI18N
        itmConsultarHorario.setText("Consultar Horarios existentes");
        itmConsultarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmConsultarHorarioActionPerformed(evt);
            }
        });
        opcHorario.add(itmConsultarHorario);

        menuBar.add(opcHorario);

        opcSalir.setBackground(new java.awt.Color(97, 18, 51));
        opcSalir.setForeground(new java.awt.Color(255, 255, 255));
        opcSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logout.png"))); // NOI18N
        opcSalir.setText("Salir");
        opcSalir.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N

        itmSalir.setText("Cerrar sesión");
        itmSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSalirActionPerformed(evt);
            }
        });
        opcSalir.add(itmSalir);

        menuBar.add(opcSalir);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dskEscritorio, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dskEscritorio, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itmConsultarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmConsultarHorarioActionPerformed
        abrirConsulta();
    }//GEN-LAST:event_itmConsultarHorarioActionPerformed

    private void itmSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSalirActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_itmSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane dskEscritorio;
    private javax.swing.JMenuItem itmConsultarHorario;
    private javax.swing.JMenuItem itmSalir;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu opcHorario;
    private javax.swing.JMenu opcSalir;
    // End of variables declaration//GEN-END:variables
}
