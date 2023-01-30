/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controller;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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
import ofc2_cliente.logic.SponsorManagerFactory;
import ofc2_cliente.model.Sponsor;

/**
 * This class will be controller all in the SponsorWindow FXML Controller class
 *
 * @author Elias
 */
public class SponsorWindowController{
    private ObservableList<Sponsor> sponsorList;
    private Sponsor sponsor;
    private Stage stage;
    @FXML
    private Pane sponsorPane;
    @FXML
    private TableView tbvSponsor;
    @FXML
    private TableColumn clName;
    @FXML
    private TableColumn clEmail;
    @FXML
    private TableColumn clState;
    @FXML
    private TableColumn clDate;
    @FXML
    private TableColumn clPhone;
    @FXML
    private TextField txtfFind;
    @FXML
    private Button createBtn;
    @FXML
    private Text txtSponsor;
    @FXML
    private Button modifyBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button findBtn;
    @FXML
    private ComboBox cbxFilter;
    @FXML
    private Button reportBtn;
    @FXML
    private TableColumn clAdType;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem mItModify;
    @FXML
    private MenuItem mItDelete;

    SimpleDateFormat format;
    SponsorManagerFactory sponsorFactory = new SponsorManagerFactory();
    private static final Logger LOGGER=Logger.getLogger("ofc2_cliente.controller.SponsorWindowController");

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * This method will start the window
     *
     * @author Jp
     * @author Elias
     * @param root
     */
    public void initStage(Parent root) {
            //Create a scene associated to the node graph root.
            Scene scene = new Scene(root);
            //Associate scene to primaryStage(Window)
            stage.setScene(scene);
            //title of the window: OFC SIGN IN.
            LOGGER.info("Init Window Sponsor");
            stage.setTitle("OFC Sponsor");
            stage.setResizable(false);
            stage.setOnShowing(this::windowShowing);
            createBtn.setOnAction(this::formSponsorWindow);
            modifyBtn.setOnAction(this::modifySponsor);
            deleteBtn.setOnAction(this::deleteSponsor);
            reportBtn.setOnAction(this::sponsorReport);
            mItDelete.setOnAction(this::deleteSponsor);
            mItModify.setOnAction(this::modifySponsor);
            cbxFilter.valueProperty().addListener(this::changePrompText);
            tbvSponsor.getSelectionModel().selectedItemProperty()
                    .addListener(this::enbledButtons);
            //ComboBox Options
            ObservableList<String> opciones = FXCollections.observableArrayList("Name", "Date", "Find All");
            cbxFilter.setItems(opciones);
            findBtn.setOnAction(this::findData);


            //Show window
            stage.show();
    }
    /**
     * 
     * This method show disables the modify and delete buttons, and 
     * the create button will be enabled. 
     * Then searches for sponsors data and adds in to the table.   
     * @param event 
     */
    private void windowShowing(WindowEvent event) {

        try {
            LOGGER.info("Window Showing in start");
            createBtn.setDisable(false);
            modifyBtn.setDisable(true);
            deleteBtn.setDisable(true);
            mItModify.setDisable(true);
            mItDelete.setDisable(true);
            clName.setCellValueFactory(new PropertyValueFactory<>("name"));
            clState.setCellValueFactory(new PropertyValueFactory<>("status"));
            clEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            clDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            clPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            clAdType.setCellValueFactory(new PropertyValueFactory<>("ad"));
            //Factory call method to find all sponsor data and adds into the table
            sponsorList = FXCollections.observableArrayList(sponsorFactory.createSponsorManager().
                    findAllSponsors_XML(new GenericType<List<Sponsor>>() {}));
            tbvSponsor.setItems(sponsorList);
            
            //Default selected
            cbxFilter.getSelectionModel().selectFirst();
        } catch (BusinessLogicException ex) { 
            LOGGER.log(Level.SEVERE, "Error al buscar los datos de los anuncios", 
                    ex.getMessage());
        }



    }
    public void changePrompText(ObservableValue observable,Object oldValue, Object newValue){
         if(cbxFilter.getValue().toString().equalsIgnoreCase("Date")){
             txtfFind.setPromptText("yyyy-MM-dd");
         }else{
             txtfFind.setPromptText("Name");
         }
     }
    /**
     * 
     * This method enables the modify and delete buttons when a table row is selected.
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    private void enbledButtons(ObservableValue observable, Object oldValue, Object newValue) {
        LOGGER.info("Enable Buttons");
        if(newValue!=null){
            modifyBtn.setDisable(false);
            deleteBtn.setDisable(false);
            mItDelete.setDisable(false);
            mItModify.setDisable(false);
        }
    }
    /**
     * 
     * This method opens the form window 
     * @param event 
     */
    @FXML
    private void formSponsorWindow(ActionEvent event) {
        try {
            LOGGER.info("Open the form Sponsor Window");
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/FormSponsorWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            FormSponsorWindowController mainStageController
                    = ((FormSponsorWindowController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);
            //start the stage
            mainStageController.initStage(root);
            this.stage.close();
            //Gets the Sponsor that has been created in the form
            Sponsor s = mainStageController.getSponsor();
            if(s == null){
                // And adds it to the table
                tbvSponsor.getItems().add(s);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error al abrir la ventana del formulario", 
                    ex.getMessage());
        }
    }
    /**
     * 
     * This method opens the form window sending the Sponsor data of the selected row.
     * @param event 
     */
    @FXML
    private void modifySponsor(ActionEvent event) {
        //This is the Sponsor selected row
        sponsor = ((Sponsor) this.tbvSponsor.getSelectionModel().getSelectedItem());
        try {
            LOGGER.info("Open the form Sponsor Window and sending the data");
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/FormSponsorWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            FormSponsorWindowController mainStageController
                    = ((FormSponsorWindowController) loader.getController());
            //Here send the data to the window 
            mainStageController.loadData(sponsor);
            //set the stage
            mainStageController.setStage(mainStage);
            //start the stage
            mainStageController.initStage(root);
            this.stage.close();
            //Update table
            tbvSponsor.getSelectionModel().clearSelection();
            tbvSponsor.refresh();


        } catch (IOException ex) {
            Logger.getLogger(SponsorWindowController.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
            LOGGER.log(Level.SEVERE, "Error al abrir la ventana del formulario", 
                    ex.getMessage());
        }
    }

    /**
     * This method deletes the ad from the selected row 
     * and asks for confirmation to delete it.
     * @param event Action Event
     */
    @FXML
    private void deleteSponsor(ActionEvent event) {
        try {
            //This saves the selected row into the object Sponsor
            sponsor = ((Sponsor) this.tbvSponsor.getSelectionModel().getSelectedItem());
            //This is the alert to confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Borrar la fila seleccionada?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If you press ok the factory will call the delete method by Sponsor ID 
            //and delete the selected row from the table.
            if (result.isPresent() && result.get() == ButtonType.OK) {
                LOGGER.info("Deleting the Sponsor Data");
                sponsorFactory.createSponsorManager().remove(sponsor.getId().toString());
                tbvSponsor.getItems().remove(sponsor);
                tbvSponsor.getSelectionModel().clearSelection();
                tbvSponsor.refresh();
            }
            //This Exception control the errors
        } catch (BusinessLogicException b) {
            LOGGER.log(Level.SEVERE, "Error al borrar la fila selecionada", 
                    b.getMessage());
        }
    }
    /**
     * This method searches the Sponsor data by date and name.
     *
     * @param event
     */
    @FXML
    private void findData(ActionEvent event) {
        ObservableList<Sponsor> list = null;
        String value = cbxFilter.getSelectionModel().getSelectedItem().toString();
        try {
            //If select the Date option in combobox
            //and enter a data that is not a date will display an error message
            if (cbxFilter.getSelectionModel().isSelected(1)
                    && !validateDate(txtfFind.getText())) {
                throw new Exception("El valor introducido no es una fecha, "
                        + "el formato para buscar por fecha es (yyyy-MM-dd)");
            }
            //If select the Name option in combobox
            //and enter data is a date will display an error message
            if (cbxFilter.getSelectionModel().isSelected(0)
                    && validateDate(txtfFind.getText())) {
                throw new Exception("No se puede introducir una fecha si estas "
                        + "buscando por nombre");
            }

            if (txtfFind.getText().isEmpty() && cbxFilter.getSelectionModel().getSelectedItem() != "Find All") {
                throw new Exception("El campo de Busqueda tiene que estar informado");
            } else {
                switch (value) {
                    case "Date":
                        list = FXCollections.observableArrayList(sponsorFactory.createSponsorManager().
                                findSponsorByDate_XML(new GenericType<Sponsor>() {
                                }, txtfFind.getText()));

                        break;
                    case "Name":
                        list = FXCollections.observableArrayList(sponsorFactory.createSponsorManager().
                                findSponsorByName_XML(new GenericType<Sponsor>() {
                                }, txtfFind.getText()));
                        break;
                    case "Find All":
                        list = FXCollections.observableArrayList(sponsorFactory.createSponsorManager().
                                findAllSponsors_XML(new GenericType<List<Sponsor>>() {
                                }));

                        break;
                }
                if (list.isEmpty()) {
                    throw new Exception("No se ha encontrado el dato especificado");
                }
                tbvSponsor.getItems().remove(sponsorList);
                tbvSponsor.setItems(list);
                txtfFind.clear();
            }

            
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK)
                    .showAndWait();
        }
    }
    /**
     * This method show a report from Sponsor
     * @param event 
     */
    
    @FXML
    private void sponsorReport(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/ofc2_cliente/report/sponsorReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Sponsor>)this.tbvSponsor.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperV = new JasperViewer(jasperPrint, false);
            jasperV.setVisible(true);
            
        } catch (JRException ex) {
            Logger.getLogger(SponsorWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method validate the date entered
     * @param fecha Date
     * @return true or false
     */
    public Boolean validateDate(String fecha){
        try {
            format = new SimpleDateFormat("yyyy-MM-dd");
            format.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
