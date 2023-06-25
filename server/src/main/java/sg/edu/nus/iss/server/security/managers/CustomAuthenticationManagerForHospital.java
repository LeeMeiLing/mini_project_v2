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

        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), hospitalSg.getAccountPassword())){
            throw new BadCredentialsException("Wrong Password");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), hospitalSg.getAccountPassword());
        
    }

    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public String getFacilityId(Authentication authentication){

        return  userSvc.findHospitalSgByEthAddress((String) authentication.getPrincipal()).getFacilityId();
    }
}
