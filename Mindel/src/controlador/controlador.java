package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import modelo.miComboBox;
import modelo.modelo;
import vista.Carro;
import vista.ContratarProveedor;
import vista.CreacionOferta;
import vista.ProveedorNuevoProducto;
import vista.OfertaAplicada;
import vista.ProveedorListaProductos;
import vista.interfaz;
import vista.Registro;
import vista.RegistroCliente;
import vista.RegistroEmpleado;
import vista.Seccion;
import vista.menuCliente;
import vista.ProveedorCarroProductos;
import vista.menuEmpleado;

public class controlador implements ActionListener, MouseListener, KeyListener, ItemListener {

    /**
     * instancia nuestra (s) interfaz
     */
    interfaz vista;
    Carro carro;
    ContratarProveedor cp;
    CreacionOferta co;
    OfertaAplicada oa;
    ProveedorNuevoProducto pnp;
    ProveedorCarroProductos pcp;
    ProveedorListaProductos plp;
    Registro r;
    RegistroCliente rc;
    RegistroEmpleado re;
    Seccion s;
    menuCliente mc;
    menuEmpleado me;
    JFileChooser dlg;
    int option, cont = 0, cant = 1, idCliente, idEmpleado;
    String ruta;
    Object lc[][];
    boolean tipoUsu;
    /**
     * instancia a nuestro(s) modelo(s)
     */
    modelo modelo = new modelo();
    miComboBox mcb = new miComboBox();
    DefaultTableModel tc = new DefaultTableModel(
            new Object[1][3], new String[]{"Nombre del Producto", "Descripcion", "Precio"});

    public enum AccionMVC {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (AccionProyecto.valueOf(e.getActionCommand())) {
            case _btnAceptarCarro:

                break;
            case _btnCancelarCarro:

                break;
            case _btnEliminarCarro:

                break;
            case _btnSalirCarro:
                this.carro.setVisible(false);
                break;
            case _btnContratarProveedor:
                if (this.modelo.registrarProveedor(this.cp.txtNombProvee.getText(), this.cp.txtDomiProvee.getText(), Integer.parseInt(this.cp.txtCPProvee.getText()))) {
                    JOptionPane.showMessageDialog(null, "Registro completado");
                    this.cp.txtNombProvee.setText("");
                    this.cp.txtDomiProvee.setText("");
                    this.cp.txtCPProvee.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Error en la introduccion de datos");
                }
                break;
            case _btnCancelarProveedor:
                this.cp.txtNombProvee.setText("");
                this.cp.txtDomiProvee.setText("");
                this.cp.txtCPProvee.setText("");
                this.cp.setVisible(false);
                this.me.setVisible(true);
                break;
            case _btnCrearOferta:
                if (this.modelo.crearOferta(this.co.txtDescOferta.getText(), Double.parseDouble(this.co.txtCantidadOferta.getText()))) {
                    JOptionPane.showMessageDialog(null, "Registro completado");
                    this.cp.txtNombProvee.setText("");
                    this.cp.txtDomiProvee.setText("");
                    this.cp.txtCPProvee.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Error en la introduccion de datos");
                }
                break;
            case _btnCancelarOferta:
                this.co.txtDescOferta.setText("");
                this.co.txtCantidadOferta.setText("");
                this.co.setVisible(false);
                this.me.setVisible(true);
                break;
            case _btnSalirPCP:
                this.pcp.setVisible(false);
                break;
            case _btnEnviarPNP:
                if (this.modelo.crearProducto(this.pnp.txtNombrePNP.getText(), this.pnp.txtADescPNP.getText(), String.valueOf(this.pnp.CBSeccionEnvio.getSelectedIndex()), (double) this.pnp.SpinPrecioPNP.getValue())) {
                    this.pnp.txtNombrePNP.setText("");
                    this.pnp.txtADescPNP.setText("");
                    this.pnp.SpinCantidadPNP.setValue(0);
                    this.pnp.SpinPrecioPNP.setValue(0);
                }
                break;
            case _btnCancelarPNP:
                this.pnp.txtNombrePNP.setText("");
                this.pnp.txtADescPNP.setText("");
                this.pnp.SpinCantidadPNP.setValue(0);
                this.pnp.SpinPrecioPNP.setValue(0);
                this.pnp.setVisible(false);
                break;
            case _btnCarroPLP:
                this.pcp.setVisible(true);
                break;
            case _btnSalirPLP:
                this.pcp.setVisible(false);
                this.plp.setVisible(false);
                this.me.setVisible(true);
                break;
            case _btnAceptarOfertaA:

