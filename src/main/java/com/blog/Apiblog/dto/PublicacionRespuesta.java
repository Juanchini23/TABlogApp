package com.blog.Apiblog.dto;

import java.util.List;

public class PublicacionRespuesta {

    private List<PublicacionDto> contenido;
    private Integer numeroPaginas;
    private Integer medidaPagina;
    private Long totalElementos;
    private Integer totalPaginas;
    private Boolean ultima;

    public List<PublicacionDto> getContenido() {
        return contenido;
    }

    public void setContenido(List<PublicacionDto> contenido) {
        this.contenido = contenido;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Integer getMedidaPagina() {
        return medidaPagina;
    }

    public void setMedidaPagina(Integer medidaPagina) {
        this.medidaPagina = medidaPagina;
    }

    public Long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(Long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public Boolean getUltima() {
        return ultima;
    }

    public void setUltima(Boolean ultima) {
        this.ultima = ultima;
    }

    public PublicacionRespuesta() {
    }
}
