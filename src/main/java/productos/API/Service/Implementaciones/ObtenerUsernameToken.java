package productos.API.Service.Implementaciones;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsernameToken {



    // Verificar si hay un usuario autenticado

        public String findUserByToken(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username;
            if (authentication != null && authentication.isAuthenticated()) {
                // Obtener el nombre de usuario
                username= authentication.getName();  // El nombre de usuario está disponible en getName()
                return username;
            }else{
                 return  username="";
            }
        }

}
