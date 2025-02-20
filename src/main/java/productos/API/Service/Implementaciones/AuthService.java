package productos.API.Service.Implementaciones;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import productos.API.Jwt.JwtService;
import productos.API.Model.DAO.IUserDAO;
import productos.API.Model.Entity.User;
import productos.API.Model.Payload.AuthResponse;
import productos.API.Model.Request.LoginRequest;
import productos.API.Model.Request.SignupRequest;
import productos.API.User.Role;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest){
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserDetails userDetails = userDAO.findByUsername(loginRequest.getUsername()).orElseThrow();

        String token = jwtService.getToken(userDetails);

        return  AuthResponse.builder().token(token).build();


    }

    public AuthResponse signup(SignupRequest signupRequest){
        User user = User.builder()
                .username(signupRequest.getUsername())
                .password( passwordEncoder.encode( signupRequest.getPassword()))
                .firstname(signupRequest.getFirstname())
                .lastname(signupRequest.getLastname())
                .email(signupRequest.getEmail())
                .roles(Role.USER)
                .build();

        userDAO.save(user);

        return  AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
