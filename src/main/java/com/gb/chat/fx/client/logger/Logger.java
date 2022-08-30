package com.gb.chat.fx.client.logger;

public interface Logger {
    void log(String user,String message);
    String loadHistory(String user, int count);
}
