package com.blog.Apiblog.seguridad;

public class JwtAuthResponseDto {

    private String tokenAcceso;
    private String tipoToken = "Bearer";

    public JwtAuthResponseDto(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }

    public JwtAuthResponseDto(String tokenAcceso, String tipoToken) {
        this.tokenAcceso = tokenAcceso;
        this.tipoToken = tipoToken;
    }

    public String getTokenAcceso() {
        return tokenAcceso;
    }

    public void setTokenAcceso(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }

    public String getTipoToken() {
        return tipoToken;
    }

    public void setTipoToken(String tipoToken) {
        this.tipoToken = tipoToken;
    }
}
