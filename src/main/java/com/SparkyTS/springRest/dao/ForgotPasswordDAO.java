package com.SparkyTS.springRest.dao;

public interface ForgotPasswordDAO {

    boolean GenerateAndSendToken(String email);

    boolean verifyUser(String email);

    void setNewPassword(String token, String password) throws Exception;
}
