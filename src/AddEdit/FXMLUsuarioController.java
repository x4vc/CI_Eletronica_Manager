/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddEdit;

import Entities.TbGestaoUsuarios;
import Entities.TbUnidadeOrganizacional;
import Entities.TbUsuario;
import Entities.TbUsuarioPerfil;
import Entities.TbUsuarioPerfilUo;
import Queries.GestaoQueries;
import Utilities.Seguranca;
import ci_eletronica_manager.FXMLCI_Eletronica_ManagerController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author victorcmaf
 */
public class FXMLUsuarioController implements Initializable {
    @FXML
    private Button btnResetearSenha;
    @FXML
    private TextField txtIdUsuario;
    @FXML
    private TextField txtNomeCompleto;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtSenha;
    @FXML
    private ComboBox cmbAtivoUsuario;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAddUo;
    @FXML
    private Button btnDeleteUo;
    @FXML
    private Button btnEditarUo;
    @FXML
    private TableView<TbGestaoUsuarios> tbViewUosPerfil;
    @FXML
    private TableColumn<TbGestaoUsuarios, Integer> tbColIdUoPerfil;
    @FXML
    private TableColumn<TbGestaoUsuarios, String> tbColUoNome;
    @FXML
    private TableColumn<TbGestaoUsuarios, String> tbColUoDescricao;
    @FXML
    private TableColumn<TbGestaoUsuarios, String> tbColPerfil;
    @FXML
    private TableColumn<TbGestaoUsuarios, Boolean> tbColAtivoUsuarioPerfilUo;
    
    //Valores nTipoCrud:
    //1 ==> Salvar novo registro
    //2 ==> Editar registro*/
    private int nTipoCrud = 0; 
    
    //-----------------------------
    TbUsuarioPerfilUo datagUsuario;
    
    private ObservableList<TbGestaoUsuarios> obslistaTbGestaoUsuarioPerfilUo = FXCollections.observableArrayList();
    
    private int nContador = 0;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnCancelar.setCancelButton(true);    
        
        txtNomeCompleto.textProperty().addListener((ov, oldValue, newValue) -> {
            txtNomeCompleto.setText(newValue.toUpperCase());
        });
        
