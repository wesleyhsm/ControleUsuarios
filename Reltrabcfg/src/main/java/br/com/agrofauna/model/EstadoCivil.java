package br.com.agrofauna.model;

public enum EstadoCivil {
	CASADO(1, "Casaso(a)"),
	SOLTEIRO(2, "Solteiro(a)"),
	DIVORCIADO(3, "Divorciado(a)"),
	VIUVO(4, "Viuvo(a)"),
	AMASIADO(5, "Amasiado(a)"),
	UNIAO_ESTAVEL(6, "União Estavel");
	
	private int id;
    private String nome;
    
    EstadoCivil(int id, String nome){
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
