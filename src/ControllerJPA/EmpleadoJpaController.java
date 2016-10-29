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
import Entity.Bodega;
import Entity.Empleado;
import Entity.Pedido;
import java.util.ArrayList;
import java.util.List;
import Entity.Usuario;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Diego
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, Exception {
        if (empleado.getPedidoList() == null) {
            empleado.setPedidoList(new ArrayList<Pedido>());
        }
        if (empleado.getUsuarioList() == null) {
            empleado.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bodega idBodega = empleado.getIdBodega();
            if (idBodega != null) {
                idBodega = em.getReference(idBodega.getClass(), idBodega.getIdBodega());
                empleado.setIdBodega(idBodega);
            }
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : empleado.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getIdPedido());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            empleado.setPedidoList(attachedPedidoList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : empleado.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getIdUser());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            empleado.setUsuarioList(attachedUsuarioList);
            em.persist(empleado);
            if (idBodega != null) {
                idBodega.getEmpleadoList().add(empleado);
                idBodega = em.merge(idBodega);
            }
            for (Pedido pedidoListPedido : empleado.getPedidoList()) {
                Empleado oldIdEmpleadoOfPedidoListPedido = pedidoListPedido.getIdEmpleado();
                pedidoListPedido.setIdEmpleado(empleado);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldIdEmpleadoOfPedidoListPedido != null) {
                    oldIdEmpleadoOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldIdEmpleadoOfPedidoListPedido = em.merge(oldIdEmpleadoOfPedidoListPedido);
                }
            }
            for (Usuario usuarioListUsuario : empleado.getUsuarioList()) {
                Empleado oldIdEmpleadoOfUsuarioListUsuario = usuarioListUsuario.getIdEmpleado();
                usuarioListUsuario.setIdEmpleado(empleado);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldIdEmpleadoOfUsuarioListUsuario != null) {
                    oldIdEmpleadoOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldIdEmpleadoOfUsuarioListUsuario = em.merge(oldIdEmpleadoOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleado(empleado.getIdEmpleado()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getIdEmpleado());
            Bodega idBodegaOld = persistentEmpleado.getIdBodega();
            Bodega idBodegaNew = empleado.getIdBodega();
            List<Pedido> pedidoListOld = persistentEmpleado.getPedidoList();
            List<Pedido> pedidoListNew = empleado.getPedidoList();
            List<Usuario> usuarioListOld = persistentEmpleado.getUsuarioList();
            List<Usuario> usuarioListNew = empleado.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its idEmpleado field is not nullable.");
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its idEmpleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idBodegaNew != null) {
                idBodegaNew = em.getReference(idBodegaNew.getClass(), idBodegaNew.getIdBodega());
                empleado.setIdBodega(idBodegaNew);
            }
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getIdPedido());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            empleado.setPedidoList(pedidoListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdUser());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            empleado.setUsuarioList(usuarioListNew);
            empleado = em.merge(empleado);
            if (idBodegaOld != null && !idBodegaOld.equals(idBodegaNew)) {
                idBodegaOld.getEmpleadoList().remove(empleado);
                idBodegaOld = em.merge(idBodegaOld);
            }
            if (idBodegaNew != null && !idBodegaNew.equals(idBodegaOld)) {
                idBodegaNew.getEmpleadoList().add(empleado);
                idBodegaNew = em.merge(idBodegaNew);
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Empleado oldIdEmpleadoOfPedidoListNewPedido = pedidoListNewPedido.getIdEmpleado();
                    pedidoListNewPedido.setIdEmpleado(empleado);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldIdEmpleadoOfPedidoListNewPedido != null && !oldIdEmpleadoOfPedidoListNewPedido.equals(empleado)) {
                        oldIdEmpleadoOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
                        oldIdEmpleadoOfPedidoListNewPedido = em.merge(oldIdEmpleadoOfPedidoListNewPedido);
                    }
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Empleado oldIdEmpleadoOfUsuarioListNewUsuario = usuarioListNewUsuario.getIdEmpleado();
                    usuarioListNewUsuario.setIdEmpleado(empleado);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldIdEmpleadoOfUsuarioListNewUsuario != null && !oldIdEmpleadoOfUsuarioListNewUsuario.equals(empleado)) {
                        oldIdEmpleadoOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldIdEmpleadoOfUsuarioListNewUsuario = em.merge(oldIdEmpleadoOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = empleado.getIdEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pedido> pedidoListOrphanCheck = empleado.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable idEmpleado field.");
            }
            List<Usuario> usuarioListOrphanCheck = empleado.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable idEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Bodega idBodega = empleado.getIdBodega();
            if (idBodega != null) {
                idBodega.getEmpleadoList().remove(empleado);
                idBodega = em.merge(idBodega);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
