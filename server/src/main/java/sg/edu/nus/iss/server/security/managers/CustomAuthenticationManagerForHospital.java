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

import sg.edu.nus.iss.server.models.HospitalSg;
import sg.edu.nus.iss.server.services.UserService;

@Component
public class CustomAuthenticationManagerForHospital implements AuthenticationManager {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userSvc;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // get the user info from database for comparison with user login details
        HospitalSg hospitalSg = userSvc.findHospitalSgByEthAddress((String) authentication.getPrincipal()); // same as authentication.getName()

        System.out.println(">>> in CustomAuthenticationManagerForHospital: " );// debug

        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), hospitalSg.getAccountPassword())){
            System.out.println(">>> password dont match!!!");
            System.out.println("1: " + authentication.getCredentials().toString());
            System.out.println("2: " + hospitalSg.getAccountPassword());

            throw new BadCredentialsException("Wrong Password");
        }

        System.out.println(">>> password match!!!");
        return new UsernamePasswordAuthenticationToken(authentication.getName(), hospitalSg.getAccountPassword());
        
    }

    // public String getCurrentUser() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     return authentication.getName();
    // }
}
