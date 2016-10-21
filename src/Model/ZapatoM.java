/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Diego
 */
public class ZapatoM {

    private final ConexionDB conexion;
    private  Connection conDB;

    public ZapatoM() {
        conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();

    }

    public boolean insertarZapatos(Pedido pedido) {

        boolean flag = false;
        String query = "";

        query = "INSERT INTO DETALLE_PEDIDO(ID_PEDIDO,CANTIDAD,ID_ZAPATO) VALUES(PEDIDO_SEQ.CURRVAL,?,?,?)";

        PreparedStatement stmP = null;
   
        try {
            this.conDB = conexion.construirConexion();
            for (Zapato zapato : pedido.getZapatosList()) {

                conDB.setAutoCommit(false);
                stmP = conDB.prepareStatement(query);
                stmP.setInt(1, zapato.getCantidad());
                stmP.setInt(2, zapato.getIdZapato());
                stmP.setInt(3, zapato.getIdTalla().getIdTalla());

                if (stmP.executeUpdate() > 0) {
                    flag = true;
                }

            }
            conDB.commit();
            conDB.setAutoCommit(true);

        } catch (Exception e) {

        }
        return flag;

    }

    public ArrayList<Zapato> listaZapatos() throws SQLException {
        ArrayList<Zapato> listZapato = new ArrayList<>();
        Zapato z = new Zapato();
        Categoria cat = new Categoria();
        Color col = new Color();
        Talla t = new Talla();
        Marca m = new Marca();
        CallableStatement callPro = null;

        try {
            this.conDB = conexion.construirConexion();
            callPro = conDB.prepareCall("{call list_zapatos(?)}");
            callPro.registerOutParameter(1, OracleTypes.CURSOR);
            callPro.executeQuery();
            ResultSet listResul = ((OracleCallableStatement) callPro).getCursor(1);
            while (listResul.next()) {
                z.setIdZapato(listResul.getInt(1));
                z.setNombre(listResul.getString(2));
                z.setCantidad(listResul.getInt(3));
                z.setPrecio(listResul.getDouble(4));
                m.setIdMarca(listResul.getInt(5));
                m.setMarca(listResul.getString(6));
                col.setIdColor(listResul.getInt(7));
                col.setColor(listResul.getString(8));
                t.setIdTalla(9);
                t.setUs(String.valueOf(listResul.getDouble(10)));
               cat.setIdCategoria(listResul.getInt(11));
               cat.setCategoria(listResul.getString(12));
                
                //Se establecen todos los objetos relacionados con el "Zapato"
                z.setIdTalla(t);
                z.setColor(col);
                z.setIdMarca(m);
                z.setCategoria(cat);
  
                listZapato.add(z);
                //Se hace un RESET de los objetos utilizados
                z = new Zapato();
                cat = new Categoria();
                col = new Color();
                t = new Talla();
                m = new Marca();
                 
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        } finally {
            conDB.close();

        }

        return listZapato;
    }

}
