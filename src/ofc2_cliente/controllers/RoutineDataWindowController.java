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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import ofc2_cliente.logic.BusinessLogicException;
import ofc2_cliente.logic.ExerciseInterfaceFactory;
import ofc2_cliente.logic.ExerciseRESTfulClient;
import ofc2_cliente.logic.RoutineInterfaceFactory;
import ofc2_cliente.logic.RoutineRESTfulClient;
import ofc2_cliente.model.Client;
import ofc2_cliente.model.Exercise;
import ofc2_cliente.model.Exercises;
import ofc2_cliente.model.Routine;

/**
 *
 * @author Aritz
 */
public class RoutineDataWindowController {
    
    private Stage stage;
    @FXML
    private Label label;
    
    @FXML
    private TextField nameTxTF1;
    
    @FXML
    private TextField timeTxTF;
    
    @FXML
    private TextField kcalTxTF;
            
    @FXML
    private ComboBox exerciseCB;
            
    @FXML
    private Button newExerciseBtn;
            
    @FXML
    private DatePicker endDateDT;
            
    @FXML
    private Button createOrupdateBtn;
            
    @FXML
    private Button returnBtn;
    
    private Integer tablePosition;
    
    private RoutineController routineController;
    
    private Client client;
    
    private Routine routine;
    
    private ObservableList<Exercise> exerciseList;
    
    private RoutineInterfaceFactory routineFactory= new RoutineInterfaceFactory();
    
    private RoutineRESTfulClient routineREST=  (RoutineRESTfulClient) routineFactory.createRoutineManager();
                            
   // private static String numbers= "[0-9]+";
    
    private ExerciseInterfaceFactory exerciseFactory= new ExerciseInterfaceFactory();
    
    private ExerciseRESTfulClient exerciseREST=  (ExerciseRESTfulClient) exerciseFactory.createExerciseManager();
            
            
            
    private static final Logger LOGGER = Logger.getLogger("of2_cliente.controllers.RoutineDataWindowController");
    
   
    
    
     public void setStage(Stage stage) {
        this.stage = stage;
    }
     
    public void initStage(Parent root) {
        LOGGER.info("starting initStage(RoutineDataWindow)");
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        stage.setTitle("Routine form Window");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        changeButtonAction();
        timeTxTF.setOnKeyReleased(this::validateTimeTxTX);
        nameTxTF1.setOnKeyReleased(this::validateName);
        kcalTxTF.setOnKeyReleased(this::validateKcalTxTX);
        endDateDT.setOnKeyReleased(this::enableDisableCreateUpdateBtn1);
        chargeExerciseCB();

        exerciseCB.setOnKeyReleased(this::enableDisableCreateUpdateBtn1);
        
        if (createOrupdateBtn.getText().equalsIgnoreCase("Create")) {
            createOrupdateBtn.setDisable(true);
            createOrupdateBtn.setOnAction(this::createRoutine);
        }else{
            createOrupdateBtn.setOnAction(this::updateRoutine);
        }
        returnBtn.setOnAction(this::closeWindow);
        newExerciseBtn.setOnAction(this::newExercise);
        stage.setOnCloseRequest(this::cerrarVentana);
        //Show window
        stage.show();
        LOGGER.info("finished initStage(SignIN)");

    }
    
