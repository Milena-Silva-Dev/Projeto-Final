package com.mimepapelaria.Service;

import com.mimepapelaria.Exception.ResourceNotFoundException;
import com.mimepapelaria.Model.Produto;
import com.mimepapelaria.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto save(Produto product) {
        return produtoRepository.save(product);
    }

    public Produto update(Long id, Produto product) {
        if (produtoRepository.existsById(id)) {
            product.setId(Math.toIntExact(id));
            return produtoRepository.save(product);
        }
        throw new ResourceNotFoundException("Produto não encontrado!");
    }

    public boolean delete(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("Produto não encontrado!");
        }
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado!"));
    }

    @Transactional
    public Produto atualizarProdutoParcial(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado!"));

        if (produtoAtualizado.getNome() != null) {
            produtoExistente.setNome(produtoAtualizado.getNome());
        }
        if (produtoAtualizado.getQuantidade() != 0) {
            produtoExistente.setQuantidade(produtoAtualizado.getQuantidade());
        }
        if (produtoAtualizado.getDescricao() != null) {
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        }
        if (produtoAtualizado.getImagem() != null) {
            produtoExistente.setImagem(produtoAtualizado.getImagem());
        }
        if (produtoAtualizado.getPreco() != 0) {
            produtoExistente.setPreco(produtoAtualizado.getPreco());
        }
        if (produtoAtualizado.getCategoria() != null) {
            produtoExistente.setCategoria(produtoAtualizado.getCategoria());
        }
        if (produtoAtualizado.getEstoque() != 0) {
            produtoExistente.setEstoque(produtoAtualizado.getEstoque());
        }

        return produtoRepository.save(produtoExistente);
    }
}
