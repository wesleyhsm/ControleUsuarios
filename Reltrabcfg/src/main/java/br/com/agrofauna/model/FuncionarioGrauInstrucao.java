package br.com.agrofauna.model;

public enum FuncionarioGrauInstrucao {
	FUNDAMENTAL_INCOMPLETO(1, "Fundamental Incompleto"),
	FUNDAMENTAL_COMPLETO(2, "Fundamental Completo"),
	MEDIO_INCOMPLETO(3, "Medio Incompleto"),
	MEDIO_COMPLETO(4, "Medio Completo"),
	SUPERIOR_INCOMPLETO(5, "Superior Incompleto"),
	SUPERIOR_COMPLETO(6, "Superior Compelto");
	
	private int id;
    private String nome;
    
    FuncionarioGrauInstrucao(int id, String nome){
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
