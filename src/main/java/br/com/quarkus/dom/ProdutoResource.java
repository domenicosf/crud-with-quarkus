package br.com.quarkus.dom;

import br.com.quarkus.dom.models.CadastrarProdutoDTO;
import br.com.quarkus.dom.models.Produto;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @GET
    public List<Produto> buscarTodosProdutos(){
        return Produto.listAll();
    }

    @POST
    @Transactional
    public void inserirProduto(CadastrarProdutoDTO produtoDTO){
        Produto p = new Produto();
        p.nome = produtoDTO.nome;
        p.valor = produtoDTO.valor;
        p.persist();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizarProduto(@PathParam("id") Long id, CadastrarProdutoDTO produtoDTO){
        Optional<Produto> produtoOptional = Produto.findByIdOptional(id);
        if(produtoOptional.isPresent()){
            Produto produto = produtoOptional.get();
            produto.nome = produtoDTO.nome;
            produto.valor = produtoDTO.valor;
            produto.persist();
        }else{
            throw new NotFoundException("Produto informado nao encontrado!!!");
        }
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void removeProduto(@PathParam("id") Long id){
        Optional<Produto> produtoOptional = Produto.findByIdOptional(id);
        if(produtoOptional.isPresent()){
            Produto produto = produtoOptional.get();
            produto.delete();
        }else{
            throw new NotFoundException("O produto nao foi encontrado");
        }
    }
}
