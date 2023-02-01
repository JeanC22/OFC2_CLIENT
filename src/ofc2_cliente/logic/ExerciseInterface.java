/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

import javax.ws.rs.core.GenericType;

/**
 *
 * @author Aritz
 */
public interface ExerciseInterface {
    

    public <T> T consultExerciseById_XML(GenericType<T> responseType, String id) throws BusinessLogicException;

    public <T> T consultExerciseById_JSON(GenericType<T> responseType, String id) throws BusinessLogicException;

    public void edit_XML(Object requestEntity) throws BusinessLogicException;

    public void edit_JSON(Object requestEntity) throws BusinessLogicException;

    public <T> T consultAllExercises_XML(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T consultAllExercises_JSON(GenericType<T> responseType) throws BusinessLogicException;

    public void create_XML(Object requestEntity) throws BusinessLogicException;
    public void create_JSON(Object requestEntity) throws BusinessLogicException;

    public <T> T consultExerciseByName_XML(GenericType<T> responseType, String name) throws BusinessLogicException;

    public <T> T consultExerciseByName_JSON(GenericType<T> responseType, String name) throws BusinessLogicException;

    public void remove(String id) throws BusinessLogicException;
}
