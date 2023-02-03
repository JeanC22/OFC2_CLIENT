/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

//import ofc2_cliente.ui.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import ofc2_cliente.logic.BusinessLogicException;
import ofc2_cliente.logic.ExerciseInterfaceFactory;
import ofc2_cliente.logic.ExerciseRESTfulClient;
import ofc2_cliente.model.Exercise;
import ofc2_cliente.model.Exercises;
import ofc2_cliente.model.Routine;

/**
 * This class is the ExerciseWindow controller
 * @author Aritz
 */
public class ExerciseWindowController {
    
    private Stage stage;
    private Label label;
    
    @FXML
    private TextField nameTxTF;
    
    @FXML
    private TextField timeTxTF;
 
    private ObservableList<Exercise> exerciseList;
    
    private ExerciseInterfaceFactory exerciseFactory= new ExerciseInterfaceFactory();
    
    private ExerciseRESTfulClient exerciseREST=  (ExerciseRESTfulClient) exerciseFactory.createExerciseManager();
            
    private static String numbers= "[0-9]+";
            
    private static final Logger LOGGER = Logger.getLogger("of2_cliente.controllers.RoutineController");
    
    @FXML
    private TableView exerciseTable;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn timeColumn;
    @FXML
    private Button returnBtn;
    @FXML
    private Button createBtn;
    

    
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
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnShowing(this::windowShowing);
        createBtn.setDisable(true);
        createBtn.setOnAction(this::createExercise);
        returnBtn.setOnAction(this::closeWindow);
        
        timeTxTF.setOnKeyReleased(this::validateTimeTxTX);
        nameTxTF.setOnKeyReleased(this::validateName);
        //findBtn.setOnAction(this::signIn);
        //createBtn.setOnAction(this::signUpWindow);
        //updateBtn.setOnAction(this::routineDataWindow);
        stage.setOnCloseRequest(this::cerrarVentana);
        //Show window
        stage.show();
        LOGGER.info("finished initStage(SignIN)");

    }
    
    private void closeWindow(ActionEvent event){
        this.stage.close();
    }
    
    private void enableDisableCreateBtn(){
        if (!nameTxTF.getText().isEmpty() && !timeTxTF.getText().isEmpty()) {
            createBtn.setDisable(false);
        }else{
            createBtn.setDisable(true);
        }
    }
    
    private void validateTimeTxTX(KeyEvent key){
        try {
            float time=Float.valueOf(timeTxTF.getText());
            
            if (time<0) {
                 Alert a= new Alert(Alert.AlertType.WARNING);
            a.setContentText("El campo time debe contener numeros positivos");
            a.showAndWait();
            timeTxTF.setText("");
            }
            enableDisableCreateBtn();
        } catch (NumberFormatException e) {
            Alert a= new Alert(Alert.AlertType.WARNING);
            a.setContentText("El campo time debe contener solo números");
            a.showAndWait();
            timeTxTF.setText("");
        }
        
       
    }

    private void createExercise(ActionEvent event) {
        try {
            Exercise ex = new Exercise();
            
            switch (nameTxTF.getText().toUpperCase()) {
                case "CORRER":
                    ex.setExercise(Exercises.CORRER);
                    break;
                    
                case "ABDOMINALES":
                    ex.setExercise(Exercises.ABDOMINALES);
                    break;
                case "CAMINAR":
                    ex.setExercise(Exercises.CAMINAR);
                    break;
                    
                case "DOMINADAS":
                    ex.setExercise(Exercises.DOMINADAS);
                    break;
                case "FLEXIONES":
                    ex.setExercise(Exercises.FLEXIONES);
                    break;
                case "PLANCHA":
                    ex.setExercise(Exercises.PLANCHA);
                    break;
                case "SENTADILLAS":
                    ex.setExercise(Exercises.SENTADILLAS);
                    break;
            }
            ex.setTime(Float.valueOf(timeTxTF.getText()));
            
            exerciseREST.create_XML(ex);
        } catch (BusinessLogicException ex1) {
            Logger.getLogger(ExerciseWindowController.class.getName()).log(Level.SEVERE, null, ex1);
        }
        
        
    }
    
    private void validateName(KeyEvent event){
        if (nameTxTF.getText().length()>30) {
            Alert a= new Alert(Alert.AlertType.WARNING);
            a.setContentText("El campo name debe contener menos de 30 caracteres");
            a.showAndWait();
             enableDisableCreateBtn();
             nameTxTF.setText("");
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
     
      private void chargeTable(ObservableList<Routine> routineList) {
       
        try {
            exerciseList= FXCollections.observableArrayList(exerciseREST.consultAllExercises_XML(new GenericType<List<Exercise>>() {}));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            
            
            exerciseTable.setItems(exerciseList);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(ExerciseWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
       private void windowShowing(WindowEvent event) {
        try {
            LOGGER.info("Method windowShowing is starting ");
            
            exerciseList= FXCollections.observableArrayList(exerciseREST.consultAllExercises_XML(new GenericType<List<Exercise>>() {}));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            
            exerciseTable.setItems(exerciseList);
            //The field (userNameTxTF) has the focus
            nameTxTF.requestFocus();
            //The field (userNameTxTF) will be shown with a ToolTip the message “max 15 characters”.
            //filterCB.setTooltip(new Tooltip("Filter by name or exercise"));
            //The field (usernameTT) will be shown with a ToolTip the message “max 15 characters”.
            //Tooltip.install(usernameTT, new Tooltip("max 15 characters"));
            //The field (passwdTxPF) will be shown with a ToolTip the message “min 6 max 12 characters”
            //passwdTxPF.setTooltip(new Tooltip("min 6 max 12 characters"));
            //The field (passwrdTT) will be shown with a ToolTip the message “min 6 max 12 characters”
            //Tooltip.install(passwrdTT, new Tooltip("min 6 max 12 characters"));
            //The HyperLink (signUpLink) will be shown with a ToolTip the message “Click para abrir la ventana de registro”.
            //signUpLink.setTooltip(new Tooltip("Click para abrir la ventana de registro"));
            LOGGER.info("Method windowShowing is finished");
        } catch (BusinessLogicException ex) {
            Logger.getLogger(ExerciseWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
       public void getExercise(ObservableList<Exercise> e){
           this.exerciseList= e;
                 
       }
  
    
}
