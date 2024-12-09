package util;

import org.hibernate.Session;

public class HibernatePreloader {
    public static void preload() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.createQuery("FROM User").setMaxResults(1).list();
        }
    }
}
