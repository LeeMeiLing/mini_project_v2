package sg.edu.nus.iss.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import sg.edu.nus.iss.server.security.filters.AuthenticationFilter;
import sg.edu.nus.iss.server.security.filters.ExceptionHandlerFilter;
import sg.edu.nus.iss.server.security.filters.JWTAuthorizationFilter;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManager;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class SecurityConfig {

    @Value("${spring.security.secret.key}")
    private String secretKey;

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setFilterProcessesUrl("/api/user/authenticate"); // default is /login
        authenticationFilter.setUsernameParameter("userEmail"); // default is username
        authenticationFilter.setPasswordParameter("userPassword"); // default is password
        authenticationFilter.setAuthenticationManager(customAuthenticationManager);
        authenticationFilter.setSecretKey(secretKey);

        http
            .cors(withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                    .anyRequest().authenticated())
            .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
            .addFilter(authenticationFilter)
            // .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

        
    }

    

}
