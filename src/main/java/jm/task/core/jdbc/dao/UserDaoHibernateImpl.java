package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Transactional
public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() throws SQLException {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id int NOT NULL AUTO_INCREMENT, name varchar(45), lastname varchar(45), age tinyint, PRIMARY KEY (id))").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.delete(session.get(User.class, id));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
