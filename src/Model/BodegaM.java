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
 * @author BlackHat
 */
public class BodegaM {
    
       
    private  ConexionDB conexion;
    private  Connection conDB;
 
    public BodegaM() {
        
        this.conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();
    
    }
    
   
    public ArrayList<Bodega> listaBodega() {
        ArrayList<Bodega> listBodega = new ArrayList<>();
        Bodega bdg = new Bodega();
        Municipio m= new Municipio();
        
        String query ="SELECT id_bodega,nombre,municipio FROM BODEGA INNER JOIN municipio on bodega.id_municipio=municipio.id_municipio";
        try {
            Statement stm = conDB.createStatement();
            ResultSet listResul = stm.executeQuery(query);
            while (listResul.next()) {
              bdg.setIdBodega(listResul.getInt(1));
              bdg.setNombre(listResul.getString(2));
              m.setMunicipio(listResul.getString(3));
              bdg.setIdMunicipio(m);
              listBodega.add(bdg);
              bdg= new Bodega();
              m= new Municipio();
              
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
