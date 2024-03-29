package sg.edu.nus.iss.server.security.filters;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.server.models.Moh;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManagerForMoh;

public class AuthenticationFilterForMoh extends UsernamePasswordAuthenticationFilter{

    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
       
        try{

            Moh moh = new ObjectMapper().readValue(request.getInputStream(), Moh.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(moh.getMohEthAddress(), moh.getAccountPassword());
            return this.getAuthenticationManager().authenticate(authentication);
        
        }catch(IOException ex){

            ex.printStackTrace();
            throw new RuntimeException(); // to send back a 400 response instead of 403

        }

    }
    
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // send back a JWT 
        String token = JWT.create()
                          .withClaim("userRole", "moh")
                          .withClaim("countryCode", ((CustomAuthenticationManagerForMoh) this.getAuthenticationManager()).getCountryCode(authResult).toLowerCase())
                          .withSubject(authResult.getName())
                          .withExpiresAt(new Date(System.currentTimeMillis() + 7200000))
                          .sign(Algorithm.HMAC512(this.getSecretKey()));

        // "Authorization" : Bearer Token             
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");

    }

    
}

