package br.com.agrofauna.model;

public enum PessoaTipo {
	FISICA(1, "Física"),
	JURIDICA(2, "Jurídica");
	
	private int id;
    private String nome;
    
    PessoaTipo(int id, String nome){
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
