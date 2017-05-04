package br.com.agrofauna.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.agrofauna.filtro.PermissoesFiltro;
import br.com.agrofauna.model.Permissao;
import br.com.agrofauna.model.Usuario;
import br.com.agrofauna.model.UsuarioPermissao;
import br.com.agrofauna.repository.PermissaoRepository;
import br.com.agrofauna.repository.UsuarioPermissaoRepository;

public class PermissaoService implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@Inject
	private PermissaoRepository permissaoRepository;
	
	@Inject
	private UsuarioPermissaoRepository usuarioPermissaoRepository; 
	
	@Transactional
	public Permissao salvar(Permissao permissao) throws NegocioException {		
		if(this.permissaoRepository.verificaExistePermissao(permissao) != null && permissao.getIdPermissao() <= 0){
			throw new NegocioException("Permissão nome janela ("+ permissao.getNmJanela() + ") já existe.");
		}
		
		permissao = this.permissaoRepository.salvar(permissao);
				
		for(Usuario user: this.usuarioPermissaoRepository.todosUsuariosPermissao()){
			UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
			usuarioPermissao.setPermissao(permissao);
			usuarioPermissao.setUsuario(user);
			
			this.usuarioPermissaoRepository.salvar(usuarioPermissao);
		}
		
		return permissao;		
	}
	
	@Transactional
	public Permissao editar(Permissao permissao){
		return this.permissaoRepository.salvar(permissao);
	}
	
	@Transactional
	public void excluir(Permissao permissao) throws NegocioException {
		if(!this.usuarioPermissaoRepository.buscarPemissao(permissao).isEmpty()){
			throw new NegocioException("Erro permissão ("+ permissao.getIdPermissao() + ") não pode ser excluida, pois esta em uso.");
		}
		
		this.permissaoRepository.excluir(permissao);
	}
	
	public Permissao buscarPermissaoPeloId(Permissao permissao) throws NegocioException {
		return this.permissaoRepository.buscarPermissaoPeloId(permissao);
	}
	
	public List<Permissao> listaTodasPermisoes() {
		return this.permissaoRepository.listaTodasPermisoes();
	}
		
	public LazyDataModel<Permissao> listaTodasPermissoes(PermissoesFiltro permissoesFiltro){
		 return new LazyDataModel<Permissao>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Permissao> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, Object> filters) {										
				permissoesFiltro.setNrPrimeiroRegistro(first);
				permissoesFiltro.setNrQuantidadeRegistro(pageSize);												
								
				setRowCount(permissaoRepository.quantidadeListaTodasPermissoes(permissoesFiltro));								
				return permissaoRepository.listaTodasPermissoes(permissoesFiltro);			
			}
		};		
	}
	
	public List<Permissao> listaPermisoesUsuario(Usuario usuario) {
		return this.permissaoRepository.listaTodasPermisoes();
	}
}
