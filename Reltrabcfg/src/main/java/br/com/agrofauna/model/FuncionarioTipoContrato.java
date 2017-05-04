package br.com.agrofauna.model;

public enum FuncionarioTipoContrato {	
	CLT(1, "CLT"),
	CLT_TEMPORARIO(2, "CLT Temporario"),
	ESTAGIARIO(3, "Estagiario"),
	TERCEIRIZADO(4, "Terceirizado"),
	CONTRATO(5, "Contrato"),
	CONTRATO_TEMPORARIO(6, "Contrato Temporario");
	
	private int id;
    private String nome;
    
    FuncionarioTipoContrato(int id, String nome){
        this.id = id;
    	this.nome = nome;       
    }

    public int getId(){
        return this.id;
    }
    
    public String getNome(){
        return this.nome;
    }
}
