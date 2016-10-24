/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class PedidoM {

    private final ConexionDB conexion;
    private final Connection conDB;

    public PedidoM() {
        conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();
    }

    public boolean InsertarPedido(Pedido xPedido) {
        boolean flag = false;
        String query = "";
        PreparedStatement stmP = null;
        query = "insert into pedido(PEDIDO.ID_PEDIDO,PEDIDO.ID_SUCURSAL,PEDIDO.ID_BODEGA,PEDIDO.ID_EMPLEADO,PEDIDO.ESTADO,PEDIDO.FECHA_PEDIDO) "
                + "values(pedido_seq.NEXTVAL,?,?,?,?,"+xPedido.getFechaPedido()+")";
        try {
            conDB.setAutoCommit(false);
            stmP = conDB.prepareStatement(query);
            stmP.setInt(1, xPedido.getIdSucursal().getIdSucursal());
            stmP.setInt(2, xPedido.getIdBodega().getIdBodega());
            stmP.setInt(3, xPedido.getIdEmpleado().getIdEmpleado());
            stmP.setInt(4, xPedido.getEstado());
            //stmP.setString(5, xPedido.getFechaPedido());
            
            if (stmP.executeUpdate() > 0) {

                flag = true;
            }
            conDB.commit();
            conDB.setAutoCommit(true);
            
            query = "select pedido_seq.CURRVAL from dual";
            Statement stm = conDB.createStatement();
            ResultSet listResul = stm.executeQuery(query);
            while (listResul.next()) {
               xPedido.setIdPedido(listResul.getInt(1));
            }
            
            conDB.close();
            stm.close();
            stmP.close();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"ErrorPedidoM",0);
        }

        return flag;

    }

}
