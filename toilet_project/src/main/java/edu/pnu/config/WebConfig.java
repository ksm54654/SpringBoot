package edu.pnu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") //http://10.125.121.189:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*");
                .exposedHeaders(HttpHeaders.AUTHORIZATION); // authorization을 보내주겠다고 설정 
        // You can add more configuration options as needed
    }


}
  