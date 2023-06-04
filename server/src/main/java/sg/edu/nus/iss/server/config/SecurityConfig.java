package sg.edu.nus.iss.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import sg.edu.nus.iss.server.security.filters.AuthenticationFilter;
import sg.edu.nus.iss.server.security.filters.ExceptionHandlerFilter;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManager;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {

    // @Bean
    // public BCryptPasswordEncoder bCryptPasswordEncoder(){
    //     return new BCryptPasswordEncoder();
    // }
    
    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/user/authenticate"); // default is /login ?
        authenticationFilter.setUsernameParameter("userEmail");
        authenticationFilter.setPasswordParameter("userPassword");

        http
            .cors(withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                    .anyRequest().authenticated())
            // .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
            .addFilter(authenticationFilter)
            // .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

        
    }

    

}
