package vista;

import controlador.ControladorAdmin;
import dao.AdminDAO;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.util.List;
import modelo.Admin;
import utilidades.Texto;

public class FrmCrudAdmin extends javax.swing.JInternalFrame {

    private ControladorAdmin controlador = new ControladorAdmin();
    private AdminDAO adminDAO = new AdminDAO();
    private int idUsuario = 0;

    public FrmCrudAdmin() {
        initComponents();
        // Ícono de la aplicación en la esquina de la ventana interna
        utilidades.Iconos.aplicarA(this);
        this.listarAdmin();
        tblRoles.getTableHeader().setBackground(new Color(97, 18, 50));
        tblRoles.getTableHeader().setForeground(Color.WHITE);
    }

    /**
     * Deja el formulario como recién abierto.
     *
     * Ya soltaba el renglón, pero le faltaba poner idUsuario en 0: mientras
     * ese campo siga apuntando a alguien, Modificar y Eliminar seguirían
     * trabajando sobre ese administrador aunque la pantalla ya se vea vacía.
     */
    private void limpiar() {
        txtUsuario.setText("");
        txtContrasena.setEnabled(true);
        txtContrasena.setText("");
        txtNombre1.setText("");
        txtApellidos.setText("");

        if (tblRoles.getSelectedRow() != -1) {
            tblRoles.clearSelection();
        }

        idUsuario = 0;
        txtUsuario.requestFocusInWindow();
    }

    private void listarAdmin() {
        javax.swing.table.DefaultTableModel tabla = new javax.swing.table.DefaultTableModel();
        tabla.addColumn("Id");
        tabla.addColumn("Login");
        tabla.addColumn("Nombre");
        tabla.addColumn("Apellido");
        tblRoles.setModel(tabla);

        List<Admin> lista = controlador.listar();
        for (Admin a : lista) {
            Object fila[] = {a.getIdUsuario(), a.getLogin(), a.getNombre(), a.getApellido()};
            tabla.addRow(fila);
        }
    }

    // ↓↓↓ AQUÍ, junto a tus otros métodos propios, fuera del editor-fold ↓↓↓
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCrudRol = new javax.swing.JPanel();
        lblNombreVentana = new javax.swing.JLabel();
        lblAlumno = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre1 = new javax.swing.JTextField();
        lblApellidos = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        pnlOperacionesCrud = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRoles = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Administradores");

        pnlCrudRol.setBackground(new java.awt.Color(255, 247, 232));

        lblNombreVentana.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombreVentana.setForeground(new java.awt.Color(0, 0, 0));
        lblNombreVentana.setText("Gestion del catalogo de Administradores");

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

        lblNombre.setForeground(new java.awt.Color(0, 0, 0));
        lblNombre.setText("Nombre");

