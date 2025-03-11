package db.Service;

import db.Models.User;
import db.dao.dao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceHibernate implements service {
    private final dao methods;

    public ServiceHibernate(dao methods) {
        this.methods = methods;
    }


    @Override
    public List<User> getAllUsers() {
        return (methods.getAllUsers());
    }

    @Override
    public User getUserByMail(String mail) {
        return (methods.getUserByMail(mail));
    }

    @Override
    public Boolean addUser(User user) {
        return methods.addUser(user);
    }

    @Override
    public Boolean deleteUser(String mail) {
        return (methods.deleteUser(mail));
    }

    @Override
    public void changeUser(User user) {
        methods.changeUser(user);
    }
}
