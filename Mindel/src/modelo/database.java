package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {

    /**
     * database
     */
    private String db = "dam12_Mindel";
    /**
     * usuario
     */
    private String user = "dam12";
    /**
     * contraseña de MySql
     */
    private String password = "salesianas";
    /**
     * Cadena de conexion
     */
    private String url = "jdbc:mysql://192.168.28.3/" + db;
    /**
     * variable para trabajar con la conexion a la base de datos
     */
    private Connection conn = null;

    /**
     * Constructor de clase
     */
    public database() {
        this.url = "jdbc:mysql://192.168.28.3/" + this.db;
        try {
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexión
            conn = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("EXITO");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConexion() {
        return this.conn;
    }
}
