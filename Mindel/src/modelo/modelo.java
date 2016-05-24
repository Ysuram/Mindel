package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class modelo extends database {

    public modelo() {
    }

    /**
     * Método para hallar la tabla de los productos a partir de una sección
     *
     * @param s sección del producto
     * @return modelo de la tabla de productos
     */
    public DefaultTableModel getTablaProducto(String s) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Nombre del Producto", "Descripcion", "Precio"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM Productos WHERE seccion_producto = '" + s + "'");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][3];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT nombre_producto, descripcion_producto, precio_producto FROM Productos WHERE seccion_producto = '" + s + "'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("nombre_producto");
                data[i][1] = res.getString("descripcion_producto");
                data[i][2] = res.getString("precio_producto");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }

    /**
     * Método para confirmar que el login del cliente ha sido correcto.
     *
     * @param u usuario del cliente
     * @param p password del cliente
     * @return True = correcto, False = incorrecto
     */
    public boolean loginCliente(String u, String p) {
        try {
            //realizamos la consulta sql
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Clientes");
            ResultSet res = pstm.executeQuery();
            String nombre, pass;
            while (res.next()) {
                nombre = res.getString("nombre_usuario_cliente");
                pass = res.getString("password_usuario_cliente");
                if (nombre.equals(u) && pass.equals(p)) {
                    return true;
                }
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para confirmar que el login del empleado ha sido correcto.
     *
     * @param u Usuario del empleado
     * @param p password del empleado
     * @return True = correcto, False = incorrecto
     */
    public boolean loginEmpleado(String u, String p) {
        try {
            //realizamos la consulta sql
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Empleados");
            ResultSet res = pstm.executeQuery();
            String nombre, pass;
            while (res.next()) {
                nombre = res.getString("nombre_usuario_empleado");
                pass = res.getString("password_usuario_empleado");
                if (nombre.equals(u) && pass.equals(p)) {
                    return true;
                }
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para hallar el id de un empleado en particular.
     *
     * @param n Nombre del Empleado
     * @return El id del empleado.
     */
    public int getIdEmpleado(String n) {
        try {
            //realizamos la consulta sql 
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT cod_empleado FROM Empleados WHERE nombre_usuario_empleado='" + n + "'");
            ResultSet res = pstm.executeQuery();
            int id;
            while (res.next()) {
                id = res.getInt("cod_empleado");
                return id;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Método para hallar5 el id de un cliente en particular.
     *
     * @param n Nombre del cliente
     * @return El id del cliente.
     */
    public int getIdCliente(String n) {
        try {
            //realizamos la consulta sql"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT cod_cliente FROM Clientes WHERE nombre_usuario_cliente='" + n + "'");
            ResultSet res = pstm.executeQuery();
            int id;
            while (res.next()) {
                id = res.getInt("cod_cliente");
                return id;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Método para validar el registro de un cliente.
     *
     * @param nu Nombre del Usuario del cliente
     * @param pss Contraseña del cliente
     * @param n Nombre del cliente
     * @param d DNI del cliente
     * @param dm Domicilio del cliente
     * @param p Provincia del cliente
     * @param cd Codigo Postal del cliente
     * @param f Foto del cliente
     * @return Ture = correctto, False = incorrecto
     */
    public boolean registrarCliente(String nu, String pss, String n, String d, String dm, String p, String cd, String f) {
        //realizamos la consulta sql"
        String insert = "INSERT INTO Clientes(nombre_usuario_cliente, password_usuario_cliente,"
                + "nombre_cliente, dni_cliente, domicilio_cliente, provincia_cliente, "
                + "cod_postal_cliente, foto_cliente) VALUES(?,?,?,?,?,?,?,?)";
        FileInputStream fis = null;
        PreparedStatement ps = null;
        try {
            this.getConexion().setAutoCommit(false);
            File file = new File(f);
            fis = new FileInputStream(file);
            ps = this.getConexion().prepareStatement(insert);
            ps.setString(1, nu);
            ps.setString(2, pss);
            ps.setString(3, n);
            ps.setString(4, d);
            ps.setString(5, dm);
            ps.setString(6, p);
            ps.setString(7, cd);
            ps.setBinaryStream(8, fis, (int) file.length());
            ps.executeUpdate();
            this.getConexion().commit();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
                fis.close();

            } catch (Exception ex) {
                Logger.getLogger(database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Método para validar el registro de un empleado.
     *
     * @param nu Nombre del Usuario del empleado
     * @param pss Contraseña del empleado
     * @param n Nombre del empleado
     * @param d DNI del empleado
     * @param dm Domicilio del empleado
     * @param p provincia del empleado
     * @param cd Codigo Postal del empleado
     * @param f Foto del empleado
     * @return True = correcto, False = Incorrecto
     */
    public boolean registrarEmpleado(String nu, String pss, String n, String d, String dm, String p, String cd, String f) {
        //realizamos la consulta sql"
        String insert = "INSERT INTO Empleados(nombre_usuario_empleado, password_usuario_empleado,"
                + "nombre_empleado, dni_empleado, domicilio_empleado, provincia_empleado, "
                + "cod_postal_empleado, foto_empleado) VALUES(?,?,?,?,?,?,?,?)";
        FileInputStream fis = null;
        PreparedStatement ps = null;
        try {
            this.getConexion().setAutoCommit(false);
            File file = new File(f);
            fis = new FileInputStream(file);
            ps = this.getConexion().prepareStatement(insert);
            ps.setString(1, nu);
            ps.setString(2, pss);
            ps.setString(3, n);
            ps.setString(4, d);
            ps.setString(5, dm);
            ps.setString(6, p);
            ps.setString(7, cd);
            ps.setBinaryStream(8, fis, (int) file.length());
            ps.executeUpdate();
            this.getConexion().commit();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
                fis.close();

            } catch (Exception ex) {
                Logger.getLogger(database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Método para registrar un proveedor.
     *
     * @param n Nombre del proveedor
     * @param d Dirección del proveedor
     * @param cp Codigo Postal del proveedor
     * @return Ture = correcto, False = incorrecto
     */
    public boolean registrarProveedor(String n, String d, int cp) {
        //realizamos la consulta sql"

        String insert = "INSERT INTO Proveedores(nombre_proveedor, domicilio_proveedor,"
                + "cod_postal_proveedor) VALUES(?,?,?)";
        PreparedStatement ps = null;
        try {
            this.getConexion().setAutoCommit(false);
            ps = this.getConexion().prepareStatement(insert);
            ps.setString(1, n);
            ps.setString(2, d);
            ps.setInt(3, cp);
            ps.executeUpdate();
            this.getConexion().commit();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (Exception ex) {
                Logger.getLogger(database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Método para crear una oferta.
     *
     * @param d Descripcion de la oferta
     * @param c Cantidad que modifica la oferta
     * @return True = correcto, False = incorrecto
     */
    public boolean crearOferta(String d, double c) {
        //realizamos la consulta sql"
        String insert = "INSERT INTO Ofertas(descripcion_oferta, cantidad_oferta) VALUES(?,?)";
        PreparedStatement ps = null;
        try {
            this.getConexion().setAutoCommit(false);
            ps = this.getConexion().prepareStatement(insert);
            ps.setString(1, d);
            ps.setDouble(2, c);
            ps.executeUpdate();
            this.getConexion().commit();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (Exception ex) {
                Logger.getLogger(database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Método para crear un producto.
     *
     * @param n Nombre del producto
     * @param d Descripción del producto
     * @param s Sección a la que pertenece el producto
     * @param p Precio del producto
     * @return True = correcto, False = incorrecto
     */
    public boolean crearProducto(String n, String d, String s, double p) {
        //realizamos la consulta sql
        String insert = "INSERT INTO Productos(nombre_producto, descripcion_producto, seccion_producto, precio_producto) VALUES(?,?,?,?)";
        PreparedStatement ps = null;
        try {
            this.getConexion().setAutoCommit(false);
            ps = this.getConexion().prepareStatement(insert);
            ps.setString(1, n);
            ps.setString(2, d);
            ps.setString(3, s);
            ps.setDouble(4, p);
            ps.executeUpdate();
            this.getConexion().commit();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (Exception ex) {
                Logger.getLogger(database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Método que crea una tabla vacía para el carro
     *
     * @return Modelo de la tabla vacia.
     */
    public DefaultTableModel tablaVaciaCarro() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        //realizamos la consulta sql
        String[] columNames = {"Nombre del Producto", "Descripcion", "Precio", "Cantidad"};
        Object[][] data = new String[0][4];
        tablemodel.setDataVector(data, columNames);
        return tablemodel;
    }

    /**
     * Método que crea una tabla vacía para el proveedor
     *
     * @return Modelo de la tabla vacia
     */
    public DefaultTableModel tablaVaciaProveedor() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        //realizamos la consulta sql
        String[] columNames = {"Nombre del Producto", "Descripcion", "Seccion", "Proveedor", "Cantidad", "Precio"};
        Object[][] data = new String[0][7];
        tablemodel.setDataVector(data, columNames);
        return tablemodel;
    }

    /**
     * Método que crea un Combobox con los proveedores contratados.
     *
     * @return Modelo del combobox con los proveedores
     */
    public ComboBoxModel cbProveedor() {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        String r;
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT nombre_proveedor FROM Proveedores");
            ResultSet res = pstm.executeQuery();
            m.removeAllElements();
            while (res.next()) {
                r = res.getString("nombre_proveedor");
                m.addElement(r);
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return m;
    }

    /**
     * Método para crear la tabla de productos/proveedor a partir de un id de
     * proveedor
     *
     * @param num id del proveedor
     * @return Modelo de la tabla.
     */
    public DefaultTableModel getTablaProvProd(int num) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Nombre del Producto", "Descripcion", "Precio"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT COUNT(*) as total FROM Envios WHERE proveedor_cod=" + num);
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][5];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT p.* FROM Productos p, Envios e WHERE e.producto_cod=p.cod_producto AND e.proveedor_cod=" + num);
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("nombre_producto");
                data[i][1] = res.getString("descripcion_producto");
                data[i][2] = res.getString("precio_producto");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }

    /**
     * Método que verifica que un producto ha sido correctamente creado.
     *
     * @param u Nombre del producto a verificar
     * @return True = correcto, False = incorrecto
     */
    public boolean verificarProducto(String u) {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Productos");
            ResultSet res = pstm.executeQuery();
            String nombre;
            while (res.next()) {
                nombre = res.getString("nombre_producto");
                if (nombre.equals(u)) {
                    return true;
                }
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para hallar el id del proveedor en particular.
     *
     * @param n Nombre del proveedor a buscar
     * @return Id del proveedor.
     */
    public int getIdProveedor(String n) {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT cod_proveedor FROM Proveedores WHERE nombre_proveedor='" + n + "'");
            ResultSet res = pstm.executeQuery();
            int id;
            while (res.next()) {
                id = res.getInt("cod_proveedor");
                return id;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Método que halla la sección de un producto
     *
     * @param n Nombre del producto donde se quiere hallar la seccion
     * @return Seccion del producto
     */
    public String getSeccion(String n) {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT seccion_producto FROM Productos WHERE nombre_producto='" + n + "'");
            ResultSet res = pstm.executeQuery();
            String x;
            while (res.next()) {
                x = res.getString("seccion_producto");
                return x;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    /**
     * Método para hallar la id de un producto
     *
     * @param n Nombre del producto que se quiere sacar la id
     * @return ID del producto seleccionado
     */
    public int getIdProducto(String n) {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT cod_producto FROM Productos WHERE nombre_producto='" + n + "'");
            ResultSet res = pstm.executeQuery();
            int id;
            while (res.next()) {
                id = res.getInt("cod_producto");
                return id;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Método para crear un envio de un producto a un proveedor
     *
     * @param np Nombre del producto
     * @param npr Nombre del proveedor
     * @param c Cantidad del producto a enviar
     * @param p Precio del producto a enviar
     * @param d Fecha del envio
     * @return
     */
    public boolean crearEnvio(String np, String npr, int c, double p, Date d) {
        //realizamos la consulta sql
        String insert = "INSERT INTO Envios(proveedor_cod, producto_cod, cantidad_productos, coste_envio, fecha_envio) VALUES(?,?,?,?,?)";
        PreparedStatement ps = null;
        int prod = this.getIdProducto(np);
        int pro = this.getIdProveedor(npr);
        try {
            this.getConexion().setAutoCommit(false);
            ps = this.getConexion().prepareStatement(insert);
            ps.setInt(1, pro);
            ps.setInt(2, prod);
            ps.setInt(3, c);
            ps.setDouble(4, p);
            ps.setDate(5, d);
            ps.executeUpdate();
            this.getConexion().commit();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (Exception ex) {
                Logger.getLogger(database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Método para aplicar una oferta creada a un producto creado.
     *
     * @param e Id del producto
     * @param x Nombre del producto
     * @return True = correcto, False = incorrecto
     */
    public boolean aplicarOferta(String e, String x) {
        int ido = this.getIdOferta(e);
        String insert = "UPDATE Productos SET oferta_cod = " + ido + " WHERE nombre_producto = '" + x + "'";
        PreparedStatement ps = null;
        try {
            this.getConexion().setAutoCommit(false);
            ps = this.getConexion().prepareStatement(insert);
            ps.executeUpdate();
            this.getConexion().commit();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (Exception ex) {
                Logger.getLogger(database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Método que actualiza el estado de un pedido.
     *
     * @param np Estado del producto nuevo
     * @param c Codigo del pedido
     * @param f Fecha del pedido
     * @param id id del empeleado
     * @return True = correcto, False = incorrecto
     */
    public boolean actualizarEstado(String np, int c, String f, int id) {
        //realizamos la consulta sql
        String insert = "UPDATE Pedido SET estado_pedido = '" + np + "', empleado_cod = " + id + " WHERE cod_pedido = " + c + " AND fecha_pedido = '" + f + "'";
        PreparedStatement ps = null;
        int prod = this.getIdProducto(np);
        try {
            this.getConexion().setAutoCommit(false);
            ps = this.getConexion().prepareStatement(insert);
            ps.executeUpdate();
            this.getConexion().commit();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (Exception ex) {
                Logger.getLogger(database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Método que crea un pedido de un producto a la tienda.
     *
     * @param n Id del producto
     * @param c Id del cliente
     * @param ca Cantidad del producto
     * @param f Fecha del pedido
     * @param p Precio del pedido
     * @param e Estado del pedido
     * @return True = correcto, False = incorrecto
     */
    public boolean crearPedido(String n, int c, int ca, Date f, double p, String e) {
        String insert = "INSERT INTO Pedido(producto_cod, cliente_cod, cantidad_producto, fecha_pedido, coste_pedido, estado_pedido) "
                + "VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = null;
        int pr = this.getIdProducto(n);

        try {
            this.getConexion().setAutoCommit(false);
            ps = this.getConexion().prepareStatement(insert);
            ps.setInt(1, pr);
            ps.setInt(2, c);
            ps.setInt(3, ca);
            ps.setDate(4, f);
            ps.setDouble(5, p);
            ps.setString(6, e);
            ps.executeUpdate();
            this.getConexion().commit();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(database.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (Exception ex) {
                Logger.getLogger(database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Método para hallar el nombre de un empleado.
     *
     * @param i Codigo del empleado
     * @return Nombre del empleado
     */
    public String getNombreEmpleado(int i) {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT nombre_empleado FROM Empleados WHERE cod_empleado='" + i + "'");
            ResultSet res = pstm.executeQuery();
            String n;
            while (res.next()) {
                n = res.getString("nombre_empleado");
                return n;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "No atendido";
    }

    /**
     * Método que revisa la tabla de productos esta vacia o no.
     *
     * @return True = correcto, False = Incorrecto
     */
    public boolean getRevisar() {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Pedido");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                return true;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método que revisa si un pedido ha sido entregado o no.
     *
     * @return True = correcto, False = incorrecto
     */
    public boolean getRevisarEmp() {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Pedido WHERE estado_pedido != 'Entregado'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                return true;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para hallar el id de una oferta creada.
     *
     * @param x Nombre de la oferta
     * @return Id de la oferta.
     */
    public int getIdOferta(String x) {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT cod_oferta FROM Ofertas WHERE descripcion_oferta='" + x + "'");
            ResultSet res = pstm.executeQuery();
            int n;
            while (res.next()) {
                n = res.getInt("cod_oferta");
                return n;
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Método para crear una tabla que revise la compra del cliente
     *
     * @param s Fecha del pedido.
     * @param idc Codigo del cliente
     * @return Modelo de la tabla del pedido
     */
    public DefaultTableModel getTablaRevisarCompra(String s, int idc) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Nombre del Producto", "Descripcion", "Seccion", "Cantidad", "Precio", "Estado"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM Pedido WHERE cliente_cod = " + idc + " AND fecha_pedido = '" + s + "'");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][8];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement(""
                    + "SELECT p.nombre_producto, p.descripcion_producto, p.seccion_producto, pe.cantidad_producto, pe.coste_pedido, pe.estado_pedido FROM Pedido pe, Productos p "
                    + "WHERE pe.producto_cod=p.cod_producto AND cliente_cod = '" + idc + "' AND fecha_pedido = '" + s + "'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("p.nombre_producto");
                data[i][1] = res.getString("p.descripcion_producto");
                data[i][2] = res.getString("p.seccion_producto");
                data[i][3] = res.getString("cantidad_producto");
                data[i][4] = res.getString("coste_pedido");
                data[i][5] = res.getString("estado_pedido");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }

    /**
     * Método para crear una tabla que revise la compra del cliente.
     *
     * @param s Fecha del pedido
     * @return Modelo de la tabla de la compra
     */
    public DefaultTableModel getTablaRevisarCompra(String s) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Id", "Nombre del Producto", "Descripcion", "Seccion", "Cantidad", "Precio", "Estado"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM Pedido WHERE fecha_pedido = '" + s + "' AND estado_pedido != 'Entregado'");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][7];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement(""
                    + "SELECT pe.cod_pedido, p.nombre_producto, p.descripcion_producto, p.seccion_producto, pe.cantidad_producto, pe.coste_pedido, pe.estado_pedido FROM Pedido pe, Productos p "
                    + "WHERE pe.producto_cod=p.cod_producto AND fecha_pedido = '" + s + "' AND estado_pedido != 'Entregado'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("pe.cod_pedido");
                data[i][1] = res.getString("p.nombre_producto");
                data[i][2] = res.getString("p.descripcion_producto");
                data[i][3] = res.getString("p.seccion_producto");
                data[i][4] = res.getString("cantidad_producto");
                data[i][5] = res.getString("coste_pedido");
                data[i][6] = res.getString("estado_pedido");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }

    /**
     * Método para crear un ComboBox con las compras
     *
     * @param x Fechas de las compras
     * @return Modelo del Combobox
     */
    public ComboBoxModel cbCompra(int x) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        String r = null;
        try {
            int i = 0;
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT DISTINCT fecha_pedido FROM Pedido WHERE cliente_cod=" + x + " GROUP BY fecha_pedido");
            ResultSet res = pstm.executeQuery();
            m.removeAllElements();
            while (res.next()) {
                r = res.getString("fecha_pedido");
                m.addElement(r);
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return m;
    }

    /**
     * Método para crear un ComboBox con las compras
     *
     * @return Model del Combobox
     */
    public ComboBoxModel cbCompra() {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        String r = null;
        try {
            int i = 0;
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT fecha_pedido FROM Pedido GROUP BY fecha_pedido");
            ResultSet res = pstm.executeQuery();
            m.removeAllElements();
            while (res.next()) {
                r = res.getString("fecha_pedido");
                m.addElement(r);
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return m;
    }

    /**
     * Método para crear un ComboBox con los productos a partir de una seccion
     *
     * @param x Seccion del producto
     * @return Modelo del ComboBox
     */
    public ComboBoxModel cbProducto(String x) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        String r = null;
        try {
            int i = 0;
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT nombre_producto FROM Productos WHERE seccion_producto = '" + x + "'");
            ResultSet res = pstm.executeQuery();
            m.removeAllElements();
            while (res.next()) {
                r = res.getString("nombre_producto");
                m.addElement(r);
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return m;
    }

    /**
     * Método para crear un ComboBox con las ofertas
     *
     * @return Modelo del Combobox
     */
    public ComboBoxModel cbOferta() {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        String r = null;
        try {
            int i = 0;
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT descripcion_oferta FROM Ofertas");
            ResultSet res = pstm.executeQuery();
            m.removeAllElements();
            while (res.next()) {
                r = res.getString("descripcion_oferta");
                m.addElement(r);
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return m;
    }

    
    
}
