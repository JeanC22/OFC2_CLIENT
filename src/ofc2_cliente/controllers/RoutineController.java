/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

//import ofc2_cliente.ui.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import ofc2_cliente.logic.BusinessLogicException;
import ofc2_cliente.model.Routine;
import ofc2_cliente.logic.RoutineInterfaceFactory;
import ofc2_cliente.logic.RoutineRESTfulClient;
import ofc2_cliente.model.Client;
import ofc2_cliente.model.Exercise;
import ofc2_cliente.model.Exercises;

/**
 * Controller class for RoutineWindow in routines management application.
 * @author Aritz
 */
public class RoutineController {
    
    private Stage stage;
    
    private ObservableList<Routine> routineList;
    
    private RoutineInterfaceFactory routineFactory= new RoutineInterfaceFactory();
    
    private RoutineRESTfulClient routineREST=  (RoutineRESTfulClient) routineFactory.createRoutineManager();
    
    
    
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
    
    private Routine routine;
    
    private Exercise ex;
    
    private static final Logger LOGGER = Logger.getLogger("of2_cliente.controllers.ExerciseCpontroller");
    @FXML
    private Pane OFC_SIGN_IN;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem updateMn;
    @FXML
    private MenuItem createMn;
    @FXML
    private MenuItem findMn;
    @FXML
    private MenuItem deleteMn;
    
    @FXML
    private Button helpBtn;
    
    private Client cli;
    
    
    private RoutineController mainStageController;

    
    
     public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /*
     This method starts the RoutineWindow window with all its components
     */
    public void initStage(Parent root) {
        
        LOGGER.info("starting initStage(SignIN)");
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
       
        stage.setScene(scene);
        //title of the window: OFC SIGN IN.
        stage.setTitle("OFC SING IN");
        stage.setResizable(false);
        deleteBtn.setDisable(true);
        updateBtn.setDisable(true);
        deleteMn.setDisable(true);
        updateMn.setDisable(true);
        findBtn.setDisable(true);
        findMn.setDisable(true);
        ObservableList<String> list= FXCollections.observableArrayList("Routine name", "All routines");
        filterCH.setItems(list);
        filterCH.getItems();
        filterCH.getSelectionModel().selectFirst();
        
        setTooltips();
        deleteMn.setOnAction(this::deleteRoutine);
        updateMn.setOnAction(this::routineDataWindowUpdate);
        createMn.setOnAction(this::routineDataWindowCreate);
        helpBtn.setOnAction(this::showHelpWindow);
        
        findBtn.setOnAction(this::filterMethod);
        findMn.setOnAction(this::filterMethod);
        nameTxTF.setOnKeyReleased(this::enabledFindBtn);
        
        
        deleteBtn.setOnAction(this::deleteRoutine);
        createBtn.setOnAction(this::routineDataWindowCreate);
        updateBtn.setOnAction(this::routineDataWindowUpdate);
        reportBtn.setOnAction(this::showReport);
        routineTable.getSelectionModel().selectedItemProperty().addListener(this::tableControl);
        
        stage.setOnShowing(this::windowShowing);
       
      
        stage.setOnCloseRequest(this::cerrarVentana);
        //Show window
        stage.show();
        LOGGER.info("finished initStage(RoutineWindow)");

    }
    
    /**
     * This method enables or disables findBtn depending on whether the 
     * combo box filterCH and text field nameTxTX are filled or empty
     * @param event 
     */
    public void enableFindBtnC(ActionEvent event){
        if (!nameTxTF.getText().isEmpty() && !filterCH.getItems().isEmpty()) {
            findBtn.setDisable(false);
            findMn.setDisable(false);
        }else{
            findBtn.setDisable(true);
            findMn.setDisable(true);
        }
    }
    
