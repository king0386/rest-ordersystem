/*****************************************************************c******************o*******v******id********
 * File: StoreResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.STORE_RESOURCE_NAME;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.algonquincollege.cst8277.ejb.CustomerService;
import com.algonquincollege.cst8277.ejb.StoreService;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.StorePojo;

@Path(STORE_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StoreResource {

    /**
     * Store Service Bean
     */
    @EJB
    protected StoreService storeServiceBean;

    /**
     * Servlet Context
     */
    @Inject
    protected ServletContext servletContext;

    /**
     * Get Stores
     * @return value of response
     */
    @GET
    public Response getStores() {
        servletContext.log("retrieving all stores ...");
        List<StorePojo> stores = storeServiceBean.getAllStores();
        Response response = Response.ok(stores).build();
        return response;
    }
    /**
     * Get Store by ID
     * @param id of specific store
     * @return value of response
     */
    @GET
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getStoreById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific store " + id);
        StorePojo theStore = storeServiceBean.getStoreById(id);
        Response response = Response.ok(theStore).build();
        return response;
    }

    /**
     * Add Store
     * @param newStore to be added
     * @return value of response
     */
    @POST
    @Transactional
    public Response addStore(StorePojo newStore) {
      Response response = null;
      StorePojo newStoreWithIdTimestamps = storeServiceBean.persistStore(newStore);
      response = Response.ok(newStoreWithIdTimestamps).build();
      return response;
    }
}