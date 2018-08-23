package com.haulmont.testtask.data;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public abstract class DAO<T> {

    private static final String SHUTDOWN_QUERY = "SHUTDOWN";

    protected static SessionFactory sessionFactory;

    /**
     * Инициализирует соединение с базой
     */
    public static void init() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    /**
     * Выполняет полный обрыв соединения с базой, сохраняет все измения
     */
    public static void closeDB() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(SHUTDOWN_QUERY);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    /**
     * Activity - сессия с активной транзакцией
     * @return активная сессия
     */
   protected Session beginActivity() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
   }

    /**
     * Выполняет коммит активности и закрывает сессию
     * @param activity активная сессия
     */
   protected void commit(Session activity) {
        activity.getTransaction().commit();
        activity.close();
   }


}
