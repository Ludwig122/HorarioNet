package vista;

import controlador.ControladorHorario;
import dao.CarreraDAO;
import dao.CuatriDAO;
import dao.GrupoDAO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Carrera;
import modelo.Cuatri;
import modelo.Grupo;
import modelo.Horario;
import utilidades.Almacenamiento;

/**
 * Consulta de horarios existentes. SOLO LECTURA: busca, lista y abre el
 * archivo del horario, pero no permite dar de alta, modificar ni eliminar.
 *
 * La usan dos roles distintos:
 *  - El administrador, desde Horarios -> Consultar Horarios existentes
 *  - El docente, que no tiene ninguna otra opción disponible
 *
 * Los tres filtros son opcionales: dejando uno en "Todos" se amplía la
 * búsqueda. Por ejemplo, filtrando solo por grupo salen todos los
 * cuatrimestres de ese grupo.
 */
public class FrmConsultaHorario extends javax.swing.JInternalFrame {

    private final ControladorHorario controlador = new ControladorHorario();
    private final CarreraDAO carreraDAO = new CarreraDAO();
    private final GrupoDAO grupoDAO = new GrupoDAO();
    private final CuatriDAO cuatriDAO = new CuatriDAO();

    // Resultados de la última búsqueda, en el mismo orden que la tabla
    private List<Horario> resultados = new ArrayList<>();

    /**
     * Margen que se deja libre a la derecha y abajo del último control, para
     * que el botón "Ver horario" no quede pegado al filo de la ventana.
     */
    private static final int MARGEN = 20;

    public FrmConsultaHorario() {
        initComponents();
        // Ícono de la aplicación en la esquina de la ventana interna
        utilidades.Iconos.aplicarA(this);
        ajustarAlContenido();
        cargarCombos();

        tblHorarios.getTableHeader().setBackground(new Color(0x7A1F2B));
        tblHorarios.getTableHeader().setForeground(Color.WHITE);
        tblHorarios.setForeground(new java.awt.Color(0, 0, 0));
        tblHorarios.setBackground(new java.awt.Color(255, 255, 255));
        tblHorarios.setRowHeight(25);
        tblHorarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        buscar(); // al abrir muestra todos los horarios registrados
    }

    /**
     * Deja la ventana del tamaño exacto que necesita su contenido.
     *
     * El problema que resuelve: el diseñador guarda un setBounds() fijo
     * (820x520) que es el tamaño TOTAL de la ventana, decoraciones incluidas.
     * Pero la barra de título de FlatLaf mide distinto según el tema y la
     * escala de pantalla de Windows, así que esos pixeles se los come la
     * barra y el contenido de hasta abajo — el botón "Ver horario" y el
     * renglón de ayuda — queda cortado.
     *
     * En vez de adivinar un número más grande, aquí se le dice al panel
     * cuánto espacio necesita de verdad (el control que llega más lejos, más
     * un margen) y se deja que pack() sume por su cuenta lo que midan la
     * barra de título y los bordes. Así se ve completo sin importar el tema
     * ni el DPI.
     */
    private void ajustarAlContenido() {

        int anchoNecesario = 0;
        int altoNecesario = 0;

        // AbsoluteLayout: el tamaño lo marca el control que llega más abajo
        // y el que llega más a la derecha.
        for (java.awt.Component control : pnlPrincipal.getComponents()) {
            anchoNecesario = Math.max(anchoNecesario, control.getX() + control.getWidth());
            altoNecesario = Math.max(altoNecesario, control.getY() + control.getHeight());
        }

        pnlPrincipal.setPreferredSize(new java.awt.Dimension(
                anchoNecesario + MARGEN, altoNecesario + MARGEN));

        pack();
    }

    // ----------------------------------------------------------
    // Carga de los filtros
    // ----------------------------------------------------------

