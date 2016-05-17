package controlador;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import vista.Carro;
import vista.ContratarProveedor;
import vista.CreacionOferta;
import vista.ProveedorNuevoProducto;
import vista.OfertaAplicada;
import vista.ProveedorCarroProductos;
import vista.ProveedorListaProductos;
import vista.Registro;
import vista.RegistroCliente;
import vista.RegistroEmpleado;
import vista.Seccion;
import vista.interfaz;
import vista.menuCliente;
import vista.menuEmpleado;

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
                new ProveedorNuevoProducto(new interfaz(), true), new ProveedorCarroProductos(new interfaz(), true),
                new OfertaAplicada(new interfaz(), true),
                new ProveedorListaProductos(new interfaz(), true), new Registro(new interfaz(), true),
                new RegistroCliente(new interfaz(), true), new RegistroEmpleado(new interfaz(), true),
                new Seccion(new interfaz(), true), new menuCliente(new interfaz(), true),
                new menuEmpleado(new interfaz(), true)).iniciar();
    }
}
