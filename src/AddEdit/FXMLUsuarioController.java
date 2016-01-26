/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddEdit;

import Entities.TbGestaoUsuarios;
import Entities.TbUsuarioPerfilUo;
import Queries.GestaoQueries;
import ci_eletronica_manager.FXMLCI_Eletronica_ManagerController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

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
    private TableColumn tbColAtivoUsuarioPerfilUo;
    @FXML
    private Button btnTeste;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
    public void setVariaveisAmbienteFXMLUsuario(final FXMLCI_Eletronica_ManagerController mainController, TbGestaoUsuarios dataUsuario){
        txtIdUsuario.setDisable(true);
        txtIdUsuario.setText(String.valueOf(dataUsuario.getIntp_idUsuario()));
        txtNomeCompleto.setText(dataUsuario.getStrp_UsuarioNomeCompleto());
        txtLogin.setText(dataUsuario.getStrp_UsuarioLogin());
        txtSenha.setDisable(true);
        txtSenha.setText(dataUsuario.getStrp_UsuarioSenha());
        cmbAtivoUsuario.getItems().addAll("Verdadeiro","Falso");
        if (true == dataUsuario.getBoolp_UsuarioAtivo()){
            cmbAtivoUsuario.setValue("Verdadeiro");
        }else{
            cmbAtivoUsuario.setValue("Falso");
        }
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
        ObservableList<TbGestaoUsuarios> obslistaTbGestaoUsuarioPerfilUo = FXCollections.observableArrayList();
        
        int nIdUsuarioPerfilUo = 0;
        String strUoNome = "";
        String strUODescricao = "";
        String strPerfil = "";
        boolean bAtivoUsuarioPerfilUo = true;
        
             
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        listaUsuarioPerfilUo = consulta.getlistaUsuarioPerfilUo(nIdUsuario);
        try {
        for(TbUsuarioPerfilUo l : listaUsuarioPerfilUo){
            nIdUsuarioPerfilUo = l.getIdUsuarioPerfilUo();
            strUoNome = l.getIdUnidadeOrganizacional().getUnorNome();
            strUODescricao = l.getIdUnidadeOrganizacional().getUnorDescricao();
            strPerfil = l.getIdUsuarioPerfil().getPeusDescricao();
            bAtivoUsuarioPerfilUo = l.getUspuAtivo();
            
            obslistaTbGestaoUsuarioPerfilUo.add(new TbGestaoUsuarios(bAtivoUsuarioPerfilUo, nIdUsuarioPerfilUo, strUoNome, strUODescricao, strPerfil));            
        }
        
            //tbColAtivoUsuarioPerfilUo.setCellFactory(new PropertyValueFactory<TbGestaoUsuarios,Boolean>("boolp_UsuarioPerfilUoAtivo"));
            tbColIdUoPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,Integer>("intp_idUsuarioPerfilUo"));
            tbColUoNome.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UoNome"));
            tbColUoDescricao.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_UoDescricao"));
            tbColPerfil.setCellValueFactory(new PropertyValueFactory<TbGestaoUsuarios,String>("strp_PerfilNome"));
        
            tbViewUosPerfil.setItems(obslistaTbGestaoUsuarioPerfilUo);        
        }catch(Exception e){
            System.out.println("Erro: " + e);
                    
        }
        
    }
    
    @FXML
    public void btnTeste(ActionEvent event) throws IOException{
        IniciarTabGestaoUsuariosUoPerfil(10);
        
    }
            
    
}
