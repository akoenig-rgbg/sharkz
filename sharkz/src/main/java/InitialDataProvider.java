

import de.othr.sw.sharkz.entity.Administrator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        try {
            UserTransaction transaction = (UserTransaction)
                    new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            
            System.out.println("InitialData!");
            
            Administrator admin = new Administrator();
            
            admin.seteMail("admin@sharkz.de");
            admin.setPassword("admin");
            
            em.persist(admin);
            em.flush();
            
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
