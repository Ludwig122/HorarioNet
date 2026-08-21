package vista;

import controlador.ControladorRolPermiso;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import modelo.Permiso;
import modelo.Rol;
import modelo.RolPermiso;
import java.util.List;

/**
 *
 * @author Equipo 4 chiludos de Nayarit
 */
public class FrmCrudRolPermiso extends javax.swing.JInternalFrame {
    private ControladorRolPermiso controlador = new ControladorRolPermiso();
    private Rol rolSelccionado;
    private Permiso permisoSeleccionado;

    public FrmCrudRolPermiso() {
        initComponents();
        // Ícono de la aplicación en la esquina de la ventana interna
        utilidades.Iconos.aplicarA(this);
        this.listar();
        this.cargarRoles();
        this.cargarPermisos();
        this.cargarFiltroRoles();
        tblRolPermiso.getTableHeader().setBackground(new Color(0xF2D9A0));
        tblRolPermiso.getTableHeader().setForeground(Color.BLACK);


    }
//______________________________________________________________________________________________________________________________
    //metodo que llena el combo box de los roles
    private void cargarRoles() {
        this.cmbRol.removeAllItems();
        List<Rol> lista = controlador.obtenerRoles();
        for (Rol rol : lista) {
            this.cmbRol.addItem(rol);
        }
    }

    //_____________________________________________________________________________________________________________________________
    //metodo que llena el combo box  los permisos
    private void cargarPermisos() {
        this.cmbPermiso.removeAllItems();
        List<Permiso> lista = controlador.obtenerPermisos();
        for (Permiso permiso : lista) {
            this.cmbPermiso.addItem(permiso);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------------
    //metodo que llena el combo box  del filtro
    private void cargarFiltroRoles() {
        this.cmbFiltroRol.removeAllItems();
        List<Rol> lista = controlador.obtenerRoles();
        for (Rol rol : lista) {
            this.cmbFiltroRol.addItem(rol);
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------
    //metodo que llena el JTable de permisos rol
    private void listar() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Rol");
        modelo.addColumn("Permiso");
        this.tblRolPermiso.setModel(modelo);
        List<RolPermiso> lista = controlador.listar();
        for (RolPermiso rp : lista) {
            Object fila[] = {
                rp.getRol().getNombreRol(),
                rp.getPermiso().getNombre()
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Deja el formulario como recién abierto: regresa los combos al primer
     * elemento, suelta el renglón seleccionado y olvida la pareja
     * rol/permiso que estaba marcada.
     *
     * Olvidar la selección es lo importante: mientras rolSelccionado y
     * permisoSeleccionado sigan apuntando a algo, Modificar y Eliminar
     * seguirían trabajando sobre esa pareja aunque el usuario ya haya movido
     * los combos a otra cosa.
     */
    private void limpiarSeleccion() {

        rolSelccionado = null;
        permisoSeleccionado = null;

        if (cmbRol.getItemCount() > 0) {
            cmbRol.setSelectedIndex(0);
        }

        if (cmbPermiso.getItemCount() > 0) {
            cmbPermiso.setSelectedIndex(0);
        }

        if (tblRolPermiso.getSelectedRow() != -1) {
            tblRolPermiso.clearSelection();
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCrudRol = new javax.swing.JPanel();
        lblNombreVentana = new javax.swing.JLabel();
        pnlOperacionesCrud = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRolPermiso = new javax.swing.JTable();
        cmbRol = new javax.swing.JComboBox<>();
        lblPermiso = new javax.swing.JLabel();
        cmbPermiso = new javax.swing.JComboBox<>();
        lblCarrera = new javax.swing.JLabel();
        cmbFiltroRol = new javax.swing.JComboBox<>();
        lblFiltrar = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Ventana de Gestión de Permisos de Roles");

        pnlCrudRol.setBackground(new java.awt.Color(255, 247, 232));

        lblNombreVentana.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombreVentana.setForeground(new java.awt.Color(0, 0, 0));
        lblNombreVentana.setText("Gestion del catalogo de Permisos de Roles del Sistema");

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
        btnLimpiar.setToolTipText("Regresa los combos y suelta el renglón seleccionado");
        btnLimpiar.setBorder(null);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tblRolPermiso.setBackground(new java.awt.Color(255, 255, 255));
        tblRolPermiso.setForeground(new java.awt.Color(0, 0, 0));
        tblRolPermiso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Rol", "Permiso"
            }
        ));
        tblRolPermiso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRolPermisoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRolPermiso);

        javax.swing.GroupLayout pnlOperacionesCrudLayout = new javax.swing.GroupLayout(pnlOperacionesCrud);
        pnlOperacionesCrud.setLayout(pnlOperacionesCrudLayout);
        pnlOperacionesCrudLayout.setHorizontalGroup(
            pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperacionesCrudLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(171, 171, 171)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        cmbRol.setBackground(new java.awt.Color(240, 248, 255));
        cmbRol.setForeground(new java.awt.Color(0, 0, 0));
        cmbRol.setModel(new javax.swing.DefaultComboBoxModel<Rol>());

        lblPermiso.setForeground(new java.awt.Color(0, 0, 0));
        lblPermiso.setText("Nombre del Permiso");

        cmbPermiso.setBackground(new java.awt.Color(240, 248, 255));
        cmbPermiso.setForeground(new java.awt.Color(0, 0, 0));
        cmbPermiso.setModel(new javax.swing.DefaultComboBoxModel<Permiso>());

        lblCarrera.setForeground(new java.awt.Color(0, 0, 0));
        lblCarrera.setText("Nombre del Rol");

        cmbFiltroRol.setBackground(new java.awt.Color(240, 248, 255));
        cmbFiltroRol.setForeground(new java.awt.Color(0, 0, 0));
        cmbFiltroRol.setModel(new javax.swing.DefaultComboBoxModel<Rol>());

        lblFiltrar.setForeground(new java.awt.Color(0, 0, 0));
        lblFiltrar.setText("Filtrar");

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
                        .addGap(23, 23, 23)
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                .addComponent(lblNombreVentana, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCarrera)
                                    .addComponent(lblPermiso))
                                .addGap(38, 38, 38)
                                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbPermiso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbRol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(158, 158, 158)
                                .addComponent(lblFiltrar)
                                .addGap(18, 18, 18)
                                .addComponent(cmbFiltroRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(373, 373, 373)))))
                .addContainerGap())
        );
        pnlCrudRolLayout.setVerticalGroup(
            pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombreVentana)
                .addGap(18, 18, 18)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCarrera)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltroRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFiltrar))
                .addGap(18, 18, 18)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPermiso)
                    .addComponent(cmbPermiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(pnlOperacionesCrud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
        Rol rol = (Rol) cmbRol.getSelectedItem();
        Permiso permiso = (Permiso) cmbPermiso.getSelectedItem();

        if (rol == null || permiso == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un rol y un permiso.");
            return;
        }

        boolean guardado = controlador.guardar(rol, permiso);
        if (guardado) {
            JOptionPane.showMessageDialog(this, "Permiso asignado correctamente");
            listar();
        } else {
            JOptionPane.showMessageDialog(this, "Ese permiso ya existe!");
        }


    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int opcion;
        Rol rol = (Rol) cmbRol.getSelectedItem();
        Permiso permiso = (Permiso) cmbPermiso.getSelectedItem();

        if (rol == null || permiso == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un rol y un permiso.");
            return;
        }

        opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar este permiso del rol?",
                "Confirmación", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            this.controlador.eliminar(rol, permiso);
            this.listar();
            this.limpiarSeleccion();
        }


    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblRolPermisoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRolPermisoMouseClicked
        
 int fila = tblRolPermiso.getSelectedRow();

        if (fila >= 0) {
            String nombreRol = tblRolPermiso.getValueAt(fila, 0).toString();
            String nombrePermiso = tblRolPermiso.getValueAt(fila, 1).toString();

            // Buscar el Rol
            for (int i = 0; i < cmbRol.getItemCount(); i++) {
                Rol rol = cmbRol.getItemAt(i);
                if (rol.getNombreRol().equals(nombreRol)) {
                    this.cmbRol.setSelectedIndex(i);
                    this.rolSelccionado = rol;
                    break;
                }
            }

            // Buscar el Permiso
            for (int i = 0; i < cmbPermiso.getItemCount(); i++) {
                Permiso permiso = cmbPermiso.getItemAt(i);
                if (permiso.getNombre().equals(nombrePermiso)) {
                    this.cmbPermiso.setSelectedIndex(i);
                    this.permisoSeleccionado = permiso;
                    break;
                }
            }
        }



    }//GEN-LAST:event_tblRolPermisoMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
