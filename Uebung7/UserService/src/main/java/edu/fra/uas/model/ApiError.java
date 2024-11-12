package edu.fra.uas.model;

import org.springframework.http.HttpStatus;

public class ApiError {
    
    private HttpStatus status;
    private String message;

    public ApiError() {
    }
    
    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMesssage(String message) {
        this.message = message;
    }

}
