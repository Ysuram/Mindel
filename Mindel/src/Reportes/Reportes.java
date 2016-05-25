/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

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
 * @author EQUIPO
 */
public class Reportes {

    public void ReportePedido() throws SQLException, JRException {
        Connection a;
        a = DriverManager.getConnection("jdbc:mysql://79.148.236.236/dam12_Mindel", "dam12", "salesianas");
        JasperReport reporte = null;
        reporte = (JasperReport) JRLoader.loadObjectFromFile("\\Reportes\\Cliente.jasper");
        JasperPrint print = JasperFillManager.fillReport(reporte, null, a);
        JasperViewer ver = new JasperViewer(print);
        ver.setTitle("Reporte factura");
        ver.setVisible(true);
    }
}
