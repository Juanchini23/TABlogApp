package com.blog.Apiblog.repository;

import com.blog.Apiblog.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByPublicacionId(Long idPubli);
}
