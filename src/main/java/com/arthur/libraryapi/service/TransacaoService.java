package com.arthur.libraryapi.service;

import com.arthur.libraryapi.model.Autor;
import com.arthur.libraryapi.model.GeneroLivro;
import com.arthur.libraryapi.model.Livro;
import com.arthur.libraryapi.repository.AutorRepository;
import com.arthur.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Transactional
    public void atualizarSemAtualizar(){
        var livro = livroRepository
                .findById(UUID.fromString("ef478421-5f07-4fe8-a14d-98121a332dac"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 06, 01));
    }

    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("Teste Francisco");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1951, 12, 12));

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("12345-36789");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Livro teste Francisco");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("Teste Francisco")){
            throw  new RuntimeException("RollBack!");
        }

    }
}
