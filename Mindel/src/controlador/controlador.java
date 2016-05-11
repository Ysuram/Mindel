package controlador;

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
                    String file = dlg.getSelectedFile().getPath(); //Obtiene ruta y nombre del archivo seleccionado
                    //si solo quisiera el nombre, se usa getName()

                    this.rc.etiFotoCliente.getPreferredSize();
                    this.rc.etiFotoCliente.setIcon(new ImageIcon(file)); //Carga el archivo imagen en la etiqueta del label central
                    this.rc.pack();
                }
                break;
            case _btnAceptarRegistrarCli:
                
                this.rc.dispose();
                this.r.dispose();
                break;
            case _btnCancelarRegistrarCli:
                this.rc.dispose();
                this.r.setVisible(true);
                break;
            case _buscarFotoEmp:
                dlg = new JFileChooser(); //Objeto dialogo JFileChooser 
                option = dlg.showOpenDialog(this.re); //Abre la ventana de dialogo
                if (option == JFileChooser.APPROVE_OPTION) { //Si click en abrir 
                    String file = dlg.getSelectedFile().getPath(); //Obtiene ruta y nombre del archivo seleccionado
                    //si solo quisiera el nombre, se usa getName()

                    this.re.etiFotoEmpleado.getPreferredSize();
                    this.re.etiFotoEmpleado.setIcon(new ImageIcon(file)); //Carga el archivo imagen en la etiqueta del label central
                    this.re.pack();
                }
                break;
            case _btnAceptarRegistrarEmp:
                
                this.re.dispose();
                this.r.dispose();
                break;
            case _btnCancelarRegistrarEmp:
                this.re.dispose();
                this.r.setVisible(true);
                break;
            case _btnCancelarSeccion:

                break;
            case _btnAnadirSeccion:

                break;
            case _btnEntrar:

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
        _btnCancelarSeccion, _btnAnadirSeccion, //vista.Seccion
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
        this.r = r;
        this.re = re;
        this.rc = rc;
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
        
        
    }
}
