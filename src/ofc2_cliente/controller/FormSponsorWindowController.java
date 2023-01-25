/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ofc2_cliente.model.AdType;
import ofc2_cliente.model.Sponsor;

/**
 * FXML Controller class
 *
 * @author Elias
 */
public class FormSponsorWindowController{
   private static String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Stage stage;
    private Sponsor sponsor;
    
    private ObservableList<Sponsor> sponsorList;
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
    private ComboBox cmbEvents;
    @FXML
    private CheckBox chbxState;
    @FXML
    private DatePicker dpDate;
    
     public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This method will start the window
     *
     * @author Jp
     * @param root
     */
    public void initStage(Parent root){
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);

        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //title of the window: OFC SIGN IN.
        stage.setTitle("OFC Sponsor Form");
        stage.setResizable(false);
        stage.setOnShowing(this::windowShowing);
        returnBtn.setOnAction(this::returnSponsorWindow);
        confirmBtn.setOnAction(this::btnCreateSponsor);
        
        //Show window
        stage.show();

    }
    
    private void windowShowing(WindowEvent event) {
        txtFName.requestFocus();
        confirmBtn.setDisable(true);
        txtFName.setOnKeyReleased(this::enableConfirmBtn);
        txtFPhone.setOnKeyReleased(this::enableConfirmBtn);
        txtFEmail.setOnKeyReleased(this::enableConfirmBtn);
        dpDate.setOnKeyReleased(this::enableConfirmBtn);
    }
    
    @FXML
    private void returnSponsorWindow(ActionEvent event) {
        try {
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
            event.consume();
            
        } catch (IOException ex) {
            Logger.getLogger(FormSponsorWindowController.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    private void enableConfirmBtn(KeyEvent event) {
        
        try {
            if(!this.txtFName.getText().isEmpty() 
                    && !this.txtFPhone.getText().isEmpty()
                    && !this.txtFEmail.getText().isEmpty()
                    && !dpDate.toString().isEmpty()){
                confirmBtn.setDisable(false);
            }else{
                confirmBtn.setDisable(true);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
    @FXML
    private void btnCreateSponsor(ActionEvent event) {
        try {
            
            if(this.txtFName.getText().length() > 30){
                throw new Exception("La longitud maxima "
                        + "del campo Name es de 30 caracteres");
            }
            if(!isNumeric(this.txtFPhone.getText().trim())){
                throw new Exception("El campo Phone no es numerico");
            }
            
            if(this.txtFPhone.getText().length() > 9
                    || this.txtFPhone.getText().length()< 9){
                throw new Exception("La longitud maxima "
                        + "del campo Phone es de 9 caracteres o minimo de 9 caracteres");
            }
            
            if(!this.txtFEmail.getText().matches(regexEmail)){
                throw new Exception("El campo Email no tiene el formato adecuado");
            }
            
            
            try {
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
                event.consume();

            } catch (IOException ex) {
                Logger.getLogger(FormSponsorWindowController.class.getName())
                        .log(Level.SEVERE, ex.getMessage(), ex);
            }
            
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK)
                    .showAndWait();
            
        }
    }
    
    public Boolean isNumeric(String valor){
        try {
            if(valor !=null){
                Integer.parseInt(valor);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
}
