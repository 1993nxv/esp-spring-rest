package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.core.email.EmailProperties;

@Service
public class SandBoxEnvioEmailService extends SmtpEnvioEmailService {
	
	@Autowired
	private EmailProperties emailProperties;

	protected MimeMessage criarMimeMessage(Mensagem mensagem, String corpo) throws MessagingException {
		
		MimeMessage mimeMessage = super.criarMimeMessage(mensagem, corpo);
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setTo(emailProperties.getSandbox().getDestinatario());
		
		return mimeMessage;
		
	}	
}