                break;
            case _btnCancelarOfertaA:
                this.oa.setVisible(false);
                this.me.setVisible(true);
                break;
            case _btnRegistrarCliente:
                this.r.setVisible(false);
                this.rc.setVisible(true);
                this.rc.setModal(true);
                break;
            case _btnRegistrarEmpleado:
                this.r.setVisible(false);
                this.re.setVisible(true);
                this.re.setModal(true);
                break;
            case _buscarFotoCli:
                dlg = new JFileChooser(); //Objeto dialogo JFileChooser 
                option = dlg.showOpenDialog(this.rc); //Abre la ventana de dialogo
                if (option == JFileChooser.APPROVE_OPTION) { //Si click en abrir 
                    ruta = dlg.getSelectedFile().getPath(); //Obtiene ruta y nombre del archivo seleccionado
                    //si solo quisiera el nombre, se usa getName()
                    ImageIcon imgTemp = new ImageIcon(ruta);
                    ImageIcon img = new ImageIcon(imgTemp.getImage().getScaledInstance(70, -1, Image.SCALE_DEFAULT));

                    this.rc.etiFotoCliente.setIcon(img); //Carga el archivo imagen en la etiqueta del label central
                    this.rc.etiFotoCliente.getPreferredSize();
                    this.rc.pack();
                }
                break;
            case _btnAceptarRegistrarCli:
                if (String.valueOf(this.rc.jpfContrasenia1.getPassword()).equals(String.valueOf(this.rc.jpfContrasenia2.getPassword()))) {
                    if (this.modelo.registrarCliente(this.rc.txtUserCliente.getText(),
                            String.valueOf(this.rc.jpfContrasenia1.getPassword()), this.rc.txtNombreCliente.getText(),
                            this.rc.txtDNICliente.getText(), this.rc.txtDomicilioCliente.getText(),
                            this.rc.txtProvinciaCliente.getText(), this.rc.txtCPCliente.getText(),
                            ruta)) {
                        JOptionPane.showMessageDialog(vista, "Exito: Nuevo registro agregado.");
                        this.rc.etiFotoCliente.setIcon(null);
                        this.rc.txtCPCliente.setText("");
                        this.rc.txtDNICliente.setText("");
                        this.rc.txtDomicilioCliente.setText("");
                        this.rc.txtNombreCliente.setText("");
                        this.rc.txtProvinciaCliente.setText("");
                        this.rc.txtUserCliente.setText("");
                        this.rc.jpfContrasenia1.setText("");
                        this.rc.jpfContrasenia2.setText("");
                        this.ruta = "";
                        this.rc.dispose();
                        this.r.dispose();
                    } else //ocurrio un error
                    {
                        JOptionPane.showMessageDialog(vista, "Error: Los datos son incorrectos.");
                    }
                } else //ocurrio un error
                {
                    JOptionPane.showMessageDialog(vista, "Error: La contraseña no son iguales.");
                }
                break;
            case _btnCancelarRegistrarCli:
                this.rc.etiFotoCliente.setIcon(null);
                this.rc.txtCPCliente.setText("");
                this.rc.txtDNICliente.setText("");
                this.rc.txtDomicilioCliente.setText("");
                this.rc.txtNombreCliente.setText("");
                this.rc.txtProvinciaCliente.setText("");
                this.rc.txtUserCliente.setText("");
                this.rc.jpfContrasenia1.setText("");
                this.rc.jpfContrasenia2.setText("");
                this.ruta = "";
                this.rc.dispose();
                this.r.setVisible(true);
                break;
            case _buscarFotoEmp:
                dlg = new JFileChooser(); //Objeto dialogo JFileChooser 
                option = dlg.showOpenDialog(this.re); //Abre la ventana de dialogo
                if (option == JFileChooser.APPROVE_OPTION) { //Si click en abrir 
                    ruta = dlg.getSelectedFile().getPath(); //Obtiene ruta y nombre del archivo seleccionado
                    //si solo quisiera el nombre, se usa getName()
                    ImageIcon imgTemp = new ImageIcon(ruta);
                    ImageIcon img = new ImageIcon(imgTemp.getImage().getScaledInstance(70, -1, Image.SCALE_DEFAULT));

                    this.re.etiFotoEmpleado.setIcon(img); //Carga el archivo imagen en la etiqueta del label central
                    this.re.etiFotoEmpleado.getPreferredSize();
                    this.re.pack();
                }
                break;
            case _btnAceptarRegistrarEmp:
                if (String.valueOf(this.re.jpfContrasenia1.getPassword()).equals(String.valueOf(this.re.jpfContrasenia2.getPassword()))) {
                    if (this.modelo.registrarEmpleado(this.re.txtUserEmpleado.getText(),
                            String.valueOf(this.re.jpfContrasenia1.getPassword()), this.re.txtNombreEmpleado.getText(),
                            this.re.txtDNIEmpleado.getText(), this.re.txtDomicilioEmpleado.getText(),
                            this.re.txtProvinciaEmpleado.getText(), this.re.txtCPEmpleado.getText(),
                            ruta)) {
                        JOptionPane.showMessageDialog(vista, "Exito: Nuevo registro agregado.");
                        this.re.etiFotoEmpleado.setIcon(null);
                        this.re.txtCPEmpleado.setText("");
                        this.re.txtDNIEmpleado.setText("");
                        this.re.txtDomicilioEmpleado.setText("");
                        this.re.txtNombreEmpleado.setText("");
                        this.re.txtProvinciaEmpleado.setText("");
                        this.re.txtUserEmpleado.setText("");
                        this.re.jpfContrasenia1.setText("");
                        this.re.jpfContrasenia2.setText("");
                        this.ruta = "";
                        this.re.dispose();
                        this.r.dispose();
                    } else //ocurrio un error
                    {
                        JOptionPane.showMessageDialog(vista, "Error: Los datos son incorrectos.");
                    }
                } else //ocurrio un error
                {
                    JOptionPane.showMessageDialog(vista, "Error: La contraseña no son iguales.");
                }
                break;
            case _btnCancelarRegistrarEmp:
                this.re.etiFotoEmpleado.setIcon(null);
                this.re.txtCPEmpleado.setText("");
                this.re.txtDNIEmpleado.setText("");
                this.re.txtDomicilioEmpleado.setText("");
                this.re.txtUserEmpleado.setText("");
                this.re.txtProvinciaEmpleado.setText("");
                this.re.txtUserEmpleado.setText("");
                this.re.jpfContrasenia1.setText("");
                this.re.jpfContrasenia2.setText("");
                this.ruta = "";
                this.re.dispose();
                this.r.setVisible(true);
                break;
            case _btnCancelarSeccion:
                if (this.tipoUsu == true) {
                    this.carro.dispose();
                    this.s.dispose();
                    this.me.setVisible(true);
                } else {
                    this.carro.dispose();
                    this.s.dispose();
                    this.mc.setVisible(true);
                }
                break;
            case _btnAnadirSeccion:
                String n = String.valueOf(this.s.jTablaProductos.getValueAt(this.s.jTablaProductos.getSelectedRow(), 0));
                String d = String.valueOf(this.s.jTablaProductos.getValueAt(this.s.jTablaProductos.getSelectedRow(), 1));
                String p = String.valueOf(this.s.jTablaProductos.getValueAt(this.s.jTablaProductos.getSelectedRow(), 2));
                Object nuevo[] = {tc.getRowCount() + 1, "", ""};
                tc.addRow(nuevo);

