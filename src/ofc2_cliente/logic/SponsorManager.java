/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

import javax.ws.rs.core.GenericType;

/**
 * Logic Interface for Sponso Manager
 * @author Elias
 */
public interface SponsorManager {
    /**
     * Update Sponsor entity XML and send it as a request to update 
     * it to the SponsorRESTful web service
     * @param requestEntity Sponsor Object
     * @throws BusinessLogicException If there is an error while processing
     */
    public void edit_XML(Object requestEntity) throws BusinessLogicException;

    public void edit_JSON(Object requestEntity) throws BusinessLogicException;
    /**
     * Get Sponsor entity XML from SponsorRESTful web service
     * 
     * @param responseType The class object
     * @param id The id of the instance in the server side
     * @return The object containing data.
     * @throws BusinessLogicException If there is an error while processing
     */
    public <T> T find_XML(Class<T> responseType, String id) throws BusinessLogicException;

    public <T> T find_JSON(Class<T> responseType, String id) throws BusinessLogicException;
    /**
     * Create Sponsor entity XML and send it as a request to create it
     * to the SponsorRESTful.
     * @param requestEntity The class Object
     * @throws BusinessLogicException If there is an error while processing
     */
    public void create_XML(Object requestEntity) throws BusinessLogicException;

    public void create_JSON(Object requestEntity) throws BusinessLogicException;
    /**
     * Get All Sponsor entity XML from SponsorRESTful web service
     * @param responseType The class object
     * @return List containing data
     * @throws BusinessLogicException If there is an error while processing
     */
    public <T> T findAllSponsors_XML(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findAllSponsors_JSON(GenericType<T> responseType) throws BusinessLogicException;
    /**
     * Delete Sponsor identified by its id and send a request to 
     * the SponsorRESTful web service
     * @param id The id of the Sponsor
     * @throws BusinessLogicException If there is an error while processing
     */
    public void remove(String id) throws BusinessLogicException;
    /**
     * Get Sponsor by name from the SponsorRESTful web service.
     * @param responseType The class object
     * @param name The name of the Sponsor
     * @return The object containing data.
     * @throws BusinessLogicException If there is an error while processing
     */
    public <T> T findSponsorByName_XML(GenericType<T> responseType, String name) throws BusinessLogicException;

    public <T> T findSponsorByName_JSON(GenericType<T> responseType, String name) throws BusinessLogicException;
    /**
     * Get Sponsor by Date from the SponsorRESTful web service.
     * @param responseType The class object
     * @param date The date of teh Sponsor
     * @return The object containing data.
     * @throws BusinessLogicException If there is an error while processing
     */
    public <T> T findSponsorByDate_XML(GenericType<T> responseType, String date) throws BusinessLogicException;

    public <T> T findSponsorByDate_JSON(GenericType<T> responseType, String date) throws BusinessLogicException;
}
