/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Pedido;
import Model.Empleado;
import java.util.List;

/**
 *
 * @author Diego
 */
public class Bodega {

    private int idBodega;
    private String direccion;
    private String telefono;
    private Municipio idMunicipio;
    private List<Pedido> pedidoList;
    private List<Empleado> empleadoList;
    private List<Zapato> zapatosList;
    private String nombre;

    public Bodega() {

    }

    public Bodega(int idBodega, String direccion, String telefono, Municipio idMunicipio) {
        this.idBodega = idBodega;
        this.direccion = direccion;
        this.telefono = telefono;
        this.idMunicipio = idMunicipio;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Municipio getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Municipio idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    public List<Zapato> getZapatosList() {
        return zapatosList;
    }

    public void setZapatosList(List<Zapato> zapatosList) {
        this.zapatosList = zapatosList;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
