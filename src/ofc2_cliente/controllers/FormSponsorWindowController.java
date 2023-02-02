/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import ofc2_cliente.controllers.SponsorWindowController;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ofc2_cliente.logic.BusinessLogicException;
import ofc2_cliente.logic.SponsorManagerFactory;
import ofc2_cliente.model.AdType;
import ofc2_cliente.model.Admin;
import ofc2_cliente.model.Event;
import ofc2_cliente.model.Sponsor;
import ofc2_cliente.model.User;
/**
 * This class will be controller all in the FormSponsorWindow FXML 
 *
 * @author Elias
 */
public class FormSponsorWindowController{
   private static String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
   private static String regexNumber = "[0-9]+";
    private Stage stage;
    private Sponsor sponsor;
    private AdType ad;
    @FXML
    private Pane sponsorForm;
    @FXML
    private Text txtName;
    @FXML
    private Button confirmBtn;
    @FXML
    private ComboBox cmbAdType;
    @FXML
    private Text txtAdType;
    @FXML
    private Text txtSponsor;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtFPhone;
    @FXML
    private Text txtPhone;
    @FXML
    private Text txtDate;
    @FXML
    private TextField txtFEmail;
    @FXML
    private Text txtEmail;
    @FXML
    private Button returnBtn;
    @FXML
    private Text txtAdType1;
    @FXML
    private CheckBox chbxState;
    @FXML
    private DatePicker dpDate;
    Event eventos = new Event();
    SponsorManagerFactory sponsorFactory = new SponsorManagerFactory();
    private static final Logger LOGGER=Logger.getLogger("ofc2_cliente.controller.FormSponsorWindowController");

     public void setStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * This method will start the window
     *
     * @author Elias
     * @param root
     */
    public void initStage(Parent root){
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/ofc2_cliente/ui/resources/style.css").toExternalForm());
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        LOGGER.info("Init The From Window");
        //title of the window: OFC SIGN IN.
        stage.setTitle("OFC Sponsor Form");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setOnCloseRequest(this::closeWindow);
        stage.setOnShowing(this::windowShowing);
        returnBtn.setOnAction(this::returnSponsorWindow);
        confirmBtn.setOnAction(this::createAndUpdateSponsor);

        //Show window
        stage.show();
    }
    /**
     * This method show the confirm button will be disabled, 
     * the focus will be on the name field and the combobox will 
     * have different choices of ad types.
     * @param event 
     */
    private void windowShowing(WindowEvent event) {
        LOGGER.info("Window Showing");
        txtFName.requestFocus();
        confirmBtn.setDisable(true);
        txtFName.setOnKeyReleased(this::enableConfirmBtn);
        txtFPhone.setOnKeyReleased(this::enableConfirmBtn);
        txtFEmail.setOnKeyReleased(this::enableConfirmBtn);
        dpDate.setOnKeyReleased(this::enableConfirmBtn);
        cmbAdType.getItems().addAll(ad.VIDEO.toString(), 
                ad.PANCARTA.toString(), ad.POSTER.toString());
    }
    /**
     * This method returns to the previous window by closing the form window.
     * @param event 
     */
    @FXML
    private void returnSponsorWindow(ActionEvent event) {
        try {
            LOGGER.info("Return to the Sponsor Window");
             Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/SponsorWindow.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            SponsorWindowController mainStageController
                    = ((SponsorWindowController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);
            //start the stage
            mainStageController.initStage(root);
            this.stage.close();
            
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error al volver a la ventana anterior", 
                    ex.getMessage());
        }
    }
 
