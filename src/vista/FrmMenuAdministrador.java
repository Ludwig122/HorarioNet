package vista;

//import java.awt.Image;
//import javax.swing.ImageIcon;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import dao.RolPermisoDAO;
import modelo.RolPermiso;
import modelo.Usuario;
import utilidades.DesktopFondo;
import utilidades.Sesion;

/**
 *
 * @author Lud
 */
public final class FrmMenuAdministrador extends javax.swing.JFrame {
    private DesktopFondo escritorio = new DesktopFondo();  

    public FrmMenuAdministrador() {
        initComponents();
        // Ícono de la aplicación (esquina de la ventana y barra de tareas)
        utilidades.Iconos.aplicarA(this);
        this.setContentPane(escritorio);          // NUEVO — pone el fondo con imagen
    this.setExtendedState(MAXIMIZED_BOTH); 
        //A partir de aquí voy a modificar los componentes que no se quieran modificar desde el design
        //Porque yo no les tengo que pedir permiso wey, yo soy el programador;
        //que los pinches componentes se adapten a mi, yo no a ellos, PUNTO
        menuBar.setOpaque(true);
        menuBar.setBackground(new java.awt.Color(97, 18, 51)); // aquí edito el color como si lo editara desde mi design
        menuBar.setForeground(new java.awt.Color(255, 255, 255));
//para poner el icono
    setIcon();

    // Deja visibles solo las opciones que el rol tenga permitidas
    cargarPermisos();
    }

    /**
     * Muestra u oculta las opciones del menú según los permisos que tenga
     * asignado el rol del usuario que inició sesión.
     *
     * De dónde sale la información: la tabla rol_permiso, que se administra
     * desde "Gestionar Permisos y Roles". Hasta antes de esto esa tabla
     * existía y se podía editar, pero nadie la leía: el menú siempre se
     * mostraba completo. Ahora sí manda.
     *
     * Se OCULTAN las opciones en lugar de solo deshabilitarlas, para que el
     * usuario no vea funciones que no le tocan.
     *
     * Ojo: esto es comodidad y orden en la interfaz, NO es la seguridad del
     * sistema. La protección de verdad es que el Login manda a cada rol a su
     * propia ventana: un docente jamás llega a este menú. Esconder un botón
     * nunca debe ser la única barrera.
     */
    private void cargarPermisos() {

        Usuario usuario = Sesion.getUsuario();

        // Si por alguna razón no hay sesión, se deja el menú completo para no
        // dejar al administrador encerrado sin poder hacer nada.
        if (usuario == null) {
            return;
        }

        // Nombres de los permisos que tiene el rol, en MAYÚSCULAS porque así
        // quedaron guardados en la base tras la migración.
        List<String> permisos = new ArrayList<>();

        for (RolPermiso rp : new RolPermisoDAO().listarPorRol(usuario.getIdRol())) {
            permisos.add(rp.getPermiso().getNombre().trim().toUpperCase());
        }

        // Si el rol no tiene NINGÚN permiso asignado, lo más probable es que
        // todavía no se hayan configurado. Se deja el menú completo en vez de
        // dejar la pantalla en blanco.
        if (permisos.isEmpty()) {
            return;
        }

        itmEditDocente.setVisible(permisos.contains("GESTIONAR DOCENTES"));

        itmEditAlumno.setVisible(permisos.contains("GESTIONAR ALUMNOS"));
        itmAsignarAlumnoGrupo.setVisible(permisos.contains("GESTIONAR ALUMNOS"));

        itmEditAdmin.setVisible(permisos.contains("GESTIONAR ADMINISTRADORES"));

        // Roles, permisos y su asignación son la misma facultad: configurar
        // la seguridad del sistema.
        boolean puedeConfigurarSeguridad = permisos.contains("GESTIONAR PERMISOS");
        itmGestionarRoles.setVisible(puedeConfigurarSeguridad);
        itmGestionarPermisos.setVisible(puedeConfigurarSeguridad);
        itmConfigurePermisosRol.setVisible(puedeConfigurarSeguridad);

        itmEditHorario.setVisible(permisos.contains("GESTIONAR HORARIOS"));
        itmConsultarHorario.setVisible(permisos.contains("CONSULTAR HORARIOS"));

        // "Salir" nunca se esconde: si se ocultara, el usuario no tendría
        // forma de cerrar sesión.

        ocultarMenusVacios();
    }

