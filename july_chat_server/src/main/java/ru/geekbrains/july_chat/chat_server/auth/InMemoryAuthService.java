package ru.geekbrains.july_chat.chat_server.auth;

import ru.geekbrains.july_chat.chat_server.error.UserNotFoundException;
import ru.geekbrains.july_chat.chat_server.error.WrongCredentialsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryAuthService implements AuthService {
    private List<User> users;

    public InMemoryAuthService() {
//        this.users = Arrays.asList();
        this.users = new ArrayList<>(
//                List.of() java 9+
                Arrays.asList(
                        new User("log1", "pass1", "Benny"),
                        new User("log2", "pass2", "Johny"),
                        new User("log3", "pass3", "Teddy"),
                        new User("log4", "pass4", "Bobby")
                )
        );
    }

    @Override
    public void start() {
        System.out.println("Auth service started!");
    }

    @Override
    public void stop() {
        System.out.println("Auth service stopped");
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        for (User user : users) {
            if (login.equals(user.getLogin())) {
                if (password.equals(user.getPassword())) return user.getNickname();
                else throw new WrongCredentialsException("");
            }
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public String changeNickname(String oldNick, String newNick) {
        return null;
    }

    @Override
    public void changePassword(String nickname, String oldPassword, String newPassword) {

    }

    @Override
    public void createNewUser(String login, String password, String nickname) {

    }

    @Override
    public void deleteUser(String nickname) {

    }
}
