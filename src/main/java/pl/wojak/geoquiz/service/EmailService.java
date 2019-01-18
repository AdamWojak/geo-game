package pl.wojak.geoquiz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
public class EmailService {

    private String fromAddress;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    public EmailService(JavaMailSender javaMailSender,
                        @Value("${spring.mail.from}") String fromAddress, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.fromAddress = fromAddress;
        this.templateEngine = templateEngine;
    }


    public void sendExpandedEmail(String title, String description, String to) {

        String content = prepareEmailContent(title, description);

        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo(fromAddress);
            helper.setFrom(fromAddress);
            helper.setSubject(title);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("EXTENDED.MAIL: Data przed rozpoczęciem wysyłania: " + LocalDateTime.now());
        javaMailSender.send(mail);
    }


    public String prepareEmailContent(String title, String description) {
        Context context = new Context();
        context.setVariable("header", "GEO-QUIZ");
        context.setVariable("title", title);
        context.setVariable("description", description);
        String content = templateEngine.process("template", context);
        return content;
    }


    public void sendSimpleEmail(String temat, String tresc, String mail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail);
            message.setFrom(fromAddress);
            message.setSubject(temat);
            message.setText(tresc);
            System.out.println("SIMPLE.MAIL: Data przed rozpoczęciem wysyłania: " + LocalDateTime.now());
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

