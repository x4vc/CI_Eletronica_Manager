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
 * @author Victor
 */
public class TbGestaoUsuarios {
    //Variaveis para TbUsuario
    private IntegerProperty intp_idUsuario;
    private StringProperty strp_UsuarioNomeCompleto;
    private StringProperty strp_UsuarioLogin;
    private StringProperty strp_UsuarioSenha;
    private BooleanProperty boolp_UsuarioAtivo;
    
    //Variaveis para TbUsuarioPerfilUo
    private IntegerProperty intp_idUsuarioPerfilUo;
    private StringProperty strp_UoNome;
    private StringProperty strp_UoDescricao;
    private StringProperty strp_PerfilNome;
    private BooleanProperty boolp_UsuarioPerfilUoAtivo;
    
    //Constructor TbUsuario
    public TbGestaoUsuarios(Integer nIdUsuario, String strUsuNomeCompleto, String strUsuLogin, String strUsuSenha, boolean bUsuAtivo) {
        intp_idUsuario = new SimpleIntegerProperty(nIdUsuario);
        strp_UsuarioNomeCompleto = new SimpleStringProperty(strUsuNomeCompleto);
        strp_UsuarioLogin = new SimpleStringProperty(strUsuLogin);
        strp_UsuarioSenha = new SimpleStringProperty(strUsuSenha);
        boolp_UsuarioAtivo = new SimpleBooleanProperty(bUsuAtivo);
    }
    
    //Constructor TbUsuarioPerfilUo    
    public TbGestaoUsuarios(boolean bUsuPerfilUoAtivo, Integer nIdUsuarioPerfilUo, String strUonome, String strUoDescricao, String strPerfilNome){
        boolp_UsuarioPerfilUoAtivo = new SimpleBooleanProperty(bUsuPerfilUoAtivo);
        intp_idUsuarioPerfilUo = new SimpleIntegerProperty(nIdUsuarioPerfilUo);
        strp_UoNome = new SimpleStringProperty(strUonome);
        strp_UoDescricao = new SimpleStringProperty(strUoDescricao);
        strp_PerfilNome = new SimpleStringProperty(strPerfilNome);   
        
    }

    public Integer getIntp_idUsuario() {
        return intp_idUsuario.getValue();
    }

    public void setIntp_idUsuario(Integer intp_idUsuario) {
        this.intp_idUsuario.setValue(intp_idUsuario);
    }

    public String getStrp_UsuarioLogin() {
        return strp_UsuarioLogin.getValue();
    }

    public void setStrp_UsuarioLogin(String strp_UsuarioLogin) {
        this.strp_UsuarioLogin.setValue(strp_UsuarioLogin);
    }

    public String getStrp_UsuarioSenha() {
        return strp_UsuarioSenha.getValue();
    }

    public void setStrp_UsuarioSenha(String strp_UsuarioSenha) {
        this.strp_UsuarioSenha.setValue(strp_UsuarioSenha);
    }

    public Boolean getBoolp_UsuarioAtivo() {
        return boolp_UsuarioAtivo.getValue();
    }

    public void setBoolp_UsuarioAtivo(Boolean boolp_UsuarioAtivo) {
        this.boolp_UsuarioAtivo.setValue(boolp_UsuarioAtivo);
    }

    public String getStrp_UsuarioNomeCompleto() {
        return strp_UsuarioNomeCompleto.getValue();
    }

    public void setStrp_UsuarioNomeCompleto(String strp_UsuarioNomeCompleto) {
        this.strp_UsuarioNomeCompleto.setValue(strp_UsuarioNomeCompleto);
    }

    public Integer getIntp_idUsuarioPerfilUo() {
        return intp_idUsuarioPerfilUo.getValue();
    }

    public void setIntp_idUsuarioPerfilUo(Integer intp_idUsuarioPerfilUo) {
        this.intp_idUsuarioPerfilUo.setValue(intp_idUsuarioPerfilUo);
    }

    public String getStrp_UoNome() {
        return strp_UoNome.getValue();
    }

    public void setStrp_UoNome(String strp_UoNome) {
        this.strp_UoNome.setValue(strp_UoNome);
    }

    public String getStrp_PerfilNome() {
        return strp_PerfilNome.getValue();
    }

    public void setStrp_PerfilNome(String strp_PerfilNome) {
        this.strp_PerfilNome.setValue(strp_PerfilNome);
    }

    public String getStrp_UoDescricao() {
        return strp_UoDescricao.getValue();
    }

    public void setStrp_UoDescricao(String strp_UoDescricao) {
        this.strp_UoDescricao.setValue(strp_UoDescricao);
    }

    public Boolean getBoolp_UsuarioPerfilUoAtivo() {
        return boolp_UsuarioPerfilUoAtivo.getValue();
    }

    public void setBoolp_UsuarioPerfilUoAtivo(Boolean boolp_UsuarioPerfilUoAtivo) {
        this.boolp_UsuarioPerfilUoAtivo.setValue(boolp_UsuarioPerfilUoAtivo);
    }
    
}
