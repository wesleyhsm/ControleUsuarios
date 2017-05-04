package br.com.agrofauna.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.agrofauna.webservice.CepWebService;
import br.com.agrofauna.filtro.EmpresaFiltro;
import br.com.agrofauna.model.Email;
import br.com.agrofauna.model.Empresa;
import br.com.agrofauna.model.Endereco;
import br.com.agrofauna.model.Estado;
import br.com.agrofauna.model.NfeConfiguracao;
import br.com.agrofauna.model.PessoaTipo;
import br.com.agrofauna.model.Telefone;
import br.com.agrofauna.model.Usuario;
import br.com.agrofauna.repository.EmpresaRepository;
import br.com.agrofauna.validation.RetiraCaracteres;
import br.com.agrofauna.validation.ValidaCnpjCpf;

public class EmpresaService implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaRepository empresaRepository;
	
	@Inject
	private EstadoService estadoService;
	
	@Transactional
	public Empresa salvarEmpresa(Empresa empresa, List<Endereco> enderecos, List<Telefone> telefones, List<Email> emails, List<NfeConfiguracao> nfeConfiguracaes) throws NegocioException{
		
		if(!ValidaCnpjCpf.isValidCNPJ( RetiraCaracteres.removerTodosCaracterEspecial(empresa.getNmCnpjCpf()) )){
			throw new NegocioException("O CNPJ não é valido.");
		}
		
		if(this.empresaRepository.verificaExisteCnpj(empresa) != null && empresa.getIdPessoa() <= 0){			
			throw new NegocioException("Empresa CNPJ (" + empresa.getNmCnpjCpf()+") já existe.");
		}
				
		if(enderecos.isEmpty()){
			throw new NegocioException("Lista de endereços esta vazia.");
		}
		
		if(telefones.isEmpty()){
			throw new NegocioException("Lista de telefones esta vazia.");
		}
		
		if(emails.isEmpty()){
			throw new NegocioException("Lista de emails esta vazia.");
		}
		
		if(nfeConfiguracaes.isEmpty()){
			throw new NegocioException("Lista de NF-e Configuração esta vazia.");
		}
					
		for(Email email: emails){
			email.setPessoa(empresa);
		}
		empresa.setEmails(emails);
		
		for(Endereco endereco: enderecos){
			endereco.setPessoa(empresa);
		}
		empresa.setEnderecos(enderecos);
		
		for(Telefone telefone: telefones){
			telefone.setPessoa(empresa);
		}
		empresa.setTelefones(telefones);
		
		for(NfeConfiguracao nfe: nfeConfiguracaes){
			nfe.setPessoa(empresa);
		}
		empresa.setNfeConfiguracoes(nfeConfiguracaes);
						
		if(empresa != null && empresa.getIdPessoa() > 0){			
			excluirCadastroEmpresa(empresa); //quando for edição todos os cadastros de endereços, telefones, emails e nf-e
		}
		
		empresa.setPessoaTipo(PessoaTipo.JURIDICA);
		return this.empresaRepository.salvar(empresa);
	}
	
	public List<Estado> buscarTodosEstados(){
		return this.estadoService.buscarTodosEstados();
	}
	
	public Endereco encontraCEP(Endereco endereco){ 				
		if(!"".equals(endereco.getNmCep())){
			CepWebService cepWebService = new CepWebService(endereco.getNmCep());
 				        	
        	endereco.setEstado(this.estadoService.buscarEstadosSigla(cepWebService.getEstado()));
            endereco.setNmRua(cepWebService.getLogradouro());	            
            endereco.setNmCidade(cepWebService.getCidade());
            endereco.setNmBairro(cepWebService.getBairro());        	        
		}
		return endereco;
    }
	
	public LazyDataModel<Empresa> listaTodasEmpresas(EmpresaFiltro empresaFiltro){
		 return new LazyDataModel<Empresa>(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Empresa> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, Object> filters) {										
				empresaFiltro.setNrPrimeiroRegistro(first);
				empresaFiltro.setNrQuantidadeRegistro(pageSize);

				setRowCount(empresaRepository.quantidadeListaTodasEmpresas(empresaFiltro));
				return empresaRepository.listaTodasEmpresas(empresaFiltro);
			}
		};
	}

	public Empresa buscarEmpresaPeloId(Empresa empresa) {		
		return this.empresaRepository.buscarEmpresaPeloId(empresa);
	}

	public List<Endereco> buscarEnderecoPeloId(Empresa empresa) {
		return this.empresaRepository.buscarEnderecoPeloId(empresa);
	}

	public List<Telefone> buscarTelefonePeloId(Empresa empresa) {
		return this.empresaRepository.buscarTelefonePeloId(empresa);
	}

	public List<Email> buscarEmailPeloId(Empresa empresa) {
		return this.empresaRepository.buscarEmailPeloId(empresa);
	}

	public List<NfeConfiguracao> buscarNfeConfiguracaoPeloId(Empresa empresa) {
		return this.empresaRepository.buscarNfeConfiguracaoPeloId(empresa);
	}
	
	@Transactional
	public void excluirCadastroEmpresa(Empresa empresa){
		this.empresaRepository.excluirEnderecos(empresa);
		this.empresaRepository.excluirTelefones(empresa);
		this.empresaRepository.excluirEmails(empresa);
		this.empresaRepository.excluirNfeConfigurador(empresa);
	}
	
	@Transactional
	public void excluir(Empresa empresa) throws NegocioException {
		/*falta fazer a verificação se existe algo relacionado 
		if(!this.usuarioPermissaoRepository.buscarPemissao(permissao).isEmpty()){		 
			throw new NegocioException("Erro permissão ("+ permissao.getIdPermissao() + ") não pode ser excluida, pois esta em uso.");
		}
		*/		
		this.empresaRepository.excluir(empresa);		
	}

	public List<Empresa> buscarFuncionarioEmpresas(Usuario usuario) {		
		return this.empresaRepository.buscarFuncionarioEmpresas(usuario);
	}	
}
