package com.arthur.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor")
public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 2,max = 100, message = "Máximo de caracteres ultrapassado")
        String nome,

        @NotNull(message = "campo obrigatório")
        @Past
        LocalDate dataNascimento,

        @NotBlank(message = "campo obrigatório")
        @Size(min = 1, max = 50, message = "Máximo de caracteres ultrapassado")
        String nacionalidade) {



}
