package br.com.farmacia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.farmacia.modelo.Produto;

public class ProdutoDAO {

    private Connection conn;

    ProdutoDAO(Connection connection) {
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
        System.out.println(sql);

        try {
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();

            System.out.println(sql);

            while (resultSet.next()) {
                float preco = resultSet.getDouble(1);
                String nome = resultSet.getString(2);
                String fabricante = resultSet.getString(3);
                

                DadosCadastroProduto dadosCadastroProduto =
                        new DadosCadastroProduto(preco, nome, fabricante);
                Produto produto = new Produto(dadosCadastroProduto);
                System.out.println(produto.toString()   );

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

    public Conta listarPorNumero(Integer numero) {
        String sql = "SELECT * FROM conta WHERE numero = " + numero + " and esta_ativa = true";

        PreparedStatement ps;
        ResultSet resultSet;
        Conta conta = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, numero);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Integer numeroRecuperado = resultSet.getInt(1);
                BigDecimal saldo = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);

                DadosCadastroCliente dadosCadastroCliente =
                        new DadosCadastroCliente(nome, cpf, email);
                Cliente cliente = new Cliente(dadosCadastroCliente);

                conta = new Conta(numeroRecuperado, saldo, cliente);
            }
            resultSet.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conta;
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
