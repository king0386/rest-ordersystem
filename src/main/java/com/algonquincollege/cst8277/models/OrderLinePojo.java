/*****************************************************************c******************o*******v******id********
 * File: OrderLinePojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
*
* Description: model for the OrderLine object
*/
@Entity(name = "Orderline")
@Table(name = "ORDERLINE")
@Access(AccessType.PROPERTY)
public class OrderLinePojo implements Serializable {
    /**
     * Explicit set of serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * the primary key
     */
    protected OrderLinePk primaryKey;
    /**
     * the owning order
     */
    protected OrderPojo owningOrder;
    /**
     * the amount
     */
    protected Double amount;
    /**
     * the product
     */
    protected ProductPojo product;

    // JPA requires each @Entity class have a default constructor
    public OrderLinePojo() {
    }
    /**
     * Get PK
     * @return the value of primary key
     */
    @EmbeddedId
    public OrderLinePk getPk() {
        return primaryKey;
    }
    /**
     * set PK
     * @param primaryKey value of new primaryKey
     */
    public void setPk(OrderLinePk primaryKey) {
        this.primaryKey = primaryKey;
    }
    /**
     * get Owning Order
     * @return value of owningOrder
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "OWNING_ORDER_ID")
    @MapsId("owningOrderId")
    public OrderPojo getOwningOrder() {
        return owningOrder;
    }
    /**
     * set Owning Order
     * @param owningOrder new value of owningOrder
     */
    public void setOwningOrder(OrderPojo owningOrder) {
        this.owningOrder = owningOrder;
    }

    /**
     * Get Amount
     * @return value of amount
     */
    public Double getAmount() {
        return amount;
    }
    /**
     * Set Amount
     * @param amount sets new value of amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    /**
     * Get product
     * @return value of product
     */
    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    public ProductPojo getProduct() {
        return product;
    }
    /**
     * Set Product
     * @param product new value of product
     */
    public void setProduct(ProductPojo product) {
        this.product = product;
    }

}