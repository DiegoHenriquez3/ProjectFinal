/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import Entity.Categoria;
import Entity.Color;
import Entity.Departamento;
import Entity.EntityMain;
import Entity.Marca;
import Entity.Municipio;
import Entity.Sucursal;
import Entity.Talla;
import View.IngresarZapato;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BlackHat
 */
public class ControllerMainZ {

    private DefaultComboBoxModel modelTalla;
    private DefaultComboBoxModel modelMarca;
    private DefaultComboBoxModel modelColor;
    private DefaultComboBoxModel modelCate;
    private DefaultComboBoxModel modelDepar;
    private DefaultComboBoxModel modelMunic;
    private DefaultTableModel modelTable;
    private JComboBox dinamiCombo;
    private JComboBox masterCombo;
    private JTable table;

    public ControllerMainZ() {

        modelTalla = new DefaultComboBoxModel();
        modelMarca = new DefaultComboBoxModel();
        modelColor = new DefaultComboBoxModel();
        modelCate = new DefaultComboBoxModel();
        modelDepar = new DefaultComboBoxModel();
        modelMunic = new DefaultComboBoxModel();
        modelTable= new DefaultTableModel();
        dinamiCombo = new JComboBox();
        masterCombo = new JComboBox();
        table = new JTable();

    }

    public void llenarCombo(IngresarZapato form) {
        //cbTalla.setModel(modelCombox);

        List<Talla> tallList;
        List<Marca> marcaList;
        List<Color> colorList;
        List<Categoria> catList;

        try {
            TallaJpaController tallaC = new TallaJpaController(EntityMain.getEntity());
            MarcaJpaController marcaC = new MarcaJpaController(EntityMain.getEntity());
            ColorJpaController colorC = new ColorJpaController(EntityMain.getEntity());
            CategoriaJpaController catC = new CategoriaJpaController(EntityMain.getEntity());

            tallList = tallaC.findTallaEntities();
            marcaList = marcaC.findMarcaEntities();
            colorList = colorC.findColorEntities();
            catList = catC.findCategoriaEntities();

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

    public void linkCombo(JComboBox comboSlave, JComboBox comboMaster) {
        this.dinamiCombo = comboSlave;
        dinamiCombo.setModel(modelMunic);
        dinamiCombo.setEnabled(false);

        this.masterCombo = comboMaster;
        changeCombo(masterCombo);
        getDepartsForCb();

    }

    private void getDepartsForCb() {

        this.masterCombo.setModel(modelDepar);
        try {
            DepartamentoJpaController departamentoC = new DepartamentoJpaController(EntityMain.getEntity());
            List<Departamento> listD = new ArrayList<>();

            listD = departamentoC.findDepartamentoEntities();

            listD.sort(new Comparator<Departamento>() {
                @Override
                public int compare(Departamento d1, Departamento d2) {
                    return d1.getDepartamento().compareTo(d2.getDepartamento());
                }
            });

            for (Departamento d : listD) {
                modelDepar.addElement(d);
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorC", 0);
        }
    }

    private void getMuniciForCb(String d) {

        try {
            MunicipioJpaController muniC = new MunicipioJpaController(EntityMain.getEntity());
            List<Municipio> listD = new ArrayList<>();

            listD = muniC.findMunicipioEntities();

            for (Municipio m : listD) {
                if (m.getIdDepartamento().getDepartamento().equals(d)) {
                    modelMunic.addElement(m);
                    dinamiCombo.setEnabled(true);
                } else {
                    modelMunic.removeAllElements();
                    dinamiCombo.setEnabled(false);
                }

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorC", 0);
        }

    }

    private void changeCombo(JComboBox combo) {

        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String departamento = combo.getSelectedItem().toString();
                getMuniciForCb(departamento);
            }
        });

    }

    public void linkTable(JTable tab) {
        
        this.table = tab;
        fillTable();
        
    }

    private void fillTable() {
         
        table.setModel(modelTable);
        modelTable.addColumn("NOMBRE");
        modelTable.addColumn("DIRECCION");
        modelTable.addColumn("TELEFONO");
        modelTable.addColumn("MUNICIPIO");

        SucursalJpaController susJpa = new SucursalJpaController(EntityMain.getEntity());
        List<Sucursal> listaS = new ArrayList<>();

        listaS = susJpa.findSucursalEntities();

        Object[] valores;
        for (Sucursal s : listaS) {
            valores = new Object[4];
            valores[0] = s.getNombre();
            valores[1] = s.getDireccion();
            valores[2] = s.getTelefono();
            valores[3] = s.getIdMunicipio().getMunicipio();
            modelTable.addRow(valores);

        }
    }

}
