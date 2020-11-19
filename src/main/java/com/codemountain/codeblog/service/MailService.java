package com.codemountain.codeblog.service;

import com.codemountain.codeblog.entity.NotificationEmail;
import com.codemountain.codeblog.exception.CodeBlogException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilderService mailContentBuilderService;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("codeblog.mail.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilderService.build(notificationEmail.getBody()));
        };

        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Activation email sent!!");
        }catch (MailException e) {
            throw new CodeBlogException("Error occurred when sending mail to: " + notificationEmail.getRecipient());
        }
    }
}
