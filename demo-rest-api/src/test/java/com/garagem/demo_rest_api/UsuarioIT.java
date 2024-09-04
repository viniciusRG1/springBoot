package com.garagem.demo_rest_api;

import com.garagem.demo_rest_api.web.dto.UsuarioCreateDto;
import com.garagem.demo_rest_api.web.dto.UsuarioResponseDto;
import com.garagem.demo_rest_api.web.dto.UsuarioSenhaDto;
import com.garagem.demo_rest_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)



public class UsuarioIT {
    @Autowired
    WebTestClient testeClient;

    @Test
    public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201(){
        UsuarioResponseDto responseBody = testeClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("toddy@nescal.com", "123456"))
                .exchange().expectStatus().isCreated().expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();
    }
    @Test
    public void createUsuario_ComUsernameInvalido_RetornarErrorComStatus422(){
        ErrorMessage responseBody = testeClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("", "123456"))
                .exchange().expectStatus().isCreated().expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
    }
    @Test
    public void createUsuario_ComPasswordInvalido_RetornarErrorComStatus422(){
        ErrorMessage responseBody = testeClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tobias@APC.com", "12345"))
                .exchange().expectStatus().isCreated().expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
    }
    public void createUsuario_ComUsernameRepetido_RetornarErrorComStatus409(){
        ErrorMessage responseBody = testeClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("toddy@nescal.com", "123456"))
                .exchange().expectStatus().isEqualTo(409).expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
    }
    @Test
    public void buscarUsuario_ComUsernameEPasswordValidos_RetornarUsuarioComStatus200(){
        UsuarioResponseDto responseBody = testeClient.get().uri("/api/v1/usuarios/100")
                .exchange().expectStatus().isOk().expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();
    }
    @Test
    public void buscarUsuario_ComUsernameIValidos_RetornarErrrorComStatus404(){
        ErrorMessage responseBody = testeClient.get().uri("/api/v1/usuarios/100")
                .exchange().expectStatus().isOk().expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
    }
    @Test
    public void editarSenha_PasswordValidos_RetornarSenhaModificadaComStatus204(){
        testeClient.patch().uri("/api/v1/usuarios/100").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123457","123457"))
                .exchange().expectStatus().isNoContent();
    }
    @Test
    public void editarSenha_PasswordInValidos_RetornarErrorComStatus404(){
        testeClient.patch().uri("/api/v1/usuarios/0").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123457","123457"))
                .exchange().expectStatus().isNoContent();
    }
    @Test
    public void listarUsuarios_SemParametro_RetornarListaComStatus200(){
        testeClient.get().uri("/api/v1/usuarios").exchange().expectStatus().isOk()
                .expectBodyList(UsuarioResponseDto.class).returnResult().getResponseBody();
    }
}
