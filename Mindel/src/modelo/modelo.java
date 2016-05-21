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
    
    public boolean loginCliente(String u, String p) {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
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

    public boolean loginEmpleado(String u, String p) {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
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

    public int getIdEmpleado(String n) {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
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

    public int getIdCliente(String n) {
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
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

    public boolean registrarCliente(String nu, String pss, String n, String d, String dm, String p, String cd, String f) {
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

    public boolean registrarEmpleado(String nu, String pss, String n, String d, String dm, String p, String cd, String f) {
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

    public boolean registrarProveedor(String n, String d, int cp) {
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

    public boolean crearOferta(String d, double c) {
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

    public boolean crearProducto(String n, String d, String s, double p) {
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

    public DefaultTableModel tablaVaciaCarro(){
        DefaultTableModel tablemodel = new DefaultTableModel();
        String[] columNames = {"Nombre del Producto", "Descripcion", "Precio", "Cantidad"};
        Object [][] data = new String[0][4];
        tablemodel.setDataVector(data, columNames);
        return tablemodel;
    } 
    
    public DefaultTableModel tablaVaciaProveedor(){
        DefaultTableModel tablemodel = new DefaultTableModel();
        String[] columNames = {"Nombre del Producto", "Descripcion", "Seccion", "Proveedor", "Cantidad", "Precio"};
        Object [][] data = new String[0][7];
        tablemodel.setDataVector(data, columNames);
        return tablemodel;
    }
    
    
    
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

    public DefaultTableModel getTablaProvProd(int num) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Nombre del Producto", "Descripcion", "Precio"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT COUNT(*) as total FROM Envios WHERE proveedor_cod="+num);
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
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT p.* FROM Productos p, Envios e WHERE e.producto_cod=p.cod_producto AND e.proveedor_cod="+num);
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

    public boolean crearEnvio(String np, String npr, int c, double p, Date d) {
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
    
    public boolean aplicarOferta(String e, String x) {
        int ido = this.getIdOferta(e);
        String insert = "UPDATE Productos SET oferta_cod = "+ido+" WHERE nombre_producto = '"+x+"'";
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
    
    public boolean actualizarEstado(String np, int c, String f, int id) {
        String insert = "UPDATE Pedido SET estado_pedido = '"+np+"', empleado_cod = "+id+" WHERE cod_pedido = "+c+" AND fecha_pedido = '"+f+"'";
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
    
    public ComboBoxModel cbProducto(String x) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        String r = null;
        try {
            int i = 0;
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT nombre_producto FROM Productos WHERE seccion_producto = '"+x+"'");
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
