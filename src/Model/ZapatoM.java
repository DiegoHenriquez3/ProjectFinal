/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.math.BigDecimal;
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
    private Connection conDB;

    public ZapatoM() {
        conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();

    }

    public boolean insertarZapatos(Pedido pedido) {

        boolean flag = false;
        String query = "";

        query = "INSERT INTO DETALLE_PEDIDO(ID_PEDIDO,CANTIDAD,ID_ZAPATO) VALUES(" + pedido.getIdPedido() + ",?,?)";

        PreparedStatement stmP = null;

        try {
            this.conDB = conexion.construirConexion();
            for (Zapato zapato : pedido.getZapatosList()) {

                conDB.setAutoCommit(false);
                stmP = conDB.prepareStatement(query);
                stmP.setInt(1, zapato.getCantidad());
                stmP.setInt(2, zapato.getIdZapato());

                if (stmP.executeUpdate() > 0) {
                    flag = true;
                    conDB.commit();
                    conDB.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorZ", 0);
        }
        return flag;

    }

    public boolean insertZapatosP(Pedido pedido) {
        boolean flag = false;
        CallableStatement callPro = null;
        try {
            this.conDB = conexion.construirConexion();

            for (Zapato zapato : pedido.getZapatosList()) {
                callPro = conDB.prepareCall("{call INSERT_ZAPATO(?,?,?,?)}");
                callPro.setInt(1, pedido.getIdPedido());
                callPro.setInt(2, zapato.getCantidad());
                callPro.setInt(3, zapato.getIdZapato());
                callPro.registerOutParameter(4, OracleTypes.VARCHAR);
                callPro.executeQuery();

                if (callPro.getString(4).equals("TRANSACION REALIZADA CON EXITO")) {
                    flag = true;
                }

            }
            conDB.commit();
            conDB.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorM", 0);
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
                cat.setIdCategoria(listResul.getInt(12));
                cat.setCategoria(listResul.getString(13));

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

    public boolean abastecer(Entity.Zapato z, Bodega b, int cantidad) {
        boolean flag = false;
        CallableStatement callPro = null;

        try {
            this.conDB = conexion.construirConexion();
            callPro = conDB.prepareCall("{call abastecer(?,?,?,?)}");
            callPro.setBigDecimal(1, z.getIdZapato());
            callPro.setInt(2, b.getIdBodega());
            callPro.setInt(3, cantidad);
            callPro.registerOutParameter(4, OracleTypes.NUMBER);

            callPro.executeQuery();

            if (callPro.getInt(4) > -2) {
                flag = true;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorM", 0);
        }
        return flag;
    }

    public boolean createZapato(Entity.Zapato z) {

        CallableStatement callPro = null;
        try {
            this.conDB = conexion.construirConexion();
            callPro = conDB.prepareCall("{call CREATE_ZAPATO(?,?,?,?,?,?)}");
            callPro.setBigDecimal(1, z.getIdMarca().getIdMarca());
            callPro.setBigDecimal(2, z.getPrecio());
            callPro.setString(3, z.getZapato());
            callPro.setBigDecimal(4, z.getIdColor().getIdColor());
            callPro.setBigDecimal(5, z.getIdTalla().getIdTalla());
            callPro.registerOutParameter(6, OracleTypes.NUMBER);

            callPro.executeQuery();

            if (callPro.getInt(6) > -1) {

                z.setIdZapato(callPro.getBigDecimal(6));
                return true;

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorM", 0);
        }

        return false;

    }

}
