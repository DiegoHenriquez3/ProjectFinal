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
import Entity.Zapato;
import View.ZapatoCategoria;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
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
    private DefaultComboBoxModel modelDepar;
    private DefaultComboBoxModel modelMunic;
    // private DefaultListModel modelList;
    private DefaultTableModel modelTable;
    private DefaultTableModel modelTableZ;
    private JComboBox dinamiCombo;
    private JComboBox masterCombo;
    private JTable table;
    private JTable tableZapato;
    private List<Zapato> listZ;

    public ControllerMainZ() {

        modelTalla = new DefaultComboBoxModel();
        modelMarca = new DefaultComboBoxModel();
        modelColor = new DefaultComboBoxModel();
        modelDepar = new DefaultComboBoxModel();
        modelMunic = new DefaultComboBoxModel();
        modelTable = new DefaultTableModel();
        // modelList = new DefaultListModel();
        dinamiCombo = new JComboBox();
        masterCombo = new JComboBox();
        modelTableZ = new DefaultTableModel();
        table = new JTable();
        tableZapato = new JTable();
        listZ = new ArrayList<>();

    }

    public void fillCbCategoria(ZapatoCategoria form) {
        List<Categoria> catList = new ArrayList<>();
        try {
            CategoriaJpaController catC = new CategoriaJpaController(EntityMain.getEntity());
            catList = catC.findCategoriaEntities();
            int y = 50;
            for (Categoria categoria : catList) {
                JCheckBox jc = new JCheckBox();
                jc.setName(categoria.getIdCategoria().toString());
                jc.setText(categoria.toString());
                jc.setSize(100, 30);
                jc.setLocation(50, y);
                jc.setVisible(true);
                form.add(jc);
                y += 30;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorC", 0);
        }
    }

    public void fillCbColor(JComboBox combo) {
        List<Color> colorList = new ArrayList<>();
        combo.setModel(modelColor);
        try {
            ColorJpaController colorC = new ColorJpaController(EntityMain.getEntity());
            colorList = colorC.findColorEntities();
            colorList.stream().forEach((color) -> {
                modelColor.addElement(color);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorC", 0);
        }
    }

    public void fillCbMarca(JComboBox combo) {
        List<Marca> marcaList = new ArrayList<>();
        combo.setModel(modelMarca);
        try {
            MarcaJpaController marcaC = new MarcaJpaController(EntityMain.getEntity());
            marcaList = marcaC.findMarcaEntities();
            marcaList.stream().forEach((marca) -> {
                modelMarca.addElement(marca);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorC", 0);
        }
    }

    public void fillCbTalla(JComboBox combo) {
        List<Talla> tallList = new ArrayList<>();
        combo.setModel(modelTalla);
        try {
            TallaJpaController tallaC = new TallaJpaController(EntityMain.getEntity());
            tallList = tallaC.findTallaEntities();
            tallList.stream().forEach((t) -> {
                modelTalla.addElement(t);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorC", 0);
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

    public void linkTableZapato(JTable tablaZ) {
        tableZapato = tablaZ;
        try {
            ZapatoJpaController zapatoC = new ZapatoJpaController(EntityMain.getEntity());
            listZ = zapatoC.findZapatoEntities();

        } catch (Exception e) {

        }
        fillTablaZapato(listZ);

    }

    private void orderByTalla() {

        listZ.sort(new Comparator<Zapato>() {
            @Override
            public int compare(Zapato o1, Zapato o2) {
                return o1.getIdTalla().toString().compareTo(o2.getIdTalla().toString());
            }
        });

        fillTablaZapato(listZ);

    }

    private void orderByNombre() {

        listZ.sort(new Comparator<Zapato>() {
            @Override
            public int compare(Zapato o1, Zapato o2) {
                return o1.getZapato().compareTo(o2.getZapato());
            }
        });

        fillTablaZapato(listZ);

    }

    private void orderByPrecio() {

        listZ.sort(new Comparator<Zapato>() {
            @Override
            public int compare(Zapato o1, Zapato o2) {
                return o1.getPrecio().toString().compareTo(o2.getPrecio().toString());
            }
        });
        fillTablaZapato(listZ);
    }

    private void fillTablaZapato(List<Zapato> list) {
        try {
            modelTableZ = new DefaultTableModel();
            tableZapato.setModel(modelTableZ);
            modelTableZ.addColumn("NOMBRE");
            modelTableZ.addColumn("MARCA");
            modelTableZ.addColumn("COLOR");
            modelTableZ.addColumn("TALLA");
            modelTableZ.addColumn("PRECIO U");
            Object[] valores;

            for (Zapato zapato : list) {
                valores = new Object[5];
                valores[0] = zapato.getZapato();
                valores[1] = zapato.getIdMarca().getMarca();
                valores[2] = zapato.getIdColor().getColor();
                valores[3] = zapato.getIdTalla().getUs();
                valores[4] = zapato.getPrecio();
                modelTableZ.addRow(valores);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ErrorC", 0);
        }

    }

    public void linkComboOrder(JComboBox combOrder) {
        DefaultComboBoxModel modelOrder = new DefaultComboBoxModel();
        modelOrder.addElement("NOMBRE");
        modelOrder.addElement("TALLA");
        modelOrder.addElement("PRECIO");
        combOrder.setModel(modelOrder);

        changeComboOrder(combOrder);

    }

    private void changeComboOrder(JComboBox combo) {

        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valor = combo.getSelectedItem().toString();
                if (valor.equals("NOMBRE")) {
                    orderByNombre();

                } else if (valor.equals("TALLA")) {
                    orderByTalla();
                } else if (valor.equals("PRECIO")) {
                    orderByPrecio();
                }
            }
        });

    }

    public List<Zapato> getListZ() {
        return listZ;
    }
    

}
