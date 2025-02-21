package com.br.Lojinha.Lojinha.repository;

import com.br.Lojinha.Lojinha.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
