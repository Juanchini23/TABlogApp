package com.blog.Apiblog.controller;

import com.blog.Apiblog.dto.ComentarioDto;
import com.blog.Apiblog.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ComentarioController {

    @Autowired
    private ComentarioService serviceComentario;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDto> verComentariosPorPublicacion(@PathVariable("publicacionId") Long idPubli){

        return serviceComentario.obtenerComentariosPorPublicacionId(idPubli);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDto> verComentarioPorIdYPublicacion(@PathVariable(value = "publicacionId")Long idPubli,
                                                                        @PathVariable(value = "comentarioId")Long idComentario){


        return new ResponseEntity<>(serviceComentario.obtenerComentarioPorId(idPubli, idComentario),HttpStatus.OK);
    }

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDto> guardarComentario(@PathVariable(value = "publicacionId") Long idPubli,
                                                           @Valid @RequestBody ComentarioDto comentarioDto){
        return new ResponseEntity<>(serviceComentario.crearComentario(idPubli, comentarioDto), HttpStatus.CREATED);
    }


    @PutMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDto> actualizarComentario(@Valid @RequestBody ComentarioDto comentarioDto,
                                                              @PathVariable(value = "publicacionId")Long idPubli,
                                                              @PathVariable(value = "comentarioId")Long idComentario){

        return new ResponseEntity<>(serviceComentario.actualizarComentario(idPubli, idComentario, comentarioDto),HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<String> eliminarComentario(@PathVariable(value = "publicacionId")Long idPubli,
                                                     @PathVariable(value = "comentarioId")Long idComentario){
        serviceComentario.eliminarComentario(idPubli,idComentario);
        return new ResponseEntity<>("Comentario eliminado correctamente",HttpStatus.OK);
    }

}
