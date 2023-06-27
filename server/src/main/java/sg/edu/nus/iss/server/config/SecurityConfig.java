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
import sg.edu.nus.iss.server.security.filters.AuthenticationFilterForHospital;
import sg.edu.nus.iss.server.security.filters.AuthenticationFilterForMoh;
import sg.edu.nus.iss.server.security.filters.ExceptionHandlerFilter;
import sg.edu.nus.iss.server.security.filters.JWTAuthorizationFilter;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManager;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManagerForHospital;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManagerForMoh;
import sg.edu.nus.iss.server.services.EthereumService;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class SecurityConfig {

    @Value("${spring.security.secret.key}")
    private String secretKey;
    
    // @Value("${INFURA_URL}")
    // private String infuraUrl;

    @Value("${ALCHEMY_URL}")
    private String alchemyUrl;

    @Value("${ETH_PRIVATE_KEY}")
    private String privateKey;

    @Value("${GAS_PRICE}")
    private String price;

    @Bean
    public EthereumService ethereumService() {
        return new EthereumService(alchemyUrl, privateKey, price);
    }

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager; 

    @Autowired
    private CustomAuthenticationManagerForHospital customAuthenticationManagerForHospital; 

     @Autowired
    private CustomAuthenticationManagerForMoh customAuthenticationManagerForMoh; 
  
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

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/user/authenticate"); // default is /login
        authenticationFilter.setUsernameParameter("userEmail"); // default is username
        authenticationFilter.setPasswordParameter("userPassword"); // default is password
        authenticationFilter.setSecretKey(secretKey);

        AuthenticationFilterForHospital authenticationFilterForHospital = new AuthenticationFilterForHospital();
        authenticationFilterForHospital.setFilterProcessesUrl("/api/hospitals/authenticate"); // default is /login
        authenticationFilterForHospital.setUsernameParameter("ethAddress"); // default is username
        authenticationFilterForHospital.setPasswordParameter("accountPassword"); // default is password
        authenticationFilterForHospital.setAuthenticationManager(customAuthenticationManagerForHospital);
        authenticationFilterForHospital.setSecretKey(secretKey);

        AuthenticationFilterForMoh authenticationFilterForMoh = new AuthenticationFilterForMoh();
        authenticationFilterForMoh.setFilterProcessesUrl("/api/hospitals/authenticate/moh"); // default is /login
        authenticationFilterForMoh.setUsernameParameter("mohEthAddress"); // default is username
        authenticationFilterForMoh.setPasswordParameter("accountPassword"); // default is password
        authenticationFilterForMoh.setAuthenticationManager(customAuthenticationManagerForMoh);
        authenticationFilterForMoh.setSecretKey(secretKey);


        http
            .cors(withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/api/user/register/public").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/hospitals/*/register/hospital").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/hospitals/*/register/moh").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/hospitals/moh").permitAll()
                    .anyRequest().authenticated())
            .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
            .addFilter(authenticationFilter)
            .addFilterAfter(new JWTAuthorizationFilter(secretKey), AuthenticationFilter.class)
            .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilterForHospital.class)
            .addFilter(authenticationFilterForHospital)
            .addFilterAfter(new JWTAuthorizationFilter(secretKey), AuthenticationFilterForHospital.class)
            .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilterForMoh.class)
            .addFilter(authenticationFilterForMoh)
            .addFilterAfter(new JWTAuthorizationFilter(secretKey), AuthenticationFilterForMoh.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
        
    } 

}
