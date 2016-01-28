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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author victorcmaf
 */
@Entity
@Table(name = "TB_UNIDADE_ORGANIZACIONAL_GESTOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbUnidadeOrganizacionalGestor.findAll", query = "SELECT t FROM TbUnidadeOrganizacionalGestor t"),
    @NamedQuery(name = "TbUnidadeOrganizacionalGestor.findByIdUo", query = "SELECT t FROM TbUnidadeOrganizacionalGestor t WHERE t.idUnidadeOrganizacional = :idUnidadeOrganizacional"),
    @NamedQuery(name = "TbUnidadeOrganizacionalGestor.findByIdUoge", query = "SELECT t FROM TbUnidadeOrganizacionalGestor t WHERE t.idUoge = :idUoge"),
    @NamedQuery(name = "TbUnidadeOrganizacionalGestor.findByIdUoGestor", query = "SELECT t FROM TbUnidadeOrganizacionalGestor t WHERE t.idUoGestor = :idUoGestor"),
    @NamedQuery(name = "TbUnidadeOrganizacionalGestor.findByUogeAtivo", query = "SELECT t FROM TbUnidadeOrganizacionalGestor t WHERE t.uogeAtivo = :uogeAtivo")})
public class TbUnidadeOrganizacionalGestor implements Serializable {
    @JoinColumn(name = "ID_UO_GESTOR", referencedColumnName = "ID_UNIDADE_ORGANIZACIONAL")
    @ManyToOne(optional = false)
    private TbUnidadeOrganizacional idUoGestor;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_UOGE")
    private Integer idUoge;
//    @Basic(optional = false)
//    @Column(name = "ID_UO_GESTOR")
//    private int idUoGestor;
    @Basic(optional = false)
    @Column(name = "UOGE_ATIVO")
    private boolean uogeAtivo;
    @JoinColumn(name = "ID_UNIDADE_ORGANIZACIONAL", referencedColumnName = "ID_UNIDADE_ORGANIZACIONAL")
    @ManyToOne(optional = false)
    private TbUnidadeOrganizacional idUnidadeOrganizacional;

    public TbUnidadeOrganizacionalGestor() {
    }

    public TbUnidadeOrganizacionalGestor(Integer idUoge) {
        this.idUoge = idUoge;
    }

    public TbUnidadeOrganizacionalGestor(Integer idUoge, TbUnidadeOrganizacional idUoGestor, boolean uogeAtivo) {
        this.idUoge = idUoge;
        this.idUoGestor = idUoGestor;
        this.uogeAtivo = uogeAtivo;
    }

    public Integer getIdUoge() {
        return idUoge;
    }

    public void setIdUoge(Integer idUoge) {
        this.idUoge = idUoge;
    }

//    public int getIdUoGestor() {
//        return idUoGestor;
//    }
//
//    public void setIdUoGestor(int idUoGestor) {
//        this.idUoGestor = idUoGestor;
//    }

    public boolean getUogeAtivo() {
        return uogeAtivo;
    }

    public void setUogeAtivo(boolean uogeAtivo) {
        this.uogeAtivo = uogeAtivo;
    }

    public TbUnidadeOrganizacional getIdUnidadeOrganizacional() {
        return idUnidadeOrganizacional;
    }

    public void setIdUnidadeOrganizacional(TbUnidadeOrganizacional idUnidadeOrganizacional) {
        this.idUnidadeOrganizacional = idUnidadeOrganizacional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUoge != null ? idUoge.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbUnidadeOrganizacionalGestor)) {
            return false;
        }
        TbUnidadeOrganizacionalGestor other = (TbUnidadeOrganizacionalGestor) object;
        if ((this.idUoge == null && other.idUoge != null) || (this.idUoge != null && !this.idUoge.equals(other.idUoge))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TbUnidadeOrganizacionalGestor[ idUoge=" + idUoge + " ]";
    }

    public TbUnidadeOrganizacional getIdUoGestor() {
        return idUoGestor;
    }

    public void setIdUoGestor(TbUnidadeOrganizacional idUoGestor) {
        this.idUoGestor = idUoGestor;
    }
    
}
