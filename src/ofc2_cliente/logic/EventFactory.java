/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

import javafx.event.Event;

/**
 *
 * @author 2dam
 */
public class EventFactory{
    
    
    public EventManager getFactory(){
        
    EventManager eventmanager = new EventRESTClient();
    
    return eventmanager;
    }
}
