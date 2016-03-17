/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "TB_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbUsuario.findAll", query = "SELECT t FROM TbUsuario t"),
    @NamedQuery(name = "TbUsuario.findByIdUsuario", query = "SELECT t FROM TbUsuario t WHERE t.idUsuario = :idUsuario"),
    @NamedQuery(name = "TbUsuario.findByUsuNomeCompleto", query = "SELECT t FROM TbUsuario t WHERE t.usuNomeCompleto = :usuNomeCompleto"),
    @NamedQuery(name = "TbUsuario.findByUsuLogin", query = "SELECT t FROM TbUsuario t WHERE t.usuLogin = :usuLogin"),
    @NamedQuery(name = "TbUsuario.findByUsuLoginUpdate", query = "SELECT t FROM TbUsuario t WHERE t.usuLogin = :usuLogin AND t.idUsuario NOT IN(:idUsuario)"),
    @NamedQuery(name = "TbUsuario.findByUsuSenha", query = "SELECT t FROM TbUsuario t WHERE t.usuSenha = :usuSenha"),
    @NamedQuery(name = "TbUsuario.findByUsuAtivo", query = "SELECT t FROM TbUsuario t WHERE t.usuAtivo = :usuAtivo"),
    @NamedQuery(name = "TbUsuario.findByUsuAssinatura", query = "SELECT t FROM TbUsuario t WHERE t.usuAssinatura = :usuAssinatura")})
public class TbUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;
    @Basic(optional = false)
    @Column(name = "USU_NOME_COMPLETO")
    private String usuNomeCompleto;
    @Basic(optional = false)
    @Column(name = "USU_LOGIN")
    private String usuLogin;
    @Basic(optional = false)
    @Column(name = "USU_SENHA")
    private String usuSenha;
    @Basic(optional = false)
    @Column(name = "USU_ATIVO")
    private boolean usuAtivo;
    @Column(name = "USU_ASSINATURA")
    private String usuAssinatura;

    public TbUsuario() {
    }

    public TbUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TbUsuario(Integer idUsuario, String usuNomeCompleto, String usuLogin, String usuSenha, boolean usuAtivo) {
        this.idUsuario = idUsuario;
        this.usuNomeCompleto = usuNomeCompleto;
        this.usuLogin = usuLogin;
        this.usuSenha = usuSenha;
        this.usuAtivo = usuAtivo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuNomeCompleto() {
        return usuNomeCompleto;
    }

    public void setUsuNomeCompleto(String usuNomeCompleto) {
        this.usuNomeCompleto = usuNomeCompleto;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getUsuSenha() {
        return usuSenha;
    }

    public void setUsuSenha(String usuSenha) {
        this.usuSenha = usuSenha;
    }

    public boolean getUsuAtivo() {
        return usuAtivo;
    }

    public void setUsuAtivo(boolean usuAtivo) {
        this.usuAtivo = usuAtivo;
    }

    public String getUsuAssinatura() {
        return usuAssinatura;
    }

    public void setUsuAssinatura(String usuAssinatura) {
        this.usuAssinatura = usuAssinatura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbUsuario)) {
            return false;
        }
        TbUsuario other = (TbUsuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TbUsuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
