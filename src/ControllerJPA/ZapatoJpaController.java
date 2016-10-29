/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Color;
import Entity.Marca;
import Entity.Talla;
import Entity.Zapato;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Diego
 */
public class ZapatoJpaController implements Serializable {

    public ZapatoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Zapato zapato) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Color idColor = zapato.getIdColor();
            if (idColor != null) {
                idColor = em.getReference(idColor.getClass(), idColor.getIdColor());
                zapato.setIdColor(idColor);
            }
            Marca idMarca = zapato.getIdMarca();
            if (idMarca != null) {
                idMarca = em.getReference(idMarca.getClass(), idMarca.getIdMarca());
                zapato.setIdMarca(idMarca);
            }
            Talla idTalla = zapato.getIdTalla();
            if (idTalla != null) {
                idTalla = em.getReference(idTalla.getClass(), idTalla.getIdTalla());
                zapato.setIdTalla(idTalla);
            }
            em.persist(zapato);
            if (idColor != null) {
                idColor.getZapatoList().add(zapato);
                idColor = em.merge(idColor);
            }
            if (idMarca != null) {
                idMarca.getZapatoList().add(zapato);
                idMarca = em.merge(idMarca);
            }
            if (idTalla != null) {
                idTalla.getZapatoList().add(zapato);
                idTalla = em.merge(idTalla);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findZapato(zapato.getIdZapato()) != null) {
                throw new PreexistingEntityException("Zapato " + zapato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Zapato zapato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zapato persistentZapato = em.find(Zapato.class, zapato.getIdZapato());
            Color idColorOld = persistentZapato.getIdColor();
            Color idColorNew = zapato.getIdColor();
            Marca idMarcaOld = persistentZapato.getIdMarca();
            Marca idMarcaNew = zapato.getIdMarca();
            Talla idTallaOld = persistentZapato.getIdTalla();
            Talla idTallaNew = zapato.getIdTalla();
            if (idColorNew != null) {
                idColorNew = em.getReference(idColorNew.getClass(), idColorNew.getIdColor());
                zapato.setIdColor(idColorNew);
            }
            if (idMarcaNew != null) {
                idMarcaNew = em.getReference(idMarcaNew.getClass(), idMarcaNew.getIdMarca());
                zapato.setIdMarca(idMarcaNew);
            }
            if (idTallaNew != null) {
                idTallaNew = em.getReference(idTallaNew.getClass(), idTallaNew.getIdTalla());
                zapato.setIdTalla(idTallaNew);
            }
            zapato = em.merge(zapato);
            if (idColorOld != null && !idColorOld.equals(idColorNew)) {
                idColorOld.getZapatoList().remove(zapato);
                idColorOld = em.merge(idColorOld);
            }
            if (idColorNew != null && !idColorNew.equals(idColorOld)) {
                idColorNew.getZapatoList().add(zapato);
                idColorNew = em.merge(idColorNew);
            }
            if (idMarcaOld != null && !idMarcaOld.equals(idMarcaNew)) {
                idMarcaOld.getZapatoList().remove(zapato);
                idMarcaOld = em.merge(idMarcaOld);
            }
            if (idMarcaNew != null && !idMarcaNew.equals(idMarcaOld)) {
                idMarcaNew.getZapatoList().add(zapato);
                idMarcaNew = em.merge(idMarcaNew);
            }
            if (idTallaOld != null && !idTallaOld.equals(idTallaNew)) {
                idTallaOld.getZapatoList().remove(zapato);
                idTallaOld = em.merge(idTallaOld);
            }
            if (idTallaNew != null && !idTallaNew.equals(idTallaOld)) {
                idTallaNew.getZapatoList().add(zapato);
                idTallaNew = em.merge(idTallaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = zapato.getIdZapato();
                if (findZapato(id) == null) {
                    throw new NonexistentEntityException("The zapato with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zapato zapato;
            try {
                zapato = em.getReference(Zapato.class, id);
                zapato.getIdZapato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The zapato with id " + id + " no longer exists.", enfe);
            }
            Color idColor = zapato.getIdColor();
            if (idColor != null) {
                idColor.getZapatoList().remove(zapato);
                idColor = em.merge(idColor);
            }
            Marca idMarca = zapato.getIdMarca();
            if (idMarca != null) {
                idMarca.getZapatoList().remove(zapato);
                idMarca = em.merge(idMarca);
            }
            Talla idTalla = zapato.getIdTalla();
            if (idTalla != null) {
                idTalla.getZapatoList().remove(zapato);
                idTalla = em.merge(idTalla);
            }
            em.remove(zapato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Zapato> findZapatoEntities() {
        return findZapatoEntities(true, -1, -1);
    }

    public List<Zapato> findZapatoEntities(int maxResults, int firstResult) {
        return findZapatoEntities(false, maxResults, firstResult);
    }

    private List<Zapato> findZapatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Zapato.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Zapato findZapato(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Zapato.class, id);
        } finally {
            em.close();
        }
    }

    public int getZapatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Zapato> rt = cq.from(Zapato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
