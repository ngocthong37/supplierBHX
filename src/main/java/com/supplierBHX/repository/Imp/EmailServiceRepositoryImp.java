package com.supplierBHX.repository.Imp;

import com.supplierBHX.repository.EmailServiceRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

@Repository
public class EmailServiceRepositoryImp implements EmailServiceRepository {
    private String fromEmail = "ngocthong2k2@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration config;

    @Override
    public String sendMail(String to, String[] cc, String subject, Map<String, Object> model) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

//            mimeMessageHelper.addAttachment("logo.jpg", new ClassPathResource("logo.jpg"));
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            Template t;
            if (subject.equals("Tài khoản truy cập website của bạn đã được tạo")) {
                t = config.getTemplate("register.ftl");
            }
            else {
                t = config.getTemplate("email-template.ftl");
            }
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            mimeMessageHelper.setText(html, true);
            javaMailSender.send(mimeMessage);

            return "mail send";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}