/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controller;

import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.ws.rs.core.GenericType;
import ofc2_cliente.logic.CommentFactoryManager;
import ofc2_cliente.logic.CommetRESTClient;
import ofc2_cliente.model.Coment;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class CommentWindowController {

    private static final Logger LOGGER = Logger.getLogger(CommentWindowController.class.getName());

    private Stage stage;
    @FXML
    private TableView<?> commentTableView;
    @FXML
    private TextField findCommentTxTF;
    @FXML
    private MenuButton filterMenu;
    @FXML
    private Button searchButton;
    @FXML
    private Button showCommentBTN;
    @FXML
    private Button informeBtn;
    @FXML
    private TableColumn<?, ?> subjectComment;
    @FXML
    private TableColumn<?, ?> eventComment;
    @FXML
    private TableColumn<?, ?> clientComment;
    @FXML
    private TableColumn<?, ?> PrivacityComment;
    @FXML
    private TableColumn<?, ?> dateComment;
    @FXML
    private TableColumn<?, ?> messageComment;
    private ObservableList<Coment> comments;
  
    CommentFactoryManager commentFactory = new CommentFactoryManager();
    CommetRESTClient commentRest = (CommetRESTClient) commentFactory.createOfcManager();
    @FXML
    private TableColumn<?, ?> ratingComment;

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

        LOGGER.info("starting initStage(SignIN)");
        //Create a scene associated to the node graph root.
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //title of the window: OFC SIGN IN.
        stage.setTitle("Comment Window");
        stage.setResizable(false);
        //Set factories for cell values in users table columns.
        subjectComment.setCellValueFactory(
                new PropertyValueFactory<>("subject"));
        eventComment.setCellValueFactory(
                new PropertyValueFactory<>("event"));
        clientComment.setCellValueFactory(
                new PropertyValueFactory<>("comClie"));
        PrivacityComment.setCellValueFactory(
                new PropertyValueFactory<>("privacity"));
        dateComment.setCellValueFactory(
                new PropertyValueFactory<>("perfil"));
        messageComment.setCellValueFactory(
                new PropertyValueFactory<>("perfil"));
        ratingComment.setCellValueFactory(
                new PropertyValueFactory<>("valoration"));
        //Show window.messageComment.setCellValueFactory(
        comments = FXCollections.observableArrayList(commentRest.findAllComents_XML(GenericType<List<Coment>>));
        //Show window
        stage.show();
        LOGGER.info("finished initStage(CommentWindow)");
    }

}
