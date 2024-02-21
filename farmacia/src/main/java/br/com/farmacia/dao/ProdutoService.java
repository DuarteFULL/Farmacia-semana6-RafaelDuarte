package br.com.farmacia.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import br.com.farmacia.RegraDeNegocioException;
import br.com.farmacia.modelo.Produto;
import br.com.farmacia.dao.ConnectionFactory;

public class ProdutoService {

    private Set<Produto> produtos = new HashSet<>();

    private ConnectionFactory connection;

    public ProdutoService() {
        this.connection = new ConnectionFactory();
    }

    public Set<Produto> listarProdutos() {
        Connection conn = connection.recuperarConexao();
        return new ProdutoDAO(conn).listar();
    }

    private void alterar(Produto produto, String novoFabricante, double novoValor) {
        Connection conn = connection.recuperarConexao();
        new ProdutoDAO(conn).alterar(produto.getNome(), novoFabricante, novoValor);
    }

    private Produto buscarProdutoPorNome(String nome) {
        Connection conn = connection.recuperarConexao();
        Conta produto = new ProdutoDAO(conn).listarPorNome(nome);
        if(produto != null) {
            return produto;
        } else {
            throw new RegraDeNegocioException("Não existe produto cadastrado com esse nome!");
        }
    }

    // public BigDecimal consultarSaldo(Integer numeroDaConta) {
    //     var conta = buscarContaPorNumero(numeroDaConta);
    //     return conta.getSaldo();
    // }

    // public void abrir(DadosAberturaConta dadosDaConta) {
    //     Connection conn = connection.recuperarConexao();
    //     new ContaDAO(conn).salvar(dadosDaConta);
    // }

    // public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
    //     var conta = buscarContaPorNumero(numeroDaConta);

    //     if (valor.compareTo(BigDecimal.ZERO) <= 0) {
    //         throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
    //     }

    //     if (valor.compareTo(conta.getSaldo()) > 0) {
    //         throw new RegraDeNegocioException("Saldo insuficiente!");
    //     }

    //     BigDecimal novoValor = conta.getSaldo().subtract(valor);
    //     alterar(conta, novoValor);
    // }

    // public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
    //     var conta = buscarContaPorNumero(numeroDaConta);

    //     if (valor.compareTo(BigDecimal.ZERO) <= 0) {
    //         throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
    //     }

    //     BigDecimal novoValor = conta.getSaldo().add(valor);
    //     alterar(conta, novoValor);
    // }

    // public void realizarTransferencia(Integer numeroDaContaOrigem, Integer numeroDaContaDestino,
    //                                   BigDecimal valor) {
    //     this.realizarSaque(numeroDaContaOrigem, valor);
    //     this.realizarDeposito(numeroDaContaDestino, valor);
    // }

    // public void encerrar(Integer numeroDaConta) {
    //     var conta = buscarContaPorNumero(numeroDaConta);
    //     if (conta.possuiSaldo()) {
    //         throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
    //     }

    //     contas.remove(conta);
    // }

    
}

