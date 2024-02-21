package br.com.farmacia;

import java.util.Scanner;

import br.com.farmacia.modelo.Produto;

public class Main {
    private static Produto service = new Produto(0, null, null);
    private static Scanner teclado = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        var opcao = Menu();
        while (opcao != 8) {
            try {
                switch (opcao) {
                    case 1:
                        listarProdutos();
                        break;
                    case 2:
                        cadastrarProduto();
                        break;
                    case 3:
                        deletarProduto();
                        break;
                    case 4:
                        consultarProduto()
                        break;
                }
            } catch (RegraDeNegocioException e) {
                System.out.println("Erro: " +e.getMessage());
                System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu");
                teclado.next();
            }
            opcao = Menu();
        }

        System.out.println("Finalizando a aplicação.");
    }

    private static int Menu() {
        System.out.println("""
                FARMÁCIA - ESCOLHA UMA OPÇÃO:
                1 - Listar produtos
                2 - Cadastrar produto
                3 - Deletar produto
                4 - Consultar produto
                5 - Sair
                """);
        return teclado.nextInt();
    }

    private static void listarProdutos() {
        System.out.println("Produtos cadastrados:");
        var produtos = service.listarProdutos();
        produtos.stream().forEach(System.out::println);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void cadastrarProduto() {
        System.out.println("Digite o nome do prduto:");
        var nomeDoProduto = teclado.next();

        System.out.println("Digite o valor do produto:");
        var valorDoProduto = teclado.nextFloat();

        System.out.println("Digite o fabricante do produto:");
        var fabricanteDoProduto = teclado.next();

        service.cadastrar(new Produto(valorDoProduto, nomeDoProduto, fabricanteDoProduto));

        System.out.println("Produto Cadastrador com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void deletarProduto() {
        System.out.println("Digite o nome do produto:");
        var nomeDoProduto = teclado.next();

        service.encerrar(nomeDoProduto);

        System.out.println("Produto deletado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void consultarProduto() {
        System.out.println("Digite o nome do produto:");
        var nomeDoProduto = teclado.next();
        var produto = service.consultarProduto(nomeDoProduto);
        System.out.println(produto.toString());

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    // private static void realizarSaque() {
    //     System.out.println("Digite o número da conta:");
    //     var numeroDaConta = teclado.nextInt();

    //     System.out.println("Digite o valor do saque:");
    //     var valor = teclado.nextBigDecimal();

    //     service.realizarSaque(numeroDaConta, valor);
    //     System.out.println("Saque realizado com sucesso!");
    //     System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
    //     teclado.next();
    // }

    // private static void realizarDeposito() {
    //     System.out.println("Digite o número da conta:");
    //     var numeroDaConta = teclado.nextInt();

    //     System.out.println("Digite o valor do depósito:");
    //     var valor = teclado.nextBigDecimal();

    //     service.realizarDeposito(numeroDaConta, valor);

    //     System.out.println("Depósito realizado com sucesso!");
    //     System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
    //     teclado.next();
    // }

    // private static void realizarTransferencia() {
    //     System.out.println("Digite o número da conta de origem:");
    //     var numeroDaContaOrigem = teclado.nextInt();

    //     System.out.println("Digite o número da conta de destino:");
    //     var numeroDaContaDestino = teclado.nextInt();

    //     System.out.println("Digite o valor a ser transferido:");
    //     var valor = teclado.nextBigDecimal();

    //     service.realizarTransferencia(numeroDaContaOrigem, numeroDaContaDestino, valor);

    //     System.out.println("Transferência realizada com sucesso!");
    //     System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
    //     teclado.next();
    // }

}