package com.blog.Apiblog.service;

import com.blog.Apiblog.dto.ComentarioDto;

import java.util.List;

public interface ComentarioService {


    ComentarioDto crearComentario(Long idPubli, ComentarioDto comentarioDto);
    
    List<ComentarioDto> obtenerComentariosPorPublicacionId(Long idPubli);

    ComentarioDto obtenerComentarioPorId(Long publicacionId, Long idComentario);

    ComentarioDto actualizarComentario(Long idPublicacion ,Long idComentario, ComentarioDto comentarioDto);

    void eliminarComentario(Long idPublicacion ,Long idComentario);
}
