package br.com.agrofauna.filtro;

import java.io.Serializable;

public class LogFiltro implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	private int nrTipoBusca;
	private String nmTermoBusca;
	private Boolean snStatus;
	private int nrOrdenacao;	
	private int nrPrimeiroRegistro;
	private int nrQuantidadeRegistro;
	
	public int getNrTipoBusca() {
		return nrTipoBusca;
	}
	public void setNrTipoBusca(int nrTipoBusca) {
		this.nrTipoBusca = nrTipoBusca;
	}
	public String getNmTermoBusca() {
		return nmTermoBusca;
	}
	public void setNmTermoBusca(String nmTermoBusca) {
		this.nmTermoBusca = nmTermoBusca;
	}
	public Boolean getSnStatus() {
		return snStatus;
	}
	public void setSnStatus(Boolean snStatus) {
		this.snStatus = snStatus;
	}
	public int getNrOrdenacao() {
		return nrOrdenacao;
	}
	public void setNrOrdenacao(int nrOrdenacao) {
		this.nrOrdenacao = nrOrdenacao;
	}	
	public int getNrPrimeiroRegistro() {
		return nrPrimeiroRegistro;
	}
	public void setNrPrimeiroRegistro(int nrPrimeiroRegistro) {
		this.nrPrimeiroRegistro = nrPrimeiroRegistro;
	}
	public int getNrQuantidadeRegistro() {
		return nrQuantidadeRegistro;
	}
	public void setNrQuantidadeRegistro(int nrQuantidadeRegistro) {
		this.nrQuantidadeRegistro = nrQuantidadeRegistro;
	}	
}
