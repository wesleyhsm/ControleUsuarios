package br.com.agrofauna.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="pessoa")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="nm_pessoa", discriminatorType=DiscriminatorType.STRING)
@NamedQueries({
	@NamedQuery(name="Pessoa.findAll", query="SELECT p FROM Pessoa p"),
	@NamedQuery(name="Pessoa.existeCnpjCpf", query="SELECT p FROM Pessoa p WHERE p.nmCnpjCpf=:cnpjCpf")
})	
public class Pessoa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long idPessoa;
	private String nmCnpjCpf;
	private Long idFatfClie;
	private PessoaTipo pessoaTipo;	
	private Login login;	
	private List<Email> emails;
	private List<Telefone> telefones;
	private List<Endereco> enderecos;
	private List<NfeConfiguracao> nfeConfiguracoes;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pessoa")
	public Long getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	@NotBlank
	@Column(name="nm_cnpj_cpf", length=14)
	public String getNmCnpjCpf() {
		return nmCnpjCpf;
	}	
	public void setNmCnpjCpf(String nmCnpjCpf) {
		this.nmCnpjCpf = nmCnpjCpf;
	}
	
	@Column(name="id_fatfclie")
	public Long getIdFatfClie() {
		return idFatfClie;
	}
	public void setIdFatfClie(Long idFatfClie) {
		this.idFatfClie = idFatfClie;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="nm_pessoa_tipo")
	public PessoaTipo getPessoaTipo() {
		return pessoaTipo;
	}
	public void setPessoaTipo(PessoaTipo pessoaTipo) {
		this.pessoaTipo = pessoaTipo;
	}
	
	@OneToMany(mappedBy="pessoa", targetEntity=br.com.agrofauna.model.Endereco.class, cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
		
	@OneToOne(mappedBy="pessoa", targetEntity=br.com.agrofauna.model.Login.class, cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, optional=true)	
	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}
	
	@OneToMany(mappedBy="pessoa", targetEntity=br.com.agrofauna.model.Email.class, cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)		
	public List<Email> getEmails() {
		return emails;
	}
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
		
	@OneToMany(mappedBy="pessoa", targetEntity=br.com.agrofauna.model.Telefone.class, cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
		
	@OneToMany(mappedBy="pessoa", targetEntity=br.com.agrofauna.model.NfeConfiguracao.class, cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	public List<NfeConfiguracao> getNfeConfiguracoes() {
		return nfeConfiguracoes;
	}
	public void setNfeConfiguracoes(List<NfeConfiguracao> nfeConfiguracoes) {
		this.nfeConfiguracoes = nfeConfiguracoes;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPessoa == null) ? 0 : idPessoa.hashCode());
		return result;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (idPessoa == null) {
			if (other.idPessoa != null)
				return false;
		} else if (!idPessoa.equals(other.idPessoa))
			return false;
		return true;
	}	
}
