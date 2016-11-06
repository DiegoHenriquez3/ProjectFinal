/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Diego
 */
public class PedidoM {

    private final ConexionDB conexion;
    private  Connection conDB;

    public PedidoM() {
        conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();
    }

    /*  callPro = conDB.prepareCall("{call list_zapatos(?)}");
            callPro.registerOutParameter(1, OracleTypes.CURSOR);
            callPro.executeQuery();
            ResultSet listResul = ((OracleCallableStatement) callPro).getCursor(1);*/
    public boolean InsertarPedido(Pedido xPedido) {
        
        boolean flag = false;
        String query = "";
        PreparedStatement stmP = null;
        CallableStatement callPro = null;
        query = "";
        query = "insert into pedido(PEDIDO.ID_PEDIDO,PEDIDO.ID_SUCURSAL,PEDIDO.ID_BODEGA,PEDIDO.ID_EMPLEADO,PEDIDO.ESTADO,PEDIDO.FECHA_PEDIDO) "
                + "values(pedido_seq.NEXTVAL,?,?,?,?," + xPedido.getFechaPedido() + ")";
        try {
	    this.conDB = conexion.construirConexion();
            conDB.setAutoCommit(false);
            callPro = conDB.prepareCall("{call PEDIDO_INSERT(?,?,?," + xPedido.getFechaPedido() + ",?)}");
            callPro.setInt(1, xPedido.getIdSucursal().getIdSucursal());
            callPro.setInt(2, xPedido.getIdBodega().getIdBodega());
            callPro.setInt(3, xPedido.getIdEmpleado().getIdEmpleado());
          //  callPro.setString(4, xPedido.getFechaPedido());
            callPro.registerOutParameter(4, OracleTypes.NUMBER);
            /*stmP = conDB.prepareStatement(query);
            stmP.setInt(1, xPedido.getIdSucursal().getIdSucursal());
            stmP.setInt(2, xPedido.getIdBodega().getIdBodega());
            stmP.setInt(3, xPedido.getIdEmpleado().getIdEmpleado());
            stmP.setInt(4, xPedido.getEstado());
            //stmP.setString(5, xPedido.getFechaPedido());
             */
           callPro.execute() ;
                   
                   
             if (callPro.getInt(4)>0){

                flag = true;
                xPedido.setIdPedido(callPro.getInt(4));
            }
            /*conDB.commit();
            conDB.setAutoCommit(true);
            
            query = "select pedido_seq.CURRVAL from dual";
            Statement stm = conDB.createStatement();
            ResultSet listResul = stm.executeQuery(query);
            while (listResul.next()) {
               xPedido.setIdPedido(listResul.getInt(1));
            }
             */

            conDB.close();
            callPro.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ErrorPedidoM", 0);
        }

        return flag;

    }

}
