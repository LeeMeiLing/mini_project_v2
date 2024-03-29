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

import sg.edu.nus.iss.server.models.Moh;
import sg.edu.nus.iss.server.services.UserService;

@Component
public class CustomAuthenticationManagerForMoh implements AuthenticationManager {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userSvc;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // get the user info from database for comparison with user login details
        Moh moh = userSvc.getMohByEthAddress((String) authentication.getPrincipal()); // same as authentication.getName()

        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), moh.getAccountPassword())){

            throw new BadCredentialsException("Wrong Password");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), moh.getAccountPassword());
        
    }

    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public String getCountryCode(Authentication authentication){

        return  userSvc.getMohByEthAddress((String) authentication.getPrincipal()).getCountryCode();
    }

    public Moh getMoh(){
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  userSvc.getMohByEthAddress((String) authentication.getPrincipal());
    }
}
