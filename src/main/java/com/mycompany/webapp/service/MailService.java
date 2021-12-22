package com.mycompany.webapp.service;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.OrdersMemberDao;
import com.mycompany.webapp.dto.MailData;
import com.mycompany.webapp.dto.fromCartToOrder.OrderAllInfo;

import lombok.extern.slf4j.Slf4j;

@Service("mailService")
@Slf4j
public class MailService {
	@Autowired
	JavaMailSender javaMailSender;

	@Autowired OrdersMemberDao ordersMemberDao;
	
	// 1.2버전을 어디다 적용하라는거야..
	public void sendTextMail(OrderAllInfo orderAllInfo) throws MessagingException {
		log.info("메일 보내기");
		String memail = orderAllInfo.getOrders().getMemail();
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MailData mailData = new MailData(orderAllInfo);
		String subject = orderAllInfo.getOrders().getMname()+"님께서 주문하신 내역입니다.";
		message.setSubject(subject);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(memail));
		message.setText(mailData.mailContent(), "UTF-8", "html");
		message.setSentDate(new Date());
		javaMailSender.send(message);
	}
}
