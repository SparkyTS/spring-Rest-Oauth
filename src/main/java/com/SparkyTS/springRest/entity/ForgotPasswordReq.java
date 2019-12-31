package com.SparkyTS.springRest.entity;

public class ForgotPasswordReq {

    private String newPassword;
    private String token;

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ForgotPasswordReq{" +
                "newPassword='" + newPassword + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
