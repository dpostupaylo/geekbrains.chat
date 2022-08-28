package com.gb.chat.fx.server.db;

import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String pass;
    private String nick;

    public User(int id, String login, String pass, String nick) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.nick = nick;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }
    public String getPass() {
        return pass;
    }

    public String getNick() {
        return nick;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && login.equals(user.login) && pass.equals(user.pass) && nick.equals(user.nick);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id ,login, pass, nick);
    }
}
