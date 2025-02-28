package productos.API.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productos.API.Model.DAO.IUserDAO;
import productos.API.Model.DTO.UserDTO;
import productos.API.Model.Entity.User;
import productos.API.Model.Payload.AuthResponse;
import productos.API.Model.Payload.Response;
import productos.API.Model.Request.LoginRequest;
import productos.API.Model.Request.SignupRequest;
import productos.API.Service.IUserService;
import productos.API.Service.Implementaciones.AuthService;
import productos.API.Service.Implementaciones.UserIMPL;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserController {

//    @Autowired(required = true)
//    private IUserService userService;

//    @Autowired
//    private IUserDAO userDAO;

    @Autowired
    private  AuthService authService;


    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){

        AuthResponse authResponse = AuthResponse.builder().build();
        try{
            AuthResponse token =authService.signup(signupRequest);
            return new ResponseEntity<>(Response.builder()
                    .mensaje("Registro exitoso")
                    .object(token)
                    .build(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(Response.builder()
                    .mensaje("Hay campos no validos")
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST
            );
        }

    }


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){


        try{
            AuthResponse token = authService.login(loginRequest);

            return new ResponseEntity<>(Response.builder()
                    .mensaje("Inicio de sesion exitoso")
                    .object(token)
                    .build(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(Response.builder()
                    .mensaje("Usuario o contraseña son incorrectos")
                    .object(null)
                .build(), HttpStatus.BAD_REQUEST
            );
        }


    }

//    @GetMapping("user/{username}")
//    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
////        return userDAO.findAll().stream()
////                .filter(user -> user.getUsername().equals(username))
////                .findFirst()
////                .map(user -> ResponseEntity.ok(
////                        Response.builder()
////                                .mensaje("Usuario encontrado con éxito")
////                                .object(user)
////                                .build()
////                ))
////                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
////                        .body(Response.builder()
////                                .mensaje("Usuario no encontrado")
////                                .build())
////                );
//        return new ResponseEntity<>(
//                Response.builder().mensaje("encontradooo").object(userDAO.findByUsername(username)).build(),
//                HttpStatus.OK);
//
//    }


    //    @PostMapping("signup")
//    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO){
//
//
//        try{
//            if(userDTO.getFirstname() != null){
//                User userEntity = userService.signup(userDTO);
//
//
//                UserDTO userDTO1 = UserDTO.builder()
//                        .username(userEntity.getUsername())
//                        .password(userEntity.getPassword())
//                        .firstname(userEntity.getFirstname())
//                        .lastname(userEntity.getLastname())
//                        .email(userEntity.getEmail())
//                        .roles(userEntity.getRoles())
//                        .build();
//
//                return new ResponseEntity<>(
//                        Response.builder().mensaje("Usuario creado con exito").object(userDTO1).build(),
//                        HttpStatus.CREATED
//                        );
//            }
//        }catch (Exception exception){
//            return new ResponseEntity<>(
//                    Response.builder().mensaje(exception.getMessage()).object(null).build(),
//                    HttpStatus.BAD_REQUEST
//            );
//        }
//
//        return new ResponseEntity<>(
//                Response.builder().mensaje("hubo algun erro").object(null).build(),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }


}
