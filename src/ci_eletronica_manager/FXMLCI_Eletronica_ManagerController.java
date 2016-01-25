/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci_eletronica_manager;

import Entities.TbGestaoUsuarios;
import Entities.TbUsuario;
import Entities.TbUsuarioPerfilUo;
import Queries.GestaoQueries;
import static java.lang.System.exit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author victorcmaf
 */
public class FXMLCI_Eletronica_ManagerController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TableView<TbGestaoUsuarios> tbViewUsuarios;
    @FXML
    private TableColumn tbColIdUsuario;
    @FXML
    private TableColumn tbColNomeUsuario;
    @FXML
    private TableColumn tbColLogin;
    @FXML
    private TableColumn tbColAtivo;
    @FXML
    private TableView tbViewUosPerfil;
    @FXML
    private TableColumn tbColIdUoPerfil;
    @FXML
    private TableColumn tbColUoNome;
    @FXML
    private TableColumn tbColPerfil;
    @FXML
    private Button btnAddUsuario;
    @FXML
    private Button btnEditUsuario;
    @FXML
    private Button btnSair;
    @FXML
    private Button btnProcurarUsuario;
    @FXML
    private TextField txtNomeUsuario;
    
    @FXML
    private void handleBtnSair(ActionEvent event) {
        exit(0);
    }
    private void IniciarTabGestaoUsuarios(){
        int nIdUsuario = 0;
        String strNomeUsuario = "";
        String strLogin = "";
        String strSenha = "";
        boolean bAtivo = true;
        
        List<TbUsuario> listaUsuarios = new ArrayList<TbUsuario>();
        ObservableList<TbGestaoUsuarios> obslistaTbGestaoUsuarios = FXCollections.observableArrayList();
        
             
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        listaUsuarios = consulta.listaUsuarios();
        
        for(TbUsuario l : listaUsuarios){
            nIdUsuario = l.getIdUsuario();
            strNomeUsuario = l.getUsuNomeCompleto();
            strLogin = l.getUsuLogin();
            strSenha = l.getUsuSenha();
            bAtivo = l.getUsuAtivo();
            
            obslistaTbGestaoUsuarios.add(new TbGestaoUsuarios(nIdUsuario, strNomeUsuario, strLogin, strSenha, bAtivo));            
        }
        tbColIdUsuario.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,Integer>("intp_idUsuario"));
        tbColNomeUsuario.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UsuarioNomeCompleto"));
        tbColLogin.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UsuarioLogin"));
        tbColAtivo.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,Boolean>("boolp_UsuarioAtivo"));
        tbViewUsuarios.setItems(obslistaTbGestaoUsuarios);
        
        tbViewUsuarios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                    try{
                        //TableView<TbCIPorAprovar> TbViewGeral = new TableView<>();
                        if(tbViewUsuarios.getSelectionModel().getSelectedItem() != null){
                            TbGestaoUsuarios dataUsuarios = tbViewUsuarios.getSelectionModel().getSelectedItem();
                            
                            int nlIdUsuario = 0;
                            nlIdUsuario = dataUsuarios.getIntp_idUsuario();
                            IniciarTabGestaoUsuariosUoPerfil(nlIdUsuario);
                            
                        }
                        }catch (Exception e) {
                            e.printStackTrace();
                            //labelMessage.setText("Error on get row Data");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erro");
                            alert.setHeaderText("Erro na carga da TableView");
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                    }
                }
            });         
        
        
    }
    private void IniciarTabGestaoUsuariosUoPerfil(int nIdUsuario){
        int nlTblViewGeralSize = 0;
        //Devemos fazer refresh da tableView
        nlTblViewGeralSize = tbViewUosPerfil.getItems().size();
        if (nlTblViewGeralSize > 0){
            tbViewUosPerfil.getItems().clear();
        }
        
        //Preenchemos com os valores de acordo ao Id do usuário
        List<TbUsuarioPerfilUo> listaUsuarioPerfilUo = new ArrayList<TbUsuarioPerfilUo>();
        ObservableList<TbGestaoUsuarios> obslistaTbGestaoUsuarioPerfilUo = FXCollections.observableArrayList();
        
        int nIdUsuarioPerfilUo = 0;
        String strUoNome = "";
        String strPerfil = "";
        
             
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        listaUsuarioPerfilUo = consulta.getlistaUsuarioPerfilUo(nIdUsuario);
        
        for(TbUsuarioPerfilUo l : listaUsuarioPerfilUo){
            nIdUsuarioPerfilUo = l.getIdUsuarioPerfilUo();
            strUoNome = l.getIdUnidadeOrganizacional().getUnorNome();
            strPerfil = l.getIdUsuarioPerfil().getPeusDescricao();
            
            obslistaTbGestaoUsuarioPerfilUo.add(new TbGestaoUsuarios(nIdUsuarioPerfilUo, strUoNome, strPerfil));            
        }
        tbColIdUoPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,Integer>("intp_idUsuarioPerfilUo"));
        tbColUoNome.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UoNome"));
        tbColPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_PerfilNome"));
        
        tbViewUosPerfil.setItems(obslistaTbGestaoUsuarioPerfilUo);        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        IniciarTabGestaoUsuarios();        
        
    }    
    
}
