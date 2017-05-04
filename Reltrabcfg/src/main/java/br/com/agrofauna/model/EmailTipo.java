package br.com.agrofauna.model;

public enum EmailTipo {
	COMPRAS(1, "Compras"),
	VENDAS(2, "Vendas"),
	FINANCEIRO(3, "Financeiro"),
	FISCAL(4, "Fiscal"),
	FUNCIONARIO(5, "Funcionario"),
	OUTROS(6, "Outros"),
	DIRETORIA(7, "Diretoria");
	
	
	private int id;
    private String nome;
    
    EmailTipo(int id, String nome){
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
