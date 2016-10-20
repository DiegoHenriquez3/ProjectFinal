/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class TallaM {

    private ConexionDB conexion;
    private Connection conDB;

    public TallaM() {

        this.conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();
    }
    
      public ArrayList<Talla> listaTallas() {
        ArrayList<Talla> listTalla = new ArrayList<>();
        Talla talla = new Talla();
        
        
        String query ="SELECT id_talla,us FROM talla order by us";
        try {
            Statement stm = conDB.createStatement();
            ResultSet listResul = stm.executeQuery(query);
            while (listResul.next()) {
              talla.setIdTalla(listResul.getInt(1));
              talla.setUs(String.valueOf(listResul.getDouble(2)));
              listTalla.add(talla);
              talla= new Talla();
              
            }
            conDB.close();
            stm.close();

        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
            return null;
        }
        return listTalla;
    }
    

}
