package vista;

import dao.AlumnoDAO;
import dao.CarreraDAO;
import dao.CuatriDAO;
import dao.GrupoDAO;
import dao.HistorialDAO;
import dao.HorarioDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Alumno;
import modelo.Carrera;
import modelo.Cuatri;
import utilidades.Almacenamiento;
import modelo.Grupo;
import modelo.Historial;
import modelo.Horario;

/**
 * Consulta del historial de horarios de un alumno.
 * Esta ventana NO modifica el historial: solamente consulta y permite visualizar
 * el horario asociado al registro seleccionado.
 */
public class FrmHistorial extends javax.swing.JInternalFrame {

    private final HistorialDAO historialDAO = new HistorialDAO();
    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private final CarreraDAO carreraDAO = new CarreraDAO();
    private final GrupoDAO grupoDAO = new GrupoDAO();
    private final CuatriDAO cuatriDAO = new CuatriDAO();
    private final HorarioDAO horarioDAO = new HorarioDAO();
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private List<Historial> historialActual;

    public FrmHistorial() {
        initComponents();
        // Ícono de la aplicación en la esquina de la ventana interna
        utilidades.Iconos.aplicarA(this);
        configurarTabla();
        limpiar();
    }

    private void configurarTabla() {
        tblHistorial.setRowHeight(28);
        tblHistorial.getTableHeader().setBackground(new Color(0xF2D9A0));
        tblHistorial.getTableHeader().setForeground(Color.BLACK);
        tblHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblHistorial.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    private void buscarHistorial() {
        String matricula = txtMatricula.getText().trim();

        if (matricula.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa la matrícula del alumno.",
                    "Dato requerido", JOptionPane.WARNING_MESSAGE);
            txtMatricula.requestFocus();
            return;
        }

        Alumno alumno = alumnoDAO.buscarPorLogin(matricula);

        if (alumno == null) {
            limpiarDatosAlumno();
            limpiarTabla();
            JOptionPane.showMessageDialog(this,
                    "No se encontró un alumno con esa matrícula.",
                    "Alumno no encontrado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        mostrarDatosAlumno(alumno);
        historialActual = historialDAO.listarPorUsuario(alumno.getIdUsuario());
        llenarTabla(historialActual);

        if (historialActual.isEmpty()) {
            lblMensaje.setText("El alumno no tiene historial de horarios registrado.");
            lblMensaje.setVisible(true);
        } else {
            lblMensaje.setText("");
            lblMensaje.setVisible(false);
        }
    }

    private void mostrarDatosAlumno(Alumno alumno) {
        txtNombre.setText(alumno.getNombre() + " " + alumno.getApellido());
        Carrera carrera = carreraDAO.buscarPorId(alumno.getIdCarrera());
        txtCarrera.setText(carrera != null ? carrera.getNombre() : "No registrada");
    }

    private void llenarTabla(List<Historial> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblHistorial.getModel();
        modelo.setRowCount(0);

        for (Historial h : lista) {
            Cuatri cuatri = cuatriDAO.buscar(h.getIdCuatri());
            Grupo grupo = grupoDAO.buscarPorId(h.getIdGrupo());
            Horario horario = h.tieneHorario()
                    ? horarioDAO.buscarPorId(h.getIdHorario()) : null;

            String cuatriTexto = cuatri != null
                    ? String.valueOf(cuatri.getNumCuatri()) : String.valueOf(h.getIdCuatri());
            String grupoTexto = grupo != null
                    ? grupo.getLetra() : String.valueOf(h.getIdGrupo());
            String horarioTexto;
            if (horario != null && horario.getImagen() != null) {
                horarioTexto = Almacenamiento.existe(horario.getImagen())
                        ? horario.getImagen() : "Archivo no encontrado";
            } else {
                horarioTexto = "Sin horario publicado";
            }

            String fecha = h.getFechaRegistro() != null
                    ? h.getFechaRegistro().format(formatoFecha) : "";

            modelo.addRow(new Object[]{cuatriTexto, grupoTexto, fecha, horarioTexto});
        }
    }

    private void verHorarioSeleccionado() {
        int fila = tblHistorial.getSelectedRow();

        if (fila < 0 || historialActual == null || fila >= historialActual.size()) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un registro del historial para ver su horario.",
                    "Selección requerida", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Historial historial = historialActual.get(tblHistorial.convertRowIndexToModel(fila));

        if (!historial.tieneHorario()) {
            JOptionPane.showMessageDialog(this,
                    "En ese momento todavía no se había publicado el horario de ese grupo.",
                    "Sin horario", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Horario horario = horarioDAO.buscarPorId(historial.getIdHorario());

        if (horario == null || horario.getImagen() == null || horario.getImagen().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El horario asociado a ese registro ya no existe.",
                    "Horario no disponible", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!Almacenamiento.existe(horario.getImagen())) {
            JOptionPane.showMessageDialog(this,
                    "El archivo del horario no se encuentra.\n\n"
                    + "Archivo: " + horario.getImagen() + "\n"
                    + "Se buscó en: " + Almacenamiento.rutaCarpeta(),
                    "Archivo no disponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Se abre con el programa del sistema: puede ser PDF o imagen
        if (!Almacenamiento.abrir(horario.getImagen())) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo abrir el archivo del horario.",
                    "Error al abrir", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        txtMatricula.setText("");
        limpiarDatosAlumno();
        limpiarTabla();
        lblMensaje.setText("");
        lblMensaje.setVisible(false);
        historialActual = null;
    }

    private void limpiarDatosAlumno() {
        txtNombre.setText("");
        txtCarrera.setText("");
    }

    private void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblHistorial.getModel();
        modelo.setRowCount(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        pnlConsulta = new javax.swing.JPanel();
        lblMatricula = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCarrera = new javax.swing.JLabel();
        txtCarrera = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();
        pnlOperaciones = new javax.swing.JPanel();
        btnVerHorario = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Historial de horarios");

        pnlPrincipal.setBackground(new Color(255, 247, 232));

        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setText("Consulta del historial de horarios");

        pnlConsulta.setBackground(new Color(255, 247, 232));
        pnlConsulta.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(41, 43, 45)),
                "Consulta por matrícula", 0, 0,
                new Font("Roboto", Font.BOLD, 14), Color.BLACK));

        lblMatricula.setForeground(Color.BLACK);
        lblMatricula.setText("Matrícula");

        txtMatricula.setBackground(Color.WHITE);
        txtMatricula.setForeground(Color.BLACK);
        txtMatricula.setBorder(null);
        txtMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatriculaActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new Color(33, 148, 145));
        btnBuscar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/imagenes/search24x24.png")));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        lblNombre.setForeground(Color.BLACK);
        lblNombre.setText("Alumno");

        txtNombre.setEditable(false);
        txtNombre.setBackground(Color.WHITE);
        txtNombre.setForeground(Color.BLACK);
        txtNombre.setBorder(null);

        lblCarrera.setForeground(Color.BLACK);
        lblCarrera.setText("Carrera");

        txtCarrera.setEditable(false);
        txtCarrera.setBackground(Color.WHITE);
        txtCarrera.setForeground(Color.BLACK);
        txtCarrera.setBorder(null);

        tblHistorial.setBackground(Color.WHITE);
        tblHistorial.setForeground(Color.BLACK);
        tblHistorial.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Cuatrimestre", "Grupo", "Fecha del registro", "Horario"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jScrollPane1.setViewportView(tblHistorial);

        pnlOperaciones.setBackground(new Color(255, 247, 232));
        pnlOperaciones.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(41, 43, 45)),
                "Operaciones disponibles", 0, 0,
                new Font("Roboto", Font.BOLD, 14), Color.BLACK));

