/*******************************************************************************
 * Class        ：SmtpMailServiceImpl
 * Created date ：2021/01/18
 * Lasted date  ：2021/01/18
 * Author       ：TrieuVD
 * Change log   ：2021/01/18：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.smtp.mail.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.dto.AbstractEmailDto;
import vn.com.unit.common.dto.AttachFileEmailDto;
import vn.com.unit.common.dto.EmailResultDto;
import vn.com.unit.common.service.EmailService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.smtp.mail.constant.SmtpMailConstant;

/**
 * SmtpMailServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
public class SmtpMailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(SmtpMailServiceImpl.class);

    /* (non-Javadoc)
     * @see vn.com.unit.common.service.EmailService#sendEmail(vn.com.unit.common.dto.AbstractEmailDto)
     */
    @Override
    public EmailResultDto sendEmail(AbstractEmailDto emailDto, Map<String, String> configMail) {
        EmailResultDto responseEmailDto = new EmailResultDto();
        
        String emailContent = emailDto.getEmailContent();
        String contentType = emailDto.getContentType(); // contentType "text/html; charset=utf-8"
        try {
            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            // content
            messageBodyPart.setContent(emailContent, contentType);
            multipart.addBodyPart(messageBodyPart);
            // attachment
            List<AttachFileEmailDto> attachFileList = emailDto.getAttachFile();
            if (CommonCollectionUtil.isNotEmpty(attachFileList)) {
                this.addAttachment(multipart, attachFileList);
            }
            //config mail
            JavaMailSenderImpl javaMailSender = this.configSmtpMail(configMail);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //set message header
            mimeMessage.addHeader("Content-type", configMail.getOrDefault(SmtpMailConstant.SMTP_HEADER_CONTENT_TYPE, ""));
            mimeMessage.addHeader("format", configMail.getOrDefault(SmtpMailConstant.SMTP_HEADER_FORMAT, ""));
            mimeMessage.addHeader("Content-Transfer-Encoding", configMail.getOrDefault(SmtpMailConstant.SMTP_HEADER_ENCODING, ""));
            
            mimeMessage.setFrom(new InternetAddress(configMail.getOrDefault(SmtpMailConstant.SMTP_SENDER_ADDRESS, "")));
            mimeMessage.setSubject(emailDto.getSubject(), "UTF-8");
            mimeMessage.setContent(multipart);
            mimeMessage.setSentDate(CommonDateUtil.getSystemDateTime());
            
            //recipient
            List<String>  toAddressList = emailDto.getToAddress();
            List<String>  ccAddressList = emailDto.getCcAddress();
            List<String>  bccAddressList = emailDto.getBccAddress();
            if(CommonCollectionUtil.isNotEmpty(toAddressList)) {
                InternetAddress[] toAddress = InternetAddress.parse(toAddressList.stream().collect(Collectors.joining(CommonConstant.COMMA)));
                mimeMessage.setRecipients(Message.RecipientType.TO, toAddress);
            }
            if(CommonCollectionUtil.isNotEmpty(ccAddressList)) {
                InternetAddress[] ccAddress = InternetAddress.parse(ccAddressList.stream().collect(Collectors.joining(CommonConstant.COMMA)));
                mimeMessage.setRecipients(Message.RecipientType.CC, ccAddress);
            }
            if(CommonCollectionUtil.isNotEmpty(bccAddressList)) {
                InternetAddress[] bccAddress = InternetAddress.parse(bccAddressList.stream().collect(Collectors.joining(CommonConstant.COMMA)));
                mimeMessage.setRecipients(Message.RecipientType.BCC, bccAddress);
            }
            javaMailSender.send(mimeMessage);
            
            responseEmailDto.setStatus(true);
        } catch (Exception e) {
            logger.error("[SmtpMailServiceImpl] [sendEmail] send mail error", e);
            responseEmailDto.setStatus(false);
            responseEmailDto.setErrorMessage(e.getMessage());
        }
        
        return responseEmailDto;
    }

    private JavaMailSenderImpl configSmtpMail(Map<String, String> smtpMailConfig) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        String host = smtpMailConfig.getOrDefault(SmtpMailConstant.SMTP_HOST, "");
        String portStr = smtpMailConfig.getOrDefault(SmtpMailConstant.SMTP_PORT, "");
        int port = Integer.parseInt(portStr);
        String senderAddress = smtpMailConfig.getOrDefault(SmtpMailConstant.SMTP_SENDER_ADDRESS, "");
        String password = smtpMailConfig.getOrDefault(SmtpMailConstant.SMTP_PASS, "");
        boolean sslSupport = SmtpMailConstant.STR_TRUE.equals(smtpMailConfig.getOrDefault(SmtpMailConstant.SMTP_SSL_FLAG, SmtpMailConstant.STR_FALSE));

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(senderAddress);

        Properties javaMailProperties = new Properties();
        if (CommonStringUtil.isNotBlank(password)) {
            mailSender.setPassword(password);
            javaMailProperties.put("mail.smtp.auth", "true");
            javaMailProperties.put("mail.smtp.starttls.enable", "true");
        }
        javaMailProperties.put("mail.smtp.ssl.trust", "*");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        
        javaMailProperties.put("mail.debug", "true");

        if (sslSupport) {
            javaMailProperties.put("mail.smtp.socketFactory.port", port);
            javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }

        // TrieuVD - 20200416 - The attached file name is converted to .dat in outlook if long name above 60
        System.setProperty("mail.mime.encodeparameters", "false");
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    private void addAttachment(Multipart multipart, List<AttachFileEmailDto> attachFileList) throws MessagingException {
        for (AttachFileEmailDto attachFileEmailDto : attachFileList) {
            byte[] fileByte = attachFileEmailDto.getFileByte();
            String contenType = attachFileEmailDto.getContentType();
            String fileName = attachFileEmailDto.getFileName();
            DataSource source = new ByteArrayDataSource(fileByte, contenType);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
        }
    }

}
