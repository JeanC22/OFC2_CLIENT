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
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.DatePicker;
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
import ofc2_cliente.model.Admin;
import ofc2_cliente.model.Event;
import ofc2_cliente.model.User;
import org.hibernate.mapping.Collection;

/**
 * FXML Controller class
 *
 * @author iker
 */
public class CreateModifyController {

    private Event event;
    private Stage stage;
    @FXML
    private TextField nameFld;
    @FXML
    private TextField actFld;
    @FXML
    private DatePicker datePick;
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

    private static String regex = "^[a-zA-Z]*$";
    private static String regexNum = "^[1-9]*$";
    private static final Logger LOGGER = Logger.getLogger("ofc2_cliente.Controllers");
    @FXML
    private ContextMenu menuCont;

    /**
     * setStage
     *
     * @param stage
     */
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    /**
     * this Method will start the stage
     *
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
        nameFld.setPromptText("Max 30 caracteres");
        actFld.setPromptText("Max 50 caracteres");
        plcFld.setPromptText("Max 30 caracteres");
        capFld.setPromptText("Solo numeros");
        priceFld.setPromptText("solo numeros, admite decimales");
        datePick.setPromptText("dd/MM/yyyy");

        bckBtn.setOnAction(this::backBtn);
        stage.setOnCloseRequest(this::cerrarVentana);
        crtBtns.setOnAction(this::createEvent);
        mdfBtn.setOnAction(this::modifyEvent);

        this.stage.show();
        LOGGER.info("Stage Started");

    }

    /**
     * This method modifies the selected event of the table in the database.
     */
    public void modifyEvent(ActionEvent event) {
        Event eve = new Event();
        Date newDate = null;
        

        newDate = Date.from(datePick.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        eve.setId(id);
        eve.setName(nameFld.getText());
        eve.setActivity(actFld.getText());
        eve.setDate(newDate);
        eve.setPlace(plcFld.getText());
        eve.setCapacity(Integer.parseInt(capFld.getText()));
        eve.setPrice(Float.valueOf(priceFld.getText()));

        try {
            eventFact.getFactory().edit_XML(eve);
            backBtn(event);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(CreateModifyController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        
    }

    /**
     * This method validates the fields and if they are correct it creates an
     * event in the database.
     * @param event 
     */
    public void createEvent(ActionEvent event) {
        Event eve = new Event();
        Date newDate = null;
        User u = new Admin();
        Date otherDate = null;
        try {
            otherDate =Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()); 
            newDate = Date.from(datePick.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            if (nameFld.getText().isEmpty() || actFld.getText().isEmpty()
                    || datePick.getValue() == null || capFld.getText().isEmpty()
                    || priceFld.getText().isEmpty() || plcFld.getText().isEmpty()) {
                throw new Exception("Alguno de los campos no esta informado");
            }
            if(this.nameFld.getText().length() > 30){
                throw new Exception("El campo de name tiene mas de 30 caracteres");
            }
            if(this.actFld.getText().length() > 50 || !this.actFld.getText().matches(regex)){
                throw new Exception("El campo de activity tiene mas de 50 caracteres o tiene caracteres especiales");
            }
            if(newDate.before(otherDate)){
                throw new Exception("La fecha es anterior a la de el dia de hoy");
            }
            if(this.plcFld.getText().length() > 30 || !this.plcFld.getText().matches(regex) ){
                throw new Exception("El campo de place tiene mas de 30 caracteres o tiene caracteres especiales");
            }
            if(!this.capFld.getText().matches(regexNum)){
                throw new Exception("El campo price solo permite numeros");
            }
            

            
            eve.setName(nameFld.getText());
            eve.setActivity(actFld.getText());
            eve.setDate(newDate);
            eve.setPlace(plcFld.getText());
            eve.setCapacity(Integer.parseInt(capFld.getText()));
            eve.setPrice(Float.valueOf(priceFld.getText()));
            u.setId(1L);
            eve.setAdmin((Admin) u);
            
            
            eventFact.getFactory().create_XML(eve);
            backBtn(event);
        } catch (Exception ex) {
            Logger.getLogger(CreateModifyController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * This Method confirm if the user want to close the window
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
     * This method is to send us to the previous window when we click on 
     * the back button.
     * @param event 
     */
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

    /**
     * The method loads the event data into the textfields of the window and 
     * makes the modify button visible and the create button invisible.
     * @param event 
     */
    public void loadDate(Event event) {
        nameFld.setText(event.getName());
        actFld.setText(event.getActivity());
        datePick.setValue(event.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        plcFld.setText(event.getPlace());
        capFld.setText(event.getCapacity().toString());
        priceFld.setText(event.getPrice().toString());
        this.id = event.getId();
        mdfBtn.setVisible(true);
        mdfBtn.setDisable(false);
        crtBtns.setVisible(false);
        crtBtns.setDisable(true);

    }

}
