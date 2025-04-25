package DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import model.Categoria;

public class CategoriaDAO {

	private static final String URL = "jdbc:postgresql://localhost:5432/Gestor_Financeiro";
	private static final String USER = "postgres";
	private static final String PASSWORD = "root";

	public void createCategoria(Categoria categoria) {
		String sql = "INSERT INTO categoria (nome, valor_limite) VALUES (?, ?)";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, categoria.getNome());
			stmt.setDouble(2, categoria.getValor_limite());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int id = generatedKeys.getInt(1);
						categoria.setId(id);
						System.out.println("model.Categoria inserida com sucesso! ID: " + id);
					}
				}
			} else {
				System.out.println("Falha ao inserir a categoria.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Categoria getCategoriaById(int id) {
		String sql = "SELECT * FROM categoria WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new Categoria(rs.getInt("id"), rs.getString("nome"), rs.getDouble("valor_limite"));
			} else {
				System.out.println("Nenhuma categoria encontrada para o ID: " + id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateCategoria(Categoria categoria) {
		String sql = "UPDATE categoria SET nome = ?, valor_limite = ? WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, categoria.getNome());
			stmt.setDouble(2, categoria.getValor_limite());
			stmt.setInt(3, categoria.getId());

			stmt.executeUpdate();
			System.out.println("model.Categoria atualizada com sucesso.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCategoria(int id) {
		String sql = "DELETE FROM categoria WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("model.Categoria excluída com sucesso.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Categoria> listarCategorias() {

		List<Categoria> categorias = new ArrayList();
		String sql = "SELECT id, nome, valor_limite FROM categoria";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				double valor_limite = rs.getDouble("valor_limite");
				categorias.add(new Categoria(id, nome, valor_limite));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return categorias;

	}

}
