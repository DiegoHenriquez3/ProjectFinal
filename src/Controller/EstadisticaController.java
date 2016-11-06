/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.EstadisticaModel;
import Model.InventarioM;
import Model.Zapato;
import View.EstadisticaInventario;
import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Diego
 */
public class EstadisticaController {

    private JFreeChart chartP;
    private DefaultCategoryDataset data;
    private EstadisticaModel estadiscaM;
    private InventarioM inventarioM;
    private List<Zapato> listTop;
    private List<Zapato> listStock;

    public EstadisticaController() {
        data = new DefaultCategoryDataset();
        estadiscaM = new EstadisticaModel();
        inventarioM= new InventarioM();
        
    }

    public void buildChart() {
        listTop = new ArrayList<>();
        if (estadiscaM.getListZapato().size() > 0) {

            listTop = estadiscaM.getListZapato();
            
            for (Zapato zapato : listTop) {
                data.addValue(zapato.getCantidad(), "BODEGA PRINCIPAL", zapato.getNombre());
            }
           
            JFrame form = new JFrame("PEDIDOS");
            chartP = ChartFactory.createBarChart("TOP PEDIDOS", "ZAPATOS", "CANTIDAD", data, PlotOrientation.VERTICAL, true, true,false);
           
            ChartPanel panel = new ChartPanel(chartP);
            
            
            form.getContentPane().add(panel);
            form.pack();
            
            form.setVisible(true);
            form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } else {

        }

    }
    
    public void builChartBodega(){
    
       listStock = new ArrayList<>();
        try {
            listStock=inventarioM.listaZapatos();
        } catch (SQLException ex) {
            Logger.getLogger(EstadisticaController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        if (listStock.size()>0) {

            listStock.stream().forEach((zapato) -> {
                data.addValue(zapato.getCantidad(), "BODEGA PRINCIPAL", zapato.getNombre());
           });
           
            JFrame form = new JFrame("BODEGA");
            chartP = ChartFactory.createBarChart("STOCK", "ZAPATOS", "CANTIDAD EN BODEGA", data, PlotOrientation.VERTICAL, true, true,false);
           
            ChartPanel panel = new ChartPanel(chartP);
            
            
            form.getContentPane().add(panel);
            form.pack();
            
            form.setVisible(true);
            form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } else {

        } 
        
         
    }

}
