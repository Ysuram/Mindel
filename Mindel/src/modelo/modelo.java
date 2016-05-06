package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class modelo extends database {

    public modelo() {
    }
    
    
    public DefaultTableModel getTablaProducto(String s)
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"Nombre del Producto","Descripcion","Precio"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
      //para formar la matriz de datos
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total WHERE seccion_producto = '" + s + "' FROM Productos");
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
    //se crea una matriz con tantas filas y columnas que necesite
    Object[][] data = new String[registros][4];
      try{
          //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Productos");
         ResultSet res = pstm.executeQuery();
         int i=0;
         while(res.next()){
                data[i][0] = res.getString( "nombre_producto" );
                data[i][1] = res.getString( "descripcion_producto" );
                data[i][2] = res.getString( "precio_producto" );
            i++;
         }
         res.close();
         //se añade la matriz de datos en el DefaultTableModel
         tablemodel.setDataVector(data, columNames );
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }
    
    public DefaultTableModel getTablaCarro()
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"Nombre del Producto","Descripcion","Precio"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
      //para formar la matriz de datos
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total GROUP BY seccion_producto FROM Productos");
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
    //se crea una matriz con tantas filas y columnas que necesite
    Object[][] data = new String[registros][5];
      try{
          //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Productos");
         ResultSet res = pstm.executeQuery();
         int i=0;
         while(res.next()){
                data[i][0] = res.getString( "nombre_producto" );
                data[i][1] = res.getString( "descripcion_producto" );
                data[i][2] = res.getString( "precio_producto" );
            i++;
         }
         res.close();
         //se añade la matriz de datos en el DefaultTableModel
         tablemodel.setDataVector(data, columNames );
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }
}
