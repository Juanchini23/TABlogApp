package com.blog.Apiblog.service;

import com.blog.Apiblog.dto.PublicacionDto;
import com.blog.Apiblog.dto.PublicacionRespuesta;
import com.blog.Apiblog.excepciones.ResourceNotFoundException;
import com.blog.Apiblog.model.Publicacion;
import com.blog.Apiblog.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionService{

    @Autowired
    private PublicacionRepository repoPublicacion;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PublicacionDto crearPublicacion(PublicacionDto publicacionDto) {
        Publicacion publicacion = mapearEntidad(publicacionDto);

        Publicacion nuevaPublicacion = repoPublicacion.save(publicacion);

        PublicacionDto publicacionResponse = mapearDto(nuevaPublicacion);

        return publicacionResponse;
    }

    @Override
    public PublicacionRespuesta obtenerPublicaciones(Integer numeroPagina, Integer medidaPaginas, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(ordenarPor).ascending() : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroPagina, medidaPaginas, sort);
        Page<Publicacion> publicaciones = repoPublicacion.findAll(pageable);

        List<Publicacion> publicacionesPaginadas = publicaciones.getContent();
        List<PublicacionDto> contenido = publicacionesPaginadas.stream().map(publicacion -> mapearDto(publicacion)).collect(Collectors.toList());

        PublicacionRespuesta respuesta = new PublicacionRespuesta();
        respuesta.setContenido(contenido);
        respuesta.setNumeroPaginas(publicaciones.getNumber());
        respuesta.setMedidaPagina(publicaciones.getSize());
        respuesta.setTotalElementos(publicaciones.getTotalElements());
        respuesta.setTotalPaginas(publicaciones.getTotalPages());
        respuesta.setUltima(publicaciones.isLast());

        return respuesta;

    }

    @Override
    public PublicacionDto obtenerPublicacionId(Long id) {
        Publicacion publicacion = repoPublicacion.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", id));

        return mapearDto(publicacion);
    }

    @Override
    public PublicacionDto editarPublicacion(PublicacionDto publicacionDto, Long id) {

        Publicacion publicacion = repoPublicacion.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", id));
        publicacion.setTitulo(publicacionDto.getTitulo());
        publicacion.setContenido(publicacionDto.getContenido());
        publicacion.setDescripcion(publicacionDto.getDescripcion());

        Publicacion actualizada = repoPublicacion.save(publicacion);
        return mapearDto(actualizada);
    }

    @Override
    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = repoPublicacion.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", id));

        repoPublicacion.delete(publicacion);
    }

    //convierte entidad a dto
    private PublicacionDto mapearDto(Publicacion publicacion){
        PublicacionDto publicacionDto = modelMapper.map(publicacion, PublicacionDto.class);

        return publicacionDto;
    }

    // convierte dto a entidad
    private Publicacion mapearEntidad(PublicacionDto publicacionDto){

        Publicacion publicacion = modelMapper.map(publicacionDto, Publicacion.class);

        return publicacion;
    }
}
