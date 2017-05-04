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
import br.com.agrofauna.filtro.LogFiltro;
import br.com.agrofauna.model.Log;
import br.com.agrofauna.model.LogStatus;
import br.com.agrofauna.model.Sistema;

public class LogRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
	
	public void salvarLog(Log log){
		manager.merge(log);		
	}
	
	public List<Log> buscarLog(){
		List<Log> logs = new ArrayList<Log>();
		
		return logs;
	}

	public int quantidadeListaTodosLogs(LogFiltro logFiltro) {
		int resultado = 0;
		try{
			String filtro = "";
			if(logFiltro.getNrTipoBusca() == 1){				
				filtro = " WHERE \n	id_pessoa like ? \n";
			}else if(logFiltro.getNrTipoBusca() == 2){
				filtro = " WHERE \n	id_registro like ? \n";
			}
					
			String ordenacao = "";
			if(logFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY id_log ASC \n";			
			}else if(logFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY dt_criacao ASC \n";			
			}else if(logFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY dt_criacao DESC \n";
			}else{
				ordenacao = "	ORDER BY id_log DESC \n";
			}
															
			String query = 	"SELECT \n"+
							"	count(id_log) total \n"+							
							"FROM \n"+
							"	db_agro_matriz.log \n"+					 			
								filtro +																
								ordenacao;

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(logFiltro.getNrTipoBusca() == 1 || logFiltro.getNrTipoBusca() == 2){	
					stmtQuery.setString(1, "%"+ logFiltro.getNmTermoBusca() +"%");
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

	public List<Log> listaTodosLogs(LogFiltro logFiltro) {
		List<Log> logs = new ArrayList<Log>();
		try{
			String filtro = "";
			if(logFiltro.getNrTipoBusca() == 1){				
				filtro = " WHERE \n	id_pessoa like ? \n";
			}else if(logFiltro.getNrTipoBusca() == 2){
				filtro = " WHERE \n	id_registro like ? \n";
			}
					
			String ordenacao = "";
			if(logFiltro.getNrOrdenacao() == 2){
				ordenacao = "	ORDER BY id_log ASC \n";			
			}else if(logFiltro.getNrOrdenacao() == 3){
				ordenacao = "	ORDER BY dt_criacao ASC \n";			
			}else if(logFiltro.getNrOrdenacao() == 4){
				ordenacao = "	ORDER BY dt_criacao DESC \n";
			}else{
				ordenacao = "	ORDER BY id_log DESC \n";
			}
															
			String query = 	"SELECT \n"+
							"	id_log, \n"+
							"	id_pessoa, \n"+
							"	id_registro, \n"+
							"	dt_criacao, \n"+
							"	nm_classe, \n"+
							"	nm_metodo, \n"+
							"	nm_tabela, \n"+
							"	nm_sistema, \n"+
							"	nm_log_status, \n"+
							"	nm_mensagem \n"+
							"FROM \n"+
							"	db_agro_matriz.log \n"+					 			
								filtro +																
								ordenacao +
							"LIMIT "+ logFiltro.getNrPrimeiroRegistro() +","+ logFiltro.getNrQuantidadeRegistro();

			System.out.println(query);
			Connection connQuery = new ConexaoBanco().getConnection();
			try(java.sql.PreparedStatement stmtQuery = connQuery.prepareStatement(query)){
				
				if(logFiltro.getNrTipoBusca() == 1 || logFiltro.getNrTipoBusca() == 2){	
					stmtQuery.setString(1, "%"+ logFiltro.getNmTermoBusca() +"%");
				}				
				
				ResultSet rsQuery = stmtQuery.executeQuery();								
				while(rsQuery.next()){
					Log log = new Log();
					log.setDtCriacao(rsQuery.getTimestamp("dt_criacao"));
					log.setIdLog(rsQuery.getLong("id_log"));
					log.setIdPessoa(rsQuery.getLong("id_pessoa"));
					log.setIdRegistro(rsQuery.getLong("id_registro"));
					log.setLogStatus(LogStatus.valueOf(rsQuery.getString("nm_log_status")));
					log.setMnClasse(rsQuery.getString("nm_classe"));
					log.setNmMensagem(rsQuery.getString("nm_mensagem"));
					log.setNmMetodo(rsQuery.getString("nm_metodo"));
					log.setNmTabela(rsQuery.getString("nm_tabela"));
					log.setSistema(Sistema.valueOf(rsQuery.getString("nm_sistema")));
					
					logs.add(log);
				}
				
				rsQuery.close();
				stmtQuery.close();
			}
			connQuery.close();			
		}catch(SQLException sql){			
			sql.printStackTrace();			
		}		
		return logs;
	}
}
