package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Produtos {

    private ArrayList<Produto> produtos;
    private Arquivo arquivo = new Arquivo();

    public Produtos() {
        produtos = new ArrayList<>();
        carregar("src\\model\\Produtos.txt", false);
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public boolean cadastrar(Produto produto) {

        if(checarNomeId(produto)) { // Checar se o Id ou o nome já existe
            produtos.add(produto);
            arquivo.escrever(produto);

            return true;
        }

        return false;
    }

    public boolean remover(Produto produto) {

        for(int i = 0; i < produtos.size(); i++) {

            if(produto.equals(produtos.get(i))) {
                arquivo.remover(i);
                produtos.remove(produto);
                return true;
            }
        }
        return false;
    }

    public boolean checarNomeId(Produto produto) {
        int idProdutoVez;
        String nomeProdutoVez;

        // Percorrer produtos cadastrados, para ver se o que vai ser cadastrado existe
        for(int i = 0; i < produtos.size(); i++) {
            idProdutoVez = produtos.get(i).getId(); // Pegar ID de cada produto
            nomeProdutoVez = produtos.get(i).getProduto().toLowerCase(); // Pegar o nome de cada produto e colocar em minúsculo

            // Caso o id seja igual ou nome seja igual
            if(produto.getId() == idProdutoVez || produto.getProduto().toLowerCase().equals(nomeProdutoVez)) {
                return false;
            }
        }

        return true; // Caso o id não seja igual e o nome não seja igual
    }

    public void carregar(String caminho, boolean cabecalho) {
        // Criar um arquivo com base no caminho passado
        File arquivo = new File(caminho);

        try {
            // Se o arquivo não existir, ele cria e para o método (não há nada para ler)
            if (!arquivo.exists()) {
                arquivo.createNewFile();

            } else {
                Scanner scanner = new Scanner(new BufferedReader(new FileReader(arquivo)));
                String[] valores;
                produtos.clear(); // Limpar o array antes de carregar o conteúdo

                if(cabecalho) {
                    scanner.nextLine(); // Pular a primeira linha se for cabeçalho
                }

                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    valores = linha.split(";");

                    // Ignorar linhas vazias ou linhas com menos de 4 valores
                    if (linha.isEmpty() || valores.length < 4) {
                        continue;
                    }

                    String nome = valores[0];
                    int id = Integer.parseInt(valores[1]);
                    int quantidade = Integer.parseInt(valores[2]);
                    String valorStr = valores[3].replace(',', '.'); // Trocar ',' por '.'
                    float valor = Float.parseFloat(valorStr);

                    Produto produto = new Produto(nome, id, quantidade, valor);

                    if (checarNomeId(produto)) {
                        //produtos.add(produto);
                        cadastrar(produto);
                    }
                }

                scanner.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}