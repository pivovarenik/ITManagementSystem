package dao;

import entities.User;
import org.hibernate.Session;
import util.HibernateUtil;

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
}