package br.com.farmacia.service;

import java.sql.Connection;
import java.util.Set;


import br.com.farmacia.modelo.RegraDeNegocioException;
import br.com.farmacia.modelo.Produto;
import br.com.farmacia.dao.ConnectionFactory;
import br.com.farmacia.dao.ProdutoDAO;

public class ProdutoService {


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
    
}

