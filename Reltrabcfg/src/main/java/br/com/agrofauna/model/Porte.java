package br.com.agrofauna.model;

public enum Porte {
	PEQUENO(1, "Pequeno"),
	MEDIO(2, "Medio"),
	GRANDE(3, "Grande");
	
	private int id;
    private String nome;
    
    Porte(int id, String nome){
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
