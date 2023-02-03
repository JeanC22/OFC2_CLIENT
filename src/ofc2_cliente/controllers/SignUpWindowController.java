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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ofc2_cliente.cifrado.Cifrar;
import ofc2_cliente.logic.UserFactory;
import ofc2_cliente.model.Client;
import ofc2_cliente.model.User;

/**
 * FXML Controller class
 *
 * @author iker
 * colaborator Jp, Elias
 */


public class SignUpWindowController {

    /**
     * these variables are the regular expressions that we will use to validate
     * the user data
     */
    private static String regex = "^[a-zA-Z1-9]*$";
    private static String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @FXML
    private TextField userNameTxTF;
    @FXML
    private PasswordField passwdTxPF;
    @FXML
    private TextField fullNameTxTF;
    @FXML
    private TextField eMailTxTF;
    @FXML
    private Button signUpBtn;
    @FXML
    private ImageView passwdTT;
    @FXML
    private ImageView eMailTT;
    @FXML
    private ImageView userNameTT;
    @FXML
    private ImageView fullNameTT;
    @FXML
    private Button spBtn;
    @FXML
    private Button backBtn;
    private Stage stage;
    private Boolean typeShow = true;
    @FXML
    private TextField pass_text;

    private UserFactory userFac = new UserFactory();
    private static final Logger LOGGER = Logger.getLogger("model.controllers.SingUpWindowController");

