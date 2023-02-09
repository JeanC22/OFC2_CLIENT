/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ofc2_cliente.model.User;

/**
 * FXML Controller class
 *
 * @author DarkD
 */
public class ProfileWindowController {
    
    @FXML
    private Pane OFC_LOGED;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label fullnameLabel;
    @FXML
    private Button changePasswdBtn;
    private static final Logger LOGGER = Logger.getLogger(ProfileWindowController.class.getName());
    private Stage stage;
    private User user;
    @FXML
    private Button backBtn;

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
        //get the user from the singInController and init on the local User
        //set the Welcome Message
        stage.setOnCloseRequest(this::cerrarVentana);
        stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.ico").toString()));
        
        usernameLabel.setText(this.user.getUsername());
        emailLabel.setText(this.user.getEmail());
        fullnameLabel.setText(this.user.getFullName());
        changePasswdBtn.setOnAction(this::openModalChangePasswd);
        backBtn.setOnAction(this::closeWindow);
        stage.setTitle("OFC Profile View");
        stage.show();
        LOGGER.info("Stage Started");
    }

    /**
     * This Method get the userLogin from the SignInWindow
     *
     * @param userLogin
     */
    public void getUser(User userLogin) {
        this.user = userLogin;
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

    /**
     * This Method confirm if the user want to close the window
     *
     * @author JP
     * @param e
     */
    private void openModalChangePasswd(ActionEvent e) {
        try {
            Stage modalStage = new Stage();
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/changePasswordModal.fxml");
            FXMLLoader loader = new FXMLLoader(viewLink);
            Parent rootModal = (Parent) loader.load();
            ChangePasswordModalController modalStageControllerProfile
                    = ((ChangePasswordModalController) loader.getController());
            LOGGER.info("sending the comment");
            modalStageControllerProfile.getUser(this.user);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStageControllerProfile.setStage(modalStage);
            modalStageControllerProfile.initStage(rootModal);
            modalStage.show();
            
            LOGGER.info("Method open modal finished");
            
        } catch (IOException ex) {
            Logger.getLogger(CommentWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        
    }
    
    public void setDataUser(WindowEvent event) {
        usernameLabel.setText(user.getUsername());
        
    }
    
    private void closeWindow(ActionEvent event) {
        try {
            //Gonna initialition a new Stage
            Stage mainStage = new Stage();
            // we gonna create a URL for get the fxml view
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/LogedWindow.fxml");

            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.ico").toString()));
            //Get the controller
            LogedWindowController logedWindowController
                    = ((LogedWindowController) loader.getController());
            logedWindowController.getUser(this.user);
            //set the stage
            logedWindowController.setStage(mainStage);
            //start the stage
            logedWindowController.initStage(root);
            //close the actually View
            this.stage.close();
            event.consume();
        } catch (IOException ex) {
            Logger.getLogger(LogedWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        this.stage.close();
        event.consume();
    }
}