                break;
            case _btnVerCarro:
                this.carro.setVisible(true);
                break;
            case _btnComprarMenuClien:
                this.mc.setVisible(false);
                this.carro.setModal(false);
                this.s.cbSeccion.setSelectedIndex(0);
                this.s.jTablaProductos.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                this.s.setModal(false);
                this.s.setLocation((this.vista.getX() - 300), (this.vista.getY() - 100));
                this.s.setVisible(true);
                this.carro.setLocation((this.vista.getX() + 250), (this.vista.getY() - 100));
                this.carro.setVisible(true);
                break;
            case _btnRevisarMenu:

                break;
            case _btnSalirMenuClie:
                this.mc.setVisible(false);
                this.vista.txtContrasenia.setText("");
                this.vista.txtUserName.setText("");
                this.vista.setVisible(true);
                break;
            case _btnOfertaMenu:
                this.me.setVisible(false);
                this.co.setLocationRelativeTo(vista);
                this.co.setVisible(true);
                break;
            case _btnOfertaAMenu:
                this.me.setVisible(false);
                this.oa.setLocationRelativeTo(vista);
                this.oa.setVisible(true);
                break;
            case _btnPedidoMenu:
                this.me.setVisible(false);
                this.plp.setModal(false);
                this.pcp.setModal(false);
                this.plp.setLocation((this.vista.getX() - 250), (this.vista.getY() - 100));
                this.plp.setVisible(true);
                this.pcp.setLocation((this.vista.getX() + 250), (this.vista.getY() - 100));
                this.pcp.setVisible(true);
                break;
            case _btnProveedorMenu:
                this.me.setVisible(false);
                this.cp.setLocationRelativeTo(vista);
                this.cp.setVisible(true);
                break;
            case _btnNuevoProducto:
                this.pnp.setLocationRelativeTo(vista);
                this.pnp.setModal(true);
                this.pnp.setVisible(true);
                break;
            case _btnSalirMenu:
                this.me.setVisible(false);
                this.vista.txtContrasenia.setText("");
                this.vista.txtUserName.setText("");
                this.vista.setVisible(true);
                break;
            case _btnEntrar:
                if (this.modelo.loginEmpleado(this.vista.txtUserName.getText(), String.valueOf(this.vista.txtContrasenia.getPassword()))) {
                    this.idEmpleado = this.modelo.getIdEmpleado(this.vista.txtUserName.getText());
                    this.vista.dispose();
                    this.me.setLocationRelativeTo(vista);
                    this.me.setVisible(true);
                    this.tipoUsu = true;
                } else if (this.modelo.loginCliente(this.vista.txtUserName.getText(), String.valueOf(this.vista.txtContrasenia.getPassword()))) {
                    this.idCliente = this.modelo.getIdCliente(this.vista.txtUserName.getText());
                    this.vista.dispose();
                    this.mc.setModal(false);
                    this.mc.setLocationRelativeTo(vista);
                    this.mc.setVisible(true);
                    this.tipoUsu = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Error en la introduccion de datos");
                }
                break;
            case _btnRegistrar:
                this.r.setLocationRelativeTo(vista);
                this.r.setVisible(true);
                this.r.setModal(true);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (this.vista.isVisible() == true) {
                if (this.modelo.loginEmpleado(this.vista.txtUserName.getText(), String.valueOf(this.vista.txtContrasenia.getPassword()))) {
                    this.idEmpleado = this.modelo.getIdEmpleado(this.vista.txtUserName.getText());
                    this.vista.dispose();
                    this.me.setLocationRelativeTo(vista);
                    this.me.setVisible(true);
                    this.tipoUsu = true;
                } else if (this.modelo.loginCliente(this.vista.txtUserName.getText(), String.valueOf(this.vista.txtContrasenia.getPassword()))) {
                    this.idCliente = this.modelo.getIdCliente(this.vista.txtUserName.getText());
                    this.vista.dispose();
                    this.mc.setModal(false);
                    this.mc.setLocationRelativeTo(vista);
                    this.mc.setVisible(true);
                    this.tipoUsu = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Error en la introduccion de datos");
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (this.s.isVisible() == true) {
                switch (String.valueOf(this.s.cbSeccion.getSelectedItem())) {
                    case "Carniceria":
                        this.s.jTablaProductos.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Pescaderia":
                        this.s.jTablaProductos.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Charcuteria":
                        this.s.jTablaProductos.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Limpieza":
                        this.s.jTablaProductos.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Fruteria":
                        this.s.jTablaProductos.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Congelados":
                        this.s.jTablaProductos.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Panaderia":
                        this.s.jTablaProductos.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Higiene":
                        this.s.jTablaProductos.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Frio":
                        this.s.jTablaProductos.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                }
            }
        }
    }

