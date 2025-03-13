package com.br.Lojinha.Lojinha.domain.services;

import com.br.Lojinha.Lojinha.data.entities.User;
import com.br.Lojinha.Lojinha.data.repositories.UserRepository;
import com.br.Lojinha.Lojinha.dto.LoginDTO;
import com.br.Lojinha.Lojinha.util.ExceptionResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()){
            return user.get();
        }
        throw new RuntimeException(new ExceptionResponse("Dados inválidos", HttpStatus.UNAUTHORIZED));
    }

    public void verifyUserCredentials(LoginDTO login) throws ExceptionResponse {
        UserDetails credential = loadUserByUsername(login.getEmail());
        //Após a validação do username, retorna um usuario e agora verifica a senha criptografada
        boolean passwordIsTheSame = passwordEncoder.matches(login.getPassword(), credential.getPassword());
        if (!passwordIsTheSame) {
            throw new ExceptionResponse("Dados Invalidos", HttpStatus.UNAUTHORIZED);
        }
    }
}
