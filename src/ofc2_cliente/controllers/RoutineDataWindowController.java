/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

//import ofc2_cliente.ui.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ofc2_cliente.logic.RoutineInterfaceFactory;
import ofc2_cliente.logic.RoutineRESTfulClient;
import ofc2_cliente.model.Client;
import ofc2_cliente.model.Exercise;
import ofc2_cliente.model.Routine;

/**
 *
 * @author 2dam
 */
public class RoutineDataWindowController implements Initializable {
    
    private Stage stage;
    @FXML
    private Label label;
    
    @FXML
    private TextField nameTxTF;
    
    @FXML
    private TextField kcalTxTF;
            
    @FXML
    private ComboBox exerciseCB;
            
    @FXML
    private Button exerciseBtn;
            
    @FXML
    private DatePicker endDateDT;
            
    @FXML
    private Button createOrupdateBtn;
            
    @FXML
    private Button returnBtn;
    
    private Client client;
    
    private List<Exercise> exercises;
    
    private Routine routine;
    
    
    
     private RoutineInterfaceFactory routineFactory= new RoutineInterfaceFactory();
    
    private RoutineRESTfulClient routineREST=  (RoutineRESTfulClient) routineFactory.createRoutineManager();
                            
            
            
            
    private static final Logger LOGGER = Logger.getLogger("of2_cliente.controllers.RoutineDataWindowController");
    
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
        LOGGER.info("starting initStage(RoutineDataWindow)");
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);

        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //title of the window: OFC SIGN IN.
        stage.setTitle("OFC SING IN");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        changeButtonAction();
        if (createOrupdateBtn.getText().equalsIgnoreCase("Create")) {
            createOrupdateBtn.setOnAction(this::createRoutine);
        }else{
            createOrupdateBtn.setOnAction(this::updateRoutine);
        }
        returnBtn.setOnAction(this::closeWindow);
        
       // stage.setOnShowing(this::windowShowing);
        //
        //createBtn.setOnAction(this::signUpWindow);
        //updateBtn.setOnAction(this::routineDataWindow);
        stage.setOnCloseRequest(this::cerrarVentana);
        //Show window
        stage.show();
        LOGGER.info("finished initStage(SignIN)");

    }
    
    private void createRoutine(ActionEvent event){
        LocalDate ld= endDateDT.getValue();
        Routine newRoutine= new Routine();
        newRoutine.setName(nameTxTF.getText());
        newRoutine.setKcal(Double.valueOf(kcalTxTF.getText()));
        newRoutine.setStart_date(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        newRoutine.setEnd_date(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        //newRoutine.setClie(this.client);
        
        try {
            routineREST.create_XML(newRoutine);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        
        this.stage.close();
    }
    
    private void updateRoutine(ActionEvent event){
       LocalDate ld= endDateDT.getValue();
        routine.setName(nameTxTF.getText());
        routine.setKcal(Double.valueOf(kcalTxTF.getText()));
        routine.setEnd_date(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        
         try {
            routineREST.edit_XML(routine);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }

            
         this.stage.close();
    }
    
    
    private void changeButtonAction(){
        if (routine==null) {
            createOrupdateBtn.setText("Create");
        }else{
            createOrupdateBtn.setText("Update");
        }
    }
    
    /* @FXML
    private void routineDataWindow(ActionEvent event) {
        LOGGER.info("Method signUpWindow is starting");

        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/model/views/SignUpWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            RoutineDataWindow mainStageController
                    = ((RoutineDataWindow) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);
            //start the stage
            mainStageController.initStage(root);

            this.stage.close();
            LOGGER.info("Method signUpWindow is finished");

        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }

    }*/
    
    
    public void closeWindow(ActionEvent event) {
        this.stage.close();

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
        //nameTxTF.requestFocus();
        //The field (userNameTxTF) will be shown with a ToolTip the message “max 15 characters”. 
        //nameTxTF.setTooltip(new Tooltip("max 15 characters"));
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
    
    public void getRoutine(Routine routine){
        this.routine=routine;
        
        nameTxTF.setText(routine.getName());
        endDateDT.setValue(routine.getEnd_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        kcalTxTF.setText(routine.getKcal().toString());
        
    }
    
    
}
