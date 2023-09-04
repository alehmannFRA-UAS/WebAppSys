package edu.fra.uas.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Message implements java.io.Serializable {

    private static final Logger log = LoggerFactory.getLogger(ChatUser.class);

    private Long id;
    private String content;
    private Long userId;
    private LocalDateTime timeStamp;

    public Message() {
        log.debug("Message created without values");
    }

    public Message(Long id, String content, Long userId, LocalDateTime timeStamp) {
        log.debug("Message created with values + id: " + id + " content: " + content + " userId: " + userId + " timeStamp: " + timeStamp);
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", content=" + content + ", userId=" + userId + ", timeStamp=" + timeStamp + "]";
    }
}
