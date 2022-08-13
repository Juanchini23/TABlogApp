package com.blog.Apiblog.dto;

import com.blog.Apiblog.model.Publicacion;
import org.springframework.stereotype.Service;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ComentarioDto {

    private Long id;
    @NotEmpty(message = "El nombre no debe estar vacio")
    private String nombre;
    @NotEmpty(message = "El email no debe estar vacio o nulo")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 10, message = "El cuerpo debe tener al menos 10 carateres")
    private String cuerpo;
    private Publicacion publicacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public ComentarioDto() {
    }
}
