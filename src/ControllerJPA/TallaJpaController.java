/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import Entity.Talla;
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
public class TallaJpaController implements Serializable {

    public TallaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Talla talla) throws PreexistingEntityException, Exception {
        if (talla.getZapatoList() == null) {
            talla.setZapatoList(new ArrayList<Zapato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Zapato> attachedZapatoList = new ArrayList<Zapato>();
            for (Zapato zapatoListZapatoToAttach : talla.getZapatoList()) {
                zapatoListZapatoToAttach = em.getReference(zapatoListZapatoToAttach.getClass(), zapatoListZapatoToAttach.getIdZapato());
                attachedZapatoList.add(zapatoListZapatoToAttach);
            }
            talla.setZapatoList(attachedZapatoList);
            em.persist(talla);
            for (Zapato zapatoListZapato : talla.getZapatoList()) {
                Talla oldIdTallaOfZapatoListZapato = zapatoListZapato.getIdTalla();
                zapatoListZapato.setIdTalla(talla);
                zapatoListZapato = em.merge(zapatoListZapato);
                if (oldIdTallaOfZapatoListZapato != null) {
                    oldIdTallaOfZapatoListZapato.getZapatoList().remove(zapatoListZapato);
                    oldIdTallaOfZapatoListZapato = em.merge(oldIdTallaOfZapatoListZapato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTalla(talla.getIdTalla()) != null) {
                throw new PreexistingEntityException("Talla " + talla + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Talla talla) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Talla persistentTalla = em.find(Talla.class, talla.getIdTalla());
            List<Zapato> zapatoListOld = persistentTalla.getZapatoList();
            List<Zapato> zapatoListNew = talla.getZapatoList();
            List<Zapato> attachedZapatoListNew = new ArrayList<Zapato>();
            for (Zapato zapatoListNewZapatoToAttach : zapatoListNew) {
                zapatoListNewZapatoToAttach = em.getReference(zapatoListNewZapatoToAttach.getClass(), zapatoListNewZapatoToAttach.getIdZapato());
                attachedZapatoListNew.add(zapatoListNewZapatoToAttach);
            }
            zapatoListNew = attachedZapatoListNew;
            talla.setZapatoList(zapatoListNew);
            talla = em.merge(talla);
            for (Zapato zapatoListOldZapato : zapatoListOld) {
                if (!zapatoListNew.contains(zapatoListOldZapato)) {
                    zapatoListOldZapato.setIdTalla(null);
                    zapatoListOldZapato = em.merge(zapatoListOldZapato);
                }
            }
            for (Zapato zapatoListNewZapato : zapatoListNew) {
                if (!zapatoListOld.contains(zapatoListNewZapato)) {
                    Talla oldIdTallaOfZapatoListNewZapato = zapatoListNewZapato.getIdTalla();
                    zapatoListNewZapato.setIdTalla(talla);
                    zapatoListNewZapato = em.merge(zapatoListNewZapato);
                    if (oldIdTallaOfZapatoListNewZapato != null && !oldIdTallaOfZapatoListNewZapato.equals(talla)) {
                        oldIdTallaOfZapatoListNewZapato.getZapatoList().remove(zapatoListNewZapato);
                        oldIdTallaOfZapatoListNewZapato = em.merge(oldIdTallaOfZapatoListNewZapato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = talla.getIdTalla();
                if (findTalla(id) == null) {
                    throw new NonexistentEntityException("The talla with id " + id + " no longer exists.");
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
            Talla talla;
            try {
                talla = em.getReference(Talla.class, id);
                talla.getIdTalla();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The talla with id " + id + " no longer exists.", enfe);
            }
            List<Zapato> zapatoList = talla.getZapatoList();
            for (Zapato zapatoListZapato : zapatoList) {
                zapatoListZapato.setIdTalla(null);
                zapatoListZapato = em.merge(zapatoListZapato);
            }
            em.remove(talla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Talla> findTallaEntities() {
        return findTallaEntities(true, -1, -1);
    }

    public List<Talla> findTallaEntities(int maxResults, int firstResult) {
        return findTallaEntities(false, maxResults, firstResult);
    }

    private List<Talla> findTallaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Talla.class));
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

    public Talla findTalla(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Talla.class, id);
        } finally {
            em.close();
        }
    }

    public int getTallaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Talla> rt = cq.from(Talla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
