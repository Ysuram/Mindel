package controlador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.ComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import modelo.miComboBox;
import modelo.miComboBoxEstado;
import modelo.modelo;
import vista.ProveedorCarroProductos;
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
import vista.Carro;
import vista.RevisarCompras;
import vista.RevisarComprasEmpleado;
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
    RevisarCompras rcom;
    RevisarComprasEmpleado rcome;
    JFileChooser dlg;
    DefaultTableModel tc;
    ImageIcon iico;
    Date fecha;
    Calendar fe;
    Icon ico;
    Graphics g;
    int option, cont = 0, cant = 1, idCliente, idEmpleado;
    String ruta;
    Object lc[][];
    boolean tipoUsu;
    /**
     * instancia a nuestro(s) modelo(s)
     */
    modelo modelo = new modelo();
    miComboBox mcb = new miComboBox();
    miComboBoxEstado mcbe = new miComboBoxEstado();

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (AccionProyecto.valueOf(e.getActionCommand())) {
            case _btnAceptarCarro:
                fe = Calendar.getInstance();
                fecha = new Date(fe.get(Calendar.YEAR) - 1900, fe.get(Calendar.MONTH), fe.get(Calendar.DAY_OF_MONTH));
                if (this.carro.jTablaCarroCarro.getRowCount() > 0) {
                    for (int i = 0; i < this.carro.jTablaCarroCarro.getRowCount(); i++) {
                        this.modelo.crearPedido(this.carro.jTablaCarroCarro.getValueAt(i, 0).toString(),
                                this.idCliente,
                                Integer.parseInt(this.carro.jTablaCarroCarro.getValueAt(i, 3).toString()),
                                fecha,
                                Double.parseDouble(this.carro.jTablaCarroCarro.getValueAt(i, 2).toString()),
                                "Espera");
                    }
                    tc = (DefaultTableModel) this.carro.jTablaCarroCarro.getModel();
                    for (int i = 0; i < this.carro.jTablaCarroCarro.getRowCount(); i++) {
                        tc.removeRow(i);
                        i--;
                    }
                    JOptionPane.showMessageDialog(null, "Pedido realizado con exito");
                    this.carro.setVisible(false);
                    this.s.setVisible(false);
                    this.mc.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Error: no ha hecho ninguna compra");
                }
                break;
            case _btnCancelarCarro:
                tc = (DefaultTableModel) this.carro.jTablaCarroCarro.getModel();
                for (int i = 0; i < this.carro.jTablaCarroCarro.getRowCount(); i++) {
                    tc.removeRow(i);
                    i--;
                }
                break;
            case _btnEliminarCarro:
                if (this.carro.jTablaCarroCarro.getSelectedRow() != -1) {
                    tc = (DefaultTableModel) this.carro.jTablaCarroCarro.getModel();
                    tc.removeRow(this.carro.jTablaCarroCarro.getSelectedRow());
                }
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
                    this.oa.CBOfertaOfertaA.setModel(this.modelo.cbOferta());
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
            case _btnAceptarPCP:
                fe = Calendar.getInstance();
                fecha = new Date(fe.get(Calendar.YEAR) - 1900, fe.get(Calendar.MONTH), fe.get(Calendar.DAY_OF_MONTH));
                if (this.pcp.jTablaCarroPCP.getRowCount() > 0) {
                    for (int i = 0; i < this.pcp.jTablaCarroPCP.getRowCount(); i++) {
                        if (!this.modelo.verificarProducto(this.pcp.jTablaCarroPCP.getValueAt(i, 0).toString())) {
                            this.modelo.crearProducto(this.pcp.jTablaCarroPCP.getValueAt(i, 0).toString(),
                                    this.pcp.jTablaCarroPCP.getValueAt(i, 1).toString(),
                                    this.pcp.jTablaCarroPCP.getValueAt(i, 2).toString(),
                                    Double.parseDouble(this.pcp.jTablaCarroPCP.getValueAt(i, 4).toString()));
                        }
                        this.modelo.crearEnvio(this.pcp.jTablaCarroPCP.getValueAt(i, 0).toString(),
                                this.pcp.jTablaCarroPCP.getValueAt(i, 3).toString(),
                                Integer.parseInt(this.pcp.jTablaCarroPCP.getValueAt(i, 4).toString()),
                                Double.parseDouble(this.pcp.jTablaCarroPCP.getValueAt(i, 5).toString()),
                                fecha);
                    }
                    JOptionPane.showMessageDialog(null, "Pedido realizado con exito");
                    tc = (DefaultTableModel) this.pcp.jTablaCarroPCP.getModel();
                    for (int i = 0; i < this.pcp.jTablaCarroPCP.getRowCount(); i++) {
                        tc.removeRow(i);
                        i--;
                    }
                    this.pcp.setVisible(false);
                    this.plp.setVisible(false);
                    this.me.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Error, no ha realizado ningún pedido");
                }

                break;
            case _btnEliminarPCP:
                if (this.pcp.jTablaCarroPCP.getSelectedRow() != -1) {
                    tc = (DefaultTableModel) this.pcp.jTablaCarroPCP.getModel();
                    tc.removeRow(this.pcp.jTablaCarroPCP.getSelectedRow());
                }
                break;
            case _btnSalirPCP:
                this.pcp.setVisible(false);
                break;
            case _btnEnviarPNP:
                if (!this.pnp.txtNombrePNP.getText().equals("")
                        && !this.pnp.txtADescPNP.getText().equals("")
                        && Double.parseDouble(this.pnp.SpinPrecioPNP.getValue().toString()) != 0
                        && Integer.parseInt(this.pnp.SpinCantidadPNP.getValue().toString()) != 0) {
                    tc = (DefaultTableModel) this.pcp.jTablaCarroPCP.getModel();
                    double ca = Integer.parseInt(this.pnp.SpinCantidadPNP.getValue().toString())
                            * Double.parseDouble(this.pnp.SpinPrecioPNP.getValue().toString());
                    int i = tc.getRowCount();
                    Object nuevo[] = {this.pnp.txtNombrePNP.getText(), this.pnp.txtADescPNP.getText(),
                        this.pnp.CBSeccionEnvio.getSelectedItem().toString(),
                        this.plp.CBProveedorPLP.getSelectedItem().toString(),
                        this.pnp.SpinCantidadPNP.getValue().toString(), String.valueOf(ca)};
                    tc.addRow(nuevo);
                    this.pnp.txtNombrePNP.setText("");
                    this.pnp.txtADescPNP.setText("");
                    this.pnp.SpinCantidadPNP.setValue(1);
                    this.pnp.SpinPrecioPNP.setValue(1);
                    this.pnp.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Error, campos vacios");
                }
                break;
            case _btnCancelarPNP:
                this.pnp.txtNombrePNP.setText("");
                this.pnp.txtADescPNP.setText("");
                this.pnp.SpinCantidadPNP.setValue(0);
                this.pnp.SpinPrecioPNP.setValue(0);
                this.pnp.setVisible(false);
                break;
            case _btnAceptarPLP:
                if (this.plp.jTablaPLP.getSelectedRow() != -1) {
                    int c = Integer.parseInt(this.plp.SpinCantidad.getValue().toString());
                    double ca = Double.parseDouble(this.plp.SpinCantidad.getValue().toString()) * Double.parseDouble(
                            this.plp.jTablaPLP.getValueAt(this.plp.jTablaPLP.getSelectedRow(), 2).toString());
                    boolean res = true;
                    Object nuevo[] = {
                        this.plp.jTablaPLP.getValueAt(this.plp.jTablaPLP.getSelectedRow(), 0).toString(),
                        this.plp.jTablaPLP.getValueAt(this.plp.jTablaPLP.getSelectedRow(), 1).toString(),
                        this.modelo.getSeccion(this.plp.jTablaPLP.getValueAt(this.plp.jTablaPLP.getSelectedRow(), 0).toString()),
                        this.plp.CBProveedorPLP.getSelectedItem().toString(),
                        this.plp.SpinCantidad.getValue(),
                        ca
                        };
                    tc = (DefaultTableModel) this.pcp.jTablaCarroPCP.getModel();
                    if (this.pcp.jTablaCarroPCP.getRowCount() > 0) {
                        int cant = this.pcp.jTablaCarroPCP.getRowCount()+1;
                        for (int i = 0; i < this.pcp.jTablaCarroPCP.getRowCount(); i++) {
                            JOptionPane.showMessageDialog(null, this.pcp.jTablaCarroPCP.getValueAt(i, 0).toString());
                            if (this.pcp.jTablaCarroPCP.getValueAt(i, 0).equals(
                                    this.plp.jTablaPLP.getValueAt(
                                            this.plp.jTablaPLP.getSelectedRow(), 0).toString())){
                                c += Integer.parseInt(this.pcp.jTablaCarroPCP.getValueAt(i, 4).toString());
                                
                                ca = Double.parseDouble(this.pcp.jTablaCarroPCP.getValueAt(
                                    i, 5).toString())
                                    * Double.parseDouble(
                                            this.pcp.jTablaCarroPCP.getValueAt(i, 4).toString());
                                this.pcp.jTablaCarroPCP.setValueAt(c, i, 4);
                                this.pcp.jTablaCarroPCP.setValueAt(ca, i, 5);
                                res = false;
                            }
                        }
                    }
                    if (res) {
                        tc.addRow(nuevo);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error, selecciona una fila");
                }
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
                this.modelo.aplicarOferta(this.oa.CBOfertaOfertaA.getSelectedItem().toString(),
                        this.oa.CBProductoOfertaA.getSelectedItem().toString());
                JOptionPane.showMessageDialog(null, "Oferta aplicada");
                this.oa.CBOfertaOfertaA.setSelectedIndex(0);
                this.oa.CBSeccionOfertaA.setSelectedIndex(0);
                this.oa.setVisible(false);
                this.me.setVisible(true);
                break;
            case _btnCancelarOfertaA:
                this.oa.setVisible(false);
                this.me.setVisible(true);
                break;
            case _btnRegistrarCliente:
                this.r.setVisible(false);
                this.rc.setModal(false);
                this.rc.setLocationRelativeTo(vista);
                this.rc.setVisible(true);
                this.rc.setModal(true);
                break;
            case _btnRegistrarEmpleado:
                this.r.setVisible(false);
                this.re.setModal(false);
                this.re.setLocationRelativeTo(vista);
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
                tc = (DefaultTableModel) this.carro.jTablaCarroCarro.getModel();
                for (int i = 0; i < this.carro.jTablaCarroCarro.getRowCount(); i++) {
                    tc.removeRow(i);
                    i--;
                }
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
                if (this.s.jTablaProductosSeccion.getSelectedRow() != -1) {
                    int c = (int) this.s.SpinCantidad.getValue();
                    double ca = Double.parseDouble(this.s.SpinCantidad.getValue().toString()) * Double.parseDouble(
                            this.s.jTablaProductosSeccion.getValueAt(this.s.jTablaProductosSeccion.getSelectedRow(), 2).toString());
                    boolean res = true;
                    Object nuevo[] = {
                        this.s.jTablaProductosSeccion.getValueAt(this.s.jTablaProductosSeccion.getSelectedRow(), 0).toString(),
                        this.s.jTablaProductosSeccion.getValueAt(this.s.jTablaProductosSeccion.getSelectedRow(), 1).toString(),
                        ca,
                        this.s.SpinCantidad.getValue()};
                    tc = (DefaultTableModel) this.carro.jTablaCarroCarro.getModel();
                    for (int i = 0; i < this.carro.jTablaCarroCarro.getRowCount(); i++) {
                        if (this.carro.jTablaCarroCarro.getValueAt(i, 0).equals(
                                this.s.jTablaProductosSeccion.getValueAt(
                                        this.s.jTablaProductosSeccion.getSelectedRow(), 0).toString())) {
                            c += (int) this.carro.jTablaCarroCarro.getValueAt(i, 3);
                            ca = Double.parseDouble(this.carro.jTablaCarroCarro.getValueAt(
                                    this.s.jTablaProductosSeccion.getSelectedRow(), 3).toString())
                                    * Double.parseDouble(
                                            this.s.jTablaProductosSeccion.getValueAt(this.s.jTablaProductosSeccion.getSelectedRow(), 2).toString());
                            this.carro.jTablaCarroCarro.setValueAt(c, i, 3);
                            this.carro.jTablaCarroCarro.setValueAt(ca, i, 2);
                            res = false;
                        }
                    }
                    if (res) {
                        tc.addRow(nuevo);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error, selecciona una fila");
                }
                break;
            case _btnVerCarro:
                this.carro.setVisible(true);
                break;
            case _btnComprarMenuClien:
                this.mc.setVisible(false);
                this.carro.setModal(false);
                this.s.cbSeccion.setSelectedIndex(0);
                this.s.setModal(false);
                this.s.setLocation((this.vista.getX() - 300), (this.vista.getY() - 100));
                this.s.setVisible(true);
                this.carro.setLocation((this.vista.getX() + 250), (this.vista.getY() - 100));
                this.carro.setVisible(true);
                break;
            case _btnRevisarMenu:
                this.mc.setVisible(false);
                this.rcom.CBListaRevisar.setModel(this.modelo.cbCompra(this.idCliente));
                this.rcom.CBListaRevisar.setSelectedIndex(0);
                this.rcom.jTablaRevisar.setModel(this.modelo.getTablaRevisarCompra(this.rcom.CBListaRevisar.getSelectedItem().toString(),
                        this.idCliente));
                this.rcom.setLocationRelativeTo(vista);
                this.rcom.setVisible(true);
                break;
            case _btnRevisarMenuEmp:
                this.mc.setVisible(false);
                this.rcome.CBListaRevisarEmp.setModel(this.modelo.cbCompra());
                this.rcome.CBListaRevisarEmp.setSelectedIndex(0);
                this.rcome.jTablaRevisarEmp.setModel(this.modelo.getTablaRevisarCompra(this.rcome.CBListaRevisarEmp.getSelectedItem().toString()));

                this.rcome.setLocationRelativeTo(vista);
                this.rcome.setVisible(true);
                break;
            case _btnSalirRevisarEmp:
                this.rcome.setVisible(false);
                this.me.setVisible(true);
                break;
            case _Cambiar:
                if (this.rcome.jTablaRevisarEmp.getSelectedRow() != -1) {
                    for (int i = 1; i < this.rcome.jTablaRevisarEmp.getRowCount(); i++) {
                        this.modelo.actualizarEstado(this.rcome.cbEstado.getSelectedItem().toString(), i);
                    }

                }
                break;
            case _btnSalirRevisar:
                this.rcom.setVisible(false);
                this.mc.setVisible(true);
                break;
            case _btnSalirMenuClie:
                tc = (DefaultTableModel) this.carro.jTablaCarroCarro.getModel();
                for (int i = 0; i < this.carro.jTablaCarroCarro.getRowCount(); i++) {
                    tc.removeRow(i);
                    i--;
                }
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
                this.plp.CBProveedorPLP.setSelectedIndex(0);
                this.plp.jTablaPLP.setModel(this.modelo.getTablaProvProd(this.plp.CBProveedorPLP.getSelectedIndex() + 1));
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
                this.pnp.SpinCantidadPNP.setValue(1);
                this.pnp.SpinPrecioPNP.setValue(1);
                this.pnp.setLocationRelativeTo(vista);
                this.pnp.setModal(true);
                this.pnp.setVisible(true);
                break;
            case _btnSalirMenu:
                tc = (DefaultTableModel) this.pcp.jTablaCarroPCP.getModel();
                for (int i = 0; i < this.pcp.jTablaCarroPCP.getRowCount(); i++) {
                    tc.removeRow(i);
                    i--;
                }
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
    public void mouseClicked(MouseEvent e
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e
    ) {
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
    public void keyReleased(KeyEvent e
    ) {
    }

    @Override
    public void keyTyped(KeyEvent e
    ) {
    }

    @Override
    public void itemStateChanged(ItemEvent e
    ) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (this.s.isVisible() == true) {
                switch (String.valueOf(this.s.cbSeccion.getSelectedItem())) {
                    case "Carniceria":
                        this.s.jTablaProductosSeccion.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Pescaderia":
                        this.s.jTablaProductosSeccion.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Charcuteria":
                        this.s.jTablaProductosSeccion.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Limpieza":
                        this.s.jTablaProductosSeccion.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Fruteria":
                        this.s.jTablaProductosSeccion.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Congelados":
                        this.s.jTablaProductosSeccion.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Panaderia":
                        this.s.jTablaProductosSeccion.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Higiene":
                        this.s.jTablaProductosSeccion.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                    case "Frio":
                        this.s.jTablaProductosSeccion.setModel(this.modelo.getTablaProducto(String.valueOf(this.s.cbSeccion.getSelectedItem())));
                        break;
                }
            } else if (this.plp.isVisible() == true) {
                this.plp.jTablaPLP.setModel(this.modelo.getTablaProvProd(this.plp.CBProveedorPLP.getSelectedIndex() + 1));
            } else if (this.rcom.isVisible()) {
                this.rcom.jTablaRevisar.setModel(this.modelo.getTablaRevisarCompra(this.rcom.CBListaRevisar.getSelectedItem().toString(),
                        this.idCliente));

            } else if (this.rcome.isVisible()) {
                this.rcome.jTablaRevisarEmp.setModel(this.modelo.getTablaRevisarCompra(this.rcome.CBListaRevisarEmp.getSelectedItem().toString()));

            } else if (this.oa.isVisible()) {
                switch (String.valueOf(this.s.cbSeccion.getSelectedItem())) {
                    case "Carniceria":
                        this.oa.CBProductoOfertaA.setModel(this.modelo.cbProducto("Carniceria"));
                        break;
                    case "Pescaderia":
                        this.oa.CBProductoOfertaA.setModel(this.modelo.cbProducto("Pescaderia"));
                        break;
                    case "Charcuteria":
                        this.oa.CBProductoOfertaA.setModel(this.modelo.cbProducto("Charcuteria"));
                        break;
                    case "Limpieza":
                        this.oa.CBProductoOfertaA.setModel(this.modelo.cbProducto("Limpieza"));
                        break;
                    case "Fruteria":
                        this.oa.CBProductoOfertaA.setModel(this.modelo.cbProducto("Fruteria"));
                        break;
                    case "Congelados":
                        this.oa.CBProductoOfertaA.setModel(this.modelo.cbProducto("Congelados"));
                        break;
                    case "Panaderia":
                        this.oa.CBProductoOfertaA.setModel(this.modelo.cbProducto("Panaderia"));
                        break;
                    case "Higiene":
                        this.oa.CBProductoOfertaA.setModel(this.modelo.cbProducto("Higiene"));
                        break;
                    case "Frio":
                        this.oa.CBProductoOfertaA.setModel(this.modelo.cbProducto("Frio"));
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
        _btnSalirPCP, _btnAceptarPCP, _btnEliminarPCP, //vista.ProveedorCarroProductos
        _btnAceptarPLP, _btnCancelarPLP, _btnEliminarPLP, _btnCarroPLP, _btnNuevoProducto, _btnSalirPLP, //vista.ProveedorListaProductos
        _btnEnviarPNP, _btnCancelarPNP, //vista.ProveedorNuevoProductos
        _btnAceptarOfertaA, _btnCancelarOfertaA, //vista.OfertaAplicada
        _btnRegistrarCliente, _btnRegistrarEmpleado, _buscarFotoEmp, //vista.Registro
        _btnAceptarRegistrarCli, _btnCancelarRegistrarCli, _buscarFotoCli, //vista.RegistroCliente
        _btnAceptarRegistrarEmp, _btnCancelarRegistrarEmp, //vista.RegistroEmpleado
        _btnCancelarSeccion, _btnAnadirSeccion, _btnVerCarro, //vista.Seccion
        _btnOfertaMenu, _btnOfertaAMenu, _btnPedidoMenu, _btnProveedorMenu, _btnSalirMenu,//vista.menuEmpleado
        _btnComprarMenuClien, _btnRevisarMenu, _btnRevisarMenuEmp, _btnSalirMenuClie,//vista.menuCliente
        _btnEntrar, _btnRegistrar, _txtLogin, //vista.interfaz
        _btnSalirRevisar, //vista.RevisarCompras
        _btnSalirRevisarEmp, _Cambiar //vista.RevisarComprasEmpleado
    }

    /**
     * crea el constructor con la interfaz principal de nuestro proyecto
     *
     * @param vista
     */
    public controlador(interfaz vista, Carro carro, ContratarProveedor cp, CreacionOferta co,
            ProveedorNuevoProducto pnp, ProveedorCarroProductos pcp, OfertaAplicada oa, ProveedorListaProductos plp, Registro r,
            RegistroCliente rc, RegistroEmpleado re, Seccion s, menuCliente mc, menuEmpleado me,
            RevisarCompras rcom, RevisarComprasEmpleado rcome) {
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
        this.rcom = rcom;
        this.rcome = rcome;
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
        this.vista.etiContrasenia.setOpaque(true);
        this.vista.etiUserName.setOpaque(true);
        this.vista.etiMINDEL.setOpaque(true);
        this.vista.etiContrasenia.setBackground(Color.white);
        this.vista.etiMINDEL.setBackground(Color.white);
        this.vista.etiUserName.setBackground(Color.white);
        iico = new ImageIcon(getClass().getResource("/Archivos/login.jpg"));
        this.vista.lblFondo.setIcon(new ImageIcon(iico.getImage().getScaledInstance(this.vista.getWidth(), this.vista.getHeight(), Image.SCALE_DEFAULT)));

        this.vista.txtUserName.addKeyListener(this);

        this.vista.txtContrasenia.addKeyListener(this);

        this.vista.btnRegistrar.setActionCommand("_btnRegistrar");
        iico = new ImageIcon(getClass().getResource("/Archivos/registro.png"));
        this.vista.btnRegistrar.setIcon(new ImageIcon(iico.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        this.vista.btnRegistrar.addActionListener(this);

        this.vista.btnEntrar.setActionCommand("_btnEntrar");
        iico = new ImageIcon(getClass().getResource("/Archivos/entrar.png"));
        this.vista.btnEntrar.setIcon(new ImageIcon(iico.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        this.vista.btnEntrar.addActionListener(this);

        this.r.btnRegistrarEmpleado.setActionCommand("_btnRegistrarEmpleado");
        iico = new ImageIcon(getClass().getResource("/Archivos/empleado.png"));
        this.r.btnRegistrarEmpleado.setIcon(new ImageIcon(iico.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        this.r.btnRegistrarEmpleado.addActionListener(this);

        this.r.btnRegistrarCliente.setActionCommand("_btnRegistrarCliente");
        iico = new ImageIcon(getClass().getResource("/Archivos/clientes.png"));
        this.r.btnRegistrarCliente.setIcon(new ImageIcon(iico.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        this.r.btnRegistrarCliente.addActionListener(this);

        this.rc.btnAceptarRegistrarCli.setActionCommand("_btnAceptarRegistrarCli");
        this.rc.btnAceptarRegistrarCli.setIcon(new ImageIcon(getClass().getResource("/Archivos/Vverde.png")));
        this.rc.btnAceptarRegistrarCli.addActionListener(this);

        this.rc.btnCancelarRegistroCli.setActionCommand("_btnCancelarRegistrarCli");
        this.rc.btnCancelarRegistroCli.setIcon(new ImageIcon(getClass().getResource("/Archivos/Xrojo.png")));
        this.rc.btnCancelarRegistroCli.addActionListener(this);

        this.rc.btnBuscarFotoCli.setActionCommand("_buscarFotoCli");
        iico = new ImageIcon(getClass().getResource("/Archivos/buscar.png"));
        this.rc.btnBuscarFotoCli.setIcon(new ImageIcon(iico.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        this.rc.btnBuscarFotoCli.addActionListener(this);

        this.re.btnAceptarRegistrarEmp.setActionCommand("_btnAceptarRegistrarEmp");
        this.re.btnAceptarRegistrarEmp.setIcon(new ImageIcon(getClass().getResource("/Archivos/Vverde.png")));
        this.re.btnAceptarRegistrarEmp.addActionListener(this);

        this.re.btnCancelarRegistroEmp.setActionCommand("_btnCancelarRegistrarEmp");
        this.re.btnCancelarRegistroEmp.setIcon(new ImageIcon(getClass().getResource("/Archivos/Xrojo.png")));
        this.re.btnCancelarRegistroEmp.addActionListener(this);

        this.re.btnBuscarFotoEmp.setActionCommand("_buscarFotoEmp");
        iico = new ImageIcon(getClass().getResource("/Archivos/buscar.png"));
        this.re.btnBuscarFotoEmp.setIcon(new ImageIcon(iico.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        this.re.btnBuscarFotoEmp.addActionListener(this);

        this.re.jcbSeccion.setModel(mcb);

        this.s.btnVerCarro.setActionCommand("_btnVerCarro");
        iico = new ImageIcon(getClass().getResource("/Archivos/verCarro.png"));
        this.s.btnVerCarro.setIcon(new ImageIcon(iico.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        this.s.btnVerCarro.addActionListener(this);

        this.s.btnAnadirSeccion.setActionCommand("_btnAnadirSeccion");
        this.s.btnAnadirSeccion.addActionListener(this);

        this.s.btnCancelarSeccion.setActionCommand("_btnCancelarSeccion");
        iico = new ImageIcon(getClass().getResource("/Archivos/salir.jpg"));
        this.s.btnCancelarSeccion.setIcon(new ImageIcon(iico.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        this.s.btnCancelarSeccion.addActionListener(this);

        this.s.cbSeccion.setModel(this.mcb);
        this.s.cbSeccion.addItemListener(this);

        this.s.SpinCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        this.s.SpinCantidad.setValue(1);

        this.s.jTablaProductosSeccion.setModel(this.modelo.getTablaProducto(this.s.cbSeccion.getSelectedItem().toString()));

        this.carro.btnAceptarCarro.setActionCommand("_btnAceptarCarro");
        iico = new ImageIcon(getClass().getResource("/Archivos/buscar.png"));
        this.carro.btnAceptarCarro.setIcon(new ImageIcon(iico.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        this.carro.btnAceptarCarro.addActionListener(this);

        this.carro.btnCancelarCarro.setActionCommand("_btnCancelarCarro");
        this.carro.btnCancelarCarro.setIcon(new ImageIcon(getClass().getResource("/Archivos/Xrojo.png")));
        this.carro.btnCancelarCarro.addActionListener(this);

        this.carro.btnEliminarCarro.setActionCommand("_btnEliminarCarro");
        iico = new ImageIcon(getClass().getResource("/Archivos/eliminarCarro.png"));
        this.carro.btnEliminarCarro.setIcon(new ImageIcon(iico.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        this.carro.btnEliminarCarro.addActionListener(this);

        this.carro.btnSalirCarro.setActionCommand("_btnSalirCarro");
        this.carro.btnSalirCarro.setIcon(new ImageIcon(getClass().getResource("/Archivos/flechaAtras.png")));
        this.carro.btnSalirCarro.addActionListener(this);

        this.carro.jTablaCarroCarro.setModel(this.modelo.tablaVaciaCarro());

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

        this.me.btnRevisarMenu.setActionCommand("_btnRevisarMenuEmp");
        this.me.btnRevisarMenu.addActionListener(this);

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

        this.oa.CBSeccionOfertaA.setModel(this.mcb);
        this.oa.CBSeccionOfertaA.addItemListener(this);

        this.oa.CBProductoOfertaA.setModel(this.modelo.cbProducto(this.oa.CBSeccionOfertaA.getSelectedItem().toString()));
        this.oa.CBProductoOfertaA.addItemListener(this);

        this.oa.CBOfertaOfertaA.setModel(this.modelo.cbOferta());
        this.oa.CBOfertaOfertaA.addItemListener(this);

        this.pcp.btnAceptarPCP.setActionCommand("_btnAceptarPCP");
        this.pcp.btnAceptarPCP.addActionListener(this);

        this.pcp.btnCancelarPCP.setActionCommand("_btnCancelarPCP");
        this.pcp.btnCancelarPCP.addActionListener(this);

        this.pcp.btnEliminarPCP.setActionCommand("_btnEliminarPCP");
        this.pcp.btnEliminarPCP.addActionListener(this);

        this.pcp.btnSalirPCP.setActionCommand("_btnSalirPCP");
        this.pcp.btnSalirPCP.addActionListener(this);

        this.pcp.jTablaCarroPCP.setModel(this.modelo.tablaVaciaProveedor());

        this.plp.btnAceptarPLP.setActionCommand("_btnAceptarPLP");
        this.plp.btnAceptarPLP.addActionListener(this);

        this.plp.btnCarroPLP.setActionCommand("_btnCarroPLP");
        this.plp.btnCarroPLP.addActionListener(this);

        this.plp.btnSalirPLP.setActionCommand("_btnSalirPLP");
        this.plp.btnSalirPLP.addActionListener(this);

        this.plp.btnNuevoProducto.setActionCommand("_btnNuevoProducto");
        this.plp.btnNuevoProducto.addActionListener(this);

        this.plp.CBProveedorPLP.setModel(this.modelo.cbProveedor());
        this.plp.CBProveedorPLP.addItemListener(this);

        this.plp.jTablaPLP.setModel(this.modelo.getTablaProvProd(this.modelo.getIdProveedor(ruta)));

        this.plp.SpinCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        this.pnp.btnEnviarPNP.setActionCommand("_btnEnviarPNP");
        this.pnp.btnEnviarPNP.addActionListener(this);

        this.pnp.btnCancelarPNP.setActionCommand("_btnCancelarPNP");
        this.pnp.btnCancelarPNP.addActionListener(this);

        this.pnp.CBSeccionEnvio.setModel(this.mcb);

        this.pnp.SpinPrecioPNP.setModel(new javax.swing.SpinnerNumberModel(1.0d, 0.0d, null, 0.5d));
        this.pnp.SpinCantidadPNP.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        this.rcom.btnSalirRevisar.setActionCommand("_btnSalirRevisar");
        this.rcom.btnSalirRevisar.addActionListener(this);

        this.rcom.CBListaRevisar.addItemListener(this);

        this.rcome.btnSalirRevisarEmp.setActionCommand("_btnSalirRevisarEmp");
        this.rcome.btnSalirRevisarEmp.addActionListener(this);

        this.rcome.Cambiar.setActionCommand("_Cambiar");
        this.rcome.Cambiar.addActionListener(this);

        this.rcome.cbEstado.setModel(this.mcbe);

        this.rcome.CBListaRevisarEmp.addItemListener(this);

    }
}
