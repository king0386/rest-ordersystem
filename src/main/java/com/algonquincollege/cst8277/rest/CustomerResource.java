/*****************************************************************c******************o*******v******id********
 * File: CustomerResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ADDRESS_RESOURCE_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ORDER_RESOURCE_PATH;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.soteria.WrappingCallerPrincipal;

import com.algonquincollege.cst8277.ejb.CustomerService;
import com.algonquincollege.cst8277.models.AddressPojo;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.OrderPojo;
import com.algonquincollege.cst8277.models.SecurityUser;

@Path(CUSTOMER_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    /**
     * Customer Service bean
     */
    @EJB
    protected CustomerService customerServiceBean;

    /**
     * Servlet Context
     */
    @Inject
    protected ServletContext servletContext;

    /**
     * Security Context
     */
    @Inject
    protected SecurityContext sc;

    /**
     * Get Customers
     * @return value of customers
     */
    //@RolesAllowed({ADMIN_ROLE})
    @GET
    public Response getCustomers() {
        servletContext.log("retrieving all customers ...");
        List<CustomerPojo> custs = customerServiceBean.getAllCustomers();
//        servletContext.log("custs size ======= " + custs.size());
        Response response = Response.ok(custs).build();
        return response;
    }
    /**
     * Get Customer by Id
     * @param id specific customer id
     * @return value of specific customer
     */
    @GET
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getCustomerById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific customer " + id);
        Response response = null;
        CustomerPojo cust = null;

//        if (sc.isCallerInRole(ADMIN_ROLE)) {
            cust = customerServiceBean.getCustomerById(id);
            response = Response.status( cust == null ? NOT_FOUND : OK).entity(cust).build();
//        }
//        else if (sc.isCallerInRole(USER_ROLE)) {
//            WrappingCallerPrincipal wCallerPrincipal = (WrappingCallerPrincipal)sc.getCallerPrincipal();
//            SecurityUser sUser = (SecurityUser)wCallerPrincipal.getWrapped();
//            cust = sUser.getCustomer();
//            if (cust != null && cust.getId() == id) {
//                response = Response.status(OK).entity(cust).build();
//            }
//            else {
//                throw new ForbiddenException();
//            }
//        }
//        else {
//            response = Response.status(BAD_REQUEST).build();
//        }
        return response;
    }
    /**
     * Add Customer
     * @param newCustomer to be added
     * @return  value of new customer
     */
    //@RolesAllowed({ADMIN_ROLE})
    @POST
    public Response addCustomer(CustomerPojo newCustomer) {
      Response response = null;
      CustomerPojo newCustomerWithIdTimestamps = customerServiceBean.persistCustomer(newCustomer);
      response = Response.ok(newCustomerWithIdTimestamps).build();
      return response;
    }
    /**
     * Update Customer by ID
     * @param id specific customer to be updaed
     * @param newCustomer new customer
     * @return value of updated customer
     */
    @PUT
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateCustomerById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, CustomerPojo newCustomer) {
        servletContext.log("try to update specific customer " + id);
        Response response = null;
        CustomerPojo updatedCustoner = customerServiceBean.mergeCustomerById(id, newCustomer);
        if (newCustomer == updatedCustoner) {
        response = Response.ok(updatedCustoner).build();
        } else servletContext.log("Two customers' ids don't match!!");
        return response;
    }
    /**
     * Delete Customer by Id
     * @param id of specific customer
     * @return response if customer was deleted
     */
    @DELETE
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deleteCustomerById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        Response response = null;
        servletContext.log("try to remove specific customer " + id);
        customerServiceBean.removeCustomerById(id);
        response = Response.noContent().build();
        
        return response;
    }
    /**
     * Get Address for Customer
     * @param id of specific customer
     * @return value of customer address
     */
    @GET
    @Path(CUSTOMER_ADDRESS_RESOURCE_PATH)
    public Response getAddressForCustomer(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific shippingAddress of a customer id " + id);
         CustomerPojo cust = customerServiceBean.getCustomerById(id);
            Response response = Response.status( cust.getShippingAddress() == null ? NOT_FOUND : OK).entity(cust.getShippingAddress()).build();
      return response;
    }
    /**
     * Update Address For Customer
     * @param id of specific customer
     * @param newAddress of specific customer
     * @return updated value of customer
     */
    //@RolesAllowed({ADMIN_ROLE})
    @PUT
    @Path(CUSTOMER_ADDRESS_RESOURCE_PATH)
    public Response updateAddressForCustomer(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, AddressPojo newAddress) {
      servletContext.log("try to update specific shippingAddress of a customer id" + id);
      Response response = null;
      CustomerPojo updatedCustomer = customerServiceBean.setAddressFor(id, newAddress);
      response = Response.ok(updatedCustomer).build();
      return response;
    }
    /**
     * Delete Address For Customer
     * @param id of specific customer
     * @return updated value of customer
     */
    @DELETE
    @Path(CUSTOMER_ADDRESS_RESOURCE_PATH)
    public Response deleteAddresForCustomer(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to remove specific shippingAddress of a customer id" + id);
        customerServiceBean.removeAddressFor(id);
        CustomerPojo cust = customerServiceBean.getCustomerById(id);
        Response response = Response.ok(cust).build();
        return response;
    }
    /**
     * Get orders
     * @param id of specific customer
     * @return value of cusomter orders
     */
    @GET
    @Path(CUSTOMER_ORDER_RESOURCE_PATH)
    public Response getOrders(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("retrieving all orders of a customer id" + id);
        List<OrderPojo> ords = customerServiceBean.getAllOrders(id);
        Response response = Response.ok(ords).build();
        return response;
    }
    /**
     * Add Order To Customer
     * @param id of specific customer
     * @param newOrder new order to be added
     * @return updated value of orders
     */
    @POST
    @Path(CUSTOMER_ORDER_RESOURCE_PATH)
    public Response addOrderToCustomer(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, OrderPojo newOrder) {
      servletContext.log("try to create new order to a customer id" + id);
      Response response = null;
      OrderPojo newOrderWithIdTimestamps = customerServiceBean.persistOrder(id, newOrder);
      //newOrderWithIdTimestamps.getOwningCustomer().setId(id);
      response = Response.ok(newOrderWithIdTimestamps).build();
      return response;
    }

    
    //TODO - endpoints for setting up Orders/OrderLines

}