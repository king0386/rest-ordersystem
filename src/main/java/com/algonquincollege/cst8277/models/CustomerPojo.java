/*****************************************************************c******************o*******v******id********
 * File: CustomerPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 */
package com.algonquincollege.cst8277.models;

import static com.algonquincollege.cst8277.models.CustomerPojo.ALL_CUSTOMERS_QUERY_NAME;
//import static com.algonquincollege.cst8277.models.CustomerPojo.ID_CUSTOMERS_QUERY_NAME;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
*
* Description: model for the Customer object
*/
@Entity(name = "Customer")
@Table(name = "CUSTOMER")
@AttributeOverride(name = "id", column = @Column(name="CUST_ID"))
@NamedQuery(name=ALL_CUSTOMERS_QUERY_NAME, query = "select c FROM Customer c")
//@NamedQuery(name=ID_CUSTOMERS_QUERY_NAME, query = "select c FROM Customer c where c.id = ?1")
public class CustomerPojo extends PojoBase implements Serializable {
    /**
     * Explicit set of serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constant query name for all cstomers
     */
    public static final String ALL_CUSTOMERS_QUERY_NAME = "allCustomers";
//    public static final String ID_CUSTOMERS_QUERY_NAME = "idCustomers";

    /**
     * First name
     */
    protected String firstName;
    /**
     * Last name
     */
    protected String lastName;
    /**
     * email
     */
    protected String email;
    /**
     * phone number
     */
    protected String phoneNumber;
    /**
     * Shipping Address
     */
    protected AddressPojo shippingAddress;
    /**
     * Billing Address
     */
    protected AddressPojo billingAddress;
    /**
     * List of orders
     */
    protected List<OrderPojo> orders;
    
    // JPA requires each @Entity class have a default constructor
	public CustomerPojo() {
	}
	
    /**
     * @return the value for firstName
     */
    @Column(name = "FNAME")
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the value for lastName
     */
    @Column(name = "LNAME")
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName new value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get Email
     * @return value of email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Set Email
     * @param email new value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get Phone Number
     * @return value for phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * Set Phone Number
     * @param phoneNumber new value of phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //dont use CascadeType.All (skipping CascadeType.REMOVE): what if two customers
    //live at the same address and 1 leaves the house but the other does not?
    /**
     * Get Shipping Address
     * @return the value for shippingAddress
     */
    @OneToOne
    @JoinColumn(name = "SHIPPING_ADDR")
    public AddressPojo getShippingAddress() {
        return shippingAddress;
    }
    /**
     * Set Shipping Address
     * @param shippingAddress new value for shippingAddress
     */
    public void setShippingAddress(AddressPojo shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * Get Billing Address
     * @return value for billingAddress
     */
    @OneToOne
    @JoinColumn(name = "BILLING_ADDR")
    public AddressPojo getBillingAddress() {
        return billingAddress;
    }
    /**
     * Set Billing Address
     * @param billingAddress new value for billingAddress
     */
    public void setBillingAddress(AddressPojo billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * @return the orders
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "owningCustomer")
    public List<OrderPojo> getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(List<OrderPojo> orders) {
        this.orders = orders;
    }

    /**
     * toString method
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("Customer [id=")
            .append(id)
            .append(", ");
        if (firstName != null) {
            builder
                .append("firstName=")
                .append(firstName)
                .append(", ");
        }
        if (lastName != null) {
            builder
                .append("lastName=")
                .append(lastName)
                .append(", ");
        }
        if (phoneNumber != null) {
            builder
                .append("phoneNumber=")
                .append(phoneNumber)
                .append(", ");
        }
        if (email != null) {
            builder
                .append("email=")
                .append(email)
                .append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

}