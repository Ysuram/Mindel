package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
