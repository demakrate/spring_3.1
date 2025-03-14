package db.Service;

import db.Models.User;

import java.util.List;

public interface Service {
    List<User> getAllUsers();

    User getUserByMail(String mail);

    Boolean addUser(User user);

    Boolean deleteUser(String mail);

    void changeUser(User user);
}
