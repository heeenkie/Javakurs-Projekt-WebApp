/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Dish;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author andreashenriksson
 */

@Named(value = "dishBean")
@SessionScoped
public class DishBean implements Serializable {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Project-WebAppPU");
    
    public DishBean() {
    }
     
    public void persist(Dish entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Dish entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public List<Dish> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Dish> dList = em.createNamedQuery("Dish.findAll").getResultList();
        em.close();
        return dList;
    }
    
    public Dish findById(Integer dishid) {
        EntityManager em = emf.createEntityManager();
        Dish d = (Dish) em.createNamedQuery("Dish.findByDishid").setParameter("dishid", dishid).getSingleResult();
        em.close();
        return d;
    }  
}