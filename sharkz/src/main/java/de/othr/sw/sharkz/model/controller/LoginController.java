package de.othr.sw.sharkz.model.controller;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class LoginController implements Serializable {
    
    @Inject private Logger logger;
    
    public void test() {
        logger.info("Test for dependent injection");
    }
}
