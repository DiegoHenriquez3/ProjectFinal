/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class ZapatoM {

    private final ConexionDB conexion;
    private final Connection conDB;

    public ZapatoM() {
        conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();

    }

    public boolean insertarZapatos(Pedido pedido) {

        boolean flag = false;
        String query = "";

        query = "INSERT INTO DETALLE_PEDIDO(ID_PEDIDO,CANTIDAD,ID_ZAPATO) VALUES(PEDIDO_SEQ.CURRVAL,?,?,?)";

        PreparedStatement stmP = null;

        try {
            for (Zapato zapato : pedido.getZapatosList()) {

                conDB.setAutoCommit(false);
                stmP = conDB.prepareStatement(query);
                stmP.setInt(1, zapato.getCantidad());
                stmP.setInt(2, zapato.getIdZapato());
                stmP.setInt(3, zapato.getIdTalla().getIdTalla());
                
                if(stmP.executeUpdate()>0){
                     flag=true;
                }
              
            }
              conDB.commit(); 
              conDB.setAutoCommit(true);
              

        } catch (Exception e) {
         
        }
        return flag;

    }
    
    
  public ArrayList<Zapato> listaZapatos() throws SQLException {
        ArrayList<Zapato> listZapato = new ArrayList<>();
        Zapato alguien = new Zapato();
        Statement  stm = null;
        try {
            stm = conDB.createStatement();
            ResultSet listResul = stm.executeQuery("");
            while (listResul.next()) {
                
                
               alguien = new Zapato();
            }
           
           

        } catch (SQLException e) {
              JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        }
        finally{
          conDB.close();
          if (stm!=null){
               stm.close();
          }
        }
        
       
        return listZapato;
    }

    public boolean eliminarEmpleado(Empleado x) {
        boolean flag = false;
         PreparedStatement stmP = null;
        try {
            conDB.setAutoCommit(false);
            conDB.setTransactionIsolation(TRANSACTION_READ_UNCOMMITTED);
            stmP=conDB.prepareStatement("DELETE * FROM empleado WHERE ID_EMPLEADO=?");
            stmP.setInt(1,x.getIdEmpleado());
            conDB.commit();
            conDB.setAutoCommit(true);
            if (stmP.executeUpdate() > 0) {

                flag = true;
            }
        } catch (SQLException e) {
        }
        return flag;
    }
    
  

}
