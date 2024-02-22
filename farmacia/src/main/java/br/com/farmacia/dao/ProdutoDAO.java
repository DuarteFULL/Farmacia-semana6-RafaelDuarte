package br.com.farmacia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.farmacia.modelo.Produto;

public class ProdutoDAO {

    private Connection conn;

    public ProdutoDAO(Connection connection) {
        this.conn = connection;
    }

    public void salvar(Produto dadosDoProduto) {
        var produtoNovo = new Produto(dadosDoProduto.getPreco(), dadosDoProduto.getNome(), dadosDoProduto.getFabricante());
        
        String sql = "INSERT INTO produtos (preco, nome, fabricante)" +
                "VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setDouble(1, produtoNovo.getPreco());
            preparedStatement.setString(2, produtoNovo.getNome());
            preparedStatement.setString(3, produtoNovo.getFabricante());
            
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Produto> listar() {
        PreparedStatement ps;
        ResultSet resultSet;
        Set<Produto> produtos = new HashSet<>();

        String sql = "SELECT * FROM produtos"; //WHERE esta_ativa = true";

        try {
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                float preco = resultSet.getFloat(2);
                String nome = resultSet.getString(3);
                String fabricante = resultSet.getString(4);

                produtos.add(new Produto(preco, nome, fabricante));
            }
            resultSet.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produtos;
    }

    public Produto listarPorNome(String nomeConsultar) {
        String sql = "SELECT * FROM produtos WHERE nome = '" + nomeConsultar+"'";// + " and esta_ativa = true";

        PreparedStatement ps;
        ResultSet resultSet;
        Produto produto = null;
        try {
            ps = conn.prepareStatement(sql);
            //ps.setInt(1, nome);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                float preco = resultSet.getFloat(2);
                String nome = resultSet.getString(3);
                String fabricante = resultSet.getString(4);

                // DadosCadastroCliente dadosCadastroCliente =
                //         new DadosCadastroCliente(nome, cpf, email);
                // Cliente cliente = new Cliente(dadosCadastroCliente);

                produto = new Produto(preco, nome, fabricante);
            }
            resultSet.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produto;
    }

    public void alterar(Integer numero, BigDecimal valor) {
        PreparedStatement ps;
        String sql = "UPDATE conta SET saldo = ? WHERE numero = ?";

        try {
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql);

            ps.setBigDecimal(1, valor);
            ps.setInt(2, numero);

            ps.execute();
            ps.close();
            conn.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
