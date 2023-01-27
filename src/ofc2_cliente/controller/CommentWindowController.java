/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
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

    private static final Logger LOGGER = Logger
            .getLogger(CommentWindowController.class.getName());
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
    private TableColumn<Coment, String> subjectComment;
    @FXML
    private TableColumn eventComment;
    @FXML
    private TableColumn clientComment;
    @FXML
    private TableColumn<Coment, String> PrivacityComment;
    @FXML
    private TableColumn<Coment, Date> dateComment;
    @FXML
    private TableColumn<Coment, String> messageComment;
    @FXML
    private TableColumn<Coment, String> ratingComment;
    private ObservableList<Coment> comments;
    private Integer posicionComent;

    CommentFactoryManager commentFactory = new CommentFactoryManager();
    CommetRESTClient commentRest = (CommetRESTClient) commentFactory.createOfcManager();

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
        subjectComment.setCellFactory(TextFieldTableCell.<Coment>forTableColumn());
        subjectComment.setOnEditCommit(
                (CellEditEvent<Coment, String> t) -> {
                    ((Coment) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setSubject(t.getNewValue());
                });
        eventComment.setCellValueFactory(
                new PropertyValueFactory<>("event"));
        clientComment.setCellValueFactory(
                new PropertyValueFactory<>("comClie"));
        PrivacityComment.setCellValueFactory(
                new PropertyValueFactory<>("privacity"));
        PrivacityComment.setCellFactory(TextFieldTableCell.<Coment>forTableColumn());
        PrivacityComment.setOnEditCommit(
                (CellEditEvent<Coment, String> t) -> {
                    ((Coment) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setPrivacity(t.getNewValue());
                });
        dateComment.setCellValueFactory(
                new PropertyValueFactory<>("privacity"));
        Callback<TableColumn<Coment, Date>, TableCell<Coment, Date>> dateCellFactory
                = (TableColumn<Coment, Date> param) -> new DateEditingCell();
        dateComment.setCellValueFactory(cellData -> cellData.getValue().publicDateProperty());
        dateComment.setCellFactory(dateCellFactory);
        dateComment.setOnEditCommit(
                (TableColumn.CellEditEvent<Coment, Date> t) -> {
                    ((Coment) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow())).setPublication_date(t.getNewValue());

                });

        messageComment.setCellValueFactory(
                new PropertyValueFactory<>("message"));
        messageComment.setCellFactory(TextFieldTableCell.<Coment>forTableColumn());
        messageComment.setOnEditCommit(
                (CellEditEvent<Coment, String> t) -> {
                    ((Coment) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setMessage(t.getNewValue());
                });
        ratingComment.setCellValueFactory(
                new PropertyValueFactory<>("valoration"));
        ratingComment.setCellFactory(TextFieldTableCell.<Coment>forTableColumn());
        ratingComment.setOnEditCommit(
                (CellEditEvent<Coment, String> t) -> {
                    ((Coment) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setValoration(t.getNewValue());
                });
        //Show window.messageComment.setCellValueFactory(
        comments = FXCollections.observableArrayList(commentRest.
                findAllComents_XML(new GenericType<List<Coment>>() {
                }));
        commentTableView.setItems(comments);

        final ObservableList<Coment> tablaCommentCel = commentTableView.
                getSelectionModel().getSelectedItems();
        tablaCommentCel.addListener(cursorTableComment);

        //Context Menu
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem createNewCommentMenuIt = new MenuItem("Create new comment");
        // create new coment 
        //---------------------------------------------------

        final TextField addSubject = new TextField();
        addSubject.setPromptText("Subject");
        addSubject.setMaxWidth(subjectComment.getPrefWidth());

        final TextField addPrivacity = new TextField();
        addPrivacity.setMaxWidth(PrivacityComment.getPrefWidth());
        addPrivacity.setPromptText("true");

        final TextField addMessage = new TextField();
        addMessage.setPromptText("message");
        addMessage.setMaxWidth(messageComment.getPrefWidth());

        final TextField addRating = new TextField();
        addRating.setPromptText("10");
        addRating.setMaxWidth(ratingComment.getPrefWidth());

        /*
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());*/
        //-------------------------------------------------
        createNewCommentMenuIt.setOnAction((ActionEvent e) -> {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

            Coment comment = new Coment(1L, 3L, date, date, addMessage.getPromptText(), addRating.getPromptText(), addPrivacity.getPromptText(), addSubject.getPromptText());
            comments.add(comment);
            addMessage.clear();
            addSubject.clear();
            addPrivacity.clear();
            addRating.clear();
            try {
                newCommentCommit(comment);
                commentTableView.refresh();
            } catch (BusinessLogicException ex) {
                Logger.getLogger(CommentWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuItem refreshWindow = new MenuItem("Refresh Table");
        refreshWindow.setOnAction((event) -> {
          
         this.commentTableView.refresh();
        });
        contextMenu.getItems().add(refreshWindow);
        
        contextMenu.getItems().add(createNewCommentMenuIt);
        commentTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(commentTableView, ((e.getScreenX() - e.getX()) + (e.getSceneX() - 110)), e.getScreenY());
            }

        });

        MenuItem deleteCommentMenuIt = new MenuItem("Delete comment");
        deleteCommentMenuIt.setOnAction((event) -> {
            try {
                this.deleteComment();
                commentTableView.getItems().remove(commentTableView.getSelectionModel().getSelectedItem());
            } catch (BusinessLogicException ex) {
                Logger.getLogger(CommentWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
a
        contextMenu.getItems().add(deleteCommentMenuIt);

        stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (commentTableView.getSelectionModel().getSelectedItem() != null && e.getCode() == KeyCode.ENTER) {
                commentTableView.refresh();
            }
        });

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
    private void newCommentCommit(Coment coment) throws BusinessLogicException {
        try {
            commentRest.createComent_XML(coment);
            commentTableView.refresh();
        } catch (Exception e) {
        }

    }

    private void deleteComment() throws BusinessLogicException {
        System.out.println("Deleting: " + this.comments.toString());
        String clientID = String.valueOf(commentTableView.getSelectionModel().getSelectedItem().getComentid().getClient_id());
        String eventID = String.valueOf(commentTableView.getSelectionModel().getSelectedItem().getComentid().getEvent_id());
        commentRest.deleteComent(clientID, eventID);

    }

    @FXML
    private void editComment(ActionEvent event) {

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

    class EditingCell extends TableCell<Coment, String> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> arg0,
                            Boolean arg1, Boolean arg2) -> {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }

    }

    class DateEditingCell extends TableCell<Coment, Date> {

        private DatePicker datePicker;

        private DateEditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createDatePicker();
                setText(null);
                setGraphic(datePicker);
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(getDate().toString());
            setGraphic(null);
        }

        @Override
        public void updateItem(Date item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (datePicker != null) {
                        datePicker.setValue(getDate());
                    }
                    setText(null);
                    setGraphic(datePicker);
                } else {
                    setText(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
                    setGraphic(null);
                }
            }
        }

        private void createDatePicker() {
            datePicker = new DatePicker(getDate());
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.setOnAction((e) -> {
                System.out.println("Committed: " + datePicker.getValue().toString());
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
        }

        private LocalDate getDate() {
            return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }
}
