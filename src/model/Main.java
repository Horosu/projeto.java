package model;

import java.util.Scanner;

public class Main {
    private static BancoDeUsuarios banco;
    private static Produtos gerenciadorProdutos;
    private static Scanner scanner;
    private static Usuario usuarioLogado;

    public static void main(String[] args) {
        // inicializa banco de usuários, gerenciador de produtos e scanner
        banco = new BancoDeUsuarios();
        gerenciadorProdutos = new Produtos();
        scanner = new Scanner(System.in);

        // tela de login inicial
        if (telaLogin()) {
            menuPrincipal();
        }
    }

    // função para tela de login inicial
    private static boolean telaLogin() {
        while (true) {
            System.out.println("\n--- LOGIN ---");
            System.out.println("1. Fazer Login");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1:
                    // fazer login
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // limpa buffer
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();

                    if (banco.loginUsuario(id, senha)) {
                        usuarioLogado = new Usuario("", "", 0, id, senha);
                        System.out.println("Login realizado com sucesso!");
                        return true;
                    } else {
                        System.out.println("Falha no login. Tente novamente.");
                    }
                    break;

                case 0:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // função para tela de cadastro separada
    private static void telaCadastro() {
        System.out.println("\n--- CADASTRO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Sexo: ");
        String sexo = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        System.out.print("ID: ");
        int novoId = scanner.nextInt();
        scanner.nextLine(); // limpa buffer
        System.out.print("Senha: ");
        String novaSenha = scanner.nextLine();

        Usuario novoUsuario = new Usuario(nome, sexo, idade, novoId, novaSenha);
        if (banco.cadastro(novoUsuario)) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar usuário.");
        }
    }

    // função para menu principal após login
    private static void menuPrincipal() {
        while (true) {
            System.out.println("\n--- SISTEMA ---");
            System.out.println("1. Gerenciar Usuário");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1:
                    menuUsuario();
                    break;
                case 2:
                    menuProdutos();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // menu para gerenciamento de usuário
    private static void menuUsuario() {
        while (true) {
            System.out.println("\n--- GERENCIAR USUÁRIO ---");
            System.out.println("1. Editar Senha");
            System.out.println("2. Ver Perfil");
            System.out.println("3. Excluir Conta");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1:
                    editarSenha();
                    break;
                case 2:
                    verPerfil();
                    break;
                case 3:
                    excluirConta();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // menu para gerenciamento de produtos
    private static void menuProdutos() {
        while (true) {
            System.out.println("\n--- GERENCIAR PRODUTOS ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Remover Produto");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    removerProduto();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // método para cadastrar produto
    private static void cadastrarProduto() {
        System.out.print("Nome do Produto: ");
        String nome = scanner.nextLine();
        System.out.print("ID do Produto: ");
        int id = scanner.nextInt();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        System.out.print("Valor: ");
        float valor = scanner.nextFloat();
        scanner.nextLine(); // limpa buffer

        Produto novoProduto = new Produto(nome, id, quantidade, valor);
        if (gerenciadorProdutos.cadastrar(novoProduto)) {
            System.out.println("Produto cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar produto. ID ou nome já existente.");
        }
    }

    // método para listar produtos
    private static void listarProdutos() {
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        for (Produto p : gerenciadorProdutos.getProdutos()) {
            System.out.println("Nome: " + p.getProduto() +
                    ", ID: " + p.getId() +
                    ", Quantidade: " + p.getQuantidade() +
                    ", Valor: R$ " + p.getValor());
        }
    }

    // método para remover produto
    private static void removerProduto() {
        System.out.print("ID do Produto a ser removido: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // limpa buffer

        Produto produtoRemover = null;
        for (Produto p : gerenciadorProdutos.getProdutos()) {
            if (p.getId() == id) {
                produtoRemover = p;
                break;
            }
        }

        if (produtoRemover != null) {
            if (gerenciadorProdutos.remover(produtoRemover)) {
                System.out.println("Produto removido com sucesso!");
            } else {
                System.out.println("Erro ao remover produto.");
            }
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    // função para editar senha do usuário
    private static void editarSenha() {
        System.out.print("Senha atual: ");
        String senhaAtual = scanner.nextLine();
        System.out.print("Nova senha: ");
        String novaSenha = scanner.nextLine();

        if (banco.editarUsuario(usuarioLogado.getId(), senhaAtual, novaSenha)) {
            System.out.println("Senha editada com sucesso!");
        } else {
            System.out.println("Erro ao editar senha.");
        }
    }

    // função para visualizar perfil do usuário
    private static void verPerfil() {
        String perfil = banco.verPerfil(usuarioLogado);
        System.out.println(perfil.isEmpty() ? "Usuário não encontrado." : perfil);
    }

    // chama a função para excluir conta do usuário
    private static void excluirConta() {
        if (banco.ExcluirConta(usuarioLogado.getId())) {
            System.out.println("Conta excluída com sucesso!");
            System.exit(0);
        } else {
            System.out.println("Erro ao excluir conta.");
        }
    }
}