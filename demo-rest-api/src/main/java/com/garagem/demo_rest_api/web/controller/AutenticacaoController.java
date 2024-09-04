package com.garagem.demo_rest_api.web.controller;

import com.garagem.demo_rest_api.jwt.JwtToken;
import com.garagem.demo_rest_api.jwt.JwtUserDetailsService;
import com.garagem.demo_rest_api.web.dto.mapper.UsuarioLoginDto;
import com.garagem.demo_rest_api.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AutenticacaoController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")

    public ResponseEntity<?> autenticcar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request){
        log.info("processo de autenticação pelo login{}", dto.getUsername());
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthentication(dto.getUsername());
            return ResponseEntity.ok(token);
        }catch (AuthenticationException ex){
            log.warn("bad credentials from username '{}'", dto.getUsername());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "CREDENCIAIS INVALIDAS"));
    }
}