    private void cargarCombos() {

        // El primer elemento null representa "Todos". El renderer de abajo
        // se encarga de que se lea bonito en pantalla.
        DefaultComboBoxModel<Object> modeloCarrera = new DefaultComboBoxModel<>();
        modeloCarrera.addElement("Todas");
        for (Carrera carrera : carreraDAO.listar()) {
            modeloCarrera.addElement(carrera);
        }
        cboCarrera.setModel(modeloCarrera);

        DefaultComboBoxModel<Object> modeloCuatri = new DefaultComboBoxModel<>();
        modeloCuatri.addElement("Todos");
        for (Cuatri cuatri : cuatriDAO.listar()) {
            modeloCuatri.addElement(cuatri);
        }
        cboCuatri.setModel(modeloCuatri);

        DefaultComboBoxModel<Object> modeloGrupo = new DefaultComboBoxModel<>();
        modeloGrupo.addElement("Todos");
        for (Grupo grupo : grupoDAO.listar()) {
            modeloGrupo.addElement(grupo);
        }
        cboGrupo.setModel(modeloGrupo);
    }

    // ----------------------------------------------------------
    // Búsqueda
    // ----------------------------------------------------------

    /**
     * Trae los horarios y les aplica los filtros que estén seleccionados.
     * El filtrado se hace en memoria porque el catálogo de horarios es
     * chico (a lo mucho unas decenas de registros).
     */
    private void buscar() {

        Object carreraSel = cboCarrera.getSelectedItem();
        Object cuatriSel = cboCuatri.getSelectedItem();
        Object grupoSel = cboGrupo.getSelectedItem();

        List<Horario> todos = controlador.listar();
        resultados = new ArrayList<>();

        for (Horario h : todos) {

            if (carreraSel instanceof Carrera
                    && ((Carrera) carreraSel).getIdCarrera() != h.getIdCarrera()) {
                continue;
            }

            if (cuatriSel instanceof Cuatri
                    && ((Cuatri) cuatriSel).getIdCuatri() != h.getIdCuatri()) {
                continue;
            }

            if (grupoSel instanceof Grupo
                    && ((Grupo) grupoSel).getIdGrupo() != h.getIdGrupo()) {
                continue;
            }

            resultados.add(h);
        }

        llenarTabla();

        if (resultados.isEmpty()) {
            lblMensaje.setText("No hay horarios que coincidan con esos filtros.");
        } else {
            lblMensaje.setText(resultados.size() + " horario(s) encontrado(s).");
        }
    }

    private void llenarTabla() {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Carrera", "Cuatrimestre", "Grupo", "Archivo", "Disponible"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Horario h : resultados) {
            modelo.addRow(new Object[]{
                nombreCarrera(h.getIdCarrera()),
                nombreCuatri(h.getIdCuatri()),
                nombreGrupo(h.getIdGrupo()),
                h.getImagen() == null ? "" : h.getImagen(),
                Almacenamiento.existe(h.getImagen()) ? "Sí" : "No"
            });
        }

