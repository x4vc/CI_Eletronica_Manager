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
import java.io.IOException;
import static java.lang.System.exit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private TableColumn tbColUoDescricao;
    @FXML
    private TableColumn tbColPerfil;
    @FXML
    private Button btnAddUsuario;
    @FXML
    private Button btnEditUsuario;
    @FXML
    private Button btnSair;    
    @FXML
    private TextField txtNomeUsuario;
    
    public TbGestaoUsuarios datagUsuario;
    //public TbGestaoUsuarios datagUsuarioUoPerfil;
    
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
        
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<TbGestaoUsuarios> filteredData = new FilteredList<>(obslistaTbGestaoUsuarios, p -> true);
        
        // 2. Set the filter Predicate whenever the filter changes.
        txtNomeUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(TbGestaoUsuarios -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (TbGestaoUsuarios.getStrp_UsuarioNomeCompleto().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (TbGestaoUsuarios.getStrp_UsuarioLogin().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<TbGestaoUsuarios> sortedData = new SortedList<>(filteredData);
        
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tbViewUsuarios.comparatorProperty());
        
        // 5. Add sorted (and filtered) data to the table.
        tbViewUsuarios.setItems(sortedData);

        
        //tbViewUsuarios.setItems(obslistaTbGestaoUsuarios);
        
        //--------------------------------------------------------
        
        
        tbViewUsuarios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                    try{
                        //TableView<TbCIPorAprovar> TbViewGeral = new TableView<>();
                        if(tbViewUsuarios.getSelectionModel().getSelectedItem() != null){
                            TbGestaoUsuarios dataUsuarios = tbViewUsuarios.getSelectionModel().getSelectedItem();                           
                            datagUsuario = tbViewUsuarios.getSelectionModel().getSelectedItem();
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
        String strUODescricao = "";
        String strPerfil = "";
        boolean bAtivoUsuarioPerfilUo = true;
        
             
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        listaUsuarioPerfilUo = consulta.getlistaUsuarioPerfilUo(nIdUsuario);
        
        for(TbUsuarioPerfilUo l : listaUsuarioPerfilUo){
            nIdUsuarioPerfilUo = l.getIdUsuarioPerfilUo();
            strUoNome = l.getIdUnidadeOrganizacional().getUnorNome();
            strUODescricao = l.getIdUnidadeOrganizacional().getUnorDescricao();
            strPerfil = l.getIdUsuarioPerfil().getPeusDescricao();
            bAtivoUsuarioPerfilUo = l.getUspuAtivo();
            
            obslistaTbGestaoUsuarioPerfilUo.add(new TbGestaoUsuarios(bAtivoUsuarioPerfilUo, nIdUsuarioPerfilUo, strUoNome, strUODescricao, strPerfil));            
        }
        tbColIdUoPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,Integer>("intp_idUsuarioPerfilUo"));
        tbColUoNome.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UoNome"));
        tbColUoDescricao.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UoDescricao"));
        tbColPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_PerfilNome"));
        
        tbViewUosPerfil.setItems(obslistaTbGestaoUsuarioPerfilUo);        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtNomeUsuario.setPromptText("Preencha o nome do usuário ou login para filtrar");
        IniciarTabGestaoUsuarios();  
        
        
    } 
    @FXML
    public void btnEditarUsuario(ActionEvent event) throws IOException{
        ShowEditarUsuario(this, this.datagUsuario);
        
    }
    
    public void ShowEditarUsuario(final FXMLCI_Eletronica_ManagerController mainController , TbGestaoUsuarios dataUsuario){
        try{
                Scene scene;
                scene = new Scene(new AnchorPane());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEdit/FXMLUsuario.fxml"));
                scene.setRoot((Parent) loader.load());
                
                AddEdit.FXMLUsuarioController usuarioController = loader.<AddEdit.FXMLUsuarioController>getController(); 
                usuarioController.setVariaveisAmbienteFXMLUsuario(mainController, dataUsuario);
                
                Stage stage = new Stage();
                stage.setTitle("Editar Usuário");
                //set icon
                stage.getIcons().add(new Image("/Resources/administrator1-add-16x16.gif"));

                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);     //Window Parent fica inativo
                stage.showAndWait();
//                                
            }catch (IOException ex) {
                Logger.getLogger(FXMLCI_Eletronica_ManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
