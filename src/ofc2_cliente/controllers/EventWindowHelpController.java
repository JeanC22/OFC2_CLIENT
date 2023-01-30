/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class EventWindowHelpController{

    private Stage stage;
    @FXML
    private  WebView webHelp;
    
    public void setStage(Stage stage) {

        this.stage = stage;
    }
    
    /**
     * Initializes the controller class.
     */
     public void initStage(Parent root) {

        //init the scene with the root you got from singInController
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnShowing(this::shoWindow);
        
        this.stage.show();

    }

    @FXML
    private void shoWindow(WindowEvent event) {
       WebEngine webEngine = webHelp.getEngine();
                webEngine.load(getClass().getResource("/ofc2_cliente/ui/helpEvent.html").toExternalForm());
    }
     
     
    
}
