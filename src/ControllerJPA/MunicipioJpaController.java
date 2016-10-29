/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.IllegalOrphanException;
import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Departamento;
import Entity.Bodega;
import Entity.Municipio;
import java.util.ArrayList;
import java.util.List;
import Entity.Sucursal;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Diego
 */
public class MunicipioJpaController implements Serializable {

    public MunicipioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipio municipio) throws PreexistingEntityException, Exception {
        if (municipio.getBodegaList() == null) {
            municipio.setBodegaList(new ArrayList<Bodega>());
        }
        if (municipio.getSucursalList() == null) {
            municipio.setSucursalList(new ArrayList<Sucursal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento idDepartamento = municipio.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento = em.getReference(idDepartamento.getClass(), idDepartamento.getIdDepartamento());
                municipio.setIdDepartamento(idDepartamento);
            }
            List<Bodega> attachedBodegaList = new ArrayList<Bodega>();
            for (Bodega bodegaListBodegaToAttach : municipio.getBodegaList()) {
                bodegaListBodegaToAttach = em.getReference(bodegaListBodegaToAttach.getClass(), bodegaListBodegaToAttach.getIdBodega());
                attachedBodegaList.add(bodegaListBodegaToAttach);
            }
            municipio.setBodegaList(attachedBodegaList);
            List<Sucursal> attachedSucursalList = new ArrayList<Sucursal>();
            for (Sucursal sucursalListSucursalToAttach : municipio.getSucursalList()) {
                sucursalListSucursalToAttach = em.getReference(sucursalListSucursalToAttach.getClass(), sucursalListSucursalToAttach.getIdSucursal());
                attachedSucursalList.add(sucursalListSucursalToAttach);
            }
            municipio.setSucursalList(attachedSucursalList);
            em.persist(municipio);
            if (idDepartamento != null) {
                idDepartamento.getMunicipioList().add(municipio);
                idDepartamento = em.merge(idDepartamento);
            }
            for (Bodega bodegaListBodega : municipio.getBodegaList()) {
                Municipio oldIdMunicipioOfBodegaListBodega = bodegaListBodega.getIdMunicipio();
                bodegaListBodega.setIdMunicipio(municipio);
                bodegaListBodega = em.merge(bodegaListBodega);
                if (oldIdMunicipioOfBodegaListBodega != null) {
                    oldIdMunicipioOfBodegaListBodega.getBodegaList().remove(bodegaListBodega);
                    oldIdMunicipioOfBodegaListBodega = em.merge(oldIdMunicipioOfBodegaListBodega);
                }
            }
            for (Sucursal sucursalListSucursal : municipio.getSucursalList()) {
                Municipio oldIdMunicipioOfSucursalListSucursal = sucursalListSucursal.getIdMunicipio();
                sucursalListSucursal.setIdMunicipio(municipio);
                sucursalListSucursal = em.merge(sucursalListSucursal);
                if (oldIdMunicipioOfSucursalListSucursal != null) {
                    oldIdMunicipioOfSucursalListSucursal.getSucursalList().remove(sucursalListSucursal);
                    oldIdMunicipioOfSucursalListSucursal = em.merge(oldIdMunicipioOfSucursalListSucursal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMunicipio(municipio.getIdMunicipio()) != null) {
                throw new PreexistingEntityException("Municipio " + municipio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipio municipio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio persistentMunicipio = em.find(Municipio.class, municipio.getIdMunicipio());
            Departamento idDepartamentoOld = persistentMunicipio.getIdDepartamento();
            Departamento idDepartamentoNew = municipio.getIdDepartamento();
            List<Bodega> bodegaListOld = persistentMunicipio.getBodegaList();
            List<Bodega> bodegaListNew = municipio.getBodegaList();
            List<Sucursal> sucursalListOld = persistentMunicipio.getSucursalList();
            List<Sucursal> sucursalListNew = municipio.getSucursalList();
            List<String> illegalOrphanMessages = null;
            for (Bodega bodegaListOldBodega : bodegaListOld) {
                if (!bodegaListNew.contains(bodegaListOldBodega)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bodega " + bodegaListOldBodega + " since its idMunicipio field is not nullable.");
                }
            }
            for (Sucursal sucursalListOldSucursal : sucursalListOld) {
                if (!sucursalListNew.contains(sucursalListOldSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sucursal " + sucursalListOldSucursal + " since its idMunicipio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDepartamentoNew != null) {
                idDepartamentoNew = em.getReference(idDepartamentoNew.getClass(), idDepartamentoNew.getIdDepartamento());
                municipio.setIdDepartamento(idDepartamentoNew);
            }
            List<Bodega> attachedBodegaListNew = new ArrayList<Bodega>();
            for (Bodega bodegaListNewBodegaToAttach : bodegaListNew) {
                bodegaListNewBodegaToAttach = em.getReference(bodegaListNewBodegaToAttach.getClass(), bodegaListNewBodegaToAttach.getIdBodega());
                attachedBodegaListNew.add(bodegaListNewBodegaToAttach);
            }
            bodegaListNew = attachedBodegaListNew;
            municipio.setBodegaList(bodegaListNew);
            List<Sucursal> attachedSucursalListNew = new ArrayList<Sucursal>();
            for (Sucursal sucursalListNewSucursalToAttach : sucursalListNew) {
                sucursalListNewSucursalToAttach = em.getReference(sucursalListNewSucursalToAttach.getClass(), sucursalListNewSucursalToAttach.getIdSucursal());
                attachedSucursalListNew.add(sucursalListNewSucursalToAttach);
            }
            sucursalListNew = attachedSucursalListNew;
            municipio.setSucursalList(sucursalListNew);
            municipio = em.merge(municipio);
            if (idDepartamentoOld != null && !idDepartamentoOld.equals(idDepartamentoNew)) {
                idDepartamentoOld.getMunicipioList().remove(municipio);
                idDepartamentoOld = em.merge(idDepartamentoOld);
            }
            if (idDepartamentoNew != null && !idDepartamentoNew.equals(idDepartamentoOld)) {
                idDepartamentoNew.getMunicipioList().add(municipio);
                idDepartamentoNew = em.merge(idDepartamentoNew);
            }
            for (Bodega bodegaListNewBodega : bodegaListNew) {
                if (!bodegaListOld.contains(bodegaListNewBodega)) {
                    Municipio oldIdMunicipioOfBodegaListNewBodega = bodegaListNewBodega.getIdMunicipio();
                    bodegaListNewBodega.setIdMunicipio(municipio);
                    bodegaListNewBodega = em.merge(bodegaListNewBodega);
                    if (oldIdMunicipioOfBodegaListNewBodega != null && !oldIdMunicipioOfBodegaListNewBodega.equals(municipio)) {
                        oldIdMunicipioOfBodegaListNewBodega.getBodegaList().remove(bodegaListNewBodega);
                        oldIdMunicipioOfBodegaListNewBodega = em.merge(oldIdMunicipioOfBodegaListNewBodega);
                    }
                }
            }
            for (Sucursal sucursalListNewSucursal : sucursalListNew) {
                if (!sucursalListOld.contains(sucursalListNewSucursal)) {
                    Municipio oldIdMunicipioOfSucursalListNewSucursal = sucursalListNewSucursal.getIdMunicipio();
                    sucursalListNewSucursal.setIdMunicipio(municipio);
                    sucursalListNewSucursal = em.merge(sucursalListNewSucursal);
                    if (oldIdMunicipioOfSucursalListNewSucursal != null && !oldIdMunicipioOfSucursalListNewSucursal.equals(municipio)) {
                        oldIdMunicipioOfSucursalListNewSucursal.getSucursalList().remove(sucursalListNewSucursal);
                        oldIdMunicipioOfSucursalListNewSucursal = em.merge(oldIdMunicipioOfSucursalListNewSucursal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = municipio.getIdMunicipio();
                if (findMunicipio(id) == null) {
                    throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio municipio;
            try {
                municipio = em.getReference(Municipio.class, id);
                municipio.getIdMunicipio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Bodega> bodegaListOrphanCheck = municipio.getBodegaList();
            for (Bodega bodegaListOrphanCheckBodega : bodegaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Municipio (" + municipio + ") cannot be destroyed since the Bodega " + bodegaListOrphanCheckBodega + " in its bodegaList field has a non-nullable idMunicipio field.");
            }
            List<Sucursal> sucursalListOrphanCheck = municipio.getSucursalList();
            for (Sucursal sucursalListOrphanCheckSucursal : sucursalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Municipio (" + municipio + ") cannot be destroyed since the Sucursal " + sucursalListOrphanCheckSucursal + " in its sucursalList field has a non-nullable idMunicipio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento idDepartamento = municipio.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento.getMunicipioList().remove(municipio);
                idDepartamento = em.merge(idDepartamento);
            }
            em.remove(municipio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipio> findMunicipioEntities() {
        return findMunicipioEntities(true, -1, -1);
    }

    public List<Municipio> findMunicipioEntities(int maxResults, int firstResult) {
        return findMunicipioEntities(false, maxResults, firstResult);
    }

    private List<Municipio> findMunicipioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipio.class));
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

    public Municipio findMunicipio(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipio> rt = cq.from(Municipio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
