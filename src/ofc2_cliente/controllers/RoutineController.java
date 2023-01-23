/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

//import ofc2_cliente.ui.*;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author 2dam
 */
public class RoutineController implements Initializable {
    
    private Stage stage;
    @FXML
    private Label label;
    
    @FXML
    private TextField nameTxTF;
            
    @FXML
    private ComboBox filterCB;
            
    @FXML
    private Button timeTxTF;
            
            
    @FXML
    private Button createBtn;
            
    @FXML
    private Button deleteBtn;
    
    @FXML
    private Button updateBtn;
            
            
    @FXML
    private TableView exerciseTable;
            
            
            
    private static final Logger LOGGER = Logger.getLogger("of2_cliente.controllers.ExerciseCpontroller");
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void setStage(Stage stage) {
        this.stage = stage;
    }
     
    public void initStage(Parent root) {
        LOGGER.info("starting initStage(SignIN)");
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);

        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //title of the window: OFC SIGN IN.
        stage.setTitle("OFC SING IN");
        stage.setResizable(false);
        deleteBtn.setDisable(true);
        updateBtn.setDisable(true);
        createBtn.setOnAction(this::routineDataWindowCreate);
        stage.setOnShowing(this::windowShowing);
        //findBtn.setOnAction(this::signIn);
        updateBtn.setOnAction(this::routineDataWindowUpdate);
        stage.setOnCloseRequest(this::cerrarVentana);
        //Show window
        stage.show();
        LOGGER.info("finished initStage(SignIN)");

    }
    
     @FXML
    private void routineDataWindowCreate(ActionEvent event) {
        LOGGER.info("Method signUpWindow is starting");
        
         if (!exerciseTable.getSelectionModel().isEmpty()) {
             
         }
        
        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/RoutineDataWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            RoutineDataWindowController mainStageController
                    = ((RoutineDataWindowController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);
            //start the stage
            mainStageController.initStage(root);

            this.stage.close();
            LOGGER.info("Method routineDataWindow is finished");

        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
    
    
   
     
     
     public void cerrarVentana(WindowEvent event) {
        LOGGER.info("Method cerrarVentana is starting");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Quiere salir de la aplicacion?");
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/ofc2_cliente/ui/dialog.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            LOGGER.info("Method cerrarVentana is finished");

        } else {
            event.consume();
        }
    }
     
       private void windowShowing(WindowEvent event) {
        LOGGER.info("Method windowShowing is starting ");

        //The field (userNameTxTF) has the focus
        nameTxTF.requestFocus();
        //The field (userNameTxTF) will be shown with a ToolTip the message “max 15 characters”. 
        filterCB.setTooltip(new Tooltip("Filter by name or exercise"));
        //The field (usernameTT) will be shown with a ToolTip the message “max 15 characters”. 
        //Tooltip.install(usernameTT, new Tooltip("max 15 characters"));
        //The field (passwdTxPF) will be shown with a ToolTip the message “min 6 max 12 characters”
        //passwdTxPF.setTooltip(new Tooltip("min 6 max 12 characters"));
        //The field (passwrdTT) will be shown with a ToolTip the message “min 6 max 12 characters”
        //Tooltip.install(passwrdTT, new Tooltip("min 6 max 12 characters"));
        //The HyperLink (signUpLink) will be shown with a ToolTip the message “Click para abrir la ventana de registro”. 
        //signUpLink.setTooltip(new Tooltip("Click para abrir la ventana de registro"));
        LOGGER.info("Method windowShowing is finished");

    }
    
    
}
