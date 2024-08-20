package com.garagem.demo_rest_api.web.controller;

import com.garagem.demo_rest_api.entity.Usuario;
import com.garagem.demo_rest_api.service.UsuarioService;
import com.garagem.demo_rest_api.web.dto.UsuarioCreateDto;
import com.garagem.demo_rest_api.web.dto.UsuarioResponseDto;
import com.garagem.demo_rest_api.web.dto.UsuarioSenhaDto;
import com.garagem.demo_rest_api.web.dto.mapper.UsuarioMapper;
import com.garagem.demo_rest_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Array;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contém as operações sobre o usuário")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")

public class UsuarioController {

    private final UsuarioService usuarioService;
    @Operation(summary = "Criar um novo usuário", responses = {
            @ApiResponse(responseCode = "201", description = "sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "usuario já existe", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "erros na entrada", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }
    @Operation(summary = "buscar usuario pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "não encontrado", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }
    @Operation(summary = "atualizar senha", responses = {
            @ApiResponse(responseCode = "204", description = "sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = void.class))),
            @ApiResponse(responseCode = "404", description = "não encontrado", content = @Content(mediaType = "application/json",schema = @Schema(implementation = void.class))),
            @ApiResponse(responseCode = "400", description = "senha não confere", content = @Content(mediaType = "application/json",schema = @Schema(implementation = void.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@Valid @PathVariable Long id, @RequestBody UsuarioSenhaDto dto){
        Usuario user = usuarioService.editarSenha(id, dto.getPasswordAtual(), dto.getNovaPassword(), dto.getConfirmarPassword());
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "buscar todos os uruarios", responses = {
            @ApiResponse(responseCode = "200", description = "sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = UsuarioResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "não tem usuários cadastrados", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List<Usuario> users= usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
