/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;


import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface RoutineInterface {
  

    public <T> T consultRoutinesByName_XML(Class<T> responseType, String name) throws BusinessLogicException;

    public <T> T consultRoutinesByName_JSON(Class<T> responseType, String name) throws BusinessLogicException;

    public void edit_XML(Object requestEntity) throws BusinessLogicException;

    public void edit_JSON(Object requestEntity) throws BusinessLogicException;

    public <T> T consultRoutineById_XML(Class<T> responseType, String id) throws BusinessLogicException;

    public <T> T consultRoutineById_JSON(Class<T> responseType, String id) throws BusinessLogicException;

    public void create_XML(Object requestEntity) throws BusinessLogicException;

    public void create_JSON(Object requestEntity) throws BusinessLogicException;

    public <T> T consultAllRoutines_XML(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T consultAllRoutines_JSON(Class<T> responseType) throws BusinessLogicException;

    public void remove(String id) throws BusinessLogicException;

    public <T> T consultAllClientRoutines_XML(Class<T> responseType, String id) throws BusinessLogicException;

    public <T> T consultAllClientRoutines_JSON(Class<T> responseType, String id) throws BusinessLogicException;

}
