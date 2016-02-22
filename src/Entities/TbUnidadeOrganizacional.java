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
@Table(name = "TB_UNIDADE_ORGANIZACIONAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbUnidadeOrganizacional.findAll", query = "SELECT t FROM TbUnidadeOrganizacional t WHERE t.unorAtivo = 1 ORDER BY t.unorNome"),
    @NamedQuery(name = "TbUnidadeOrganizacional.findByIdUnidadeOrganizacional", query = "SELECT t FROM TbUnidadeOrganizacional t WHERE t.idUnidadeOrganizacional = :idUnidadeOrganizacional"),
    @NamedQuery(name = "TbUnidadeOrganizacional.findByUnorDescricao", query = "SELECT t FROM TbUnidadeOrganizacional t WHERE t.unorDescricao = :unorDescricao"),
    @NamedQuery(name = "TbUnidadeOrganizacional.findByUnorNome", query = "SELECT t FROM TbUnidadeOrganizacional t WHERE t.unorNome = :unorNome"),
    @NamedQuery(name = "TbUnidadeOrganizacional.findByUnorAtivo", query = "SELECT t FROM TbUnidadeOrganizacional t WHERE t.unorAtivo = :unorAtivo")})
public class TbUnidadeOrganizacional implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUnidadeOrganizacional")
    private Collection<TbUnidadeOrganizacionalGestor> tbUnidadeOrganizacionalGestorCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_UNIDADE_ORGANIZACIONAL")
    private Integer idUnidadeOrganizacional;
    @Column(name = "UNOR_DESCRICAO")
    private String unorDescricao;
    @Basic(optional = false)
    @Column(name = "UNOR_NOME")
    private String unorNome;
    @Basic(optional = false)
    @Column(name = "UNOR_ATIVO")
    private boolean unorAtivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUnidadeOrganizacional")
    private Collection<TbUsuarioPerfilUo> tbUsuarioPerfilUoCollection;

    public TbUnidadeOrganizacional() {
    }

    public TbUnidadeOrganizacional(Integer idUnidadeOrganizacional) {
        this.idUnidadeOrganizacional = idUnidadeOrganizacional;
    }

    public TbUnidadeOrganizacional(Integer idUnidadeOrganizacional, String unorNome, boolean unorAtivo) {
        this.idUnidadeOrganizacional = idUnidadeOrganizacional;
        this.unorNome = unorNome;
        this.unorAtivo = unorAtivo;
    }

    public Integer getIdUnidadeOrganizacional() {
        return idUnidadeOrganizacional;
    }

    public void setIdUnidadeOrganizacional(Integer idUnidadeOrganizacional) {
        this.idUnidadeOrganizacional = idUnidadeOrganizacional;
    }

    public String getUnorDescricao() {
        return unorDescricao;
    }

    public void setUnorDescricao(String unorDescricao) {
        this.unorDescricao = unorDescricao;
    }

    public String getUnorNome() {
        return unorNome;
    }

    public void setUnorNome(String unorNome) {
        this.unorNome = unorNome;
    }

    public boolean getUnorAtivo() {
        return unorAtivo;
    }

    public void setUnorAtivo(boolean unorAtivo) {
        this.unorAtivo = unorAtivo;
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
        hash += (idUnidadeOrganizacional != null ? idUnidadeOrganizacional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbUnidadeOrganizacional)) {
            return false;
        }
        TbUnidadeOrganizacional other = (TbUnidadeOrganizacional) object;
        if ((this.idUnidadeOrganizacional == null && other.idUnidadeOrganizacional != null) || (this.idUnidadeOrganizacional != null && !this.idUnidadeOrganizacional.equals(other.idUnidadeOrganizacional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TbUnidadeOrganizacional[ idUnidadeOrganizacional=" + idUnidadeOrganizacional + " ]";
    }

    @XmlTransient
    public Collection<TbUnidadeOrganizacionalGestor> getTbUnidadeOrganizacionalGestorCollection() {
        return tbUnidadeOrganizacionalGestorCollection;
    }

    public void setTbUnidadeOrganizacionalGestorCollection(Collection<TbUnidadeOrganizacionalGestor> tbUnidadeOrganizacionalGestorCollection) {
        this.tbUnidadeOrganizacionalGestorCollection = tbUnidadeOrganizacionalGestorCollection;
    }
    
}
