/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import ofc2_cliente.logic.BusinessLogicException;
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
    private List<Coment> commentList;
    private Stage stage;
    @FXML
    private TableView<Coment> commentTableView;
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
    private TableColumn subjectComment;
    @FXML
    private TableColumn eventComment;
    @FXML
    private TableColumn clientComment;
    @FXML
    private TableColumn PrivacityComment;
    @FXML
    private TableColumn dateComment;
    @FXML
    private TableColumn messageComment;
    private ObservableList<Coment> comments;
    private Integer posicionComent;

    CommentFactoryManager commentFactory = new CommentFactoryManager();
    CommetRESTClient commentRest = (CommetRESTClient) commentFactory.createOfcManager();
    @FXML
    private TableColumn ratingComment;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This method will start the window
     *
     * @author Jp
     * @param root
     * @throws ofc2_cliente.logic.BusinessLogicException
     */
    public void initStage(Parent root) throws BusinessLogicException {

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
                new PropertyValueFactory<>("publication_date"));
        messageComment.setCellValueFactory(
                new PropertyValueFactory<>("message"));
        ratingComment.setCellValueFactory(
                new PropertyValueFactory<>("valoration"));
        //Show window.messageComment.setCellValueFactory(
        comments = FXCollections.observableArrayList(commentRest.findAllComents_XML(new GenericType<List<Coment>>() {
        }));
        commentTableView.setItems(comments);

        final ObservableList<Coment> tablaCommentCel = commentTableView.getSelectionModel().getSelectedItems();
        tablaCommentCel.addListener(cursorTableComment);

        //Show window
        informeBtn.setOnAction(this::showReport);
        stage.show();
        LOGGER.info("finished initStage(CommentWindow)");
    }

    private void showReport(ActionEvent event) {
        try {
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/ofc2_cliente/reports/commentReport.jrxml"));
            JRBeanCollectionDataSource dataItems;
            dataItems = new JRBeanCollectionDataSource((Collection<Coment>) this.commentTableView.getItems());
            //send name from report
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(CommentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void newComment() {
        Coment coment = new Coment();

    }

    @FXML
    private void deleteComment(ActionEvent event) {
        comments.remove(posicionComent);

    }

    @FXML
    private void editComment(ActionEvent event){
        
    }
    private final ListChangeListener<Coment> cursorTableComment = new ListChangeListener<Coment>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Coment> c) {
            setCommentSelected();
        }
    };

    public Coment getTableComentSelected() {
        if (commentTableView != null) {
            List<Coment> tabla = commentTableView.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Coment comentSelect = tabla.get(0);
                return comentSelect;
            }
        }
        return null;
    }

    private void setCommentSelected() {
        final Coment coment = getTableComentSelected();
        posicionComent = comments.indexOf(coment);
        System.out.println(coment.getMessage());
    }
}
