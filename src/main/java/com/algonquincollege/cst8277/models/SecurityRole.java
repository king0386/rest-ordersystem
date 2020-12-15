/*****************************************************************c******************o*******v******id********
 * File: SecurityRole.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 */
package com.algonquincollege.cst8277.models;

import static com.algonquincollege.cst8277.models.SecurityRole.ROLE_BY_NAME_QUERY;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Role class used for (JSR-375) Java EE Security authorization/authentication
 */
@Entity(name = "SecurityRole")
@Table(name = "SECURITY_ROLE")
@AttributeOverride(name = "id", column = @Column(name="ROLE_ID"))
@NamedQuery(name=ROLE_BY_NAME_QUERY, query = "select r FROM SecurityRole r where r.roleName = ?1") //???????????
public class SecurityRole implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Constant ROLE_BY_NAME_QEURY
     */
    public static final String ROLE_BY_NAME_QUERY = "roleByName";

    /**
     * the ID
     */
    protected int id;
    /**
     * The roleName
     */
    protected String roleName;
    /**
     * set of users
     */
    protected Set<SecurityUser> users;

    /**
     * Instantiate new SecurityRole
     */
    public SecurityRole() {
        super();
    }
    /**
     * Get ID
     * @return value of ID
     */
    @Id
//    @Column(name="ROLE_ID")
    public int getId() {
        return id;
    }
    /**
     * Set ID
     * @param id new value for id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Get Role Name
     * @return value of roleName
     */
    public String getRoleName() {
        return roleName;
    }
    /**
     * Set Role Name
     * @param roleName new value of roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Get Users
     * @return set of values for users
     */
    @JsonInclude(Include.NON_NULL)
    @ManyToMany(mappedBy = "roles")
    public Set<SecurityUser> getUsers() {
        return users;
    }
    /**
     * Set Users
     * @param users set new value for users
     */
    public void setUsers(Set<SecurityUser> users) {
        this.users = users;
    }
    /**
     * Add User To Role
     * @param user value of user to be added
     */
    public void addUserToRole(SecurityUser user) {
        getUsers().add(user);
    }

    /**
     * hashCode for SecurityRole
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    /**
     * Ensures no SecurityRoles are the same
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
        SecurityRole other = (SecurityRole)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}