        txtNombre1.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre1.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre1.setBorder(null);
        txtNombre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre1ActionPerformed(evt);
            }
        });

        lblApellidos.setForeground(new java.awt.Color(0, 0, 0));
        lblApellidos.setText("Apellidos");

        txtApellidos.setBackground(new java.awt.Color(255, 255, 255));
        txtApellidos.setForeground(new java.awt.Color(0, 0, 0));
        txtApellidos.setBorder(null);
        txtApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCrudRolLayout = new javax.swing.GroupLayout(pnlCrudRol);
        pnlCrudRol.setLayout(pnlCrudRolLayout);
        pnlCrudRolLayout.setHorizontalGroup(
            pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(lblNombreVentana, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                .addComponent(lblContrasena)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCrudRolLayout.createSequentialGroup()
                                .addComponent(lblApellidos)
                                .addGap(25, 25, 25)
                                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator4)
                                    .addComponent(txtApellidos)))))
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNombre)
                            .addComponent(lblAlumno))
                        .addGap(18, 18, 18)
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(txtUsuario)
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator2))
                        .addGap(240, 240, 240)
                        .addComponent(jSeparator3)))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        pnlCrudRolLayout.setVerticalGroup(
            pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombreVentana)
                .addGap(18, 18, 18)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblAlumno)
                        .addComponent(lblContrasena)
                        .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNombre)
                                    .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pnlOperacionesCrud.setBackground(new java.awt.Color(255, 247, 232));
        pnlOperacionesCrud.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Operaciones disponibles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 14), new java.awt.Color(0, 0, 0))); // NOI18N
        pnlOperacionesCrud.setForeground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(new java.awt.Color(122, 31, 43));
        btnGuardar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/saveicon24x24.png"))); // NOI18N
        btnGuardar.setText(" Guardar");
        btnGuardar.setToolTipText("\"Guardar Alumno\"");
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
        btnModificar.setToolTipText("Modificar Docente");
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
        btnEliminar.setToolTipText("Eliminar Docente");
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
        btnBuscar.setToolTipText("Buscar Docente");
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tblRoles.setBackground(new java.awt.Color(255, 255, 255));
        tblRoles.setBorder(new com.formdev.flatlaf.ui.FlatBorder());
        tblRoles.setForeground(new java.awt.Color(0, 0, 0));
        tblRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Login", "Nombre", "Apellidos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
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

        javax.swing.GroupLayout pnlOperacionesCrudLayout = new javax.swing.GroupLayout(pnlOperacionesCrud);
        pnlOperacionesCrud.setLayout(pnlOperacionesCrudLayout);
        pnlOperacionesCrudLayout.setHorizontalGroup(
            pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperacionesCrudLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(pnlOperacionesCrudLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        pnlOperacionesCrudLayout.setVerticalGroup(
            pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperacionesCrudLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnModificar)
                        .addComponent(btnLimpiar)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnGuardar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCrudRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlOperacionesCrud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlCrudRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlOperacionesCrud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        if (txtNombre1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe capturar el nombre");
            return;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe capturar el apellido");
            return;
        }

        boolean guardado = controlador.guardar(txtUsuario.getText(), txtContrasena.getText(),
                txtNombre1.getText(), txtApellidos.getText());

        if (guardado) {
            JOptionPane.showMessageDialog(this, "Administrador guardado correctamente");
            this.listarAdmin();
            this.limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el administrador");
        }


    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed


        if (idUsuario == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un administrador");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el administrador?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            controlador.eliminar(idUsuario);
            this.listarAdmin();
            this.limpiar();
        }



    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblRolesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRolesMouseClicked

              int fila = tblRoles.getSelectedRow();
        if (fila == -1) return;

        this.idUsuario = Integer.parseInt(tblRoles.getValueAt(fila, 0).toString());
        this.txtUsuario.setText(tblRoles.getValueAt(fila, 1).toString());
        this.txtContrasena.setEnabled(false);
        this.txtContrasena.setText("(sin cambios)");
        this.txtNombre1.setText(tblRoles.getValueAt(fila, 2).toString());
        this.txtApellidos.setText(tblRoles.getValueAt(fila, 3).toString());


    }//GEN-LAST:event_tblRolesMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

        if (idUsuario == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un administrador");
            return;
        }

        controlador.modificar(idUsuario, txtUsuario.getText(), txtContrasena.getText(),
                txtNombre1.getText(), txtApellidos.getText());
        listarAdmin();
        limpiar();

    }//GEN-LAST:event_btnModificarActionPerformed

    /*
     * Búsqueda por APELLIDOS, con el mismo criterio que en el CRUD de
     * docentes: los dos apellidos juntos dan muchos menos duplicados que el
     * nombre de pila. Si aun así coincide más de uno, se avisa cuántos y se
     * deja escoger. La comparación ignora acentos y mayúsculas.
     */
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        String apellidoBuscado = JOptionPane.showInputDialog(this,
                "Ingrese los apellidos del administrador");

        if (apellidoBuscado == null || apellidoBuscado.trim().isEmpty()) {
            return;
        }

        List<Admin> encontrados = controlador.buscarPorApellido(apellidoBuscado);

        if (encontrados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró ningún administrador con los apellidos \""
                    + Texto.mayus(apellidoBuscado) + "\".",
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Admin elegido;

        if (encontrados.size() == 1) {
            elegido = encontrados.get(0);
        } else {
            elegido = escogerAdmin(encontrados, apellidoBuscado);

            if (elegido == null) {
                return; // el usuario canceló
            }
        }

        mostrarAdmin(elegido);

        JOptionPane.showMessageDialog(this,
                encontrados.size() == 1
                        ? "Se encontró 1 administrador con esos apellidos."
                        : "Se encontraron " + encontrados.size()
                          + " administradores con esos apellidos.",
                "Resultado de la búsqueda", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_btnBuscarActionPerformed

    /** Deja escoger cuando varios administradores comparten los apellidos. */
    private Admin escogerAdmin(List<Admin> encontrados, String apellidoBuscado) {

        String[] opciones = new String[encontrados.size()];

        for (int i = 0; i < encontrados.size(); i++) {
            Admin a = encontrados.get(i);
            opciones[i] = a.getNombre() + " " + a.getApellido() + "  (" + a.getLogin() + ")";
        }

        String seleccion = (String) JOptionPane.showInputDialog(this,
                "Se encontraron " + encontrados.size()
                + " administradores con los apellidos \""
                + Texto.mayus(apellidoBuscado) + "\".\n\n¿Cuál desea abrir?",
                "Varios administradores coinciden",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == null) {
            return null;
        }

        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i].equals(seleccion)) {
                return encontrados.get(i);
            }
        }

        return null;
    }

    /** Vacía el administrador encontrado en el formulario y lo marca en la tabla. */
    private void mostrarAdmin(Admin admin) {

        this.idUsuario = admin.getIdUsuario();
        this.txtUsuario.setText(admin.getLogin());
        this.txtContrasena.setEnabled(false);
        this.txtContrasena.setText("(sin cambios)");
        this.txtNombre1.setText(admin.getNombre());
        this.txtApellidos.setText(admin.getApellido());

        for (int i = 0; i < tblRoles.getRowCount(); i++) {
            int idTabla = Integer.parseInt(tblRoles.getValueAt(i, 0).toString());
            if (idTabla == admin.getIdUsuario()) {
                tblRoles.setRowSelectionInterval(i, i);
                tblRoles.scrollRectToVisible(tblRoles.getCellRect(i, 0, true));
                break;
            }
        }
    }

    private void txtNombre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre1ActionPerformed

    private void txtApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidosActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmCrudAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCrudAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCrudAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCrudAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new FrmCrudAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblAlumno;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreVentana;
    private javax.swing.JPanel pnlCrudRol;
    private javax.swing.JPanel pnlOperacionesCrud;
    private javax.swing.JTable tblRoles;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtContrasena;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
