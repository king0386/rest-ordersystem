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

import static com.algonquincollege.cst8277.models.SecurityRole.ROLE_BY_NAME_QUERY;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_KEY_SIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_SALT_SIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PREFIX;
import static com.algonquincollege.cst8277.utils.MyConstants.PARAM1;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_KEYSIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_SALTSIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;
import static com.algonquincollege.cst8277.models.OrderPojo.ORDERS_BY_CUSID_QUERY_NAME;

import static com.algonquincollege.cst8277.models.CustomerPojo.ALL_CUSTOMERS_QUERY_NAME;
//import static com.algonquincollege.cst8277.models.CustomerPojo.ID_CUSTOMERS_QUERY_NAME;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.algonquincollege.cst8277.models.AddressPojo;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.OrderPojo;
import com.algonquincollege.cst8277.models.ProductPojo;
import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;
import com.algonquincollege.cst8277.models.ShippingAddressPojo;
import com.algonquincollege.cst8277.models.StorePojo;

/**
 * Stateless Singleton Session Bean - CustomerService
 */
@Singleton
public class CustomerService implements Serializable {
    /**
     * Explicit set of serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Set constant CUSTOMER_PU
     */
    public static final String CUSTOMER_PU = "20f-groupProject-PU";

    /**
     * Inject EntityManager
     */
    @PersistenceContext(name = CUSTOMER_PU)
    protected EntityManager em;

    /**
     * Injet Pbkdf2PasswordHash
     */
    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;
    
    //TODO
    
    /**
     * Gets all Customers
     * @return Returns all customers
     */
    @Transactional
    public List<CustomerPojo> getAllCustomers() {
        return em.createNamedQuery(ALL_CUSTOMERS_QUERY_NAME, CustomerPojo.class).getResultList();
    }
    /**
     * Gets customer by ID
     * @param custPK customer PK
     * @return the found customer
     */
    @Transactional
    public CustomerPojo getCustomerById(int custPK) {
        CustomerPojo cust = null;
//        cust = em.createNamedQuery(ID_CUSTOMERS_QUERY_NAME,
//            CustomerPojo.class).setParameter(1, custPK).getSingleResult();
//         return cust;
        cust = em.find(CustomerPojo.class, custPK);
            return cust;
        }
    
    /**
     * Persist Customer
     * @param newCustomer the newly added customer
     * @return the customer pojo
     */
    @Transactional
    public CustomerPojo persistCustomer(CustomerPojo newCustomer) {
        em.persist(newCustomer);
        return newCustomer;
    }
    
    /**
     * Merge customer by ID
     * @param custId the specified customer
     * @param newCustomer the new customer to be merged
     * @return the merged customer
     */
    @Transactional
    public CustomerPojo mergeCustomerById(int custId, CustomerPojo newCustomer) {
        CustomerPojo customerToUpdate = em.find(CustomerPojo.class, custId);
        if (newCustomer.getId() == customerToUpdate.getId()) {
        customerToUpdate = newCustomer;
        em.merge(customerToUpdate);
        }
        return customerToUpdate;
    }
    
    /**
     * Dekete customer by ID
     * @param cusId the customer ID
     */
    @Transactional
    public void removeCustomerById(int cusId) {
        CustomerPojo customerToDelete = getCustomerById(cusId);
        em.remove(customerToDelete);
    }

    /**
     * Set Address For
     * @param custId specific customer ID
     * @param newAddress the new address
     * @return updated customer
     */
    @Transactional
    public CustomerPojo setAddressFor(int custId, AddressPojo newAddress) {
        CustomerPojo updatedCustomer = em.find(CustomerPojo.class, custId);
        if (newAddress instanceof ShippingAddressPojo) {
            updatedCustomer.setShippingAddress(newAddress);
        }
        else {
            updatedCustomer.setBillingAddress(newAddress);
        }
        em.merge(updatedCustomer);
        return updatedCustomer;
    }
    /**
     * Remove Address For
     * @param custId the specific customer
     */
    @Transactional
    public void removeAddressFor(int custId) {
        CustomerPojo cust = em.find(CustomerPojo.class, custId);
        cust.setShippingAddress(null);
        em.merge(cust);
    }
    
    /**
     * Get All Orders
     * @param custId the specific customer ID
     * @return all orders for the customer
     */
    public List<OrderPojo> getAllOrders(int custId) {
        return em.createNamedQuery(ORDERS_BY_CUSID_QUERY_NAME, OrderPojo.class).setParameter(1, custId).getResultList();
    }
    /**
     * Persist Order
     * @param custId specific customer ID
     * @param newOrder the new order
     * @return new Order
     */
    @Transactional
    public OrderPojo persistOrder(int custId, OrderPojo newOrder) {
        newOrder.getOwningCustomer().setId(custId);
        em.persist(newOrder);
        return newOrder;
    }
    /**
     * Build User For New Customer
     * 
     * @param newCustomerWithIdTimestamps the new customer including ID timestamps
     */
    @Transactional
    public void buildUserForNewCustomer(CustomerPojo newCustomerWithIdTimestamps) {
        SecurityUser userForNewCustomer = new SecurityUser();
        userForNewCustomer.setUsername(DEFAULT_USER_PREFIX + "" + newCustomerWithIdTimestamps.getId());
        Map<String, String> pbAndjProperties = new HashMap<>();
        pbAndjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
        pbAndjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
        pbAndjProperties.put(PROPERTY_SALTSIZE, DEFAULT_SALT_SIZE);
        pbAndjProperties.put(PROPERTY_KEYSIZE, DEFAULT_KEY_SIZE);
        pbAndjPasswordHash.initialize(pbAndjProperties);
        String pwHash = pbAndjPasswordHash.generate(DEFAULT_USER_PASSWORD.toCharArray());
        userForNewCustomer.setPwHash(pwHash);
        userForNewCustomer.setCustomer(newCustomerWithIdTimestamps);
        SecurityRole userRole = em.createNamedQuery(ROLE_BY_NAME_QUERY,
            SecurityRole.class).setParameter(PARAM1, USER_ROLE).getSingleResult();
        userForNewCustomer.getRoles().add(userRole);
        userRole.getUsers().add(userForNewCustomer);
        em.persist(userForNewCustomer);
    }
    /**
     * Get All Products
     * @return list of all products
     */
    public List<ProductPojo> getAllProducts() {
        //example of using JPA Criteria query instead of JPQL
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductPojo> q = cb.createQuery(ProductPojo.class);
            Root<ProductPojo> c = q.from(ProductPojo.class);
            q.select(c);
            TypedQuery<ProductPojo> q2 = em.createQuery(q);
            List<ProductPojo> allProducts = q2.getResultList();
            return allProducts;
        }
        catch (Exception e) {
            return null;
        }
        }
    /**
     * Get Product by ID
     * @param prodId specific product ID
     * @return the specified product
     */
    public ProductPojo getProductById(int prodId) {

        //  select c from productPojo c where c.id = prodId

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductPojo> q = cb.createQuery(ProductPojo.class);
            Root<ProductPojo> c = q.from(ProductPojo.class);
            q.select(c).where(cb.equal(c.get("id"), prodId));
            TypedQuery<ProductPojo> q2 = em.createQuery(q);
            ProductPojo theProduct = q2.getSingleResult();
            return theProduct;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Get All Stores
     * @return List of all stores
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
     * Get Store By ID
     * @param id specific store ID
     * @return the store
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
    
    /*
    
    public OrderPojo getAllOrders ... getOrderbyId ... build Orders with OrderLines ...
     
    */

}