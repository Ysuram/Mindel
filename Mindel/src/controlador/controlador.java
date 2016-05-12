package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import modelo.modelo;
import vista.Carro;
import vista.ContratarProveedor;
import vista.CreacionOferta;
import vista.EnvioProveedorTienda;
import vista.OfertaAplicada;
import vista.ProveedorProductos;
import vista.interfaz;
import vista.Registro;
import vista.RegistroCliente;
import vista.RegistroEmpleado;
import vista.Seccion;
import vista.menu;

public class controlador implements ActionListener, MouseListener {

    /**
     * instancia nuestra (s) interfaz
     */
    interfaz vista;
    Carro carro;
    ContratarProveedor cp;
    CreacionOferta co;
    EnvioProveedorTienda ept;
    OfertaAplicada oa;
    ProveedorProductos pp;
    Registro r;
    RegistroCliente rc;
    RegistroEmpleado re;
    Seccion s;
    menu m;
    JFileChooser dlg;
    int option;
    String ruta;
    /**
     * instancia a nuestro(s) modelo(s)
     */
    modelo modelo = new modelo();

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
            case _btnContratarProveedor:

                break;
            case _btnCancelarProveedor:

                break;
            case _btnCrearOferta:

                break;
            case _btnCancelarOferta:

                break;
            case _btnEnviar:

                break;
            case _btnCancelarEnvio:

                break;
            case _btnAceptarOfertaA:

                break;
            case _btnCancelarOfertaA:

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
                
                break;
            case _btnAnadirSeccion:
                
                break;
            case _btnVerCarro:
                
                break;
                
            case _cbSeccion:
                
                break;
            case _btnEntrar:
                if (this.modelo.loginEmpleado(this.vista.txtUserName.getText(), String.valueOf(this.vista.txtContrasenia.getPassword()))) {
                    this.vista.dispose();
                    this.m.setVisible(true);
                } else if (this.modelo.loginCliente(this.vista.txtUserName.getText(), String.valueOf(this.vista.txtContrasenia.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Correcto");
                    this.vista.dispose();
                    this.s.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Error en la introduccion de datos");
                }
                break;
            case _btnRegistrar:
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

    /**
     * Enumera TODOs los metodos que tendrá nuestro proyecto
     */
    public enum AccionProyecto {

        _btnAceptarCarro, _btnCancelarCarro, _btnEliminarCarro, //vista.carro
        _btnContratarProveedor, _btnCancelarProveedor, //vista.ContratarProveedor
        _btnCrearOferta, _btnCancelarOferta, //vista.CreacionOferta
        _btnEnviar, _btnCancelarEnvio, //vista.EnvioProveedorTienda
        _btnAceptarOfertaA, _btnCancelarOfertaA, //vista.OfertaAplicada
        _btnRegistrarCliente, _btnRegistrarEmpleado, _buscarFotoEmp, //vista.Registro
        _btnAceptarRegistrarCli, _btnCancelarRegistrarCli, _buscarFotoCli, //vista.RegistroCliente
        _btnAceptarRegistrarEmp, _btnCancelarRegistrarEmp, //vista.RegistroEmpleado
        _btnCancelarSeccion, _btnAnadirSeccion, _btnVerCarro, _cbSeccion, //vista.Seccion
        _btnEntrar, _btnRegistrar                                 //vista.interfaz
    }

    /**
     * crea el constructor con la interfaz principal de nuestro proyecto
     *
     * @param vista
     */
    public controlador(interfaz vista, Carro carro, ContratarProveedor cp, CreacionOferta co,
            EnvioProveedorTienda ept, OfertaAplicada oa, ProveedorProductos pp, Registro r,
            RegistroCliente rc, RegistroEmpleado re, Seccion s, menu m) {
        this.vista = vista;
        this.carro = carro;
        this.cp = cp;
        this.co = co;
        this.ept = ept;
        this.oa = oa;
        this.pp = pp;
        this.r = r;
        this.rc = rc;
        this.re = re;
        this.s = s;
        this.m = m;
    }

    /**
     * Metodo que hará que nuestra interfáz se abra. También irán las
     * modificaciones de nuestrá interfaz a la hora de abrirse esta.
     */
    public void iniciar() {
        // Skin tipo jTattoo
        SwingUtilities.updateComponentTreeUI(vista);
        this.vista.setVisible(true);

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
        
        this.s.cbSeccion.setActionCommand("_cbSeccion");
        this.s.cbSeccion.addActionListener(this);
        
    }
}