    /**
     * This method fills in the form fields with data from the Sponsor in order to make changes. 
     * @param sp Sponsor Object
     */
    public void loadData(Sponsor sp){
        LOGGER.info("Load fields from Sponsor Data");
        this.sponsor = sp;
        txtFName.setText(sp.getName());
        txtFEmail.setText(sp.getEmail());
        txtFPhone.setText(String.valueOf(sp.getPhone()));
        //This displays the indicated date of the Sponsor by converting the Date to Localdate.
        dpDate.setValue(sp.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        cmbAdType.getSelectionModel().select(sp.getAd());
        chbxState.setSelected(sp.getStatus());
    }
    /**
     * This method checks that the text fields are informed to enable the confirm button, 
     * otherwise it is disabled.
     * @param event KeyEvent
     */
    private void enableConfirmBtn(KeyEvent event) {
        try {
            
            if(!this.txtFName.getText().isEmpty() 
                    && !this.txtFPhone.getText().isEmpty()
                    && !this.txtFEmail.getText().isEmpty()
                    && !dpDate.toString().isEmpty()
                    && !cmbAdType.getItems().isEmpty()
                    && !dpDate.toString().isEmpty()){
            LOGGER.info("Enable the confirm Button");
                confirmBtn.setDisable(false);
            } else{
                LOGGER.info("Disable the confirm Button");
                confirmBtn.setDisable(true);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al habilitar el boton confirm", 
                    e.getMessage());
        }
    }
    /**
     * This method validates the text fields and depending on the action 
     * will create a new Sponsor or update an existing one.
     * @param event 
     */
    @FXML
    private void createAndUpdateSponsor(ActionEvent event) {
        LocalDate date = dpDate.getValue();
        User admin = new Admin();

        try {
            //In the field name the max length is 30 characters
            if (this.txtFName.getText().length() > 30) {
                throw new Exception("La longitud maxima "
                        + "del campo Name es de 30 caracteres");
            }
            //This check the field phone is numeric
            if (!this.txtFPhone.getText().matches(regexNumber)) {
                throw new Exception("El campo Phone no es numerico");
            }
            //In the field phone max an min length is 9 digits
            if (this.txtFPhone.getText().length() > 9
                    || this.txtFPhone.getText().length() < 9) {
                throw new Exception("La longitud maxima "
                        + "del campo Phone es de 9 caracteres o minimo de 9 caracteres");
            }
            //In the filed Email check the format is correct(example@example.com) 
            if (!this.txtFEmail.getText().matches(regexEmail)) {
                throw new Exception("El campo Email no tiene el formato adecuado");
            }
            
            try {
                //If Sponsor object is exist you can change the data
                //Then Factory call method to update Sponsor data
                if (this.sponsor != null) {
                    LOGGER.info("Updating Sponsor Data");
                    this.sponsor.setName(txtFName.getText());
                    this.sponsor.setPhone(Integer.parseInt(txtFPhone.getText()));
                    this.sponsor.setEmail(txtFEmail.getText());
                    this.sponsor.setStatus(chbxState.isSelected());
                    this.sponsor.setDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    this.sponsor.setAd(AdType.valueOf(cmbAdType.getSelectionModel().getSelectedItem().toString()));
                    this.sponsor.setAdmin((Admin) admin);
                    sponsorFactory.createSponsorManager().edit_XML(this.sponsor);
                //or if not exist you can create a new Sponsor data
                //And Factory call method to create Sponsor
                }else {
                    LOGGER.info("Creating a new Sponsor");
                    Sponsor sponsorD = new Sponsor();
                    sponsorD.setName(txtFName.getText());
                    sponsorD.setPhone(Integer.parseInt(txtFPhone.getText()));
                    sponsorD.setEmail(txtFEmail.getText());
                    sponsorD.setStatus(chbxState.isSelected());
                    sponsorD.setDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    sponsorD.setAd(AdType.valueOf(cmbAdType.getSelectionModel().getSelectedItem().toString()));
                    sponsorD.setAdmin((Admin) admin);
                    sponsorFactory.createSponsorManager().create_XML(sponsorD);
                }
                returnSponsorWindow(event);
                
            } catch (BusinessLogicException ex) {
                LOGGER.log(Level.SEVERE, "Error al abrir la ventana,"
                        + "o al crear el nuevo anuncio o al actualizar el anuncio", 
                        ex.getMessage());
            }
        } catch (Exception e) {
            //This alert show when the validations of fields is not correct
            new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK)
                    .showAndWait();
        }
    }
    /**
     * This method get Sponsor object
     * @return Sponsor Object
     */
    public Sponsor getSponsor() {
        return sponsor;
    }
    
     /**
     * This Method confirm if the user want to close the window
     *
     * @author Elias
     * @param event
     */
    @FXML
    public void closeWindow(WindowEvent event) {
        LOGGER.info("starting cerrarVentana");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Quiere salir de la aplicacion?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            LOGGER.info("finished cerrarVentana");

        } else {
            event.consume();
        }
    }
    
    
    
    
    
}