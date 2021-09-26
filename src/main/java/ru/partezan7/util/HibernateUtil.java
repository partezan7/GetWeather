package main.java.ru.partezan7.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {


    private static SessionFactory sessionFactory = initSessionFactory();

    private static SessionFactory initSessionFactory() {
        String path = new File("").getAbsolutePath();

        try {
            return new Configuration()
                    .configure(new File(path + "\\src\\main\\resources\\ru\\partezan7\\hibernate.cfg.xml"))
                    .buildSessionFactory();
        } catch (Throwable e) {
            System.out.println("Initial SessionFactory creation failed ");
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            initSessionFactory();
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        getSessionFactory().close();
    }
}