package pl.wojak.geoquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService  {

    private static final String MAIL_REPLY_TO = "mail.replyTo";
    private static final String MAIL_SENT_FROM = "mail.sentFrom";

    @Autowired
    private JavaMailSender javaMailSender;


    public void prepareEmail(String to, String title, String content){
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail,true);
            helper.setTo(to);
            helper.setReplyTo(MAIL_REPLY_TO);
            helper.setFrom(MAIL_SENT_FROM);
            helper.setSubject(title);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public String send() {
        Context context = new Context();
        context.setVariable("header", "Nowy artykuł na CodeCouple");
        context.setVariable("title", "#8 Spring Boot – email - szablon i wysyłanie");
        context.setVariable("description", "Tutaj jakis opis...");
        String body = templateEngine.process("template", context);
        emailSender.prepareEmail("your.email.here@gmail.com", "CodeCouple Newsletter", body);
        return "index";
    }



}
