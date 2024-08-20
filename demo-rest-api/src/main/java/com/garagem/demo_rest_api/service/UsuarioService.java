package com.garagem.demo_rest_api.service;

import com.garagem.demo_rest_api.entity.Usuario;
import com.garagem.demo_rest_api.exception.EntityNotFoundException;
import com.garagem.demo_rest_api.exception.PasswordInvalidException;
import com.garagem.demo_rest_api.exception.UsernameUniqueViolationException;
import com.garagem.demo_rest_api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional

    public Usuario salvar(Usuario usuario) {
        try{
        return usuarioRepository.save(usuario);
    }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("O Username {%s} já esta cadastrado", usuario.getUsername()));
        }
    }

    @Transactional

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional

    public Usuario editarSenha(Long id, String passwordAtual, String novaPassword, String confirmarPassword){
        if(!novaPassword.equals(confirmarPassword)){
            throw new PasswordInvalidException("nova senha não foi confirmada");
        }
        Usuario user = buscarPorId(id);
        if(!user.getPassword().equals(passwordAtual)){
            throw new PasswordInvalidException("sua senha não confere");
        }
        user.setPassword(novaPassword);
        return user;
    }


    @Transactional
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
}
