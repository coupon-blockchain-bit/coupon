package com.bit.bonusPointsExchange.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.bean.User;

/**
 * 发送到邮箱
 * @author gmx
 */
public class EmailUtils {
	
	private static String propFileName="/com/bit/bonusPointsExchange/EmailUtils.properties";//指定资源文件保存的位置
	private static Properties prop = new Properties(); // 创建并实例化Properties对象的实例

	private static String emailFrom = null;
	private static String emailFromPasswd = null;
	 
	/*静态代码块，类初始化时加载数据库驱动 */ 
	static{ 
		try {
			InputStream in = EmailUtils.class.getClassLoader().getResourceAsStream(propFileName);
			prop.load(in);
			emailFrom = prop.getProperty("EMAIL_FROM");
			emailFromPasswd = prop.getProperty("EMAIL_FROM_PASSWD");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 通过输入流对象加载Properties文件
		
	}
	  
	  
    /** 
     * 注册成功后,向用户发送帐户激活链接的邮件 
     * @param user 未激活的用户 
     */  
    public static void sendAccountActivateEmail(User user) {  
        Session session = getSession();  
        MimeMessage message = new MimeMessage(session);  
        try {  
            message.setSubject("帐户激活邮件");  
            message.setSentDate(new Date());  
            message.setFrom(new InternetAddress(emailFrom));  
            message.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));  
            message.setContent("<a href='" + GenerateLinkUtils.generateActivateLink(user)+"'>点击激活帐户</a>","text/html;charset=utf-8");  
            // 发送邮件  
            Transport.send(message);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 向用户发送重设密码链接的邮件 
     */  
    public static void sendResetPasswordEmail(User user) {  
        Session session = getSession();  
        MimeMessage message = new MimeMessage(session);  
        try {  
            message.setSubject("找回您的帐户与密码");  
            message.setSentDate(new Date());  
            message.setFrom(new InternetAddress(emailFrom));  
            message.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));  
            message.setContent("要使用新的密码, 请使用以下链接启用密码:<br/><a href='" + GenerateLinkUtils.generateResetPwdLink(user) +"'>点击重新设置密码</a>","text/html;charset=utf-8");  
            // 发送邮件  
            Transport.send(message);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * 向商家发送重设密码链接的邮件 
     */  
    public static void sendResetPasswordEmail(Shop shop) {  
        Session session = getSession();  
        MimeMessage message = new MimeMessage(session);  
        try {  
            message.setSubject("找回您的帐户与密码");  
            message.setSentDate(new Date());  
            message.setFrom(new InternetAddress(emailFrom));  
            message.setRecipient(RecipientType.TO, new InternetAddress(shop.getEmail()));  
            message.setContent("要使用新的密码, 请使用以下链接启用密码:<br/><a href='" + GenerateLinkUtils.generateResetPwdLink(shop) +"'>点击重新设置密码</a>","text/html;charset=utf-8");  
            // 发送邮件  
            Transport.send(message);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    public static Session getSession() {  
        Properties props = new Properties();  
        props.setProperty("mail.transport.protocol", "smtp");  
        props.setProperty("mail.smtp.host", "smtp.qq.com");  
        props.setProperty("mail.smtp.port", "25");  
        props.setProperty("mail.smtp.auth", "true");  
        Session session = Session.getInstance(props, new Authenticator() {  
            @Override  
            protected PasswordAuthentication getPasswordAuthentication() {  
        	/*    String password = null;  
               InputStream is = EmailUtils.class.getResourceAsStream("password.dat");  
                byte[] b = new byte[1024];  
                try {  
                    int len = is.read(b);  
                    password = new String(b,0,len);  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
               */ 
            
                return new PasswordAuthentication(emailFrom, emailFromPasswd);  
            }  
              
        });  
        return session;  
    }  
}
