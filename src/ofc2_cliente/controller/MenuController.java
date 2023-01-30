/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author DarkD
 */
public class MenuController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu homeMenu;
    @FXML
    private MenuItem eventMenu;
    @FXML
    private MenuItem routineMenu;
    @FXML
    private MenuItem CommentMenu;
    @FXML
    private MenuItem sponsorMenu;
    @FXML
    private Menu profileMenu;
    @FXML
    private MenuItem showProfile;
    @FXML
    private MenuItem logoutMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void showEventWindow(ActionEvent event) {
    }
    
}
