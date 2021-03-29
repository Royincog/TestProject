package testProject.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testProject.core.services.interfaces.BloggerInfo;

@Component(immediate = true,service = BloggerInfo.class)
public class BloggerInfoImpl implements BloggerInfo {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Activate
    protected void writeToLoggerActivate(){
        logger.info("This is a open source AEM project");
    }

    @Deactivate
    protected void writeToLoggerDeactivate(){
        logger.info("Deactivating this dummy service");
    }


    @Override
    public String getDeveloperInfo() {
        return "We all are cool developers";
    }
}
