package ofc2_cliente.controllers;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jeanpierr Caraballo
 */
public class ComentWindowHelpController {

    private Stage stage;
    @FXML
    private WebView webHelp;

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     * @param root
     */
    public void initStage(Parent root) {

        //init the scene with the root you got from singInController
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setOnShowing(this::shoWindow);
        stage.setTitle("helpComment");

        this.stage.show();

    }

    private void shoWindow(WindowEvent event) {
        WebEngine webEngine = webHelp.getEngine();
        webEngine.load(getClass().getResource("/ofc2_cliente/ui/help/helpComment.html").toExternalForm());
    }

}
