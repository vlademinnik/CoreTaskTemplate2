package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getBuildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users_table" +
                    "(ID BIGINT NOT NULL AUTO_INCREMENT," +
                    " NAME VARCHAR(45) NOT NULL ," +
                    " LAST_NAME VARCHAR(45) NOT NULL, " +
                    " AGE TINYINT NOT NULL," +
                    "PRIMARY KEY (ID))").executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String str2 = "DROP TABLE IF EXISTS users_table";
        try (Session session = Util.getBuildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(str2).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getBuildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("User по имени – " + name + " добавлен в базу данных");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getBuildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(new User(id));
            transaction.commit();
            System.out.println("User удален по id");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        Session session = null;
        List<User> users = new ArrayList<>();
        try {
            session = Util.getBuildSessionFactory().openSession();
            transaction = session.beginTransaction();
            users = session.createQuery("from User ").list();
            transaction.commit();
            System.out.println("Получение users из таблицы");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        List<User> users;
        try (Session session = Util.getBuildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            users = session.createQuery("from User ").list();
            for (User u : users) {
                session.delete(u);
            }
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
