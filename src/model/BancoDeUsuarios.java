package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BancoDeUsuarios {

	private ArrayList<Usuario> usuarios;

	//Construtor
	public BancoDeUsuarios() {
		this.usuarios = new ArrayList<>();
		//Carregar do arquivo para o array
		carregar();
		System.out.println("Usuários carregados:");

	}

	//Getters e Setters
	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	//Cadastro de Usu�rio
	public boolean cadastro(Usuario usuario) {
		Arquivo arquivo = new Arquivo();
		//Verifica se já existe um usuário com o mesmo id
		for (Usuario us : usuarios) {

			//Condicional para ver se o id já tá cadastrado
			if (us.getId() == usuario.getId()) {
				return false;
			}
		}
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Banco.txt", true))){
			if(!usuario.getNome().equals("") &&  // Nome for diferente de vazio
					!usuario.getSexo().equals("") &&
					!usuario.getSenha().equals("") &&
					usuario.getId() >-1 && // Id for numero possitivo
					usuario.getIdade() > 0) { // Idade maior que 0
				//Adicionar no array
				usuarios.add(usuario);
				//Escrever no arquivo
				arquivo.manipuladorArquivo(usuario,escritor);
				return true;
			}
		}catch(Exception erro) {
			System.out.println("Erro de cadastro: " + erro.getMessage());
			return false;
		}
		return false;



	}

	public boolean loginUsuario(int id, String senha) {
		for(Usuario usuario:usuarios) { // percorre todos os usuarios da lista
			if(usuario.getId() == id && usuario.getSenha().equals(senha)) { // verifica se tem o mesmo login no vetor
				return true;
			}
		}
		return false;

	}



	//Carregar do arquivo para o array
	public void carregar() {
		try (BufferedReader leitor = new BufferedReader(new FileReader("Banco.txt"))) {
			String linha;

			//Loop enquanto a linha não for nula
			while ((linha = leitor.readLine()) != null) {

				// Divide a linha em partes usando o delimitador ";"
				String[] vet = linha.split(";");

				//Pegar oque corresponde a cada posição do arquivo
				String nome = vet[0].replace("Nome:", "").trim();
				String sexo = vet[1].replace("Sexo:", "").trim();
				int idade = Integer.parseInt(vet[2].replace("Idade:", "").trim());
				int id = Integer.parseInt(vet[3].replace("Id: ", "").trim());
				String senha = vet[4].replace("Senha:", "").trim();

				// Cria o usuário com os dados extraídos da linha
				Usuario usuario = new Usuario(nome, sexo, idade, id, senha);

				// Adiciona o usuário à lista
				usuarios.add(usuario);
			}
			//Tratamento caso tenha exceção
		} catch (IOException erro) {
			System.out.println("Erro ao ler o arquivo: " + erro.getMessage());
		}
	}

	//Método para editar informações do usuário
	public boolean editarUsuario(int id, String senha, String senhaNova)
	{
		//Tratamento caso tenha erro
		try {

			//Loop por cada usuário dentro do array usuarios
			for (Usuario usuario:usuarios) {

				//Condicional para saber se o id e a senha é a mesma do banco de dados
				if(usuario.getId() == id && usuario.getSenha().equals(senha)) {
					//Altera a senha no usuário da posição
					usuario.setSenha(senhaNova);

					//Chama o método para reescrever com oque foi editado no array
					salvarUsuariosEditados();
					return true; // Senha trocada
					//Ver se é necessário um else para return false;
				}
			}
			return false; // Id n�o encontrado
		} catch (Exception erro) {
			System.out.println("Erro ao Editar o usuario: " + erro.getMessage());
			return false;
		}
	}

	public boolean salvarUsuariosEditados() {

		//Cria um buffer para escrever no arquivo
		//False tem função de sobrescrever
		try(BufferedWriter escritor = new BufferedWriter(new FileWriter("Banco.txt", false))){
			//Instanciando a classe arquivo
			Arquivo arquivo = new Arquivo();

			//Loop onde reescreve os usuários no banco com as informações atualizadas
			for (Usuario usuario:usuarios) {
				arquivo.manipuladorArquivo(usuario, escritor);
			}
			return true;
		} catch(Exception erro) {
			System.out.println("Erro ao Salvar usuario " + erro.getMessage());
		}
		return false;
	}

	//Método onde retorna as informações do perfil do usuário
	public String verPerfil(Usuario usuario) {

		//Loop para percorrer o array de usuarios
		for(Usuario us:usuarios) {

			//Condicional para verificar se o id é igual ao informado
			if(us.getId() == usuario.getId()) {

				//Retorna os dados do usuário
				return "Perfil do Úsuario:" + "\nNome: " + us.getNome() + "\nId: " + us.getId() + "\nSexo: " + us.getSexo() + "\nIdade: " + us.getIdade();
			}
		}

		//Retorna vazio caso não encontre
		return "";

	}

	public boolean ExcluirConta(int id) {

		//Loop para percorrer o array
		for (int i =0;i<usuarios.size();i++) {

			//Condicional para encontrar o id correspondente
			if(usuarios.get(i).getId() == id) {

				//Remove o usuário no array pelo índice
				usuarios.remove(i);

				//Atualiza no txt
				salvarUsuariosEditados();
				return true;

			}
		}
		return false;

	}

}