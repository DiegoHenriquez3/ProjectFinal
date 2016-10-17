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
public class EmpleadoM {

    private final ConexionDB conexion;
    private final Connection conDB;

    public EmpleadoM() {

        conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();

    }

    public boolean insertarEmpleado(Empleado x) {
        boolean flag = false;
        String query = "INSERT INTO EMPLEADO(ID_EMPLEADO,NOMBRE,APELLIDO,DUI,NIT,DIRECCION,TELEFONO,ID_BODEGA)";
        query += " VALUES(empleado_seq.nextVal,?,?,?,?,?,?,?)";
        PreparedStatement stmP = null;

        try {

            conDB.setAutoCommit(false);
            stmP = conDB.prepareStatement(query);
            stmP.setString(1, x.getNombre());
            stmP.setString(2, x.getApellido());
            stmP.setString(3, x.getDui());
            stmP.setString(4, x.getNit());
            stmP.setString(5, x.getDireccion());
            stmP.setString(6, x.getTelefono());
            stmP.setInt(7,  x.getIdBodega().getIdBodega());
            if (stmP.executeUpdate() > 0) {

                flag = true;
            }
            conDB.commit();
            conDB.setAutoCommit(true);
            
             

           

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        }

        return flag;
    }

    public ArrayList<Empleado> listarEmpleados() throws SQLException {
        ArrayList<Empleado> listEmpleados = new ArrayList<>();
        Empleado alguien = new Empleado();
        Bodega bg = new Bodega();
        Statement  stm = null;
        try {
            stm = conDB.createStatement();
            ResultSet listResul = stm.executeQuery(
                    "SELECT empleado.ID_EMPLEADO,empleado.NOMBRE,empleado.APELLIDO,empleado.DUI,empleado.NIT,empleado.TELEFONO,empleado.DIRECCION,bodega.NOMBRE"
                    + " FROM empleado INNER JOIN bodega ON empleado.ID_BODEGA=bodega.ID_BODEGA"
                    + " ORDER BY empleado.nombre, apellido DESC");
            while (listResul.next()) {
                alguien.setIdEmpleado(listResul.getInt(1));
                alguien.setNombre(listResul.getString(2));
                alguien.setApellido(listResul.getString(3));
                alguien.setDui(listResul.getString(4));
                alguien.setNit(listResul.getString(5));
                alguien.setDireccion(listResul.getString(6));
                alguien.setTelefono(listResul.getString(7));
                bg.setNombre(listResul.getString(8));
                alguien.setIdBodega(bg);
                listEmpleados.add(alguien);
                alguien = new Empleado();
                bg = new Bodega();
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
        
       
       
        return listEmpleados;
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

    public boolean actualizarEmpleado(Empleado x) {
        boolean flag = false;
        String sqlQuery = "UPDATE empleado SET nombre='" + x.getNombre();
        sqlQuery += "',apellido='" + x.getApellido() + "',dui='" + x.getDui() + "',nit='" + x.getNit()
                + "',telefono='" + x.getTelefono() + "', direccion='" + x.getDireccion()
                + ",id_bodega=" + x.getIdBodega();
        try {
            conDB.setAutoCommit(false);
            conDB.setTransactionIsolation(TRANSACTION_READ_UNCOMMITTED);
            Statement stm = conDB.createStatement();
            conDB.commit();
            conDB.setAutoCommit(true);
            if (stm.getUpdateCount() > 0) {

                flag = true;
            }

        } catch (SQLException e) {

        }
        return flag;
    }

}
