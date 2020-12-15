/*****************************************************************c******************o*******v******id********
 * File: PojoListener.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 */
package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class PojoListener {
    /**
     * Set Created On Date
     * @param pojo new value of Created on date
     */
    @PrePersist
    public void setCreatedOnDate(PojoBase pojo) {
        LocalDateTime now = LocalDateTime.now();
        pojo.setCreatedDate(now);
        pojo.setUpdatedDate(now);
    }
    /**
     * Set Updated Date
     * @param pojo new value of updatedDate
     */
    @PreUpdate
    public void setUpdatedDate(PojoBase pojo) {
        pojo.setUpdatedDate(LocalDateTime.now());
    }

}