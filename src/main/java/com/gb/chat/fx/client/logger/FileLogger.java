package com.gb.chat.fx.client.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileLogger implements Logger {
    private Path path;

    public FileLogger(){
    }

    @Override
    public void log(String user, String message) {
        this.path = Path.of(user,"log.txt");

        ensureFileExists(path);

        try {
            Files.writeString(path, message,
                    StandardOpenOption.APPEND);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String loadHistory(String user, int count) {
        String result = "";
        this.path = Path.of(user, "log.txt");

        if (!Files.exists(path)) {
            return result;
        }

        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(path);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Collections.reverse(lines);
        int counter = 0;
        while (counter < count && counter < lines.size()){
            result += lines.get(counter) + "\n";
            counter++;
        }

        return result;
    }

    private void ensureFileExists(Path path){
        Path parent = path.getParent();

        if (!Files.exists(parent)){
            try{Files.createDirectory(parent);}
            catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }

        if (!Files.exists(path)){
            try{
                Files.createFile(path);
            } catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
