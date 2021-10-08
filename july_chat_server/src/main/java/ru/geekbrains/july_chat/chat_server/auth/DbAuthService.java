package ru.geekbrains.july_chat.chat_server.auth;

import ru.geekbrains.july_chat.chat_server.error.UsersDb;

public class DbAuthService implements AuthService {
    private UsersDb usersDb;

    @Override
    public void start() {
        usersDb = UsersDb.getUsersDb();
    }

    @Override
    public void stop() {
        usersDb.disconnectDb();
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return usersDb.getNickname(login, password);
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
