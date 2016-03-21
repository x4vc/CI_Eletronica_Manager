/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci_eletronica_manager;

import Entities.TbGestaoUO;
import Entities.TbGestaoUsuarios;
import Entities.TbUnidadeOrganizacional;
import Entities.TbUnidadeOrganizacionalGestor;
import Entities.TbUsuario;
import Entities.TbUsuarioPerfil;
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
import javafx.application.Platform;
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
    public TbGestaoUO datagUO;
    
    // Variaveis para Aba UOs    
    @FXML
    private TextField txtNomeUO;
    @FXML
    private Button btnAdicionarUO;
    @FXML
    private Button btnEditarUO;
    @FXML
    private TableView<TbGestaoUO> tbViewUOs;
    @FXML
    private TableColumn tbColIdUO;
    @FXML
    private TableColumn tbColNomeUO;
    @FXML
    private TableColumn tbColDescricaoUO;
    @FXML
    private TableColumn tbColUOAtivo;
    
    @FXML
    private TableView tbViewUoGestora;
    @FXML
    private TableColumn tbColIdUOGestora;
    @FXML
    private TableColumn tbColNomeUOGestora;
    @FXML
    private TableColumn tbColDescricaoUOGestora;    
    //--------------FIM Variaveis Aba UOs
    
    //private ObservableList<TbGestaoUsuarios> obslistaTbGestaoUsuarios = FXCollections.observableArrayList();
    
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
        
        TbUsuarioPerfil nIdUsuarioPerfil;
        TbUnidadeOrganizacional nIdUO;
        
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
            nIdUsuarioPerfil = l.getIdUsuarioPerfil();
            nIdUO = l.getIdUnidadeOrganizacional();
            
            obslistaTbGestaoUsuarioPerfilUo.add(new TbGestaoUsuarios(bAtivoUsuarioPerfilUo, nIdUsuarioPerfilUo, strUoNome, strUODescricao, strPerfil, nIdUsuarioPerfil.getIdUsuarioPerfil(), nIdUO.getIdUnidadeOrganizacional()));            
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
        txtNomeUO.setPromptText("Preencha o nome da UO para filtrar");
        IniciarTabGestaoUOs();
        
    }
    @FXML
    public void btnAddNovoUsuario(ActionEvent event) throws IOException {
        TbGestaoUsuarios addNewUsuario = new TbGestaoUsuarios(0,"","","", true);
        ShowEditarUsuario(this, addNewUsuario, "Adicionar novo usuário",1);
        
    }
    @FXML
    public void btnEditarUsuario(ActionEvent event) throws IOException{
        if (null == tbViewUsuarios.getSelectionModel().getSelectedItem()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Usuário não foi selecionado.");
            alert.setContentText("Favor selecionar um usuário da tabela");
            alert.showAndWait();
        }else{
            ShowEditarUsuario(this, this.datagUsuario, "Editar usuário",2);
        }
        
    }
    
    public void ShowEditarUsuario(final FXMLCI_Eletronica_ManagerController mainController , TbGestaoUsuarios dataUsuario, String strTitulo, int nTipoCrud){
        try{
                Scene scene;
                scene = new Scene(new AnchorPane());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEdit/FXMLUsuario.fxml"));
                scene.setRoot((Parent) loader.load());
                
                AddEdit.FXMLUsuarioController usuarioController = loader.<AddEdit.FXMLUsuarioController>getController(); 
                usuarioController.setVariaveisAmbienteFXMLUsuario(mainController, dataUsuario, nTipoCrud);
                
                Stage stage = new Stage();
                stage.setTitle(strTitulo);
                //set icon
                stage.getIcons().add(new Image("/Resources/administrator1-add-16x16.gif"));

                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);     //Window Parent fica inativo
                stage.showAndWait();
                
                //clear_telas();
                IniciarTabGestaoUsuarios();
                
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        tbViewUsuarios.requestFocus();
                        tbViewUsuarios.getSelectionModel().select(0);
                        tbViewUsuarios.getFocusModel().focus(0);
                    }
                });
