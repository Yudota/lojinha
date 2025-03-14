package com.br.Lojinha.Lojinha.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class TokenDTO implements IDTO {

    private String accessToken;
    private String expiration;

    public TokenDTO(String accessToken, String expiration) {
        this.accessToken = accessToken;
        this.expiration = expiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
