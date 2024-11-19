package model;

//Funcionario herda de Pessoa
public class Usuario extends Pessoa{
	private int id;
	private String senha;


	//Construtor de Funcionario
	public Usuario(String nome, String sexo,int idade, int id, String senha) {
		super(nome, sexo, idade);
		this.id = id;
		this.senha = senha;
	}

	//Getters e Setters
	public int getId() {
		return id;
	}


	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Override
	//To String
	public String toString() {
		return super.toString() + "; Id: " +this.id +  "; Senha: " + this.senha;
	}
}