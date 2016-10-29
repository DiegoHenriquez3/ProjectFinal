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
import Entity.Bodega;
import Entity.Empleado;
import Entity.Pedido;
import Entity.Sucursal;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Diego
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bodega idBodega = pedido.getIdBodega();
            if (idBodega != null) {
                idBodega = em.getReference(idBodega.getClass(), idBodega.getIdBodega());
                pedido.setIdBodega(idBodega);
            }
            Empleado idEmpleado = pedido.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                pedido.setIdEmpleado(idEmpleado);
            }
            Sucursal idSucursal = pedido.getIdSucursal();
            if (idSucursal != null) {
                idSucursal = em.getReference(idSucursal.getClass(), idSucursal.getIdSucursal());
                pedido.setIdSucursal(idSucursal);
            }
            em.persist(pedido);
            if (idBodega != null) {
                idBodega.getPedidoList().add(pedido);
                idBodega = em.merge(idBodega);
            }
            if (idEmpleado != null) {
                idEmpleado.getPedidoList().add(pedido);
                idEmpleado = em.merge(idEmpleado);
            }
            if (idSucursal != null) {
                idSucursal.getPedidoList().add(pedido);
                idSucursal = em.merge(idSucursal);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPedido(pedido.getIdPedido()) != null) {
                throw new PreexistingEntityException("Pedido " + pedido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getIdPedido());
            Bodega idBodegaOld = persistentPedido.getIdBodega();
            Bodega idBodegaNew = pedido.getIdBodega();
            Empleado idEmpleadoOld = persistentPedido.getIdEmpleado();
            Empleado idEmpleadoNew = pedido.getIdEmpleado();
            Sucursal idSucursalOld = persistentPedido.getIdSucursal();
            Sucursal idSucursalNew = pedido.getIdSucursal();
            if (idBodegaNew != null) {
                idBodegaNew = em.getReference(idBodegaNew.getClass(), idBodegaNew.getIdBodega());
                pedido.setIdBodega(idBodegaNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                pedido.setIdEmpleado(idEmpleadoNew);
            }
            if (idSucursalNew != null) {
                idSucursalNew = em.getReference(idSucursalNew.getClass(), idSucursalNew.getIdSucursal());
                pedido.setIdSucursal(idSucursalNew);
            }
            pedido = em.merge(pedido);
            if (idBodegaOld != null && !idBodegaOld.equals(idBodegaNew)) {
                idBodegaOld.getPedidoList().remove(pedido);
                idBodegaOld = em.merge(idBodegaOld);
            }
            if (idBodegaNew != null && !idBodegaNew.equals(idBodegaOld)) {
                idBodegaNew.getPedidoList().add(pedido);
                idBodegaNew = em.merge(idBodegaNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getPedidoList().remove(pedido);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getPedidoList().add(pedido);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (idSucursalOld != null && !idSucursalOld.equals(idSucursalNew)) {
                idSucursalOld.getPedidoList().remove(pedido);
                idSucursalOld = em.merge(idSucursalOld);
            }
            if (idSucursalNew != null && !idSucursalNew.equals(idSucursalOld)) {
                idSucursalNew.getPedidoList().add(pedido);
                idSucursalNew = em.merge(idSucursalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = pedido.getIdPedido();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getIdPedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            Bodega idBodega = pedido.getIdBodega();
            if (idBodega != null) {
                idBodega.getPedidoList().remove(pedido);
                idBodega = em.merge(idBodega);
            }
            Empleado idEmpleado = pedido.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getPedidoList().remove(pedido);
                idEmpleado = em.merge(idEmpleado);
            }
            Sucursal idSucursal = pedido.getIdSucursal();
            if (idSucursal != null) {
                idSucursal.getPedidoList().remove(pedido);
                idSucursal = em.merge(idSucursal);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
