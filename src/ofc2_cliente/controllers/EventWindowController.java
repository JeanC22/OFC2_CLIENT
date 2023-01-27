/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import static mondrian.olap.fun.vba.Vba.date;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import ofc2_cliente.logic.BusinessLogicException;
import ofc2_cliente.logic.EventFactory;
import ofc2_cliente.model.Event;

/**
 * FXML Controller class
 *
 * @author Jp
 */
public class EventWindowController {

    private Stage stage;
    @FXML
    private TextField dataFld;
    @FXML
    private Button findBtn;
    @FXML
    private ComboBox comboFind;
    @FXML
    private Button createBtn;
    @FXML
    private Button modifyBtn;
    @FXML
    private Button delBtn;
    @FXML
    private Button registerBtn;
    @FXML
    private Button reportBtn;
    @FXML
    private TableView eventTable;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colActivity;
    @FXML
    private TableColumn colDate;
    @FXML
    private TableColumn colPlace;
    @FXML
    private TableColumn colCap;
    @FXML
    private TableColumn colPrice;
    @FXML
    private MenuItem modifyMenu;
    @FXML
    private MenuItem DeleteMenu;
    @FXML
    private ImageView Event;

    private EventFactory eventFact = new EventFactory();
    ObservableList<Event> events;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd");
    
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
     * @author Jp
     * @param root
     *
     */
    public void initStage(Parent root) {
        LOGGER.info("Starting Stage");
        //init the scene with the root you got from singInController
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("OFC Event");
        eventTable.getSelectionModel().selectedItemProperty().addListener(this::setVisibleButtonss);
        stage.setOnShowing(this::windowShow);
        stage.setOnCloseRequest(this::cerrarVentana);
        createBtn.setOnAction(this::createModifyWindowCre);
        reportBtn.setOnAction(this::generateReport);
        delBtn.setOnAction(this::deleteData);
        modifyBtn.setOnAction(this::createModifyWindowMod);

        stage.show();
        LOGGER.info("Stage Started");

    }

    public void windowShow(WindowEvent event) {
        try {
            modifyBtn.setDisable(true);
            delBtn.setDisable(true);
            registerBtn.setDisable(true);
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colActivity.setCellValueFactory(new PropertyValueFactory<>("activity"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
            colCap.setCellValueFactory(new PropertyValueFactory<>("capacity"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            events = FXCollections.observableArrayList(eventFact.getFactory().findAllEvents_XML(new GenericType<List<Event>>() {
            }));
            
            eventTable.setItems(events);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(EventWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * this Method will be close LogedWindow and start the SingInWindow
     *
     * @author Jp
     */
    public void setVisibleButtonss(ObservableValue a, Object oldValue, Object newValue) {
        if (newValue != null) {
            modifyBtn.setDisable(false);
            delBtn.setDisable(false);
            registerBtn.setDisable(false);
        }else{
            modifyBtn.setDisable(true);
            delBtn.setDisable(true);
            registerBtn.setDisable(true);
        }
    }

    public void deleteData(ActionEvent event) {

        Event even = ((Event) eventTable.getSelectionModel().getSelectedItem());

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Quieres borrar la fila seleccionada?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                eventFact.getFactory().remove(even.getId().toString());
                eventTable.getItems().remove(even);
                eventTable.refresh();
            } else {
                event.consume();
            }

        } catch (BusinessLogicException ex) {
            Logger.getLogger(EventWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generateReport(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/ofc2_cliente/report/eventReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Event>) this.eventTable.getItems());

            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(EventWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createModifyWindowCre(ActionEvent event) {

        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/ventanaModificarEvento.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            CreateModifyController mainStageController
                    = ((CreateModifyController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);

            //start the stage
            mainStageController.initStage(root);
            this.stage.close();
        } catch (IOException ex) {
            Logger.getLogger(CreateModifyController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    public void createModifyWindowMod(ActionEvent event) {
        Event even = ((Event) eventTable.getSelectionModel().getSelectedItem());

        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/ventanaModificarEvento.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            CreateModifyController mainStageController
                    = ((CreateModifyController) loader.getController());
            //set the stage
            
            mainStageController.setStage(mainStage);
            //start the stage
            mainStageController.initStage(root);
            mainStageController.loadDate(even);
            this.stage.close();
        } catch (IOException ex) {
            Logger.getLogger(CreateModifyController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This Method confirm if the user want to close the window
     *
     * @author Iker
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

}
