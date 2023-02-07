/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ofc2_cliente.logic.CommentFactoryManager;
import ofc2_cliente.logic.CommetRESTClient;
import ofc2_cliente.logic.UserFactory;
import ofc2_cliente.model.User;

/**
 * FXML Controller class
 *
 * @author DarkD
 */
public class ChangePasswordModalController {

    @FXML
    private Pane modalComment;
    @FXML
    private Button backBtn;
    @FXML
    private TextField curremtPassword;
    @FXML
    private Button changeBTN;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField confirmPassword;
    private User user;
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(ChangePasswordModalController.class.getName());
    private UserFactory userFac = new UserFactory();

    public void initStage(Parent root) {
        LOGGER.info("Starting Stage");
        //init the scene with the root you got from singInController
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //get the user from the singInController and init on the local User
        //set the Welcome Message
        stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.ico").toString()));
        stage.setTitle("OFC Modal password change");
        backBtn.setOnAction(this::closeWindow);
        changeBTN.setOnAction(this::changePasswd);
        
        stage.show();
        LOGGER.info("Stage Started");
    }

    private void changePasswd(ActionEvent event) {
        LOGGER.info("changin pass");
        
        userFac.getFactory().edit2_XML(user, user.getId().toString(), newPassword.getText(), user.getPassword());
        
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
     * setStage
     *
     * @param stage
     */
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    private void closeWindow(ActionEvent event) {
        this.stage.close();
        event.consume();
    }

}
