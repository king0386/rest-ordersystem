/*****************************************************************c******************o*******v******id********
 * File: OrderLinePk.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.PROPERTY)
public class OrderLinePk implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    /**
     * owning Order ID
     */
    protected int owningOrderId;
    /**
     * order Line No
     */
    protected int orderLineNo;

    /**
     * Get Owning Order ID
     * @return value for owningOrderId
     */
    @Column(name = "OWNING_ORDER_ID")
    public int getOwningOrderId() {
        return owningOrderId;
    }
    /**
     * Set Owning Order ID
     * @param owningOrderId value for owningOrderId
     */
    public void setOwningOrderId(int owningOrderId) {
        this.owningOrderId = owningOrderId;
    }

    /**
     * Get Order Line No
     * @return value of orderLineNo
     */
    @Column(name = "ORDERLINE_NO")
    public int getOrderLineNo() {
        return orderLineNo;
    }
    /**
     * Set Order Line No
     * @param orderLineNo new value of orderLineNo
     */
    public void setOrderLineNo(int orderLineNo) {
        this.orderLineNo = orderLineNo;
    }
    /**
     * creates hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderLineNo, owningOrderId);
    }
    /**
     * Ensures two OrderLinePK aren't the same
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OrderLinePk)) {
            return false;
        }
        OrderLinePk other = (OrderLinePk) obj;
        return orderLineNo == other.orderLineNo && owningOrderId == other.owningOrderId;
    }

}