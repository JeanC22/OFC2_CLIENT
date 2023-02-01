/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import ofc2_cliente.App;

/**
 * FXML Controller class
 *
 * @author iker
 */
public class MenuController {

    private Stage stage;
    @FXML
    private MenuItem eventMenu;
    @FXML
    private MenuItem routineMenu;
    @FXML
    private MenuItem CommentMenu;
    @FXML
    private MenuItem sponsorMenu;
    @FXML
    private MenuItem showProfile;
    @FXML
    private MenuItem logoutMenu;


    /**
     * setStage
     *
     * @param stage
     */
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    /**
     * this Method will start the scenario and in case of an error it will
     * display an error message per window
     *
     * @author Jp
     * @param root
     *
     */
    public void initStage(Parent root) {
        try {
            //init the scene with the root you got from singInController
            Scene scene = new Scene(root);
            stage.setScene(scene);
            eventMenu.setOnAction(this::showEventWindow);
            routineMenu.setOnAction(this::showRoutineWindow);
            sponsorMenu.setOnAction(this::showSponsorWindow);
            CommentMenu.setOnAction(this::showCommentWindow);
            
            showProfile.setOnAction(this::showProfileWindow);
            

            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El servidor glashfish no se encuentra disponible", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void showEventWindow(ActionEvent event) {
        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/ventanaModificarEvento.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            CreateModifyController mainStageController
                    = ((CreateModifyController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);

            //start the stage
            mainStageController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(CreateModifyController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    public void showRoutineWindow(ActionEvent event) {
        try {
            
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/ventanaModificarEvento.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            CreateModifyController mainStageController
                    = ((CreateModifyController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);

            //start the stage
            mainStageController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(CreateModifyController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    
    public void showSponsorWindow(ActionEvent event) {
        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/ventanaModificarEvento.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            CreateModifyController mainStageController
                    = ((CreateModifyController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);

            //start the stage
            mainStageController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(CreateModifyController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    public void showCommentWindow(ActionEvent event) {
        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/ventanaModificarEvento.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            CreateModifyController mainStageController
                    = ((CreateModifyController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);

            //start the stage
            mainStageController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(CreateModifyController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    public void showProfileWindow(ActionEvent event){
        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/ventanaModificarEvento.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            CreateModifyController mainStageController
                    = ((CreateModifyController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);

            //start the stage
            mainStageController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(CreateModifyController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
    /*
    public void logOut(){
            try {
                //Gonna initialition a new Stage
                Stage mainStage = new Stage();
                // we gonna create a URL for get the fxml view
                URL viewLink = getClass().getResource(
                        "/model/views/SignInWindow.fxml");
                // initialition loader
                FXMLLoader loader = new FXMLLoader(viewLink);
                //make the root with the loader
                Parent root = (Parent) loader.load();
                //Get the controller
                SingInWindowController mainStageController
                        = ((SingInWindowController) loader.getController());
                //set the stage
                mainStageController.setStage(mainStage);
                //start the stage
                mainStageController.initStage(root);
                //close the actually View
                this.stage.close();
            } catch (IOException ex) {
                Logger.getLogger(LogedWindowController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
    }*/
    
    
}
