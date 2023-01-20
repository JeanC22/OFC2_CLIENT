/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:SponsorFacadeREST
 * [sponsor]<br>
 * USAGE:
 * <pre>
 *        SponsorRESTfulClient client = new SponsorRESTfulClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Elias
 */
public class SponsorRESTfulClient implements SponsorManager{

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/OFC_SERVER/webresources";

    public SponsorRESTfulClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("sponsor");
    }

    @Override
    public void edit_XML(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void edit_JSON(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

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

    @Override
    public void create_XML(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void create_JSON(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

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

    @Override
    public void remove(String id) throws BusinessLogicException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    @Override
    public <T> T findSponsorByName_XML(Class<T> responseType, String name) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSponsorByName/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findSponsorByName_JSON(Class<T> responseType, String name) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSponsorByName/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T findSponsorByDate_XML(Class<T> responseType, String date) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSponsorByDate/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findSponsorByDate_JSON(Class<T> responseType, String date) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSponsorByDate/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
