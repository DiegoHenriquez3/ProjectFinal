/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Zapato;
import Model.ZapatoM;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author BlackHat
 */
public class ZapatoController {
    
    private DefaultListModel modelList;
    private ZapatoM zapatoM;
    
    public ZapatoController() {
        zapatoM = new ZapatoM();
        modelList = new DefaultListModel();
    }
    
    public void llenarList(JList jLista) {
        List<Zapato> listZapato = new ArrayList<>();
    
        try {
            listZapato = zapatoM.listaZapatos();
        } catch (SQLException ex) {
            Logger.getLogger(ZapatoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jLista.setModel(modelList);
        
        for (Zapato zapato : listZapato) {
            
            modelList.addElement(zapato);
             
        }
 
    }
    
    public List<Zapato> listOrderBy(List<Zapato> lista ,String categoria){
         List<Zapato> orderedList = new ArrayList<>();
    
         lista.stream().filter((zapato) -> (zapato.getCategoria().getCategoria().equals(categoria))).forEach((zapato) -> {
             orderedList.add(zapato);
        });
        return orderedList;
    }
    
}
