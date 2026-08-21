package vista;

import controlador.ControladorHorario;
import dao.AlumnoDAO;
import dao.CarreraDAO;
import dao.CuatriDAO;
import dao.GrupoDAO;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import modelo.Alumno;
import modelo.Carrera;
import modelo.Cuatri;
import modelo.Grupo;
import modelo.Horario;
import modelo.Usuario;
import utilidades.Almacenamiento;
import utilidades.Sesion;

/**
 * Pantalla del alumno. Es lo único que ve cuando entra al sistema.
 *
 * Muestra sus datos (matrícula, nombre, carrera, cuatrimestre y grupo) y el
 * horario que le corresponde, con dos acciones: verlo o descargarlo. La
 * descarga sirve para imprimirlo o llevarlo como comprobante.
 *
 * El alumno no elige nada: la carrera, el grupo y el cuatrimestre salen de su
 * propio registro, y con esa combinación se busca el horario (esa terna es
 * UNIQUE en la base de datos, así que solo puede haber uno).
 */
public class FrmHorarioAlumno extends javax.swing.JFrame {

    private final ControladorHorario controladorHorario = new ControladorHorario();
    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private final CarreraDAO carreraDAO = new CarreraDAO();
    private final GrupoDAO grupoDAO = new GrupoDAO();
    private final CuatriDAO cuatriDAO = new CuatriDAO();

    private Alumno alumno;
    private Horario horario;

    public FrmHorarioAlumno() {
        initComponents();
        // Ícono de la aplicación (esquina de la ventana y barra de tareas)
        utilidades.Iconos.aplicarA(this);
        setLocationRelativeTo(null);
        cargarDatosDelAlumno();
    }

    // ----------------------------------------------------------
    // Carga inicial
    // ----------------------------------------------------------

    private void cargarDatosDelAlumno() {

        Usuario usuario = Sesion.getUsuario();

        if (usuario == null) {
            JOptionPane.showMessageDialog(this,
                    "No hay una sesión activa.",
                    "Sesión no válida", JOptionPane.ERROR_MESSAGE);
            regresarAlLogin();
            return;
        }

        alumno = alumnoDAO.buscarPorId(usuario.getIdUsuario());

        if (alumno == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró tu registro de alumno.\n"
                    + "Acude con el administrador del sistema.",
                    "Datos no encontrados", JOptionPane.ERROR_MESSAGE);
            deshabilitarAcciones();
            return;
        }

        lblBienvenida.setText("Hola, " + alumno.getNombre() + " " + alumno.getApellido());
        txtMatricula.setForeground(new java.awt.Color(0, 0, 0));
        txtMatricula.setText(usuario.getLogin());
        txtNombre.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre.setText(alumno.getNombre() + " " + alumno.getApellido());
        txtCarrera.setForeground(new java.awt.Color(0, 0, 0));
        txtCarrera.setText(nombreCarrera(alumno.getIdCarrera()));
        txtCuatri.setForeground(new java.awt.Color(0, 0, 0));
        txtCuatri.setText(nombreCuatri(alumno.getIdCuatri()));
        txtGrupo.setForeground(new java.awt.Color(0, 0, 0));
        txtGrupo.setText(nombreGrupo(alumno.getIdGrupo()));

