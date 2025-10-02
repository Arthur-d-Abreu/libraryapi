package com.arthur.libraryapi.controller.mappers;
import com.arthur.libraryapi.controller.dto.UsuarioDTO;
import com.arthur.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
