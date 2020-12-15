/*****************************************************************c******************o*******v******id********
 * File: OrderSystemTestSuite.java
 * Course materials (20F) CST 8277
 * 
 * @author (original) Mike Norman
 * update by : Stewart King 040 793 799
 * Date : 2020 Dec.6
 */
package com.algonquincollege.cst8277;

import static com.algonquincollege.cst8277.utils.MyConstants.APPLICATION_API_VERSION;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_ADMIN_USER;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PREFIX;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ADDRESS_RESOURCE_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ADDRESS_SUBRESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.ORDER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ORDER_RESOURCE_PATH;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.CoreMatchers.*;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.algonquincollege.cst8277.models.AddressPojo;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.OrderPojo;
import com.algonquincollege.cst8277.models.ShippingAddressPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Description: JUnit test class
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class OrderSystemTestSuite {
    /**
     * Class<?> _thisClaz
     */
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    /**
     * Logger logger
     */
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);
    /**
     * String APPLICATION_CONTEXT_ROOT
     */
    static final String APPLICATION_CONTEXT_ROOT = "rest-orderSystem";
    /**
     * String HTTP_SCHEMA
     */
    static final String HTTP_SCHEMA = "http";
    /**
     * String HOST
     */
    static final String HOST = "localhost";
    
    //TODO - if you changed your Payara's default port (to say for example 9090)
    //       your may need to alter this constant
    /**
     * int PORT
     */
    static final int PORT = 8080;

    // test fixture(s)
    /**
     * URI uri
     */
    static URI uri;
    /**
     * HttpAuthenticationFeature adminAuth
     */
    static HttpAuthenticationFeature adminAuth;
    /**
     * HttpAuthenticationFeature userAuth
     */
    static HttpAuthenticationFeature userAuth;

    /**
     * oneTimeSetUp Method
     * Description: builds uri, adminAuth, userAuth
     */
    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_USER_PREFIX, DEFAULT_USER_PASSWORD);
    }
    /**
     * WebTarget webTarget
     */
    protected WebTarget webTarget;
    
    /**
     * setUp Method
     * Description: builds client, webTarget
     */
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

    /**
     * test01_all_customers_with_adminrole Test Method
     * Description: tests customer/ GET
     */
    @Test
    public void test01_all_customers_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            //.register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<CustomerPojo> custs = response.readEntity(new GenericType<List<CustomerPojo>>(){});
        assertThat(custs, is(not(empty())));
        //TODO - depending on what is in your Db when you run this, you may need to change the next line
        assertThat(custs, hasSize(3));
    }

    // TODO - create39 more test-cases that send GET/PUT/POST/DELETE messages
    // to REST'ful endpoints for the OrderSystem entities using the JAX-RS
    // ClientBuilder APIs

    /**
     * test02_Customer_get1 Test Method
     * Description: tests customer/{id} GET
     */
    @Test
    public void test02_Customer_get1() throws JsonMappingException, JsonProcessingException {

        Response response = webTarget
            .path("customer/1")
            .request().get();
        assertThat(response.getStatus(), is(200));
        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getFirstName(), is("John1"));
        assertThat(cust.getOrders().size(), is(3));
    }

    /**
     * test03_Customer_get2 Test Method
     * Description: tests customer/{id} GET
     */
    @Test
    public void test03_Customer_get2() throws JsonMappingException, JsonProcessingException {

        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME + "/1")
            .request().get();
        assertThat(response.getStatus(), is(200));
        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getFirstName(), is("John1"));
        assertThat(cust.getOrders().size(), is(3));
    }

    /**
     * test04_Customer_get3 Test Method
     * Description: tests customer/{id} GET
     */
    @Test
    public void test04_Customer_get3() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 2)
            .request().get();
        assertThat(response.getStatus(), is(200));
        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getFirstName(), is("John2"));
        assertThat(cust.getOrders().size(), is(2));
    }

    /**
     * test05_Customer_get4 Test Method
     * Description: tests customer/{id} GET
     */
    @Test
    public void test05_Customer_get4() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .path("customer/{"+RESOURCE_PATH_ID_ELEMENT+"}")
            .resolveTemplate("id", 2)
            .request().get();
        assertThat(response.getStatus(), is(200));
        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getEmail(), is("email2.com"));
        assertThat(cust.getOrders().size(), is(2));
    }

    /**
     * test06_Customer_get5 Test Method
     * Description: tests customer/{id} GET
     */
    @Test
    public void test06_Customer_get5() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .path("customer/{id}")
            .resolveTemplate("id", 3)
            .request().get();
        assertThat(response.getStatus(), is(200));
        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getEmail(), is("email3.com"));
        assertThat(cust.getOrders().size(), is(0));
    }

    /**
     * test07_Customer_put1 Test Method
     * Description: tests customer/{id} PUT
     */
    @Test
    public void test07_Customer_put1() throws JsonMappingException, JsonProcessingException {

        CustomerPojo newcustomer = new CustomerPojo();
        newcustomer.setId(3);
        newcustomer.setFirstName("putcustomer3");
        newcustomer.setVersion(0);

        webTarget
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
            .request().put(Entity.json(newcustomer));

        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
            .request().get();

        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getFirstName(), is("putcustomer3"));
    }

    /**
     * test08_Customer_put2 Test Method
     * Description: tests customer/{id} PUT
     */
    @Test
    public void test08_Customer_put2() throws JsonMappingException, JsonProcessingException {

        CustomerPojo newcustomer = new CustomerPojo();
        newcustomer.setId(2);
        newcustomer.setLastName("putlastname2");
        newcustomer.setVersion(0);

        webTarget
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 2)
            .request().put(Entity.json(newcustomer));

        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 2)
            .request().get();

        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getLastName(), is("putlastname2"));
    }

    /**
     * test09_Customer_post1 Test Method
     * Description: tests customer POST
     */
    @Test
    public void test09_Customer_post1() throws JsonMappingException, JsonProcessingException {

        CustomerPojo newcustomer = new CustomerPojo();
        newcustomer.setFirstName("postcustomer1");
        webTarget
            .path(CUSTOMER_RESOURCE_NAME)
            .request().post(Entity.json(newcustomer));

        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME)
            .request()
            .get();
        List<CustomerPojo> custs = response.readEntity(new GenericType<List<CustomerPojo>>(){});
        assertThat(custs, hasSize(4));
    }

    /**
     * test10_Customer_post2 Test Method
     * Description: tests customer POST
     */
    @Test
    public void test10_Customer_post2() throws JsonMappingException, JsonProcessingException {

        CustomerPojo newcustomer = new CustomerPojo();
        newcustomer.setFirstName("postcustomer2");
        webTarget
            .path(CUSTOMER_RESOURCE_NAME)
            .request().post(Entity.json(newcustomer));

        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME)
            .request()
            .get();
        List<CustomerPojo> custs = response.readEntity(new GenericType<List<CustomerPojo>>(){});
        assertThat(custs, hasSize(5));
    }

    /**
     * test11_Customer_delete1 Test Method
     * Description: tests customer/{id} DELETE
     */
    @Test
    public void test11_Customer_delete1() throws JsonMappingException, JsonProcessingException {
        webTarget
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 5)
            .request().delete();

          Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME)
            .request()
            .get();
        List<CustomerPojo> custs = response.readEntity(new GenericType<List<CustomerPojo>>(){});
        assertThat(custs, hasSize(4));
    }

    /**
     * test12_Customer_delete2 Test Method
     * Description: tests customer/{id} DELETE
     */
    @Test
    public void test12_Customer_delete2() throws JsonMappingException, JsonProcessingException {
        webTarget
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 4)
            .request().delete();

          Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME)
            .request()
            .get();
        List<CustomerPojo> custs = response.readEntity(new GenericType<List<CustomerPojo>>(){});
        assertThat(custs, hasSize(3));
    }

    /**
     * test13_CustomerIdShippingAdress_get1 Test Method
     * Description: tests customer/{id}/shippingAdress GET
     */
    @Test
    public void test13_CustomerIdShippingAdress_get1() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .path("customer/1/address")
            .request().get();
        assertThat(response.getStatus(), is(200));
        AddressPojo add = response.readEntity(AddressPojo.class);
        assertThat(add.getCity(), is("city2"));
    }

    /**
     * test14_CustomerIdShippingAdress_get2 Test Method
     * Description: tests customer/{id}/shippingAdress GET
     */
    @Test
    public void test14_CustomerIdShippingAdress_get2() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME + "/1/" + CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
            .request().get();
        assertThat(response.getStatus(), is(200));
        AddressPojo add = response.readEntity(AddressPojo.class);
        assertThat(add.getCountry(), is("country2"));
    }

    /**
     * test15_CustomerIdShippingAdress_get3 Test Method
     * Description: tests customer/{id}/shippingAdress GET
     */
    @Test
    public void test15_CustomerIdShippingAdress_get3() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .path("customer/{id}/address")
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 1)
            .request().get();
        assertThat(response.getStatus(), is(200));
        AddressPojo add2 = response.readEntity(AddressPojo.class);
        assertThat(add2.getStreet(), is("street2"));
    }

    /**
     * test16_CustomerIdShippingAdress_get4 Test Method
     * Description: tests customer/{id}/shippingAdress GET
     */
    @Test
    public void test16_CustomerIdShippingAdress_get4() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ADDRESS_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 1)
            .request().get();
        assertThat(response.getStatus(), is(200));
        AddressPojo add2 = response.readEntity(AddressPojo.class);
        assertThat(add2.getState(), is("state2"));
    }
    
    /**
     * test17_CustomerIdShippingAdress_put1 Test Method
     * Description: tests customer/{id}/shippingAdress PUT
     */
    @Test
    public void test17_CustomerIdShippingAdress_put1() throws JsonMappingException, JsonProcessingException {

        AddressPojo newadd = new ShippingAddressPojo();
        newadd.setId(4);

        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ADDRESS_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 1)
            .request().put(Entity.json(newadd));
        assertThat(response.getStatus(), is(200));
        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getId(), is(1));
        assertThat(cust.getShippingAddress().getId(), is(4));
    }

    /**
     * test18_CustomerIdShippingAdress_put2 Test Method
     * Description: tests customer/{id}/shippingAdress PUT
     */
    @Test
    public void test18_CustomerIdShippingAdress_put2() throws JsonMappingException, JsonProcessingException {

        AddressPojo newadd = new ShippingAddressPojo();
        newadd.setId(1); // This is billingAddress id.

        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ADDRESS_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 1)
            .request().put(Entity.json(newadd));
        assertThat(response.getStatus(), is(200));
        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getId(), is(1));
        assertThat(cust.getShippingAddress().getId(), is(5)); // New shippingAddress
    }

    /**
     * test19_CustomerIdShippingAdress_delete1 Test Method
     * Description: tests customer/{id}/shippingAdress DELETE
     */
    @Test
    public void test19_CustomerIdShippingAdress_delete1() throws JsonMappingException, JsonProcessingException {

        Response response = webTarget
            .path("customer/1/address")
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 1)
            .request().delete();

        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getShippingAddress(), is(nullValue()));
    }

    /**
     * test20_CustomerIdShippingAdress_delete2 Test Method
     * Description: tests customer/{id}/shippingAdress DELETE
     */
    @Test
    public void test20_CustomerIdShippingAdress_delete2() throws JsonMappingException, JsonProcessingException {

        Response response = webTarget
            .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ADDRESS_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 2)
            .request().delete();

        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        assertThat(cust.getShippingAddress(), is(nullValue()));
    }

    /**
     * test21_CustomerIdOrder_get1 Test Method
     * Description: tests customer/{id}/order GET
     */
    @Test
    public void test21_CustomerIdOrder_get1() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .path("customer/1/order")
            .request().get();
        assertThat(response.getStatus(), is(200));
        List<OrderPojo> ords = response.readEntity(new GenericType<List<OrderPojo>>(){});
        assertThat(ords, hasSize(3));
        assertThat(ords.get(0).getId(), is(1));
    }

    /**
     * test22_CustomerIdOrder_get2 Test Method
     * Description: tests customer/{id}/order GET
     */
      @Test
      public void test22_CustomerIdOrder_get2() throws JsonMappingException, JsonProcessingException {
          Response response = webTarget
              .path(CUSTOMER_RESOURCE_NAME + "/1/" + ORDER_RESOURCE_NAME)
              .request().get();
          assertThat(response.getStatus(), is(200));
          List<OrderPojo> ords = response.readEntity(new GenericType<List<OrderPojo>>(){});
          assertThat(ords, hasSize(3));
          assertThat(ords.get(1).getDescription(), is("book2"));
      }

      /**
       * test23_CustomerIdOrder_get3 Test Method
       * Description: tests customer/{id}/order GET
       */
      @Test
      public void test23_CustomerIdOrder_get3() throws JsonMappingException, JsonProcessingException {
          Response response = webTarget
              .path("customer/{id}/order")
              .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 2)
              .request().get();
          assertThat(response.getStatus(), is(200));
          List<OrderPojo> ords2 = response.readEntity(new GenericType<List<OrderPojo>>(){});
          assertThat(ords2, hasSize(2));
          assertThat(ords2.get(0).getId(), is(4));
      }

      /**
       * test24_CustomerIdOrder_get4 Test Method
       * Description: tests customer/{id}/order GET
       */
      @Test
      public void test24_CustomerIdOrder_get4() throws JsonMappingException, JsonProcessingException {
          Response response = webTarget
              .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ORDER_RESOURCE_PATH)
              .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 2)
              .request().get();
          assertThat(response.getStatus(), is(200));
          List<OrderPojo> ords2 = response.readEntity(new GenericType<List<OrderPojo>>(){});
          assertThat(ords2, hasSize(2));
          assertThat(ords2.get(1).getDescription(), is("grocery2"));
      }
      
      /**
       * test25_CustomerIdOrder_get5 Test Method
       * Description: tests customer/{id}/order GET
       */
      @Test
      public void test25_CustomerIdOrder_get5() throws JsonMappingException, JsonProcessingException {
          Response response = webTarget
              .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ORDER_RESOURCE_PATH)
              .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
              .request().get();
          assertThat(response.getStatus(), is(200));
          List<OrderPojo> ords2 = response.readEntity(new GenericType<List<OrderPojo>>(){});
          assertThat(ords2, hasSize(0));
  }

          
}