/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Sucursal;
import Model.SucursalM;
import View.IngresarPedido;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class SucursalController {
    
     
    private DefaultComboBoxModel modelCombox= new DefaultComboBoxModel(); 
    private SucursalM scModel;
    private IngresarPedido form;
    
    public SucursalController() {
        
        scModel = new SucursalM();
    }
    
    public void llenarSucursal(JComboBox combo){
    
       List<Sucursal> listSucursal = new ArrayList<>() ;
       
       listSucursal= scModel.listaSucursal();
       
       combo.setModel(modelCombox);
        try {
            for (Sucursal s : listSucursal) {
            
         
          modelCombox.addElement(s);
          

            }
            
          // formEmpInsert.getCbBodega().setSelectedIndex(0);
            
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(form,e.getMessage(),"Error",0);
            
        }
        
    }
    
}
