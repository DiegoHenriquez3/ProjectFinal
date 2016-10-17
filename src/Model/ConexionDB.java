/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Black Hat
 */
public class ConexionDB {
    
    private Connection conexion;
    private String clase;
    private String stringCon;
    private String usuario;
    private String clave;
    
    public ConexionDB (){
    
        this.clase="oracle.jdbc.OracleDriver";
        this.stringCon="jdbc:oracle:thin:@localhost:1521:XE";
        this.usuario="zapateria";
        this.clave=  "Alquimista12";

    
    }
    
    
    public Connection construirConexion(){
        
        
        try {
            
         Class.forName(clase);
         this.conexion = DriverManager.getConnection(this.stringCon,this.usuario,this.clave);
         
         
        } catch (Exception e) {
            
            
        }
          
    
     return conexion ;
        
    }
    
   
    
}
