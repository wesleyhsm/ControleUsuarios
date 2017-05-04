package br.com.agrofauna.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.agrofauna.banco.ConexaoBanco;
import br.com.agrofauna.filtro.PermissoesFiltro;
import br.com.agrofauna.model.Permissao;
import br.com.agrofauna.model.PermissaoSetor;

public class PermissaoRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
		
	public Permissao salvar(Permissao permissao) {		 		
		return this.manager.merge(permissao); 
	}
	
	public void excluir(Permissao permissao){		
		this.manager.remove( this.manager.find(Permissao.class, permissao.getIdPermissao()));
	}
	
	public Permissao verificaExistePermissao(Permissao permissao) {
		try{
			return (Permissao) this.manager.createNamedQuery("Permissao.existePermissao")
								 .setParameter("janela", permissao.getNmJanela())		
								 .getSingleResult();
		}catch (NoResultException nre){
			return null;
		}	
	}
	
	public Permissao buscarPermissaoPeloId(Permissao permissao) {
		try{
			return (Permissao) this.manager.createNamedQuery("Permissao.peloCodigo")
								 .setParameter("id", permissao.getIdPermissao())		
								 .getSingleResult();
		}catch (NoResultException nre){
			return null;
		}
	}
		
	@SuppressWarnings("unchecked")
	public List<Permissao> listaTodasPermisoes() {				
		Session session = this.manager.unwrap(Session.class);		
		return session.createCriteria(Permissao.class, "p")
								   .addOrder(Order.asc("p.nmJanela"))
								   .list();	
	}
		
	public List<Permissao> listaTodasPermissoes(PermissoesFiltro permissoesFiltro){
		List<Permissao> permissoes = new ArrayList<Permissao>();
		try{
			String filtro = "";
			if(permissoesFiltro.getNrTipoBusca() == 1){				
				filtro = " WHERE \n	id_permissao like ? \n";
			}else if(permissoesFiltro.getNrTipoBusca() == 2){
				filtro = " WHERE \n	nm_janela like ? \n";
			}
					
			String ordenacao = "";
			if(permissoesFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY nm_janela DESC \n";			
			}else if(permissoesFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY id_permissao ASC \n";			
			}else if(permissoesFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY id_permissao DESC \n";
			}else{
				ordenacao = "	ORDER BY nm_janela ASC \n";
			}
			
			String status = "";
			if(permissoesFiltro.getSnStatus() !=null && !"".equals(filtro)){
				status = " AND sn_status="+ permissoesFiltro.getSnStatus() + " \n";				
			}else if(permissoesFiltro.getSnStatus() !=null && "".equals(filtro)){
				status = " WHERE \n sn_status="+ permissoesFiltro.getSnStatus() + " \n";			
			}
			
			String setor = "";
			if( (permissoesFiltro.getPermissaoSetores() != null && !"".equals(filtro)) || (permissoesFiltro.getPermissaoSetores() != null && !"".equals(status)) ){
				setor = " AND nm_permissao_setor="+ permissoesFiltro.getPermissaoSetores() + " \n";						
			}else if( (permissoesFiltro.getPermissaoSetores() != null && "".equals(filtro)) || (permissoesFiltro.getPermissaoSetores() != null && "".equals(status)) ){
				setor = " WHERE nm_permissao_setor="+ permissoesFiltro.getPermissaoSetores() + " \n";
			}
						
			String query = 	"SELECT \n"+
					 		"	id_permissao, \n"+
					 		"	nm_janela, \n"+
					 		"	nm_permissao_setor, \n"+
					 		"	sn_status, \n"+
					 		"	dt_alteracao, \n"+
					 		"	dt_criacao \n"+
					 		"FROM \n"+
					 		"	db_agro_matriz.permissao \n"+					 			
								filtro +
								status +
								setor +
								ordenacao +
							"LIMIT "+ permissoesFiltro.getNrPrimeiroRegistro() +","+ permissoesFiltro.getNrQuantidadeRegistro();

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(permissoesFiltro.getNrTipoBusca() == 1 || permissoesFiltro.getNrTipoBusca() == 2){	
					stmtQuery.setString(1, "%"+ permissoesFiltro.getNmTermoBusca() +"%");
				}				
				
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){	
					Permissao permissao = new Permissao();
					permissao.setIdPermissao(rsQuery.getLong("id_permissao"));
					permissao.setNmJanela(rsQuery.getString("nm_janela"));
					permissao.setPermissaoSetor(PermissaoSetor.valueOf(rsQuery.getString("nm_permissao_setor")));
					permissao.setSnStatus(rsQuery.getBoolean("sn_status"));
					permissao.setDtAlteracao(rsQuery.getTimestamp("dt_alteracao"));
					permissao.setDtCriacao(rsQuery.getTimestamp("dt_criacao"));
									
					permissoes.add(permissao);
				}
				
				rsQuery.close();
				stmtQuery.close();
			}
			connQuery.close();			
		}catch(SQLException sql){			
			sql.printStackTrace();			
		}		
		return permissoes;
	}
	
	public int quantidadeListaTodasPermissoes(PermissoesFiltro permissoesFiltro){
		int resultado = 0;
		try{
			String filtro = "";
			if(permissoesFiltro.getNrTipoBusca() == 1){				
				filtro = " WHERE \n	id_permissao like ? \n";
			}else if(permissoesFiltro.getNrTipoBusca() == 2){
				filtro = " WHERE \n	nm_janela like ? \n";
			}
					
			String ordenacao = "";
			if(permissoesFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY nm_janela DESC \n";			
			}else if(permissoesFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY id_permissao ASC \n";			
			}else if(permissoesFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY id_permissao DESC \n";
			}else{
				ordenacao = "	ORDER BY nm_janela ASC \n";
			}
			
			String status = "";
			if(permissoesFiltro.getSnStatus() !=null && !"".equals(filtro)){
				status = " AND sn_status="+ permissoesFiltro.getSnStatus() + " \n";				
			}else if(permissoesFiltro.getSnStatus() !=null && "".equals(filtro)){
				status = " WHERE \n sn_status="+ permissoesFiltro.getSnStatus() + " \n";			
			}
			
			String setor = "";
			if( (permissoesFiltro.getPermissaoSetores() != null && !"".equals(filtro)) || (permissoesFiltro.getPermissaoSetores() != null && !"".equals(status)) ){
				setor = " AND nm_permissao_setor="+ permissoesFiltro.getPermissaoSetores() + " \n";						
			}else if( (permissoesFiltro.getPermissaoSetores() != null && "".equals(filtro)) || (permissoesFiltro.getPermissaoSetores() != null && "".equals(status)) ){
				setor = " WHERE nm_permissao_setor="+ permissoesFiltro.getPermissaoSetores() + " \n";
			}
						
			String query = 	"SELECT \n"+
					 		"	count( id_permissao ) total \n"+					 		
					 		"FROM \n"+
					 		"	db_agro_matriz.permissao \n"+					 			
								filtro +
								status +
								setor +
								ordenacao;
			
			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(permissoesFiltro.getNrTipoBusca() == 1 || permissoesFiltro.getNrTipoBusca() == 2){	
					stmtQuery.setString(1, "%"+ permissoesFiltro.getNmTermoBusca() +"%");
				}				
				
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){	
					resultado =  rsQuery.getInt("total");
				}
				
				rsQuery.close();
				stmtQuery.close();
			}
			connQuery.close();			
		}catch(SQLException sql){			
			sql.printStackTrace();			
		}		
		return resultado;
	}
}
