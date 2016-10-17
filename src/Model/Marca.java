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
public class Marca {
  
    private int idMarca; 
    private String marca;
    private List<Zapato> zapatoList;

    public List<Zapato> getZapatoList() {
        return zapatoList;
    }

    public void setZapatoList(List<Zapato> zapatoList) {
        this.zapatoList = zapatoList;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

   





}
