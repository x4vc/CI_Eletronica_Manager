/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Queries;

import Entities.TbUsuario;
import Entities.TbUsuarioPerfilUo;
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
    
    
    
    public List<TbUsuario> listaUsuarios() {
        
         //return em.createNamedQuery("TbUsuario.findAll",TbUsuario.class).getResultList();
        return em.createNamedQuery("TbUsuario.findAll",TbUsuario.class)                
                .getResultList();
        
        }
    
    public List<TbUsuarioPerfilUo> getlistaUsuarioPerfilUo(int nIdUsuario) {
    //public List<TbComunicacaoInterna> getlistaTbComunicacaoInternaPorAprovar() {
        
        return em.createNamedQuery("TbUsuarioPerfilUo.findByIdUsuario",TbUsuarioPerfilUo.class) 
                .setParameter("idUsuario", nIdUsuario)
                .getResultList();   
        
        }
    
}