    private void enableDisableCreateUpdateBtn(){
        if (!nameTxTF1.getText().isEmpty() && !timeTxTF.getText().isEmpty() && !kcalTxTF.getText().isEmpty()) {
            createOrupdateBtn.setDisable(false);
        }else{
            createOrupdateBtn.setDisable(true);
            
            
        }
    }
    
    
     private void enableDisableCreateUpdateBtn1(KeyEvent event){
        enableDisableCreateUpdateBtn();
        
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
            enableDisableCreateUpdateBtn();
        } catch (Exception e) {
            if (!timeTxTF.getText().isEmpty()) {
                 Alert a= new Alert(Alert.AlertType.WARNING);
            a.setContentText("El campo time debe contener solo números");
            a.showAndWait();
            timeTxTF.setText("");
            }
           
            
        }
     
       
    }
     private void validateKcalTxTX(KeyEvent key){
        try {
            float time=Float.valueOf(kcalTxTF.getText());
            
            if (time<0) {
                 Alert a= new Alert(Alert.AlertType.WARNING);
            a.setContentText("El campo kcal debe contener numeros positivos");
            a.showAndWait();
            kcalTxTF.setText("");
            }
            enableDisableCreateUpdateBtn();
        } catch (Exception e) {
            if (!kcalTxTF.getText().isEmpty()) {
                Alert a= new Alert(Alert.AlertType.WARNING);
            a.setContentText("El campo kcal debe contener solo números");
            a.showAndWait();
            kcalTxTF.setText("");
            }
            
            
        }
        
        
    }
    
    private void validateName(KeyEvent event){
        if (nameTxTF1.getText().length()>30) {
            Alert a= new Alert(Alert.AlertType.WARNING);
            a.setContentText("El campo name debe contener menos de 30 caracteres");
            a.showAndWait();
            nameTxTF1.setText("");
            
        }else{
            enableDisableCreateUpdateBtn();
        }
        
        
        
    }
    
      private void updateRoutine(ActionEvent event){
        Routine updatedRoutine=new Routine();
        try {
             LocalDate ld= endDateDT.getValue();
             updatedRoutine.setEnd_date(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } catch (Exception e) {
        }
      
        updatedRoutine.setName(nameTxTF1.getText());
        updatedRoutine.setKcal(Double.valueOf(kcalTxTF.getText()));
        
        updatedRoutine.setTime(Float.valueOf(timeTxTF.getText()));
        updatedRoutine.setStart_date(routine.getStart_date());
        
        
         try {
            routineREST.edit_XML(updatedRoutine);
             routineController.refreshTable();
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La rutina se ha modificado correctamente");
            alert.showAndWait();
        } catch (BusinessLogicException e) {
            LOGGER.severe(e.getMessage());
             Alert alert= new Alert(Alert.AlertType.ERROR,"No se puede "
                     + "conectar con el servidor");
             alert.showAndWait();
        }
         
         this.routineController.refreshTable();

            
         this.stage.close();
    }
    
    private void createRoutine(ActionEvent event){
         Routine newRoutine= new Routine();
        try {
            LocalDate ld= endDateDT.getValue();
            newRoutine.setEnd_date(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } catch (Exception e) {
        }
        
       
        newRoutine.setName(nameTxTF1.getText());
        newRoutine.setKcal(Double.valueOf(kcalTxTF.getText()));
         newRoutine.setStart_date(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        newRoutine.setTime(Float.valueOf(timeTxTF.getText()));
        newRoutine.setClie(this.client);
        
        try {
            routineREST.create_XML(newRoutine);
            routineController.refreshTable();
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La rutina se ha creado correctamente");
            alert.showAndWait();
            
            
        } catch (BusinessLogicException e) {
            LOGGER.severe(e.getMessage());
             Alert alert= new Alert(Alert.AlertType.ERROR,"No se puede "
                     + "conectar con el servidor");
             alert.showAndWait();
            
        }
        
        this.stage.close();
    }
    
    private void newExercise(ActionEvent event){
         
        //List<Exercise> exer = this.routine.getEjercicios();

        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/ExerciseWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            ExerciseWindowController mainStageController
                    = ((ExerciseWindowController) loader.getController());
            
            //set the stage
            mainStageController.setStage(mainStage);

            //start the stage
            mainStageController.initStage(root);

            
            LOGGER.info("Method routineDataWindow is finished");

        } catch (IOException ex) {
            Logger.getLogger(ExerciseWindowController.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
 
    
    
    private void changeButtonAction(){
        if (routine==null) {
            createOrupdateBtn.setText("Create");
           
        }else{
            createOrupdateBtn.setText("Update");
        }
    }
    
    
    
    
    public void closeWindow(ActionEvent event) {
        this.stage.close();

    }

     
     public void cerrarVentana(WindowEvent event) {
        LOGGER.info("Method cerrarVentana is starting");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Quiere salir de la aplicacion?");
       

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
        nameTxTF1.requestFocus();
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
       
       private void setTooltips() {

        nameTxTF1.setTooltip(new Tooltip("Max 30 characters"));
        newExerciseBtn.setTooltip(new Tooltip("Create a new exerise"));
       

    }

    
    public void getRoutine(Routine routine){
        this.routine=routine;
        try {
             this.exerciseList= FXCollections.observableArrayList(routine.getEjercicios());
        } catch (Exception e) {
        }
       
        nameTxTF1.setText(routine.getName());
        endDateDT.setValue(routine.getEnd_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        kcalTxTF.setText(routine.getKcal().toString());
        timeTxTF.setText(routine.getTime().toString());
        exerciseCB.setItems(exerciseList);
        
        
    }

    void setRoutineController(RoutineController RoutineController) {
        this.routineController= RoutineController;
    }

    private void chargeExerciseCB() {
        //try {
             ObservableList<String> exercisesNames = FXCollections.observableArrayList("CORRER","ABDOMINALES","CAMINAR","DOMINADAS","FLEXIONES","PLANCHA","SENTADILLAS");
             
            //ObservableList<Exercise> exercises= FXCollections.observableArrayList(exerciseREST.consultAllExercises_XML(new GenericType<List<Exercise>>() {}));
           
            /*for (int i = 0; i < exercises.size(); i++) {
                exercisesNames.add(exercises.get(i).getExercise());
            }*/
            exerciseCB.setItems(exercisesNames);
       /*} catch (BusinessLogicException ex) {
            Logger.getLogger(RoutineDataWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }

 
    
    
}
