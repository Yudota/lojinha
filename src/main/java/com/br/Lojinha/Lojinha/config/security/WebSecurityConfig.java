package com.br.Lojinha.Lojinha.config.security;


import com.br.Lojinha.Lojinha.domain.services.JWTService;
import com.br.Lojinha.Lojinha.domain.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {//WebSecurityConfigurerAdapter

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated()
                        ).addFilterBefore(new JwtAuthFilter(jwtService, userDetailsService)
                        , UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private JWTService jwtService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userDetailsService;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return passwordEncoder;
//    }
//
//    @Bean
//    protected DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//         return http.
//                 csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login/**").permitAll()
//                        // .requestMatchers("/pipipi_popopo/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                 .sessionManagement(sess
//                         -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .httpBasic(Customizer.withDefaults())
//                 .build();
//
//        http.authorizeHttpRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/hello").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                //Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                //Adciona um filtro antes do filtro padrão do Spring
//                //O filtro intercepta as requisições para validar o acesso do usuario
//                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    //Retorna um novo filtro
//    public OncePerRequestFilter jwtFilter() {
//        return new JwtAuthFilter(jwtService, userDetailsService);
//    }
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
