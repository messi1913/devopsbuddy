package com.devopsbuddy.backend.service;

import com.devopsbuddy.web.domain.frontend.FeedbackPojo;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    /**
     * Sends an email with the content in the Feedback Pojo.
     * @param feedbackPojo
     */
    public void sendFeedbackEmail(FeedbackPojo feedbackPojo);

    /**
     * Send an email with the content of the Simple Mail Message object.
     * @param message containing the email content.
     */
    public void sendGenericEmailMessage(SimpleMailMessage message);

}
