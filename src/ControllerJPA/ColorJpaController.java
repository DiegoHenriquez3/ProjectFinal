/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import Entity.Color;
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
public class ColorJpaController implements Serializable {

    public ColorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Color color) throws PreexistingEntityException, Exception {
        if (color.getZapatoList() == null) {
            color.setZapatoList(new ArrayList<Zapato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Zapato> attachedZapatoList = new ArrayList<Zapato>();
            for (Zapato zapatoListZapatoToAttach : color.getZapatoList()) {
                zapatoListZapatoToAttach = em.getReference(zapatoListZapatoToAttach.getClass(), zapatoListZapatoToAttach.getIdZapato());
                attachedZapatoList.add(zapatoListZapatoToAttach);
            }
            color.setZapatoList(attachedZapatoList);
            em.persist(color);
            for (Zapato zapatoListZapato : color.getZapatoList()) {
                Color oldIdColorOfZapatoListZapato = zapatoListZapato.getIdColor();
                zapatoListZapato.setIdColor(color);
                zapatoListZapato = em.merge(zapatoListZapato);
                if (oldIdColorOfZapatoListZapato != null) {
                    oldIdColorOfZapatoListZapato.getZapatoList().remove(zapatoListZapato);
                    oldIdColorOfZapatoListZapato = em.merge(oldIdColorOfZapatoListZapato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findColor(color.getIdColor()) != null) {
                throw new PreexistingEntityException("Color " + color + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
           
        }
    }

    public void edit(Color color) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Color persistentColor = em.find(Color.class, color.getIdColor());
            List<Zapato> zapatoListOld = persistentColor.getZapatoList();
            List<Zapato> zapatoListNew = color.getZapatoList();
            List<Zapato> attachedZapatoListNew = new ArrayList<Zapato>();
            for (Zapato zapatoListNewZapatoToAttach : zapatoListNew) {
                zapatoListNewZapatoToAttach = em.getReference(zapatoListNewZapatoToAttach.getClass(), zapatoListNewZapatoToAttach.getIdZapato());
                attachedZapatoListNew.add(zapatoListNewZapatoToAttach);
            }
            zapatoListNew = attachedZapatoListNew;
            color.setZapatoList(zapatoListNew);
            color = em.merge(color);
            for (Zapato zapatoListOldZapato : zapatoListOld) {
                if (!zapatoListNew.contains(zapatoListOldZapato)) {
                    zapatoListOldZapato.setIdColor(null);
                    zapatoListOldZapato = em.merge(zapatoListOldZapato);
                }
            }
            for (Zapato zapatoListNewZapato : zapatoListNew) {
                if (!zapatoListOld.contains(zapatoListNewZapato)) {
                    Color oldIdColorOfZapatoListNewZapato = zapatoListNewZapato.getIdColor();
                    zapatoListNewZapato.setIdColor(color);
                    zapatoListNewZapato = em.merge(zapatoListNewZapato);
                    if (oldIdColorOfZapatoListNewZapato != null && !oldIdColorOfZapatoListNewZapato.equals(color)) {
                        oldIdColorOfZapatoListNewZapato.getZapatoList().remove(zapatoListNewZapato);
                        oldIdColorOfZapatoListNewZapato = em.merge(oldIdColorOfZapatoListNewZapato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = color.getIdColor();
                if (findColor(id) == null) {
                    throw new NonexistentEntityException("The color with id " + id + " no longer exists.");
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
            Color color;
            try {
                color = em.getReference(Color.class, id);
                color.getIdColor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The color with id " + id + " no longer exists.", enfe);
            }
            List<Zapato> zapatoList = color.getZapatoList();
            for (Zapato zapatoListZapato : zapatoList) {
                zapatoListZapato.setIdColor(null);
                zapatoListZapato = em.merge(zapatoListZapato);
            }
            em.remove(color);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Color> findColorEntities() {
        return findColorEntities(true, -1, -1);
    }

    public List<Color> findColorEntities(int maxResults, int firstResult) {
        return findColorEntities(false, maxResults, firstResult);
    }

    private List<Color> findColorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Color.class));
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

    public Color findColor(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Color.class, id);
        } finally {
            em.close();
        }
    }

    public int getColorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Color> rt = cq.from(Color.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
