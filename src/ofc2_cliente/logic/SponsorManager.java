/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

import javax.ws.rs.core.GenericType;

/**
 *
 * @author Elias
 */
public interface SponsorManager {
     public void edit_XML(Object requestEntity) throws BusinessLogicException;

    public void edit_JSON(Object requestEntity) throws BusinessLogicException;

    public <T> T find_XML(Class<T> responseType, String id) throws BusinessLogicException;

    public <T> T find_JSON(Class<T> responseType, String id) throws BusinessLogicException;

    public void create_XML(Object requestEntity) throws BusinessLogicException;

    public void create_JSON(Object requestEntity) throws BusinessLogicException;

    public <T> T findAllSponsors_XML(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findAllSponsors_JSON(GenericType<T> responseType) throws BusinessLogicException;

    public void remove(String id) throws BusinessLogicException;

    public <T> T findSponsorByName_XML(Class<T> responseType, String name) throws BusinessLogicException;

    public <T> T findSponsorByName_JSON(Class<T> responseType, String name) throws BusinessLogicException;
    
    public <T> T findSponsorByDate_XML(Class<T> responseType, String date) throws BusinessLogicException;

    public <T> T findSponsorByDate_JSON(Class<T> responseType, String date) throws BusinessLogicException;
}
