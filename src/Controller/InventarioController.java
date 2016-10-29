/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InventarioM;
import Model.Zapato;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Diego
 */
public class InventarioController {

    private InventarioM invenM;
    private List<Zapato> listZapato;
    private DefaultTableModel modelTable;

    public InventarioController() {
        invenM = new InventarioM();

        listZapato = new ArrayList<>();

        modelTable = new DefaultTableModel();

    }
    
    
    public void llenarTable(JTable table) throws SQLException{
        
        modelTable.addColumn("NOMBRE");
        modelTable.addColumn("MARCA");
        modelTable.addColumn("COLOR");
        modelTable.addColumn("TALLA");
        modelTable.addColumn("EXISTENCIAS");
        modelTable.addColumn("PRECIO U");
        modelTable.addColumn("PRECIO_TOTAL");
        table.setModel(modelTable);
        
        listZapato=invenM.listaZapatos();
        Object[] valores;
        double precioT=0;
        for (Zapato zapato : listZapato) {
            valores = new Object[8];
            valores[0] = zapato.getNombre();
            valores[1] = zapato.getIdMarca().getMarca();
            valores[2] = zapato.getColor().getColor();
            valores[3] = zapato.getIdTalla().getUs();
            valores[4] = zapato.getCantidad();
            valores[5] = zapato.getPrecio();
            precioT= (zapato.getPrecio()*zapato.getCantidad());
            valores[6] = precioT;
            modelTable.addRow(valores);
        }
        
       
    
    }
    
    

}
