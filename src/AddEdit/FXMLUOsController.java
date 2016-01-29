/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddEdit;

import Entities.TbGestaoUO;
import Entities.TbUnidadeOrganizacional;
import Entities.TbUnidadeOrganizacionalGestor;
import Queries.GestaoQueries;
import ci_eletronica_manager.FXMLCI_Eletronica_ManagerController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author victorcmaf
 */
public class FXMLUOsController implements Initializable {
    @FXML
    private TextField txtIdUO ;
    @FXML
    private TextField txtUoNome;
    @FXML
    private TextField txtUoDescrição;
    @FXML
    private ComboBox cmbAtivoUo;
    @FXML
    private TableView tbViewUoGestora;
    @FXML
    private TableColumn tbColIdUOGestora;
    @FXML
    private TableColumn tbColNomeUOGestora;
    @FXML
    private TableColumn tbColDescricaoUOGestora;
    @FXML
    private TableColumn tbColAtivoUOGestor;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnCancelar.setCancelButton(true);
    }
    
    public void setVariaveisAmbienteFXMLUO(final FXMLCI_Eletronica_ManagerController mainController, TbGestaoUO dataUsuario){
        txtIdUO.setDisable(true);
        txtIdUO.setText(String.valueOf(dataUsuario.getIntp_idUo()));
        txtUoNome.setText(dataUsuario.getStrp_UoNome());
        txtUoDescrição.setText(dataUsuario.getStrp_UoDescricao());        
        cmbAtivoUo.getItems().addAll("Ativado","Desativado");
        if (true == dataUsuario.getBoolp_UoAtivo()){
            cmbAtivoUo.setValue("Ativado");
        }else{
            cmbAtivoUo.setValue("Desativado");
        }
        IniciarTabGestaoUoGestor(dataUsuario.getIntp_idUo());
    }
    
    private void IniciarTabGestaoUoGestor(int nIdUO){
        int nlTblViewGeralSize = 0;
        //Devemos fazer refresh da tableView
        try {
        nlTblViewGeralSize = tbViewUoGestora.getItems().size();
        
        if (nlTblViewGeralSize > 0){
            tbViewUoGestora.getItems().clear();
        }
        } catch (Exception e){
            System.out.println("Erro: " + e);
        }
        
        //Preenchemos com os valores de acordo ao Id do usuário
        List<TbUnidadeOrganizacionalGestor> listaUoGestor = new ArrayList<TbUnidadeOrganizacionalGestor>();
        ObservableList<TbGestaoUO> obslistaTbGestaoUoGestor = FXCollections.observableArrayList();
        
        TbUnidadeOrganizacional nIdUoGestor = new TbUnidadeOrganizacional(nIdUO);
        int nIdUnidadeOrganizacionalGestor = 0;
        String strUoNomeGestor = "";
        String strUODescricaoGestor = "";        
        boolean bAtivoUoGestor = true;
        
             
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        listaUoGestor = consulta.getlistaUoGestor(nIdUoGestor);
        
        for(TbUnidadeOrganizacionalGestor l : listaUoGestor){
            nIdUnidadeOrganizacionalGestor = l.getIdUoge();
            strUoNomeGestor = l.getIdUoGestor().getUnorNome();
            strUODescricaoGestor = l.getIdUoGestor().getUnorDescricao();
            bAtivoUoGestor = l.getUogeAtivo();
            
            obslistaTbGestaoUoGestor.add(new TbGestaoUO(bAtivoUoGestor, nIdUnidadeOrganizacionalGestor, strUoNomeGestor, strUODescricaoGestor ));            
        }
        tbColIdUOGestora.setCellValueFactory(new PropertyValueFactory<TbGestaoUO,Integer>("intp_idUoGe"));
        tbColNomeUOGestora.setCellValueFactory(new PropertyValueFactory<TbGestaoUO,String>("strp_UoNomeGestor"));
        tbColDescricaoUOGestora.setCellValueFactory(new PropertyValueFactory<TbGestaoUO,String>("strp_UoDescricaoGestor"));
        tbColAtivoUOGestor.setCellValueFactory(new PropertyValueFactory<TbGestaoUO,Boolean>("boolp_UoGestorAtivo"));
        //tbColPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoU),String>("strp_PerfilNome"));
        
        tbViewUoGestora.setItems(obslistaTbGestaoUoGestor);   
        
    }
    
    @FXML 
    private void btnClickCancelar(ActionEvent event) throws IOException{
        // get a handle to the stage
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // do what you have to do
        stage.close();
        
                
    }
    
}
