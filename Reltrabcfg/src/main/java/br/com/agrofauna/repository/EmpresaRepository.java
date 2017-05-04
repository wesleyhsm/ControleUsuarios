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
import br.com.agrofauna.filtro.EmpresaFiltro;
import br.com.agrofauna.model.Email;
import br.com.agrofauna.model.Empresa;
import br.com.agrofauna.model.Endereco;
import br.com.agrofauna.model.NfeConfiguracao;
import br.com.agrofauna.model.Telefone;
import br.com.agrofauna.model.Usuario;

public class EmpresaRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
		
	public Empresa salvar(Empresa empresa) {		
		return manager.merge(empresa);
	}
	
	public Empresa verificaExisteCnpj(Empresa empresa) {
		try{
			return (Empresa) manager.createNamedQuery("Empresa.existeCnpj")
								 	.setParameter("cnpjcpf", empresa.getNmCnpjCpf())		
			  	 					.getSingleResult();
		}catch (NoResultException nre){
			return null;
		}		
	}
	
	public int quantidadeListaTodasEmpresas(EmpresaFiltro empresaFiltro){
		int resultado = 0;		
		try{									
			String filtro = "";
			if(empresaFiltro.getNrTipoBusca() == 1){				
				filtro = " WHERE \n	e.id_pessoa like ? \n";
			}else if(empresaFiltro.getNrTipoBusca() == 2){
				filtro = " WHERE \n	e.nm_razao_social like ? \n";
			}else if(empresaFiltro.getNrTipoBusca() == 3){
				filtro = " WHERE \n	e.nm_cnpj_cpf like ? \n";
			}
			
			String status = "";
			if(empresaFiltro.getSnStatus() !=null && !"".equals(filtro)){
				status = " AND e.sn_status="+ empresaFiltro.getSnStatus() + " \n";				
			}else if(empresaFiltro.getSnStatus() !=null && "".equals(filtro)){
				status = " WHERE \n e.sn_status="+ empresaFiltro.getSnStatus() + " \n";			
			}			
					
			String ordenacao = "";
			if(empresaFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY e.nm_razao_social DESC \n";			
			}else if(empresaFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY e.id_pessoa ASC \n";			
			}else if(empresaFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY e.id_pessoa DESC \n";
			}else{
				ordenacao = "	ORDER BY e.nm_razao_social ASC \n";
			}
			
			String query = 	"SELECT "+
							"	count( e.id_pessoa ) total \n"+							
							"FROM \n"+
							"	db_agro_matriz.empresa e \n"+
							"	INNER JOIN db_agro_matriz.pessoa p ON p.id_pessoa=e.id_pessoa \n"+
								filtro +
								status +
								ordenacao; 							

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(empresaFiltro.getNrTipoBusca() == 1 || empresaFiltro.getNrTipoBusca() == 2 || empresaFiltro.getNrTipoBusca() == 3){	
					stmtQuery.setString(1, "%"+ empresaFiltro.getNmTermoBusca() +"%");
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
	
	public List<Empresa> listaTodasEmpresas(EmpresaFiltro empresaFiltro){
		List<Empresa> empresas = new ArrayList<Empresa>();		
		try{									
			String filtro = "";
			if(empresaFiltro.getNrTipoBusca() == 1){				
				filtro = " WHERE \n	e.id_pessoa like ? \n";
			}else if(empresaFiltro.getNrTipoBusca() == 2){
				filtro = " WHERE \n	e.nm_razao_social like ? \n";
			}else if(empresaFiltro.getNrTipoBusca() == 3){
				filtro = " WHERE \n	e.nm_cnpj_cpf like ? \n";
			}
			
			String status = "";
			if(empresaFiltro.getSnStatus() !=null && !"".equals(filtro)){
				status = " AND sn_status="+ empresaFiltro.getSnStatus() + " \n";				
			}else if(empresaFiltro.getSnStatus() !=null && "".equals(filtro)){
				status = " WHERE \n sn_status="+ empresaFiltro.getSnStatus() + " \n";			
			}			
					
			String ordenacao = "";
			if(empresaFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY e.nm_razao_social DESC \n";			
			}else if(empresaFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY e.id_pessoa ASC \n";			
			}else if(empresaFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY e.id_pessoa DESC \n";
			}else{
				ordenacao = "	ORDER BY e.nm_razao_social ASC \n";
			}
			
			String query = 	"SELECT "+
							"	e.id_pessoa, \n"+
							"	e.nm_razao_social, \n"+
							"	p.nm_cnpj_cpf, \n"+
							"	e.nm_fantasia, \n"+
							"	e.nm_filial, \n"+
							"	e.nm_inscricao_estadual, \n"+
							"	e.sn_status, \n"+
							"	e.dt_fundacao, \n"+
							"	e.dt_alteracao, \n"+
							"	e.dt_criacao \n"+
							"FROM \n"+
							"	db_agro_matriz.empresa e \n"+
							"	INNER JOIN db_agro_matriz.pessoa p ON p.id_pessoa=e.id_pessoa \n"+
								filtro +
								status +
								ordenacao +
							"LIMIT "+ empresaFiltro.getNrPrimeiroRegistro() +","+ empresaFiltro.getNrQuantidadeRegistro();

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(empresaFiltro.getNrTipoBusca() == 1 || empresaFiltro.getNrTipoBusca() == 2 || empresaFiltro.getNrTipoBusca() == 3){	
					stmtQuery.setString(1, "%"+ empresaFiltro.getNmTermoBusca() +"%");
				}				
				
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){				
					Empresa empresa = new Empresa();
					empresa.setDtAlteracao(rsQuery.getTimestamp("dt_alteracao"));
					empresa.setDtCriacao(rsQuery.getTimestamp("dt_criacao"));
					empresa.setDtFundacao(rsQuery.getDate("dt_fundacao"));
					empresa.setSnStatus(rsQuery.getBoolean("sn_status"));
					empresa.setNmCnpjCpf(rsQuery.getString("nm_cnpj_cpf"));
					empresa.setNmFantasia(rsQuery.getString("nm_fantasia"));
					empresa.setNmFilial(rsQuery.getString("nm_filial"));
					empresa.setNmInscricaoEstadual(rsQuery.getString("nm_inscricao_estadual"));
					empresa.setNmRazaoSocial(rsQuery.getString("nm_razao_social"));
					empresa.setIdPessoa(rsQuery.getLong("id_pessoa"));
					
					empresas.add(empresa);
				}
				
				rsQuery.close();
				stmtQuery.close();
			}
			connQuery.close();			
		}catch(SQLException sql){			
			sql.printStackTrace();			
		}		
		return empresas;		
	}
	
	
	public Empresa buscarEmpresaPeloId(Empresa empresa) {
		try{
			return (Empresa) manager.createNamedQuery("Empresa.id")
								    .setParameter("idPessoa", empresa.getIdPessoa())		
			 					 	.getSingleResult();
		}catch (NoResultException nre){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Endereco> buscarEnderecoPeloId(Empresa empresa) {
		try{
			return manager.createNamedQuery("Empresa.endereco")
						  .setParameter("idPessoa", empresa.getIdPessoa())		
			 			  .getResultList();
		}catch (NoResultException nre){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Telefone> buscarTelefonePeloId(Empresa empresa) {
		try{
			return manager.createNamedQuery("Empresa.telefone")
						  .setParameter("idPessoa", empresa.getIdPessoa())		
			 			  .getResultList();
		}catch (NoResultException nre){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Email> buscarEmailPeloId(Empresa empresa) {
		try{
			return manager.createNamedQuery("Empresa.email")
						  .setParameter("idPessoa", empresa.getIdPessoa())		
			 			  .getResultList();
		}catch (NoResultException nre){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<NfeConfiguracao> buscarNfeConfiguracaoPeloId(Empresa empresa) {
		try{
			return manager.createNamedQuery("Empresa.nfeConfiguraor")
						  .setParameter("idPessoa", empresa.getIdPessoa())		
			 			  .getResultList();
		}catch (NoResultException nre){
			return null;
		}
	}

	public void excluirEnderecos(Empresa empresa) {
		this.manager.createNamedQuery("Empresa.enderecoExcluir")
					.setParameter("idPessoa", empresa.getIdPessoa())		
					.executeUpdate();
	}

	public void excluirTelefones(Empresa empresa) {
		this.manager.createNamedQuery("Empresa.telefoneExcluir")
					.setParameter("idPessoa", empresa.getIdPessoa())		
					.executeUpdate();
	}

	public void excluirEmails(Empresa empresa) {
		this.manager.createNamedQuery("Empresa.emailExcluir")
					.setParameter("idPessoa", empresa.getIdPessoa())		
					.executeUpdate();		
	}

	public void excluirNfeConfigurador(Empresa empresa) {
		this.manager.createNamedQuery("Empresa.nfeConfiguraorExcluir")
					.setParameter("idPessoa", empresa.getIdPessoa())		
					.executeUpdate();
	}

	public void excluir(Empresa empresa) {		
		this.manager.remove( this.manager.find(Empresa.class, empresa.getIdPessoa()) );
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> buscarFuncionarioEmpresas(Usuario usuario) {
		try{
			return manager.createNamedQuery("Empresa.funcionarioEmpresas")
						  .setParameter("idUsuario", usuario.getIdUsuario())		
			 			  .getResultList();
		}catch (NoResultException nre){
			return null;
		}
	}	
}
