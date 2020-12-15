/*****************************************************************c******************o*******v******id********
 * File: CustomIdentityStoreJPAHelper.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 */
package com.algonquincollege.cst8277.security;

import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY;
import static com.algonquincollege.cst8277.utils.MyConstants.PARAM1;
import static com.algonquincollege.cst8277.utils.MyConstants.PU_NAME;
import static java.util.Collections.emptySet;

import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;

/*
 * Stateless Session bean should also be a Singleton
 */
@Singleton
public class CustomIdentityStoreJPAHelper {
    /**
     * Constant CUSTOMER_PU
     */
    public static final String CUSTOMER_PU = "20f-groupProject-PU";
    /**
     * Entity Manager
     */
    @PersistenceContext(name=PU_NAME)
    protected EntityManager em;
    /**
     * pbAndJPasswordHash
     */
    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    /**
     * Find user By Name
     * @param username specific username of user
     * @return value of found user
     */
    public SecurityUser findUserByName(String username) {
        SecurityUser user = null;
        try {
            //TODO

            user = em.createNamedQuery(SECURITY_USER_BY_NAME_QUERY,
                SecurityUser.class).setParameter(PARAM1, username).getSingleResult();
            
        }
        catch (Exception e) {
           
        }
        return user;
    }

    /**
     * Find Role Names For User
     * @param username specific username to be found
     * @return valoe of roleNames
     */
    public Set<String> findRoleNamesForUser(String username) {
        Set<String> roleNames = emptySet();
        SecurityUser securityUser = findUserByName(username);
        if (securityUser != null) {
            roleNames = securityUser.getRoles().stream().map(s -> s.getRoleName()).collect(Collectors.toSet());
        }
        return roleNames;
    }

    /**
     * Save Security User
     * @param user specific user to be saved
     */
    @Transactional
    public void saveSecurityUser(SecurityUser user) {
        //TODO
        em.persist(user);
    }

    /**
     * Save Security Role
     * @param role specfic ole to be saved
     */
    @Transactional
    public void saveSecurityRole(SecurityRole role) {
        //TODO
        em.persist(role);
    }
}