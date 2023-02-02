/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    private ImageView changePasswdBtn;
    private static final Logger LOGGER = Logger.getLogger(ProfileWindowController.class.getName());
    private Stage stage;
    private User user;

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

    public void setDataUser(WindowEvent event) {
        usernameLabel.setText(user.getUsername());

    }

}
