package dao;

import entities.Message;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class MessageDAO extends GenericDAOImpl<Message, Integer> {
    public MessageDAO() {
        super(Message.class);
    }


    public List<Message> findMessagesByChatId(int id) {
        List<Message> messages = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Message> query = session.createQuery(
                    "FROM Message WHERE chat.id = :chatId order by sentAt", Message.class);
            query.setParameter("chatId", id);

            messages = query.list();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }
}
