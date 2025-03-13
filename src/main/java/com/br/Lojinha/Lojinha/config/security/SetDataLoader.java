package com.br.Lojinha.Lojinha.config.security;

import com.br.Lojinha.Lojinha.data.repositories.UserRepository;
import com.br.Lojinha.Lojinha.data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@SuppressWarnings("unused")
public class SetDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (!alreadySetup) {
            User User = new User();
            User.setPassword(passwordEncoder.encode("123456"));
            User.setEmail("teste@gmail.com");
            createUserIfNotFound(User);
            alreadySetup = true;
        }
    }

    private void createUserIfNotFound(User root) {

        Optional<User> user = UserRepository.findByEmail(root.getEmail());
        if (user.isEmpty()) {
            UserRepository.save(root);
        }
    }
    
}
