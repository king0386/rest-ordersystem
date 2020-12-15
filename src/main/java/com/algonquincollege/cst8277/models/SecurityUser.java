/*****************************************************************c******************o*******v******id********
 * File: SecurityUser.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 */
package com.algonquincollege.cst8277.models;

import static com.algonquincollege.cst8277.models.SecurityUser.USER_FOR_OWNING_CUST_QUERY;
import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.algonquincollege.cst8277.rest.SecurityRoleSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * User class used for (JSR-375) Java EE Security authorization/authentication
 */
@Entity(name = "SecurityUser")
@Table(name = "SECURITY_USER")
@AttributeOverride(name = "id", column = @Column(name="USER_ID"))  //??????????????????????
//@NamedQuery(name=USER_FOR_OWNING_CUST_QUERY, query = "select u FROM SecurityUser u where u.id = ?1") //??????????
@NamedQuery(name=SECURITY_USER_BY_NAME_QUERY, query = "select u FROM SecurityUser u where u.username = ?1") //???????????
public class SecurityUser implements Serializable, Principal {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Constant USER_FOR_OWNING_CUST_QUERY
     */
    public static final String USER_FOR_OWNING_CUST_QUERY =
        "userForOwningCust";
    /**
     * Constant SECURITY_USER_BY_NAME_QUERY
     */
    public static final String SECURITY_USER_BY_NAME_QUERY =
        "userByName";

    /**
     * The id
     */
    protected int id;
    /**
     * The username
     */
    protected String username;
    /**
     * The pwHash
     */
    protected String pwHash;
    /**
     * Set of roles
     */
    protected Set<SecurityRole> roles = new HashSet<>();
    /**
     * Cust
     */
    protected CustomerPojo cust;

    /**
     * Instantiates a new SecurityUser
     */
    public SecurityUser() {
        super();
    }
    /**
     * Get Id
     * @return value of id
     */
    @Id
//    @Column(name="USER_ID")
    public int getId() {
        return id;
    }
    /**
     * Set ID
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Get username
     * @return value of username
     */
    public String getUsername() {
        return username;
    }
    /**
     * set username
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get PwHash
     * @return value of pwHash
     */
    @JsonIgnore
    public String getPwHash() {
        return pwHash;
    }
    /**
     * set PwHash
     * @param pwHash new value for pwHash
     */
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }
    
    /**
     * Get Roles
     * @return value of roles
     */
    @JsonInclude(Include.NON_NULL)
    @JsonSerialize(using = SecurityRoleSerializer.class)
    @ManyToMany
    @JoinTable(name="SECURITY_USER_SECURITY_ROLE",
    joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="USER_ID"),
    inverseJoinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ROLE_ID"))
    public Set<SecurityRole> getRoles() {
        return roles;
    }
    /**
     * Set Roles
     * @param roles new value of roles
     */
    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }
    /**
     * Get Customer
     * @return value for cust
     */
    @OneToOne
    @JoinColumn(name = "CUSTOMER")
    public CustomerPojo getCustomer() {
        return cust;
    }
    /**
     * Set Customer
     * @param cust new value of cust
     */
    public void setCustomer(CustomerPojo cust) {
        this.cust = cust;
    }

    /**
     * getName
     * @return value of getUsername
     */
    //Principal
    @JsonIgnore
    @Override
    public String getName() {
        return getUsername();
    }

    /**
     * hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    /**
     * equals method
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SecurityUser other = (SecurityUser)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}