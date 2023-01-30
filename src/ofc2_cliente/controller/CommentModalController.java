/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controller;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ofc2_cliente.App;
import ofc2_cliente.model.Coment;

/**
 * FXML Controller class
 *
 * @author DarkD
 */
public class CommentModalController {

    @FXML
    private Button backBtn;
    @FXML
    private Text userTxTF;
    @FXML
    private Text subjectTxTF;
    @FXML
    private Text eventTxTF;
    @FXML
    private Text dateTxTF;
    @FXML
    private Text messageTxTF;
    private static final Logger LOGGER = Logger.getLogger(CommentModalController.class.getName());
    private Stage stage;
    private Coment comment;

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) {
        try {
            LOGGER.info("Starting Stage");
            //init the scene with the root you got from singInController
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //Title from the window OFC LOGED
            stage.setTitle("OFC Modal Comment");

            userTxTF.setText(this.comment.getComClie().getUsername());

            subjectTxTF.setText(this.comment.getSubject());

            eventTxTF.setText(this.comment.getEvent().getName());

            dateTxTF.setText(getDate(this.comment.getPublication_date()).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));

            messageTxTF.setText(this.comment.getMessage());

            backBtn.setOnAction(this::closeWindow);
            stage.show();
            LOGGER.info("Stage Started");
        } catch (ParseException ex) {
            Logger.getLogger(CommentModalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeWindow(ActionEvent event) {
        this.stage.close();
        event.consume();
    }

    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("Starting SingInWindow");
        try {
            //link to get the FXML file
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/commentModal.fxml");
            //initialization the loader witk the FXML file
            FXMLLoader loader = new FXMLLoader(viewLink);
            //initialization the root (Parent) with the FXML Loader.load
            Parent root = (Parent) loader.load();
            //initialization the singInController
            CommentWindowController commentWindowController
                    = ((CommentWindowController) loader.getController());
            //set the Stage to the controll
            commentWindowController.setStage(primaryStage);
            //Start the Stage
            commentWindowController.initStage(root);
            LOGGER.info("Started SingInWindow");

        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    public void getComment(Coment coment) {
        this.comment = coment;
    }

    private LocalDate getDate(Date date) {
        return date == null ? LocalDate.now() : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