        tblHorarios.setModel(modelo);
    }

    private String nombreCarrera(int id) {
        Carrera c = carreraDAO.buscarPorId(id);
        return c != null ? c.getNombre() : String.valueOf(id);
    }

    private String nombreGrupo(int id) {
        Grupo g = grupoDAO.buscarPorId(id);
        return g != null ? g.getLetra() : String.valueOf(id);
    }

    private String nombreCuatri(int id) {
        Cuatri c = cuatriDAO.buscar(id);
        return c != null ? String.valueOf(c.getNumCuatri()) : String.valueOf(id);
    }

    // ----------------------------------------------------------
    // Ver el horario seleccionado
    // ----------------------------------------------------------

    private void verHorario() {

        int fila = tblHorarios.getSelectedRow();

        if (fila < 0 || fila >= resultados.size()) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un horario de la tabla.",
                    "Selección requerida", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Horario horario = resultados.get(tblHorarios.convertRowIndexToModel(fila));

        if (horario.getImagen() == null || horario.getImagen().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Este horario no tiene ningún archivo asociado.",
                    "Sin archivo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!Almacenamiento.existe(horario.getImagen())) {
            JOptionPane.showMessageDialog(this,
                    "El archivo del horario no se encuentra.\n\n"
                    + "Archivo: " + horario.getImagen() + "\n"
                    + "Se buscó en: " + Almacenamiento.rutaCarpeta() + "\n\n"
                    + "Pide al administrador que lo vuelva a subir.",
                    "Archivo no disponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!Almacenamiento.abrir(horario.getImagen())) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo abrir el horario con el programa del sistema.",
                    "Error al abrir", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFiltros() {
        cboCarrera.setSelectedIndex(0);
        cboCuatri.setSelectedIndex(0);
        cboGrupo.setSelectedIndex(0);
        buscar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblCarrera = new javax.swing.JLabel();
        cboCarrera = new javax.swing.JComboBox<>();
        lblCuatri = new javax.swing.JLabel();
        cboCuatri = new javax.swing.JComboBox<>();
        lblGrupo = new javax.swing.JLabel();
        cboGrupo = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHorarios = new javax.swing.JTable();
        lblMensaje = new javax.swing.JLabel();
        btnVerHorario = new javax.swing.JButton();
        lblAyuda = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consultar horarios");

        pnlPrincipal.setBackground(new java.awt.Color(255, 247, 232));
        pnlPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(122, 31, 43));
        lblTitulo.setText("Consulta de horarios");
        pnlPrincipal.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, -1, -1));

        lblCarrera.setForeground(new java.awt.Color(0, 0, 0));

        lblCarrera.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCarrera.setText("Carrera:");
        pnlPrincipal.add(lblCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));
        cboCarrera.setBackground(new java.awt.Color(240, 248, 255));
        cboCarrera.setForeground(new java.awt.Color(0, 0, 0));
        pnlPrincipal.add(cboCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 220, 28));

        lblCuatri.setForeground(new java.awt.Color(0, 0, 0));

        lblCuatri.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCuatri.setText("Cuatrimestre:");
        pnlPrincipal.add(lblCuatri, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, -1, -1));
        cboCuatri.setBackground(new java.awt.Color(240, 248, 255));
        cboCuatri.setForeground(new java.awt.Color(0, 0, 0));
        pnlPrincipal.add(cboCuatri, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 120, 28));

        lblGrupo.setForeground(new java.awt.Color(0, 0, 0));

        lblGrupo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblGrupo.setText("Grupo:");
        pnlPrincipal.add(lblGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, -1, -1));
        cboGrupo.setBackground(new java.awt.Color(240, 248, 255));
        cboGrupo.setForeground(new java.awt.Color(0, 0, 0));
        pnlPrincipal.add(cboGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 100, 28));

        btnBuscar.setBackground(new java.awt.Color(122, 31, 43));
        btnBuscar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search24x24.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        pnlPrincipal.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 79, 130, 30));

        btnLimpiar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnLimpiar.setText("Limpiar filtros");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        pnlPrincipal.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 79, 150, 30));

        tblHorarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Carrera", "Cuatrimestre", "Grupo", "Archivo", "Disponible"}
        ));
        tblHorarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHorariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHorarios);

        pnlPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 770, 280));

        lblMensaje.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblMensaje.setForeground(new java.awt.Color(90, 90, 90));
        pnlPrincipal.add(lblMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 415, 500, 20));

        btnVerHorario.setBackground(new java.awt.Color(122, 31, 43));
        btnVerHorario.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnVerHorario.setForeground(new java.awt.Color(255, 255, 255));
        btnVerHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/HorarioCheck24x24.png"))); // NOI18N
        btnVerHorario.setText("Ver horario");
        btnVerHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerHorarioActionPerformed(evt);
            }
        });
        pnlPrincipal.add(btnVerHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 440, 170, 34));

        lblAyuda.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        lblAyuda.setForeground(new java.awt.Color(120, 120, 120));
        lblAyuda.setText("Doble clic sobre un renglón para abrir el horario.");
        pnlPrincipal.add(lblAyuda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 448, 480, 20));

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

        setBounds(0, 0, 830, 570);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFiltros();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnVerHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerHorarioActionPerformed
        verHorario();
    }//GEN-LAST:event_btnVerHorarioActionPerformed

    private void tblHorariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHorariosMouseClicked
        if (evt.getClickCount() == 2) {
            verHorario();
        }
    }//GEN-LAST:event_tblHorariosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnVerHorario;
    private javax.swing.JComboBox<Object> cboCarrera;
    private javax.swing.JComboBox<Object> cboCuatri;
    private javax.swing.JComboBox<Object> cboGrupo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAyuda;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblCuatri;
    private javax.swing.JLabel lblGrupo;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTable tblHorarios;
    // End of variables declaration//GEN-END:variables
}
