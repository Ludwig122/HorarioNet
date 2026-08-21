package vista;

import controlador.ControladorAlumno;
import dao.GrupoDAO;
import dao.CuatriDAO;
import dao.CarreraDAO;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;
import modelo.Grupo;
import modelo.Cuatri;
import modelo.Carrera;
import java.util.List;
import javax.swing.JButton;
import javax.swing.UIManager;
import modelo.Alumno;

/**
 *
 * @author Equipo 4 chiludos de Nayarit
 */
public class FrmCrudAlumno extends javax.swing.JInternalFrame {
    //Atributos de clase

    private ControladorAlumno controlador = new ControladorAlumno();
    private GrupoDAO grupoDAO = new GrupoDAO();
    private CuatriDAO cuatriDAO = new CuatriDAO();
    private CarreraDAO carreraDAO = new CarreraDAO();
    private int idUsuario = 0;

    public FrmCrudAlumno() {
        initComponents();
        // Ícono de la aplicación en la esquina de la ventana interna
        utilidades.Iconos.aplicarA(this);
//voy a crear mis propios métodos porque ya soy una chica programadora grande
        // coqueta y audaz, sin miedo al éxito
        this.cargarCombos();   // ← se llama aquí, después de initComponents()
        this.listarAlumnos();
        tblRoles.getTableHeader().setBackground(new Color(0xF2D9A0));
        tblRoles.getTableHeader().setForeground(Color.BLACK);

    }

    private void limpiar() {
        txtUsuario.setForeground(new java.awt.Color(0, 0, 0));
        txtUsuario.setText("");
        txtContrasena.setForeground(new java.awt.Color(0, 0, 0));
        txtContrasena.setEnabled(true);
        txtContrasena.setText("");
        txtNombre1.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre1.setText("");
        txtApellido.setForeground(new java.awt.Color(0, 0, 0));
        txtApellido.setText("");
        if (cmbGrupo.getItemCount() > 0) {
            cmbGrupo.setSelectedIndex(0);
        }
        if (cmbCuatri.getItemCount() > 0) {
            cmbCuatri.setSelectedIndex(0);
        }
        if (cmbCarrera.getItemCount() > 0) {
            cmbCarrera.setSelectedIndex(0);
        }

        // Soltar el renglón es tan importante como vaciar las cajas: mientras
        // idUsuario siga apuntando a un alumno, Modificar y Eliminar
        // trabajarían sobre él aunque la pantalla ya se vea vacía.
        if (tblRoles.getSelectedRow() != -1) {
            tblRoles.clearSelection();
        }

        idUsuario = 0;
        txtUsuario.requestFocusInWindow();
    }

    private void listarAlumnos() {
        javax.swing.table.DefaultTableModel tabla = new javax.swing.table.DefaultTableModel();
        tabla.addColumn("Id");
        tabla.addColumn("Login");
        tabla.addColumn("Nombre");
        tabla.addColumn("Apellido");
        tabla.addColumn("Carrera");
        tabla.addColumn("Cuatrimestre");
        tabla.addColumn("Grupo");
        tblRoles.setModel(tabla);

        List<Alumno> lista = controlador.listar();
        for (Alumno a : lista) {
            Grupo g = grupoDAO.buscarPorId(a.getIdGrupo());
            String letraGrupo = (g != null) ? g.getLetra() : String.valueOf(a.getIdGrupo());

            Carrera c = carreraDAO.buscarPorId(a.getIdCarrera());
            String nombreCarrera = (c != null) ? c.getNombre() : String.valueOf(a.getIdCarrera());

            Object fila[] = {a.getIdUsuario(), a.getLogin(),
                a.getNombre(), a.getApellido(), nombreCarrera, a.getIdCuatri(), letraGrupo};
            tabla.addRow(fila);
        }

    }

