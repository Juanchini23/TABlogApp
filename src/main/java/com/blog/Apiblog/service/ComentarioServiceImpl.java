package com.blog.Apiblog.service;

import com.blog.Apiblog.dto.ComentarioDto;
import com.blog.Apiblog.excepciones.BlogAppExcepcion;
import com.blog.Apiblog.excepciones.ResourceNotFoundException;
import com.blog.Apiblog.model.Comentario;
import com.blog.Apiblog.model.Publicacion;
import org.hibernate.annotations.CreationTimestamp;
import com.blog.Apiblog.repository.ComentarioRepository;
import com.blog.Apiblog.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService{

    @Autowired
    private ComentarioRepository repoComentario;

    @Autowired
    private PublicacionRepository repoPublicacion;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ComentarioDto crearComentario(Long idPubli, ComentarioDto comentarioDto) {

        Comentario comentario = mapearEntidad(comentarioDto);
        Publicacion publicacion = repoPublicacion.findById(idPubli)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", idPubli));
        comentario.setPublicacion(publicacion);
        Comentario comentarioResponse = repoComentario.save(comentario);

        return mapearDto(comentarioResponse);
    }

    @Override
    public List<ComentarioDto> obtenerComentariosPorPublicacionId(Long idPubli) {

        List<Comentario> comentarios = repoComentario.findByPublicacionId(idPubli);

        return comentarios.stream().map(comentario -> mapearDto(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDto obtenerComentarioPorId(Long publicacionId, Long idComentario) {

        Publicacion publicacion = repoPublicacion.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentario comentario = repoComentario.findById(idComentario)
                .orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", idComentario));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }

        return mapearDto(comentario);
    }

    @Override
    public ComentarioDto actualizarComentario(Long publicacionId, Long idComentario, ComentarioDto comentarioDto) {
        Publicacion publicacion = repoPublicacion.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentario comentario = repoComentario.findById(idComentario)
                .orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", idComentario));

        if(!(comentario.getPublicacion().getId().equals(publicacion.getId()))){
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }

        comentario.setNombre(comentarioDto.getNombre());
        comentario.setEmail(comentarioDto.getEmail());
        comentario.setCuerpo(comentarioDto.getCuerpo());
        Comentario response = repoComentario.save(comentario);
        return mapearDto(response);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long idComentario) {
        Publicacion publicacion = repoPublicacion.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentario comentario = repoComentario.findById(idComentario)
                .orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", idComentario));

        if(!(comentario.getPublicacion().getId().equals(publicacion.getId()))){
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }

        repoComentario.delete(comentario);
    }

    private ComentarioDto mapearDto(Comentario comentario){
        ComentarioDto comentarioDto = modelMapper.map(comentario, ComentarioDto.class);
        return comentarioDto;
    }

    private Comentario mapearEntidad(ComentarioDto comentarioDto){
        Comentario comentario = modelMapper.map(comentarioDto, Comentario.class);
        return comentario;
    }
}
