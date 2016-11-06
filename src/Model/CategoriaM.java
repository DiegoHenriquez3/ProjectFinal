/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class CategoriaM {

    private ConexionDB conexion;
    private Connection conDB;

    public CategoriaM() {

        this.conexion = new ConexionDB();
        this.conDB = conexion.construirConexion();

    }

    public ArrayList<Categoria> listaCategoria() {
        ArrayList<Categoria> listCat = new ArrayList<>();
        Categoria c = new Categoria();

        String query = "SELECT id_categoria,categoria FROM categoria order by categoria";
        try {
            Statement stm = conDB.createStatement();
            ResultSet listResul = stm.executeQuery(query);
            while (listResul.next()) {
                c.setIdCategoria(listResul.getInt(1));
                c.setCategoria(listResul.getString(2));
                listCat.add(c);
                c = new Categoria();

            }
            conDB.close();
            stm.close();

        } catch (SQLException e) {
            return null;
        }
        return listCat;
    }

    public boolean CategoriaZapato(List<Categoria> listCategoria, Entity.Zapato zapato) {
        boolean flag= false;
        PreparedStatement stmP = null;
        String query = "INSERT INTO ZAPATO_CATEGORIA(ID_CATEGORIA,ID_ZAPATO) VALUES(?,?)";
        try {
            this.conDB = conexion.construirConexion();
            for (Categoria categoria : listCategoria) {
                conDB.setAutoCommit(false);
                stmP = conDB.prepareStatement(query);
                stmP.setInt(1, categoria.getIdCategoria());
                stmP.setBigDecimal(2, zapato.getIdZapato());
               
                 if (stmP.executeUpdate() > 0) {
                    flag = true;
                    conDB.commit();
                    conDB.setAutoCommit(true);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"ERRORM", 0);
            return false;
        }
     return flag;
    }

}
