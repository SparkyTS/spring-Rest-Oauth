package com.SparkyTS.springRest.entity;

import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="forgot_password")
public class ForgotPassword {

    @Id
    @Column(name="time_stamp")
    public Timestamp timeStamp;

    @Column(name="email")
    public String email;

    @Column(name="token")
    public String token;

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ForgotPassword{" +
                "timeStamp=" + timeStamp +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