        btnVerHorario.setBackground(new Color(122, 31, 43));
        btnVerHorario.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnVerHorario.setForeground(Color.WHITE);
        btnVerHorario.setIcon(new ImageIcon(getClass().getResource("/imagenes/HorarioCheck24x24.png")));
        btnVerHorario.setText("Ver horario");
        btnVerHorario.setBorder(null);
        btnVerHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerHorarioActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new Color(122, 31, 43));
        btnLimpiar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBorder(null);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMensaje.setForeground(new Color(122, 31, 43));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);

        javax.swing.GroupLayout pnlConsultaLayout = new javax.swing.GroupLayout(pnlConsulta);
        pnlConsulta.setLayout(pnlConsultaLayout);
        pnlConsultaLayout.setHorizontalGroup(
            pnlConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMatricula)
                .addGap(18, 18, 18)
                .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(lblNombre)
                .addGap(10, 10, 10)
                .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(lblCarrera)
                .addGap(10, 10, 10)
                .addComponent(txtCarrera, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlConsultaLayout.setVerticalGroup(
            pnlConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(pnlConsultaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblMatricula)
                    .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCarrera)
                    .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout pnlOperacionesLayout = new javax.swing.GroupLayout(pnlOperaciones);
        pnlOperaciones.setLayout(pnlOperacionesLayout);
        pnlOperacionesLayout.setHorizontalGroup(
            pnlOperacionesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVerHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlOperacionesLayout.setVerticalGroup(
            pnlOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(pnlOperacionesLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(pnlOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnVerHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(pnlConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(lblMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlOperaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblTitulo)
                .addGap(12, 12, 12)
                .addComponent(pnlConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(lblMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(pnlOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
        );

        // El disenador de NetBeans no usa setContentPane: envuelve el panel
        // en un GroupLayout sobre el contentPane. Se deja en ese formato para
        // que el formulario se pueda abrir en la vista Diseno.
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setPreferredSize(new java.awt.Dimension(1050, 620));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatriculaActionPerformed
        buscarHistorial();
    }//GEN-LAST:event_txtMatriculaActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarHistorial();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnVerHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerHorarioActionPerformed
        verHorarioSeleccionado();
    }//GEN-LAST:event_btnVerHorarioActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnVerHorario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblMatricula;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlConsulta;
    private javax.swing.JPanel pnlOperaciones;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTable tblHistorial;
    private javax.swing.JTextField txtCarrera;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
