/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:ComentFacadeREST
 * [comments]<br>
 * USAGE:
 * <pre>
 *        CommetRESTClient client = new CommetRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class CommetRESTClient implements CommentManager {

    private CommetRESTClient commentRestClient;
    private final WebTarget webTarget;
    private static final Logger LOGGER = Logger.getLogger(CommetRESTClient.class.getName());

    private final Client client;
    private static final String BASE_URI
            = ResourceBundle.getBundle("ofc2_cliente.config.RESTful")
                    .getString("RESTful.baseURI");

    public CommetRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("comments");
    }

    @Override
    public <T> T findOrderByLessRate_XML(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path("findOrderByLessRate");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findOrderByLessRate_JSON(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path("findOrderByLessRate");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T findMyComments_XML(GenericType<T> responseType, String clientID) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMyComments/{0}", new Object[]{clientID}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findMyComments_JSON(GenericType<T> responseType, String clientID) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMyComments/{0}", new Object[]{clientID}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override

    public void editComent_XML(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void editComent_JSON(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public <T> T findAllComents_XML(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findAllComents_JSON(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public void createComent_XML(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void createComent_JSON(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public <T> T findOrderByLastPublicate_XML(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path("findOrderByLastPublicate");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findOrderByLastPublicate_JSON(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path("findOrderByLastPublicate");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T find_XML(GenericType<T> responseType, String subject) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findBySubject/{0}", new Object[]{subject}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override

    public <T> T find_JSON(GenericType<T> responseType, String subject) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findBySubject/{0}", new Object[]{subject}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override

    public void deleteComent(String clientID, String evetID) throws BusinessLogicException {
        webTarget.path(java.text.MessageFormat.format("deleteComent/{0}/{1}", new Object[]{clientID, evetID})).request().delete();
    }

    @Override
    public <T> T findOrderByMoreRate_XML(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path("findOrderByMoreRate");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findOrderByMoreRate_JSON(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path("findOrderByMoreRate");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T findOrderByMoreRecent_XML(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path("findOrderByMoreRecent");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findOrderByMoreRecent_JSON(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path("findOrderByMoreRecent");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }


    public void close() {
        client.close();
    }

}
