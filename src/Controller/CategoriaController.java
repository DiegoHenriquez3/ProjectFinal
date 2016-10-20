/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Categoria;
import Model.CategoriaM;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class CategoriaController {
    
    private DefaultComboBoxModel modelCombox= new DefaultComboBoxModel(); 
    private CategoriaM ctModel;
    
    public CategoriaController() {
        
        ctModel = new CategoriaM();
    }
    
    public void llenarCategoria(JComboBox combo){
    
       List<Categoria> listCategoria= new ArrayList<>() ;
       
       listCategoria= ctModel.listaCategoria();
       
       combo.setModel(modelCombox);
        try {
            
            for (Categoria cat : listCategoria) {
            
         
                    modelCombox.addElement(cat);
          

            }
            
            
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",0);
            
        }
        
    }
    
}
