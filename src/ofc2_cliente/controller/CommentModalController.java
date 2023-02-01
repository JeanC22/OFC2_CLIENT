/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controller;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    @FXML
    private Pane modalComment;
    @FXML
    private Text usersTxTF;

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
            scene.getStylesheets().addAll(this.getClass().getResource("/ofc2_cliente/ui/resources/style.css").toExternalForm());
            stage.resizableProperty().set(false);
            usersTxTF.setText(this.comment.getComClie().getUsername());

            subjectTxTF.setText(this.comment.getSubject());

            eventTxTF.setText(this.comment.getEvent().getName());

            dateTxTF.setText(getDate(this.comment.getPublication_date())
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));

            messageTxTF.setText(this.comment.getMessage());

            backBtn.setOnAction(this::closeWindow);
            stage.show();
            LOGGER.info("Stage Started");
        } catch (ParseException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
            Logger.getLogger(CommentModalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeWindow(ActionEvent event) {
        this.stage.close();
        event.consume();
    }

   public Stage GetStage() {

        return this.stage;
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