    /**
     * Enumera TODOs los metodos que tendrá nuestro proyecto
     */
    public enum AccionProyecto {

        _btnAceptarCarro, _btnCancelarCarro, _btnEliminarCarro, _btnSalirCarro, //vista.carro
        _btnContratarProveedor, _btnCancelarProveedor, //vista.ContratarProveedor
        _btnCrearOferta, _btnCancelarOferta, //vista.CreacionOferta
        _btnSalirPCP, //vista.ProveedorCarroProductos
        _btnAceptarPLP, _btnCancelarPLP, _btnEliminarPLP, _btnCarroPLP, _btnNuevoProducto, _btnSalirPLP, //vista.ProveedorListaProductos
        _btnEnviarPNP, _btnCancelarPNP, //vista.ProveedorNuevoProductos
        _btnAceptarOfertaA, _btnCancelarOfertaA, //vista.OfertaAplicada
        _btnRegistrarCliente, _btnRegistrarEmpleado, _buscarFotoEmp, //vista.Registro
        _btnAceptarRegistrarCli, _btnCancelarRegistrarCli, _buscarFotoCli, //vista.RegistroCliente
        _btnAceptarRegistrarEmp, _btnCancelarRegistrarEmp, //vista.RegistroEmpleado
        _btnCancelarSeccion, _btnAnadirSeccion, _btnVerCarro, //vista.Seccion
        _btnOfertaMenu, _btnOfertaAMenu, _btnPedidoMenu, _btnProveedorMenu, _btnSalirMenu,//vista.menuEmpleado
        _btnComprarMenuClien, _btnRevisarMenu, _btnSalirMenuClie,//vista.menuCliente
        _btnEntrar, _btnRegistrar, _txtLogin                                 //vista.interfaz
    }

