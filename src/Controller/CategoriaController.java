/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Categoria;
import Model.CategoriaM;
import View.ZapatoCategoria;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class CategoriaController {

    private DefaultComboBoxModel modelCombox;
    private DefaultListModel modelList;
    private DefaultListModel modelListAdd;
    private CategoriaM ctModel;
    private List<Categoria> listCategoria;
    private List<Categoria> listNew;

    public CategoriaController() {
        modelCombox = new DefaultComboBoxModel();
        ctModel = new CategoriaM();
        listCategoria = new ArrayList<>();
        modelList = new DefaultListModel<>();
        modelListAdd = new DefaultListModel<>();
        listNew = new ArrayList<>();
        llenarCategoria();
    }

    private void llenarCategoria() {

        listCategoria = ctModel.listaCategoria();

        try {

            listCategoria.stream().map((cat) -> {
                modelCombox.addElement(cat);
                return cat;
            }).forEach((cat) -> {
                modelList.addElement(cat);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);

        }

    }

    public void fillComboCat(JComboBox combo) {
        combo.setModel(modelCombox);

    }

    public void fillListCat(JList list) {
        list.setModel(modelList);
    }

    public void showCategoriasForm(Entity.Zapato z) {

        ZapatoCategoria formC = new ZapatoCategoria(z);
        formC.setVisible(true);

    }

    public void linkListAdd(JList list) {

        list.setModel(modelListAdd);
    }

    public void addCategoriasList(int id) {

        modelListAdd.addElement(modelList.get(id));
        listNew.add((Categoria)modelList.get(id));
        modelList.remove(id);
    }

    public void returnCategoriasList(int id) {
        modelList.addElement(modelListAdd.get(id));
        modelListAdd.remove(id);
    }

    public void zapatoCategoria(List<Categoria> listaCat, Entity.Zapato z) {

        if (ctModel.CategoriaZapato(listaCat, z)) {
            JOptionPane.showMessageDialog(null, "CATEGORIAS AGREGADAS CON EXITO", "CATEGORIAC", 1);
        } else {

            JOptionPane.showMessageDialog(null, "ALGO FALLO", "CATEGORIAC", 0);
        }

    }

    public List<Categoria> getListNew() {
        return listNew;
    }

    
   

}
