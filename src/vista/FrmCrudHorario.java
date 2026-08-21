package vista;

import controlador.ControladorHorario;
import dao.CarreraDAO;
import dao.CuatriDAO;
import dao.GrupoDAO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Carrera;
import modelo.Cuatri;
import modelo.Grupo;
import modelo.Horario;
import utilidades.Almacenamiento;

/**
 * CRUD de Horarios.
 *
 * Cada registro representa un horario para una combinación única de:
 * carrera + grupo + cuatrimestre.
 *
 * La columna "imagen" de la BD almacena la ruta del archivo de imagen
 * que contiene el horario completo.
 */
public class FrmCrudHorario extends javax.swing.JInternalFrame {

    private final ControladorHorario controlador = new ControladorHorario();
    private final CarreraDAO carreraDAO = new CarreraDAO();
    private final GrupoDAO grupoDAO = new GrupoDAO();
    private final CuatriDAO cuatriDAO = new CuatriDAO();

    private int idHorarioSeleccionado = 0;
    private String rutaImagenSeleccionada = null;

    public FrmCrudHorario() {
        initComponents();
        // Ícono de la aplicación en la esquina de la ventana interna
        utilidades.Iconos.aplicarA(this);
        cargarCombos();
        listarHorarios();

        // Doble clic en la vista previa abre el archivo con el programa del
        // sistema. Va aquí y no en initComponents para no alterar el .form.
        lblVistaPrevia.setForeground(new java.awt.Color(0, 0, 0));
        lblVistaPrevia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblVistaPrevia.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    abrirArchivoActual();
                }
            }
        });

        tblHorarios.getTableHeader().setBackground(new Color(0x7A1F2B));
        tblHorarios.getTableHeader().setForeground(Color.WHITE);
        tblHorarios.setRowHeight(25);

        limpiar();

        /*
         * Se vuelve a dimensionar la ventana AL FINAL, a propósito.
         *
         * El pack() que genera el diseñador corre dentro de initComponents(),
         * o sea antes de esta parte. Pero aquí arriba se le sube la altura de
         * renglón a la tabla (de 16 a 25), y eso hace que la tabla necesite
         * más espacio del que tenía cuando se calculó el tamaño. El resultado
         * era que los últimos pixeles del panel de operaciones quedaban por
         * debajo del borde de la ventana.
         *
         * Volviendo a empacar aquí, el cálculo ya toma en cuenta la altura
         * real de la tabla.
         */
        pack();
    }

    private void cargarCombos() {
        cargarCarreras();
        cargarGrupos();
        cargarCuatrimestres();
    }

    private void cargarCarreras() {
        List<Carrera> lista = carreraDAO.listar();
        DefaultComboBoxModel<Carrera> modelo = new DefaultComboBoxModel<>();
        for (Carrera carrera : lista) {
            modelo.addElement(carrera);
        }
        cboCarrera.setModel(modelo);
    }

    private void cargarGrupos() {
        List<Grupo> lista = grupoDAO.listar();
        DefaultComboBoxModel<Grupo> modelo = new DefaultComboBoxModel<>();
        for (Grupo grupo : lista) {
            modelo.addElement(grupo);
        }
        cboGrupo.setModel(modelo);
    }

    private void cargarCuatrimestres() {
        List<Cuatri> lista = cuatriDAO.listar();
        DefaultComboBoxModel<Cuatri> modelo = new DefaultComboBoxModel<>();
        for (Cuatri cuatri : lista) {
            modelo.addElement(cuatri);
        }
        cboCuatri.setModel(modelo);
    }

    private void listarHorarios() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Id", "Carrera", "Grupo", "Cuatrimestre", "Imagen"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Horario h : controlador.listar()) {
            modelo.addRow(new Object[]{
                h.getIdHorario(),
                nombreCarrera(h.getIdCarrera()),
                nombreGrupo(h.getIdGrupo()),
                nombreCuatri(h.getIdCuatri()),
                h.getImagen()
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

    private void limpiar() {
        idHorarioSeleccionado = 0;
        rutaImagenSeleccionada = null;

        if (cboCarrera.getItemCount() > 0) {
            cboCarrera.setSelectedIndex(0);
        }
        if (cboGrupo.getItemCount() > 0) {
            cboGrupo.setSelectedIndex(0);
        }
        if (cboCuatri.getItemCount() > 0) {
            cboCuatri.setSelectedIndex(0);
        }

        txtImagen.setText("");
        lblVistaPrevia.setIcon(null);
        lblVistaPrevia.setText("Sin imagen seleccionada");
    }

    private Carrera carreraSeleccionada() {
        return (Carrera) cboCarrera.getSelectedItem();
    }

    private Grupo grupoSeleccionado() {
        return (Grupo) cboGrupo.getSelectedItem();
    }

    private Cuatri cuatriSeleccionado() {
        return (Cuatri) cboCuatri.getSelectedItem();
    }

    private boolean validarFormulario() {
        if (carreraSeleccionada() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera.");
            return false;
        }
        if (grupoSeleccionado() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un grupo.");
            return false;
        }
        if (cuatriSeleccionado() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cuatrimestre.");
            return false;
        }
        if (txtImagen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione la imagen del horario.");
            return false;
        }
        return true;
    }

    private void seleccionarImagen() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar imagen del horario");
        selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes (*.jpg, *.jpeg, *.png, *.pdf)", "jpg", "jpeg", "png", "pdf"));

        if (selector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            rutaImagenSeleccionada = archivo.getAbsolutePath();
            txtImagen.setText(rutaImagenSeleccionada);
            mostrarVistaPrevia(archivo);
        }
    }

    /**
     * Devuelve el archivo que se debe guardar al modificar: el que el usuario
     * acaba de elegir, o el que ya estaba almacenado si no cambió nada.
     */
    private File archivoSeleccionadoOAlmacenado() {

        String texto = txtImagen.getText().trim();

        File comoRuta = new File(texto);

        if (comoRuta.exists()) {
            return comoRuta;
        }

        // No es una ruta: es el nombre de un archivo ya guardado en la carpeta
        return Almacenamiento.obtener(texto);
    }

    private void mostrarVistaPrevia(File archivo) {

        // Swing no sabe dibujar un PDF, así que para esos se muestra el nombre
        // y el usuario lo abre con el lector del sistema.
        if (archivo != null && archivo.getName().toLowerCase().endsWith(".pdf")) {
            lblVistaPrevia.setIcon(null);
            lblVistaPrevia.setText("<html><center>Archivo PDF<br><b>"
                    + archivo.getName()
                    + "</b><br>Doble clic aquí para abrirlo.</center></html>");
            return;
        }

        ImageIcon original = new ImageIcon(archivo.getAbsolutePath());
        if (original.getIconWidth() <= 0 || original.getIconHeight() <= 0) {
            lblVistaPrevia.setIcon(null);
            lblVistaPrevia.setText("No se pudo cargar el archivo");
            return;
        }

        int anchoMax = 380;
        int altoMax = 180;
        double escala = Math.min(
                (double) anchoMax / original.getIconWidth(),
                (double) altoMax / original.getIconHeight());

        escala = Math.min(escala, 1.0);

        int ancho = Math.max(1, (int) (original.getIconWidth() * escala));
        int alto = Math.max(1, (int) (original.getIconHeight() * escala));

        Image imagen = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        lblVistaPrevia.setText("");
        lblVistaPrevia.setIcon(new ImageIcon(imagen));
    }

    /**
     * Abre el archivo que está cargado en el formulario, sea el que se acaba
     * de elegir o el del horario seleccionado en la tabla.
     */
    private void abrirArchivoActual() {

        String texto = txtImagen.getText().trim();

        if (texto.isEmpty()) {
            return;
        }

        File archivo = new File(texto);

        if (!archivo.exists()) {
            archivo = Almacenamiento.obtener(texto);
        }

        if (archivo == null || !archivo.exists()) {
            JOptionPane.showMessageDialog(this,
                    "El archivo no se encuentra disponible.",
                    "Archivo no disponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Almacenamiento.abrir(archivo.getAbsolutePath());
    }

    private void seleccionarFila() {
        int fila = tblHorarios.getSelectedRow();
        if (fila == -1) {
            return;
        }

        idHorarioSeleccionado = Integer.parseInt(tblHorarios.getValueAt(fila, 0).toString());

        Horario horario = controlador.buscarPorId(idHorarioSeleccionado);
        if (horario == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el horario seleccionado.");
            return;
        }

        seleccionarComboPorId(cboCarrera, horario.getIdCarrera());
        seleccionarComboPorId(cboGrupo, horario.getIdGrupo());
        seleccionarComboPorId(cboCuatri, horario.getIdCuatri());

        rutaImagenSeleccionada = horario.getImagen();
        txtImagen.setText(horario.getImagen() == null ? "" : horario.getImagen());

        if (horario.getImagen() != null && !horario.getImagen().trim().isEmpty()) {
            File archivo = Almacenamiento.obtener(horario.getImagen());
            if (archivo != null && archivo.exists()) {
                mostrarVistaPrevia(archivo);
            } else {
                lblVistaPrevia.setIcon(null);
                lblVistaPrevia.setText("<html><center>El archivo no se encuentra en<br>"
                        + Almacenamiento.rutaCarpeta() + "</center></html>");
            }
        } else {
            lblVistaPrevia.setIcon(null);
            lblVistaPrevia.setText("Sin imagen");
        }
    }

    private void seleccionarComboPorId(javax.swing.JComboBox<?> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            Object item = combo.getItemAt(i);
            if (item instanceof Carrera && ((Carrera) item).getIdCarrera() == id) {
                combo.setSelectedIndex(i);
                return;
            }
            if (item instanceof Grupo && ((Grupo) item).getIdGrupo() == id) {
                combo.setSelectedIndex(i);
                return;
            }
            if (item instanceof Cuatri && ((Cuatri) item).getIdCuatri() == id) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (!validarFormulario()) {
            return;
        }

        Carrera carrera = carreraSeleccionada();
        Grupo grupo = grupoSeleccionado();
        Cuatri cuatri = cuatriSeleccionado();

        // El archivo se copia a la carpeta "horarios" de la aplicación y en la
        // base de datos se guarda solo el nombre, nunca la ruta de esta máquina.
        String nombreArchivo = Almacenamiento.guardar(
                new File(txtImagen.getText().trim()),
                carrera.getNombre(),
                cuatri.getNumCuatri(),
                grupo.getLetra());

        if (nombreArchivo == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo copiar el archivo del horario.\n"
                    + "Verifica que el archivo exista y vuelve a intentarlo.",
                    "Error con el archivo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean guardado = controlador.guardar(
                carrera.getIdCarrera(),
                grupo.getIdGrupo(),
                cuatri.getIdCuatri(),
                nombreArchivo);

        if (guardado) {
            JOptionPane.showMessageDialog(this, "Horario guardado correctamente.");
            listarHorarios();
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el horario.\n"
                    + "Es posible que ya exista un horario para esa carrera, grupo y cuatrimestre.",
                    "Error al guardar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (idHorarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un horario de la tabla.");
            return;
        }

        if (!validarFormulario()) {
            return;
        }

        Carrera carrera = carreraSeleccionada();
        Grupo grupo = grupoSeleccionado();
        Cuatri cuatri = cuatriSeleccionado();

        String nombreArchivo = Almacenamiento.guardar(
                archivoSeleccionadoOAlmacenado(),
                carrera.getNombre(),
                cuatri.getNumCuatri(),
                grupo.getLetra());

        if (nombreArchivo == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo copiar el archivo del horario.\n"
                    + "Verifica que el archivo exista y vuelve a intentarlo.",
                    "Error con el archivo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean modificado = controlador.modificar(
                idHorarioSeleccionado,
                carrera.getIdCarrera(),
                grupo.getIdGrupo(),
                cuatri.getIdCuatri(),
                nombreArchivo);

        if (modificado) {
            JOptionPane.showMessageDialog(this, "Horario modificado correctamente.");
            listarHorarios();
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo modificar el horario.\n"
                    + "Revise que no exista otro horario con la misma carrera, grupo y cuatrimestre.",
                    "Error al modificar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (idHorarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un horario de la tabla.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar el horario seleccionado?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {

            // Se guarda el nombre antes de borrar el registro, para poder
            // ofrecer el borrado del archivo después.
            Horario aEliminar = controlador.buscarPorId(idHorarioSeleccionado);
            String archivo = aEliminar != null ? aEliminar.getImagen() : null;

            if (controlador.eliminar(idHorarioSeleccionado)) {

                if (archivo != null && Almacenamiento.existe(archivo)) {
                    int borrar = JOptionPane.showConfirmDialog(this,
                            "¿También deseas borrar el archivo del disco?\n\n"
                            + archivo + "\n\n"
                            + "Si eliges No, el archivo se conserva en la carpeta de horarios.",
                            "Archivo del horario",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (borrar == JOptionPane.YES_OPTION) {
                        Almacenamiento.eliminar(archivo);
                    }
                }

                JOptionPane.showMessageDialog(this, "Horario eliminado correctamente.");
                listarHorarios();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo eliminar el horario.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String valor = JOptionPane.showInputDialog(
                this, "Ingrese el ID del horario:", "Buscar horario",
                JOptionPane.QUESTION_MESSAGE);

        if (valor == null || valor.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(valor.trim());
            Horario horario = controlador.buscarPorId(id);

            if (horario == null) {
                JOptionPane.showMessageDialog(this, "No se encontró un horario con ese ID.");
                return;
            }

            for (int i = 0; i < tblHorarios.getRowCount(); i++) {
                if (Integer.parseInt(tblHorarios.getValueAt(i, 0).toString()) == id) {
                    tblHorarios.setRowSelectionInterval(i, i);
                    tblHorarios.scrollRectToVisible(tblHorarios.getCellRect(i, 0, true));
                    seleccionarFila();
                    return;
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblCarrera = new javax.swing.JLabel();
        cboCarrera = new javax.swing.JComboBox<>();
        lblGrupo = new javax.swing.JLabel();
        cboGrupo = new javax.swing.JComboBox<>();
        lblCuatri = new javax.swing.JLabel();
        cboCuatri = new javax.swing.JComboBox<>();
        lblImagen = new javax.swing.JLabel();
        txtImagen = new javax.swing.JTextField();
        btnSeleccionarImagen = new javax.swing.JButton();
        lblVistaPrevia = new javax.swing.JLabel();
        pnlOperaciones = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        scrollTabla = new javax.swing.JScrollPane();
        tblHorarios = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Horarios");

        pnlPrincipal.setBackground(new java.awt.Color(255, 247, 232));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblTitulo.setForeground(new java.awt.Color(0, 0, 0));
        lblTitulo.setText("Gestión del catálogo de Horarios");

        lblCarrera.setForeground(new java.awt.Color(0, 0, 0));
        lblCarrera.setText("Carrera:");

        cboCarrera.setBackground(new java.awt.Color(240, 248, 255));
        cboCarrera.setForeground(new java.awt.Color(0, 0, 0));

        lblGrupo.setForeground(new java.awt.Color(0, 0, 0));
        lblGrupo.setText("Grupo:");

        cboGrupo.setBackground(new java.awt.Color(240, 248, 255));
        cboGrupo.setForeground(new java.awt.Color(0, 0, 0));

        lblCuatri.setForeground(new java.awt.Color(0, 0, 0));
        lblCuatri.setText("Cuatrimestre:");

        cboCuatri.setBackground(new java.awt.Color(240, 248, 255));
        cboCuatri.setForeground(new java.awt.Color(0, 0, 0));

        lblImagen.setForeground(new java.awt.Color(0, 0, 0));
        lblImagen.setText("Imagen del horario:");

        txtImagen.setBackground(new java.awt.Color(255, 255, 255));
        txtImagen.setForeground(new java.awt.Color(0, 0, 0));

        btnSeleccionarImagen.setBackground(new java.awt.Color(33, 148, 145));
        btnSeleccionarImagen.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnSeleccionarImagen.setForeground(java.awt.Color.WHITE);
        btnSeleccionarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addHorario24.png")));
        btnSeleccionarImagen.setText("Seleccionar");
        btnSeleccionarImagen.setBorder(null);
        btnSeleccionarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarImagenActionPerformed(evt);
            }
        });

        lblVistaPrevia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVistaPrevia.setText("Sin imagen seleccionada");
        lblVistaPrevia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180)));
        lblVistaPrevia.setPreferredSize(new Dimension(400, 190));

        pnlOperaciones.setBackground(new java.awt.Color(255, 247, 232));
        pnlOperaciones.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1),
                "Operaciones disponibles",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Roboto", 1, 14),
                new java.awt.Color(0, 0, 0)));

        btnGuardar.setBackground(new java.awt.Color(122, 31, 43));
        btnGuardar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnGuardar.setForeground(java.awt.Color.WHITE);
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/saveicon24x24.png")));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(122, 31, 43));
        btnModificar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnModificar.setForeground(java.awt.Color.WHITE);
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/userEdit24x24.png")));
        btnModificar.setText("Modificar");
        btnModificar.setBorder(null);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(179, 38, 30));
        btnEliminar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnEliminar.setForeground(java.awt.Color.WHITE);
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete24x24.png")));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(null);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(33, 148, 145));
        btnBuscar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnBuscar.setForeground(java.awt.Color.WHITE);
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search24x24.png")));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(100, 100, 100));
        btnLimpiar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnLimpiar.setForeground(java.awt.Color.WHITE);
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBorder(null);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlOperacionesLayout = new javax.swing.GroupLayout(pnlOperaciones);
        pnlOperaciones.setLayout(pnlOperacionesLayout);
        pnlOperacionesLayout.setHorizontalGroup(
                pnlOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlOperacionesLayout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(45, Short.MAX_VALUE))
                        .addGroup(pnlOperacionesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollTabla)
                                .addContainerGap())
        );
        pnlOperacionesLayout.setVerticalGroup(
                pnlOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlOperacionesLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(pnlOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnGuardar)
                                        .addComponent(btnModificar)
                                        .addComponent(btnEliminar)
                                        .addComponent(btnBuscar)
                                        .addComponent(btnLimpiar))
                                .addGap(12, 12, 12)
                                .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        tblHorarios.setBackground(new java.awt.Color(255, 255, 255));
        tblHorarios.setForeground(new java.awt.Color(0, 0, 0));
        tblHorarios.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Id", "Carrera", "Grupo", "Cuatrimestre", "Imagen"}
        ));
        tblHorarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHorariosMouseClicked(evt);
            }
        });
        scrollTabla.setViewportView(tblHorarios);

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);

        pnlPrincipalLayout.setHorizontalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlOperaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblTitulo)
                                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblCarrera)
                                                                        .addComponent(lblGrupo)
                                                                        .addComponent(lblCuatri)
                                                                        .addComponent(lblImagen))
                                                                .addGap(15, 15, 15)
                                                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(cboCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(cboGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(cboCuatri, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                                                .addComponent(txtImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(10, 10, 10)
                                                                                .addComponent(btnSeleccionarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                .addGap(25, 25, 25)
                                                .addComponent(lblVistaPrevia, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        pnlPrincipalLayout.setVerticalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTitulo)
                                .addGap(16, 16, 16)
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblCarrera)
                                                        .addComponent(cboCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(12, 12, 12)
                                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblGrupo)
                                                        .addComponent(cboGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(12, 12, 12)
                                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblCuatri)
                                                        .addComponent(cboCuatri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(12, 12, 12)
                                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblImagen)
                                                        .addComponent(txtImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnSeleccionarImagen))
                                                .addGap(8, 8, 8))
                                        .addComponent(lblVistaPrevia, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addComponent(pnlOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarImagenActionPerformed
        seleccionarImagen();
    }//GEN-LAST:event_btnSeleccionarImagenActionPerformed

    private void tblHorariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHorariosMouseClicked
        seleccionarFila();
    }//GEN-LAST:event_tblHorariosMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSeleccionarImagen;
    private javax.swing.JComboBox<Carrera> cboCarrera;
    private javax.swing.JComboBox<Grupo> cboGrupo;
    private javax.swing.JComboBox<Cuatri> cboCuatri;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblCuatri;
    private javax.swing.JLabel lblGrupo;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVistaPrevia;
    private javax.swing.JPanel pnlOperaciones;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tblHorarios;
    private javax.swing.JTextField txtImagen;
    // End of variables declaration//GEN-END:variables
}
