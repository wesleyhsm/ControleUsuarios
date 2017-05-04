package br.com.agrofauna.model;

public enum Sexo {
	MASCULINO(1, "Masculino"),
	FEMININO(2, "Feminino");
	
	private int id;
    private String nome;
    
    Sexo(int id, String nome){
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
