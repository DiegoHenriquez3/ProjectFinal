/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Diego
 */
public class Empleado {
      
    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String dui;
    private String nit;
    private String direccion;
    private String telefono;
    private Bodega idBodega;
    private String MunicipioTrabajo;
    

    public String getMunicipioTrabajo() {
        return MunicipioTrabajo;
    }

    public void setMunicipioTrabajo(String MunicipioTrabajo) {
        this.MunicipioTrabajo = MunicipioTrabajo;
    }

    public Empleado() {
        
       
    }

     public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    public Empleado(String nombre, String apellido, String dui, String nit, String direccion, String telefono, Bodega idBodega) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dui = dui;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.idBodega = idBodega;
    }
    
    

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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

    public Bodega getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Bodega idBodega) {
        this.idBodega = idBodega;
    }

 
  
    
    
}
