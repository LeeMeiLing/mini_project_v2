package sg.edu.nus.iss.server.security.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import sg.edu.nus.iss.server.models.User;
import sg.edu.nus.iss.server.services.UserService;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserService userSvc;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        // get the user info from database for comparison with user login details
        User user = userSvc.findUser((String) authentication.getPrincipal()); // same as authentication.getName()
        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getUserPassword())){
            //TODO: compare with password from hospital table
            
            throw new BadCredentialsException("Wrong Password");
        }
        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getUserPassword());

    }
    
    public String getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
