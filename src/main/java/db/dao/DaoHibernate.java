package db.dao;

import db.Models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoHibernate implements dao {
    private final EntityManagerFactory entityManagerFactory;

    public DaoHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<User> getAllUsers() {
        EntityManager manager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<User> query = manager.createQuery("from User", User.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            System.out.println(e);
            return (new ArrayList<>());
        } finally {
            manager.close();
        }

    }

    @Override
    public User getUserByMail(String mail) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        try {
            String jpql = "SELECT user FROM User user WHERE user.mail = :value";
            Query query = manager.createQuery(jpql);
            query.setParameter("value", mail);
            return ((User) query.getSingleResult());
        } catch (PersistenceException e) {
            return (null);
        } finally {
            manager.close();
        }
    }

    @Override
    public Boolean addUser(User user) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(user);
            transaction.commit();
            return (true);
        } catch (PersistenceException e1) {
            transaction.rollback();
            return (false);
        } finally {
            manager.close();
        }

    }

    @Override
    public Boolean deleteUser(String mail) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        try {
            Query query = manager.createQuery("DELETE User user WHERE mail IN (:mail)");
            query.setParameter("mail", mail);
            query.executeUpdate();
            transaction.commit();
            return (true);
        } catch (PersistenceException e) {
            transaction.rollback();
            return (false);
        } finally {
            manager.close();
        }

    }

    @Override
    public void changeUser(User user) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(user);
            transaction.commit();
        } catch (PersistenceException e) {
            try {
                transaction.rollback();
                transaction.begin();
                Query query = manager.createQuery("DELETE User user WHERE mail IN (:mail)");
                query.setParameter("mail", user.getMail());
                query.executeUpdate();
                manager.persist(user);
                transaction.commit();
            } catch (PersistenceException e1) {
                transaction.rollback();
                System.out.println(e1);
            }
        } finally {
            manager.close();
        }
    }
}