    /**
     * This method opens the RoutineHelpWindow
     * @param event 
     */
    private void showHelpWindow(ActionEvent event){
        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/RoutineHelpWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            RoutineHelpWindowController mainStageController
                    =  loader.getController();
            
            mainStageController.setStage(mainStage);
            
            //start the stage
            mainStageController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(RoutineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void filterMethod(ActionEvent event) {
        ObservableList<Routine> list = null;


            switch (filterCH.getSelectionModel().getSelectedItem().toString()) {

                case "Routine name":

                    try {
                        list = FXCollections.observableArrayList(routineREST.consultRoutinesByName_XML(new GenericType<List<Routine>>() {
                    }, nameTxTF.getText()));
                    } catch (Exception e) {
                       Alert a = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
                       a.setContentText("No se han encontrado rutinas con el nombre introducido");
                       a.showAndWait();
                    }
                   
                    break;

                case "All routines":

                    if (nameTxTF.getText().equalsIgnoreCase("all")) {
                        try {
                             list = FXCollections.observableArrayList(routineREST.consultAllRoutines_XML(new GenericType<List<Routine>>() {
                        }));
                            
                        } catch (Exception e) {
                            Alert a= new Alert(Alert.AlertType.ERROR,"",ButtonType.OK);
                            a.setContentText("No hay rutinas");
                            a.showAndWait();
                        }
                       
                    }else{
                         Alert a= new Alert(Alert.AlertType.INFORMATION,"",ButtonType.OK);
                            a.setContentText("Para buscar todas las rutinas escriba all en name y tenga seleccionada la opcion All routines");
                            a.showAndWait();
                    }
                    break;
            
          
           
        }
              if (list!=null) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                a.setContentText("Se ha encontrado rutina/s");
                a.showAndWait();
                routineTable.getItems().remove(routineList);
                routineTable.setItems(list);
            }
    }

    

    /**
     * This method enables or disables deleteBtn and updateBtn depending on
     * whether the routineTable has a selected row or not
     */
    private void filterRoutine(ActionEvent event) {
        ObservableList<Routine> routines;
        try {
            //routines= FXCollections.observableArrayList(routineREST.consultRoutinesByName_XML(new GenericType<List<Routine>>() {},nameTxTF.getText()));

            routineTable.getItems().remove(routineList);
            //routineList=routines;
            routineTable.setItems(routineList);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("No se han encontrado Rutinas con el nombre introducido");
        }
        
        
    }
    
    /**
     * This method charge the table with the new routines
     */
    public void refreshTable(){
        try {
            
            routineList= FXCollections.observableArrayList(routineREST.consultAllRoutines_XML(new GenericType<List<Routine>>() {}));
            chargeTable(routineList);
          
        } catch (BusinessLogicException ex) {
            Logger.getLogger(RoutineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method enables or disables the findBtn
     * @param event 
     */
    private void enabledFindBtn(KeyEvent event){
        if (!nameTxTF.getText().isEmpty() && !filterCH.getItems().isEmpty()) {
            findBtn.setDisable(false);
            findMn.setDisable(false);
            findBtn.setTooltip(new Tooltip("Write the name of the "+filterCH.getSelectionModel().getSelectedItem()));
        }else{
             findBtn.setDisable(true);
             findMn.setDisable(true);
        }
    }
    
    /**
     * This method opens RoutineDataWindow  to update the routine
     * @param event 
     */
    private void routineDataWindowUpdate(ActionEvent event) {
        LOGGER.info("Method signUpWindow is starting");
        
        
        try {
            
            this.routine=(Routine) routineTable.getSelectionModel().getSelectedItem();
            

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
            
            mainStageController.setRoutineController(this.mainStageController);
            mainStageController.getRoutine(routine);
            mainStageController.setRoutineList(routineTable.getItems());
            
            //set the stage
            mainStageController.setStage(mainStage);
           
            //start the stage
            mainStageController.initStage(root);
            
         
            LOGGER.info("Method routineDataWindow is finished");

        } catch (IOException ex) {
            Logger.getLogger(RoutineDataWindowController.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
    
    /**
     * This method opens RoutineDataWindow  to create a new routine
     * @param event 
     */
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
            mainStageController.setRoutineController(this.mainStageController);
            //set the stage
            mainStageController.setStage(mainStage);
            //start the stage
            mainStageController.initStage(root);
            routineTable.refresh();

           
            LOGGER.info("Method routineDataWindow is finished");

        } catch (IOException ex) {
            Logger.getLogger(RoutineDataWindowController.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }
        routineTable.getSelectionModel().clearSelection();

    }
    
    /**
     * This method open the table report
     * @param event 
     */
    private void showReport(ActionEvent event){
        
        try {
            JasperReport jr= JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/ofc2_cliente/reports/RoutineReport.jrxml"));
            JRBeanCollectionDataSource dataItems=
                    new JRBeanCollectionDataSource((Collection<Routine>)this.routineTable.getItems());
            
            //Map of parameter to be passed to the report
            Map<String,Object> parameters=new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr,parameters,dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(RoutineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method enable or deisable 
     * @param observableValue
     * @param oldValue
     * @param newValue 
     */
    private void tableControl(ObservableValue observableValue, Object oldValue, Object newValue){
       
       if (newValue!=null) {
             deleteBtn.setDisable(false);
            deleteMn.setDisable(false);
            updateBtn.setDisable(false);
            updateMn.setDisable(false);
           
        }else{
            deleteBtn.setDisable(true);
            deleteMn.setDisable(true);
            updateBtn.setDisable(true);
            updateMn.setDisable(true);
            
        }
    }
    
    private void deleteRoutine(ActionEvent event){
        try {
             Routine routine;
        routine= (Routine) routineTable.getSelectionModel().getSelectedItem();
        
        routineREST.remove(routine.getId().toString());
        routineTable.getItems().remove(routine);
        routineTable.refresh();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("La rutina se ha borrado correctamente");
        alert.showAndWait();
        } catch (BusinessLogicException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        }
       routineTable.getSelectionModel().clearSelection();
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
            
            //set the stage
            mainStageController.setStage(mainStage);

            //start the stage
            mainStageController.initStage(root);

            this.stage.close();
            LOGGER.info("Method routineDataWindow is finished");

        } catch (IOException ex) {
            Logger.getLogger(ExerciseWindowController.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }
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

    private void chargeTable(ObservableList<Routine> routineList) {
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //exerciseColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        kcalColumn.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        start_dateColumn.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        end_dateColumn.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        start_dateColumn.setCellFactory(column -> {
                TableCell<Routine, Date> cell = new TableCell<Routine, Date>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            if (item != null) {
                                setText(getDate(item).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
                            }
                        }
                    }
                };

                return cell;
            });
        
        end_dateColumn.setCellFactory(column -> {
                TableCell<Routine, Date> cell = new TableCell<Routine, Date>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            if (item != null) {
                                setText(getDate(item).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
                            }
                        }
                    }
                };

                return cell;
            });

        routineTable.setItems(routineList);
    }

    private void windowShowing(WindowEvent event) {
            //Long id= new Long(1);
            
            
        //cli.setId(id);
        try {
           
            LOGGER.info("Method windowShowing is starting ");
            //rutinaDePrueba();
            routineList= FXCollections.observableArrayList(routineREST.consultAllRoutines_XML(new GenericType<List<Routine>>() {}));
            chargeTable(routineList);
            } catch (BusinessLogicException ex) {
            //Logger.getLogger(RoutineController.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.info(ex.getMessage());
        }
            
            deleteMn.setText("Delete");
            updateMn.setText("Update");
            findMn.setText("Find");
            createMn.setText("Create");
            
            nameTxTF.requestFocus();
            
            LOGGER.info("Method windowShowing is finished");
            
            Logger.getLogger(RoutineController.class.getName()).log(Level.SEVERE, null, ex);
            
        

    }

    private void setTooltips() {

        deleteBtn.setTooltip(new Tooltip("First select the row you want to delete"));
        updateBtn.setTooltip(new Tooltip("First select the row you want to update"));
        nameTxTF.setTooltip(new Tooltip("Max 30 characters"));
        nameTxTF.setTooltip(new Tooltip("Max 30 characters"));
        Tooltip.install(filterCH, new Tooltip("To find all write in name: all"));
        createBtn.setTooltip(new Tooltip("Open the form"));
        findBtn.setTooltip(new Tooltip("First complite filter components"));
        //Tooltip.install(filterCH, new Tooltip("Choose the search mode"));

    }
    
   
    
    private void rutinaDePrueba(){
        Routine r= new Routine();
        Exercise e= new Exercise();
        e.setExercise(Exercises.CORRER);
        
        ObservableList<Exercise> list= FXCollections.observableArrayList(e);
                
        float c= 12;
        r.setName("Prueba1");
        r.setKcal(12.11);
        r.setTime(c);
        r.setStart_date(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        r.setEnd_date(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        r.setEjercicios(list);
        routineList= FXCollections.observableArrayList(r);
    }
    
    private ObservableList<Routine> rutinaDePrueba2(){
        Routine r= new Routine();
        Exercise e= new Exercise();
        e.setExercise(Exercises.CORRER);
        
        ObservableList<Exercise> list= FXCollections.observableArrayList(e);
                
        float c= 12;
        r.setName("Prueba2");
        r.setKcal(12.11);
        r.setTime(c);
        r.setStart_date(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        r.setEnd_date(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        r.setEjercicios(list);
        routineList= FXCollections.observableArrayList(r);
        
        return routineList;
    }

    public void setController(RoutineController mainStageController) {
        this.mainStageController= mainStageController;
    }
    
    public void setClient(Client cli){
        this.cli=cli;
    }
    
    private LocalDate getDate(Date date) {
        return date == null ? LocalDate.now() : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
