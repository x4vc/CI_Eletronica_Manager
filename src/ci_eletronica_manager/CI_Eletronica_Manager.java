/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci_eletronica_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author victorcmaf
 */
public class CI_Eletronica_Manager extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        String strVersao = "1.0";
        String strRelease = "28032016";
        Parent root = FXMLLoader.load(getClass().getResource("FXMLCI_Eletronica_Manager.fxml"));
        
        
        Scene scene = new Scene(root);
        stage.setTitle("Gestão de usuários e UOs" + " (versão: " + strVersao + " release: " + strRelease + ")");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
