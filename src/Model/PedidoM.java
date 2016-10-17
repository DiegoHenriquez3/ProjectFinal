/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

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
                + "values(pedido_seq.NEXTVAL,?,?,?,?)";
        try {
            conDB.setAutoCommit(false);
            conDB.setTransactionIsolation(TRANSACTION_READ_UNCOMMITTED);
            stmP = conDB.prepareStatement(query);
            stmP.setInt(1, xPedido.getIdSucursal().getIdSucursal());
            stmP.setInt(2, xPedido.getIdBodega().getIdBodega());
            stmP.setInt(2, xPedido.getIdEmpleado().getIdEmpleado());
            stmP.setInt(4, xPedido.getEstado());
            stmP.setString(5, xPedido.getFechaPedido());

            conDB.commit();
            conDB.setAutoCommit(true);
            conDB.close();
            stmP.close();
            

        } catch (Exception ex) {
            
        }

        return flag;

    }

}
