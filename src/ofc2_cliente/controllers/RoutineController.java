/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

//import ofc2_cliente.ui.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import ofc2_cliente.model.Routine;
import ofc2_cliente.logic.RoutineInterfaceFactory;
import ofc2_cliente.logic.RoutineRESTfulClient;
import ofc2_cliente.model.Exercise;

/**
 *
 * @author 2dam
 */
public class RoutineController implements Initializable {
    
    private Stage stage;
    
    private ObservableList<Routine> routineList;
    
   // private RoutineInterfaceFactory routineFactory= new RoutineInterfaceFactory();
    
    //private RoutineRESTfulClient routineREST= (RoutineRESTfulClient) routineFactory.createRoutineManager();
    
    
    
    @FXML
    private TextField nameTxTF;
            
    @FXML
    private ComboBox filterCH;
            
            
            
    @FXML
    private Button createBtn;
            
    @FXML
    private Button deleteBtn;
    
    @FXML
    private Button updateBtn;
            
            
    @FXML
    private TableView routineTable;
            
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn exerciseColumn;
    @FXML
    private TableColumn kcalColumn;
    @FXML
    private TableColumn timeColumn;
    @FXML
    private TableColumn start_dateColumn;
    @FXML
    private TableColumn end_dateColumn;
    @FXML
    
    private Button findBtn;
    @FXML
    private Button reportBtn;
    
    private Exercise ex;
    
    private static final Logger LOGGER = Logger.getLogger("of2_cliente.controllers.ExerciseCpontroller");
    
   
    
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
        routineTable.getSelectionModel().selectedItemProperty().addListener(this::tableControl);
        //findBtn.setOnAction(this::signIn);
        //updateBtn.setOnAction(this::routineDataWindowUpdate);
        stage.setOnCloseRequest(this::cerrarVentana);
        //Show window
        stage.show();
        LOGGER.info("finished initStage(SignIN)");

    }
    
    private void routineDataWindowCreate(ActionEvent event) {
        LOGGER.info("Method signUpWindow is starting");
        
  
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
    
    private void tableControl(ObservableValue observableValue, Object oldValue, Object newValue){
        
        
        if (newValue!=null) {
            deleteBtn.setDisable(false);
            updateBtn.setDisable(false);
        }
    }
    
     private void routineDataWindowUpdate(ActionEvent event) {
        LOGGER.info("Method signUpWindow is starting");
        
         if (!routineTable.getSelectionModel().isEmpty()) {
             
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

    private void exerciseWindoow() {
        Routine r = (Routine) routineTable.getSelectionModel().getSelectedItem();
        List<Exercise> exer = r.getEjercicios();

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
            mainStageController.setEx(exer);
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
       
       // filterCH.getItems().add("Routine name", "Exercise name");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        exerciseColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        kcalColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        start_dateColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        end_dateColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
       // routineList= FXCollections.observableArrayList(routineREST.consultAllRoutines_XML(new GenericType<List<Routine>>() {}));
        routineTable.setItems(routineList);
        //The field (userNameTxTF) has the focus
        nameTxTF.requestFocus();
        //The field (userNameTxTF) will be shown with a ToolTip the message “max 15 characters”. 
        //filterCH.setTooltip(new Tooltip("Filter by name or exercise"));
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
