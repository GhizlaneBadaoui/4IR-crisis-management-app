package com.crisis.crisisproject.manager;

import com.crisis.crisisproject.configuration.Hibernate;
import com.crisis.crisisproject.model.Notification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class NotificationDBManager {
    //Pour mettre a jour une notification
    public static void UpdtateDetached(Notification newNotif){
        SessionFactory factory = Hibernate.getSessionFactory();
        Session session2 = factory.openSession();

        try{

            session2.getTransaction().begin();
            session2.merge(newNotif);

            session2.flush();
            session2.getTransaction().commit();
            Hibernate.shutdown();

        }
        catch(Exception e){
            e.printStackTrace();
            session2.getTransaction().rollback();
        }


    }

    public static void InsertDetached(Notification newNotif){
        SessionFactory factory = Hibernate.getSessionFactory();
        Session session2 = factory.openSession();

        try{

            session2.getTransaction().begin();
            session2.save(newNotif);

            session2.flush();
            session2.getTransaction().commit();
            Hibernate.shutdown();

        }
        catch(Exception e){
            e.printStackTrace();
            session2.getTransaction().rollback();
        }


    }

    public static  List<Notification> getListNotifForUser(String emailUser) {

        Session session = Hibernate.getSessionFactory().openSession();
        String hql = "FROM Notification  WHERE senderNotif = :id";
        List<Notification> listNotifFromDB = null;


        try {
            session.getTransaction().begin();
            Query query = session.createQuery(hql);
            query.setParameter("id", emailUser);
            listNotifFromDB = query.list();
            session.getTransaction().commit();
            Hibernate.shutdown();
        }
        catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        return listNotifFromDB;
    }
}