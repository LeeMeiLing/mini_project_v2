package sg.edu.nus.iss.server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.server.exceptions.PostReviewFailedException;
import sg.edu.nus.iss.server.exceptions.RegisterHospitalFailedException;
import sg.edu.nus.iss.server.exceptions.ResultNotFoundException;

@RestControllerAdvice
public class ErrorController {
    
    @ExceptionHandler(ResultNotFoundException.class)
    public ResponseEntity<String> handleResultNotFoundException(ResultNotFoundException ex){

        JsonObject payload = Json.createObjectBuilder().add("error", ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(payload.toString());
        
    }

    @ExceptionHandler(PostReviewFailedException.class)
    public ResponseEntity<String> handlePostReviewFailedException(PostReviewFailedException ex){

        JsonObject payload = Json.createObjectBuilder().add("error", ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(payload.toString());
        
    }

    @ExceptionHandler(RegisterHospitalFailedException.class)
    public ResponseEntity<String> handleRegisterHospitalFailedException(RegisterHospitalFailedException ex){

        JsonObject payload = Json.createObjectBuilder().add("error", ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(payload.toString());
        
    }
}
