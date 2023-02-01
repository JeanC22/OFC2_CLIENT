/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

import javax.ws.rs.core.GenericType;

/**
 *
 * @author Jeanpierr Caraballo
 */
interface CommentManager {

    public <T> T findOrderByLessRate_XML(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findOrderByLessRate_JSON(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findMyComments_XML(GenericType<T> responseType, String clientID) throws BusinessLogicException;

    public <T> T findMyComments_JSON(GenericType<T> responseType, String clientID) throws BusinessLogicException;

    public void editComent_XML(Object requestEntity) throws BusinessLogicException;

    public void editComent_JSON(Object requestEntity) throws BusinessLogicException;

    public <T> T findAllComents_XML(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findAllComents_JSON(GenericType<T> responseType) throws BusinessLogicException;

    public void createComent_XML(Object requestEntity) throws BusinessLogicException;

    public void createComent_JSON(Object requestEntity) throws BusinessLogicException;

    public <T> T findOrderByLastPublicate_XML(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findOrderByLastPublicate_JSON(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T find_XML(GenericType<T> responseType, String subject) throws BusinessLogicException;

    public <T> T find_JSON(GenericType<T> responseType, String subject) throws BusinessLogicException;

    public void deleteComent(String clientID, String evetID) throws BusinessLogicException;

    public <T> T findOrderByMoreRate_XML(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findOrderByMoreRate_JSON(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findOrderByMoreRecent_XML(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findOrderByMoreRecent_JSON(GenericType<T> responseType) throws BusinessLogicException;

}
