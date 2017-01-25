package de.othr.sw.sharkz.util;



import de.othr.sw.sharkz.entity.Administrator;
import de.othr.sw.sharkz.entity.Customer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.transaction.UserTransaction;

public class InitialDataProvider implements Servlet {

    @PersistenceContext
    EntityManager em;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        
        System.out.println("INITIALISIERUNG SERVLET GEHT!");
        
        Query q = em.createNativeQuery("SELECT COUNT(*) FROM Account");
        
        if ((Integer) q.getSingleResult() != 0) {
            return;
        }
        
        try {
            UserTransaction transaction = (UserTransaction)
                    new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();  
            
            Administrator admin = new Administrator();
            admin.seteMail("admin@sharkz.de");
            admin.setPassword("admin");
            
            Customer c1 = new Customer();
            c1.seteMail("andi@gmail.com");
            c1.setPassword("123456");
            c1.setFirstName("Andreas");
            c1.setLastName("König");
            
            try {
                em.persist(admin);
                em.persist(c1);
                em.flush();
            } catch (NullPointerException e) {
                System.out.println("InitialDataProvider: fucked up...");
            }
            
            transaction.commit();
        } catch (Exception ex) {
            Logger.getLogger(InitialDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        
    }

    @Override
    public String getServletInfo() {
        return "Servlet which provides initial data for the database";
    }

    @Override
    public void destroy() {
        
    }
    
}
