/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerJPA;

import ControllerJPA.exceptions.IllegalOrphanException;
import ControllerJPA.exceptions.NonexistentEntityException;
import ControllerJPA.exceptions.PreexistingEntityException;
import Entity.Bodega;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Municipio;
import Entity.Pedido;
import java.util.ArrayList;
import java.util.List;
import Entity.Empleado;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Diego
 */
public class BodegaJpaController implements Serializable {

    public BodegaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bodega bodega) throws PreexistingEntityException, Exception {
        if (bodega.getPedidoList() == null) {
            bodega.setPedidoList(new ArrayList<Pedido>());
        }
        if (bodega.getEmpleadoList() == null) {
            bodega.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio idMunicipio = bodega.getIdMunicipio();
            if (idMunicipio != null) {
                idMunicipio = em.getReference(idMunicipio.getClass(), idMunicipio.getIdMunicipio());
                bodega.setIdMunicipio(idMunicipio);
            }
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : bodega.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getIdPedido());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            bodega.setPedidoList(attachedPedidoList);
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : bodega.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            bodega.setEmpleadoList(attachedEmpleadoList);
            em.persist(bodega);
            if (idMunicipio != null) {
                idMunicipio.getBodegaList().add(bodega);
                idMunicipio = em.merge(idMunicipio);
            }
            for (Pedido pedidoListPedido : bodega.getPedidoList()) {
                Bodega oldIdBodegaOfPedidoListPedido = pedidoListPedido.getIdBodega();
                pedidoListPedido.setIdBodega(bodega);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldIdBodegaOfPedidoListPedido != null) {
                    oldIdBodegaOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldIdBodegaOfPedidoListPedido = em.merge(oldIdBodegaOfPedidoListPedido);
                }
            }
            for (Empleado empleadoListEmpleado : bodega.getEmpleadoList()) {
                Bodega oldIdBodegaOfEmpleadoListEmpleado = empleadoListEmpleado.getIdBodega();
                empleadoListEmpleado.setIdBodega(bodega);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldIdBodegaOfEmpleadoListEmpleado != null) {
                    oldIdBodegaOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldIdBodegaOfEmpleadoListEmpleado = em.merge(oldIdBodegaOfEmpleadoListEmpleado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBodega(bodega.getIdBodega()) != null) {
                throw new PreexistingEntityException("Bodega " + bodega + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bodega bodega) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bodega persistentBodega = em.find(Bodega.class, bodega.getIdBodega());
            Municipio idMunicipioOld = persistentBodega.getIdMunicipio();
            Municipio idMunicipioNew = bodega.getIdMunicipio();
            List<Pedido> pedidoListOld = persistentBodega.getPedidoList();
            List<Pedido> pedidoListNew = bodega.getPedidoList();
            List<Empleado> empleadoListOld = persistentBodega.getEmpleadoList();
            List<Empleado> empleadoListNew = bodega.getEmpleadoList();
            List<String> illegalOrphanMessages = null;
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its idBodega field is not nullable.");
                }
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its idBodega field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idMunicipioNew != null) {
                idMunicipioNew = em.getReference(idMunicipioNew.getClass(), idMunicipioNew.getIdMunicipio());
                bodega.setIdMunicipio(idMunicipioNew);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getIdPedido());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            bodega.setPedidoList(pedidoListNew);
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            bodega.setEmpleadoList(empleadoListNew);
            bodega = em.merge(bodega);
            if (idMunicipioOld != null && !idMunicipioOld.equals(idMunicipioNew)) {
                idMunicipioOld.getBodegaList().remove(bodega);
                idMunicipioOld = em.merge(idMunicipioOld);
            }
            if (idMunicipioNew != null && !idMunicipioNew.equals(idMunicipioOld)) {
                idMunicipioNew.getBodegaList().add(bodega);
                idMunicipioNew = em.merge(idMunicipioNew);
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Bodega oldIdBodegaOfPedidoListNewPedido = pedidoListNewPedido.getIdBodega();
                    pedidoListNewPedido.setIdBodega(bodega);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldIdBodegaOfPedidoListNewPedido != null && !oldIdBodegaOfPedidoListNewPedido.equals(bodega)) {
                        oldIdBodegaOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldIdBodegaOfPedidoListNewPedido = em.merge(oldIdBodegaOfPedidoListNewPedido);
                    }
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Bodega oldIdBodegaOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getIdBodega();
                    empleadoListNewEmpleado.setIdBodega(bodega);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldIdBodegaOfEmpleadoListNewEmpleado != null && !oldIdBodegaOfEmpleadoListNewEmpleado.equals(bodega)) {
                        oldIdBodegaOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldIdBodegaOfEmpleadoListNewEmpleado = em.merge(oldIdBodegaOfEmpleadoListNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = bodega.getIdBodega();
                if (findBodega(id) == null) {
                    throw new NonexistentEntityException("The bodega with id " + id + " no longer exists.");
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
            Bodega bodega;
            try {
                bodega = em.getReference(Bodega.class, id);
                bodega.getIdBodega();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bodega with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pedido> pedidoListOrphanCheck = bodega.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bodega (" + bodega + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable idBodega field.");
            }
            List<Empleado> empleadoListOrphanCheck = bodega.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bodega (" + bodega + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable idBodega field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Municipio idMunicipio = bodega.getIdMunicipio();
            if (idMunicipio != null) {
                idMunicipio.getBodegaList().remove(bodega);
                idMunicipio = em.merge(idMunicipio);
            }
            em.remove(bodega);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bodega> findBodegaEntities() {
        return findBodegaEntities(true, -1, -1);
    }

    public List<Bodega> findBodegaEntities(int maxResults, int firstResult) {
        return findBodegaEntities(false, maxResults, firstResult);
    }

    private List<Bodega> findBodegaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bodega.class));
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

    public Bodega findBodega(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bodega.class, id);
        } finally {
            em.close();
        }
    }

    public int getBodegaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bodega> rt = cq.from(Bodega.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
