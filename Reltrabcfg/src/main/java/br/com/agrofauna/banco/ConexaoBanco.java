package br.com.agrofauna.banco;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
    public  final String DATABASE_NOVO = "db_agro_matriz";
    
  //produção
    protected  String user = "teste";
    protected  String passwd = "teste";
        
    //teste
    protected  String userTeste = "teste";
    protected  String passwdTeste = "teste";
      
    public  String url = "jdbc:mysql://127.0.0.1:10049/" + DATABASE_NOVO + "?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false&autoReconnect=true&holdResultsOpenOverStatementClose=true";
    
    //teste
    public Connection getConnection(){
        try {
            return DriverManager.getConnection(url, userTeste, passwdTeste);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    //produção
    /*
    public Connection getConnection(){
        try {
            return DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
}
