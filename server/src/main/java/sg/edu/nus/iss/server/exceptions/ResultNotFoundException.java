package sg.edu.nus.iss.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResultNotFoundException extends RuntimeException{
    
    public ResultNotFoundException(String searchItem){
        super(searchItem + " not found");
    }
}
