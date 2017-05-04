package br.com.agrofauna.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.agrofauna.filtro.LogFiltro;
import br.com.agrofauna.model.Funcionario;
import br.com.agrofauna.model.Log;
import br.com.agrofauna.model.LogStatus;
import br.com.agrofauna.model.Sistema;
import br.com.agrofauna.repository.LogRepository;

public class LogService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LogRepository logRepository;
	
	@Inject
	private FuncionarioService funcionarioService;
	
	@Transactional
	public void salvarLog(Long idPessoa, Long idRegistro, LogStatus logStatus, String nmClasse, String nmMetodo, String nmTabela, Sistema sistema, String nmMensagem){
		
		if(idRegistro != null){
			Log log =  new Log();
			log.setIdPessoa(idPessoa);
			log.setIdRegistro(idRegistro);
			log.setLogStatus(logStatus);
			log.setMnClasse(nmClasse);
			log.setNmMetodo(nmMetodo);
			log.setNmTabela(nmTabela);
			log.setSistema(sistema);
			log.setNmMensagem(nmMensagem);
			
			logRepository.salvarLog(log);
		}	
	}
	
	public LazyDataModel<Log> listaTodasLogs(LogFiltro logFiltro){
		 return new LazyDataModel<Log>() {			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Log> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, Object> filters) {										
				logFiltro.setNrPrimeiroRegistro(first);
				logFiltro.setNrQuantidadeRegistro(pageSize);												
								
				setRowCount(logRepository.quantidadeListaTodosLogs(logFiltro));								
				return logRepository.listaTodosLogs(logFiltro);			
			}
		};		
	}

	public String buscarNomeFuncionario(Log log) {
		Funcionario funcionario  = new Funcionario();
		funcionario.setIdPessoa(log.getIdPessoa());
		if(log.getIdPessoa() != null && log.getIdPessoa() > 0){
			return this.funcionarioService.buscarNomeFuncionario(funcionario).getNmFuncionario();
		}else{
			return "";
		}	
	}	
}
