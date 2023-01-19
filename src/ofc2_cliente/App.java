
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class App extends Application {

    private Logger logger = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) throws Exception {
        LOGGER.info("Starting SingInWindow");
        try {
            //link to get the FXML file
            URL viewLink = getClass().getResource("/model/views/SignInWindow.fxml");
            //initialization the loader witk the FXML file
            FXMLLoader loader = new FXMLLoader(viewLink);
            //initialization the root (Parent) with the FXML Loader.load
            Parent root = (Parent) loader.load();
            //initialization the singInController
            SignInWindowController mainStageController
                    = ((SignInWindowController) loader.getController());
            //set the Stage to the controll
            mainStageController.setStage(primaryStage);
            //Start the Stage
            mainStageController.initStage(root);
            LOGGER.info("Started SingInWindow");

        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
