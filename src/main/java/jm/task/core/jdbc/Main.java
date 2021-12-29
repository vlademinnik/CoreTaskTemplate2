package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Сайтама", "Сайтамыч", (byte) 27);
        userService.saveUser("Иосиф", "Сталин", (byte) 74);
        userService.saveUser("Киану", "Ривз", (byte) 57);
        userService.saveUser("Александр", "Пушкин", (byte) 37);
        userService.removeUserById(3);
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
        // реализуйте алгоритм здесь
    }
}
