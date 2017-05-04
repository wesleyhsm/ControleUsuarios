package br.com.agrofauna.model;

public enum LogStatus {
	OK(1, "Funcionando Normalmente"),
	ERRO(2, "Problema Gerado");
	
	private int id;
    private String nome;
    
    LogStatus(int id, String nome){
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
