/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.logic;

/**
 *
 * @author Jeanpierr Caraballo
 */
public class CommentFactoryManager {

    public CommentFactoryManager() {

    }

    public CommetRESTClient getFactory() {

        CommetRESTClient comentREST = new CommetRESTClient();
        return comentREST;

    }

}
