package com.dm.utils;

import com.dm.beans.EmailAuthentication;
import com.dm.common.GlobalConstant;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 * @author hu.yuhao
 * @date 2020-04-30
 * 邮件发送服务
 * */
public class JavaMailUtils {
    public static boolean sendEmail(String to, String copyTo, String subject, String content) {
        boolean result=false;
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", GlobalConstant.PROTOCOL);
        props.setProperty("mail.host", GlobalConstant.SMTP_SERVER);
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props, new EmailAuthentication(GlobalConstant.USERNAME, GlobalConstant.PASSWORD));
        session.setDebug(true);
        //创建代表邮件的MimeMessage对象
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(GlobalConstant.FROM));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copyTo));
            message.setSentDate(new Date());
            message.setSubject(subject);
            //保存并且生成邮件对象
            BodyPart mdp = new MimeBodyPart();//新建一个存放信件内容的BodyPart对象
            mdp.setContent(content, "text/html;charset=utf-8");//给BodyPart对象设置内容和格式/编码方式tcontent为邮件内容
            Multipart mm = new MimeMultipart(); //新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
            mm.addBodyPart(mdp);

            message.setContent(mm);
            message.saveChanges();
            //建立发送邮件的对象
            Transport.send(message);
            result=true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public static void main(String[] args) {
        boolean test = sendEmail("1099679383@qq.com", "", "test", "this is test email!!!");
    }
}
