package com.arthur.libraryapi.repository;

import com.arthur.libraryapi.model.Autor;
import com.arthur.libraryapi.model.GeneroLivro;
import com.arthur.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("12345-36789");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

         Autor autor = autorRepository
                .findById(UUID.fromString("874be604-b276-44fc-a4e5-4d8d88489835"))
                .orElse(null);

       livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    public void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("12345-36789");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1951, 12, 12));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    public void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("12345-36789");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1951, 12, 12));

        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    public void atualizarAutorDoLivroTest(){
        UUID id = UUID.fromString("fee8d247-fa7d-40d3-92fa-fecad6e2273c");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("47d0173d-c121-4d90-89d1-27fd687c53fb");
        Autor maria = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);

    }

    @Test
    public void deletarPorIdTest(){
        UUID id = UUID.fromString("fee8d247-fa7d-40d3-92fa-fecad6e2273c");
        repository.deleteById(id);
    }


    @Test
    public void deletarCascadeTest(){ //Vai deletar o livro e o autor, porém apenas se tiver o @ManyToOne(cascade = CascadeType.ALL) no livro
        UUID id = UUID.fromString("e0006dac-7f3a-4c18-b0fd-0c2a3524683e");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    public void buscarLivroTest(){
        UUID id = UUID.fromString("ef478421-5f07-4fe8-a14d-98121a332dac");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    public void pesquisaPorTituloTest(){
        List<Livro> lista = repository.findByTitulo("A casa assombrada");
        lista.forEach(System.out::println);
    }

    @Test
    public void pesquisarPorIsbn(){
        Optional<Livro> livro = repository.findByIsbn("12345-36789");
        livro.ifPresent(System.out::println);
    }
    @Test
    public void pesquisarPorTituloEPrecoTest(){
        var preco = BigDecimal.valueOf(120);
        var tituloPesquisa = "A chave";

        List<Livro> lista = repository.findByTituloAndPreco(tituloPesquisa, preco);
        lista.forEach(System.out::println);
    }

    @Test
    public void pesquisarEntreDataPublicacaoTest(){
        var dataInicio = LocalDate.of(1900, 1, 1);
        var dataFinal = LocalDate.of(1980, 1, 1);

        List<Livro> lista = repository.findByDataPublicacaoBetween(dataInicio, dataFinal);
        lista.forEach(System.out::println);
    }

    @Test
    public void listarLivrosComQueryJPQLTest(){
        var resultadoPesquisa = repository.listarTodosOrdenadoPorTituloEPreco();
        resultadoPesquisa.forEach(System.out::println);
    }

    @Test
    @Transactional
    public void listarAutoresDosLivrosTest(){
        var resultadoPesquisa = repository.listarAutoresDosLivros();
        resultadoPesquisa.forEach(System.out::println);
    }

    @Test
    public void listarTitulosNaoRepetidosTest(){
        var resultadoPesquisa = repository.listarDiferentesLivros();
        resultadoPesquisa.forEach(System.out::println);
    }

    @Test
    public void listarGenerosDeAutoresBrasileirosTest(){
        var resultadoPesquisa = repository.listarGenerosDeAutoresBrasileiros();
        resultadoPesquisa.forEach(System.out::println);
    }

    @Test
    public void listarGeneroQueryParamTest(){
        var resultadoPesquisa = repository.findByGenero(GeneroLivro.MISTERIO, "preco");
        resultadoPesquisa.forEach(System.out::println);
    }

    @Test
    public void listarGeneroQueryPositionalParamTest(){
        var resultadoPesquisa = repository.findByGenero(GeneroLivro.MISTERIO, "preco");
        resultadoPesquisa.forEach(System.out::println);
    }

    @Test
    public void deletePorGeneroTest(){
        repository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    public void updateDataPublicacaoTest(){
        repository.updateDataPublicacao(LocalDate.of(2000, 1, 1));
    }
}