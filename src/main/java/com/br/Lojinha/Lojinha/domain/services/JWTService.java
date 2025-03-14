package com.br.Lojinha.Lojinha.domain.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

//Classe responsavel por criptografar e descriptografar o token
@Service
public class JWTService {

    //Tempo de expiração do token
    @Value("${security.jwt.expiration}")
    private int expiration;
    //Chave utilizada para criptografar e descriptografar o token
    @Value("${security.jwt.key}")
    private String key;
    //Valores dos atributos estão declarados no application.properties

    //Método responsável por gerar o token recebendo um userName
    public String generateToken(String username) {

        //Pega o tempo atual e adiciona o tempo de expiração do token
        Calendar currentTimeNow = Calendar.getInstance();
        currentTimeNow.add(Calendar.MINUTE, expiration);
        Date expirationDate = currentTimeNow.getTime();

        //Chava o  método ptivado que converte a chave num objeto SecretKey
        SecretKey secretKey = getSecretKey();

        //Retorna o token
        return Jwts.builder()
                //Dados do payload(userName)
                .setSubject(username)
                //Passa o tempo de expiração
                .setExpiration(expirationDate)
                //Informa a chave do token
                .signWith(secretKey)
                //Gera o token
                .compact();
    }

    private SecretKey getSecretKey() {
        //Pega os bytes da chave no formato UTF 8
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getClaims(String token) {
        SecretKey secretKey = getSecretKey();

        //Com base no token criptografado e na chave, ele retorna o body descriptografado
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody();
    }


    public String getUserName(String token) {
        //Retonar o Subject, no caso o userName
        return getClaims(token).getSubject();

    }
}
