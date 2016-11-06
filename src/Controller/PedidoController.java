/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Bodega;
import Model.Pedido;
import Model.PedidoM;
import Model.Sucursal;
import Model.Zapato;
import Model.ZapatoM;
import View.IngresarPedido;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Mauricio
 */
public class PedidoController {

    private DefaultComboBoxModel modelCombox;
    private ZapatoM zapatoM;
    private PedidoM pedidoM;
    private Pedido pedido;

    public PedidoController() {
        modelCombox = new DefaultComboBoxModel();
        zapatoM = new ZapatoM();
        this.pedido = new Pedido();
        this.pedidoM = new PedidoM();

    }

    public void realizarPedido() {
        /*if (zapatoM.insertarZapatos(pedido)) {
            JOptionPane.showMessageDialog(null, "Pedido Realizado con exito", "Pedido", 1);
        }*/
        if (zapatoM.insertZapatosP(pedido)) {
            JOptionPane.showMessageDialog(null, "Pedido Realizado con exito", "Pedido", 1);
        }
    }

    public void crearPedido(IngresarPedido form, List<Zapato> listZ) {
        pedido.setIdBodega((Bodega) form.getCbBodega().getSelectedItem());
        pedido.setIdSucursal((Sucursal) form.getCbSucursal().getSelectedItem());
        pedido.setEstado(0);
        pedido.setIdEmpleado(form.getUser().getIdEmpleado());
        pedido.setFechaPedido(getFechaPedido());
        pedido.setZapatosList(listZ);
        JOptionPane.showMessageDialog(null, getFechaPedido(), "Pedido", 1);
        if (pedidoM.InsertarPedido(pedido)) {
            if (zapatoM.insertarZapatos(pedido)) {
                JOptionPane.showMessageDialog(null, "Pedido Realizado", "Pedido", 1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pedido Realizado", "Pedido", 0);
        }

    }

    private String getFechaPedido() {
        String dateFinal = "";
        try {
            Date fecha = new Date();
            long miliSegundos = fecha.getTime();
            Calendar cal = Calendar.getInstance();
            String sqlDate = fechaString(cal);
            Time sqlTime = new Time(miliSegundos);
            dateFinal = "TO_DATE('" + sqlDate + " " + sqlTime.toString() + "','DD/MM/YYYY HH24:MI:SS')";

        } catch (Exception e) {

        }
        return dateFinal;
    }

    private String fechaString(Calendar cal) {

        String stringDate = "";

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        if (cal != null) {

            stringDate = formato.format(cal.getTime());

        }
        return stringDate;
    }
}
