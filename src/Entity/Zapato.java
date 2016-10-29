/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "ZAPATO")
@NamedQueries({
    @NamedQuery(name = "Zapato.findAll", query = "SELECT z FROM Zapato z")})
public class Zapato implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "ZAPATO_SEQ")
    @SequenceGenerator(name="ZAPATO_SEQ",sequenceName = "ZAPATO_SEQ")
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ZAPATO")
    private BigDecimal idZapato;
    @Column(name = "ZAPATO")
    private String zapato;
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @JoinColumn(name = "ID_COLOR", referencedColumnName = "ID_COLOR")
    @ManyToOne
    private Color idColor;
    @JoinColumn(name = "ID_MARCA", referencedColumnName = "ID_MARCA")
    @ManyToOne
    private Marca idMarca;
    @JoinColumn(name = "ID_TALLA", referencedColumnName = "ID_TALLA")
    @ManyToOne
    private Talla idTalla;

    public Zapato() {
    }

    public Zapato(BigDecimal idZapato) {
        this.idZapato = idZapato;
    }

    public BigDecimal getIdZapato() {
        return idZapato;
    }

    public void setIdZapato(BigDecimal idZapato) {
        this.idZapato = idZapato;
    }

    public String getZapato() {
        return zapato;
    }

    public void setZapato(String zapato) {
        this.zapato = zapato;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Color getIdColor() {
        return idColor;
    }

    public void setIdColor(Color idColor) {
        this.idColor = idColor;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZapato != null ? idZapato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zapato)) {
            return false;
        }
        Zapato other = (Zapato) object;
        if ((this.idZapato == null && other.idZapato != null) || (this.idZapato != null && !this.idZapato.equals(other.idZapato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return zapato;
    }
    
}
