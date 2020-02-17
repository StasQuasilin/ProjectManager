package services.hibernate;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public class dbDAOService {

    private static final dbDAO dao = new HibernateDAO();

    public static dbDAO getDao() {
        return dao;
    }
}
