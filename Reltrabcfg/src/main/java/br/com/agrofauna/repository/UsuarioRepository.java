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

import br.com.agrofauna.banco.ConexaoBanco;
import br.com.agrofauna.filtro.UsuariosFiltro;
import br.com.agrofauna.model.Funcionario;
import br.com.agrofauna.model.Usuario;

public class UsuarioRepository  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	public Usuario salvar(Usuario usuario) {		 		
		return this.manager.merge(usuario); 
	}
	
	public Usuario verificaExisteUsuario(Usuario usuario) {
		try{
			return (Usuario) this.manager.createNamedQuery("Usuario.existeUsuario")
								 .setParameter("nomeUsuario", usuario.getNmUsuario())
								 .getSingleResult();
		}catch (NoResultException nre){
			return null;
		}	
	}
	
	public Usuario verificaUsuario(Usuario usuario) {
		try{
			return (Usuario) this.manager.createNamedQuery("Usuario.verificaUsuario")
								 .setParameter("idUsuario", usuario.getIdUsuario())
								 .getSingleResult();
		}catch (NoResultException nre){
			return null;
		}	
	}
	
	public Usuario buscarUsuarioPeloId(Usuario usuario) {
		try{
			return (Usuario) this.manager.createNamedQuery("Usuario.peloCodigo")
								 .setParameter("idUsuario", usuario.getIdUsuario())		
								 .getSingleResult();
		}catch (NoResultException nre){
			return null;
		}
	}
	
	public void excluir(Usuario usuario) {
		this.manager.remove( this.manager.find(Usuario.class, usuario.getIdUsuario()) );		
	}
				
	public int quantidadeListaTodosUsuarios(UsuariosFiltro usuariosFiltro){
		int resultado = 0;
		try{
			String filtro = "";
			if(usuariosFiltro.getNrTipoBusca() == 1){				
				filtro = " WHERE \n	id_usuario like ? \n";
			}else if(usuariosFiltro.getNrTipoBusca() == 2){
				filtro = " WHERE \n	nm_usuario like ? \n";
			}
					
			String ordenacao = "";
			if(usuariosFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY nm_usuario DESC \n";			
			}else if(usuariosFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY id_usuario ASC \n";			
			}else if(usuariosFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY id_usuario DESC \n";
			}else{
				ordenacao = "	ORDER BY nm_usuario ASC \n";
			}
			
			String status = "";
			if(usuariosFiltro.getSnStatus() !=null && !"".equals(filtro)){
				status = " AND sn_status="+ usuariosFiltro.getSnStatus() + " \n";				
			}else if(usuariosFiltro.getSnStatus() !=null && "".equals(filtro)){
				status = " WHERE \n sn_status="+ usuariosFiltro.getSnStatus() + " \n";			
			}
									
			String query = 	"SELECT "+
					 		"	count(id_usuario) total \n"+					 		
					 		"FROM "+
					 		"	db_agro_matriz.usuario \n"+					 			
								filtro +
								status +								
								ordenacao;

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(usuariosFiltro.getNrTipoBusca() == 1 || usuariosFiltro.getNrTipoBusca() == 2){	
					stmtQuery.setString(1, "%"+ usuariosFiltro.getNmTermoBusca() +"%");
				}				
				
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){
					resultado = rsQuery.getInt("total");
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

	public List<Usuario> listaTodosUsuarios(UsuariosFiltro usuariosFiltro){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try{
			String filtro = "";
			if(usuariosFiltro.getNrTipoBusca() == 1){				
				filtro = " WHERE \n	id_usuario like ? \n";
			}else if(usuariosFiltro.getNrTipoBusca() == 2){
				filtro = " WHERE \n	nm_usuario like ? \n";
			}
					
			String ordenacao = "";
			if(usuariosFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY nm_usuario DESC \n";			
			}else if(usuariosFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY id_usuario ASC \n";			
			}else if(usuariosFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY id_usuario DESC \n";
			}else{
				ordenacao = "	ORDER BY nm_usuario ASC \n";
			}
			
			String status = "";
			if(usuariosFiltro.getSnStatus() !=null && !"".equals(filtro)){
				status = " AND sn_status="+ usuariosFiltro.getSnStatus() + " \n";				
			}else if(usuariosFiltro.getSnStatus() !=null && "".equals(filtro)){
				status = " WHERE \n sn_status="+ usuariosFiltro.getSnStatus() + " \n";			
			}
									
			String query = 	"SELECT "+
					 		"	id_usuario, "+
					 		"	id_pessoa, "+
					 		"	nm_usuario, "+
					 		"	sn_status, "+
					 		"	dt_alteracao, "+
					 		"	dt_criacao "+
					 		"FROM "+
					 		"	db_agro_matriz.usuario \n"+					 			
								filtro +
								status +								
								ordenacao +
							"LIMIT "+ usuariosFiltro.getNrPrimeiroRegistro() +","+ usuariosFiltro.getNrQuantidadeRegistro();

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(usuariosFiltro.getNrTipoBusca() == 1 || usuariosFiltro.getNrTipoBusca() == 2){	
					stmtQuery.setString(1, "%"+ usuariosFiltro.getNmTermoBusca() +"%");
				}				
				
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){
					Usuario usuario = new Usuario();
					usuario.setIdUsuario(rsQuery.getLong("id_usuario"));
					usuario.setNmUsuario(rsQuery.getString("nm_usuario"));
					usuario.setSnStatus(rsQuery.getBoolean("sn_status"));				
					usuario.setDtAlteracao(rsQuery.getTimestamp("dt_alteracao"));
					usuario.setDtCriacao(rsQuery.getTimestamp("dt_criacao"));
					
					Funcionario funcionario = new Funcionario();
					funcionario.setIdPessoa(rsQuery.getLong("id_pessoa"));
					usuario.setFuncionario(funcionario);
										
					usuarios.add(usuario);
				}
				
				rsQuery.close();
				stmtQuery.close();
			}
			connQuery.close();			
		}catch(SQLException sql){			
			sql.printStackTrace();			
		}		
		return usuarios;
	}

	public void limparUsuarioFuncionario(Funcionario funcionario) {
		try{								
			String query = 	"update db_agro_matriz.usuario set id_pessoa=null where id_pessoa=?";

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){									
				stmtQuery.setLong(1, funcionario.getIdPessoa());
				stmtQuery.execute();								
				stmtQuery.close();
			}
			connQuery.close();			
		}catch(SQLException sql){			
			sql.printStackTrace();			
		}			
	}
	
	public int quantidadeListaTodosUsuarios2(UsuariosFiltro usuariosFiltro){
		int resultado = 0;
		try{
			String filtro = "";
			if(usuariosFiltro.getNrTipoBusca() == 1){				
				filtro = " AND \n	id_usuario like ? \n";
			}else if(usuariosFiltro.getNrTipoBusca() == 2){
				filtro = " AND \n	nm_usuario like ? \n";
			}
					
			String ordenacao = "";
			if(usuariosFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY nm_usuario DESC \n";			
			}else if(usuariosFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY id_usuario ASC \n";			
			}else if(usuariosFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY id_usuario DESC \n";
			}else{
				ordenacao = "	ORDER BY nm_usuario ASC \n";
			}
			
			String status = "";
			if(usuariosFiltro.getSnStatus() !=null && !"".equals(filtro)){
				status = " AND sn_status="+ usuariosFiltro.getSnStatus() + " \n";				
			}else if(usuariosFiltro.getSnStatus() !=null && "".equals(filtro)){
				status = " AND \n sn_status="+ usuariosFiltro.getSnStatus() + " \n";			
			}
									
			String query = 	"SELECT "+
					 		"	count(id_usuario) total \n"+					 		
					 		"FROM "+
					 		"	db_agro_matriz.usuario \n"+
					 		"WHERE "+
					 		"	id_pessoa is null \n"+
								filtro +
								status +								
								ordenacao;

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(usuariosFiltro.getNrTipoBusca() == 1 || usuariosFiltro.getNrTipoBusca() == 2){	
					stmtQuery.setString(1, "%"+ usuariosFiltro.getNmTermoBusca() +"%");
				}				
				
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){
					resultado = rsQuery.getInt("total");
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

	public List<Usuario> listaTodosUsuarios2(UsuariosFiltro usuariosFiltro){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try{
			String filtro = "";
			if(usuariosFiltro.getNrTipoBusca() == 1){				
				filtro = " AND \n	id_usuario like ? \n";
			}else if(usuariosFiltro.getNrTipoBusca() == 2){
				filtro = " AND \n	nm_usuario like ? \n";
			}
					
			String ordenacao = "";
			if(usuariosFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY nm_usuario DESC \n";			
			}else if(usuariosFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY id_usuario ASC \n";			
			}else if(usuariosFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY id_usuario DESC \n";
			}else{
				ordenacao = "	ORDER BY nm_usuario ASC \n";
			}
			
			String status = "";
			if(usuariosFiltro.getSnStatus() !=null && !"".equals(filtro)){
				status = " AND sn_status="+ usuariosFiltro.getSnStatus() + " \n";				
			}else if(usuariosFiltro.getSnStatus() !=null && "".equals(filtro)){
				status = " AND \n sn_status="+ usuariosFiltro.getSnStatus() + " \n";			
			}
									
			String query = 	"SELECT "+
					 		"	id_usuario, "+
					 		"	id_pessoa, "+
					 		"	nm_usuario, "+
					 		"	sn_status, "+
					 		"	dt_alteracao, "+
					 		"	dt_criacao "+
					 		"FROM "+
					 		"	db_agro_matriz.usuario \n"+
					 		"WHERE "+
					 		"	id_pessoa is null \n"+
								filtro +
								status +								
								ordenacao +
							"LIMIT "+ usuariosFiltro.getNrPrimeiroRegistro() +","+ usuariosFiltro.getNrQuantidadeRegistro();

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(usuariosFiltro.getNrTipoBusca() == 1 || usuariosFiltro.getNrTipoBusca() == 2){	
					stmtQuery.setString(1, "%"+ usuariosFiltro.getNmTermoBusca() +"%");
				}				
				
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){
					Usuario usuario = new Usuario();
					usuario.setIdUsuario(rsQuery.getLong("id_usuario"));
					usuario.setNmUsuario(rsQuery.getString("nm_usuario"));
					usuario.setSnStatus(rsQuery.getBoolean("sn_status"));				
					usuario.setDtAlteracao(rsQuery.getTimestamp("dt_alteracao"));
					usuario.setDtCriacao(rsQuery.getTimestamp("dt_criacao"));
					
					Funcionario funcionario = new Funcionario();
					funcionario.setIdPessoa(rsQuery.getLong("id_pessoa"));
					usuario.setFuncionario(funcionario);
										
					usuarios.add(usuario);
				}
				
				rsQuery.close();
				stmtQuery.close();
			}
			connQuery.close();			
		}catch(SQLException sql){			
			sql.printStackTrace();			
		}		
		return usuarios;
	}
}
