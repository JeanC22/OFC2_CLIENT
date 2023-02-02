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
public class RoutineInterfaceFactory {
    
    public RoutineInterface createRoutineManager(){
        
         RoutineInterface r= new RoutineRESTfulClient();
         return r;
  
    }
}
