package model;

public class Usuario {
	private int id;
	private String nome;
	private double saldo;
	private String email;
	private String senha;

	public Usuario(int id, String nome, double saldo, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.saldo = saldo;
		this.email = email;
		this.senha = senha;
	}

	// Construtor sem o id, para inserção no banco
	public Usuario(String nome, double saldo, String email, String senha) {
		this.nome = nome;
		this.saldo = saldo;
		this.email = email;
		this.senha = senha;
	}

	// Getters e Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome == null || nome.isEmpty()) {
			throw new IllegalArgumentException("Nome não pode ser vazio.");
		}
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if (senha == null || senha.isEmpty()) {
			throw new IllegalArgumentException("Senha não pode ser vazia.");
		}
		this.senha = senha;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		if (saldo < 0) {
			throw new IllegalArgumentException("Saldo não pode ser negativo.");
		}
		this.saldo = saldo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new IllegalArgumentException("Formato de e-mail inválido.");
		}
		this.email = email;
	}

	@Override
	public String toString() {
		return "model.Usuario{id=" + id + ", nome='" + nome + "', saldo=" + saldo + ", email='" + email + "'}";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Usuario usuario = (Usuario) obj;
		return id == usuario.id;
	}
}
