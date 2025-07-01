package com.pritam.journalApp.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;





@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender jm;

    public void sendEmail(String to, String subject, String body) {
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            jm.send(mail);
        }catch (Exception e){
            log.error("Error while sending email {} : {} " ,to , e.getMessage());
        };
    }
}
