/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddEdit;

import Entities.TbGestaoUsuarios;
import Entities.TbUnidadeOrganizacional;
import Entities.TbUsuarioPerfil;
import Queries.GestaoQueries;
import com.sun.glass.ui.Robot;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author victorcmaf
 */
public class FXMLAddEditUOPerfilUsuarioController implements Initializable {
    @FXML
    ComboBox<Choice> cbUo;
    @FXML
    ChoiceBox<Choice> chbUo;
    @FXML
    ChoiceBox<Choice> chbPerfilUsuario;
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
        
        //Por falta de tempo não vamos permitir editar a comboxbox
        //caso seja necessário filtrar o combobox então deve ser Editable = true
        cbUo.setEditable(false);
        
        //Ocultamos o choicebox das Uos porque vamos utilizar o combobox
        chbUo.setVisible(false);
        //--------------------------------
        
        //Preenchemos UOs no combobox
        ObservableList<Choice> choicesUOs = FXCollections.observableArrayList();
        choicesUOs.add(new Choice(null, "Favor selecionar"));
        
        List<TbUnidadeOrganizacional> listaUOs = new ArrayList<TbUnidadeOrganizacional>();
        GestaoQueries consulta;
        consulta  = new GestaoQueries();  
        
        listaUOs = consulta.listaUOs();
        
        for(TbUnidadeOrganizacional l : listaUOs){
            choicesUOs.add(new Choice(l.getIdUnidadeOrganizacional(), l.getUnorNome(), l.getUnorDescricao()));         
        }
        cbUo.setItems(choicesUOs);
        
        //Para utilizar auto-completar devemos instanciar a clase AutoCompleteComboBoxListener
        //new AutoCompleteComboBoxListener(cbUo);
       
        /*
        // Create a FilteredList wrapping the ObservableList.
        FilteredList<Choice> filteredItems = new FilteredList<Choice>(choicesUOs, p -> true);
        
        // Add a listener to the textProperty of the combobox editor. The
        // listener will simply filter the list every time the input is changed
        // as long as the user hasn't selected an item in the list.
        cbUo.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = cbUo.getEditor();
            Choice selected = cbUo.getSelectionModel().getSelectedItem();

            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        if (item.displayString.toUpperCase().startsWith(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });
        */
        
        chbUo.setItems(choicesUOs);        
        chbUo.getSelectionModel().select(0);
        
        //cbUo.setItems(filteredItems);
        
        //cbUo.setItems(choicesUOs);
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
            choicesPerfil.add(new Choice(l.getIdUsuarioPerfil(), l.getPeusNome(), l.getPeusDescricao()));       
        }
        
        chbPerfilUsuario.setItems(choicesPerfil);        
        chbPerfilUsuario.getSelectionModel().select(0);
        
        /*cbUo.setItems(choicesUOs);
        cbUo.getSelectionModel().select(0);*/

        //--------------FIM preencher Perfil do usuário no box ---------------
        
    }
    
       /** Helper class for mapping a choice displayable in a ChoiceBox to a backing id. */
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
    
    @FXML 
    private void btnClickAddUO(ActionEvent event) throws IOException{
        Choice choiceUo = cbUo.getSelectionModel().getSelectedItem();
        Choice choicePerfilUsuario = chbPerfilUsuario.getSelectionModel().getSelectedItem();
        
        
        System.out.println("UO = " + choiceUo.id + " " + choiceUo.displayString + " " + choiceUo.displayString2);
        
        System.out.println("Perfil = " + choicePerfilUsuario.id + " " + choicePerfilUsuario.displayString + " " + choicePerfilUsuario.displayString2);
        
        //ObservableList<Choice> U0PerfilUsuario = FXCollections.observableArrayList();        
        ObservableList<TbGestaoUsuarios> obslistaTbGestaoUsuarioPerfilUo = FXCollections.observableArrayList();
        
        //obslistaTbGestaoUsuarioPerfilUo.add(new TbGestaoUsuarios(bAtivoUsuarioPerfilUo, nIdUsuarioPerfilUo, strUoNome, strUODescricao, strPerfil));
        obslistaTbGestaoUsuarioPerfilUo.add(
                new TbGestaoUsuarios(/*bAtivoUsuarioPerfilUo*/ true, /*nIdUsuarioPerfilUo*/ 0, 
                        /*strUoNome*/ choiceUo.displayString, 
                        /*strUODescricao*/choiceUo.displayString, 
                        /*strPerfil*/ choicePerfilUsuario.displayString));
        listItensUOPerfil.setItems(obslistaTbGestaoUsuarioPerfilUo);
               
                
    }
    @FXML 
    private void btnClickDeleteUO(ActionEvent event) throws IOException{
          
                
    }
    
    @FXML 
    private void btnClickCancelar(ActionEvent event) throws IOException{
        // get a handle to the stage
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // do what you have to do
        stage.close();        
                
    }
    
    
    
}

