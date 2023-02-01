/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ofc2_cliente.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for help window
 *
 * @author Elias
 */
public class HelpSponsorController{
    private Stage stage;
    @FXML
    private WebView webView;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This method will start the window
     *
        * @author Elias
     * @param root
     */
    public void initStage(Parent root) {
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //title of the window: OFC SIGN IN.
        stage.setTitle("Ayuda para la Gestion de Anuncios");
        stage.setResizable(false);
        stage.setOnShowing(this::windowShowing);
        //Show window
        stage.show();

    }   
    /**
     * Initializes window state.
     * @param event Window Event
     */
    private void windowShowing(WindowEvent event){
        WebEngine webEngine = webView.getEngine();
        //Load help Page
        webEngine.load(getClass()
                .getResource("/ofc2_cliente/ui/help/helpSponsor.html").toExternalForm());
    }
    
}
