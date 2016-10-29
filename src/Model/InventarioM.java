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
import javax.swing.JOptionPane;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Diego
 */
public class InventarioM {

    private final ConexionDB conexion;
    private Connection conDB;

    public InventarioM(){
        conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();

    }

    public ArrayList<Zapato> listaZapatos() throws SQLException {
        ArrayList<Zapato> listZapato = new ArrayList<>();
        Zapato z = new Zapato();
        Color col = new Color();
        Talla t = new Talla();
        Marca m = new Marca();
        CallableStatement callPro = null;

        try {
            this.conDB = conexion.construirConexion();
            callPro = conDB.prepareCall("{call list_existencias(?)}");
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
              

                //Se establecen todos los objetos relacionados con el "Zapato"
                z.setIdTalla(t);
                z.setColor(col);
                z.setIdMarca(m);

                listZapato.add(z);
                //Se hace un RESET de los objetos utilizados
                z = new Zapato();
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
