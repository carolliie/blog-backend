package dev.carolliie.BlogServer.controller;

import dev.carolliie.BlogServer.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmailSenderController {
    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody Email email) {
        try {
            String mailTo = System.getenv("MAIL_TO");
            String subject = "Olá, Carole! Uma nova mensagem de " + email.getName();
            String body = "Nome: " + email.getName() + "\nEmail: " + email.getEmail() + "\n\nMessagem:\n" + email.getMessage();

            emailSenderService.sendEmail(mailTo, subject, body);
            return ResponseEntity.ok("Email sent successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

class Email {
    private String name;
    private String email;
    private String message;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}