/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddEdit;

import Entities.TbUnidadeOrganizacional;
import Entities.TbUsuarioPerfil;
import Queries.GestaoQueries;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author victorcmaf
 */
public class FXMLAddEditUOPerfilUsuarioController implements Initializable {
    @FXML
    ComboBox cbUo;
    @FXML
    ChoiceBox<Choice> chbUo;
    @FXML
    ChoiceBox chbPerfilUsuario;
    @FXML
    Button btnAdicionar;
    @FXML
    Button btnOK;
    @FXML
    Button btnCancelar;
    @FXML
    Button btDeletar;
    @FXML
    ListView listItensUOPerfil;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnCancelar.setCancelButton(true);
        
        //Preenchemos UOs no combobox
        ObservableList<Choice> choicesUOs = FXCollections.observableArrayList();
        choicesUOs.add(new Choice(null, "Favor selecionar"));
        
        List<TbUnidadeOrganizacional> listaUOs = new ArrayList<TbUnidadeOrganizacional>();
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        listaUOs = consulta.listaUOs();
        
        for(TbUnidadeOrganizacional l : listaUOs){
            choicesUOs.add(new Choice(l.getIdUnidadeOrganizacional(), l.getUnorNome()));
//            nIdUO = l.getIdUnidadeOrganizacional();
//            strNomeUO = l.getUnorNome();
//            strDescricaoUO = l.getUnorDescricao();
//            bAtivo = l.getUnorAtivo();
//            
//            obslistaTbGestaoUOs.add(new TbGestaoUO(nIdUO, strNomeUO, strDescricaoUO, bAtivo));            
        }
        
//        for (Database.Animal animal : db.findAllAnimals()) {
//          choices.add(new Choice(animal.id, animal.name));
//        }
        chbUo.setItems(choicesUOs);        
        chbUo.getSelectionModel().select(0);
        
        cbUo.setItems(choicesUOs);
        cbUo.getSelectionModel().select(0);

        //--------------FIM preencher UOs no combox ---------------
        
        //Preenchemos Perfil do Usuário  no Choicebox
        ObservableList<Choice> choicesPerfil = FXCollections.observableArrayList();
        choicesPerfil.add(new Choice(null, "Favor selecionar"));
        
        List<TbUsuarioPerfil> listaPerfil = new ArrayList<TbUsuarioPerfil>();
        GestaoQueries consultaPerfil;
        consultaPerfil  = new GestaoQueries();  
        
        listaPerfil = consulta.listaPerfis();
        
        for(TbUsuarioPerfil l : listaPerfil){
            choicesPerfil.add(new Choice(l.getIdUsuarioPerfil(), l.getPeusNome()));
//            nIdUO = l.getIdUnidadeOrganizacional();
//            strNomeUO = l.getUnorNome();
//            strDescricaoUO = l.getUnorDescricao();
//            bAtivo = l.getUnorAtivo();
//            
//            obslistaTbGestaoUOs.add(new TbGestaoUO(nIdUO, strNomeUO, strDescricaoUO, bAtivo));            
        }
        
//        for (Database.Animal animal : db.findAllAnimals()) {
//          choices.add(new Choice(animal.id, animal.name));
//        }
        chbPerfilUsuario.setItems(choicesPerfil);        
        chbPerfilUsuario.getSelectionModel().select(0);
        
        /*cbUo.setItems(choicesUOs);
        cbUo.getSelectionModel().select(0);*/

        //--------------FIM preencher Perfil do usuário no box ---------------
        
    }
    
       /** Helper class for mapping a choice displayable in a ChoiceBox to a backing id. */
    class Choice {        
      
        Integer id; String displayString;
        Choice(Integer id)                       { this(id, null); }
        Choice(String  displayString)            { this(null, displayString); }
        Choice(Integer id, String displayString) { this.id = id; this.displayString = displayString; }

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
    
    @FXML 
    private void btnClickCancelar(ActionEvent event) throws IOException{
        // get a handle to the stage
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // do what you have to do
        stage.close();        
                
    }
    
}

