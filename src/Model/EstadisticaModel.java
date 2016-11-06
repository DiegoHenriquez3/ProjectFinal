/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Diego
 */
public class EstadisticaModel {

    private ConexionDB conexion;
    private Connection conDB;
    private ArrayList<Zapato> listZapato;

    public EstadisticaModel() {

        this.conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();

    }

    private void getTopPedido() {
        listZapato = new ArrayList<>();
        Zapato z = new Zapato();
        Talla t = new Talla();
        Marca m = new Marca();
        CallableStatement callPro = null;

        try {
            this.conDB = conexion.construirConexion();
            callPro = conDB.prepareCall("{call TOP_PEDIDO(?)}");
            callPro.registerOutParameter(1, OracleTypes.CURSOR);
            callPro.executeQuery();
            ResultSet listResul = ((OracleCallableStatement) callPro).getCursor(1);
            while (listResul.next()) {
                z.setIdZapato(listResul.getInt(1));
                z.setNombre(listResul.getString(2));
                z.setCantidad(listResul.getInt(3));
                m.setIdMarca(listResul.getInt(4));
                m.setMarca(listResul.getString(5));
                t.setIdTalla(6);
                t.setUs(String.valueOf(listResul.getDouble(7)));
                z.setPrecio(listResul.getDouble(8));

                //Se establecen todos los objetos relacionados con el "Zapato"
                z.setIdTalla(t);
                z.setIdMarca(m);

                listZapato.add(z);
                //Se hace un RESET de los objetos utilizados
                z = new Zapato();
                t = new Talla();
                m = new Marca();

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        } finally {
            try {
                conDB.close();
            } catch (SQLException ex) {
                Logger.getLogger(EstadisticaModel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public ArrayList<Zapato> getListZapato() {
        getTopPedido();
        return listZapato;
    }
    
    

}