if (rolSelccionado == null || permisoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona primero una fila de la tabla para modificar.");
            return;
        }

        Rol nuevoRol = (Rol) cmbRol.getSelectedItem();
        Permiso nuevoPermiso = (Permiso) cmbPermiso.getSelectedItem();

        if (nuevoRol == null || nuevoPermiso == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un rol y un permiso.");
            return;
        }

        if (nuevoRol.getIdRol() == rolSelccionado.getIdRol()
                && nuevoPermiso.getIdPermiso() == permisoSeleccionado.getIdPermiso()) {
            JOptionPane.showMessageDialog(this, "No hay cambios que guardar.");
            return;
        }

        controlador.eliminar(rolSelccionado, permisoSeleccionado);
        boolean guardado = controlador.guardar(nuevoRol, nuevoPermiso);

        if (guardado) {
            JOptionPane.showMessageDialog(this, "Permiso modificado correctamente");
            listar();
            limpiarSeleccion();
        } else {
            JOptionPane.showMessageDialog(this, "Esa combinación ya existía, no se modificó.");
        }


    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
Rol rol = (Rol) cmbRol.getSelectedItem();
        Permiso permiso = (Permiso) cmbPermiso.getSelectedItem();

        if (rol == null || permiso == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un rol y un permiso.");
            return;
        }

        boolean encontrado = false;
        for (int i = 0; i < tblRolPermiso.getRowCount(); i++) {
            String nombreRolFila = tblRolPermiso.getValueAt(i, 0).toString();
            String nombrePermisoFila = tblRolPermiso.getValueAt(i, 1).toString();
            if (nombreRolFila.equals(rol.getNombreRol()) && nombrePermisoFila.equals(permiso.getNombre())) {
                tblRolPermiso.setRowSelectionInterval(i, i);
                tblRolPermiso.scrollRectToVisible(tblRolPermiso.getCellRect(i, 0, true));
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "Ese rol no tiene asignado ese permiso.");
        }


    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarSeleccion();
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
            java.util.logging.Logger.getLogger(FrmCrudRolPermiso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCrudRolPermiso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCrudRolPermiso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCrudRolPermiso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FrmCrudRolPermiso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<Rol> cmbFiltroRol;
    private javax.swing.JComboBox<Permiso> cmbPermiso;
    private javax.swing.JComboBox<Rol> cmbRol;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblFiltrar;
    private javax.swing.JLabel lblNombreVentana;
    private javax.swing.JLabel lblPermiso;
    private javax.swing.JPanel pnlCrudRol;
    private javax.swing.JPanel pnlOperacionesCrud;
    private javax.swing.JTable tblRolPermiso;
    // End of variables declaration//GEN-END:variables
}