    /**
     * crea el constructor con la interfaz principal de nuestro proyecto
     *
     * @param vista
     */
    public controlador(interfaz vista, Carro carro, ContratarProveedor cp, CreacionOferta co,
            ProveedorNuevoProducto pnp, ProveedorCarroProductos pcp, OfertaAplicada oa, ProveedorListaProductos plp, Registro r,
            RegistroCliente rc, RegistroEmpleado re, Seccion s, menuCliente mc, menuEmpleado me) {
        this.vista = vista;
        this.carro = carro;
        this.cp = cp;
        this.co = co;
        this.pnp = pnp;
        this.oa = oa;
        this.plp = plp;
        this.pcp = pcp;
        this.r = r;
        this.rc = rc;
        this.re = re;
        this.s = s;
        this.me = me;
        this.mc = mc;
    }

    /**
     * Metodo que hará que nuestra interfáz se abra. También irán las
     * modificaciones de nuestrá interfaz a la hora de abrirse esta.
     */
    public void iniciar() {
        // Skin tipo jTattoo
        SwingUtilities.updateComponentTreeUI(vista);
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);

        this.vista.txtUserName.addKeyListener(this);

        this.vista.txtContrasenia.addKeyListener(this);

        this.vista.btnRegistrar.setActionCommand("_btnRegistrar");
        this.vista.btnRegistrar.addActionListener(this);

