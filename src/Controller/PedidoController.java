/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BodegaM;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Mauricio
 */
public class PedidoController {
    
    
    private DefaultComboBoxModel modelCombox ;
    private BodegaM bgModel;
    
    public PedidoController() {
      modelCombox= new DefaultComboBoxModel();
      
    }
    
    
    
    
    public String getFechaPedido(){
        String dateFinal="";
        try {
            Date fecha = new Date();
            long miliSegundos = fecha.getTime();
            Calendar cal = Calendar.getInstance();
            String sqlDate = fechaString(cal);
            Time sqlTime = new Time(miliSegundos);
            dateFinal = "TO_DATE("+sqlDate+" "+sqlTime.toString()+",'dd-mm-yyyy hh24:mi:ss')";
            
        } catch (Exception e) {
            
        }
      return dateFinal;
    }
    
    private String  fechaString(Calendar cal){
        
        String stringDate = "";
        
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        if (cal!=null){
        
        stringDate= formato.format(cal.getTime());
        
        }
        return stringDate;    
    }
}
