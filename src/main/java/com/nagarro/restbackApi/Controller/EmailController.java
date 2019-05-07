package com.nagarro.restbackApi.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.restbackApi.Models.ApplicantDetails;

@RestController
public class EmailController {
       @RequestMapping(value="/sendemail",method = RequestMethod.POST)
       public String sendEmail(@RequestBody ApplicantDetails details) throws AddressException,MessagingException,IOException {
             sendmail(details);
             return "email sent";
       }
       private void sendmail(ApplicantDetails details) throws AddressException, MessagingException, IOException {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                   protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication("knightdark1818@gmail.com", "batman@)");
                   }
                });
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("knightdark1818@gmail.com", false));
                System.out.println("in email");
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(details.getEmail()));
                msg.setSubject("You are Registered");
                msg.setContent("Your password is :-"+ details.getPassword(), "text/html");
                msg.setSentDate(new Date());
                Transport.send(msg);   
             }

             
}
