/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.Bodega;
import Model.BodegaM;
import View.IngresarEmpleados;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;



/**
 *
 * @author Diego
 */
public class BodegaController {
    
    private DefaultComboBoxModel modelCombox= new DefaultComboBoxModel();
    
    private BodegaM bgModel;
    private IngresarEmpleados formEmpInsert;

    public BodegaController() {
        
    }

   
    public void llenarBodega(JComboBox combo){
       List<Bodega> listBodega = new ArrayList<>() ;
       bgModel = new BodegaM();
    
       listBodega= bgModel.listaBodega();
       
       combo.setModel(modelCombox);
        try {
            for (Bodega bodega : listBodega) {
            
 
          modelCombox.addElement(bodega);
          
          
            }
            
          // formEmpInsert.getCbBodega().setSelectedIndex(0);
            
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(formEmpInsert,e.getMessage(),"Error",0);
            
        }
        
        //PruebaEditRemote
        
    
    
    }
    
  
    
}
