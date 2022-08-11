package com.blog.Apiblog.repository;

import com.blog.Apiblog.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
}
