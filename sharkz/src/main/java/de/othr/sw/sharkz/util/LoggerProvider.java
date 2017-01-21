package de.othr.sw.sharkz.util;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Dependent
public class LoggerProvider {
    
    @Produces
    public Logger getLogger(InjectionPoint p) {
        return Logger.getLogger("Sharkz-" + p.getMember().getName());
    }
}
