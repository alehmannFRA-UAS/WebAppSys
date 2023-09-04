package edu.fra.uas.service;

//import java.net.http.HttpClient;


import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.fra.uas.model.ApiError;
import edu.fra.uas.model.ChatUser;
import edu.fra.uas.model.User;

@Service
public class ChatService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ChatService.class);

    // Read the URL of the external API from properties file.
    @Value("${chatservice.url}")
    String apiUrl;

    // get all rooms                    GET     /rooms
    public ResponseEntity<?> getAllRooms() {
        log.debug("forward request to " + apiUrl + "/rooms");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/rooms";

        ResponseEntity<?> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        log.debug("response: " + response);
        return response;
    }

    // get room by id                   GET     /rooms/{id}
    public ResponseEntity<?> getRoomById(Long id) {
        log.debug("forward request to " + apiUrl + "/rooms/" + id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/rooms/" + id;

        ResponseEntity<?> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }
    
    // create a new room                POST    /rooms
    public ResponseEntity<?> createRoom(String name) {
        log.debug("forward request to " + apiUrl + "/rooms");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/rooms";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(name, headers);

        ResponseEntity<?> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }
    
    // delete a room                    DELETE  /rooms/{id}
    public ResponseEntity<?> deleteRoom(Long id) {
        log.debug("forward request to " + apiUrl + "/rooms/" + id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/rooms/" + id;        

        ResponseEntity<?> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }
    
    // join a room                      PUT     /rooms/{id}
    public ResponseEntity<?> joinRoom(Long id, User user) {
        log.debug("forward request to " + apiUrl + "/rooms/" + id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/rooms/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ChatUser chatUser = new ChatUser(user.getId(), user.getEmail());
        HttpEntity<ChatUser> request = new HttpEntity<ChatUser>(chatUser, headers);

        ResponseEntity<?> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }
    
    // leave a room                     PATCH  /rooms/{id}/users/{userId}
    public ResponseEntity<?> leaveRoom(Long roomId, Long userId) {
        log.debug("forward request to " + apiUrl + "/rooms/" + roomId + "/users/" + userId);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/rooms/" + roomId + "/users/" + userId;

        // Needed to send PATCH cause of a bug in standard JDK HTTP
        org.apache.hc.client5.http.classic.HttpClient httpClient = HttpClientBuilder.create().build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<?> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.PATCH, null, String.class);
            //response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }
    
    // send a message                   POST    /rooms/{id}/users/{userId}/messages
    public ResponseEntity<?> sendMessage(Long id, Long userId, String text) {
        log.debug("forward request to " + apiUrl + "/rooms/" + id + "/users/" + userId + "/messages");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/rooms/" + id + "/users/" + userId + "/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(text, headers);

        ResponseEntity<?> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }
    
    // get all messages of a room       GET     /rooms/{id}/users/{userId}/messages
    public ResponseEntity<?> getAllMessages(Long id, Long userId) {
        log.debug("forward request to " + apiUrl + "/rooms/" + id + "/users/" + userId + "/messages");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/rooms/" + id + "/users/" + userId + "/messages";
        ResponseEntity<?> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }
    
}