    /**
     * we create a stage with which we take from the parameter
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //Set window properties
        stage.setTitle("OFC SING UP");
        stage.setResizable(false);
        userNameTxTF.setTooltip(new Tooltip("Max characters 15"));
        passwdTxPF.setTooltip(new Tooltip("min 6 and max 12 characters"));
        pass_text.setTooltip(new Tooltip("min 6 and max 12 characters"));
        fullNameTxTF.setTooltip(new Tooltip("Max 40 characters"));
        eMailTxTF.setTooltip(new Tooltip("example@example.com"));
        Tooltip.install(pass_text, new Tooltip("min 6 and max 12 characters"));

        Tooltip.install(userNameTT, new Tooltip("Max characters 15"));
        Tooltip.install(passwdTT, new Tooltip("min 6 and max 12 characters"));
        Tooltip.install(fullNameTT, new Tooltip("Max 40 characters"));
        Tooltip.install(eMailTT, new Tooltip("example@example.com"));

        /**
         * these are the actions of the window with the method references
         */
        spBtn.setOnAction(this::showPasswd);
        signUpBtn.setOnAction(this::signUp);
        backBtn.setOnAction(this::backBtn);
        stage.setOnCloseRequest(this::cerrarVentana);
        //Show window
        this.stage.show();

    }

    /**
     * this method validates that the data is correct and then sends a user to
     * register.
     *
     * @param event
     */
    private void signUp(ActionEvent event) {
        try {
            User user = new Client();
            Cifrar cif = new Cifrar();
            
            
                if (!typeShow) {
                    passwdTxPF.setText(pass_text.getText());
                    passwdTxPF.setVisible(true);
                    pass_text.setVisible(false);
                    typeShow = true;
                }
                
            /**
             * Validate that the userName ,password ,fullName and eMail fields
             * are filled in. If they are not informed, an error message is
             * displayed.
             */
            if (this.userNameTxTF.getText().trim().equalsIgnoreCase("")
                    || this.passwdTxPF.getText().trim().equalsIgnoreCase("")
                    || this.eMailTxTF.getText().trim().equalsIgnoreCase("")
                    || this.fullNameTxTF.getText().trim().equalsIgnoreCase("")) {

                throw new Exception("Uno de los campos no esta informado");

            }
            /**
             * Validate userName length max. 15 characters and no special
             * characters if it is longer than 15 characters or has special
             * characters, an error is displayed.
             */
            if (!this.userNameTxTF.getText().matches(regex)
                    || this.userNameTxTF.getText().length() > 15) {

                throw new Exception("El campo UserName tiene caracteres "
                        + "especiales o te has pasado de el limite de "
                        + "caracteres permitidos(max 15)");

            }
            /**
             * Validate that the password length is a minimum of 6 characters
             * and a maximum of 12 characters,if it is less than 6 characters an
             * error message will be displayed.
             */
            if (this.passwdTxPF.getText().length() < 6
                    || this.passwdTxPF.getText().length() > 12) {

                throw new Exception("El campo Password tiene que ser de minimo 6 "
                        + "caracteres y maximo de 12 caracteres");

            }
            /**
             * Validate that the length of the fullname is 40 characters
             * maximum,If it is longer than 40 characters an error message will
             * be displayed.
             */
            if (!this.fullNameTxTF.getText().matches(regex)
                    || this.fullNameTxTF.getText().length() > 40) {

                throw new Exception("El campo Fullname tiene "
                        + "caracteres especiales o te has pasado de el limite de "
                        + "caracteres permitidos(max 40)");

            }
            /**
             * Validate that the format of the eMail(eMailTxTF) by means of an
             * Email pattern, if it does not have the correct format an error
             * message will be displayed.
             */
            if (!this.eMailTxTF.getText().matches(regexEmail)) {

                throw new Exception("El campo email notiene el formato adecuado"
                        + "(example@example.com)");
            }
            user.setUsername(this.userNameTxTF.getText());
            //user.setPassword(cif.cifrarTexto(passwdTxPF.getText()));
            user.setPassword(passwdTxPF.getText());
            user.setFullName(this.fullNameTxTF.getText());
            user.setEmail(this.eMailTxTF.getText());
            
            
           
            try {
                
                //userFac.getFactory().create_XML(user);
                Stage primaryStage = new Stage();

                //link to get the FXML file
                URL viewLink = getClass().getResource("/model/views/SignInWindow.fxml");
                //initialization the loader witk the FXML file
                FXMLLoader loader = new FXMLLoader(viewLink);
                //initialization the root (Parent) with the FXML Loader.load
                Parent root = (Parent) loader.load();
                //initialization the singInController
                SignInWindowController mainStageController
                        = ((SignInWindowController) loader.getController());
                //set the Stage to the controll
                mainStageController.setStage(primaryStage);
                //Start the Stage
                mainStageController.initStage(root);

                //close the actually View
                this.stage.close();

            } catch (Exception ex) {
                LOGGER.severe(ex.getMessage());
                throw new Exception(ex.getMessage());

            }
            

        } catch (Exception e) {
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, e);
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            setEmptyAllField();
        }

    }

    /**
     * This method is to send us to the previous window when we click on the
     * arrow.
     *
     * @param event
     */
    private void backBtn(ActionEvent event) {

        try {
            Stage primaryStage = new Stage();

            URL a = getClass().getResource("/model/views/SignInWindow.fxml");
            System.out.println(a);
            FXMLLoader loader = new FXMLLoader(a);
            Parent root = (Parent) loader.load();
            SignInWindowController mainStageController
                    = ((SignInWindowController) loader.getController());
            mainStageController.setStage(primaryStage);
            mainStageController.initStage(root);
            this.stage.close();
            //inicializar la scena
        } catch (IOException ex) {
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method when we want to exit the application will ask us for
     * confirmation to exit.
     *
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
     * @param event
     * @author Jp
     * when typeShow is true this will be set
     * Visible passwordField when typeShow is false the passwd will ve set
     * visible
     * @return typeShow (boolean)
     */
    public Boolean showPasswd(ActionEvent event) {

        if (typeShow) {
            pass_text.setText(passwdTxPF.getText());
            pass_text.setVisible(true);
            passwdTxPF.setVisible(false);

            return typeShow = false;
        }
        passwdTxPF.setText(pass_text.getText());
        passwdTxPF.setVisible(true);
        pass_text.setVisible(false);
        return typeShow = true;
    }

    public void setEmptyAllField() {
        userNameTxTF.setText("");
        passwdTxPF.setText("");
        pass_text.setText("");
        fullNameTxTF.setText("");
        eMailTxTF.setText("");
    }

}
