package br.com.agrofauna.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="funcionario")
@PrimaryKeyJoinColumn(name="id_pessoa")
@DiscriminatorValue("FUNCIONARIO")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({
	@NamedQuery(name="Funcionario.findAll", query="SELECT f FROM Funcionario f"),
	@NamedQuery(name="Funcionario.nome", query="SELECT f FROM Funcionario f join fetch f.usuario WHERE f.usuario.idUsuario=:idUsuario"),
	@NamedQuery(name="Funcionario.funcionarioLoginUsuario", query="SELECT f FROM Funcionario f join fetch f.usuario join fetch f.login WHERE f.usuario.idUsuario=:idUsuario")
})
public class Funcionario extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Date dtExameMedico;	
	private String nmCbo;
	private EstadoCivil estadoCivil;	
	private String nmFuncionario;
	private FuncionarioGrauInstrucao funcionarioGrauInstrucao;	
	private String nmMae;	
	private String nmPai;
	private Sexo sexo;
	private FuncionarioTipoContrato funcionarioTipoContrato;	
	private Integer nrHorarioMensal;	
	private Integer nrHorarioSemanal;	
	private Boolean snConvenio;	
	private Boolean snDeficiente;	
	private Boolean snInsalubridade;
	private Date dtCriacao;
	private Date dtAlteracao;
	private Boolean snStatus;	
	private FuncionarioCargo funcionarioCargo;
	private Usuario usuario; 
		
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_exame_medico")
	public Date getDtExameMedico() {
		return dtExameMedico;
	}
	public void setDtExameMedico(Date dtExameMedico) {
		this.dtExameMedico = dtExameMedico;
	}

	@Column(name="nm_cbo")
	public String getNmCbo() {
		return nmCbo;
	}
	public void setNmCbo(String nmCbo) {
		this.nmCbo = nmCbo;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="nm_estado_civil")
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	@NotBlank
	@Column(name="nm_funcionario")
	public String getNmFuncionario() {
		return nmFuncionario;
	}
	public void setNmFuncionario(String nmFuncionario) {
		this.nmFuncionario = nmFuncionario;
	}
		
	@Enumerated(EnumType.STRING)
	@Column(name="nm_grau_instrucao")
	public FuncionarioGrauInstrucao getFuncionarioGrauInstrucao() {
		return funcionarioGrauInstrucao;
	}
	public void setFuncionarioGrauInstrucao(FuncionarioGrauInstrucao funcionarioGrauInstrucao) {
		this.funcionarioGrauInstrucao = funcionarioGrauInstrucao;
	}
	
	@NotBlank
	@Column(name="nm_mae")
	public String getNmMae() {
		return nmMae;
	}
	public void setNmMae(String nmMae) {
		this.nmMae = nmMae;
	}

	@NotBlank
	@Column(name="nm_pai")
	public String getNmPai() {
		return nmPai;
	}
	public void setNmPai(String nmPai) {
		this.nmPai = nmPai;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="nm_sexo")
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="nm_tipo_contrato")
	public FuncionarioTipoContrato getFuncionarioTipoContrato() {
		return funcionarioTipoContrato;
	}
	public void setFuncionarioTipoContrato(FuncionarioTipoContrato funcionarioTipoContrato) {
		this.funcionarioTipoContrato = funcionarioTipoContrato;
	}
	
	@NotBlank
	@Column(name="nr_horario_mensal")
	public Integer getNrHorarioMensal() {
		return nrHorarioMensal;
	}
	public void setNrHorarioMensal(Integer nrHorarioMensal) {
		this.nrHorarioMensal = nrHorarioMensal;
	}

	@NotBlank
	@Column(name="nr_horario_semanal")
	public Integer getNrHorarioSemanal() {
		return nrHorarioSemanal;
	}
	public void setNrHorarioSemanal(Integer nrHorarioSemanal) {
		this.nrHorarioSemanal = nrHorarioSemanal;
	}

	@NotNull
	@Column(name="sn_convenio")
	public Boolean getSnConvenio() {
		return snConvenio;
	}
	public void setSnConvenio(Boolean snConvenio) {
		this.snConvenio = snConvenio;
	}

	@NotNull
	@Column(name="sn_deficiente")
	public Boolean getSnDeficiente() {
		return snDeficiente;
	}
	public void setSnDeficiente(Boolean snDeficiente) {
		this.snDeficiente = snDeficiente;
	}

	@NotNull
	@Column(name="sn_insalubridade")
	public Boolean getSnInsalubridade() {
		return snInsalubridade;
	}
	public void setSnInsalubridade(Boolean snInsalubridade) {
		this.snInsalubridade = snInsalubridade;
	}
		
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	public Date getDtCriacao() {
		return dtCriacao;
	}	
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")
	public Date getDtAlteracao() {
		return dtAlteracao;
	}
	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}
	
	@NotNull
	@Column(name="sn_status")
	public Boolean getSnStatus() {
		return snStatus;
	}
	public void setSnStatus(Boolean snStatus) {
		this.snStatus = snStatus;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_funcionario_cargo")	
	public FuncionarioCargo getFuncionarioCargo() {
		return funcionarioCargo;
	}
	public void setFuncionarioCargo(FuncionarioCargo funcionarioCargo) {
		this.funcionarioCargo = funcionarioCargo;
	}
	
	@OneToOne(mappedBy = "funcionario", fetch=FetchType.LAZY, optional=true)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@PrePersist
	@PreUpdate
	public void configuraDataCriacaoAlteracao(){
		this.dtAlteracao = new Date();
		
		if(this.dtCriacao == null){
			this.dtCriacao = new Date();
		}
	}
	
	@Override
	public String toString() {
		return "Funcionario [nmFuncionario=" + nmFuncionario + ", funcionarioTipoContrato=" + funcionarioTipoContrato + ", snStatus=" + snStatus + "]";
	}
}
