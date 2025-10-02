package com.arthur.libraryapi.controller.mappers;

import com.arthur.libraryapi.controller.dto.CadastroLivroDTO;
import com.arthur.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.arthur.libraryapi.model.Livro;
import com.arthur.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapperImpl.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.id_autor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO dto);


    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
