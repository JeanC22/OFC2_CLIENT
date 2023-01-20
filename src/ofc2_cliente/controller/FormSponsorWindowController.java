/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controller;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elias
 */
public class FormSponsorWindowController{
    private Stage stage;
    @FXML
    private Pane sponsorForm;
    @FXML
    private Text txtName;
    @FXML
    private Button confirmBtn;
    @FXML
    private ComboBox<?> cmbAdType;
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
    private ComboBox<?> cmbEvents;
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
        returnBtn.setOnAction(this::returnSponsorWindow);
        //Show window
        stage.show();

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

    
}