//Create AutoComplete ComboBox
class AutoCompleteComboBoxListener implements EventHandler<KeyEvent> {
 
    private ComboBox comboBox;
    private StringBuilder sb;
    private int lastLength;
    
    public AutoCompleteComboBoxListener(ComboBox comboBox) {
        this.comboBox = comboBox;
        sb = new StringBuilder();
        
        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
        
        // add a focus listener such that if not in focus, reset the filtered typed keys
        this.comboBox.getEditor().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    // in focus
                }
                else {
                    lastLength = 0;
                    sb.delete(0, sb.length());
                    selectClosestResultBasedOnTextFieldValue(false, false);
                }
            }
        });
        
        this.comboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                selectClosestResultBasedOnTextFieldValue(true, true);
            }            
        });
    }
 
    @Override
    public void handle(KeyEvent event) {
        // this variable is used to bypass the auto complete process if the length is the same.
        // this occurs if user types fast, the length of textfield will record after the user
        // has typed after a certain delay.
        if (lastLength != (comboBox.getEditor().getLength() - comboBox.getEditor().getSelectedText().length()))
            lastLength = comboBox.getEditor().getLength() - comboBox.getEditor().getSelectedText().length();
        
        if (event.isControlDown() || event.getCode() == KeyCode.BACK_SPACE ||
            event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || 
            event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.HOME || 
            event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB
            )
            return;
        
        IndexRange ir = comboBox.getEditor().getSelection();
        sb.delete(0, sb.length());
        sb.append(comboBox.getEditor().getText());
        // remove selected string index until end so only unselected text will be recorded
        try {
            sb.delete(ir.getStart(), sb.length());
        } catch (Exception e) { }
            
        ObservableList items = comboBox.getItems();
        for (int i=0; i<items.size(); i++) {
            if (items.get(i).toString().toLowerCase().startsWith(comboBox.getEditor().getText().toLowerCase())
                )
            {
                try {
                    comboBox.getEditor().setText(sb.toString() + items.get(i).toString().substring(sb.toString().length()));
                } catch (Exception e) {
                    comboBox.getEditor().setText(sb.toString());
                }
                comboBox.getEditor().positionCaret(sb.toString().length());
                comboBox.getEditor().selectEnd();
                break;
            }
        }
    }
 
    /*
     * selectClosestResultBasedOnTextFieldValue() - selects the item and scrolls to it when
     * the popup is shown.
     * 
     * parameters:
     *  affect - true if combobox is clicked to show popup so text and caret position will be readjusted.
     *  inFocus - true if combobox has focus. If not, programmatically press enter key to add new entry to list.
     * 
     */
    private void selectClosestResultBasedOnTextFieldValue(boolean affect, boolean inFocus) {
        ObservableList items = AutoCompleteComboBoxListener.this.comboBox.getItems();
        boolean found = false;
        for (int i=0; i<items.size(); i++) {
            if (AutoCompleteComboBoxListener.this.comboBox.getEditor().getText().toLowerCase().equals(items.get(i).toString().toLowerCase())) {
                try {
                    ListView lv = ((ComboBoxListViewSkin) AutoCompleteComboBoxListener.this.comboBox.getSkin()).getListView();
                    lv.getSelectionModel().clearAndSelect(i);
                    lv.scrollTo(lv.getSelectionModel().getSelectedIndex());
                    found = true;
                    break;
                } catch (Exception e) { }
            }
        }
 
        String s = comboBox.getEditor().getText();
        if (!found && affect) {            
            comboBox.getSelectionModel().clearSelection();
            comboBox.getEditor().setText(s);
            comboBox.getEditor().end();
        }
        
        if (!inFocus && comboBox.getEditor().getText() != null && comboBox.getEditor().getText().trim().length() > 0) {
        // press enter key programmatically to have this entry added

        Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
        robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
        }
        /*
        if (!inFocus && comboBox.getEditor().getText() != null && comboBox.getEditor().getText().trim().length() > 0) {
            // press enter key programmatically to have this entry added
            KeyEvent ke = KeyEvent.impl_keyEvent(comboBox, KeyCode.ENTER.toString(), KeyCode.ENTER.getName(), KeyCode.ENTER.impl_getCode(), false, false, false, false, KeyEvent.KEY_RELEASED);
            comboBox.fireEvent(ke);
        }*/
    }
    
}

