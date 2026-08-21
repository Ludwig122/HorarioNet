package vista;

import javax.swing.table.DefaultTableModel;
import modelo.Rol;
import java.util.List;
import javax.swing.JOptionPane;
//import javax.swing.JInternalFrame;

/**
 *
 * @author Ace of Base- The sign
 */
public class FrmCrudUsuarioUnused extends javax.swing.JInternalFrame {
//
//    //
//    private ControladorRol controlador = new ControladorRol();
//    //Declaración de atributo de clase idRol, par manjar las operaciones
//    //del registro sleccionao del CRUD
//    private int idRol = 0;
//    //método constructor de la clase
//
//    public FrmCrudUsuarioUnused() {
//        initComponents();
//        //Método que se va a encargar de leer los registros de la tabla rol
//        this.listarRoles();
//    }
//
//    private void limpiar() {
//        txtUsuario.setText("");
//        txtContrasena.setText("");
//        idRol = 0;
//    }
//
//    private void listarRoles() {
//        DefaultTableModel modelo = new DefaultTableModel();
//        modelo.addColumn("Id");
//        modelo.addColumn("Nombre");
//        modelo.addColumn("Descripción");
//        tblRoles.setModel(modelo);
//        List<Rol> lista = controlador.listar(); //colocaod las cabeceras de las columnas de la tabla
//        for (Rol rol : lista) {
//            Object fila[] = {rol.getIdRol(), rol.getNombreRol(), rol.getDescripcion()};
//            modelo.addRow(fila);
//        }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCrudRol = new javax.swing.JPanel();
        lblNombreVentana = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JTextField();
        pnlOperacionesCrud = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRoles = new javax.swing.JTable();
        cmbFiltroIdUsuario = new javax.swing.JComboBox<>();
        jCalendar1 = new com.toedter.calendar.JCalendar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlCrudRol.setBackground(new java.awt.Color(204, 255, 204));

        lblNombreVentana.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombreVentana.setForeground(new java.awt.Color(0, 0, 0));
        lblNombreVentana.setText("Gestion del catalogo de Usuarios del sistema ");

        lblUsuario.setForeground(new java.awt.Color(0, 0, 0));
        lblUsuario.setText("Nombre del Usuario:");

        txtUsuario.setForeground(new java.awt.Color(0, 0, 0));

        txtUsuario.setBackground(new java.awt.Color(255, 255, 255));
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });

        lblContrasena.setForeground(new java.awt.Color(0, 0, 0));
        lblContrasena.setText("Contraseña");

        txtContrasena.setForeground(new java.awt.Color(0, 0, 0));

        txtContrasena.setBackground(new java.awt.Color(255, 255, 255));

        pnlOperacionesCrud.setBackground(new java.awt.Color(255, 255, 153));
        pnlOperacionesCrud.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Operaciones disponibles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        pnlOperacionesCrud.setForeground(new java.awt.Color(0, 0, 0));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tblRoles.setForeground(new java.awt.Color(0, 0, 0));

        tblRoles.setBackground(new java.awt.Color(255, 255, 255));

        tblRoles.setBackground(new java.awt.Color(255, 255, 255));
        tblRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id Usuario", "Nombre", "Rol", "Último Acceso"
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

        javax.swing.GroupLayout pnlOperacionesCrudLayout = new javax.swing.GroupLayout(pnlOperacionesCrud);
        pnlOperacionesCrud.setLayout(pnlOperacionesCrudLayout);
        pnlOperacionesCrudLayout.setHorizontalGroup(
            pnlOperacionesCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperacionesCrudLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(btnGuardar)
                .addGap(152, 152, 152)
                .addComponent(btnModificar)
                .addGap(73, 73, 73)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                .addComponent(btnBuscar)
                .addGap(69, 69, 69))
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
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmbFiltroIdUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlCrudRolLayout = new javax.swing.GroupLayout(pnlCrudRol);
        pnlCrudRol.setLayout(pnlCrudRolLayout);
        pnlCrudRolLayout.setHorizontalGroup(
            pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreVentana, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario)
                            .addComponent(lblContrasena))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(txtContrasena))
                        .addGap(30, 30, 30)
                        .addComponent(cmbFiltroIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlOperacionesCrud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlCrudRolLayout.setVerticalGroup(
            pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudRolLayout.createSequentialGroup()
                .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrudRolLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblNombreVentana)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario)
                            .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbFiltroIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlCrudRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblContrasena)
                            .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlOperacionesCrud, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
