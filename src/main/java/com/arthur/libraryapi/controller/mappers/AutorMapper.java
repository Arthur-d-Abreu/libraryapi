package com.arthur.libraryapi.controller.mappers;

import com.arthur.libraryapi.controller.dto.AutorDTO;
import com.arthur.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor autor);

}
