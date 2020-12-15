/*****************************************************************c******************o*******v******id********
 * File: CustomerService.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 *
 */
package com.algonquincollege.cst8277.ejb;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.models.StorePojo;

/**
 * Stateless Singleton Session Bean - StoreerService
 */
@Singleton
public class StoreService implements Serializable {
    /**
     * Explicit set of serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Constant CUSTOMER_PU
     */
    public static final String CUSTOMER_PU = "20f-groupProject-PU";

    /**
     * EntityManager
     */
    @PersistenceContext(name = CUSTOMER_PU)
    protected EntityManager em;

    /**
     * pbAndJPasswordHash
     */
    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;
    
    //TODO
   /**
    * Get All Stores
    * @return value of allStores
    */
    public List<StorePojo> getAllStores() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<StorePojo> q = cb.createQuery(StorePojo.class);
            Root<StorePojo> c = q.from(StorePojo.class);
            q.select(c);
            TypedQuery<StorePojo> q2 = em.createQuery(q);
            List<StorePojo> allStores = q2.getResultList();
            return allStores;
        }
        catch (Exception e) {
            return null;
        }
    }
   /**
    * Get Store By Id
    * @param id specific store ID
    * @return value of theStore
    */
    public StorePojo getStoreById(int id) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<StorePojo> q = cb.createQuery(StorePojo.class);
            Root<StorePojo> c = q.from(StorePojo.class);
            q.select(c).where(cb.equal(c.get("id"), id));
            TypedQuery<StorePojo> q2 = em.createQuery(q);
            StorePojo theStore = q2.getSingleResult();
            return theStore;
        }
        catch (Exception e) {
            return null;
        }
    }
    /**
     * Persist Store
     * @param newStore store to persist
     * @return value of new store
     */
    @Transactional
    public StorePojo persistStore(StorePojo newStore) {
        em.persist(newStore);
        return newStore;
    }
    /**
     * Remove Store By Id
     * @param newStore store to be removed
     */
    @Transactional
    public void removeStoreById(StorePojo newStore) {
//        em.getTransaction().begin();
        em.remove(newStore);
//        em.getTransaction().commit();
    }

}