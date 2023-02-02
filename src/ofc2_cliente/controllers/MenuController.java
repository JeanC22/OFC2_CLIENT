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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import ofc2_cliente.logic.BusinessLogicException;
import ofc2_cliente.model.User;

/**
 * FXML Controller class
 *
 * @author iker
 */
public class MenuController {

    private User user;
    CommentWindowController c;

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
     * This Method get the userLogin from the EventWindow
     *
     * @param userLogin
     */
    public void getUser(User userLogin) {
        this.user = userLogin;
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
                    "/ofc2_cliente/ui/EventWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader

            Parent root = (Parent) loader.load();
            //Get the controller
            EventWindowController eventWindowController
                    = ((EventWindowController) loader.getController());
            eventWindowController.getUser(this.user);
            //set the stage
            eventWindowController.setStage(mainStage);
            //start the stage
            eventWindowController.initStage(root);
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
                    "/ofc2_cliente/ui/RoutineWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            RoutineController routineController
                    = ((RoutineController) loader.getController());
            //set the stage
            routineController.setStage(mainStage);
            routineController.getUser(this.user);
            //start the stage
            routineController.initStage(root);
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
                    "/ofc2_cliente/ui/SponsorWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            SponsorWindowController sponsorWindowController
                    = ((SponsorWindowController) loader.getController());
            //set the stage
            sponsorWindowController.setStage(mainStage);
            sponsorWindowController.getUser(this.user);

            //start the stage
            sponsorWindowController.initStage(root);

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
                    "/ofc2_cliente/ui/commentWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            CommentWindowController commentWindowController
                    = ((CommentWindowController) loader.getController());
            //set the stage
            commentWindowController.setStage(mainStage);
            commentWindowController.getUser(user);
            try {
                //start the stage
                commentWindowController.initStage(root);
            } catch (BusinessLogicException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(CreateModifyController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void showProfileWindow(ActionEvent event) {
        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/profileWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            ProfileWindowController profileWindowController
                    = ((ProfileWindowController) loader.getController());
            //set the stage
            profileWindowController.setStage(mainStage);
            profileWindowController.getUser(user);
            //start the stage
            profileWindowController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(CreateModifyController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
    
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
                SignInWindowController mainStageController
                        = ((SignInWindowController) loader.getController());
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
    }
}
