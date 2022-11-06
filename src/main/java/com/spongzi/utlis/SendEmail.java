package com.spongzi.utlis;

import com.spongzi.exception.BlogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.Session;
import java.util.Date;

/**
 * 发送电子邮件
 *
 * @author spongzi
 */
@Component
public class SendEmail {

    @Resource
    private JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String from;

    public void sendEmail(String email, String code) {
        // 创建简单的邮件发送对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("bbs_school 验证码");
        message.setText(code);
        message.setSentDate(new Date());
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new BlogException(401, ex.getMessage());
        }
    }
}
