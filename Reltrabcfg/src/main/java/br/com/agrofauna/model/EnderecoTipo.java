package br.com.agrofauna.model;

public enum EnderecoTipo {
	ENTREGA(1, "Entrega"),
	COBRANCA(2, "Cobrança"),
	FINANCEIRO(3, "Financeiro"),
	RECEBIMENTO(4, "Recebimento"),
	TODOS(5, "Todos");
	
	private int id;
    private String nome;
    
    EnderecoTipo(int id, String nome){
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
