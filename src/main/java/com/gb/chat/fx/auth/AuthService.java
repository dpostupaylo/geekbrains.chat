package com.gb.chat.fx.auth;

import com.gb.chat.fx.server.db.User;

public interface AuthService {
    void start();
    User getUserByLoginPass(String login, String pass);

    void updateUserNick(User user, String nick);
    void stop();
}

