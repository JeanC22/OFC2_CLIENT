/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface UserManager {
    
    public void edit_XML(Object requestEntity, String id) throws ClientErrorException;
    
    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException;

    public <T> T find_XML(GenericType<T> responseType, String username, String password) throws ClientErrorException;

    public <T> T find_JSON(GenericType<T> responseType, String username, String password) throws ClientErrorException;

    public void create_XML(Object requestEntity) throws ClientErrorException;

    public void create_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAll_JSON(GenericType<T> responseType) throws ClientErrorException;
    
    
    
}
