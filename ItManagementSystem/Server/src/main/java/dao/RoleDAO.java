package dao;

import entities.Role;
import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class RoleDAO  extends GenericDAOImpl<Role,Integer>{
    public RoleDAO() {
        super(Role.class);
    }
    public Role findRoleById(Integer id) {
        Transaction transaction = null;
        Role role = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            role = session.get(Role.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return role;
    }
}
