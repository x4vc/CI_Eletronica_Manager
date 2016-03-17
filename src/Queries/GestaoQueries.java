/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Queries;

import Entities.TbUnidadeOrganizacional;
import Entities.TbUnidadeOrganizacionalGestor;
import Entities.TbUsuario;
import Entities.TbUsuarioPerfil;
import Entities.TbUsuarioPerfilUo;
import Utilities.Seguranca;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Victor
 */
public class GestaoQueries {
    EntityManager em;
    EntityManagerFactory emf;

    public GestaoQueries() {
        emf = Persistence.createEntityManagerFactory("CI_Eletronica_ManagerPU");
        em = emf.createEntityManager();        
        em.getTransaction().begin();
    }
    
    
    public TbUsuario getDadosUsuario(int nIdUsuario) {
        
        return em.createNamedQuery("TbUsuario.findByIdUsuario",TbUsuario.class)
                .setParameter("idUsuario", nIdUsuario )
                .getSingleResult();
        
    }
    
    public List<TbUsuario> listaUsuarios() {
        
         //return em.createNamedQuery("TbUsuario.findAll",TbUsuario.class).getResultList();
        return em.createNamedQuery("TbUsuario.findAll",TbUsuario.class)                
                .getResultList();
        
        }
    
    public List<TbUnidadeOrganizacional> listaUOs() {
        
         //return em.createNamedQuery("TbUsuario.findAll",TbUsuario.class).getResultList();
        return em.createNamedQuery("TbUnidadeOrganizacional.findAll",TbUnidadeOrganizacional.class)                
                .getResultList();
        
        }
    public List<TbUsuario> listaUserLogin(String strUserLogin) {
        
         //return em.createNamedQuery("TbUsuario.findAll",TbUsuario.class).getResultList();
        return em.createNamedQuery("TbUsuario.findByUsuLogin",TbUsuario.class) 
                .setParameter("usuLogin", strUserLogin)
                .getResultList();
        
        }
    
    public List<TbUsuario> listaUserLoginUpdate(String strUserLogin, int nIdUsuario) {
        
         //return em.createNamedQuery("TbUsuario.findAll",TbUsuario.class).getResultList();
        return em.createNamedQuery("TbUsuario.findByUsuLoginUpdate",TbUsuario.class) 
                .setParameter("usuLogin", strUserLogin)
                .setParameter("idUsuario", nIdUsuario)
                .getResultList();
        
    }
    
     public List<TbUsuarioPerfil> listaPerfis() {
        
         //return em.createNamedQuery("TbUsuario.findAll",TbUsuario.class).getResultList();
        return em.createNamedQuery("TbUsuarioPerfil.findAll",TbUsuarioPerfil.class)                
                .getResultList();
        
        }
    
    public List<TbUsuarioPerfilUo> getlistaUsuarioPerfilUo(int nIdUsuario) {
    //public List<TbComunicacaoInterna> getlistaTbComunicacaoInternaPorAprovar() {
        
        return em.createNamedQuery("TbUsuarioPerfilUo.findByIdUsuario",TbUsuarioPerfilUo.class) 
                .setParameter("idUsuario", nIdUsuario)
                .getResultList();   
        
        }
    
    public List<TbUnidadeOrganizacionalGestor> getlistaUoGestor(TbUnidadeOrganizacional nIdUO) {
    //public List<TbComunicacaoInterna> getlistaTbComunicacaoInternaPorAprovar() {
        
        return em.createNamedQuery("TbUnidadeOrganizacionalGestor.findByIdUo",TbUnidadeOrganizacionalGestor.class) 
                .setParameter("idUnidadeOrganizacional", nIdUO)
                .getResultList();   
        
        }
    
     public boolean ResetearSenha(int nIdUsuario, String strNovaSenha)throws Exception{
        try {
            String strEnc = "";
            //strEnc = Seguranca.encriptar(strNovaSenha);
            strEnc = Seguranca.stringToMD5(strNovaSenha);
            TbUsuario novaSenha = em.find(TbUsuario.class, nIdUsuario);
            
            //Codigo para Create new record
            novaSenha.setUsuSenha(strEnc);
            
            em.merge(novaSenha);
            em.getTransaction().commit();            
            em.close();
            emf.close();
            return true;           
            
        } catch (javax.persistence.PersistenceException e) {
            e.printStackTrace();
            em.close();
            emf.close();
            return false;            
        }        
    }
    
}
