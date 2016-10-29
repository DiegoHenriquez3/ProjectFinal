/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "TALLA")
@NamedQueries({
    @NamedQuery(name = "Talla.findAll", query = "SELECT t FROM Talla t")})
public class Talla implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TALLA")
    private BigDecimal idTalla;
    @Basic(optional = false)
    @Column(name = "US")
    private BigInteger us;
    @Column(name = "EUR")
    private BigInteger eur;
    @Column(name = "CM")
    private BigInteger cm;
    @Column(name = "UK")
    private BigInteger uk;
    @OneToMany(mappedBy = "idTalla")
    private List<Zapato> zapatoList;

    public Talla() {
    }

    public Talla(BigDecimal idTalla) {
        this.idTalla = idTalla;
    }

    public Talla(BigDecimal idTalla, BigInteger us) {
        this.idTalla = idTalla;
        this.us = us;
    }

    public BigDecimal getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(BigDecimal idTalla) {
        this.idTalla = idTalla;
    }

    public BigInteger getUs() {
        return us;
    }

    public void setUs(BigInteger us) {
        this.us = us;
    }

    public BigInteger getEur() {
        return eur;
    }

    public void setEur(BigInteger eur) {
        this.eur = eur;
    }

    public BigInteger getCm() {
        return cm;
    }

    public void setCm(BigInteger cm) {
        this.cm = cm;
    }

    public BigInteger getUk() {
        return uk;
    }

    public void setUk(BigInteger uk) {
        this.uk = uk;
    }

    public List<Zapato> getZapatoList() {
        return zapatoList;
    }

    public void setZapatoList(List<Zapato> zapatoList) {
        this.zapatoList = zapatoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTalla != null ? idTalla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Talla)) {
            return false;
        }
        Talla other = (Talla) object;
        if ((this.idTalla == null && other.idTalla != null) || (this.idTalla != null && !this.idTalla.equals(other.idTalla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(us);
    }
    
}
