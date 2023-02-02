/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
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
 * @author Aritz
 */
public class RoutineHelpWindowController {

     private Stage stage;
     
    @FXML
    private WebView webView;

  
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    private void windowShowing(WindowEvent event){
        WebEngine webEngine = webView.getEngine();
        //Load help Page
        webEngine.load(getClass()
                .getResource("/ofc2_cliente/ui/help/routineHelpWindow.html").toExternalForm());
    }
    
     public void initStage(Parent root) {
          Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnShowing(this::windowShowing);
       
        
        stage.show();
     }
    
}
