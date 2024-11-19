package model;

import java.io.*;
import java.util.Scanner;

public class Arquivo {

	// Método para escrever no arquivo - pode receber usuário ou produto
	public void escrever(Object object) {
		// Se o objeto passado no método for da classe Usuário
		if(object instanceof Usuario) {
			try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Banco.txt", true))) {
				Usuario usuario = (Usuario) object;
				// Verifica se o usuário pode ser cadastrado
				escritor.write(usuario.toString());
				escritor.newLine();
				System.out.println("Usuário cadastrado com sucesso!");
			} catch (IOException erro) {
				System.out.println("Erro ao escrever no arquivo: " + erro.getMessage());
			}
		} else {
			Produto produto = (Produto) object;
			// Checar antes de adicionar se já não existe um produto no arquivo com o mesmo nome
			if(checarNomeId(produto.getProduto(), produto.getId())) {
				try (BufferedWriter escritor = new BufferedWriter(new FileWriter("src\\model\\Produtos.txt", true))) {
					escritor.write(produto.toString());
					escritor.newLine();
				} catch (IOException erro) {
					System.out.println("Erro: " + erro.getMessage());
				}
			}
		}
	}

	// Método para manipular arquivo de usuário
	public void manipuladorArquivo(Usuario usuario, BufferedWriter escritor) {
		try {
			// Formata os dados do usuário como estão no método carregar()
			String linha = String.format("Nome:%s;Sexo:%s;Idade:%d;Id: %d;Senha:%s",
					usuario.getNome(),
					usuario.getSexo(),
					usuario.getIdade(),
					usuario.getId(),
					usuario.getSenha());

			escritor.write(linha);
			escritor.newLine();
		} catch (IOException erro) {
			System.out.println("Erro ao manipular arquivo: " + erro.getMessage());
		}
	}

	public void remover(int indiceLinha) {
		String conteudoLinha, conteudo = "";
		int i = 0;

		try (Scanner leitor = new Scanner(new BufferedReader(new FileReader("src\\model\\Produtos.txt")))) {
			while (leitor.hasNextLine()) { // Percorre cada linha do arquivo
				conteudoLinha = leitor.nextLine();

				if (indiceLinha != i) { // Caso
					conteudo += conteudoLinha + "\n";
				}

				i++;
			}

			System.out.println(conteudo);

			BufferedWriter escritor = new BufferedWriter(new FileWriter("src\\model\\Produtos.txt"));

			// Escreve os produtos não removidos
			escritor.write(conteudo);
			escritor.close();

		} catch(IOException e){
			System.out.println(e.getMessage());
		}
	}

	// Checar no arquivo se o nome e o id estão iguais
	public boolean checarNomeId(String nome, int id) {
		String[] produtoVez;

		try {
			Scanner arquivo = new Scanner(new BufferedReader(new FileReader("src\\model\\Produtos.txt")));

			while(arquivo.hasNextLine()) {
				produtoVez = arquivo.nextLine().split(";");

				// Se o nome ou id for igual ao do produto que está checando
				if(nome.equals(produtoVez[0]) || id == Integer.parseInt(produtoVez[1])) {
					return false;
				}
			}
			// Se o nome e id forem diferentes de todos no arquivo
			return true;

		} catch(IOException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}
}