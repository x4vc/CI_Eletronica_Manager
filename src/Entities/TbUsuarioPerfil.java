/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "TB_USUARIO_PERFIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbUsuarioPerfil.findAll", query = "SELECT t FROM TbUsuarioPerfil t"),
    @NamedQuery(name = "TbUsuarioPerfil.findByIdUsuarioPerfil", query = "SELECT t FROM TbUsuarioPerfil t WHERE t.idUsuarioPerfil = :idUsuarioPerfil"),
    @NamedQuery(name = "TbUsuarioPerfil.findByPeusNome", query = "SELECT t FROM TbUsuarioPerfil t WHERE t.peusNome = :peusNome"),
    @NamedQuery(name = "TbUsuarioPerfil.findByPeusDescricao", query = "SELECT t FROM TbUsuarioPerfil t WHERE t.peusDescricao = :peusDescricao"),
    @NamedQuery(name = "TbUsuarioPerfil.findByPeusAtivo", query = "SELECT t FROM TbUsuarioPerfil t WHERE t.peusAtivo = :peusAtivo")})
public class TbUsuarioPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USUARIO_PERFIL")
    private Integer idUsuarioPerfil;
    @Basic(optional = false)
    @Column(name = "PEUS_NOME")
    private String peusNome;
    @Column(name = "PEUS_DESCRICAO")
    private String peusDescricao;
    @Basic(optional = false)
    @Column(name = "PEUS_ATIVO")
    private boolean peusAtivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioPerfil")
    private Collection<TbUsuarioPerfilUo> tbUsuarioPerfilUoCollection;

    public TbUsuarioPerfil() {
    }

    public TbUsuarioPerfil(Integer idUsuarioPerfil) {
        this.idUsuarioPerfil = idUsuarioPerfil;
    }

    public TbUsuarioPerfil(Integer idUsuarioPerfil, String peusNome, boolean peusAtivo) {
        this.idUsuarioPerfil = idUsuarioPerfil;
        this.peusNome = peusNome;
        this.peusAtivo = peusAtivo;
    }

    public Integer getIdUsuarioPerfil() {
        return idUsuarioPerfil;
    }

    public void setIdUsuarioPerfil(Integer idUsuarioPerfil) {
        this.idUsuarioPerfil = idUsuarioPerfil;
    }

    public String getPeusNome() {
        return peusNome;
    }

    public void setPeusNome(String peusNome) {
        this.peusNome = peusNome;
    }

    public String getPeusDescricao() {
        return peusDescricao;
    }

    public void setPeusDescricao(String peusDescricao) {
        this.peusDescricao = peusDescricao;
    }

    public boolean getPeusAtivo() {
        return peusAtivo;
    }

    public void setPeusAtivo(boolean peusAtivo) {
        this.peusAtivo = peusAtivo;
    }

    @XmlTransient
    public Collection<TbUsuarioPerfilUo> getTbUsuarioPerfilUoCollection() {
        return tbUsuarioPerfilUoCollection;
    }

    public void setTbUsuarioPerfilUoCollection(Collection<TbUsuarioPerfilUo> tbUsuarioPerfilUoCollection) {
        this.tbUsuarioPerfilUoCollection = tbUsuarioPerfilUoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioPerfil != null ? idUsuarioPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbUsuarioPerfil)) {
            return false;
        }
        TbUsuarioPerfil other = (TbUsuarioPerfil) object;
        if ((this.idUsuarioPerfil == null && other.idUsuarioPerfil != null) || (this.idUsuarioPerfil != null && !this.idUsuarioPerfil.equals(other.idUsuarioPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TbUsuarioPerfil[ idUsuarioPerfil=" + idUsuarioPerfil + " ]";
    }
    
}
