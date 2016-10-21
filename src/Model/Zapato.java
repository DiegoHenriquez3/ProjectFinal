/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.List;

/**
 *
 * @author Diego
 */
public class Zapato {

 
    private int idZapato;
    private String nombre;
    private double precio;
    private Color color;
    private Categoria categoria; 
    private Marca idMarca;
    private Talla idTalla;
    private int cantidad;
    

    public int getIdZapato() {
        return idZapato;
    }

    public void setIdZapato(int idZapato) {
        this.idZapato = idZapato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
   
    public Marca getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Marca idMarca) {
        this.idMarca = idMarca;
    }

    public Talla getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(Talla idTalla) {
        this.idTalla = idTalla;
    }
 

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    @Override
    public String toString(){
     
        return nombre;
    
    }
    
    
    
}
