package DAO;

import model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/Gestor_Financeiro";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public void createUsuario(Usuario usuario) {
        if (usuarioExiste(usuario.getEmail())) {
            System.out.println("Erro: O e-mail já está registrado.");
            return;
        }
        
        int novoId = getUltimoId() + 1;

        String sql = "INSERT INTO usuario (id, nome, saldo, email, senha) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, novoId);
            stmt.setString(2, usuario.getNome());
            stmt.setDouble(3, usuario.getSaldo());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getSenha());
            stmt.executeUpdate();
            System.out.println("Usuário criado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario getUsuarioById(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("saldo"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, saldo = ?, email = ?, senha = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setDouble(2, usuario.getSaldo());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setInt(5, usuario.getId());
            stmt.executeUpdate();
            System.out.println("Usuário atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Usuário deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean usuarioExiste(String email) {
        String sql = "SELECT 1 FROM usuario WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Usuario autenticarUsuario(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("saldo"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    
    private int getUltimoId() {
		String sql = "SELECT MAX(id) FROM usuario";
		int ultimoId = 0;

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			if (rs.next()) {
				ultimoId = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ultimoId;
	}
    
    public void atualizarSaldo(int usuarioId, double novoSaldo) {
        String sql = "UPDATE usuario SET saldo = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, novoSaldo);
            stmt.setInt(2, usuarioId);
            stmt.executeUpdate();
            System.out.println("Saldo atualizado com sucesso para o usuário ID: " + usuarioId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao atualizar o saldo do usuário.");
        }
    }

}
