package br.com.agrofauna.model;

public enum PermissaoSetor {
	CFG(1, "CFG"),
	COMPRAS(2, "Compras"),
	VENDAS(3, "Vendas"),
	DIRETORIA(4, "Diretoria"),
	LOGISTICA(5, "Logistica"),
	CONTABIL(6, "Contabil"),
	FISCAL(7, "Fiscal"),
	FINANCEIRO(8, "Financeiro"),
	RH(9, "Recursos Humanos"),
	DP(10, "Departamento Pessoal"),
	ESTOQUE(11, "Estoque"),
	BOTAO(12, "Botão");
	
	private final int id;
    private final String nome;
    
    PermissaoSetor(int id, String nome){
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
