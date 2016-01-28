/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author victorcmaf
 */
public class TbGestaoUO {
    //Variaveis para TbUnidadeOrganizacional
    private IntegerProperty intp_idUo;
    private StringProperty strp_UoNome;
    private StringProperty strp_UoDescricao;    
    private BooleanProperty boolp_UoAtivo;
    
    //VAriaveis para TbUnidadeOrganizacionalGestor
    private IntegerProperty intp_idUoGe;
    private StringProperty strp_UoNomeGestor;
    private StringProperty strp_UoDescricaoGestor;    
    private BooleanProperty boolp_UoGestorAtivo;

    public TbGestaoUO(Integer nIdUO, String strUoNome, String strUoDescricao, boolean bUoAtivo) {
        intp_idUo = new SimpleIntegerProperty(nIdUO);
        strp_UoNome = new SimpleStringProperty(strUoNome);
        strp_UoDescricao = new SimpleStringProperty(strUoDescricao);        
        boolp_UoAtivo = new SimpleBooleanProperty(bUoAtivo);
        
    }
    
    public TbGestaoUO(boolean bUoAtivo, Integer nIdUO, String strUoNome, String strUoDescricao ) {
        boolp_UoGestorAtivo = new SimpleBooleanProperty(bUoAtivo);
        intp_idUoGe = new SimpleIntegerProperty(nIdUO);
        strp_UoNomeGestor = new SimpleStringProperty(strUoNome);
        strp_UoDescricaoGestor = new SimpleStringProperty(strUoDescricao); 
    }

    public Integer getIntp_idUoGe() {
        return intp_idUoGe.getValue();
    }

    public void setIntp_idUoGe(Integer intp_idUoGe) {
        this.intp_idUoGe.setValue(intp_idUoGe);
    }

    public String getStrp_UoNomeGestor() {
        return strp_UoNomeGestor.getValue();
    }

    public void setStrp_UoNomeGestor(String strp_UoNomeGestor) {
        this.strp_UoNomeGestor.setValue(strp_UoNomeGestor);
    }

    public String getStrp_UoDescricaoGestor() {
        return strp_UoDescricaoGestor.getValue();
    }

    public void setStrp_UoDescricaoGestor(String strp_UoDescricaoGestor) {
        this.strp_UoDescricaoGestor.setValue(strp_UoDescricaoGestor);
    }

    public Boolean getBoolp_UoGestorAtivo() {
        return boolp_UoGestorAtivo.getValue();
    }

    public void setBoolp_UoGestorAtivo(Boolean boolp_UoGestorAtivo) {
        this.boolp_UoGestorAtivo.setValue(boolp_UoGestorAtivo);
    }   
    

    public Integer getIntp_idUo() {
        return intp_idUo.getValue();
    }

    public void setIntp_idUo(Integer intp_idUo) {
        this.intp_idUo.setValue(intp_idUo);
    }

    public String getStrp_UoNome() {
        return strp_UoNome.getValue();
    }

    public void setStrp_UoNome(String strp_UoNome) {
        this.strp_UoNome.setValue(strp_UoNome);
    }

    public String getStrp_UoDescricao() {
        return strp_UoDescricao.getValue();
    }

    public void setStrp_UoDescricao(String strp_UoDescricao) {
        this.strp_UoDescricao.setValue(strp_UoDescricao);
    }

    public Boolean getBoolp_UoAtivo() {
        return boolp_UoAtivo.getValue();
    }

    public void setBoolp_UoAtivo(Boolean boolp_UoAtivo) {
        this.boolp_UoAtivo.setValue(boolp_UoAtivo);
    }
    
    
}
