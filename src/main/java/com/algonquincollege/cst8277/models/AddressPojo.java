/*****************************************************************c******************o*******v******id********
 * File: AddressPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
*
* Description: model for the Address object
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
  @JsonSubTypes({
      @Type(value = BillingAddressPojo.class, name = "B"),
      @Type(value = ShippingAddressPojo.class, name = "S")
})
@Entity(name="Address")
@Table(name = "CUST_ADDR")
@AttributeOverride(name = "id", column = @Column(name="ADDR_ID"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ADDR_TYPE", columnDefinition = "VARCHAR", length = 1)
//@DiscriminatorColumn(columnDefinition = "VARCHAR", length = 1)
public abstract class AddressPojo extends PojoBase implements Serializable {

    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    /**
     * The Street
     */
    protected String street;
    /**
     * The City
     */
    protected String city;
    /**
     * The Country
     */
    protected String country;
    /**
     * The Postal Code
     */
    protected String postal;
    /**
     * The State
     */
    protected String state;


    /**
     * JPA requires each @Entity class have a default constructor
     */
    public AddressPojo() {
        super();
    }
    /**
     * Gets the city
     * @return the city
     */
    public String getCity() {
        return city;
    }
    /**
     * Sets the city
     * @param city to set new city
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * Get Country
     * @return the country
     */
    public String getCountry() {
        return country;
    }
    /**
     * Set Country
     * @param country new Country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get Postal
     * @return the Postal code
     */
    public String getPostal() {
        return postal;
    }
    /**
     * Set Postal
     * @param postal the new postal
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }
    /**
     * Get State
     * @return the State
     */
    public String getState() {
        return state;
    }
    /**
     * Set State
     * @param state the new value of State
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * Get Street
     * @return the street
     */
    public String getStreet() {
        return street;
    }
    /**
     * Set Street
     * @param street new value of Street
     */
    public void setStreet(String street) {
        this.street = street;
    }

}