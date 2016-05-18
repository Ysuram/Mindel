package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

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
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total WHERE seccion_producto = '" + s + "' FROM Productos");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][4];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Productos");
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

    public DefaultTableModel getTablaCarro() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"Nombre del Producto", "Descripcion", "Precio"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total GROUP BY seccion_producto FROM Productos");
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
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Productos");
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

    public ComboBoxModel cbProveedor() {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        String r = null;
        try {
            int i = 0;
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

}
