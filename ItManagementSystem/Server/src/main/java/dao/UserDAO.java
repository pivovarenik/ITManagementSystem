package dao;

import entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.Collection;
import java.util.List;

public class UserDAO extends GenericDAOImpl<User, Long> {
    public UserDAO() {
        super(User.class);
    }

    public User findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        }
    }
    public User findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User WHERE fullName = :fullname", User.class)
                    .setParameter("fullname",name)
                    .uniqueResult();
        }
    }
    public boolean comparePasswords(User user){
        User data = this.findByUsername(user.getUsername());
        if(data.getPassword().equals(user.getPassword())){
            return true;
        }
        else return false;
    }
    public List<User> getUsersExcluding(Collection<Integer> userIds){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM User WHERE id NOT IN :ids";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("ids", userIds);
            return query.list();
        }
    }
}