    // ↓↓↓ AQUÍ, junto a tus otros métodos propios, fuera del editor-fold ↓↓↓
    private void cargarCombos() {
        cmbGrupo.removeAllItems();
        List<Grupo> grupos = grupoDAO.listar();
        for (Grupo g : grupos) {
            cmbGrupo.addItem(g);
        }

        cmbCuatri.removeAllItems();
        List<Cuatri> cuatris = cuatriDAO.listar();
        for (Cuatri c : cuatris) {
            cmbCuatri.addItem(c);
        }

        cmbCarrera.removeAllItems();
        List<Carrera> carreras = carreraDAO.listar();
        for (Carrera c : carreras) {
            cmbCarrera.addItem(c);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCrudRol = new javax.swing.JPanel();
        lblNombreVentana = new javax.swing.JLabel();
        lblAlumno = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JTextField();
        pnlOperacionesCrud = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRoles = new javax.swing.JTable();
        cmbGrupo = new javax.swing.JComboBox<>();
        lblNombre = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtNombre1 = new javax.swing.JTextField();
        cmbCarrera = new javax.swing.JComboBox<>();
        lblCuatri = new javax.swing.JLabel();
        lblGrupo = new javax.swing.JLabel();
        cmbCuatri = new javax.swing.JComboBox<>();
        lblCarrera = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Alumnos");

        pnlCrudRol.setBackground(new java.awt.Color(255, 247, 232));

        lblNombreVentana.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombreVentana.setForeground(new java.awt.Color(0, 0, 0));
        lblNombreVentana.setText("Gestion del catalogo de Alumnos ");

        lblAlumno.setForeground(new java.awt.Color(0, 0, 0));
        lblAlumno.setText("Usuario");

        txtUsuario.setBackground(new java.awt.Color(255, 255, 255));
        txtUsuario.setForeground(new java.awt.Color(0, 0, 0));
        txtUsuario.setBorder(null);
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });

        lblContrasena.setForeground(new java.awt.Color(0, 0, 0));
        lblContrasena.setText("Contraseña");

        txtContrasena.setBackground(new java.awt.Color(255, 255, 255));
        txtContrasena.setForeground(new java.awt.Color(0, 0, 0));
        txtContrasena.setBorder(null);

