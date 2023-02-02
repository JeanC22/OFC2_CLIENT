/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/JerseyClient.java to edit this template
 */
package ofc2_cliente.logic;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * Jersey REST client generated for REST resource:SponsorFacadeREST
 * [sponsor]<br>
 * USAGE:
 * <pre>
 *        SponsorRestClient client = new SponsorRestClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author eliaslogacho
 */
public class SponsorRESTfulClient implements SponsorManager{

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:9090/OFC_Server/webresources";

    public SponsorRESTfulClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("sponsor");
    }

     /**
     * Update Sponsor entity XML and send it as a request to update 
     * it to the SponsorRESTful web service
     * @param requestEntity Sponsor Object
     * @throws BusinessLogicException If there is an error while processing
     */
    @Override
    public void edit_XML(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void edit_JSON(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }
    /**
     * Get Sponsor entity XML from SponsorRESTful web service
     * 
     * @param responseType The class object
     * @param id The id of the instance in the server side
     * @return The object containing data.
     * @throws BusinessLogicException If there is an error while processing
     */
    @Override
    public <T> T find_XML(Class<T> responseType, String id) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSponsor/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T find_JSON(Class<T> responseType, String id) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSponsor/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }
    /**
     * Create Sponsor entity XML and send it as a request to create it
     * to the SponsorRESTful.
     * @param requestEntity The class Object
     * @throws BusinessLogicException If there is an error while processing
     */
    @Override
    public void create_XML(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void create_JSON(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }
    /**
     * Get All Sponsor entity XML from SponsorRESTful web service
     * @param responseType The class object
     * @return List containing data
     * @throws BusinessLogicException If there is an error while processing
     */
    @Override
    public <T> T findAllSponsors_XML(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findAllSponsors_JSON(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }
    /**
     * Delete Sponsor identified by its id and send a request to 
     * the SponsorRESTful web service
     * @param id The id of the Sponsor
     * @throws BusinessLogicException If there is an error while processing
     */
    @Override
    public void remove(String id) throws BusinessLogicException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }
    /**
     * Get Sponsor by name from the SponsorRESTful web service.
     * @param responseType The class object
     * @param name The name of the Sponsor
     * @return The object containing data.
     * @throws BusinessLogicException If there is an error while processing
     */
    @Override
    public <T> T findSponsorByName_XML(GenericType<T> responseType, String name) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSponsorByName/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findSponsorByName_JSON(GenericType<T> responseType, String name) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSponsorByName/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }
    /**
     * Get Sponsor by Date from the SponsorRESTful web service.
     * @param responseType The class object
     * @param date The date of teh Sponsor
     * @return The object containing data.
     * @throws BusinessLogicException If there is an error while processing
     */
    @Override
    public <T> T findSponsorByDate_XML(GenericType<T> responseType, String date) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSponsorByDate/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findSponsorByDate_JSON(GenericType<T> responseType, String date) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSponsorByDate/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }


    public void close() {
        client.close();
    }
    
}
