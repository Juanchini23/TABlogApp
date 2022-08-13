package com.blog.Apiblog.controller;

import com.blog.Apiblog.dto.PublicacionDto;
import com.blog.Apiblog.dto.PublicacionRespuesta;
import com.blog.Apiblog.service.PublicacionService;
import com.blog.Apiblog.utilerias.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService servicePublicacion;

    @GetMapping
    public PublicacionRespuesta listarPublicaciones(@RequestParam(value = "numero", defaultValue = AppConstantes.NUMERO_PAGINA_DEFECTO, required = false) Integer numeroPagina,
                                                    @RequestParam(value="size", defaultValue = AppConstantes.MEDIDA_PAGINA_DEFECTO, required = false) Integer medidaPaginas,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO,required = false) String ordenarPor,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_DEFECTO, required = false) String sortDir){
        return servicePublicacion.obtenerPublicaciones(numeroPagina, medidaPaginas, ordenarPor, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDto> traerPublicacionId(@PathVariable(name="id") Long id){
        return ResponseEntity.ok(servicePublicacion.obtenerPublicacionId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PublicacionDto> guardarPublicacion(@Valid @RequestBody PublicacionDto publicacionDto){

        return new ResponseEntity<>(servicePublicacion.crearPublicacion(publicacionDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDto> editarPublicacion(@Valid @RequestBody PublicacionDto publicacionDto,
                                                            @PathVariable(name="id") Long id){

        return new ResponseEntity<>(servicePublicacion.editarPublicacion(publicacionDto, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable(name="id") Long id){
        servicePublicacion.eliminarPublicacion(id);
        return new ResponseEntity<String>("Publicacion eliminada con exito", HttpStatus.OK);
    }

}
