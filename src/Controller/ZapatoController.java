/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Zapato;
import Model.ZapatoM;
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
    private List<Zapato> listZapato;
    private List<Zapato> listPedido;
    private List<Zapato> listOrder;
    private DefaultTableModel modelTable;

    public ZapatoController() {
        zapatoM = new ZapatoM();
        modelList = new DefaultListModel();
        listZapato = new ArrayList<>();
        listPedido = new ArrayList<>();
        listOrder = new ArrayList<>();
        modelTable = new DefaultTableModel();

    }

    public List<Zapato> getListPedido() {
        return listPedido;
    }

    public void setListPedido(List<Zapato> listPedido) {
        this.listPedido = listPedido;
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
        Zapato z = new Zapato();
        if (index != -1) {
            if (cantidad > 20) {
                listOrder.get(index).setCantidad(cantidad);
                listPedido.add(listOrder.get(index));

                z = listPedido.get(index);

                Object[] valores = new Object[5];
                valores[0] = z.getNombre();
                valores[1] = z.getIdMarca().getMarca();
                valores[2] = z.getCantidad();
                valores[3] = z.getIdTalla().getUs();
                valores[4] = z.getPrecio();
                modelTable.addRow(valores);

                JOptionPane.showMessageDialog(null, "Zapato agregado con exito", "Pedido", 1);
                
            } else {
                JOptionPane.showMessageDialog(null, "La Cantidad debe de ser mayo a 10 pares", "Error", 0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No a seleccionado ningun zapato", "Error", 0);
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
        listOrder = new ArrayList<>();
        modelList.removeAllElements();
        listZapato.stream().filter((zapato) -> (zapato.getCategoria().getCategoria().equals(categoria))).forEach((zapato) -> {
            modelList.addElement(zapato);
            listOrder.add(zapato);
        });

    }

    public void comboChange(JComboBox combo) {

        combo.addActionListener((ActionEvent e) -> {
            String categoria = combo.getSelectedItem().toString();
            listOrderBy(categoria);

        });

    }

}
