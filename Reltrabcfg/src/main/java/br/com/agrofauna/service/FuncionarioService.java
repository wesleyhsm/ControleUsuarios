package br.com.agrofauna.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.agrofauna.filtro.FuncionarioFiltro;
import br.com.agrofauna.model.Funcionario;
import br.com.agrofauna.model.Usuario;
import br.com.agrofauna.repository.FuncionarioRepository;

public class FuncionarioService  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FuncionarioRepository funcionarioRepository;
	
	public LazyDataModel<Funcionario> listaTodasFuncionarios(FuncionarioFiltro funcionarioFiltro) {
		 return new LazyDataModel<Funcionario>() {				
				private static final long serialVersionUID = 1L;
				
				@Override
				public List<Funcionario> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, Object> filters) {										
					funcionarioFiltro.setNrPrimeiroRegistro(first);
					funcionarioFiltro.setNrQuantidadeRegistro(pageSize);												
									
					setRowCount(funcionarioRepository.quantidadeListaTodosFuncionarios(funcionarioFiltro));								
					return funcionarioRepository.listaTodosFuncionarios(funcionarioFiltro);			
				}
			};	
	}
	
	public Funcionario buscarFuncionario(Usuario usuario){
		return this.funcionarioRepository.buscarFuncionario(usuario);
	}

	public Funcionario buscarFuncionarioLoginUsuario(Usuario usuario) {		
		return this.funcionarioRepository.buscarFuncionarioLoginUsuario(usuario);				
		
	}
	
	public Funcionario buscarNomeFuncionario(Funcionario funcionario){
		return this.funcionarioRepository.buscarNomeFuncionario(funcionario);
	}
}
