package com.blog.Apiblog.service;

import com.blog.Apiblog.dto.PublicacionDto;
import com.blog.Apiblog.dto.PublicacionRespuesta;
import java.util.List;

public interface PublicacionService {

    public PublicacionDto crearPublicacion(PublicacionDto publicacionDto);

    public PublicacionRespuesta obtenerPublicaciones(Integer numeroPagina, Integer medidaPaginas, String ordenarPor, String sortDir);

    public PublicacionDto obtenerPublicacionId(Long id);

    public PublicacionDto editarPublicacion(PublicacionDto publicacionDto, Long id);

    public void eliminarPublicacion(Long id);
}
