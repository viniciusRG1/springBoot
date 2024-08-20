package com.garagem.demo_rest_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UsuarioSenhaDto {
    @NotBlank
    @Size(min = 6, max = 6)
    private String passwordAtual;
    @NotBlank
    @Size(min = 6, max = 6)
    private String novaPassword;
    @NotBlank
    @Size(min = 6, max = 6)
    private String confirmarPassword;
}
