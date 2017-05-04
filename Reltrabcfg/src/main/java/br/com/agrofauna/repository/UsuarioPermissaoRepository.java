package br.com.agrofauna.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.agrofauna.banco.ConexaoBanco;
import br.com.agrofauna.controller.LoginBean;
import br.com.agrofauna.model.Permissao;
import br.com.agrofauna.model.Usuario;
import br.com.agrofauna.model.UsuarioPermissao;

public class UsuarioPermissaoRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	public UsuarioPermissao salvar(UsuarioPermissao usuarioPermissao) {		 		
		return this.manager.merge(usuarioPermissao); 
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioPermissao> buscarPemissao(Permissao permissao){
		return this.manager.createNamedQuery("UsuarioPermissao.buscarPermissao")
				 .setParameter("idPermissao", permissao.getIdPermissao())		
				 .getResultList();		
	} 
	
	@SuppressWarnings("unchecked")
	public List<UsuarioPermissao> carregarPermissoesUsuario(Usuario usuario){
		return this.manager.createNamedQuery("UsuarioPermissao.listPermissaoUsuario")
				 .setParameter("idUsuario", usuario.getIdUsuario())		
				 .getResultList();
	}

	public void excluir(Usuario usuario){
		this.manager.createNamedQuery("UsuarioPermissao.excluirPermissaoUsuario")
				.setParameter("idUsuario", usuario.getIdUsuario())		
				.executeUpdate();
	}
	
	public UsuarioPermissao pesquisarPermissao(LoginBean loginBean, Long idPermissao) {		
		UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
		usuarioPermissao.setSnAdicionar(false);
		usuarioPermissao.setSnEditar(false);
		usuarioPermissao.setSnRemover(false);
		usuarioPermissao.setSnVisualizar(false);
				
		try{
			String query = 	"SELECT sn_adicionar, sn_editar, sn_remover, sn_visualizar FROM db_agro_matriz.usuario_permissao WHERE id_permissao=? and id_usuario=?";

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){					
				stmtQuery.setLong(1, idPermissao);		
				stmtQuery.setLong(2, loginBean.getFuncionario().getUsuario().getIdUsuario());
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){					
					usuarioPermissao.setSnAdicionar(rsQuery.getBoolean("sn_adicionar"));
					usuarioPermissao.setSnEditar(rsQuery.getBoolean("sn_editar"));
					usuarioPermissao.setSnRemover(rsQuery.getBoolean("sn_remover"));
					usuarioPermissao.setSnVisualizar(rsQuery.getBoolean("sn_visualizar"));										
				}
				rsQuery.close();
				stmtQuery.close();
			}
			connQuery.close();
		}catch(SQLException sql){
			sql.printStackTrace();		
		}
		return usuarioPermissao;
	}
	
	public List<Usuario> todosUsuariosPermissao(){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try{
			String query = 	"SELECT distinct(id_usuario) id_usuario FROM db_agro_matriz.usuario_permissao";

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){				
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){					
					Usuario user =  new Usuario();
					user.setIdUsuario(rsQuery.getLong("id_usuario"));
					
					usuarios.add(user);
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