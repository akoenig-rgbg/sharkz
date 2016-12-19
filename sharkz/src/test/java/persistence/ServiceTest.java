package persistence;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.service.AccountService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServiceTest {
    
    
    //static CustomerService customerService;
    
    public ServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /*
    @Test
    public void CustomerServiceTest() {
        
        final String firstName = "Andreas";
        final String lastName = "König";
        final String eMail = "andreas.koenig@st.oth-regensburg.de";
        final String password = "123456";
        
        long id = customerService.createCustomer(firstName, lastName, eMail, password,
                null);
        
        Customer customer = customerService.findCustomer(id);
        
        assertEquals(customer.getFirstName(), firstName);
        
    }*/
}
