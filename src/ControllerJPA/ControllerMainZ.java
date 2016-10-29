/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import Entity.Categoria;
import Entity.Color;
import Entity.EntityMain;
import Entity.Marca;
import Entity.Talla;
import View.IngresarZapato;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author BlackHat
 */
public class ControllerMainZ {

    private DefaultComboBoxModel modelTalla;
    private DefaultComboBoxModel modelMarca;
    private DefaultComboBoxModel modelColor;
    private DefaultComboBoxModel modelCate;

    public ControllerMainZ() {

        modelTalla = new DefaultComboBoxModel();
        modelMarca = new DefaultComboBoxModel();
        modelColor = new DefaultComboBoxModel();
        modelCate = new DefaultComboBoxModel();
    }

    public void llenarCombo(IngresarZapato form) {
        //cbTalla.setModel(modelCombox);

        List<Talla> tallList;
        List<Marca> marcaList;
        List<Color> colorList;
        List<Categoria>catList;
        
        try {
            TallaJpaController tallaC = new TallaJpaController(EntityMain.getEntity());
            MarcaJpaController marcaC = new MarcaJpaController(EntityMain.getEntity());
            ColorJpaController colorC = new ColorJpaController(EntityMain.getEntity());
            CategoriaJpaController catC= new CategoriaJpaController(EntityMain.getEntity());
            
            tallList = tallaC.findTallaEntities();
            marcaList = marcaC.findMarcaEntities();
            colorList = colorC.findColorEntities();
            catList= catC.findCategoriaEntities();
            
            tallList.stream().forEach((t) -> {
                modelTalla.addElement(t);
            });

            marcaList.stream().forEach((marca) -> {
                modelMarca.addElement(marca);
            });
            
            colorList.stream().forEach((color) -> {
                modelColor.addElement(color);
            }); 
            
            catList.stream().forEach((categoria) -> {
                modelCate.addElement(categoria);
            });
            
            form.getCbTalla().setModel(modelTalla);
            form.getCbColor().setModel(modelColor);
            form.getCbMarca().setModel(modelMarca);
            form.getCbCategoria().setModel(modelCate);
            
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        }

    }

}
