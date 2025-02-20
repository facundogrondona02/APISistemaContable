package productos.API.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import productos.API.Jwt.JwtAuthenticationFilter;
import productos.API.Model.DAO.IUserDAO;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**") // 👈 Permite TODO lo que empiece con /auth/
//                        .permitAll()
//                        .requestMatchers("/api/v1/**") // 👈 Requiere autenticación
//                        .authenticated()
//                        .requestMatchers(
//                                "/swagger-ui/**",
//                                "/v3/api-docs/**",
//                                "/swagger-resources/**",
//                                "/webjars/**"
//                        ).authenticated()
//                )
//                .httpBasic(Customizer.withDefaults()) // 👈 Permite autenticación básica
//.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.permitAll())
//                .csrf(csrf -> csrf.disable());
//
//        return httpSecurity.build();
//    }
    ///////////////////////////


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(crsf -> crsf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }

    ///////////////////////////


//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        return username -> userDAO.findAll().stream().filter(user -> user.getUsername().equals(username))
//                .findFirst()
//                .map(user -> org.springframework.security.core.userdetails.User.builder()
//                        .username(user.getUsername())
//                        .password(user.getPassword())
//                        .roles(user.getRoles().toUpperCase()) // Agregar el prefijo si falta
//                        .build())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }


//@Bean
//public UserDetailsService userDetailsService() {
//    UserDetails userDetails = User.withDefaultPasswordEncoder()
//            .username("user")
//            .password("password")
//            .roles("ADMIN")
//            .build();
//
//    return new InMemoryUserDetailsManager(userDetails);
//}

}

