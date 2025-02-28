//package productos.API;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // Aplica a todas las rutas
//                        .allowedOrigins("http://localhost:5173") // Permite peticiones desde tu frontend
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
//                        .allowedHeaders("*") // Permitir todos los headers
//                        .allowCredentials(true); // Permite cookies y autenticación
//            }
//        };
//    }
//}
