package br.com.sgcraft.homes.MySQL;

import java.sql.*;

import br.com.sgcraft.homes.*;

public class MySQL
{
    private String user;
    private String host;
    private String database;
    private String password;
    public static Connection connection;
    static Statement statement;
    
    public MySQL(final String user, final String password, final String host, final String database, final Main plugin) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.database = database;
    }
    
    @SuppressWarnings("null")
	public void startConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            MySQL.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database, this.user, this.password);
            (MySQL.statement = MySQL.connection.createStatement()).execute("CREATE TABLE IF NOT EXISTS SgHOMES_Contas (Nome VARCHAR(16) NOT NULL, Nick VARCHAR(16) NOT NULL,Homes int(16) NOT NULL)");
            MySQL.statement.execute("CREATE TABLE IF NOT EXISTS SgHOMES (Dono VARCHAR(16) NOT NULL,Nome VARCHAR(16) NOT NULL, Nomereal VARCHAR(16) NOT NULL,Publica VARCHAR(16) NOT NULL,Location VARCHAR(100) NOT NULL)");
        }
        catch (SQLException | ClassNotFoundException ex4) {
            final Exception ex3 = null;
            final Exception ex2 = ex3;
            ex2.printStackTrace();
        }
    }
    
    public void closeConnection() {
        try {
            MySQL.statement.close();
            MySQL.connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
