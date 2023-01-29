/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aritz
 */
public class RoutineControllerTest {
    
    public RoutineControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of initialize method, of class RoutineController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        RoutineController instance = new RoutineController();
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStage method, of class RoutineController.
     */
    @Test
    public void testSetStage() {
        System.out.println("setStage");
        Stage stage = null;
        RoutineController instance = new RoutineController();
        instance.setStage(stage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initStage method, of class RoutineController.
     */
    @Test
    public void testInitStage() {
        System.out.println("initStage");
        Parent root = null;
        RoutineController instance = new RoutineController();
        instance.initStage(root);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableFindBtnC method, of class RoutineController.
     */
    @Test
    public void testEnableFindBtnC() {
        System.out.println("enableFindBtnC");
        ActionEvent event = null;
        RoutineController instance = new RoutineController();
        instance.enableFindBtnC(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableDisableCreateUpdate method, of class RoutineController.
     */
    @Test
    public void testEnableDisableCreateUpdate() {
        System.out.println("enableDisableCreateUpdate");
        RoutineController instance = new RoutineController();
        instance.enableDisableCreateUpdate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cerrarVentana method, of class RoutineController.
     */
    @Test
    public void testCerrarVentana() {
        System.out.println("cerrarVentana");
        WindowEvent event = null;
        RoutineController instance = new RoutineController();
        instance.cerrarVentana(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
