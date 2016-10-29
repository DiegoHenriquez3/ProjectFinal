/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import Entity.Marca;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Zapato;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Diego
 */
public class MarcaJpaController implements Serializable {

    public MarcaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marca marca) throws PreexistingEntityException, Exception {
        if (marca.getZapatoList() == null) {
            marca.setZapatoList(new ArrayList<Zapato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Zapato> attachedZapatoList = new ArrayList<Zapato>();
            for (Zapato zapatoListZapatoToAttach : marca.getZapatoList()) {
                zapatoListZapatoToAttach = em.getReference(zapatoListZapatoToAttach.getClass(), zapatoListZapatoToAttach.getIdZapato());
                attachedZapatoList.add(zapatoListZapatoToAttach);
            }
            marca.setZapatoList(attachedZapatoList);
            em.persist(marca);
            for (Zapato zapatoListZapato : marca.getZapatoList()) {
                Marca oldIdMarcaOfZapatoListZapato = zapatoListZapato.getIdMarca();
                zapatoListZapato.setIdMarca(marca);
                zapatoListZapato = em.merge(zapatoListZapato);
                if (oldIdMarcaOfZapatoListZapato != null) {
                    oldIdMarcaOfZapatoListZapato.getZapatoList().remove(zapatoListZapato);
                    oldIdMarcaOfZapatoListZapato = em.merge(oldIdMarcaOfZapatoListZapato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMarca(marca.getIdMarca()) != null) {
                throw new PreexistingEntityException("Marca " + marca + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marca marca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca persistentMarca = em.find(Marca.class, marca.getIdMarca());
            List<Zapato> zapatoListOld = persistentMarca.getZapatoList();
            List<Zapato> zapatoListNew = marca.getZapatoList();
            List<Zapato> attachedZapatoListNew = new ArrayList<Zapato>();
            for (Zapato zapatoListNewZapatoToAttach : zapatoListNew) {
                zapatoListNewZapatoToAttach = em.getReference(zapatoListNewZapatoToAttach.getClass(), zapatoListNewZapatoToAttach.getIdZapato());
                attachedZapatoListNew.add(zapatoListNewZapatoToAttach);
            }
            zapatoListNew = attachedZapatoListNew;
            marca.setZapatoList(zapatoListNew);
            marca = em.merge(marca);
            for (Zapato zapatoListOldZapato : zapatoListOld) {
                if (!zapatoListNew.contains(zapatoListOldZapato)) {
                    zapatoListOldZapato.setIdMarca(null);
                    zapatoListOldZapato = em.merge(zapatoListOldZapato);
                }
            }
            for (Zapato zapatoListNewZapato : zapatoListNew) {
                if (!zapatoListOld.contains(zapatoListNewZapato)) {
                    Marca oldIdMarcaOfZapatoListNewZapato = zapatoListNewZapato.getIdMarca();
                    zapatoListNewZapato.setIdMarca(marca);
                    zapatoListNewZapato = em.merge(zapatoListNewZapato);
                    if (oldIdMarcaOfZapatoListNewZapato != null && !oldIdMarcaOfZapatoListNewZapato.equals(marca)) {
                        oldIdMarcaOfZapatoListNewZapato.getZapatoList().remove(zapatoListNewZapato);
                        oldIdMarcaOfZapatoListNewZapato = em.merge(oldIdMarcaOfZapatoListNewZapato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = marca.getIdMarca();
                if (findMarca(id) == null) {
                    throw new NonexistentEntityException("The marca with id " + id + " no longer exists.");
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
            Marca marca;
            try {
                marca = em.getReference(Marca.class, id);
                marca.getIdMarca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
            }
            List<Zapato> zapatoList = marca.getZapatoList();
            for (Zapato zapatoListZapato : zapatoList) {
                zapatoListZapato.setIdMarca(null);
                zapatoListZapato = em.merge(zapatoListZapato);
            }
            em.remove(marca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marca> findMarcaEntities() {
        return findMarcaEntities(true, -1, -1);
    }

    public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
        return findMarcaEntities(false, maxResults, firstResult);
    }

    private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marca.class));
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

    public Marca findMarca(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marca.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marca> rt = cq.from(Marca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