        txtLogin.textProperty().addListener((ov, oldValue, newValue) -> {
            txtLogin.setText(newValue.toLowerCase());
        });
    }  
    
    @FXML 
    private void btnClickCancelar(ActionEvent event) throws IOException{
        // get a handle to the stage
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // do what you have to do
        stage.close();
        
                
    }
    
    @FXML
    private void btnResetearSenhaUsuario(ActionEvent event) throws IOException{        
        //setBotoesMainWindow(nTipoPerfil);
        trocarSenha(Integer.parseInt(txtIdUsuario.getText()));               
    }
    
    public void trocarSenha(int nIdUserUO) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Resetear senha");
        dialog.setHeaderText(null);
       

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("/Resources/User_password.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField username = new PasswordField();
        username.setPromptText("Nova senha");
        PasswordField password = new PasswordField();
        password.setPromptText("Confirmar nova senha");

        grid.add(new Label("Nova senha:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Confirmar nova senha:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            String strSenha1 = "";
            String strSenha2 = "";
            boolean bUpdate = false;
            if (dialogButton == loginButtonType) {
                //System.out.println("OK button pressed");
                strSenha1 = username.getText();
                strSenha2 = password.getText();
                if(username.getText().equals(password.getText())){
                    // Senhas batem. Update senha no banco de dados
                    try{
                    GestaoQueries consulta  = new GestaoQueries();
                    bUpdate = consulta.ResetearSenha(nIdUserUO, username.getText());
                    if (bUpdate){
//                        System.out.println("IdUsuario = " + nIdUserUO);
//                        System.out.println("Update deve acontecer");
                        txtSenha.setText(Seguranca.stringToMD5(username.getText()));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Informação");
                        alert.setHeaderText(null);
                        alert.setContentText("A senha foi reseteada com sucesso.");
                        alert.showAndWait();
                        return new Pair<>(username.getText(), password.getText());
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Resetear senha");
                        alert.setHeaderText("Senha não foi reseteada");
                        alert.setContentText("Favor contatar Administrador do sistema");
                        alert.showAndWait();
                    }
                    }catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Resetear senha");
                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();                        
                    }                        
                }
                else{
                    // Erro. A nova senha não bate
                    //System.out.println("As senhas não batem");  
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Os valores inseridos não batem");
                    alert.setContentText("Favor verificar os valores inseridos.\nInfelizmente sua senha não foi reseteada");
                    alert.showAndWait();
                }                
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
//            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });
    }
    
    public void setVariaveisAmbienteFXMLUsuario(final FXMLCI_Eletronica_ManagerController mainController, TbGestaoUsuarios dataUsuario, int nTipoCrud){
        txtIdUsuario.setDisable(true);
        txtIdUsuario.setText(String.valueOf(dataUsuario.getIntp_idUsuario()));
        txtNomeCompleto.setText(dataUsuario.getStrp_UsuarioNomeCompleto());
        txtLogin.setText(dataUsuario.getStrp_UsuarioLogin());
//        txtSenha.setDisable(true);
//        txtSenha.setText(dataUsuario.getStrp_UsuarioSenha());
        cmbAtivoUsuario.getItems().addAll("Ativado","Desativado");        
        if (true == dataUsuario.getBoolp_UsuarioAtivo()){
            cmbAtivoUsuario.setValue("Ativado");
        }else{
            cmbAtivoUsuario.setValue("Desativado");
        }
        
        //Valores nTipoCrud:
        //1 ==> Salvar novo registro
        //2 ==> Editar registro*/
        this.nTipoCrud = nTipoCrud;
        
        switch (nTipoCrud){
            case 1: 
                txtSenha.setDisable(false);
                txtSenha.setText(dataUsuario.getStrp_UsuarioSenha());
                btnResetearSenha.setVisible(false);
                btnEditarUo.setVisible(false);
                btnAddUo.setVisible(true);
                btnDeleteUo.setVisible(true);
                break;
            case 2:
                txtSenha.setDisable(true);
                txtSenha.setText(dataUsuario.getStrp_UsuarioSenha());
                btnResetearSenha.setVisible(true);
                btnEditarUo.setVisible(false);
                btnAddUo.setVisible(true);
                btnDeleteUo.setVisible(true);
                break;
            default:
                break;
        }
        //-------------------------------------------------------------
        
        
        IniciarTabGestaoUsuariosUoPerfil(dataUsuario.getIntp_idUsuario());
                

        
    }
    
    private void IniciarTabGestaoUsuariosUoPerfil(int nIdUsuario){
        int nlTblViewGeralSize = 0;
        //Devemos fazer refresh da tableView
//        nlTblViewGeralSize = tbViewUosPerfil.getItems().size();
//        if (nlTblViewGeralSize > 0){
//            tbViewUosPerfil.getItems().clear();
//        }
        
        //Preenchemos com os valores de acordo ao Id do usuário
        List<TbUsuarioPerfilUo> listaUsuarioPerfilUo = new ArrayList<TbUsuarioPerfilUo>();
        //ObservableList<TbGestaoUsuarios> obslistaTbGestaoUsuarioPerfilUo = FXCollections.observableArrayList();
        
        int nIdUsuarioPerfilUo = 0;
        
        TbUsuarioPerfil nIdUsuarioPerfil;
        TbUnidadeOrganizacional nIdUO;
        
        String strUoNome = "";
        String strUODescricao = "";
        String strPerfil = "";
        boolean bAtivoUsuarioPerfilUo = true;
        
        ObservableList<Boolean> namesChoiceList;
        namesChoiceList = FXCollections.observableArrayList(true, false);
        
             
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        listaUsuarioPerfilUo = consulta.getlistaUsuarioPerfilUo(nIdUsuario);
        //try {
            for(TbUsuarioPerfilUo l : listaUsuarioPerfilUo){
                nIdUsuarioPerfilUo = l.getIdUsuarioPerfilUo();
                strUoNome = l.getIdUnidadeOrganizacional().getUnorNome();
                strUODescricao = l.getIdUnidadeOrganizacional().getUnorDescricao();
                strPerfil = l.getIdUsuarioPerfil().getPeusDescricao();
                bAtivoUsuarioPerfilUo = l.getUspuAtivo();
                nIdUsuarioPerfil = l.getIdUsuarioPerfil();
                nIdUO = l.getIdUnidadeOrganizacional();

                obslistaTbGestaoUsuarioPerfilUo.add(new TbGestaoUsuarios(bAtivoUsuarioPerfilUo, nIdUsuarioPerfilUo, strUoNome, strUODescricao, strPerfil,nIdUsuarioPerfil.getIdUsuarioPerfil(), nIdUO.getIdUnidadeOrganizacional()));            
            }

                //tbColAtivoUsuarioPerfilUo.setCellFactory(new PropertyValueFactory<TbGestaoUsuarios,Boolean>("boolp_UsuarioPerfilUoAtivo"));

                //tbColIdUoPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,Integer>("intp_idUsuarioPerfilUo"));
                tbColIdUoPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,Integer>("intp_idUnidadeOrganizacional"));
                tbColUoNome.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UoNome"));
                tbColUoDescricao.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UoDescricao"));
                tbColPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_PerfilNome"));            
                tbColAtivoUsuarioPerfilUo.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,Boolean>("boolp_UsuarioPerfilUoAtivo"));
                tbColAtivoUsuarioPerfilUo.setCellFactory(ComboBoxTableCell.<TbGestaoUsuarios, Boolean>forTableColumn(namesChoiceList));
                tbViewUosPerfil.setItems(obslistaTbGestaoUsuarioPerfilUo);        
//        }catch(Exception e){
//            System.out.println("Erro: " + e);
//                    
//        }
        
        tbViewUosPerfil.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                    try{
                        //TableView<TbCIPorAprovar> TbViewGeral = new TableView<>();
                        if(tbViewUosPerfil.getSelectionModel().getSelectedItem() != null){
                            TbGestaoUsuarios dataUsuarios = tbViewUosPerfil.getSelectionModel().getSelectedItem();                           
                            //datagUsuario = tbViewUosPerfil.getSelectionModel().getSelectedItem();
                            boolean bUsuarioPerfilUo = false;
                            int nlIdUsuarioPerfil = 0;
                            int nlIdUsuario = 0;
                            nlIdUsuarioPerfil = dataUsuarios.getIntp_idUsuarioPerfilUo();
                            bUsuarioPerfilUo = dataUsuarios.getBoolp_UsuarioPerfilUoAtivo();
                            nlIdUsuario = Integer.parseInt(txtIdUsuario.getText());
                            //IniciarTabGestaoUsuariosUoPerfil(nlIdUsuario);
                            
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
    
    @FXML
    public void btnTeste(ActionEvent event) throws IOException{
        IniciarTabGestaoUsuariosUoPerfil(10);
        
    }
    
    @FXML 
    private void btnAdicionarUOPerfil(ActionEvent event) throws IOException{
        try{
                Scene scene;
                scene = new Scene(new AnchorPane());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEdit/FXMLAddEditUOPerfilUsuario.fxml"));
                scene.setRoot((Parent) loader.load());
                
                AddEdit.FXMLAddEditUOPerfilUsuarioController usuarioController = loader.<AddEdit.FXMLAddEditUOPerfilUsuarioController>getController(); 
                //usuarioController.setVariaveisAmbienteFXMLUsuario(mainController, dataUsuario, nTipoCrud);
                
                Stage stage = new Stage();
                stage.setTitle("Adicionar UO e Perfil do Usuário");
                //set icon
                stage.getIcons().add(new Image("/Resources/administrator1-add-16x16.gif"));

                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);     //Window Parent fica inativo
                stage.showAndWait();
                
                ObservableList<TbGestaoUsuarios> obslistaTbGestaoUsuarioPerfilUo = FXCollections.observableArrayList();
                obslistaTbGestaoUsuarioPerfilUo = usuarioController.getDataTableView();
                System.out.println();
                if (null != obslistaTbGestaoUsuarioPerfilUo){
                    System.out.println();
                    PreencherTableView(obslistaTbGestaoUsuarioPerfilUo);
                }
//                                
            }catch (IOException ex) {
                Logger.getLogger(FXMLCI_Eletronica_ManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }   
    }
    
    private void PreencherTableView(ObservableList<TbGestaoUsuarios> obslistaTbGestaoUsuarioPerfilUo){
        TbGestaoUsuarios  entitiyTbGestaoUsuarios;
        int nSize = 0;
        nSize = obslistaTbGestaoUsuarioPerfilUo.size();
        
        for (int i = 0; i < nSize; i++){
            this.nContador++;
            entitiyTbGestaoUsuarios = obslistaTbGestaoUsuarioPerfilUo.get(i);
            this.obslistaTbGestaoUsuarioPerfilUo.add(new TbGestaoUsuarios(true, /*this.nContador*/ entitiyTbGestaoUsuarios.getIntp_idUnidadeOrganizacional(), entitiyTbGestaoUsuarios.getStrp_UoNome(), entitiyTbGestaoUsuarios.getStrp_UoDescricao(), entitiyTbGestaoUsuarios.getStrp_PerfilNome(), entitiyTbGestaoUsuarios.getIntp_idUsuarioPerfil(), entitiyTbGestaoUsuarios.getIntp_idUnidadeOrganizacional()));
            
        }
        tbViewUosPerfil.setItems(this.obslistaTbGestaoUsuarioPerfilUo);   
        
        
    }
    
     @FXML 
    private void btnClickExcluirUO(ActionEvent event) throws IOException{
        if (null == tbViewUosPerfil.getSelectionModel().getSelectedItem()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Registro não foi selecionado.");
            alert.setContentText("Favor selecionar um registro da tabela");
            alert.showAndWait();
        }else{
            TbGestaoUsuarios data = tbViewUosPerfil.getSelectionModel().getSelectedItem();  
            obslistaTbGestaoUsuarioPerfilUo.remove(data);
        }
    }
    
    @FXML 
    private void btnClickSalvar(ActionEvent event) throws IOException{
        boolean bCampos = false;
        
        //Valores nTipoCrud:
        //1 ==> Salvar novo registro
        //2 ==> Editar registro*/       
        switch (nTipoCrud){
            case 1: 
                    bCampos = Verificar_campos(nTipoCrud);
                break;
                
            case 2:                
                    bCampos = Verificar_campos(nTipoCrud);                
                break;
                
            default:
                break;
        }
        this.nContador = 0;        
        Alert alert;
        
        Stage stage;
        
        if (bCampos){
            //Valores nTipoCrud:
            //1 ==> Salvar novo registro
            //2 ==> Editar registro*/       

            switch (nTipoCrud){
                case 1:         
                    
                    SalvarNovoUsuario();
                    
                    // get a handle to the stage
                    stage = (Stage) btnSalvar.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                    break;
                case 2:
                    
                    SalvarEdicaoUsuario(Integer.parseInt(txtIdUsuario.getText()));
                    
                    // get a handle to the stage
                    stage = (Stage) btnSalvar.getScene().getWindow();
                    // do what you have to do
                    stage.close();                     
                    break;
                default:
                    break;
            }
        } else {
//            alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erro");
//            alert.setHeaderText("Não foi possível salvar o registro.");
//            alert.setContentText("Verificar que todos os campos estejam preenchidos");
//            alert.showAndWait(); 
            
        }
        
    }
    private void SalvarEdicaoUsuario(int nIdUsuario){
        
        Alert alert;
        
        EntityManager em;
        EntityManagerFactory emf;
        
        emf = Persistence.createEntityManagerFactory("CI_Eletronica_ManagerPU");
        em = emf.createEntityManager();        
        em.getTransaction().begin();
        
        //---------------------------------------
        // 1 - Deletamos os registros da tabela TB_USUARIO_PERFIL_UO relacionados com o id do usuario
        
        //---------------------------------------------------------------
        try{
                    
            //em.getTransaction().begin();
            
            Query query = em.createQuery("DELETE FROM TbUsuarioPerfilUo t WHERE t.idUsuario = :idUsuario")
                    .setParameter("idUsuario", nIdUsuario);
            int nDeletionCount = query.executeUpdate();   
            
            if (nDeletionCount > 0){
                System.out.println("Registros foram apagados com Sucesso!!!...");
                em.getTransaction().commit();
            }
//            
//            
//            em.close();
//            emf.close();
            
        }catch (javax.persistence.PersistenceException e) {
            e.printStackTrace();
            em.close();
            emf.close();
        }
        
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        String strUserName = txtNomeCompleto.getText();
        String strLoginName = txtLogin.getText();
        String strPassword = txtSenha.getText();
        
        boolean bStatusUsuario = true;
        String strStatusUsuario = cmbAtivoUsuario.getValue().toString();
        if (0==strStatusUsuario.compareTo("Ativado")){
            bStatusUsuario = true;
        }else{
            bStatusUsuario = false;
        }
        
        try {
            //em = emf.createEntityManager();        
            em.getTransaction().begin();
            
            TbUsuario entityTbUsuario = consulta.getDadosUsuario(nIdUsuario);
            entityTbUsuario.setUsuNomeCompleto(strUserName);
            entityTbUsuario.setUsuLogin(strLoginName);
            //entityTbUsuario.setUsuSenha(Seguranca.stringToMD5(strPassword));
            entityTbUsuario.setUsuSenha(strPassword);
            entityTbUsuario.setUsuAtivo(bStatusUsuario);

            em.merge(entityTbUsuario);
            
            int nSizeListaUO = 0;
            nSizeListaUO = this.obslistaTbGestaoUsuarioPerfilUo.size();
            for (int i = 0; i<nSizeListaUO;i++){
                TbUsuarioPerfilUo newUsuarioPerfilUO = new TbUsuarioPerfilUo();
                
                TbGestaoUsuarios entityTbGestaoUsuarios = obslistaTbGestaoUsuarioPerfilUo.get(i);
                
                TbUsuarioPerfil nIdUsuarioPerfil = new TbUsuarioPerfil(entityTbGestaoUsuarios.getIntp_idUsuarioPerfil());
                TbUnidadeOrganizacional nIdUO = new TbUnidadeOrganizacional(entityTbGestaoUsuarios.getIntp_idUnidadeOrganizacional());

                newUsuarioPerfilUO.setIdUsuario(nIdUsuario);
                newUsuarioPerfilUO.setIdUsuarioPerfil(nIdUsuarioPerfil);
                newUsuarioPerfilUO.setIdUnidadeOrganizacional(nIdUO);
                newUsuarioPerfilUO.setUspuAtivo(true);

                em.persist(newUsuarioPerfilUO);
            }

            
        }catch(javax.persistence.PersistenceException e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atualizar Usuário ");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();  
            em.close();
            emf.close();
        }
        em.getTransaction().commit();            
        em.close();
        emf.close();
        System.out.println();
        
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText("Registro foi editado e salvo.");
        alert.setContentText("Salvar edição do registro");
        alert.showAndWait();       
    }
    private void SalvarNovoUsuario(){
        EntityManager em;
        EntityManagerFactory emf;
        
        emf = Persistence.createEntityManagerFactory("CI_Eletronica_ManagerPU");
        em = emf.createEntityManager();        
        em.getTransaction().begin();
        
        TbUsuario newUsuario = new TbUsuario();
        //---------------------------------------
        Alert alert;
        
        String strUserName = txtNomeCompleto.getText();
        String strLoginName = txtLogin.getText();
        String strPassword = txtSenha.getText();
        
        boolean bStatusUsuario = true;
        String strStatusUsuario = cmbAtivoUsuario.getValue().toString();
        if (0==strStatusUsuario.compareTo("Ativado")){
            bStatusUsuario = true;
        }else{
            bStatusUsuario = false;
        }
        
//        System.out.println("Valor comboBox:" + strStatusUsuario);
//        System.out.println("Valor boolean comboBox:" + bStatusUsuario);
        try {
            newUsuario.setUsuNomeCompleto(strUserName);
            newUsuario.setUsuLogin(strLoginName);
            newUsuario.setUsuSenha(Seguranca.stringToMD5(strPassword));
            newUsuario.setUsuAtivo(bStatusUsuario);

            em.persist(newUsuario);
            em.flush();


            long IdUsuario = newUsuario.getIdUsuario();
            int nIdUsuario = (int)IdUsuario;

            int nSizeListaUO = 0;
            nSizeListaUO = this.obslistaTbGestaoUsuarioPerfilUo.size();
            for (int i = 0; i<nSizeListaUO;i++){
                TbUsuarioPerfilUo newUsuarioPerfilUO = new TbUsuarioPerfilUo();
                
                TbGestaoUsuarios entityTbGestaoUsuarios = obslistaTbGestaoUsuarioPerfilUo.get(i);
                
                TbUsuarioPerfil nIdUsuarioPerfil = new TbUsuarioPerfil(entityTbGestaoUsuarios.getIntp_idUsuarioPerfil());
                TbUnidadeOrganizacional nIdUO = new TbUnidadeOrganizacional(entityTbGestaoUsuarios.getIntp_idUnidadeOrganizacional());

                newUsuarioPerfilUO.setIdUsuario(nIdUsuario);
                newUsuarioPerfilUO.setIdUsuarioPerfil(nIdUsuarioPerfil);
                newUsuarioPerfilUO.setIdUnidadeOrganizacional(nIdUO);
                newUsuarioPerfilUO.setUspuAtivo(true);

                em.persist(newUsuarioPerfilUO);
            }
            
        }catch(javax.persistence.PersistenceException e){
                //e.printStackTrace();
            System.out.println(e);
            em.close();
            emf.close();            
        }
        em.getTransaction().commit();            
        em.close();
        emf.close();
        
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText("Registro foi salvo.");
        alert.setContentText("Novo usuário foi salvo com sucesso");
        alert.showAndWait();
    }
    
    private Boolean Verificar_campos(int nTipoCrud){
        Alert alert;
        boolean bEstado= true;
        
        String strUserName = txtNomeCompleto.getText();
        strUserName = strUserName.trim();
        
        String strLoginName = txtLogin.getText();
        strLoginName = strLoginName.trim();
        
        String strPassword = txtSenha.getText();
        strPassword = strPassword.trim();
        //Verificamos se existem UOs relacionadas com o usuário
        int nSizeListaUO = 0;
        int nSizeListaLogin = 0;
        nSizeListaUO = this.obslistaTbGestaoUsuarioPerfilUo.size();
        //----------------------------------------------------------
        
        GestaoQueries consulta;
        List<TbUsuario> listaUserLogin = new ArrayList<TbUsuario>();
        
        switch (nTipoCrud){
            case 1:
                //Verificamos se já existe um login igual no banco de dados.
                
                consulta  = new GestaoQueries();                 

                listaUserLogin = consulta.listaUserLogin(txtLogin.getText());
                nSizeListaLogin = listaUserLogin.size();
                //-----------------------------------------------------------
                break;
            case 2:
                //Verificamos se já existe um login igual no banco de dados.                
                consulta  = new GestaoQueries();                  
        
                listaUserLogin = consulta.listaUserLoginUpdate(txtLogin.getText(), Integer.parseInt(txtIdUsuario.getText().trim()));
                nSizeListaLogin = listaUserLogin.size();
                //-----------------------------------------------------------
                break;
            default:
                break;
        }
        
        
        if (strUserName.isEmpty()){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Dados inconsistentes");
            alert.setContentText("Usuário deve preencher nome do usuário.");
            alert.showAndWait(); 
            
            bEstado = false;
            return bEstado;            
        }else if (strLoginName.isEmpty()){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Dados inconsistentes");
            alert.setContentText("Usuário deve preencher login do usuário.");
            alert.showAndWait();
            bEstado = false;
            
            return bEstado;                        
        }else if (strPassword.isEmpty()){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Dados inconsistentes");
            alert.setContentText("Usuário deve preencher a senha do usuário.");
            alert.showAndWait();
            
            bEstado = false;
            return bEstado;                        
        }else if ((nSizeListaUO <=0)){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Dados inconsistentes");
            alert.setContentText("Usuário deve estar relacionado com ao menos uma UO.");
            alert.showAndWait();   
            
            bEstado = false;
            return bEstado;            
        } else if ((nSizeListaLogin > 0)){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Dados inconsistentes");
            alert.setContentText("Login já existe no banco de dados.");
            alert.showAndWait();   
            
            bEstado = false;
            return bEstado;
        }
//        alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Alerta");
//        alert.setHeaderText("Dados inconsistentes");
//        alert.setContentText("UO não selecionado ou Login já existe no banco de dados  ");
//        alert.showAndWait();        
            
        return bEstado;
    }
            
    
}
