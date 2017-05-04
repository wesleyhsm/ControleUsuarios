package br.com.agrofauna.model;

public enum Sistema {
	RELTRABWEB(1, "ReltrabWeb"),
	RELTRABWEB_CFG(2, "ReltrabWeb Configurador"),	
	SITE(3, "Site"),
	SITE_VENDAS(4, "Site Vendas"),
	SITE_COTACAO(5, "Site Cotação"),
	TRATOR_DE_COMPRAS(6, "Trator de Compras");
	
	private int id;
    private String nome;
    
    Sistema(int id, String nome){
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