        pnlOperacionesCrud.setBackground(new java.awt.Color(255, 247, 232));
        pnlOperacionesCrud.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Operaciones disponibles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 14), new java.awt.Color(0, 0, 0))); // NOI18N
        pnlOperacionesCrud.setForeground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(new java.awt.Color(122, 31, 43));
        btnGuardar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/saveicon24x24.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(122, 31, 43));
        btnModificar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/userEdit24x24.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setBorder(null);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(179, 38, 30));
        btnEliminar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete24x24.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(null);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(33, 148, 145));
        btnBuscar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search24x24.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(100, 100, 100));
        btnLimpiar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setToolTipText("Vacía el formulario y suelta el renglón seleccionado");
        btnLimpiar.setBorder(null);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tblRoles.setBackground(new java.awt.Color(255, 255, 255));
        tblRoles.setForeground(new java.awt.Color(0, 0, 0));
        tblRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Login", "Nombre", "Apellido", "Carrera", "Cuatrimestre", "Grupo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRoles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRolesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRoles);

        javax.swing.GroupLayout pnlOperacionesCrudLayout = new javax.swing.GroupLayout(pnlOperacionesCrud);
        pnlOperacionesCrud.setLayout(pnlOperacionesCrudLayout);
        pnlOperacionesCrudLayout.setHorizontalGroup(
            pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperacionesCrudLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOperacionesCrudLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pnlOperacionesCrudLayout.setVerticalGroup(
            pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperacionesCrudLayout.createSequentialGroup()
                .addGroup(pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnEliminar)
                    .addGroup(pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar)
                        .addComponent(btnModificar)))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmbGrupo.setBackground(new java.awt.Color(240, 248, 255));
        cmbGrupo.setForeground(new java.awt.Color(0, 0, 0));
        cmbGrupo.setModel(new javax.swing.DefaultComboBoxModel<Grupo>());

        lblNombre.setForeground(new java.awt.Color(0, 0, 0));
        lblNombre.setText("Nombre");

        lblApellido.setForeground(new java.awt.Color(0, 0, 0));
        lblApellido.setText("Apellidos");

        txtApellido.setBackground(new java.awt.Color(255, 255, 255));
        txtApellido.setForeground(new java.awt.Color(0, 0, 0));
        txtApellido.setBorder(null);
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        txtNombre1.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre1.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre1.setBorder(null);
        txtNombre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre1ActionPerformed(evt);
            }
        });

        cmbCarrera.setBackground(new java.awt.Color(240, 248, 255));
        cmbCarrera.setForeground(new java.awt.Color(0, 0, 0));
        cmbCarrera.setModel(new javax.swing.DefaultComboBoxModel<Carrera>());

        lblCuatri.setForeground(new java.awt.Color(0, 0, 0));
        lblCuatri.setText("Cuatri");

        lblGrupo.setForeground(new java.awt.Color(0, 0, 0));
        lblGrupo.setText("Grupo");

        cmbCuatri.setBackground(new java.awt.Color(240, 248, 255));
        cmbCuatri.setForeground(new java.awt.Color(0, 0, 0));
        cmbCuatri.setModel(new javax.swing.DefaultComboBoxModel<Cuatri>());

        lblCarrera.setForeground(new java.awt.Color(0, 0, 0));
        lblCarrera.setText("Carrera:");

        javax.swing.GroupLayout pnlCrudRolLayout = new javax.swing.GroupLayout(pnlCrudRol);
        pnlCrudRol.setLayout(pnlCrudRolLayout);
        pnlCrudRolLayout.setHorizontalGroup(
            pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlOperacionesCrud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                        .addComponent(lblNombreVentana, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(152, 152, 152))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlCrudRolLayout.createSequentialGroup()
                                        .addComponent(lblAlumno)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(90, 90, 90))))
                            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(lblCarrera))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCrudRolLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(lblNombre)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNombre1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(lblCuatri)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbCuatri, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(lblGrupo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(lblContrasena)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jSeparator3)
                                            .addComponent(txtContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
                                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                        .addComponent(lblApellido)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtApellido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))))
                                .addGap(246, 246, 246)))))
                .addContainerGap())
        );
        pnlCrudRolLayout.setVerticalGroup(
            pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombreVentana)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAlumno)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblContrasena)
                            .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre)
                    .addComponent(lblApellido)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCarrera)
                            .addComponent(cmbCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCuatri)
                            .addComponent(cmbCuatri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGrupo)
                            .addComponent(cmbGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(pnlOperacionesCrud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlCrudRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlCrudRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(66, 66, 66))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtUsuario.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe capturar el login");
            return;
        }
        if (txtContrasena.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe capturar la contraseña");
            return;
        }

        Grupo grupoSeleccionado = (Grupo) cmbGrupo.getSelectedItem();
        Cuatri cuatriSeleccionado = (Cuatri) cmbCuatri.getSelectedItem();
        Carrera carreraSeleccionada = (Carrera) cmbCarrera.getSelectedItem();

        if (grupoSeleccionado == null || cuatriSeleccionado == null || carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione carrera, grupo y cuatrimestre");
            return;
        }

        boolean guardado = controlador.guardar(txtUsuario.getText(), txtContrasena.getText(),
                txtNombre1.getText(), txtApellido.getText(),
                carreraSeleccionada.getIdCarrera(), grupoSeleccionado.getIdGrupo(), cuatriSeleccionado.getIdCuatri());

        if (guardado) {
            JOptionPane.showMessageDialog(this, "Alumno guardado correctamente");
            this.listarAlumnos();
            this.limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el alumno");
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (idUsuario == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el alumno?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            controlador.eliminar(idUsuario);
            this.listarAlumnos();
            this.limpiar();
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblRolesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRolesMouseClicked
        int fila = tblRoles.getSelectedRow();
        if (fila == -1) {
            return;
        }

        this.idUsuario = Integer.parseInt(tblRoles.getValueAt(fila, 0).toString());
        this.txtUsuario.setText(tblRoles.getValueAt(fila, 1).toString());
        this.txtContrasena.setEnabled(false);
        this.txtContrasena.setText("(sin cambios)");
        this.txtNombre1.setText(tblRoles.getValueAt(fila, 2).toString());
        this.txtApellido.setText(tblRoles.getValueAt(fila, 3).toString());

        // Columna 4 = Carrera, es el nombre — buscamos por nombre
        String nombreCarreraFila = tblRoles.getValueAt(fila, 4).toString();
        for (int i = 0; i < cmbCarrera.getItemCount(); i++) {
            if (cmbCarrera.getItemAt(i).getNombre().equals(nombreCarreraFila)) {
                cmbCarrera.setSelectedIndex(i);
                break;
            }
        }

        // Columna 5 = Cuatrimestre (sigue siendo número, esto no cambia)
        int idCuatriFila = Integer.parseInt(tblRoles.getValueAt(fila, 5).toString());
        for (int i = 0; i < cmbCuatri.getItemCount(); i++) {
            if (cmbCuatri.getItemAt(i).getIdCuatri() == idCuatriFila) {
                cmbCuatri.setSelectedIndex(i);
                break;
            }
        }

        // Columna 6 = Grupo, es letra ("B"), no id numérico — buscamos por letra
        String letraGrupoFila = tblRoles.getValueAt(fila, 6).toString();
        for (int i = 0; i < cmbGrupo.getItemCount(); i++) {
            if (cmbGrupo.getItemAt(i).getLetra().equals(letraGrupoFila)) {
                cmbGrupo.setSelectedIndex(i);
                break;
            }
        }


    }//GEN-LAST:event_tblRolesMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (idUsuario == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno");
            return;
        }
        Grupo grupoSeleccionado = (Grupo) cmbGrupo.getSelectedItem();
        Cuatri cuatriSeleccionado = (Cuatri) cmbCuatri.getSelectedItem();
        Carrera carreraSeleccionada = (Carrera) cmbCarrera.getSelectedItem();

        if (grupoSeleccionado == null || cuatriSeleccionado == null || carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione carrera, grupo y cuatrimestre");
            return;
        }

        controlador.modificar(idUsuario, txtUsuario.getText(),
                txtNombre1.getText(), txtApellido.getText(),
                carreraSeleccionada.getIdCarrera(), grupoSeleccionado.getIdGrupo(), cuatriSeleccionado.getIdCuatri());
        listarAlumnos();
        limpiar();

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        int idTabla;
        Alumno alumno;
        String loginBuscado = JOptionPane.showInputDialog(this, "Ingrese el login del alumno");
        if (loginBuscado == null || loginBuscado.trim().isEmpty()) {
            return;
        }

        alumno = controlador.buscar(loginBuscado);
        if (alumno != null) {
            this.idUsuario = alumno.getIdUsuario();
            this.txtUsuario.setText(alumno.getLogin());
            this.txtNombre1.setText(alumno.getNombre());
            this.txtApellido.setText(alumno.getApellido());

            for (int i = 0; i < tblRoles.getRowCount(); i++) {
                idTabla = Integer.parseInt(tblRoles.getValueAt(i, 0).toString());
                if (idTabla == alumno.getIdUsuario()) {
                    tblRoles.setRowSelectionInterval(i, i);
                    tblRoles.scrollRectToVisible(tblRoles.getCellRect(i, 0, true));
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, "Alumno encontrado");
        } else {
            JOptionPane.showMessageDialog(this, "Alumno no encontrado");
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtNombre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmCrudAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCrudAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCrudAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCrudAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCrudAlumno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<Carrera> cmbCarrera;
    private javax.swing.JComboBox<Cuatri> cmbCuatri;
    private javax.swing.JComboBox<Grupo> cmbGrupo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblAlumno;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblCuatri;
    private javax.swing.JLabel lblGrupo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreVentana;
    private javax.swing.JPanel pnlCrudRol;
    private javax.swing.JPanel pnlOperacionesCrud;
    private javax.swing.JTable tblRoles;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtContrasena;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
