/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import ofc2_cliente.logic.BusinessLogicException;
import ofc2_cliente.logic.EventFactory;
import ofc2_cliente.model.Event;
import org.hibernate.mapping.Collection;

/**
 * FXML Controller class
 *
 * @author Jp
 */
public class CreateModifyController {

    private Event event;
    private Stage stage;
    @FXML
    private TextField nameFld;
    @FXML
    private TextField actFld;
    @FXML
    private TextField dateFld;
    @FXML
    private TextField plcFld;
    @FXML
    private TextField capFld;
    @FXML
    private TextField priceFld;
    @FXML
    private Button mdfBtn;
    @FXML
    private Button crtBtns;
    @FXML
    private Button bckBtn;
    
    private EventFactory eventFact = new EventFactory();
    ObservableList<Event> events;
    private Long id;

    private static final Logger LOGGER = Logger.getLogger("ofc2_cliente.Controllers");
    @FXML
    private ContextMenu menuCont;
    /**
     * setStage
     * @param stage 
     */
    public void setStage(Stage stage) {

        this.stage = stage;
    }
    /**
     * this Method will start the stage
     * @author Jp
     * @param root 
     */
    public void initStage(Parent root) {
        LOGGER.info("Starting Stage");
        //init the scene with the root you got from singInController
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("OFC Event");
        crtBtns.setVisible(true);
        crtBtns.setDisable(false);
        mdfBtn.setVisible(false);
        mdfBtn.setDisable(true);
        
        bckBtn.setOnAction(this::backBtn);
        stage.setOnCloseRequest(this::cerrarVentana);
        crtBtns.setOnAction(this::createEvent);
        mdfBtn.setOnAction(this::modifyEvent);
        
        this.stage.show();
        LOGGER.info("Stage Started");
        
        
    }

    /**
     * this Method will be close LogedWindow and start the SingInWindow
     * @author Jp 
     */
    
    public void modifyEvent(ActionEvent event){
        Event eve = new Event();
        Date newDate = null;
        SimpleDateFormat formateo = new SimpleDateFormat("dd/MM/yyyy");
        try {
            newDate = formateo.parse(dateFld.getText());
        } catch (ParseException ex) {
            Logger.getLogger(CreateModifyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        eve.setId(id);
        eve.setName(nameFld.getText());
        eve.setActivity(actFld.getText());
        eve.setDate(newDate);
        eve.setPlace(plcFld.getText());
        eve.setCapacity(Integer.parseInt(capFld.getText()));
        eve.setPrice(Float.valueOf(priceFld.getText()));
        
        try {
            eventFact.getFactory().edit_XML(eve);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(CreateModifyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        backBtn(event);
    }
    
    
    public void createEvent(ActionEvent event){
        Event eve = new Event();
        Date newDate = null;
        
        SimpleDateFormat formateo = new SimpleDateFormat("dd/MM/yyyy");
        try {
            newDate = formateo.parse(dateFld.getText());
        } catch (ParseException ex) {
            Logger.getLogger(CreateModifyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        eve.setName(nameFld.getText());
        eve.setActivity(actFld.getText());
        eve.setDate(newDate);
        eve.setPlace(plcFld.getText());
        eve.setCapacity(Integer.parseInt(capFld.getText()));
        eve.setPrice(Float.valueOf(priceFld.getText()));
        
        try {
            eventFact.getFactory().create_XML(eve);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(CreateModifyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        backBtn(event);
    }
    
    /**
     * This Method confirm if the user want to close the window
     * @author Iker
     * @param event 
     */
    public void cerrarVentana(WindowEvent event){
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Quiere salir de la aplicacion?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Platform.exit();
        }else {
            event.consume();
        }
    }
    
    public void backBtn(ActionEvent event) {

        try {
            Stage primaryStage = new Stage();

            URL a = getClass().getResource("/ofc2_cliente/ui/EventWindow.fxml");
            FXMLLoader loader = new FXMLLoader(a);
            Parent root = (Parent) loader.load();
            EventWindowController mainStageController
                    = ((EventWindowController) loader.getController());
            mainStageController.setStage(primaryStage);
            mainStageController.initStage(root);
            this.stage.close();
            //inicializar la scena
        } catch (IOException ex) {
            Logger.getLogger(EventWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void loadDate(Event event){
        nameFld.setText(event.getName());
        actFld.setText(event.getActivity());
        dateFld.setText(event.getDate().toString());
        plcFld.setText(event.getPlace());
        capFld.setText(event.getCapacity().toString());
        priceFld.setText(event.getPrice().toString());
        this.id= event.getId();
        mdfBtn.setVisible(true);
        mdfBtn.setDisable(false);
        crtBtns.setVisible(false);
        crtBtns.setDisable(true);
        
    }
    
    
}
