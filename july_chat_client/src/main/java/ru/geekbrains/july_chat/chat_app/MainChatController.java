package ru.geekbrains.july_chat.chat_app;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ru.geekbrains.july_chat.chat_app.net.ChatMessageService;
import ru.geekbrains.july_chat.chat_app.net.MessageProcessor;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainChatController implements Initializable, MessageProcessor {
    public VBox loginPanel;
    public TextField loginField;
    public PasswordField passwordField;
    private ChatMessageService chatMessageService;
    private String nickName;
    public VBox mainChatPanel;
    public TextArea mainChatArea;
    public ListView<String> contactList;
    public TextField inputField;
    public Button btnSendMessage;
    private ChatLogger chatLogger;


    public void mockAction(ActionEvent actionEvent) {

    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void sendMessage(ActionEvent actionEvent) {
        String text = inputField.getText();
        if (text.isEmpty()) return;
        String message = this.nickName + ": " + text;
        chatMessageService.send(message);
//        try {
//            chatLogger.chatLogAdd(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
// данная запись продублирует запись чатлога
        inputField.clear();
    }


    @Override
    public void processMessage(String message) {
        Platform.runLater(() -> {
            try {
                parseMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void sendAuth(ActionEvent actionEvent) {
        if (loginField.getText().isBlank() || passwordField.getText().isBlank()) return;
        chatMessageService.connect();
        chatMessageService.send("auth: " + loginField.getText() + " " + passwordField.getText());
    }

    private void parseMessage(String message) throws IOException {
        if (message.startsWith("authok: ")) {
            this.nickName = message.substring(8);
            loginPanel.setVisible(false);
            mainChatPanel.setVisible(true);
            this.chatLogger = new ChatLogger(nickName);
            List<String> chatLog = chatLogger.chatLogReader();
            for (String s : chatLog) {
                mainChatArea.appendText(s + System.lineSeparator());
            }
        } else if (message.startsWith("ERROR: ")) {
            showError(message);
        } else if (message.startsWith("$.list: ")) {
            ObservableList<String> list = FXCollections.observableArrayList(message.substring(8).split("\\s"));
            contactList.setItems(list);
        } else {
            mainChatArea.appendText(message + System.lineSeparator());
            chatLogger.chatLogAdd(message + System.lineSeparator());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(message);

        alert.showAndWait();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        List<String> contacts = Arrays.asList("Vasya", "Petya", "Masha", "Kolya", "Sergey");
//        ObservableList<String> list = FXCollections.observableArrayList("Vasya", "Petya", "Masha", "Kolya", "Sergey");
//        contactList.setItems(list);
        this.chatMessageService = new ChatMessageService(this);
    }
}
