package com.gb.chat.fx.auth;

import com.gb.chat.fx.server.db.ChatDataBase;
import com.gb.chat.fx.server.db.User;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private List<User> entries;
    private ChatDataBase chatDataBase;

    @Override
    public void start() {
        System.out.println("Authentication service is launched");
    }

    @Override
    public void stop() {
        System.out.println("Authentication service stopped");
    }

    public BaseAuthService() {
        chatDataBase = new ChatDataBase();
        entries = new ArrayList<>();
        entries = chatDataBase.getUsersList();
    }

    @Override
    public void updateUserNick(User user, String nick){
        entries.remove(user);
        chatDataBase.modifyUserNick(user, nick);
        entries.add(new User(user.getId(), user.getLogin(), user.getPass(), nick));
    }

    @Override
    public User getUserByLoginPass(String login, String pass) {
        for (User o : entries) {
            if (o.getLogin().equals(login) && o.getPass().equals(pass)) return o;
        }
        return null;
    }

}
