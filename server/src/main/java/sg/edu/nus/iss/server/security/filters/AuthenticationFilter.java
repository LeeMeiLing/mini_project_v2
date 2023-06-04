package sg.edu.nus.iss.server.security.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.server.models.User;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    // @Override
    // public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    //         throws IOException, ServletException {
        
    //     chain.doFilter(request, response);
    // }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        System.out.println(">>> in attemptauthentication()");
        
        try{

            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            System.out.println(">>> In Authentication Filter attemptAuthentication(), " + user.getUserEmail()); // debug
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword());
            return this.getAuthenticationManager().authenticate(authentication);
            
        }catch(IOException ex){
            throw new RuntimeException(); // to send back a 400 response instead of 403
        }
    }
    
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
                System.out.println(">>> Boohoo authentication didnt work");
            }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        System.out.println(">>> Woohoo authentication worked");
    }

    
    

    
}
