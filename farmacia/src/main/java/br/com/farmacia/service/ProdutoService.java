package br.com.farmacia.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import com.mysql.cj.conf.PropertyDefinition;

import br.com.farmacia.modelo.RegraDeNegocioException;
import br.com.farmacia.modelo.Produto;
import br.com.farmacia.dao.ConnectionFactory;
import br.com.farmacia.dao.ProdutoDAO;

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

    public void alterarValorProduto(Produto produto, float novoValor) {
        Connection conn = connection.recuperarConexao();
        new ProdutoDAO(conn).alterarValor(produto.getNome(), novoValor);
    }

    public void alterarFabricanteProduto(Produto produto, String novoFabricante) {
        Connection conn = connection.recuperarConexao();
        new ProdutoDAO(conn).alterarFabricante(produto.getNome(), novoFabricante);
    }

    public Produto buscarProdutoPorNome(String nome) {
        Connection conn = connection.recuperarConexao();
        Produto produto = new ProdutoDAO(conn).listarPorNome(nome);
        if(produto != null) {
            return produto;
        } else {
            throw new RegraDeNegocioException("Não existe produto cadastrado com esse nome!");
        }
    }

    public void cadastrar(Produto dadosDoProduto) {
        Connection conn = connection.recuperarConexao();
        new ProdutoDAO(conn).salvar(dadosDoProduto);
    }

    public void deletar(String nomeDoProduto) {
        Connection conn = connection.recuperarConexao();

        Produto produto = buscarProdutoPorNome(nomeDoProduto);

        new ProdutoDAO(conn).remove(produto.getNome());
    }

    // public BigDecimal consultarSaldo(Integer numeroDaConta) {
    //     var conta = buscarContaPorNumero(numeroDaConta);
    //     return conta.getSaldo();
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

    

    
}

