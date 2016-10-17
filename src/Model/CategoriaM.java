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

/**
 *
 * @author Diego
 */
public class CategoriaM {
    
    private  ConexionDB conexion;
    private  Connection conDB;
 
    public CategoriaM() {
        
        this.conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();
    
    }
    
      public ArrayList<Categoria> listaCategoria() {
        ArrayList<Categoria> listBodega = new ArrayList<>();
        Categoria c = new Categoria();
        
        
        String query ="SELECT id_categoria,categoria FROM categoria order by categoria";
        try {
            Statement stm = conDB.createStatement();
            ResultSet listResul = stm.executeQuery(query);
            while (listResul.next()) {
              c.setIdCategoria(listResul.getInt(1));
              c.setCategoria(listResul.getString(2));
              c = new Categoria();
            }
            conDB.close();
            stm.close();

        } 
        catch (SQLException e) {
            return null;
        }
        return listBodega;
    }
    
}
