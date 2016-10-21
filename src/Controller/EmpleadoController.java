/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Bodega;
import Model.Empleado;
import Model.EmpleadoM;
import View.IngresarEmpleados;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Mauricio
 */
public class EmpleadoController {

    private EmpleadoM empModel;
    private Empleado alguien;
    //private final List<String> mensajes;
    private Bodega bodega;
    private DefaultTableModel modelTable;
    

    public EmpleadoController() {
        alguien = new Empleado();
        empModel = new EmpleadoM();
        bodega = new Bodega();
        modelTable = new DefaultTableModel();
        /*0 mal,1 drop,2 insert,3 carga;

        mensajes = new ArrayList<>();
        mensajes.add("Algo Fallo:");
        mensajes.add("Datos Eliminados con exito");
        mensajes.add("Datos Inseratado con exito");
        mensajes.add("Datos Cargados con exito");*/

    }

    public void insertarEmpleado(String nombre, String apellido, String dui, String nit, String dir, String tel, int id_bodega) {

        alguien.setNombre(nombre);
        alguien.setApellido(apellido);
        alguien.setDui(dui);
        alguien.setNit(nit);
        alguien.setDireccion(dir);
        alguien.setTelefono(tel);
        bodega.setIdBodega(id_bodega);
        alguien.setIdBodega(bodega);

        if(empModel.insertarEmpleado(alguien)){
            Object[] valores ;
           
            valores=new Object[8];
            valores[0]=nombre;
            valores[1]=apellido;
            valores[2]=dui;
            valores[3]=nit;
            valores[4]=dir;
            valores[5]=tel;
            valores[6]=id_bodega;
            modelTable.addRow(valores);
         
        }

        alguien = new Empleado();
        empModel = new EmpleadoM();
        bodega = new Bodega();

    }

    public void eliminarEmpleado(int id_empleado) {

        alguien.setIdEmpleado(id_empleado);
        if (empModel.eliminarEmpleado(alguien)) {
            JOptionPane.showMessageDialog(null, "Eli", "Eliminado", 1);

        } else {

        }

    }

    public void actualizarEmpleado(String nombre, String apelldio, String dui, String nit, String dir, String tel, int id_bodega) {

        alguien.setNombre(nombre);
        alguien.setApellido(apelldio);
        alguien.setDui(dui);
        alguien.setNit(nit);
        alguien.setDireccion(dir);
        alguien.setTelefono(tel);
        bodega.setIdBodega(id_bodega);
        alguien.setIdBodega(bodega);

        if (empModel.actualizarEmpleado(alguien)) {

        } else {

        }
        bodega = new Bodega();
        alguien = new Empleado();
        empModel = new EmpleadoM();
    }

    public ArrayList<Empleado> listaEmpleados() {

        ArrayList<Empleado> listaEmp = new ArrayList<>();
        try {
            listaEmp = empModel.listarEmpleados();
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        empModel= null;

        return listaEmp;

    }

    public void llenarJtable(JTable tabla) {
       //Configurar Tabla y modelTable
        tabla.setModel(modelTable);
       // modelTable.addColumn("ID");
        modelTable.addColumn("NOMBRE");
        modelTable.addColumn("APELLIDO");
        modelTable.addColumn("DUI");
        modelTable.addColumn("NIT");
        modelTable.addColumn("DIRECCION");
        modelTable.addColumn("TELEFONO");
        modelTable.addColumn("BODEGA");
        
        
        ArrayList<Empleado> listaEmp = new ArrayList<>();
        try {
            listaEmp = empModel.listarEmpleados();
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Object[] valores;
     

        for (Empleado emp : listaEmp) {
         
            valores=new Object[8];
            //valores[0]=emp.getIdEmpleado();
            valores[0]=emp.getNombre();
            valores[1]=emp.getApellido();
            valores[2]=emp.getDui();
            valores[3]=emp.getNit();
            valores[4]=emp.getDireccion();
            valores[5]=emp.getTelefono();
            valores[6]=emp.getIdBodega().getNombre();
            modelTable.addRow(valores);
           
            
        }
        

    }
    
    public static void filtroEmpleado(String var){
        IngresarEmpleados form= new IngresarEmpleados();
        TableRowSorter tableFil= new TableRowSorter();
        int columTosearch=0;
        String itemSelec = "";
        itemSelec=form.getCbFindBy().getSelectedItem().toString();
        
        if (itemSelec.equals("DUI")){
         columTosearch=3;
        }
        else if (itemSelec.equals("Nombre")){
         columTosearch=1;
        }
        else if (itemSelec.equals("Apellido")){
         columTosearch=2;
        }
        tableFil.setRowFilter(RowFilter.regexFilter(var,columTosearch));
     
    }
    
    /**
     *
     */
    private TableRowSorter tableFil= new TableRowSorter();
    public void reTable(IngresarEmpleados form){
       
       tableFil = new TableRowSorter(form.getTbEmpleados().getModel());
       form.getTbEmpleados().setRowSorter(tableFil);
    }

}
