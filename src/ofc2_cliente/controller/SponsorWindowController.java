/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controller;

import java.io.IOException;
import java.net.URL;
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
import ofc2_cliente.logic.SponsorRESTfulClient;
import ofc2_cliente.model.Sponsor;

/**
 * FXML Controller class
 *
 * @author Elias
 */
public class SponsorWindowController{
    private ObservableList<Sponsor> sponsorList;
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
    private TableColumn clEvents;
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
    
    SponsorManagerFactory sponsorFactory = new SponsorManagerFactory();
    SponsorRESTfulClient rest = (SponsorRESTfulClient) sponsorFactory.createSponsorManager();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This method will start the window
     *
     * @author Jp
     * @param root
     */
    public void initStage(Parent root) {
            //Create a scene associated to the node graph root.
            Scene scene = new Scene(root);

            //Associate scene to primaryStage(Window)
            stage.setScene(scene);
            //title of the window: OFC SIGN IN.
            stage.setTitle("OFC Sponsor");
            stage.setResizable(false);
            stage.setOnShowing(this::windowShowing);
            createBtn.setOnAction(this::formSponsorWindow);
            modifyBtn.setOnAction(this::formSponsorWindow);
            deleteBtn.setOnAction(this::deleteSponsor);
            reportBtn.setOnAction(this::sponsorReport);
            tbvSponsor.getSelectionModel().selectedItemProperty()
                    .addListener(this::enbledButtons);

            //Show window
            stage.show();
        

    }
    
    private void windowShowing(WindowEvent event) {
        try {
            createBtn.setDisable(false);
            modifyBtn.setDisable(true);
            deleteBtn.setDisable(true);
            clName.setCellValueFactory(new PropertyValueFactory<>("name"));
            clState.setCellValueFactory(new PropertyValueFactory<>("status"));
            clEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            clDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            clPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            clAdType.setCellValueFactory(new PropertyValueFactory<>("ad"));
            clEvents.setCellValueFactory(new PropertyValueFactory<>("events"));
            sponsorList = FXCollections.observableArrayList(rest
                    .findAllSponsors_XML(new GenericType<List<Sponsor>>() {}));
            tbvSponsor.setItems(sponsorList);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(SponsorWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void enbledButtons(ObservableValue observable, Object oldValue, Object newValue) {
        if(newValue!=null){
            modifyBtn.setDisable(false);
            deleteBtn.setDisable(false);
        }
    }
    
    @FXML
    private void formSponsorWindow(ActionEvent event) {
        try {
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
            
        } catch (IOException ex) {
            Logger.getLogger(SponsorWindowController.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
     @FXML
    private void deleteSponsor(ActionEvent event) {
        try {
            Sponsor sponsor = ((Sponsor) this.tbvSponsor.getSelectionModel().getSelectedItem());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Borrar la fila seleccionada?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                rest.remove(sponsor.getId().toString());
                tbvSponsor.getItems().remove(sponsor);
                tbvSponsor.refresh();
            }
        } catch (BusinessLogicException b) {
            Logger.getLogger(SponsorWindowController.class.getName()).log(Level.SEVERE, null, b);
        }

    }

    @FXML
    private void sponsorReport(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport("/ofc2_cliente/report/sponsorReport.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Sponsor>)this.tbvSponsor.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperV = new JasperViewer(jasperPrint);
            jasperV.setVisible(true);
            
        } catch (JRException ex) {
            Logger.getLogger(SponsorWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

