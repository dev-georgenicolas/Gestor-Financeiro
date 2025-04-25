package model;

public class Categoria {
	private int id;
	private String nome;
	private double valor_limite;

	public Categoria(int id, String nome, double valor_limite) {
		this.id = id;
		this.nome = nome;
		this.valor_limite = valor_limite;
	}

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
		this.nome = nome;
	}

	public double getValor_limite() {
		return valor_limite;
	}

	public void setValor_limite(double valor_limite) {
		this.valor_limite = valor_limite;
	}

	@Override
	public String toString() {
		return nome;
	}

}
