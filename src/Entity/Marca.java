/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "MARCA")
@NamedQueries({
    @NamedQuery(name = "Marca.findAll", query = "SELECT m FROM Marca m")})
public class Marca implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MARCA")
    private BigDecimal idMarca;
    @Basic(optional = false)
    @Column(name = "MARCA")
    private String marca;
    @OneToMany(mappedBy = "idMarca")
    private List<Zapato> zapatoList;

    public Marca() {
    }

    public Marca(BigDecimal idMarca) {
        this.idMarca = idMarca;
    }

    public Marca(BigDecimal idMarca, String marca) {
        this.idMarca = idMarca;
        this.marca = marca;
    }

    public BigDecimal getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(BigDecimal idMarca) {
        this.idMarca = idMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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
        hash += (idMarca != null ? idMarca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Marca)) {
            return false;
        }
        Marca other = (Marca) object;
        if ((this.idMarca == null && other.idMarca != null) || (this.idMarca != null && !this.idMarca.equals(other.idMarca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return marca;
    }
    
}
