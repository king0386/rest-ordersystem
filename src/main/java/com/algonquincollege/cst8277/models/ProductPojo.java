/*****************************************************************c******************o*******v******id********
 * File: OrderPojo.java
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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
*
* Description: model for the Product object
*/
@Entity(name = "Product")
@Table(name = "PRODUCT")
@AttributeOverride(name = "id", column = @Column(name="PRODUCT_ID"))
public class ProductPojo extends PojoBase implements Serializable {
    /**
     * Explicit set to serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The description
     */
    protected String description;
    /**
     * The serialNo
     */
    protected String serialNo;
    /**
     * List of stores
     */
    protected Set<StorePojo> stores = new HashSet<>();

    // JPA requires each @Entity class have a default constructor
    public ProductPojo() {
    }
    
    /**
     * @return the value for firstName
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description new value for description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get SerialNo
     * @return value of serialNo
     */
    @Column(name = "SERIALNUMBER")
    public String getSerialNo() {
        return serialNo;
    }
    /**
     * Set SerialNo
     * @param serialNo new value of serialNo
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    /**
     * Get Stores
     * @return value of set of stores
     */
    @JsonInclude(Include.NON_NULL)
    @ManyToMany(mappedBy = "products")
    public Set<StorePojo> getStores() {
        return stores;
    }
    /**
     * Set Stores
     * @param stores new value of store
     */
    public void setStores(Set<StorePojo> stores) {
        this.stores = stores;
    }

}