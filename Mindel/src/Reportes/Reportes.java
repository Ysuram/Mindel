/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author android-0174654321
 */
public class Reportes {
    
    public void reporteContratos(Connection a) throws SQLException, JRException{
    JasperReport contratos=null;
        System.out.println("1");
    contratos=(JasperReport) JRLoader.loadObjectFromFile("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\beticaDeSeguros\\beticaDeSeguros\\src\\reportes\\AdminContratos.jasper");
        System.out.println("2");
    JasperPrint print=JasperFillManager.fillReport(contratos, null, a);
        System.out.println("3");
    JasperViewer ver= new JasperViewer(print);
        System.out.println("4");
    ver.setTitle("Contratos");
    ver.setVisible(true);
    }
    
}

