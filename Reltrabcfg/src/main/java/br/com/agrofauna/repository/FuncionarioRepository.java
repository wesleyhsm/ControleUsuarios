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

import br.com.agrofauna.filtro.FuncionarioFiltro;
import br.com.agrofauna.model.EstadoCivil;
import br.com.agrofauna.model.Funcionario;
import br.com.agrofauna.model.FuncionarioCargo;
import br.com.agrofauna.model.FuncionarioGrauInstrucao;
import br.com.agrofauna.model.FuncionarioTipoContrato;
import br.com.agrofauna.model.Sexo;
import br.com.agrofauna.model.Usuario;

public class FuncionarioRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	public Funcionario buscarFuncionario(Usuario usuario) {
		try{
			return (Funcionario) this.manager.createNamedQuery("Funcionario.nome")
								 .setParameter("idUsuario", usuario.getIdUsuario())
								 .getSingleResult();
		}catch (NoResultException nre){
			return null;
		}	
	}
	
	public List<Funcionario> listaTodosFuncionarios(FuncionarioFiltro funcionarioFiltro){
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		try{
			String filtro = "";
			if(funcionarioFiltro.getNrTipoBusca() == 1){				
				filtro = " WHERE \n	f.id_pessoa like ? \n";
			}else if(funcionarioFiltro.getNrTipoBusca() == 2){
				filtro = " WHERE \n	f.nm_funcionario like ? \n";
			}
					
			String ordenacao = "";
			if(funcionarioFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY f.nm_funcionario DESC \n";			
			}else if(funcionarioFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY f.id_pessoa ASC \n";			
			}else if(funcionarioFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY f.id_pessoa DESC \n";
			}else{
				ordenacao = "	ORDER BY f.nm_funcionario ASC \n";
			}
			
			String query = 	"SELECT \n"+ 
							"	 f.id_pessoa, \n"+ 
							"	 f.nm_funcionario, \n"+
							"	 p.nm_cnpj_cpf, \n"+
							"    f.nm_sexo, \n"+ 
							"    f.dt_exame_medico, \n"+ 
							"    f.sn_deficiente, \n"+
							"    f.nm_pai, \n"+
							"    f.nm_mae, \n"+
							"    f.nr_horario_semanal, \n"+
							"    f.nr_horario_mensal, \n"+ 
							"    f.nm_estado_civil, \n"+
							"    f.nm_tipo_contrato, \n"+
							"    f.sn_insalubridade, \n"+
							"    f.sn_convenio, \n"+
							"    f.nm_cbo, \n"+
							"    f.nm_grau_instrucao, \n"+ 
							"    f.sn_status, \n"+    
							"    f.dt_alteracao, \n"+
							"    f.dt_criacao, \n"+
							"    f.id_funcionario_cargo \n"+
							"FROM \n"+ 
							"	db_agro_matriz.funcionario f \n"+
							"	INNER JOIN db_agro_matriz.pessoa p ON p.id_pessoa=f.id_pessoa \n"+
								filtro +
								ordenacao +
							"LIMIT "+ funcionarioFiltro.getNrPrimeiroRegistro() +","+ funcionarioFiltro.getNrQuantidadeRegistro();

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(funcionarioFiltro.getNrTipoBusca() == 1 || funcionarioFiltro.getNrTipoBusca() == 2){	
					stmtQuery.setString(1, "%"+ funcionarioFiltro.getNmTermoBusca() +"%");
				}				
				
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){				
					Funcionario funcionario = new Funcionario();
					funcionario.setIdPessoa(rsQuery.getLong("id_pessoa"));
					funcionario.setNmCnpjCpf(rsQuery.getString("nm_cnpj_cpf"));
					funcionario.setNmFuncionario(rsQuery.getString("nm_funcionario"));
					funcionario.setSexo(Sexo.valueOf(rsQuery.getString("nm_sexo")));
					funcionario.setDtExameMedico(rsQuery.getTimestamp("dt_exame_medico"));
					funcionario.setSnDeficiente(rsQuery.getBoolean("sn_deficiente"));
					funcionario.setSnDeficiente(rsQuery.getBoolean("sn_deficiente"));
					funcionario.setNmPai(rsQuery.getString("nm_pai"));
					funcionario.setNmMae(rsQuery.getString("nm_mae"));
					funcionario.setNrHorarioSemanal(rsQuery.getInt("nr_horario_semanal"));
					funcionario.setNrHorarioMensal(rsQuery.getInt("nr_horario_mensal"));
					funcionario.setEstadoCivil(EstadoCivil.valueOf(rsQuery.getString("nm_estado_civil")));
					funcionario.setFuncionarioTipoContrato(FuncionarioTipoContrato.valueOf(rsQuery.getString("nm_tipo_contrato")));
					funcionario.setSnInsalubridade(rsQuery.getBoolean("sn_insalubridade"));
					funcionario.setSnConvenio(rsQuery.getBoolean("sn_convenio"));
					funcionario.setNmCbo(rsQuery.getString("nm_cbo"));
					funcionario.setNmCbo(rsQuery.getString("nm_cbo"));
					funcionario.setFuncionarioGrauInstrucao(FuncionarioGrauInstrucao.valueOf(rsQuery.getString("nm_grau_instrucao")));
					funcionario.setSnStatus(rsQuery.getBoolean("sn_status"));
					funcionario.setDtAlteracao(rsQuery.getTimestamp("dt_alteracao"));
					funcionario.setDtCriacao(rsQuery.getTimestamp("dt_criacao"));
					
					FuncionarioCargo funcionarioCargo = new FuncionarioCargo();
					funcionarioCargo.setIdFuncionarioCargo(rsQuery.getLong("id_funcionario_cargo"));
					funcionario.setFuncionarioCargo(funcionarioCargo);
					
					funcionarios.add(funcionario);
				}
				
				rsQuery.close();
				stmtQuery.close();
			}
			connQuery.close();			
		}catch(SQLException sql){			
			sql.printStackTrace();			
		}		
		return funcionarios;		
	}
	
	public int quantidadeListaTodosFuncionarios(FuncionarioFiltro funcionarioFiltro){
		int resultado = 0;
		try{
			String filtro = "";
			if(funcionarioFiltro.getNrTipoBusca() == 1){				
				filtro = " WHERE \n	f.id_pessoa like ? \n";
			}else if(funcionarioFiltro.getNrTipoBusca() == 2){
				filtro = " WHERE \n	f.nm_funcionario like ? \n";
			}
					
			String ordenacao = "";
			if(funcionarioFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY f.nm_funcionario DESC \n";			
			}else if(funcionarioFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY f.id_pessoa ASC \n";			
			}else if(funcionarioFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY f.id_pessoa DESC \n";
			}else{
				ordenacao = "	ORDER BY f.nm_funcionario ASC \n";
			}
			
			String query = 	"SELECT \n"+ 
							"	 count(f.id_pessoa) total \n"+							
							"FROM \n"+ 
							"	db_agro_matriz.funcionario f \n"+
							"	INNER JOIN db_agro_matriz.pessoa p ON p.id_pessoa=f.id_pessoa \n"+
								filtro +
								ordenacao;

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(funcionarioFiltro.getNrTipoBusca() == 1 || funcionarioFiltro.getNrTipoBusca() == 2){	
					stmtQuery.setString(1, "%"+ funcionarioFiltro.getNmTermoBusca() +"%");
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

	public Funcionario buscarFuncionarioLoginUsuario(Usuario usuario) {
		try{
			return (Funcionario) this.manager.createNamedQuery("Funcionario.funcionarioLoginUsuario")
								 .setParameter("idUsuario", usuario.getIdUsuario())
								 .getSingleResult();
		}catch (NoResultException nre){			
			return null;
		}
	}

	public Funcionario buscarNomeFuncionario(Funcionario funcionario) {		
		return this.manager.find(Funcionario.class, funcionario.getIdPessoa());
	}
}
