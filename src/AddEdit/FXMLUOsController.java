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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    private TableView<TbGestaoUO> tbViewUoGestora;
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
    @FXML
    private Button btnAdicionarUOGestora;
    @FXML
    private Button btnExcluirUOGestora;    
    @FXML
    private Button btnEditarUOGestora;
    @FXML
    ComboBox<Choice> cbUo;
    
    //Valores nTipoCrud:
    //1 ==> Salvar novo registro
    //2 ==> Editar registro*/
    private int nTipoCrud = 0;     
    //-----------------------------
    
    private ObservableList<TbGestaoUO> obslistaTbGestaoUoGestor = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnCancelar.setCancelButton(true);
        
        txtUoNome.textProperty().addListener((ov, oldValue, newValue) -> {
            txtUoNome.setText(newValue.toUpperCase());
        });
        
    }
    
    public void setVariaveisAmbienteFXMLUO(final FXMLCI_Eletronica_ManagerController mainController, TbGestaoUO dataUsuario, int nTipoCrud){
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
        //Valores nTipoCrud:
        //1 ==> Salvar novo registro
        //2 ==> Editar registro*/
        this.nTipoCrud = nTipoCrud; 
        
        switch (nTipoCrud){
            case 1: 
                
                btnEditarUOGestora.setVisible(false);
                
                break;
            case 2:
                
                btnEditarUOGestora.setVisible(true);
                
                break;
            default:
                break;
        }
        //-------------------------------------------------------------
        
        IniciarTabGestaoUoGestor(dataUsuario.getIntp_idUo());
    }
    
    private void IniciarTabGestaoUoGestor(int nIdUO){
        //Preenchemos UOs no combobox
        ObservableList<Choice> choicesUOs = FXCollections.observableArrayList();
        choicesUOs.add(new Choice(null, "Favor selecionar UO Gestora"));
        
        List<TbUnidadeOrganizacional> listaUOs = new ArrayList<TbUnidadeOrganizacional>();
        GestaoQueries consultaUOs;
        consultaUOs  = new GestaoQueries();  
        
        listaUOs = consultaUOs.listaUOs();
        
        for(TbUnidadeOrganizacional l : listaUOs){
            choicesUOs.add(new Choice(l.getIdUnidadeOrganizacional(), l.getUnorNome(), l.getUnorDescricao()));         
        }
        cbUo.setItems(choicesUOs);
       
        cbUo.getSelectionModel().select(0);

        //--------------FIM preencher UOs no combox ---------------
        
        
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
        //ObservableList<TbGestaoUO> obslistaTbGestaoUoGestor = FXCollections.observableArrayList();
        
        TbUnidadeOrganizacional nIdUoGestor = new TbUnidadeOrganizacional(nIdUO);
        int nIdUnidadeOrganizacionalGestor = 0;
        String strUoNomeGestor = "";
        String strUODescricaoGestor = "";        
        boolean bAtivoUoGestor = true;
        
             
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        listaUoGestor = consulta.getlistaUoGestor(nIdUoGestor);
        
        for(TbUnidadeOrganizacionalGestor l : listaUoGestor){
            nIdUnidadeOrganizacionalGestor = l.getIdUoGestor().getIdUnidadeOrganizacional();
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
       /** Helper class for mapping a choice displayable in a ChoiceBox to a backing id. */
    
    
    @FXML 
    private void btnClickCancelar(ActionEvent event) throws IOException{
        // get a handle to the stage
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // do what you have to do
        stage.close();        
                
    }
    
    @FXML
    private void btnAdicionarUOGestora(ActionEvent event) throws IOException{
        Alert alert;    
        int nSize = 0;
        boolean bAtivo = false;
        int nAtivo = 0;
        //Valores nTipoCrud:
        //1 ==> Salvar novo registro
        //2 ==> Editar registro*/
        
        switch (nTipoCrud){
            case 1:
                tbViewUoGestora.getSelectionModel().select(0);
                nSize = tbViewUoGestora.getSelectionModel().getSelectedItems().size();
                if (nSize == 0){
                    Choice choiceUo = cbUo.getSelectionModel().getSelectedItem();
                    obslistaTbGestaoUoGestor.add(new TbGestaoUO(true, choiceUo.id /*nIdUnidadeOrganizacionalGestor*/, 
                            choiceUo.displayString /*strUoNomeGestor*/, choiceUo.displayString2 /*strUODescricaoGestor*/ ));
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("UO não pode ter mais de uma UO Gestora com Status = true.");
                    alert.showAndWait();
                }
                
                break;
            case 2:
                tbViewUoGestora.getSelectionModel().select(0);
                nSize = tbViewUoGestora.getSelectionModel().getSelectedItems().size();
                if (nSize>0){
                    for (int i = 0; i <nSize; i++){
                        TbGestaoUO entityTb = tbViewUoGestora.getSelectionModel().getSelectedItems().get(i);
                        bAtivo = entityTb.getBoolp_UoGestorAtivo();
                        if (true == bAtivo){
                            nAtivo++;
                        }
                    }
                }
                if (nAtivo >= 1){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("UO não pode ter mais de uma UO Gestora com Status = true.\nFavor conferir que todas as UOs Gestoras possuem status = false");
                    alert.showAndWait();

                } else {

                }
                break;
            default:
                break;            
        }
        
        
        
        
    }
    @FXML
    private void btnExcluirUOGestora(ActionEvent event) throws IOException{
        Alert alert;    
        if (null == tbViewUoGestora.getSelectionModel().getSelectedItem()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Registro não foi selecionado.");
            alert.setContentText("Favor selecionar um registro da tabela");
            alert.showAndWait();
        }else{
            TbGestaoUO data = tbViewUoGestora.getSelectionModel().getSelectedItem();  
            obslistaTbGestaoUoGestor.remove(data);            
        }
    }
    
    @FXML
    private void btnEditarUOGestora(ActionEvent event) throws IOException{
        Alert alert;    
        if (null == tbViewUoGestora.getSelectionModel().getSelectedItem()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Registro não foi selecionado.");
            alert.setContentText("Favor selecionar um registro da tabela");
            alert.showAndWait();
        }else{
            try{
                TbGestaoUO entitiyTbGestaoUo= tbViewUoGestora.getSelectionModel().getSelectedItem();
                int nlIndex = tbViewUoGestora.getSelectionModel().getSelectedIndex();
                Scene scene;
                scene = new Scene(new AnchorPane());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEdit/FXMLEditUOGestora.fxml"));
                scene.setRoot((Parent) loader.load());
                
                AddEdit.FXMLEditUOGestoraController usuarioController = loader.<AddEdit.FXMLEditUOGestoraController>getController(); 
                usuarioController.setVariaveisAmbienteFXMLEditUOGestora(this/*mainController*/, entitiyTbGestaoUo);
                
                Stage stage = new Stage();
                stage.setTitle("Editar Status da UO Gestora");
                //set icon
                stage.getIcons().add(new Image("/Resources/administrator1-add-16x16.gif"));

                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);     //Window Parent fica inativo
                stage.showAndWait();
                
                boolean bButtonSaveClicked = false;
                bButtonSaveClicked = usuarioController.isbButtonSalvarClicked();
                
                TbGestaoUO entidadeTbGestaoUO;
                entidadeTbGestaoUO = usuarioController.getgEntityTbGestaoUO();
                
                if (bButtonSaveClicked){
                    System.out.println();
                    if (null != entidadeTbGestaoUO){
                        obslistaTbGestaoUoGestor.set(nlIndex,entidadeTbGestaoUO);
                        //System.out.println();
                        
                    }
                }
                //System.out.println();
                               
            }catch (IOException ex) {
                Logger.getLogger(FXMLCI_Eletronica_ManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
        
    @FXML
    private void btnSalvarUO(ActionEvent event) throws IOException{
        boolean bCampos = false;
        Stage stage;
        
        //Valores nTipoCrud:
        //1 ==> Salvar novo registro
        //2 ==> Editar registro*/       
        switch (nTipoCrud){
            case 1: 
                    bCampos = Verificar_campos(nTipoCrud);
                    if (true == bCampos){
                        SalvarNovaUO();
                        // get a handle to the stage
                        stage = (Stage) btnSalvar.getScene().getWindow();
                        // do what you have to do
                        stage.close();
                    }
                break;
                
            case 2:                
                    bCampos = Verificar_campos(nTipoCrud);   
                    bCampos = Verificar_campos(nTipoCrud);
                    if (true == bCampos){
                        SalvarEdicaoUO(Integer.parseInt(txtIdUO.getText().trim()));
                        // get a handle to the stage
                        stage = (Stage) btnSalvar.getScene().getWindow();
                        // do what you have to do
                        stage.close();
                    }
                break;
                
            default:
                break;
        }
        
        //-------------------------------------------------------------
        
    }
    private void SalvarNovaUO(){
        Alert alert;
        
        String strNomeUO = txtUoNome.getText();
        String strDescricaoUO = txtUoDescrição.getText();
        boolean bStatusUO = true;
        String strStatusUO = cmbAtivoUo.getValue().toString();
        if (0==strStatusUO.compareTo("Ativado")){
            bStatusUO = true;
        }else{
            bStatusUO = false;
        }
        //------------ Iniciamos transaction ------------------------
        EntityManager em;
        EntityManagerFactory emf;
        
        emf = Persistence.createEntityManagerFactory("CI_Eletronica_ManagerPU");
        em = emf.createEntityManager();        
        em.getTransaction().begin();
        
        
        try{
            TbUnidadeOrganizacional newUO = new TbUnidadeOrganizacional();
            //-----------------------------------------------------------
            newUO.setUnorNome(strNomeUO);
            newUO.setUnorDescricao(strDescricaoUO);
            newUO.setUnorAtivo(bStatusUO);

            em.persist(newUO);
            em.flush();


            long IdUO = newUO.getIdUnidadeOrganizacional();
            int nIdUO = (int)IdUO;
            
            TbUnidadeOrganizacional nIdUnidadeOrganizacional = new TbUnidadeOrganizacional(nIdUO);
            
            int nSizeListaUO = 0;
            nSizeListaUO = this.obslistaTbGestaoUoGestor.size();
            for (int i = 0; i<nSizeListaUO;i++){
                TbUnidadeOrganizacionalGestor newUOGestor = new TbUnidadeOrganizacionalGestor();
                
                TbGestaoUO entityTbGestaoUO = this.obslistaTbGestaoUoGestor.get(i);                
                
                TbUnidadeOrganizacional nlIdUOGestor = new TbUnidadeOrganizacional(entityTbGestaoUO.getIntp_idUoGe());

                newUOGestor.setIdUnidadeOrganizacional(nIdUnidadeOrganizacional);
                newUOGestor.setIdUoGestor(nlIdUOGestor);                
                newUOGestor.setUogeAtivo(true);

                em.persist(newUOGestor);
            }            
            
        }catch (javax.persistence.PersistenceException e){
            System.out.println(e);
            em.close();
            emf.close();
            
        }
        em.getTransaction().commit();            
        em.close();
        emf.close();
        
        //-----------------------------------------------------------
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText("Registro foi salvo.");
        alert.setContentText("Nova UO foi salvo com sucesso");
        alert.showAndWait();
    }
    
    private void SalvarEdicaoUO(int nIdUO){
        Alert alert;
        
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText("Registro da UO foi editado e salvo.");
        alert.setContentText("Salvar edição do registro");
        alert.showAndWait();   
        
    }
    
    private Boolean Verificar_campos(int nTipoCrud){
        Alert alert;
        boolean bEstado= true;
        
        String strUOName = txtUoNome.getText();
        strUOName = strUOName.trim();
        
        String strUODescricao = txtUoDescrição.getText();
        strUODescricao = strUODescricao.trim();
        
        
        int nSizeListaUO = 0;        
        nSizeListaUO = this.obslistaTbGestaoUoGestor.size();
        //----------------------------------------------------------
        //Verificamos se TableView possui mais de uma UO com status = true
        int nSize = 0;
        int nAtivo = 0;
        boolean bAtivo = false;
        tbViewUoGestora.getSelectionModel().select(0);
        nSize = tbViewUoGestora.getSelectionModel().getSelectedItems().size();
        if (nSize>0){
            for (int i = 0; i <nSize; i++){
                TbGestaoUO entityTb = tbViewUoGestora.getSelectionModel().getSelectedItems().get(i);
                bAtivo = entityTb.getBoolp_UoGestorAtivo();
                if (true == bAtivo){
                    nAtivo++;
                }
            }
        }
        //----------------------------------------------------------
        
        if (strUOName.isEmpty()){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Dados inconsistentes");
            alert.setContentText("Usuário deve preencher nome da UO.");
            alert.showAndWait(); 
            
            bEstado = false;
            return bEstado;            
        }else if (strUODescricao.isEmpty()){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Dados inconsistentes");
            alert.setContentText("Usuário deve preencher descrição da UO.");
            alert.showAndWait();
            bEstado = false;
            
            return bEstado;                        
                               
        }else if ((nSizeListaUO <=0)){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Dados inconsistentes");
            alert.setContentText("UO deve estar relacionado com ao menos uma UO Gestora.");
            alert.showAndWait();   
            
            bEstado = false;
            return bEstado;            
        
        }else if (nAtivo > 1){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("UO não pode ter mais de uma UO Gestora com Status = true.\nFavor conferir que todas as UOs Gestoras possuem status = false");
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

class Choice {        
      
        Integer id; String displayString; String displayString2;
        Choice(Integer id)                       { this(id, null,null); }
        Choice(String  displayString)            { this(null, displayString); }        
        Choice(Integer id, String displayString) { this.id = id; this.displayString = displayString;}
        Choice(Integer id, String displayString, String displayString2) { this.id = id; this.displayString = displayString; this.displayString2 = displayString2;}

        @Override public String toString() { return displayString; } 
        @Override public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          Choice choice = (Choice) o;
          return displayString != null && displayString.equals(choice.displayString) || id != null && id.equals(choice.id);      
        }

        @Override public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (displayString != null ? displayString.hashCode() : 0);
            return result;
        }
      }