        this.vista.btnEntrar.setActionCommand("_btnEntrar");
        this.vista.btnEntrar.addActionListener(this);

        this.r.btnRegistrarEmpleado.setActionCommand("_btnRegistrarEmpleado");
        this.r.btnRegistrarEmpleado.addActionListener(this);

        this.r.btnRegistrarCliente.setActionCommand("_btnRegistrarCliente");
        this.r.btnRegistrarCliente.addActionListener(this);

        this.rc.btnAceptarRegistrarCli.setActionCommand("_btnAceptarRegistrarCli");
        this.rc.btnAceptarRegistrarCli.addActionListener(this);

        this.rc.btnCancelarRegistroCli.setActionCommand("_btnCancelarRegistrarCli");
        this.rc.btnCancelarRegistroCli.addActionListener(this);

        this.rc.btnBuscarFotoCli.setActionCommand("_buscarFotoCli");
        this.rc.btnBuscarFotoCli.addActionListener(this);

        this.re.btnAceptarRegistrarEmp.setActionCommand("_btnAceptarRegistrarEmp");
        this.re.btnAceptarRegistrarEmp.addActionListener(this);

        this.re.btnCancelarRegistroEmp.setActionCommand("_btnCancelarRegistrarEmp");
        this.re.btnCancelarRegistroEmp.addActionListener(this);

        this.re.btnBuscarFotoEmp.setActionCommand("_buscarFotoEmp");
        this.re.btnBuscarFotoEmp.addActionListener(this);

        this.s.btnAnadirSeccion.setActionCommand("_btnAnadirSeccion");
        this.s.btnAnadirSeccion.addActionListener(this);

        this.s.btnCancelarSeccion.setActionCommand("_btnCancelarSeccion");
        this.s.btnCancelarSeccion.addActionListener(this);

        this.s.btnVerCarro.setActionCommand("_btnVerCarro");
        this.s.btnVerCarro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Archivos/carro.png")));
        this.s.btnVerCarro.addActionListener(this);

        this.s.btnAnadirSeccion.setActionCommand("_btnAnadirSeccion");
        this.s.btnAnadirSeccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Archivos/anadirCarro.png")));
        this.s.btnAnadirSeccion.addActionListener(this);

        this.s.btnCancelarSeccion.setActionCommand("_btnCancelarSeccion");
        this.s.btnCancelarSeccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Archivos/Xrojo.png")));
        this.s.btnCancelarSeccion.addActionListener(this);

        this.s.cbSeccion.setModel(this.mcb);
        this.s.cbSeccion.addItemListener(this);

        this.carro.btnAceptarCarro.setActionCommand("_btnAceptarCarro");
        this.carro.btnAceptarCarro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Archivos/Verificarcompra.png")));
        this.carro.btnAceptarCarro.addActionListener(this);

        this.carro.btnCancelarCarro.setActionCommand("_btnCancelarCarro");
        this.carro.btnCancelarCarro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Archivos/cancelarCompra.png")));
        this.carro.btnCancelarCarro.addActionListener(this);

        this.carro.btnEliminarCarro.setActionCommand("_btnEliminarCarro");
        this.carro.btnEliminarCarro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Archivos/quitarCarro.png")));
        this.carro.btnEliminarCarro.addActionListener(this);

        this.carro.btnSalirCarro.setActionCommand("_btnSalirCarro");
        this.carro.btnSalirCarro.addActionListener(this);

        this.carro.jTablaCarro.setModel(this.tc);

