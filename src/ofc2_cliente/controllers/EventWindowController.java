/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
 * @author iker
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
    private ImageView Event;
    @FXML
    private ContextMenu menuCont;
    @FXML
    private MenuItem modifyMenu = new MenuItem();
    @FXML
    private MenuItem deleteMenu = new MenuItem();
    @FXML
    private MenuItem showComents = new MenuItem();
    @FXML
    private Button helpBtn;

    private EventFactory eventFact = new EventFactory();
    ObservableList<Event> events;
    ObservableList<String> combo = FXCollections.observableArrayList("FindByActivity", "FindByName", "FindByDate", "FindAll");
    private static final Logger LOGGER = Logger.getLogger("ofc2_cliente.Controllers");

    /**
     * setStage
     *
     * @param stage
     */
    public void setStage(Stage stage) {

        this.stage = stage;
    }

    /**
     * this Method will start the scenario and in case of an error it will
     * display an error message per window
     *
     * @author Jp
     * @param root
     *
     */
    public void initStage(Parent root) {
        try {

            LOGGER.info("Starting Stage");
            //init the scene with the root you got from singInController
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("OFC Event");
            modifyBtn.setDisable(true);
            delBtn.setDisable(true);
            registerBtn.setDisable(true);
            comboFind.setItems(combo);
            comboFind.getItems();
            comboFind.getSelectionModel().selectFirst();
            eventTable.setContextMenu(menuCont);

            eventTable.getSelectionModel().selectedItemProperty().addListener(this::setVisibleButtonss);
            stage.setOnShowing(this::windowShow);
            stage.setOnCloseRequest(this::cerrarVentana);
            createBtn.setOnAction(this::createModifyWindowCre);
            reportBtn.setOnAction(this::generateReport);
            delBtn.setOnAction(this::deleteData);
            modifyBtn.setOnAction(this::createModifyWindowMod);
            comboFind.valueProperty().addListener(this::showMessage);
            findBtn.setOnAction(this::find);
            modifyMenu.setOnAction(this::createModifyWindowMod);
            deleteMenu.setOnAction(this::deleteData);
            showComents.setOnAction(this::showComent);
            helpBtn.setOnAction(this::showWindowHelper);

            stage.show();

            LOGGER.info("Stage Started");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El servidor glashfish no se encuentra disponible", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * The method will show us all the event data from the database in a table
     * and in case of error it will show us an error message per window.
     *
     * @param event
     */
    private void windowShow(WindowEvent event) {
        try {
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colActivity.setCellValueFactory(new PropertyValueFactory<>("activity"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colDate.setCellFactory(column -> {
                TableCell<Event, Date> cell = new TableCell<Event, Date>() {
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
            colPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
            colCap.setCellValueFactory(new PropertyValueFactory<>("capacity"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colPrice.setCellFactory(column -> {
                TableCell<Event, Float> cell = new TableCell<Event, Float>() {

                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {
                            setText(item.toString() + " €");

                        }
                    }
                };

                return cell;
            });

            events = FXCollections.observableArrayList(eventFact.getFactory().findAllEvents_XML(new GenericType<List<Event>>() {
            }));
            eventTable.setItems(events);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(EventWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * the method is to enable and disable buttons depending on whether we have
     * selected a row in the table.
     */
    public void setVisibleButtonss(ObservableValue a, Object oldValue, Object newValue) {
        if (newValue != null) {
            modifyBtn.setDisable(false);
            delBtn.setDisable(false);
            registerBtn.setDisable(false);
        } else {
            modifyBtn.setDisable(true);
            delBtn.setDisable(true);
            registerBtn.setDisable(true);
        }
    }

    /**
     * The method will ask for confirmation to delete data from the database, on
     * confirming the deletion, the selected event will be deleted from the
     * database and in case of error, an error message will be displayed on the
     * screen.
     *
     * @param event
     */
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
            //Logger.getLogger(EventWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * The method generates a report sheet with the data shown in the window
     * table.
     *
     * @param event
     */
    public void generateReport(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/ofc2_cliente/report/eventReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Event>) this.eventTable.getItems());

            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(EventWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * This method initiates the window for modifying and creating events.
     *
     * @param event
     */
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

    /**
     * This method starts the modify and create events window, in which we pass
     * the event in question that we want to modify.
     *
     * @param event
     */
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

    public void showWindowHelper(ActionEvent event) {

        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/eventWindowHelp.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            EventWindowHelpController mainStageController
                    = ((EventWindowHelpController) loader.getController());
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

    /**
     * This method will load the data from the database into the table,
     * depending on the desired filtering.In case of any error it will display a
     * message on the screen.
     *
     * @param event
     */
    public void find(ActionEvent event) {
        String value;
        ObservableList<Event> events = null;
        value = comboFind.getSelectionModel().getSelectedItem().toString();
        try {
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colActivity.setCellValueFactory(new PropertyValueFactory<>("activity"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
            colCap.setCellValueFactory(new PropertyValueFactory<>("capacity"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            //check that the field is informed as long as it is not selected search all in the combo box
            if (dataFld.getText().isEmpty() && comboFind.getSelectionModel().getSelectedItem() != "FindAll") {
                throw new Exception("El campo de busqueda tiene que estar informado");
            } else {
                //depending on the value that is selected in the combo box we 
                //will use different search engines.
                switch (value) {
                    case "FindByActivity":
                        events = FXCollections.observableArrayList(eventFact.getFactory().findEventByActivity_XML(new GenericType<List<Event>>() {
                        }, dataFld.getText()));

                        break;
                    case "FindByName":
                        events = FXCollections.observableArrayList(eventFact.getFactory().findEventByName_XML(new GenericType<Event>() {
                        }, dataFld.getText()));
                        break;
                    case "FindByDate":
                        events = FXCollections.observableArrayList(eventFact.getFactory().findEventByDate_XML(new GenericType<List<Event>>() {
                        }, dataFld.getText()));
                        break;
                    case "FindAll":
                        events = FXCollections.observableArrayList(eventFact.getFactory().findAllEvents_XML(new GenericType<List<Event>>() {
                        }));
                        break;
                }
                eventTable.setItems(events);
                eventTable.refresh();
                dataFld.clear();
            }
        } catch (Exception ex) {
            Logger.getLogger(EventWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();

        }

    }

    /**
     * This method initialises the comments window that will contain the
     * comments associated to the events, for this we send the event in
     * question.
     *
     * @param event
     */
    public void showComent(ActionEvent event) {
        Event even = ((Event) eventTable.getSelectionModel().getSelectedItem());

        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/commentWindow.fxml");
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

    public void showMessage(ObservableValue observable, Object oldValue, Object newValue) {

        if (comboFind.getValue().toString().equalsIgnoreCase("FindByDate")) {
            dataFld.setPromptText("yyyy-MM-dd");
        } else {
            dataFld.setPromptText("");
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

    private LocalDate getDate(Date date) {
        return date == null ? LocalDate.now() : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
