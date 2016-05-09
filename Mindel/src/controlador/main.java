package controlador;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import vista.Carro;
import vista.ContratarProveedor;
import vista.CreacionOferta;
import vista.EnvioProveedorTienda;
import vista.OfertaAplicada;
import vista.ProveedorProductos;
import vista.Registro;
import vista.RegistroCliente;
import vista.RegistroEmpleado;
import vista.Seccion;
import vista.interfaz;
import vista.menu;

public class main {

   

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            //ejecuta el controlador y este la vista
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        new controlador(new interfaz(), new Carro(), ContratarProveedor cp, CreacionOferta co, 
            EnvioProveedorTienda ept, OfertaAplicada oa, ProveedorProductos pp, Registro r, 
            RegistroCliente rc, RegistroEmpleado re, Seccion s, menu m).iniciar();
    }
}
