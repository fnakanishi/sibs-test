package org.example.service;

import org.example.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Value("${spring.mail.host}") private String host;
    @Value("${spring.mail.port}") private Integer port;
    @Value("${spring.mail.username}") private String username;
    @Value("${spring.mail.password}") private String password;
    @Value("${spring.mail.auth}") private String auth;
    @Value("${spring.mail.starttls.enable}") private String starttls;



    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();

        emailSender.setHost(host);
        emailSender.setPort(port);
        emailSender.setUsername(username);
        emailSender.setPassword(password);

        Properties props = emailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.debug", "true");

        return emailSender;
    }

    public void sendMessage(String to, String subject, String text) {
        JavaMailSender emailSender = getJavaMailSender();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("code-test@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }

    public void buildOrderEmail(Order order) {
        Long orderId = order.getId();
        String itemName = order.getItem().getName();
        String email = order.getUser().getEmail();
        int quantity = order.getQuantity();

        String subject = String.format("Order %s issued", orderId);
        String text = String.format("Your order for %s %s has been issued.", quantity, itemName);

        sendMessage(email, subject, text);
    }
}
