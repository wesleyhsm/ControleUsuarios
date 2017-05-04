package br.com.agrofauna.model;

public enum TelefoneTipo {
	RESIDENCIAL(1, "Residêncial"),
	COMERCIAL(2, "Comercial"),
	CELULAR(3, "Celular"),
	FAX(4, "Fax");
	
	private int id;
    private String nome;
    
    TelefoneTipo(int id, String nome){
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
