package com.SparkyTS.springRest.dao;

import com.SparkyTS.springRest.entity.ForgotPassword;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class ForgotPasswordDAOImpl implements ForgotPasswordDAO {

    @Autowired
    private EntityManager entityManger;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean GenerateAndSendToken(String email) {

        String token = getTokenAndSaveToDb(email);

        boolean success = sendEmail(email,token);

        return success;
    }


    @Autowired
    private JavaMailSender javaMailSender;
    private boolean sendEmail(String email, String token) {
        try{
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);

            msg.setSubject("Reset Password");
            msg.setText("http://localhost:4200/resetPassword?token="+token);
            javaMailSender.send(msg);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String getTokenAndSaveToDb(String email) {
        Session session = entityManger.unwrap(Session.class);
        String token = UUID.randomUUID().toString();
        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setTimeStamp(new Timestamp(new Date().getTime()));
        forgotPassword.setEmail(email);
        forgotPassword.setToken(token);
        session.saveOrUpdate(forgotPassword);
        return token;
    }

    @Override
    public boolean verifyUser(String email) {
        Session session = entityManger.unwrap(Session.class);

        List<String> emails = session.createSQLQuery("select email from user where email='"+email+"'").list();

        return (emails!=null && emails.size()>0) ?  true : false;
    }

    @Override
    public void setNewPassword(String token, String password) throws Exception {
        Session session = entityManger.unwrap(Session.class);

        List<String> emails = session.createSQLQuery("SELECT email FROM forgot_password WHERE token='"+token+"'").list();
        if(emails!=null && emails.size()>0){
            session.createSQLQuery(
                    "UPDATE user SET password='"+ bCryptPasswordEncoder.encode(password) +"' WHERE email='"+emails.get(0)+"'"
            ).executeUpdate();
        } else
            throw new Exception("invalid or token expired!");
    }
}
