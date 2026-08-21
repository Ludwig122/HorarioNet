package vista;

import controlador.ControladorRol;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Rol;
import java.util.List;

/**
 * CRUD de Roles del sistema.
 * @author Lud
 */
public class FrmCrudRol extends javax.swing.JInternalFrame {

    private ControladorRol controlador = new ControladorRol();
    private int idRolSeleccionado = 0;

    public FrmCrudRol() {
        initComponents();
        // Ícono de la aplicación en la esquina de la ventana interna
        utilidades.Iconos.aplicarA(this);
        this.listar();
        tblRol.getTableHeader().setBackground(new Color(0xF2D9A0));
        tblRol.getTableHeader().setForeground(Color.BLACK);
    }

    /**
     * Deja el formulario como recién abierto: vacía las cajas, suelta el
     * renglón seleccionado en la tabla y olvida el id.
     *
     * Soltar el renglón importa tanto como vaciar las cajas: mientras
     * idRolSeleccionado siga apuntando a un registro, Modificar y Eliminar
     * seguirían trabajando sobre él aunque en pantalla ya no se vea nada
     * escrito. Sirve para cancelar cuando uno se equivocó de renglón o ya no
     * quiere guardar el cambio.
     */
    private void limpiar() {
        txtNombre.setText("");
        txtDescripcion.setText("");

        if (tblRol.getSelectedRow() != -1) {
            tblRol.clearSelection();
        }

        idRolSeleccionado = 0;
        txtNombre.requestFocusInWindow();
    }

    private void listar() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripción");
        this.tblRol.setModel(modelo);
        List<Rol> lista = controlador.listar();
        for (Rol r : lista) {
            Object fila[] = {r.getIdRol(), r.getNombreRol(), r.getDescripcion()};
            modelo.addRow(fila);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCrudRol = new javax.swing.JPanel();
        lblNombreVentana = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        pnlOperacionesCrud = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRol = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Roles");

        pnlCrudRol.setBackground(new java.awt.Color(255, 247, 232));

        lblNombreVentana.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombreVentana.setForeground(new java.awt.Color(0, 0, 0));
        lblNombreVentana.setText("Gestion del catalogo de Roles del sistema");

        lblNombre.setForeground(new java.awt.Color(0, 0, 0));
        lblNombre.setText("Nombre del Rol:");

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setForeground(new java.awt.Color(0, 0, 0));

        lblDescripcion.setForeground(new java.awt.Color(0, 0, 0));
        lblDescripcion.setText("Descripción:");

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setForeground(new java.awt.Color(0, 0, 0));

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

        tblRol.setBackground(new java.awt.Color(255, 255, 255));
        tblRol.setForeground(new java.awt.Color(0, 0, 0));
        tblRol.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRolMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRol);

        javax.swing.GroupLayout pnlOperacionesCrudLayout = new javax.swing.GroupLayout(pnlOperacionesCrud);
        pnlOperacionesCrud.setLayout(pnlOperacionesCrudLayout);
        pnlOperacionesCrudLayout.setHorizontalGroup(
            pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperacionesCrudLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap()
                .addGroup(pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlCrudRolLayout = new javax.swing.GroupLayout(pnlCrudRol);
        pnlCrudRol.setLayout(pnlCrudRolLayout);
        pnlCrudRolLayout.setHorizontalGroup(
            pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlOperacionesCrud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(lblDescripcion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addComponent(lblNombreVentana, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlCrudRolLayout.setVerticalGroup(
            pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombreVentana)
                .addGap(18, 18, 18)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescripcion)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(pnlOperacionesCrud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCrudRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCrudRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe capturar el nombre del rol.");
            return;
        }

        Rol rol = new Rol();
        rol.setNombreRol(txtNombre.getText().trim());
        rol.setDescripcion(txtDescripcion.getText().trim());

        boolean guardado = controlador.guardar(rol);
        if (guardado) {
            JOptionPane.showMessageDialog(this, "Rol guardado correctamente");
            listar();
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "Ya existe un rol con ese nombre.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tblRolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRolMouseClicked
        int fila = tblRol.getSelectedRow();
        if (fila == -1) {
            return;
        }
        this.idRolSeleccionado = Integer.parseInt(tblRol.getValueAt(fila, 0).toString());
        this.txtNombre.setText(tblRol.getValueAt(fila, 1).toString());
        Object descripcion = tblRol.getValueAt(fila, 2);
        this.txtDescripcion.setText(descripcion != null ? descripcion.toString() : "");
    }//GEN-LAST:event_tblRolMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (idRolSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un rol de la tabla.");
            return;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe capturar el nombre del rol.");
            return;
        }

        Rol rol = new Rol(idRolSeleccionado, txtNombre.getText().trim(), txtDescripcion.getText().trim());
        boolean modificado = controlador.modificar(rol);
        if (modificado) {
            JOptionPane.showMessageDialog(this, "Rol modificado correctamente");
            listar();
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo modificar el rol.");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (idRolSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un rol de la tabla.");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar este rol?",
                "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            boolean eliminado = controlador.eliminar(idRolSeleccionado);
            if (eliminado) {
                listar();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el rol (puede tener permisos asignados).");
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String nombreBuscado = JOptionPane.showInputDialog(this, "Ingrese el nombre del rol a buscar");
        if (nombreBuscado == null || nombreBuscado.trim().isEmpty()) {
            return;
        }

        Rol rol = controlador.buscarPorNombre(nombreBuscado.trim());
        if (rol != null) {
            this.idRolSeleccionado = rol.getIdRol();
            this.txtNombre.setText(rol.getNombreRol());
            this.txtDescripcion.setText(rol.getDescripcion());

            for (int i = 0; i < tblRol.getRowCount(); i++) {
                int idFila = Integer.parseInt(tblRol.getValueAt(i, 0).toString());
                if (idFila == rol.getIdRol()) {
                    tblRol.setRowSelectionInterval(i, i);
                    tblRol.scrollRectToVisible(tblRol.getCellRect(i, 0, true));
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, "Rol encontrado");
        } else {
            JOptionPane.showMessageDialog(this, "No existe un rol con ese nombre.");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCrudRol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCrudRol().setVisible(true);
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
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreVentana;
    private javax.swing.JPanel pnlCrudRol;
    private javax.swing.JPanel pnlOperacionesCrud;
    private javax.swing.JTable tblRol;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
