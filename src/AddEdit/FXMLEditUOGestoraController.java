/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddEdit;

import Entities.TbGestaoUO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author victorcmaf
 */
public class FXMLEditUOGestoraController implements Initializable {
    @FXML
    Button btnSalvar;
    @FXML
    Button btnCancelar;
    @FXML
    TextField txtIdUOGestora;
    @FXML
    TextField txtUONome;
    @FXML
    TextField txtUODescricao;
    @FXML
    ComboBox cbUOStatus;
    
    private boolean bButtonSalvarClicked = false;
    private TbGestaoUO gEntityTbGestaoUO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         btnCancelar.setCancelButton(true);
         txtIdUOGestora.setDisable(true);
         txtUONome.setEditable(false);
         txtUODescricao.setEditable(false);
        
    }
    public void setVariaveisAmbienteFXMLEditUOGestora(final FXMLUOsController mainController, TbGestaoUO entityTbGestaoUo){
        this.gEntityTbGestaoUO = entityTbGestaoUo;
        
        txtIdUOGestora.setText(Integer.toString(entityTbGestaoUo.getIntp_idUoGe()));
        txtUONome.setText(entityTbGestaoUo.getStrp_UoNomeGestor());
        txtUODescricao.setText(entityTbGestaoUo.getStrp_UoDescricaoGestor());
        
        cbUOStatus.getItems().addAll("Ativado","Desativado");        
        if (true == entityTbGestaoUo.getBoolp_UoGestorAtivo()){
            cbUOStatus.setValue("Ativado");
        }else{
            cbUOStatus.setValue("Desativado");
        }
    }

    public boolean isbButtonSalvarClicked() {
        return bButtonSalvarClicked;        
    }

    public void setbButtonSalvarClicked(boolean bButtonSalvarClicked) {
        this.bButtonSalvarClicked = bButtonSalvarClicked;
    }

    public TbGestaoUO getgEntityTbGestaoUO() {
        return gEntityTbGestaoUO;
    }

    public void setgEntityTbGestaoUO(TbGestaoUO gEntityTbGestaoUO) {
        this.gEntityTbGestaoUO = gEntityTbGestaoUO;
    }
    
    @FXML 
    private void btnClickCancelar(ActionEvent event) throws IOException{
        bButtonSalvarClicked = false;
        // get a handle to the stage
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // do what you have to do
        stage.close();        
                
    }
    
    @FXML
    private void btnClickSalvar(ActionEvent event) throws IOException{
        bButtonSalvarClicked = true;
        
        boolean bStatusUO = true;
        
        String strStatusUO = cbUOStatus.getValue().toString();
        if (0==strStatusUO.compareTo("Ativado")){
            bStatusUO = true;
        }else{
            bStatusUO = false;
        }
        
        gEntityTbGestaoUO.setBoolp_UoGestorAtivo(bStatusUO);
        
        // get a handle to the stage
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // do what you have to do
        stage.close();
        
    }
    
    
    
}
