package com.crisis.crisisproject.manager;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;


import com.crisis.crisisproject.model.Utilisateur;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.crisis.crisisproject.configuration.Hibernate;

import org.hibernate.*;

public class UserDBManager {

    public static void UpdtateDetached(Utilisateur utilisateur){
        SessionFactory factory = Hibernate.getSessionFactory();
        Session session2 = factory.openSession();

        try{

            session2.getTransaction().begin();
            session2.merge(utilisateur);

            session2.flush();
            session2.getTransaction().commit();
            Hibernate.shutdown();

        }
        catch(Exception e){
            e.printStackTrace();
            session2.getTransaction().rollback();
        }


    }

    public static void InsertDetached(Utilisateur utilisateur){
        SessionFactory factory = Hibernate.getSessionFactory();
        Session session2 = factory.openSession();

        try{

            session2.getTransaction().begin();
            session2.save(utilisateur);

            session2.flush();
            session2.getTransaction().commit();
            Hibernate.shutdown();

        }
        catch(Exception e){
            e.printStackTrace();
            session2.getTransaction().rollback();
        }


    }

    public static  List<String> getListUser() {
        Session session = Hibernate.getSessionFactory().openSession();
        String hql = "SELECT email FROM Utilisateur ";

        // String hql = "SELECT u FROM "+Utilisateur.class.getName()+ " u WHERE u.idUser = :id";
        Query query = session.createQuery(hql);

        List<String> utilisateurs = query.list();
        session.close();
        return utilisateurs;
    }

    public static Utilisateur getUtilisateur(String emailUser) throws UnknownHostException, SocketException {
        SessionFactory factory = Hibernate.getSessionFactory();
        //System.out.println("Factory crée");
        Session session = factory.openSession();
        // System.out.println("Session crée");
        Utilisateur userFromDB = new Utilisateur();
        System.out.println("From UserDBManager : email passé enn entré => "+emailUser);
        userFromDB.setEmail("null_1");
        System.out.println("From UserDBManager : UserFromDB crée et email set a null , UserFromDB.getEmail() = => "+userFromDB.getEmail());
        List<Utilisateur> list = null;

        String hql = " FROM  Utilisateur  WHERE email = :id";

        try {
            session.getTransaction().begin();
            Query query = session.createQuery(hql);
            query.setParameter("id", emailUser);
            list = query.list();
            session.getTransaction().commit();
            Hibernate.shutdown();
        }
        catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        for(Utilisateur ite : list){
            System.out.println("From UserDBManager : 1er élément de la liste retourné par la db  , ite.getEmail() = => "+userFromDB.getEmail());
            if(ite.getEmail().equals(emailUser)){
                userFromDB.setEmail(ite.getEmail());
                userFromDB.setPassWord(ite.getPassWord());
                userFromDB.setRole(ite.getRole());
                userFromDB.setUserSafe(ite.getUserSafe());
                userFromDB.setFullName(ite.getFullName());
            }
        }


        if(userFromDB != null) {
            if (!(userFromDB.getEmail().equals(emailUser))) {
                userFromDB.setEmail("NOT FOUND");
                System.out.println("From UserDBManager : user pas trouvé donc email mis à note found , UserFromDB.getEmail() = => " + userFromDB.getEmail());
            }
        }

        return  userFromDB;
    }

    public static String getUserPassword(String email){
        System.out.println("Depuis UserDBManager getUserPassword : email passé en entrée :  "+email);
        SessionFactory factory = Hibernate.getSessionFactory();
        //System.out.println("Factory crée");
        Session session = factory.openSession();
        // System.out.println("Session crée");
        String password = "";
        List<Utilisateur> list = null;

        String hql = " FROM  Utilisateur  WHERE email = :id";

        try {
            session.getTransaction().begin();
            Query query = session.createQuery(hql);
            query.setParameter("id", email);
            list = query.list();
            session.getTransaction().commit();
            Hibernate.shutdown();
        }
        catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        for(Utilisateur ite : list){
            System.out.println("Depuis UserDBManager getUserPassword : email récupéré depuis la BD : email : "+ite.getEmail() + "password : "+ite.getPassWord());
            if(ite.getEmail().equals(email)){
                System.out.println("Depuis UserDBManager getUserPassword : email de la liste est même que celui passé en entrée");
                password = ite.getPassWord();
            }
        }
        System.out.println("Depuis UserDBManager getUserPassword : ");
        return  password;

    }
}
