package br.com.agrofauna.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.agrofauna.filtro.EmpresaFiltro;
import br.com.agrofauna.filtro.FuncionarioFiltro;
import br.com.agrofauna.filtro.UsuariosFiltro;
import br.com.agrofauna.model.Empresa;
import br.com.agrofauna.model.Funcionario;
import br.com.agrofauna.model.Login;
import br.com.agrofauna.model.Permissao;
import br.com.agrofauna.model.Usuario;
import br.com.agrofauna.model.UsuarioEmpresa;
import br.com.agrofauna.model.UsuarioPermissao;
import br.com.agrofauna.repository.UsuarioRepository;

public class UsuariosService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private EmpresaService empresaService;
	
	@Inject
	private FuncionarioService funcionarioService;
	
	@Inject
	private PermissaoService permissaoService;
	
	@Inject
	private UsuarioPermissaoService usuarioPermissaoService;
	
	@Inject
	private UsuarioEmpresaService usuarioEmpresaService;
	
	@Inject
	private LoginService loginService;
	
	public List<UsuarioPermissao> carregarPermissoes(){
		List<UsuarioPermissao> usuarioPermissoes = new ArrayList<UsuarioPermissao>();
		
		List<Permissao> permissoes = this.permissaoService.listaTodasPermisoes();		
		for(Permissao permissao : permissoes){			
			UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
			usuarioPermissao.setPermissao(permissao);
			usuarioPermissoes.add(usuarioPermissao);
		}
		
		return usuarioPermissoes;
	}
	
	@Transactional
	public Usuario salvar(Usuario usuario, List<UsuarioPermissao> usuarioPermissoes) throws NegocioException {
				
		if(this.usuarioRepository.verificaExisteUsuario(usuario) != null && usuario.getIdUsuario() <= 0){
			throw new NegocioException("Usuario nome ("+ usuario.getNmUsuario() + ") já existe.");
		}
		
		usuario = this.usuarioRepository.salvar(usuario);
		
		for(UsuarioPermissao usuarioPermissao : usuarioPermissoes){
			usuarioPermissao.setUsuario(usuario);
			this.usuarioPermissaoService.salvar(usuarioPermissao);
		}
		
		return usuario;
	}

	public LazyDataModel<Usuario> listaTodasUsuarios(UsuariosFiltro usuariosFiltro){
		 return new LazyDataModel<Usuario>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, Object> filters) {										
				usuariosFiltro.setNrPrimeiroRegistro(first);
				usuariosFiltro.setNrQuantidadeRegistro(pageSize);												
								
				setRowCount(usuarioRepository.quantidadeListaTodosUsuarios(usuariosFiltro));								
				return usuarioRepository.listaTodosUsuarios(usuariosFiltro);			
			}
		};		
	}
	
	public LazyDataModel<Usuario> listaTodasUsuarios2(UsuariosFiltro usuariosFiltro){
		 return new LazyDataModel<Usuario>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, Object> filters) {										
				usuariosFiltro.setNrPrimeiroRegistro(first);
				usuariosFiltro.setNrQuantidadeRegistro(pageSize);												
								
				setRowCount(usuarioRepository.quantidadeListaTodosUsuarios2(usuariosFiltro));								
				return usuarioRepository.listaTodosUsuarios2(usuariosFiltro);			
			}
		};		
	}
	
	public Usuario buscarUsuarioPeloId(Usuario usuario) throws NegocioException {
		return this.usuarioRepository.buscarUsuarioPeloId(usuario);
	}
	
	public List<UsuarioPermissao> carregarPermissoesUsuario(Usuario usuario){
		return this.usuarioPermissaoService.carregarPermissoesUsuario(usuario);
	}
	
	@Transactional
	public void excluirUsuario(Usuario usuario) throws NegocioException {
				
		if(this.usuarioRepository.verificaUsuario(usuario) != null){
			throw new NegocioException("Erro usuario ("+ usuario.getNmUsuario() + ") não pode ser excluido, pois esta em uso.");
		}
		
		limparUsuarioEmpresa(usuario);
		this.usuarioPermissaoService.excluir(usuario);
		this.usuarioRepository.excluir(usuario);
	}

	public LazyDataModel<Empresa> listaTodasEmpresas(EmpresaFiltro empresaFiltro) {
		 return this.empresaService.listaTodasEmpresas(empresaFiltro);				 
	}

	public LazyDataModel<Funcionario> listaTodasFuncionarios(FuncionarioFiltro funcionarioFiltro) {
		 return this.funcionarioService.listaTodasFuncionarios(funcionarioFiltro);
	}
	
	@Transactional
	public void salvarRelacionarUsuario(Funcionario funcionario, List<Empresa> empresas, Usuario usuario, Login login) throws NegocioException {
		
		if(funcionario == null){
			throw new NegocioException("Erro selecionar funcionario.");
		}
		
		if(empresas.isEmpty()){
			throw new NegocioException("Erro selecionar empresa.");
		}
		
		if(usuario == null){
			throw new NegocioException("Erro selecionar usuario.");
		}
		
		if(usuario.getFuncionario().getIdPessoa() == null || usuario.getFuncionario().getIdPessoa() <= 0){
			
			limparUsuarioEmpresa(usuario);
			limparUsuarioFuncionario(funcionario);
			
			usuario.setFuncionario(funcionario);
			login.setPessoa(funcionario);
			
			this.usuarioRepository.salvar(usuario);		
			this.loginService.salvar(login);
					
			for(Empresa ep: empresas){
				UsuarioEmpresa ue = new UsuarioEmpresa();
				ue.setEmpresa(ep);
				ue.setUsuario(usuario);
							
				this.usuarioEmpresaService.salvar(ue);
			}
		}else{
			throw new NegocioException("Erro usuario selecionado esta em uso.");
		}	
	}
	
	@Transactional
	public void limparUsuarioEmpresa(Usuario usuario){
		this.usuarioEmpresaService.limparUsuarioEmpresa(usuario);
	}
	
	@Transactional
	public void limparUsuarioFuncionario(Funcionario funcionario){
		this.usuarioRepository.limparUsuarioFuncionario(funcionario);
	}
	
	public String buscarFuncionario(Usuario usuario) {
		Funcionario funcionario = this.funcionarioService.buscarFuncionario(usuario); 		
		
		if(funcionario != null){
			return funcionario.getNmFuncionario();
		}else{
			return "";
		} 
	}
	
	public Funcionario buscarFuncionarioLoginUsuario(Usuario usuario){
		return this.funcionarioService.buscarFuncionarioLoginUsuario(usuario);				
	}

	public List<Empresa> buscarFuncionarioEmpresas(Usuario usuario) {
		return this.empresaService.buscarFuncionarioEmpresas(usuario);
	}
}
