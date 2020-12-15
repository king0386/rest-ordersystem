/*****************************************************************c******************o*******v******id********
 * File: StorePojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.algonquincollege.cst8277.rest.ProductSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
*
* Description: model for the Store object
*/
@Entity(name = "Store")
@Table(name = "STORES")
@AttributeOverride(name = "id", column = @Column(name="STORE_ID"))
public class StorePojo extends PojoBase implements Serializable {
    /**
     * Explicit set of serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * StoreName
     */
    protected String storeName;
    /**
     * Set of products
     */
    protected Set<ProductPojo> products = new HashSet<>();

    // JPA requires each @Entity class have a default constructor
    public StorePojo() {
    }

    /**
     * Get Store Name
     * @return value of storeName
     */
    public String getStoreName() {
        return storeName;
    }
    /**
     * Set Store Name
     * @param storeName new value of storeName
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    /**
     * Get Products
     * @return value of products
     */
    @JsonSerialize(using = ProductSerializer.class)
      //Discovered what I think is a bug: you should be able to list them in any order,
      //but it turns out, EclipseLink's JPA implementation needs the @JoinColumn StorePojo's PK
      //first, the 'inverse' to ProductPojo's PK second
    @ManyToMany
    @JoinTable(name="STORES_PRODUCTS",
      joinColumns=@JoinColumn(name="STORE_ID", referencedColumnName="STORE_ID"),
      inverseJoinColumns=@JoinColumn(name="PRODUCT_ID", referencedColumnName="PRODUCT_ID"))
    public Set<ProductPojo> getProducts() {
        return products;
    }
    /**
     * Set Products
     * @param products new value of products
     */
    public void setProducts(Set<ProductPojo> products) {
        this.products = products;
    }

}
