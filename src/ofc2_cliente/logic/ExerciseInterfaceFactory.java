/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

/**
 *
 * @author Aritz
 */
public class ExerciseInterfaceFactory {

    public ExerciseInterface createExerciseManager() {

        ExerciseInterface r = (ExerciseInterface) new ExerciseRESTfulClient();
        return r;
    }
}
