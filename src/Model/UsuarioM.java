/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Diego
 */
public class UsuarioM {
    
    private final ConexionDB conexion;
    private final Connection conDB;

    public UsuarioM() {
        conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();
    }

    public void insertarUser(Usuario user) {
        String sqlQuery="";
        sqlQuery = "INSERT INTO USUARIO(ID_USER,USUARIO,CONTRASEÑA,ID_EMPLEADO) VALUES ";
        sqlQuery += "(USUARIO_SEQ.NEXTVAL,";
        sqlQuery += "('" + user.getUsuario();
        sqlQuery += ",'" + user.getContra();
        sqlQuery += ",'" + user.getIdEmpleado();
        
          try 
        {
            conDB.setAutoCommit(false);
            conDB.setTransactionIsolation(TRANSACTION_READ_UNCOMMITTED);
            Statement stm= conDB.createStatement();
            stm.executeUpdate(sqlQuery);
            conDB.commit();
            conDB.setAutoCommit(true);
        } 
        catch (Exception ex) {   
        }
          
    }
    
    
    
   public Usuario getUsuario(Usuario user) {


        String sqlQuery = "SELECT id_user,usuario,contra,empleado.NOMBRE"
                + " from usuario inner join empleado on usuario.ID_EMPLEADO=empleado.ID_EMPLEADO"
                + " WHERE contra='" + user.getContra() + "' AND usuario='" + user.getUsuario() + "'";
        Usuario userInfo = new Usuario();
        Empleado userEm = new Empleado();
        
        try {
           
            Statement stm = null;
            ResultSet lista=null;
            
            stm=conDB.createStatement();
            lista=stm.executeQuery(sqlQuery);
        
            while (lista.next()) {
                userInfo.setIdUser(lista.getInt(1));
                userInfo.setUsuario(lista.getString(2));
                userEm.setNombre(lista.getString(4));
                userInfo.setIdEmpleado(userEm);
                System.out.println(lista.getString(4));
               
               return userInfo;
                
            }

            conDB.close();
            stm.close();

        } catch (SQLException e) {

        }

        return null;
    }

}
