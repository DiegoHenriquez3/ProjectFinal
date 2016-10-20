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
public class Categoria {
   
    private int idCategoria;
    private String categoria;
    private List<Zapato> zapatoList;

    public Categoria() {
    }

    public Categoria(String categoria) {
        this.categoria = categoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
     @Override
    public String toString() {
        return categoria;
    }

    public List<Zapato> getZapatoList() {
        return zapatoList;
    }

    public void setZapatoList(List<Zapato> zapatoList) {
        this.zapatoList = zapatoList;
    }
    
    
    
}

    
    

