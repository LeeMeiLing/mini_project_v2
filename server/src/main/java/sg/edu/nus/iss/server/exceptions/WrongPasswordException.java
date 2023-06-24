package sg.edu.nus.iss.server.exceptions;

public class WrongPasswordException extends Exception {
    
    public WrongPasswordException(String message){
        super(message);
    }

}