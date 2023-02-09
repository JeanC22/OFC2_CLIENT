/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ofc2_cliente.logic.BusinessLogicException;
import ofc2_cliente.model.User;

/**
 * FXML Controller class
 *
 * @author Jp
 */
public class LogedWindowController {
    private User user;
    private Stage stage;
    @FXML
    private Label profileLabel;
    private Label welcomeString;
    @FXML
    private Label logoutLabel;
    @FXML
    private ImageView profileImage;
    @FXML
    private ImageView logoutImage;
    private static final Logger LOGGER = Logger.getLogger("model.controllers.LogedWindowController");
    @FXML
    private Pane OFC_LOGED;
    @FXML
    private Button comentBTN;
    @FXML
    private Button sponsorBTN;
    @FXML
    private Button routineBTN;
    @FXML
    private Button eventBTN;

    /**
     * setStage
     *
     * @param stage
     */
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    /**
     * this Method will start the stage
     *
     * @author Jp
     * @param root
     */
    public void initStage(Parent root) {
        LOGGER.info("Starting Stage");
        //init the scene with the root you got from singInController
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("OFC SIGN IN");
        //set the Welcome Message
        stage.setOnCloseRequest(this::cerrarVentana);
        logoutImage.setOnMouseClicked(this::logout);
        logoutLabel.setOnMouseClicked(this::logout);
        profileImage.setOnMouseClicked(this::openPorfileWindow);
        profileLabel.setOnMouseClicked(this::openPorfileWindow);
        eventBTN.setOnMouseClicked(this::openEventWindow);
        comentBTN.setOnMouseClicked(this::openCommentWindow);
        sponsorBTN.setOnMouseClicked(this::openSponsorWindow);
        routineBTN.setOnMouseClicked(this::openRoutineWindow);
        stage.show();
        LOGGER.info("Stage Started");
    }

    /**
     * this Method will be close LogedWindow and start the SingInWindow
     *
     * @author Jp
     * @param event
     */
    @FXML
    public void logout(MouseEvent event) {
        try {
            //Gonna initialition a new Stage
            Stage mainStage = new Stage();
            // we gonna create a URL for get the fxml view
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/SignInWindow.fxml");

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
            event.consume();
        } catch (IOException ex) {
            Logger.getLogger(LogedWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This Method confirm if the user want to close the window
     *
     * @author Iker
     * @param event
     */
    public void cerrarVentana(WindowEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Quiere salir de la aplicacion?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    public void openPorfileWindow(MouseEvent event) {
        try {
            //Gonna initialition a new Stage
            Stage mainStage = new Stage();
            // we gonna create a URL for get the fxml view
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/profileWindow.fxml");

            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.ico").toString()));
            //Get the controller
            ProfileWindowController profileStageController
                    = ((ProfileWindowController) loader.getController());
            profileStageController.getUser(this.user);
            //set the stage
            profileStageController.setStage(mainStage);
            //start the stage
            profileStageController.initStage(root);
            //close the actually View
            this.stage.close();
            event.consume();
        } catch (IOException ex) {
            Logger.getLogger(LogedWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void openEventWindow(MouseEvent event) {
        try {
            //Gonna initialition a new Stage
            Stage mainStage = new Stage();
            // we gonna create a URL for get the fxml view
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/EventWindow.fxml");

            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.ico").toString()));
            //Get the controller
            EventWindowController eventWindowController
                    = ((EventWindowController) loader.getController());
            eventWindowController.getUser(this.user);
            //set the stage
            eventWindowController.setStage(mainStage);
            //start the stage
            eventWindowController.initStage(root);
            //close the actually View
            this.stage.close();
            event.consume();
        } catch (IOException ex) {
            Logger.getLogger(LogedWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void openCommentWindow(MouseEvent event) {
        try {
            //Gonna initialition a new Stage
            Stage mainStage = new Stage();
            // we gonna create a URL for get the fxml view
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/commentWindow.fxml");

            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.ico").toString()));
            //Get the controller
            CommentWindowController commentWindowController
                    = ((CommentWindowController) loader.getController());
            commentWindowController.getUser(this.user);
            //set the stage
            commentWindowController.setStage(mainStage);
            try {
                //start the stage
                commentWindowController.initStage(root);
            } catch (BusinessLogicException ex) {
                Logger.getLogger(LogedWindowController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR, "El servidor glashfish no se encuentra disponible", ButtonType.OK);
                alert.showAndWait();
            }
            //close the actually View
            this.stage.close();
            event.consume();
        } catch (IOException ex) {
            Logger.getLogger(LogedWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void openSponsorWindow(MouseEvent event) {
        try {
            //Gonna initialition a new Stage
            Stage mainStage = new Stage();
            // we gonna create a URL for get the fxml view
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/SponsorWindow.fxml");

            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.ico").toString()));
            //Get the controller
            SponsorWindowController sponsorWindowController
                    = ((SponsorWindowController) loader.getController());
            sponsorWindowController.getUser(this.user);
            //set the stage
            sponsorWindowController.setStage(mainStage);
            //start the stage
            sponsorWindowController.initStage(root);
            //close the actually View
            this.stage.close();
            event.consume();
        } catch (IOException ex) {
            Logger.getLogger(LogedWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void openRoutineWindow(MouseEvent event) {
        try {
            //Gonna initialition a new Stage
            Stage mainStage = new Stage();
            // we gonna create a URL for get the fxml view
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/RoutineWindow.fxml");

            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.ico").toString()));
            //Get the controller
            RoutineController routineController
                    = ((RoutineController) loader.getController());
            routineController.getUser(this.user);
            //set the stage
            routineController.setStage(mainStage);
            //start the stage
            routineController.initStage(root);
            //close the actually View
            this.stage.close();
        } catch (IOException ex) {
            Logger.getLogger(LogedWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This Method get the userLogin from the SignInWindow
     *
     * @param userLogin
     */
    public void getUser(User userLogin) {
        this.user = userLogin;
    }
}
