/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:ExerciseFacadeREST
 * [entities.exercise]<br>
 * USAGE:
 * <pre>
        ExerciseRESTfulClient client = new ExerciseRESTfulClient();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author Aritz
 */
public class ExerciseRESTfulClient implements ExerciseInterface{

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("ofc2_cliente.PropertiesFile")
                          .getString("URL");//"http://localhost:9616/OFC_Server/webresources";

    public ExerciseRESTfulClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.exercise");
    }

    

    public void close() {
        client.close();
    }

    @Override
    public <T> T consultExerciseById_XML(GenericType<T> responseType, String id) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("consultExerciseById/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T consultExerciseById_JSON(GenericType<T> responseType, String id) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("consultExerciseById/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
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
    public <T> T consultAllExercises_XML(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T consultAllExercises_JSON(GenericType<T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
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
    public <T> T consultExerciseByName_XML(GenericType<T> responseType, String name) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("consultExerciseByName/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T consultExerciseByName_JSON(GenericType<T> responseType, String name) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("consultExerciseByName/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public void remove(String id) throws BusinessLogicException {
        webTarget.path(java.text.MessageFormat.format("delete/{0}", new Object[]{id})).request().delete();
    }

    
    
}
