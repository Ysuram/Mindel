package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import modelo.modelo;
import vista.interfaz;

public class controlador implements ActionListener, MouseListener {

    /**
     * instancia nuestra (s) interfaz 
     */
    interfaz vista;
    /**
     * instancia a nuestro(s) modelo(s)
     */
    modelo modelo = new modelo();

    public enum AccionMVC
    {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

    switch ( AccionProyecto.valueOf( e.getActionCommand() ) )
        {
            case btnAceptarCarro:
                 
                break;
            case btnCancelarCarro:
                 
                break;
            case btnEliminarCarro:
                 
                break;
            case btnContratarProveedor:
                 
                break;
            case btnCancelarProveedor:
                 
                break;
            case btnCrearOferta:
                 
                break;
            case btnCancelarOferta:
                 
                break;
            case btnEnviar:
                 
                break;
            case btnCancelarEnvio:
                 
                break;
            case btnAceptarOfertaA:
                 
                break;
            case btnCancelarOfertaA:
                 
                break;
            case btnRegistrarCliente:
                 
                break;
            case btnRegistrarEmpleado:
                 
                break;
            case btnAceptarRegistrarCli:
                 
                break;
            case btnCancelarRegistrarCli:
                 
                break;
            case btnAceptarRegistrarEmp:
                 
                break;
            case btnCancelarRegistrarEmp:
                 
                break;
            case btnCancelarSeccion:
                 
                break;
            case btnAnadirSeccion:
                 
                break;
            case btnEntrar:
                 
                break;
            case btnRegistrar:
                 
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
        btnAceptarCarro, btnCancelarCarro, btnEliminarCarro,    //vista.carro
        btnContratarProveedor, btnCancelarProveedor,            //vista.ContratarProveedor
        btnCrearOferta, btnCancelarOferta,                      //vista.CreacionOferta
        btnEnviar, btnCancelarEnvio,                            //vista.EnvioProveedorTienda
        btnAceptarOfertaA, btnCancelarOfertaA,                  //vista.OfertaAplicada
        btnRegistrarCliente, btnRegistrarEmpleado,              //vista.Registro
        btnAceptarRegistrarCli, btnCancelarRegistrarCli,        //vista.RegistroCliente
        btnAceptarRegistrarEmp, btnCancelarRegistrarEmp,        //vista.RegistroEmpleado
        btnCancelarSeccion, btnAnadirSeccion,                   //vista.Seccion
        btnEntrar, btnRegistrar                                 //vista.interfaz
    }

    /**
     * crea el constructor con la interfaz principal de nuestro proyecto
     *
     * @param vista
     */
    public controlador(interfaz vista) {
        this.vista = vista;
    }

    /**
     * Metodo que hará que nuestra interfáz se abra. También irán las
     * modificaciones de nuestrá interfaz a la hora de abrirse esta.
     */
    public void iniciar() {
        // Skin tipo jTattoo
        SwingUtilities.updateComponentTreeUI(vista);
        this.vista.setVisible(true);

        
    }
}
