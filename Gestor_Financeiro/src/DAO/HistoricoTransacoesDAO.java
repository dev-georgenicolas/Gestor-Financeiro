package DAO;

import java.sql.*;
import java.util.ArrayList;
import model.Transacao;

public class HistoricoTransacoesDAO {

	private static final String URL = "jdbc:postgresql://localhost:5432/Gestor_Financeiro";
	private static final String USER = "postgres";
	private static final String PASSWORD = "root";

	public ArrayList<Transacao> buscarHistorico(int idUsuario) {
		ArrayList<Transacao> transacoes = new ArrayList<>();
		String slq = "SELECT * FROM transacao WHERE id_usuario = ? ORDER BY data_transacao DESC";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(slq)) {
			stmt.setInt(1, idUsuario);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Transacao transacao = new Transacao();

				transacao.setId(rs.getInt("id"));
				transacao.setId_usuario(rs.getInt("id_usuario"));
				transacao.setValor(rs.getDouble("valor"));
				transacao.setData(rs.getDate("data_transacao"));
				transacao.setIdCategoria(rs.getInt("id_categoria"));
				transacao.setDescricao(rs.getString("descricao"));
				transacao.setTipo(rs.getString("tipo"));

				transacoes.add(transacao);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transacoes;
	}
}
