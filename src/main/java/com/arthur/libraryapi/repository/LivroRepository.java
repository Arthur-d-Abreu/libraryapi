package com.arthur.libraryapi.repository;

import com.arthur.libraryapi.model.Autor;
import com.arthur.libraryapi.model.GeneroLivro;
import com.arthur.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    // Query Method
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    Optional<Livro> findByIsbn(String isbn);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //JPQL -> referencia as entidades e as propriedades
    //select l.* from livro as l order by l.titulo
    @Query(" select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloEPreco();

    //select a.* from livro l join autor a on a.id = l.id_autor
    @Query(" select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    //select distinct l.* from livro l
    @Query("select distinct l.titulo from Livro l")
    List<String> listarDiferentesLivros();

    @Query("""
        select l.genero 
        from Livro l  
        join l.autor a 
        where a.nacionalidade = 'Brasileira'
        order by l.genero
    """)
    List<String> listarGenerosDeAutoresBrasileiros();

    //named parameters -> parâmetros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String paramOrdenacao);

    //positional parameters
    @Query(" select l from Livro l where l.genero = ?1 order by ?2 ")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro,String paramOrdenacao);

    @Modifying
    @Transactional
    @Query(" delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query(" update Livro set dataPublicacao = ?1 ")
    void updateDataPublicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);


}
