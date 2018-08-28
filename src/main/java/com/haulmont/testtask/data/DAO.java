package com.haulmont.testtask.data;


import com.haulmont.testtask.data.entity.Identifiable;
import com.haulmont.testtask.exceptions.MedicamentsSystemException;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class DAO<E extends Identifiable> {

    private static final String CONNECTIVITY_ERROR = "Database connectivity error.";

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
    public static void closeDB() throws MedicamentsSystemException {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery(SHUTDOWN_QUERY);
            session.getTransaction().commit();
            session.close();
            sessionFactory.close();
        } catch (HibernateException exc) {
            System.err.println(CONNECTIVITY_ERROR);
            exc.printStackTrace();
            throw new MedicamentsSystemException(CONNECTIVITY_ERROR);
        }
    }

    /**
     * Activity - сессия с активной транзакцией
     * @return активная сессия
     */
    protected Session beginActivity() throws MedicamentsSystemException {
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
        } catch (HibernateException exc) {
            System.err.println(CONNECTIVITY_ERROR);
            exc.printStackTrace();
            throw new MedicamentsSystemException(CONNECTIVITY_ERROR);
        }
        return session;
    }

    /**
     * Выполняет коммит активности и закрывает сессию
     * @param activity активная сессия
     */
    protected void commit(Session activity) throws MedicamentsSystemException {
        try {
            activity.getTransaction().commit();
            activity.close();
        } catch (HibernateException exc) {
            System.err.println(CONNECTIVITY_ERROR);
            exc.printStackTrace();
            throw new MedicamentsSystemException(CONNECTIVITY_ERROR);
        }
    }

    /**
     * Добавляет объект к модели данных
     * @param item объект добавления
     * @throws MedicamentsSystemException
     */
    public void add(E item) throws MedicamentsSystemException {
        Session activity = beginActivity();
        activity.save(item);
        commit(activity);
    }

    public void delete(E item) throws MedicamentsSystemException {
        Session activity = beginActivity();
        activity.delete(item);
        commit(activity);
    }

    public E getById(Long id) {
        Session activity = beginActivity();
        E entity = (E) activity.load(getEntityClass(), id);
        commit(activity);
        return entity;
    }

    public List<E> getAll() {
        Session activity = beginActivity();
        String getAllQuery = "From " +  this.getEntityClass().getSimpleName();
        List<E> entityList = activity.createQuery(getAllQuery).list();
        commit(activity);
        return entityList;
    }

    private Class getEntityClass() {
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }



}
