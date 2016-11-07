/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Bodega;
import Model.ZapatoM;
import View.ZapatoCategoria;
import com.sun.prism.j2d.J2DPipeline;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BlackHat
 */
public class ZapatoController {

    private DefaultListModel modelList;
    private ZapatoM zapatoM;
    private List<Model.Zapato> listZapato;
    private List<Model.Zapato> listPedido;
    private DefaultTableModel modelTable;

    public ZapatoController() {
        zapatoM = new ZapatoM();
        modelList = new DefaultListModel();
        listZapato = new ArrayList<>();
        listPedido = new ArrayList<>(10);
        modelTable = new DefaultTableModel();

    }

    public List<Model.Zapato> getListPedido() {
        return listPedido;
    }

    public void llenarTabla(JTable tabla) {
        tabla.setModel(modelTable);
        modelTable.addColumn("ZAPATO");
        modelTable.addColumn("MARCA");
        modelTable.addColumn("CANTIDAD");
        modelTable.addColumn("TALLA");
        modelTable.addColumn("PRECIO PU");

    }

    public void addListZapato(int index, int cantidad) {
        Model.Zapato z = new Model.Zapato();
        if (index != -1) {
            if (cantidad > 20) {
                z = (Model.Zapato) modelList.get(index);
                z.setCantidad(cantidad);
                listPedido.add(z);
                Object[] valores = new Object[5];
                valores[0] = z.getNombre();
                valores[1] = z.getIdMarca().getMarca();
                valores[2] = z.getCantidad();
                valores[3] = z.getIdTalla().getUs();
                valores[4] = z.getPrecio();
                modelTable.addRow(valores);

                JOptionPane.showMessageDialog(null, "Zapato agregado con exito", "PedidoC", 1);

            } else {
                JOptionPane.showMessageDialog(null, "La Cantidad debe de ser mayor a 20 pares", "AlertaC", 2);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No a seleccionado ningun zapato", "ErrorC", 0);
        }

    }

    public void llenarList(JList jLista) {

        try {
            listZapato = zapatoM.listaZapatos();
        } catch (SQLException ex) {
            Logger.getLogger(ZapatoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        jLista.setModel(modelList);

        listOrderBy("Hombre");

    }

    private void listOrderBy(String categoria) {
        modelList.removeAllElements();
        listZapato.stream().filter((zapato) -> (zapato.getCategoria().getCategoria().equals(categoria))).forEach((zapato) -> {
            modelList.addElement(zapato);
        });

    }

    public void comboChange(JComboBox combo) {

        combo.addActionListener((ActionEvent e) -> {
            String categoria = combo.getSelectedItem().toString();
            listOrderBy(categoria);

        });

    }

    public void abastecerBodega(Entity.Zapato z, Bodega b, int cantidad) {

        if (zapatoM.abastecer(z, b, cantidad)) {
            JOptionPane.showMessageDialog(null, "Transaccion exitosa", "Bodega", 1);

        } else {

        }
    }

    public void sendList() {

    }

    public void create(Entity.Zapato z,JTable table) {
        if (zapatoM.createZapato(z)) {
            ZapatoCategoria form = new ZapatoCategoria(z);
            form.setVisible(true);

            Object[] valores = new Object[4];
            valores[0] = z.getZapato();
            valores[1] = z.getIdMarca().getMarca();
            valores[2] = z.getIdTalla().getUs();
            valores[3] = z.getPrecio();
            modelTable.addRow(valores);

            //JOptionPane.showMessageDialog(null,"ZAPATO INGRESADO CON EXITO","ZAPATOC", 1);
        } else {
            JOptionPane.showMessageDialog(null, "ERROR", "ZAPATOC", 0);
        }

    }
    
    private JTable tableZ ;

    public  void linkTableZapato( JTable table) {
        this.tableZ=table;
        modelTable.addColumn("ZAPATO");
        modelTable.addColumn("MARCA");
        modelTable.addColumn("CANTIDAD");
        modelTable.addColumn("TALLA");
        modelTable.addColumn("PRECIO PU");
        table.setModel(modelTable);

    }

}
