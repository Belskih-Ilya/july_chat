package ru.geekbrains.july_chat.chat_app;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatLogger {
    private static final int CHAT_LOG_CAPACITY = 100;
    private String login;
    private File history;

    public ChatLogger(String login) {
        this.login = login;
        this.history = new File("history/history_" + login + ".txt");
        if(!history.exists()) {
            File hisoryPath = new File("history");
            hisoryPath.mkdirs();
        }
    }

    public List<String> chatLogReader() throws IOException {
        List<String> chatLog = new ArrayList<>();
        if (history.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(history))) {
                String chatMessage;
                while ((chatMessage = reader.readLine()) != null) {
                    chatLog.add(chatMessage);
                }
            }
        } else {
            return Collections.singletonList("Отсутствует история сообщений");
        }
        if (chatLog.size() <= CHAT_LOG_CAPACITY) {
            return chatLog;
        } else {
            int new_first = chatLog.size() - CHAT_LOG_CAPACITY;
            List<String> chatLog_oversize = new ArrayList<>();
            for (int i = new_first - 1; i < chatLog.size(); i++) {
                chatLog_oversize.add(chatLog.get(i));
            }
            return chatLog_oversize;
        }
    }

    public void chatLogAdd(String message) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(history, true))) {
         writer.write(message);
        }
    }

}
