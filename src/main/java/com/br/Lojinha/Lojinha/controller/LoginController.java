package com.br.Lojinha.Lojinha.controller;

import com.br.Lojinha.Lojinha.domain.services.JWTService;
import com.br.Lojinha.Lojinha.dto.LoginDTO;
import com.br.Lojinha.Lojinha.dto.TokenDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Value("${security.jwt.expiration}")
    private String expiration;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid LoginDTO dto){
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        authenticationManager.authenticate(usernamePassword);
        return ResponseEntity.ok(new TokenDTO(jwtService.generateToken(dto.getEmail()), expiration));
    }
}

//    @Autowired
//    private UserDetailsService userDetailsService;
//    @Autowired
//    private JWTService jwtService;
//
//    @Value("${security.jwt.expiration}")
//    private String expiration;
//
//    @PostMapping
//    @SuppressWarnings("unused")
//    public AppResponse<DataResponse> login(@Valid @RequestBody LoginDTO loginDTO) throws ExceptionResponse {
//        try{
//            userDetailsService.verifyUserCredentials(loginDTO);
//            String token = jwtService.generateToken(loginDTO.getEmail());
//            return AppResponse.success(new TokenDTO(token, expiration), "Login efetuado com sucesso!");
//        }catch (ExceptionResponse e) {
//            throw new RuntimeException(e);
//        }
//    }

