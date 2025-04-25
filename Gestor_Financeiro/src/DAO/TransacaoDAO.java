package DAO;

import java.sql.*;
import model.Transacao;

public class TransacaoDAO {

	private static final String URL = "jdbc:postgresql://localhost:5432/Gestor_Financeiro";
	private static final String USER = "postgres";
	private static final String PASSWORD = "root";

	public void createTransacao(Transacao transacao) {

		String sql = "INSERT INTO transacao (id_usuario, valor, id_categoria, descricao, tipo) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, transacao.getId_usuario());
			stmt.setDouble(2, transacao.getValor());

			if ("Receita".equalsIgnoreCase(transacao.getTipo())) {
				stmt.setNull(3, Types.INTEGER);
			} else {
				stmt.setInt(3, transacao.getIdCategoria());
			}

			stmt.setString(4, transacao.getDescricao());
			stmt.setString(5, transacao.getTipo());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int id = generatedKeys.getInt(1);
						transacao.setId(id);
						System.out.println("Transação inserida com sucesso! ID: " + id);
					}
				}
			} else {
				System.out.println("Falha ao inserir a transação.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Transacao getTransacaoById(int id, int id_usuario) {
		String sql = "SELECT * FROM transacao WHERE id = ? AND id_usuario = ? ";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.setInt(2, id_usuario);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Transacao transacao = new Transacao();

				transacao.setId(rs.getInt("id"));
				transacao.setId_usuario(rs.getInt("id_usuario"));
				transacao.setValor(rs.getDouble("valor"));
				transacao.setData(rs.getDate("data"));
				transacao.setIdCategoria(rs.getInt("id_categoria"));
				transacao.setDescricao(rs.getString("descricao"));
				transacao.setTipo(rs.getString("tipo"));

				return transacao;

			} else {
				System.out.println("Nenhuma transação encontrada para o ID: " + id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateTransacao(Transacao transacao) {
		String sql = "UPDATE transacao SET valor = ?, data = ?, id_categoria = ?, descricao = ?, tipo = ? WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setDouble(1, transacao.getValor());
			stmt.setDate(2, transacao.getData());
			stmt.setInt(3, transacao.getIdCategoria());
			stmt.setString(4, transacao.getDescricao());
			stmt.setString(5, transacao.getTipo());
			stmt.setInt(6, transacao.getId());

			stmt.executeUpdate();
			System.out.println("Transação atualizada com sucesso.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteTransacao(int id, int id_usuario) {
		String sql = "DELETE FROM transacao WHERE id = ? AND id_usuario = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.setInt(2, id_usuario);
			stmt.executeUpdate();
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Transação excluída com sucesso.");

			} else {
				System.out.println("Nenhuma transação encontrada para exclusão.");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public double getSaldo(int usuarioId) throws SQLException {
	    double receitas = getReceitas(usuarioId);
	    double despesas = getDespesas(usuarioId);
	    double saldoAtual = receitas - despesas;

	   
	    UsuarioDAO usuarioDAO = new UsuarioDAO();
	    usuarioDAO.atualizarSaldo(usuarioId, saldoAtual);

	    return saldoAtual;
	}

	public double getReceitas(int usuarioId) throws SQLException {
		String sql = "SELECT SUM(valor) FROM transacao WHERE id_usuario = ? AND tipo = 'Receita'";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, usuarioId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
			}
			return 0.0;
		}

	}

	public double getDespesas(int usuarioId) throws SQLException {
		String sql = "SELECT SUM(valor) FROM transacao WHERE id_usuario = ? AND tipo = 'Despesa'";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, usuarioId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
			}
		}
		return 0.0;
	}

}
