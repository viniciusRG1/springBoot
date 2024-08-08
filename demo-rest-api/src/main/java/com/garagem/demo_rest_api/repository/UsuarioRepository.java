package com.garagem.demo_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.garagem.demo_rest_api.entity.Usuario;

@Repository

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}