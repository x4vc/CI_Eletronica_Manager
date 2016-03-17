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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "TB_USUARIO_PERFIL_UO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbUsuarioPerfilUo.findAll", query = "SELECT t FROM TbUsuarioPerfilUo t"),
    @NamedQuery(name = "TbUsuarioPerfilUo.findByIdUsuarioPerfilUo", query = "SELECT t FROM TbUsuarioPerfilUo t WHERE t.idUsuarioPerfilUo = :idUsuarioPerfilUo"),
    @NamedQuery(name = "TbUsuarioPerfilUo.findByIdUsuario", query = "SELECT t FROM TbUsuarioPerfilUo t WHERE t.idUsuario = :idUsuario"),
    @NamedQuery(name = "TbUsuarioPerfilUo.findByUspuAtivo", query = "SELECT t FROM TbUsuarioPerfilUo t WHERE t.uspuAtivo = :uspuAtivo")})
public class TbUsuarioPerfilUo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_USUARIO_PERFIL_UO")
    private Integer idUsuarioPerfilUo;
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private int idUsuario;
    @Basic(optional = false)
    @Column(name = "USPU_ATIVO")
    private boolean uspuAtivo;
    @JoinColumn(name = "ID_UNIDADE_ORGANIZACIONAL", referencedColumnName = "ID_UNIDADE_ORGANIZACIONAL")
    @ManyToOne(optional = false)
    private TbUnidadeOrganizacional idUnidadeOrganizacional;
    @JoinColumn(name = "ID_USUARIO_PERFIL", referencedColumnName = "ID_USUARIO_PERFIL")
    @ManyToOne(optional = false)
    private TbUsuarioPerfil idUsuarioPerfil;

    public TbUsuarioPerfilUo() {
    }

    public TbUsuarioPerfilUo(Integer idUsuarioPerfilUo) {
        this.idUsuarioPerfilUo = idUsuarioPerfilUo;
    }

    public TbUsuarioPerfilUo(Integer idUsuarioPerfilUo, int idUsuario, boolean uspuAtivo) {
        this.idUsuarioPerfilUo = idUsuarioPerfilUo;
        this.idUsuario = idUsuario;
        this.uspuAtivo = uspuAtivo;
    }

    public Integer getIdUsuarioPerfilUo() {
        return idUsuarioPerfilUo;
    }

    public void setIdUsuarioPerfilUo(Integer idUsuarioPerfilUo) {
        this.idUsuarioPerfilUo = idUsuarioPerfilUo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean getUspuAtivo() {
        return uspuAtivo;
    }

    public void setUspuAtivo(boolean uspuAtivo) {
        this.uspuAtivo = uspuAtivo;
    }

    public TbUnidadeOrganizacional getIdUnidadeOrganizacional() {
        return idUnidadeOrganizacional;
    }

    public void setIdUnidadeOrganizacional(TbUnidadeOrganizacional idUnidadeOrganizacional) {
        this.idUnidadeOrganizacional = idUnidadeOrganizacional;
    }

    public TbUsuarioPerfil getIdUsuarioPerfil() {
        return idUsuarioPerfil;
    }

    public void setIdUsuarioPerfil(TbUsuarioPerfil idUsuarioPerfil) {
        this.idUsuarioPerfil = idUsuarioPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioPerfilUo != null ? idUsuarioPerfilUo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbUsuarioPerfilUo)) {
            return false;
        }
        TbUsuarioPerfilUo other = (TbUsuarioPerfilUo) object;
        if ((this.idUsuarioPerfilUo == null && other.idUsuarioPerfilUo != null) || (this.idUsuarioPerfilUo != null && !this.idUsuarioPerfilUo.equals(other.idUsuarioPerfilUo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TbUsuarioPerfilUo[ idUsuarioPerfilUo=" + idUsuarioPerfilUo + " ]";
    }
    
}
