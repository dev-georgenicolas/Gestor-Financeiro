package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private static Conexao instancia;
	private static String url = "jdbc:postgresql://localhost:5432/Gestor_FinanceiroDB";
	private static String user = "postgres";
	private static String password = "root";
	private static Connection conexao;

	private Conexao() {
	}

	public static Conexao getIntancia() {
		if (instancia == null) {
			instancia = new Conexao();
		}
		return instancia;
	}

	public Connection abrirConexao() {
		try {
			conexao = DriverManager.getConnection(url, user, password);
			conexao.setAutoCommit(false);
			System.out.println("Conexão bem-sucedida!");
		} catch (SQLException e) {
			System.out.println("Erro na conexão: " + e.getMessage());
		}
		return conexao;
	}

	public void fecharConexao() {
		try {
			if (conexao != null && !conexao.isClosed()) {
				conexao.close();
			}
		} catch (SQLException e) {
			System.out.println("Erro ao encerrar a conexão: " + e.getMessage());

		}
	}

}
