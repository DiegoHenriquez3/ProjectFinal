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
public class SucursalM {
    
    private  ConexionDB conexion;
    private  Connection conDB;
 
    public SucursalM() {
        
        this.conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();
    
    }
    
    
    public ArrayList<Sucursal> listaSucursal() {
        ArrayList<Sucursal> listSucursal = new ArrayList<>();
        Sucursal sucursal = new Sucursal();
        Municipio m= new Municipio();
        String query ="SELECT id_sucursal,nombre,municipio FROM sucursal INNER JOIN municipio on sucursal.id_municipio=municipio.id_municipio";
        try {
            Statement stm = conDB.createStatement();
            ResultSet listResul = stm.executeQuery(query);
            while (listResul.next()) {
              sucursal.setIdSucursal(listResul.getInt(1));
              sucursal.setNombre(listResul.getString(2));
              m.setMunicipio(listResul.getString(3));
              sucursal.setIdMunicipio(m);
              listSucursal.add(sucursal);
              sucursal= new Sucursal();
              m= new Municipio();
            }
            conDB.close();
            stm.close();

        } 
        catch (SQLException e) {
            return null;
        }
        return listSucursal;
    }
    
    
}