        buscarHorario();
    }

    private String nombreCarrera(int id) {
        // id_carrera permite null en la BD; el DAO lo devuelve como 0
        if (id <= 0) {
            return "No asignada";
        }
        Carrera c = carreraDAO.buscarPorId(id);
        return c != null ? c.getNombre() : "No asignada";
    }

    private String nombreGrupo(int id) {
        Grupo g = grupoDAO.buscarPorId(id);
        return g != null ? g.getLetra() : "No asignado";
    }

    private String nombreCuatri(int id) {
        Cuatri c = cuatriDAO.buscar(id);
        return c != null ? String.valueOf(c.getNumCuatri()) : "No asignado";
    }

    /**
     * Busca el horario de la carrera/grupo/cuatrimestre del alumno y ajusta
     * la pantalla según lo que se haya encontrado.
     */
    private void buscarHorario() {

        if (alumno.getIdCarrera() <= 0) {
            mostrarSinHorario("Todavía no tienes una carrera asignada.\n"
                    + "Acude con el administrador para que la registre.");
            return;
        }

        horario = controladorHorario.consultarHorario(
                alumno.getIdCarrera(),
                alumno.getIdGrupo(),
                alumno.getIdCuatri());

        if (horario == null) {
            mostrarSinHorario("Aún no se ha publicado el horario de tu grupo.\n"
                    + "Vuelve a consultarlo más adelante.");
            return;
        }

        if (!Almacenamiento.existe(horario.getImagen())) {
            mostrarSinHorario("El horario de tu grupo está registrado, pero el "
                    + "archivo no se encuentra disponible.\n"
                    + "Repórtalo con el administrador.");
            return;
        }

        lblEstado.setText("Horario disponible: " + horario.getImagen());
        lblEstado.setForeground(new java.awt.Color(16, 122, 70));
        btnVer.setEnabled(true);
        btnDescargar.setEnabled(true);
    }

    private void mostrarSinHorario(String mensaje) {
        lblEstado.setText("<html>" + mensaje.replace("\n", "<br>") + "</html>");
        lblEstado.setForeground(new java.awt.Color(150, 60, 30));
        deshabilitarAcciones();
    }

    private void deshabilitarAcciones() {
        btnVer.setEnabled(false);
        btnDescargar.setEnabled(false);
    }

    // ----------------------------------------------------------
    // Acciones
    // ----------------------------------------------------------

    private void verHorario() {

        if (horario == null) {
            return;
        }

        if (!Almacenamiento.abrir(horario.getImagen())) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo abrir el horario.\n"
                    + "Verifica que tengas instalado un lector de PDF.",
                    "Error al abrir", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Copia el archivo del horario a donde el alumno lo quiera guardar,
     * con un nombre que lo identifica (matrícula, carrera, cuatri y grupo).
     */
    private void descargarHorario() {

        if (horario == null) {
            return;
        }

        String sugerido = Almacenamiento.nombreDescarga(
                txtMatricula.getText(),
                txtCarrera.getText(),
                alumno.getIdCuatri(),
                txtGrupo.getText(),
                horario.getImagen());

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar mi horario");
        selector.setSelectedFile(new File(sugerido));

        if (selector.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File destino = selector.getSelectedFile();

        if (destino.exists()) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "Ya existe un archivo con ese nombre. ¿Deseas reemplazarlo?",
                    "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }
        }

        if (Almacenamiento.descargar(horario.getImagen(), destino)) {

            int opcion = JOptionPane.showConfirmDialog(this,
                    "Tu horario se guardó en:\n" + destino.getAbsolutePath()
                    + "\n\n¿Deseas abrirlo ahora para imprimirlo?",
                    "Descarga completa", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            if (opcion == JOptionPane.YES_OPTION) {
                Almacenamiento.abrir(destino.getAbsolutePath());
            }

        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el archivo en esa ubicación.\n"
                    + "Intenta con otra carpeta.",
                    "Error al descargar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cerrarSesion() {

        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Deseas cerrar tu sesión?",
                "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            regresarAlLogin();
        }
    }

    private void regresarAlLogin() {
        Sesion.cerrarSesion();
        new Login().setVisible(true);
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        pnlEncabezado = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblBienvenida = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        lblMatricula = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCarrera = new javax.swing.JLabel();
        txtCarrera = new javax.swing.JTextField();
        lblCuatri = new javax.swing.JLabel();
        txtCuatri = new javax.swing.JTextField();
        lblGrupo = new javax.swing.JLabel();
        txtGrupo = new javax.swing.JTextField();
        sepDatos = new javax.swing.JSeparator();
        lblEstado = new javax.swing.JLabel();
        btnVer = new javax.swing.JButton();
        btnDescargar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblNota = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HorarioNet - Mi horario");
        setResizable(false);

        pnlPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        pnlPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlEncabezado.setBackground(new java.awt.Color(97, 18, 51));
        pnlEncabezado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LogoColor64x64.png"))); // NOI18N
        pnlEncabezado.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblBienvenida.setFont(new java.awt.Font("Roboto", 1, 22)); // NOI18N
        lblBienvenida.setForeground(new java.awt.Color(255, 255, 255));
        lblBienvenida.setText("Hola");
        pnlEncabezado.add(lblBienvenida, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 15, 480, -1));

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSubtitulo.setForeground(new java.awt.Color(230, 210, 215));
        lblSubtitulo.setText("Este es el horario que te corresponde este cuatrimestre.");
        pnlEncabezado.add(lblSubtitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 48, 480, -1));

        pnlPrincipal.add(pnlEncabezado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 80));

        lblMatricula.setForeground(new java.awt.Color(0, 0, 0));
        lblMatricula.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMatricula.setText("Matrícula:");
        pnlPrincipal.add(lblMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 105, -1, -1));

        txtMatricula.setForeground(new java.awt.Color(0, 0, 0));
        txtMatricula.setEditable(false);
        txtMatricula.setBackground(new java.awt.Color(240, 240, 240));
        txtMatricula.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlPrincipal.add(txtMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 125, 200, 28));

        lblNombre.setForeground(new java.awt.Color(0, 0, 0));
        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombre.setText("Nombre:");
        pnlPrincipal.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 105, -1, -1));

        txtNombre.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(240, 240, 240));
        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlPrincipal.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 125, 360, 28));

        lblCarrera.setForeground(new java.awt.Color(0, 0, 0));
        lblCarrera.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCarrera.setText("Carrera:");
        pnlPrincipal.add(lblCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 165, -1, -1));

        txtCarrera.setForeground(new java.awt.Color(0, 0, 0));
        txtCarrera.setEditable(false);
        txtCarrera.setBackground(new java.awt.Color(240, 240, 240));
        txtCarrera.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlPrincipal.add(txtCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 185, 340, 28));

        lblCuatri.setForeground(new java.awt.Color(0, 0, 0));
        lblCuatri.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCuatri.setText("Cuatrimestre:");
        pnlPrincipal.add(lblCuatri, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 165, -1, -1));

        txtCuatri.setForeground(new java.awt.Color(0, 0, 0));
        txtCuatri.setEditable(false);
        txtCuatri.setBackground(new java.awt.Color(240, 240, 240));
        txtCuatri.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlPrincipal.add(txtCuatri, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 185, 100, 28));

        lblGrupo.setForeground(new java.awt.Color(0, 0, 0));
        lblGrupo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblGrupo.setText("Grupo:");
        pnlPrincipal.add(lblGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 165, -1, -1));

        txtGrupo.setForeground(new java.awt.Color(0, 0, 0));
        txtGrupo.setEditable(false);
        txtGrupo.setBackground(new java.awt.Color(240, 240, 240));
        txtGrupo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlPrincipal.add(txtGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 185, 100, 28));
        pnlPrincipal.add(sepDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 235, 580, 10));

        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblEstado.setText("Buscando tu horario...");
        lblEstado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pnlPrincipal.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 580, 60));

        btnVer.setBackground(new java.awt.Color(97, 18, 51));
        btnVer.setEnabled(false);
        btnVer.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnVer.setForeground(new java.awt.Color(255, 255, 255));
        btnVer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/HorarioCheck24x24.png"))); // NOI18N
        btnVer.setText("Ver mi horario");
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });
        pnlPrincipal.add(btnVer, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 335, 220, 44));

        btnDescargar.setBackground(new java.awt.Color(16, 122, 70));
        btnDescargar.setEnabled(false);
        btnDescargar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnDescargar.setForeground(new java.awt.Color(255, 255, 255));
        btnDescargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/saveicon24x24.png"))); // NOI18N
        btnDescargar.setText("Descargar");
        btnDescargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarActionPerformed(evt);
            }
        });
        pnlPrincipal.add(btnDescargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 335, 200, 44));

        btnSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logout.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        pnlPrincipal.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 335, 120, 44));

        lblNota.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        lblNota.setForeground(new java.awt.Color(120, 120, 120));
        lblNota.setText("Al descargarlo puedes imprimirlo o presentarlo como comprobante.");
        pnlPrincipal.add(lblNota, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 392, 580, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        verHorario();
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnDescargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarActionPerformed
        descargarHorario();
    }//GEN-LAST:event_btnDescargarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_btnSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDescargar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVer;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblCuatri;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblGrupo;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMatricula;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JSeparator sepDatos;
    private javax.swing.JTextField txtCarrera;
    private javax.swing.JTextField txtCuatri;
    private javax.swing.JTextField txtGrupo;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
