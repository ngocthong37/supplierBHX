package com.supplierBHX.repository.Imp;

import com.supplierBHX.repository.EmailServiceRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Repository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.util.Properties;

@Repository
public class EmailServiceRepositoryImp implements EmailServiceRepository {
        private final String fromEmail = "ngocthong2k2@gmail.com";

        private final JavaMailSender javaMailSender;

        @Autowired
        public EmailServiceRepositoryImp(JavaMailSender javaMailSender) {
            this.javaMailSender = javaMailSender;
        }

        @Override
        public String sendMail(String to, String[] cc, String subject, String body) {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

                mimeMessageHelper.setFrom(fromEmail);
                mimeMessageHelper.setTo(to);
                mimeMessageHelper.setCc(cc);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(body);

                // Configure TLS properties if not already set
                JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
                if (!mailSender.getJavaMailProperties().containsKey("mail.smtp.starttls.enable")) {
                    mailSender.setHost("smtp.gmail.com");
                    mailSender.setPort(587); // Typical port for TLS
                    mailSender.setUsername("ngocthong2k2@gmail.com");
                    mailSender.setPassword("yqmydypegzgxydjn");

                    Properties props = mailSender.getJavaMailProperties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Trust the SMTP server

                    mailSender.setJavaMailProperties(props);
                }

                javaMailSender.send(mimeMessage);

                return "mail send";

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
}
