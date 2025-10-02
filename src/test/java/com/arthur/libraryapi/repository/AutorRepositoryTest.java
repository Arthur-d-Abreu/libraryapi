package com.arthur.libraryapi.repository;

import com.arthur.libraryapi.model.Autor;
import com.arthur.libraryapi.model.GeneroLivro;
import com.arthur.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1951, 12, 12));

        var autorSalvo = repository.save(autor);

        System.out.println("Autor salvo: " + autorSalvo);

    }

    @Test
    public void atualizarTest(){
       var id = UUID.fromString("17c451f0-ed25-4297-b1cf-9d5f1e5be737");
       Optional<Autor> possivelAutor = repository.findById(id);
       if(possivelAutor.isPresent()){

           Autor autorEncontrado = possivelAutor.get();

           System.out.println("Dados do Autor: ");
           System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));

            repository.save(autorEncontrado);
       }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    //deleta por Id
    @Test
    public void deletePorIDTest(){
        var id = UUID.fromString("17c451f0-ed25-4297-b1cf-9d5f1e5be737");
        repository.deleteById(id);
    }

    //Deleta por Object
    @Test
    public void deleteTest(){
        var id = UUID.fromString("ad1765ee-60fa-478a-a0cb-923b0d3d3d8e");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    void salvarAutorComLivroTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1890, 05, 20));

        Livro livro = new Livro();
        livro.setIsbn("42345-56789");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("A casa assombrada");
        livro.setDataPublicacao(LocalDate.of(1920, 1, 2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("56849-98364");
        livro2.setPreco(BigDecimal.valueOf(120));
        livro2.setGenero(GeneroLivro.FANTASIA);
        livro2.setTitulo("A chave");
        livro2.setDataPublicacao(LocalDate.of(1927, 4, 30));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());

    }

    @Test
    public void listarLivroAutorTest(){
        var id = UUID.fromString("874be604-b276-44fc-a4e5-4d8d88489835");
        var autor = repository.findById(id).get();

        List<Livro> livroLista = livroRepository.findByAutor(autor);
        autor.setLivros(livroLista);

        autor.getLivros().forEach(System.out::println);
    }


}