//        // TODO add your handling code here:
//        if (txtUsuario.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this,
//                    "Debe capturar el nombre del rol");
//            txtUsuario.requestFocus();
//            return;
//
//        }
//        if (txtContrasena.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this,
//                    "Debe capturar el nombre de la descripcion");
//            txtContrasena.requestFocus();
//            return;
//        }
//        //Solicitando al controlador llamar al metodo que permitira guardar
//        //a traves de la clase DAO el nuevo rol
//        boolean guardado = controlador.guardar(txtUsuario.getText(),
//                txtContrasena.getText());
//        if (guardado) {
//            JOptionPane.showMessageDialog(this, "Rol guardado correctamente");
//            this.listarRoles();
//            this.limpiar();
//        }


    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
//        // TODO add your handling code here:
//        if (idRol == 0) {
//            JOptionPane.showMessageDialog(this, "Seleccione un rol");
//            return;
//        }
//        //verificando si desean borrar el registro
//        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el rol?",
//                "Confirmar", JOptionPane.YES_NO_OPTION);
//        /* Si el usuario quiere eliminar un registro, se llama al controlador que
//        hae uso del método que elimina el registro seleccionado
//         */
//        if (opcion == JOptionPane.YES_NO_OPTION) {
//            controlador.eliminar(idRol); //Eliminando el registro
//            this.listarRoles(); //llenando el table data
//            this.limpiar(); //limpiando las cajas de texto
//        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblRolesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRolesMouseClicked
//        int fila = tblRoles.getSelectedRow(); //Conociendo cuál es el renglón seleccionado
//        this.idRol = Integer.parseInt(tblRoles.getValueAt(fila, 0).toString());
//        this.txtUsuario.setText(tblRoles.getValueAt(fila, 1).toString());
//        this.txtContrasena.setText(tblRoles.getValueAt(fila, 2).toString());
    }//GEN-LAST:event_tblRolesMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
//        //Se verfica que se teng seleccionado el registro a modificar
//        if (idRol == 0) {
//            JOptionPane.showMessageDialog(this, "Seleccione un rol");
//            return;
//
//        }
//        controlador.modificar(idRol, txtUsuario.getText(), txtContrasena.getText());
//        listarRoles(); //Lena el table data con el contenido de la tabla rolesDAO
//        limpiar();     //Deja las cajas de texto vacás
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
//        //Método que busca a un rol por su nombre
//        int idTabla; //se declara la variable que retorna el id donde se localiza el dato
//        Rol rol;
//        String nombreRol = JOptionPane.showInputDialog(this, "Ingrese el nombre del rol");
////Si la busqueda es cancelada o está vacío el campo
//        if (nombreRol == null || nombreRol.trim().isEmpty()) {
//            return;
//        }
//
//        rol = controlador.buscar(nombreRol);
//        // Si el objeto trol tiene información que localizó
//        if (rol != null) {
//            /*Cargamos el formulario con los datos que tiene el objeto rol a través
//            de sus métodos get
//             */
//            this.idRol = rol.getIdRol();
//            this.txtUsuario.setText(rol.getNombreRol());
//            this.txtContrasena.setText(rol.getNombreRol());
//            // Seleccionar en el Jtable el registro localizado
//            for (int i = 0; i < tblRoles.getRowCount(); i++) {
//                //Se obtiene el id de cada registro contenido en el jtable
//                idTabla = Integer.parseInt(tblRoles.getValueAt(i, 0).toString());
//                //Se compara el di del jtable vs el id del objeto proveniente del CRUD
//                if (idTabla == rol.getIdRol()) {
//                    //Selecciona fila
//                    tblRoles.setRowSelectionInterval(i, i);
//                    // Hace scroll para situar el foco en sa celda de la tabla
//                    tblRoles.scrollRectToVisible(tblRoles.getCellRect(i, 0, true));
//                    break;
//                }
//            }
//            //Se informa que se localizó el registro
//            JOptionPane.showMessageDialog(this, "Rol encontrado");
//        }else {
//        JOptionPane.showMessageDialog(this, "Rol no encontrado");
//        }
        
    }//GEN-LAST:event_btnBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmCrudUsuarioUnused.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCrudUsuarioUnused.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCrudUsuarioUnused.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCrudUsuarioUnused.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCrudUsuarioUnused().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cmbFiltroIdUsuario;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblNombreVentana;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlCrudRol;
    private javax.swing.JPanel pnlOperacionesCrud;
    private javax.swing.JTable tblRoles;
    private javax.swing.JTextField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
