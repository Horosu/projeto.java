package model;

public class Pessoa {
	//Atributos
	private String sexo;
	private String nome;
	private int idade;

	//Construtor
	public Pessoa(String nome, String sexo, int idade) {
		this.nome = nome;
		this.sexo = sexo;
		this.idade = idade;
	}
	
	//Getters e Setters
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	//To String
	public String toString() {
		return "Nome: " + nome + "; Sexo: " + sexo + "; Idade: "  + idade;
	}
	
	
}