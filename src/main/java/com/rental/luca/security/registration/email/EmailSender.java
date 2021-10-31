package com.rental.luca.security.registration.email;

public interface EmailSender {

    void send(String to, String email);
}