//                                
            }catch (IOException ex) {
                Logger.getLogger(FXMLCI_Eletronica_ManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void clear_telas(){
        int nTblViewUsuariosSize = 0;
        int nTblViewUoPerfilSize = 0;
        //Devemos fazer refresh da tableView e campo texto
        txtNomeUsuario.setText("");
        nTblViewUsuariosSize = tbViewUsuarios.getItems().size();        
        nTblViewUoPerfilSize = tbViewUosPerfil.getItems().size();
        try {
            if (nTblViewUsuariosSize > 0){
                tbViewUsuarios.getItems().clear();
            }
            if (nTblViewUoPerfilSize > 0){
                tbViewUosPerfil.getItems().clear();
            }
        }catch(Exception ex){
            System.out.println("clear_telas() Exception: " + ex);            
        }
    }
    
    @FXML
    public void btnEditarUO(ActionEvent event) throws IOException{
        if (null == tbViewUOs.getSelectionModel().getSelectedItem()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("UO não foi selecionada.");
            alert.setContentText("Favor selecionar uma UO da tabela");
            alert.showAndWait();
        }else{
            //Valores nTipoCrud:
            //1 ==> Salvar novo registro
            //2 ==> Editar registro*/
            int nTipoCrud = 2; 
            ShowEditarUO(this, this.datagUO, "Editar UO", nTipoCrud);
        }
        
    }
    @FXML
    public void btnAddNovaUO(ActionEvent event) throws IOException{
        //Valores nTipoCrud:
        //1 ==> Salvar novo registro
        //2 ==> Editar registro*/
        int nTipoCrud = 1; 
        
        TbGestaoUO addNovaUO = new TbGestaoUO(0,"", "", true);
        ShowEditarUO(this, addNovaUO, "Adicionar nova UO", nTipoCrud);
        
    }
    
    public void ShowEditarUO(final FXMLCI_Eletronica_ManagerController mainController , TbGestaoUO dataUo, String strTitulo, int nTipoCrud){
        try{
                Scene scene;
                scene = new Scene(new AnchorPane());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEdit/FXMLUOs.fxml"));
                scene.setRoot((Parent) loader.load());
                
                AddEdit.FXMLUOsController usuarioController = loader.<AddEdit.FXMLUOsController>getController(); 
                usuarioController.setVariaveisAmbienteFXMLUO(mainController, dataUo, nTipoCrud);
                
                Stage stage = new Stage();
                stage.setTitle(strTitulo);
                //set icon
                stage.getIcons().add(new Image("/Resources/user_group.png"));

                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);     //Window Parent fica inativo
                stage.showAndWait();
                
                //clear telas
                IniciarTabGestaoUOs();
                
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        tbViewUOs.requestFocus();
                        tbViewUOs.getSelectionModel().select(0);
                        tbViewUOs.getFocusModel().focus(0);
                    }
                });
                
                
//                                
            }catch (IOException ex) {
                Logger.getLogger(FXMLCI_Eletronica_ManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void IniciarTabGestaoUOs(){
        int nIdUO = 0;
        String strNomeUO = "";
        String strDescricaoUO = "";        
        boolean bAtivo = true;
        
        List<TbUnidadeOrganizacional> listaUOs = new ArrayList<TbUnidadeOrganizacional>();
        ObservableList<TbGestaoUO> obslistaTbGestaoUOs = FXCollections.observableArrayList();
        
             
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        listaUOs = consulta.listaUOs();
        
        for(TbUnidadeOrganizacional l : listaUOs){
            nIdUO = l.getIdUnidadeOrganizacional();
            strNomeUO = l.getUnorNome();
            strDescricaoUO = l.getUnorDescricao();
            bAtivo = l.getUnorAtivo();
            
            obslistaTbGestaoUOs.add(new TbGestaoUO(nIdUO, strNomeUO, strDescricaoUO, bAtivo));            
        }
        tbColIdUO.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,Integer>("intp_idUo"));
        tbColNomeUO.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UoNome"));
        tbColDescricaoUO.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UoDescricao"));
        tbColUOAtivo.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,Boolean>("boolp_UoAtivo"));
        
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<TbGestaoUO> filteredData = new FilteredList<>(obslistaTbGestaoUOs, p -> true);
        
        // 2. Set the filter Predicate whenever the filter changes.
        txtNomeUO.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(TbGestaoUO -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (TbGestaoUO.getStrp_UoNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (TbGestaoUO.getStrp_UoDescricao().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<TbGestaoUO> sortedData = new SortedList<>(filteredData);
        
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tbViewUOs.comparatorProperty());
        
        // 5. Add sorted (and filtered) data to the table.
        tbViewUOs.setItems(sortedData);

        
        //tbViewUsuarios.setItems(obslistaTbGestaoUsuarios);
        
        //--------------------------------------------------------
        
        
        tbViewUOs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                    try{
                        //TableView<TbCIPorAprovar> TbViewGeral = new TableView<>();
                        if(tbViewUOs.getSelectionModel().getSelectedItem() != null){
                            TbGestaoUO dataUO = tbViewUOs.getSelectionModel().getSelectedItem();                           
                            datagUO = tbViewUOs.getSelectionModel().getSelectedItem();
                            int nlIdUO = 0;
                            nlIdUO = dataUO.getIntp_idUo();
                            //IniciarTabGestaoUsuariosUoPerfil(nlIdUsuario);
                            IniciarTabGestaoUoGestor(nlIdUO);
                            
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
        //tbColPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoU),String>("strp_PerfilNome"));
        
        tbViewUoGestora.setItems(obslistaTbGestaoUoGestor);        
    }
    
}
