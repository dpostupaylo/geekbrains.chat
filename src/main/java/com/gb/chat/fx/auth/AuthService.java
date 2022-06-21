package com.gb.chat.fx.auth;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass);
    void stop();
}