    /**
     * Esconde los menús de la barra que se quedaron sin ninguna opción
     * visible.
     *
     * Sin esto quedarían títulos como "Administradores" que al darles clic
     * despliegan un recuadro vacío, y eso se ve peor que no tenerlos.
     */
    private void ocultarMenusVacios() {

        for (int i = 0; i < menuBar.getMenuCount(); i++) {

            JMenu menu = menuBar.getMenu(i);

            if (menu == null) {
                continue;
            }

            boolean tieneAlgoVisible = false;

            for (int j = 0; j < menu.getItemCount(); j++) {
                JMenuItem item = menu.getItem(j);
                if (item != null && item.isVisible()) {
                    tieneAlgoVisible = true;
                    break;
                }
            }

            menu.setVisible(tieneAlgoVisible);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dskEscritorio = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        opcUsuarios = new javax.swing.JMenu();
        itmEditDocente = new javax.swing.JMenuItem();
        opcAlumnos = new javax.swing.JMenu();
        itmEditAlumno = new javax.swing.JMenuItem();
        itmAsignarAlumnoGrupo = new javax.swing.JMenuItem();
        opcAdmins = new javax.swing.JMenu();
        itmEditAdmin = new javax.swing.JMenuItem();
        itmConfigurePermisosRol = new javax.swing.JMenuItem();
        itmGestionarRoles = new javax.swing.JMenuItem();
        itmGestionarPermisos = new javax.swing.JMenuItem();
        opcHorario = new javax.swing.JMenu();
        itmEditHorario = new javax.swing.JMenuItem();
        itmConsultarHorario = new javax.swing.JMenuItem();
        opcSalir = new javax.swing.JMenu();
        itmSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Principal Horarionet\n");
        setAlwaysOnTop(true);

        dskEscritorio.setForeground(new java.awt.Color(204, 204, 204));

        menuBar.setBackground(new java.awt.Color(97, 18, 51));
        menuBar.setToolTipText("Menu Principal HorarioNet\n");

        opcUsuarios.setBackground(new java.awt.Color(97, 18, 51));
        opcUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        opcUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Docente48x48.png"))); // NOI18N
        opcUsuarios.setMnemonic('f');
        opcUsuarios.setText("Docentes");
        opcUsuarios.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N

        itmEditDocente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/EditPerson24x24.png"))); // NOI18N
        itmEditDocente.setMnemonic('s');
        itmEditDocente.setText("Editar Docente");
        itmEditDocente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmEditDocenteActionPerformed(evt);
            }
        });
        opcUsuarios.add(itmEditDocente);

        menuBar.add(opcUsuarios);

        opcAlumnos.setBackground(new java.awt.Color(97, 18, 51));
        opcAlumnos.setForeground(new java.awt.Color(255, 255, 255));
        opcAlumnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Alumno48x48.png"))); // NOI18N
        opcAlumnos.setMnemonic('e');
        opcAlumnos.setText("Alumnos");
        opcAlumnos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N

        itmEditAlumno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/EditPerson24x24.png"))); // NOI18N
        itmEditAlumno.setMnemonic('y');
        itmEditAlumno.setText("Editar Alumno");
        itmEditAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmEditAlumnoActionPerformed(evt);
            }
        });
        opcAlumnos.add(itmEditAlumno);

        itmAsignarAlumnoGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono_grupo_asignar.png"))); // NOI18N
        itmAsignarAlumnoGrupo.setMnemonic('p');
        itmAsignarAlumnoGrupo.setText("Historial de Horarios");
        itmAsignarAlumnoGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmAsignarAlumnoGrupoActionPerformed(evt);
            }
        });
        opcAlumnos.add(itmAsignarAlumnoGrupo);

        menuBar.add(opcAlumnos);

        opcAdmins.setBackground(new java.awt.Color(97, 18, 51));
        opcAdmins.setForeground(new java.awt.Color(255, 255, 255));
        opcAdmins.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/admin_icon48x48.png"))); // NOI18N
        opcAdmins.setText("Administradores");
        opcAdmins.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N

        itmEditAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/EditPerson24x24.png"))); // NOI18N
        itmEditAdmin.setMnemonic('y');
        itmEditAdmin.setText("Editar Admin");
        itmEditAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmEditAdminActionPerformed(evt);
            }
        });
        opcAdmins.add(itmEditAdmin);

        itmConfigurePermisosRol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icon_permisos_configure24x24.png"))); // NOI18N
        itmConfigurePermisosRol.setText("Gestionar Permisos y Roles");
        itmConfigurePermisosRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmConfigurePermisosRolActionPerformed(evt);
            }
        });
        opcAdmins.add(itmConfigurePermisosRol);

        itmGestionarRoles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Rol24x24.png"))); // NOI18N
        itmGestionarRoles.setText("Gestionar Roles");
        itmGestionarRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmGestionarRolesActionPerformed(evt);
            }
        });
        opcAdmins.add(itmGestionarRoles);

        itmGestionarPermisos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Permiso24x24.png"))); // NOI18N
        itmGestionarPermisos.setText("Gestionar Permisos");
        itmGestionarPermisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmGestionarPermisosActionPerformed(evt);
            }
        });
        opcAdmins.add(itmGestionarPermisos);

        menuBar.add(opcAdmins);

        opcHorario.setBackground(new java.awt.Color(97, 18, 51));
        opcHorario.setForeground(new java.awt.Color(255, 255, 255));
        opcHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Horario48.png"))); // NOI18N
        opcHorario.setText("Horarios");
        opcHorario.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N

        itmEditHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/HorarioEditar24x24.png"))); // NOI18N
        itmEditHorario.setText("Editar Horario");
        itmEditHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmEditHorarioActionPerformed(evt);
            }
        });
        opcHorario.add(itmEditHorario);

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
            .addGroup(layout.createSequentialGroup()
                .addComponent(dskEscritorio, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dskEscritorio, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itmAsignarAlumnoGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmAsignarAlumnoGrupoActionPerformed
        // TODO add your handling code here:
          FrmHistorial frm = new FrmHistorial();
    escritorio.add(frm);   // antes: dskEscritorio.add(frm)
    frm.setVisible(true);
    }//GEN-LAST:event_itmAsignarAlumnoGrupoActionPerformed

    private void itmSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSalirActionPerformed
        // TODO add your handling code here:
        int opcion = JOptionPane.showConfirmDialog(
                this, "¿Esta seguro de que desea salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            utilidades.Sesion.cerrarSesion();
            new Login().setVisible(true);
            this.dispose();
        }

    }//GEN-LAST:event_itmSalirActionPerformed

    private void itmEditAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmEditAlumnoActionPerformed
  FrmCrudAlumno frm = new FrmCrudAlumno();
    escritorio.add(frm);   // antes: dskEscritorio.add(frm)
    frm.setVisible(true);
    }//GEN-LAST:event_itmEditAlumnoActionPerformed

    private void itmEditAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmEditAdminActionPerformed
        // TODO add your handling code here:
           FrmCrudAdmin frm = new FrmCrudAdmin();
    escritorio.add(frm);   // antes: dskEscritorio.add(frm)
    frm.setVisible(true);
    }//GEN-LAST:event_itmEditAdminActionPerformed

    private void itmEditDocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmEditDocenteActionPerformed
   FrmCrudDocente frm = new FrmCrudDocente();
    escritorio.add(frm);   // antes: dskEscritorio.add(frm)
    frm.setVisible(true);
    }//GEN-LAST:event_itmEditDocenteActionPerformed

    private void itmConfigurePermisosRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmConfigurePermisosRolActionPerformed
        // TODO add your handling code here:
                  FrmCrudRolPermiso frm = new FrmCrudRolPermiso();
    escritorio.add(frm);   // antes: dskEscritorio.add(frm)
    frm.setVisible(true);
    }//GEN-LAST:event_itmConfigurePermisosRolActionPerformed

    private void itmGestionarRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmGestionarRolesActionPerformed
        // TODO add your handling code here:
                          FrmCrudRol frm = new FrmCrudRol();
    escritorio.add(frm);   // antes: dskEscritorio.add(frm)
    frm.setVisible(true);
    }//GEN-LAST:event_itmGestionarRolesActionPerformed

    private void itmGestionarPermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmGestionarPermisosActionPerformed
        // TODO add your handling code here:
                          FrmCrudPermiso frm = new FrmCrudPermiso();
    escritorio.add(frm);   // antes: dskEscritorio.add(frm)
    frm.setVisible(true);
    }//GEN-LAST:event_itmGestionarPermisosActionPerformed

    private void itmConsultarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmConsultarHorarioActionPerformed
        // Misma ventana de solo lectura que usa el docente
        FrmConsultaHorario frm = new FrmConsultaHorario();
        escritorio.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_itmConsultarHorarioActionPerformed

    private void itmEditHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmEditHorarioActionPerformed
        // TODO add your handling code here:
                                 FrmCrudHorario frm = new FrmCrudHorario();
    escritorio.add(frm);   // antes: dskEscritorio.add(frm)
    frm.setVisible(true);
    }//GEN-LAST:event_itmEditHorarioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        // Esta parte del código me ayudó la ia para que se viera más "Material Design"
        System.setProperty("flatlaf.menuBarEmbedded", "false");

        try {
            com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTMaterialOceanicIJTheme.setup(); //aqui importamos el estilo usando nuestra super librería
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//Aquí establecemos el fondo de donde pone el nombre del programa
        javax.swing.UIManager.getLookAndFeelDefaults().put("TitlePane.unifiedBackground", false);
        javax.swing.UIManager.getLookAndFeelDefaults().put("TitlePane.background", new java.awt.Color(65, 105, 225));
        javax.swing.UIManager.getLookAndFeelDefaults().put("TitlePane.foreground", java.awt.Color.WHITE);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenuAdministrador().setVisible(true);
            }
        });// fin de la parte de la ia que me ayudó a manejar la librería
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane dskEscritorio;
    private javax.swing.JMenuItem itmAsignarAlumnoGrupo;
    private javax.swing.JMenuItem itmConfigurePermisosRol;
    private javax.swing.JMenuItem itmConsultarHorario;
    private javax.swing.JMenuItem itmEditAdmin;
    private javax.swing.JMenuItem itmEditAlumno;
    private javax.swing.JMenuItem itmEditDocente;
    private javax.swing.JMenuItem itmEditHorario;
    private javax.swing.JMenuItem itmGestionarPermisos;
    private javax.swing.JMenuItem itmGestionarRoles;
    private javax.swing.JMenuItem itmSalir;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu opcAdmins;
    private javax.swing.JMenu opcAlumnos;
    private javax.swing.JMenu opcHorario;
    private javax.swing.JMenu opcSalir;
    private javax.swing.JMenu opcUsuarios;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
    setIconImage(Toolkit.getDefaultToolkit().getImage(
        getClass().getResource("/imagenes/LogoColor48x48.png")));
}

}
