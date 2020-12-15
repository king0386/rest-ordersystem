/***************************************************************************f******************u************zz*******y**
 * File: Assignment3Driver.java
 * Course materials (20F) CST 8277
 * 
 * @author (original) Mike Norman

 * Note: students do NOT need to change anything in this class
 */


package com.algonquincollege.cst8277;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Description: Main method class
 */
public class DriverTestForJPAErrors {

    public static final String PU_NAME = "test-for-jpa-errors-PU";

    /**
     * main Method. Builds EntityManagerFactory.
     * @param args
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);
        emf.close();
    }

}
