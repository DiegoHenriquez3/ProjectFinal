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
public class Talla {

    private int idTalla;
    private String us;
    private String eur;
    private String cm;
    private String uk;
    private List<Zapato> zapatoList;

    public List<Zapato> getZapatoList() {
        return zapatoList;
    }

    public void setZapatoList(List<Zapato> zapatoList) {
        this.zapatoList = zapatoList;
    }
    

    public int getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(int idTalla) {
        this.idTalla = idTalla;
    }

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }

    public String getEur() {
        return eur;
    }

    public void setEur(String eur) {
        this.eur = eur;
    }

    public String getCm() {
        return cm;
    }

    public void setCm(String cm) {
        this.cm = cm;
    }

    public String getUk() {
        return uk;
    }

    public void setUk(String uk) {
        this.uk = uk;
    }

}
