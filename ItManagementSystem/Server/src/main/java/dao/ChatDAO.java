package dao;

import entities.Chat;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDAO extends GenericDAOImpl<Chat, Integer> {
    public ChatDAO() {
        super(Chat.class);
    }

    public List<Chat> findChatsByUserId(int userId) throws SQLException {
        List<Chat> chats = new ArrayList<>();
        String sql = "SELECT * FROM chats WHERE user_one_id = ? OR user_two_id = ?";
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { // Метод для получения соединения
            transaction = session.beginTransaction();
            String hql = "FROM Chat c WHERE c.userOne.id = :userId OR c.userTwo.id = :userId";
            chats = session.createQuery(hql, Chat.class)
                    .setParameter("userId", userId)
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return chats;
    }
}