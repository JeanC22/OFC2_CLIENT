/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface EventManager {
    
     public <T> T findAllEvents_XML(GenericType<T> responseType) throws BusinessLogicException;
     
    public <T> T findAllEvents_JSON(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findEventByActivity_XML(GenericType<T> responseType, String activity) throws BusinessLogicException;

    public <T> T findEventByActivity_JSON(GenericType<T> responseType, String activity) throws BusinessLogicException;

    public void edit_XML(Object requestEntity) throws BusinessLogicException;
    
    public void edit_JSON(Object requestEntity) throws BusinessLogicException;

    public <T> T findEventByName_XML(GenericType<T> responseType, String name) throws BusinessLogicException;

    public <T> T findEventByName_JSON(GenericType<T> responseType, String name) throws BusinessLogicException;

    public <T> T findEventByDate_XML(GenericType<T> responseType, String date) throws BusinessLogicException;
    
    public <T> T findEventByDate_JSON(GenericType<T> responseType, String date) throws BusinessLogicException;
    
    public void create_XML(Object requestEntity) throws BusinessLogicException;

    public void create_JSON(Object requestEntity) throws BusinessLogicException;

    public void remove(String id) throws BusinessLogicException;
}