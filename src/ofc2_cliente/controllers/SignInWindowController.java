/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ofc2_cliente.controllers;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import ofc2_cliente.logic.UserFactory;
import ofc2_cliente.model.User;

/**
 * FXML Controller class
 *
 * @author Elias
 */
public class SignInWindowController {

    private static String regex = "^[a-zA-Z1-9]*$";
    private Stage stage;
    @FXML
    private ImageView imageView;
    @FXML
    private Button singInBtn;
    @FXML
    private TextField userNameTxTF;
    @FXML
    private PasswordField passwdTxPF;
    @FXML
    private ImageView passwrdTT;
    @FXML
    private ImageView usernameTT;
    @FXML
    private Hyperlink signUpLink;
    private User userLoged;

    private UserFactory userFac = new UserFactory();
    private static final Logger LOGGER = Logger.getLogger(SignInWindowController.class.getName());

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
        LOGGER.info("Incializando la ventana de Sign In");
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/ofc2_cliente/ui/resources/style.css").toExternalForm());
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //Set window properties
        stage.setTitle("OFC SING IN");
        stage.setResizable(false);
        stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.png").toString()));
        stage.setOnShowing(this::windowShowing);
        singInBtn.setOnAction(this::signIn);
        signUpLink.setOnAction(this::singUpWindow);
        stage.setOnCloseRequest(this::cerrarVentana);
        //Show window
        stage.show();
    }

    /**
     * Window event method when start the Sign In Window
     *
     * @author Elias
     * @param event The window event
     */
    private void windowShowing(WindowEvent event) {
        userNameTxTF.requestFocus();
        //Tooltip en los campos de usuario, contraseña y el link
        userNameTxTF.setTooltip(new Tooltip("max 15 characters"));
        Tooltip.install(usernameTT, new Tooltip("max 15 characters"));
        passwdTxPF.setTooltip(new Tooltip("min 6 max 12 characters"));
        Tooltip.install(passwrdTT, new Tooltip("min 6 max 12 characters"));
        signUpLink.setTooltip(new Tooltip("Click para abrir la ventana de registro"));
    }

    /**
     * Action event for Hyper link to open the window Sign Up.
     *
     * @author Jp
     * @param event Action event
     */
    @FXML
    private void singUpWindow(ActionEvent event) {
        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/SignUpWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            SignUpWindowController mainStageController
                    = ((SignUpWindowController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);
            //start the stage
            mainStageController.initStage(root);
            this.stage.close();
        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Action event for button SignIn. If fields user name and password is
     * filled, show error message. Otherwise, open the window Loged.
     *
     * @collaborators Elias, Iker and Jp
     * @param event
     */
    private void signIn(ActionEvent event) {
        try {
            if (userNameTxTF.getText().isEmpty() || passwdTxPF.getText().isEmpty()) {
                throw new Exception("Todos los campos no estan informados");
            }
            if (userNameTxTF.getText().length() > 15) {
                throw new Exception("La longitud del "
                        + "campo user supera los 15 caracteres");
            }
            if (!userNameTxTF.getText().matches(regex)) {
                new Alert(AlertType.ERROR,
                        "El campo contiene"
                        + " caracteres especiales").showAndWait();
            }
            if (passwdTxPF.getText().length() < 6 || passwdTxPF.getText().length() > 12) {
                new Alert(AlertType.ERROR,
                        "El campo password es minimo "
                        + "de 6 caracteres o maximo de 12").showAndWait();
            }

            this.userLoged = new User();

            userLoged.setUsername(userNameTxTF.getText());
            userLoged.setPassword(passwdTxPF.getText());
            /*
            try {
                userFac.getFactory().find_XML(new GenericType<List<User>>() {
                }, userLoged.getUsername(), userLoged.getPassword());
            } catch (Exception ex) {
                try {
                    throw new Exception(ex.getMessage());
                } catch (Exception e) {
                    LOGGER.severe(e.getMessage());
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
            */
            
            Stage loginStage = new Stage();

            URL viewLink = getClass().getResource("/ofc2_cliente/ui/LogedWindow.fxml");

            FXMLLoader loader = new FXMLLoader(viewLink);
            Parent root = (Parent) loader.load();
            LogedWindowController logedStageController
                    = ((LogedWindowController) loader.getController());
            //send te user
            logedStageController.getUser(userLoged);
            logedStageController.setStage(loginStage);
            logedStageController.initStage(root);

            //close the actually View
            this.stage.close();

        } catch (IOException ex) {
            Logger.getLogger(LogedWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SignInWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @author Elias
     * @param event
     */
    @FXML
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

}
