/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import ofc2_cliente.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
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
import ofc2_cliente.model.Client;
import ofc2_cliente.model.Coment;
import ofc2_cliente.model.User;

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
    private ComboBox<String> filterMenu;
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
    private TableColumn<Coment, Boolean> PrivacityComment;
    @FXML
    private TableColumn<Coment, Date> dateComment;
    @FXML
    private TableColumn<Coment, String> messageComment;
    @FXML
    private TableColumn<Coment, String> ratingComment;
    private ObservableList<Coment> comments;
    private Integer posicionComent;
    private User client = new Client();
    private Integer commentCount = 0;
    private Event eventoo = new Event();
    private User user = new User();
    private Coment coment;

    CommentFactoryManager commentFactory = new CommentFactoryManager();
    CommetRESTClient commentRest = (CommetRESTClient) commentFactory.getFactory();
    ObservableList<String> options = FXCollections.observableArrayList("Find All", "Find By Subject");
    @FXML
    private Button helpBTN;
    @FXML
    private Button backBtn;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This Method get the userLogin from the EventWindow
     *
     * @param userLogin
     */
    public void getUser(User userLogin) {
        this.user = userLogin;
    }

    /**
     * This Method get the eventSelected from the EventWindow
     *
     * @param eventSelected
     */
    public void getEvent(ofc2_cliente.model.Event eventSelected) {
        this.eventoo = eventSelected;
    }

    /**
     * This method will start the window
     *
     * @author Jp
     * @param root
     * @throws ofc2_cliente.logic.BusinessLogicException
     *
     */
    public void initStage(Parent root) throws BusinessLogicException {

        LOGGER.info("starting initStage(commentWindwo)");
        //Create a scene associated to the node graph root.

        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/ofc2_cliente/ui/resources/style.css").toExternalForm());
        filterMenu.setItems(options);
        filterMenu.getItems();
        filterMenu.getSelectionModel().selectFirst();
        //Associate scene to primaryStage(Window)
        stage.setScene(scene);
        //title of the window: OFC SIGN IN.
        stage.setTitle("Comment Window");
        stage.setResizable(false);
        stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.ico").toString()));
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
        PrivacityComment.setCellFactory(CheckBoxTableCell.<Coment>forTableColumn(PrivacityComment));
        PrivacityComment.setCellValueFactory((CellDataFeatures<Coment, Boolean> param) -> param.getValue().getPrivacityProperty());

        dateComment.setCellValueFactory(
                new PropertyValueFactory<>("publication_date"));
        Callback<TableColumn<Coment, Date>, TableCell<Coment, Date>> dateCellFactory
                = (TableColumn<Coment, Date> param) -> new DateEditingCell();
        dateComment.setCellValueFactory(cellData -> cellData.getValue()
                .publicDateProperty());
        dateComment.setCellFactory(dateCellFactory);
        dateComment.setOnEditCommit(
                (TableColumn.CellEditEvent<Coment, Date> t) -> {
                    ((Coment) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setPublication_date(t.getNewValue());
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
        try {
            LOGGER.info("FIND ALL COMMENTS");
            LOGGER.info("--------------------SEARCHING FOR A COMMENT----------------");
            comments = FXCollections.observableArrayList(commentFactory.getFactory().
                    findAllComents_XML(new GenericType<List<Coment>>() {
                    }));
            LOGGER.info("--------------------COMMENTS SEARCHED----------------");
            commentTableView.setItems(comments);
            LOGGER.info("--------------------COMMENTS SETTED----------------");

        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
            Logger.getLogger(CommentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

        final TextField addMessage = new TextField();
        addMessage.setPromptText("message");

        final TextField addRating = new TextField();
        addRating.setPromptText("10");

        //-------------------------------------------------
        createNewCommentMenuIt.setOnAction((ActionEvent e) -> {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate localDate = LocalDate.now();
            //En caso que el evento no venga con id o el usuario no tenga id 
            System.out.println(this.eventoo.getId());
            System.out.println(this.user.getId());
            if (this.eventoo.getId() == null || this.user.getId() == null) {
                if (this.user.getId() == null) {
                    this.user.setId(7L);
                }
                if (this.eventoo.getId() == null) {
                    this.eventoo.setId(6L);
                }
            }
            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
            Coment comment = new Coment(this.eventoo.getId(), this.user.getId(), date, date, addMessage
                    .getPromptText(), addRating.getPromptText(),
                    Boolean.TRUE, addSubject.getPromptText());
            comments.add(comment);
            addMessage.clear();
            addSubject.clear();
            addRating.clear();
            try {
                newCommentCommit(comment);
                try {
                    this.cargarTodo();
                } catch (Exception ex) {
                    Logger.getLogger(CommentWindowController.class.getName())
                            .log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (BusinessLogicException ex) {
                Logger.getLogger(CommentWindowController.class.getName())
                        .log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });

        // contextMenu.getItems().add(createNewCommentMenuIt);
        // DELETE COMMENT
        MenuItem deleteCommentMenuIt = new MenuItem("Delete comment");
        deleteCommentMenuIt.setOnAction((event) -> {
            try {
                this.deleteComment();
            } catch (BusinessLogicException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
                Logger.getLogger(CommentWindowController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        });
        commentTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (commentTableView.getSelectionModel().getSelectedItem() != null) {
                contextMenu.getItems().clear();
                contextMenu.getItems().add(deleteCommentMenuIt);
            } else {
                contextMenu.getItems().clear();
                contextMenu.getItems().add(createNewCommentMenuIt);
            }
            if (e.getButton() == MouseButton.SECONDARY) {

                contextMenu.show(commentTableView, ((e.getScreenX()
                        - e.getX()) + (e.getSceneX() - 110)), e.getScreenY());
            }
        });

        stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {

            if (commentTableView.getSelectionModel().getSelectedItem() != null
                    && e.getCode() == KeyCode.ENTER) {
                LOGGER.info("UPDATING A COMMENT");

                try {
                    commentRest.editComent_XML(commentTableView
                            .getSelectionModel().getSelectedItem());
                    LOGGER.info("--------------------COMMENT UPDATED----------------");
                    LOGGER.info(commentTableView
                            .getSelectionModel().getSelectedItem().toString());
                    LOGGER.info("----------------------------------------------------");
                    commentTableView.refresh();
                } catch (BusinessLogicException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                    Logger.getLogger(CommentWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (commentTableView.getSelectionModel().getSelectedItem() != null
                    && e.getCode() == KeyCode.ESCAPE) {
                commentTableView.getSelectionModel().clearSelection();
                showCommentBTN.setDisable(true);
            }
        });

        searchButton.setOnAction(this::find);

        this.showCommentBTN.setDisable(true);
        showCommentBTN.setOnAction(this::openModalCommnet); //Show window
        stage.setOnCloseRequest(this::cerrarVentana);
        helpBTN.setOnAction(this::showWindowHelper);
        informeBtn.setOnAction(this::showReport);
        backBtn.setOnAction(this::closeWindow);
        stage.show();
        LOGGER.info("finished initStage(CommentWindow)");
    }

    /**
     * This method when we want to exit the application will ask us for
     * confirmation to exit.
     *
     * @param event
     * @author iker
     */
    public void cerrarVentana(WindowEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("You want to exit the application?");
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/ofc2_cliente/ui/resources/dialog.css").toExternalForm());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    public void showWindowHelper(ActionEvent event) {

        try {
            Stage mainStage = new Stage();
            URL viewLink = getClass().getResource(
                    "/ofc2_cliente/ui/commentWindowHelp.fxml");
            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            //Get the controller
            ComentWindowHelpController mainStageController
                    = ((ComentWindowHelpController) loader.getController());
            //set the stage
            mainStageController.setStage(mainStage);
            //start the stage
            mainStageController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(CommentWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void find(ActionEvent event) {
        String optionSelected;
        ObservableList<Coment> coments = null;
        optionSelected = filterMenu.getSelectionModel().getSelectedItem();
        try {
            //check that the field is informed as long as it is not selected search all in the combo box
            if (findCommentTxTF.getText().isEmpty()
                    && filterMenu.getSelectionModel()
                            .getSelectedItem() != "Find All") {
                throw new Exception("The field cannot be empty");
            } else {
                //depending on the value that is selected in the combo box we 
                //will use different search engines.
                switch (optionSelected) {
                    case "Find All":
                        LOGGER.info("Searching ALL COMMENTS");
                        coments = FXCollections.observableArrayList(commentRest.
                                findAllComents_XML(new GenericType<List<Coment>>() {
                                }));
                        if (coments.isEmpty()) {
                            throw new IOException("No comment found");
                        }
                        break;
                    case "Find By Subject":
                        LOGGER.info("Searching BY SUBJECT");

                        coments = FXCollections
                                .observableArrayList(commentFactory.getFactory()
                                        .find_XML(new GenericType<List<Coment>>() {
                                        }, findCommentTxTF.getText()));
                        if (coments.isEmpty()) {
                            throw new IOException("No comment found with that subject");
                        }
                        break;

                }
                commentTableView.setItems(coments);
                commentTableView.refresh();
                findCommentTxTF.clear();
            }
        } catch (Exception ex) {
            Logger.getLogger(CommentWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();

        }

    }

    private void openModalCommnet(ActionEvent e) {
        Coment comment = this.commentTableView.getSelectionModel().getSelectedItem();
        try {
            Stage modalStage = new Stage();

            URL viewLink = getClass().getResource("/ofc2_cliente/ui/commentModal.fxml");
            FXMLLoader loader = new FXMLLoader(viewLink);
            Parent rootModal = (Parent) loader.load();
            CommentModalController modalStageController
                    = ((CommentModalController) loader.getController());
            LOGGER.info("sending the comment");
            modalStageController.getComment(comment);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStageController.setStage(modalStage);
            modalStageController.initStage(rootModal);
            modalStage.show();

            LOGGER.info("Method open modal finished");

        } catch (IOException ex) {
            Logger.getLogger(CommentWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    private void cargarTodo() {
        try {
            List<Coment> listComent;
            listComent = commentRest.
                    findAllComents_XML(new GenericType<List<Coment>>() {
                    });
            comments.clear();
            comments = FXCollections.observableList(listComent);
            commentTableView.setItems(comments);
            commentTableView.refresh();
        } catch (BusinessLogicException ex) {
            Logger.getLogger(CommentWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
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
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(CommentWindowController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void newCommentCommit(Coment coment) throws BusinessLogicException {
        LOGGER.info("CREATING A NEW COMMENT");
        try {
            commentRest.createComent_XML(coment);
            commentTableView.refresh();
            LOGGER.info("--------------------COMMENT CREATED----------------");
            LOGGER.info(coment.toString());
            LOGGER.info("----------------------------------------------------");
        } catch (BusinessLogicException ex) {
            Logger.getLogger(CommentWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    private void deleteComment() throws BusinessLogicException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you want to delete this comment?");
        Optional<ButtonType> result = alert.showAndWait();
        LOGGER.info("DELETING A COMMENT");
        if (result.get() == ButtonType.OK) {
            System.out.println("Deleting: " + this.comments.toString());
            String clientID = String.valueOf(commentTableView.getSelectionModel().getSelectedItem().getComentid().getClient_id());
            String eventID = String.valueOf(commentTableView.getSelectionModel().getSelectedItem().getComentid().getEvent_id());
            commentRest.deleteComent(clientID, eventID);
            LOGGER.info("--------------------COMMENT DELETED----------------");
            LOGGER.info(commentTableView
                    .getSelectionModel().getSelectedItem().toString());
            LOGGER.info("----------------------------------------------------");
            commentTableView.getItems().remove(commentTableView
                    .getSelectionModel().getSelectedItem());
            commentTableView.getSelectionModel().clearSelection();
            showCommentBTN.setDisable(true);
        }

    }
    private final ListChangeListener<Coment> cursorTableComment = new ListChangeListener<Coment>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Coment> c) {
            setCommentSelected();
            showCommentBTN.setDisable(false);
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
        final Coment comment = getTableComentSelected();
        posicionComent = comments.indexOf(comment);
    }

    public void getComment(Coment coment) {
        this.coment = coment;
    }

    // sub class Editar comment
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
// sub class datepicker comment

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
                    Date n = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date d = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    if (datePicker != null && n == d) {
                        datePicker.setValue(getDate());
                    }
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "la fecha tiene que ser la actual arriba", ButtonType.OK);
                    alert.showAndWait();
                    setText(null);
                    setGraphic(datePicker);
                } else {

                    setText(getDate().format(DateTimeFormatter
                            .ofLocalizedDate(FormatStyle.MEDIUM)));
                    setGraphic(null);
                }
            }
        }

        private void createDatePicker() {
            datePicker = new DatePicker(getDate());
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.setOnAction((e) -> {
                System.out.println("Committed: " + datePicker.getValue().toString());
                Date d = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date n = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (d.equals(n)) {
                    commitEdit(Date.from(datePicker.getValue()
                            .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "la fecha tiene que ser la actual", ButtonType.OK);
                    alert.showAndWait();
                }

            });
        }

        private LocalDate getDate() {
            return getItem() == null ? LocalDate.now() : getItem().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    public Integer btnStatus(Integer count) {
        return this.commentCount = count;
    }

    private void closeWindow(ActionEvent event) {
        try {
            //Gonna initialition a new Stage
            Stage mainStage = new Stage();
            // we gonna create a URL for get the fxml view
            URL viewLink = getClass().getResource("/ofc2_cliente/ui/EventWindow.fxml");

            // initialition loader
            FXMLLoader loader = new FXMLLoader(viewLink);
            //make the root with the loader
            Parent root = (Parent) loader.load();
            stage.getIcons().add(new Image(this.getClass().getResource("/ofc2_cliente/ui/resources/favicon.ico").toString()));
            //Get the controller
            EventWindowController eventWindowController
                    = ((EventWindowController) loader.getController());
            eventWindowController.getUser(this.user);
            //set the stage
            eventWindowController.setStage(mainStage);
            //start the stage
            eventWindowController.initStage(root);
            //close the actually View
            this.stage.close();
            event.consume();
        } catch (IOException ex) {
            Logger.getLogger(LogedWindowController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
