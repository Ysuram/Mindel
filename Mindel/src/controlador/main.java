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
        new controlador(new interfaz(), new Carro(new interfaz(), true),
                new ContratarProveedor(new interfaz(), true), new CreacionOferta(new interfaz(), true),
                new EnvioProveedorTienda(new interfaz(), true), new OfertaAplicada(new interfaz(), true),
                new ProveedorProductos(new interfaz(), true), new Registro(new interfaz(), true),
                new RegistroCliente(new interfaz(), true), new RegistroEmpleado(new interfaz(), true),
                new Seccion(new interfaz(), true), new menu(new interfaz(), true)).iniciar();
    }
}