        this.me.btnOfertaMenu.setActionCommand("_btnOfertaMenu");
        this.me.btnOfertaMenu.addActionListener(this);

        this.me.btnOfertaAMenu.setActionCommand("_btnOfertaAMenu");
        this.me.btnOfertaAMenu.addActionListener(this);

        this.me.btnPedidoMenu.setActionCommand("_btnPedidoMenu");
        this.me.btnPedidoMenu.addActionListener(this);

        this.me.btnProveedorMenu.setActionCommand("_btnProveedorMenu");
        this.me.btnProveedorMenu.addActionListener(this);

        this.me.btnSalirMenu.setActionCommand("_btnSalirMenu");
        this.me.btnSalirMenu.addActionListener(this);

        this.mc.btnComprarMenuClien.setActionCommand("_btnComprarMenuClien");
        this.mc.btnComprarMenuClien.addActionListener(this);

        this.mc.btnRevisarMenu.setActionCommand("_btnRevisarMenu");
        this.mc.btnRevisarMenu.addActionListener(this);

        this.mc.btnSalirMenuClie.setActionCommand("_btnSalirMenuClie");
        this.mc.btnSalirMenuClie.addActionListener(this);

        this.cp.btnContratarProveedor.setActionCommand("_btnContratarProveedor");
        this.cp.btnContratarProveedor.addActionListener(this);

        this.cp.btnCancelarProveedor.setActionCommand("_btnCancelarProveedor");
        this.cp.btnCancelarProveedor.addActionListener(this);

        this.co.btnCrearOferta.setActionCommand("_btnCrearOferta");
        this.co.btnCrearOferta.addActionListener(this);

        this.co.btnCancelarOferta.setActionCommand("_btnCancelarOferta");
        this.co.btnCancelarOferta.addActionListener(this);

        this.oa.btnAceptarOfertaA.setActionCommand("_btnAceptarOfertaA");
        this.oa.btnAceptarOfertaA.addActionListener(this);

        this.oa.btnCancelarOfertaA.setActionCommand("_btnCancelarOfertaA");
        this.oa.btnCancelarOfertaA.addActionListener(this);

        this.pcp.btnAceptarPCP.setActionCommand("_btnAceptarPCP");
        this.pcp.btnAceptarPCP.addActionListener(this);

        this.pcp.btnCancelarPCP.setActionCommand("_btnCancelarPCP");
        this.pcp.btnCancelarPCP.addActionListener(this);

        this.pcp.btnEliminarPCP.setActionCommand("_btnEliminarPCP");
        this.pcp.btnEliminarPCP.addActionListener(this);

        this.pcp.btnSalirPCP.setActionCommand("_btnSalirPCP");
        this.pcp.btnSalirPCP.addActionListener(this);

        this.plp.btnAceptarPLP.setActionCommand("_btnAceptarPLP");
        this.plp.btnAceptarPLP.addActionListener(this);

        this.plp.btnCancelarPLP.setActionCommand("_btnCancelarPLP");
        this.plp.btnCancelarPLP.addActionListener(this);

        this.plp.btnCarroPLP.setActionCommand("_btnCarroPLP");
        this.plp.btnCarroPLP.addActionListener(this);

        this.plp.btnSalirPLP.setActionCommand("_btnSalirPLP");
        this.plp.btnSalirPLP.addActionListener(this);

        this.plp.btnNuevoProducto.setActionCommand("_btnNuevoProducto");
        this.plp.btnNuevoProducto.addActionListener(this);

        this.plp.CBProveedorPLP.setModel(this.modelo.cbProveedor());

        this.pnp.btnEnviarPNP.setActionCommand("_btnEnviar");
        this.pnp.btnEnviarPNP.addActionListener(this);

        this.pnp.btnCancelarPNP.setActionCommand("_btnCancelarPNP");
        this.pnp.btnCancelarPNP.addActionListener(this);

        this.pnp.CBSeccionEnvio.setModel(this.mcb);

    }
}
