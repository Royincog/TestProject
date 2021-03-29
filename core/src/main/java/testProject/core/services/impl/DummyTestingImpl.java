package testProject.core.services.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.serviceusermapping.ServiceUserMapped;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jvm.hotspot.interpreter.Bytecodes;
import testProject.core.services.interfaces.DummyTesting;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;

@Component(service = DummyTesting.class,immediate = true,
reference = {
        @Reference(
                name = "blog-user",target = "(subServiceName=blogsubservice)",service= ServiceUserMapped.class
        )
})
public class DummyTestingImpl implements DummyTesting{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private ResourceResolverFactory resolverFactory;

    @SlingObject
    SlingHttpServletRequest request;

    @Override
    public void doSomething() {

        logger.info("Inside the dummy something");


      //  ResourceResolver resourceResolver = request.getResourceResolver();
     //   Resource resource = resourceResolver.getResource(resourcePath);
     //   resourceResolver.close();


    }


    @Activate
    @Modified
    protected void activate(){
       logger.info("Something about resource");

       //Step 1 : Create a system user
        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put(ResourceResolverFactory.SUBSERVICE,"blogsubservice");


       //Step 2 :
        String resourcePath = "/conf/EditableTemplateForTestingProject/settings/wcm/templates/empty-page/structure/jcr:content/root/responsivegrid/banner";
        try {
            ResourceResolver resourceResolver = resolverFactory.getServiceResourceResolver(userMap);
            Resource res = resourceResolver.getResource(resourcePath);
            Session session =  resourceResolver.adaptTo(Session.class);
            if (res != null) { //asserts in case they are null

                //accessing the properties of the node
               // ValueMap properties  = res.adaptTo(ValueMap.class);

             //   if (properties != null) { //asserts in case of null value
               //     String title = properties.get("bannerTitle", String.class);

               //     logger.info("Title is " + title);
                Node node = res.adaptTo(Node.class);
                try {
                    if (node != null) {
                        logger.info("Node info " + node.getName());
                        node.setProperty("bannerTitle","This is generted from Sling");
                        node.setProperty("bannerSubtitle","This ia a subtitle from Sling");
                        node.addNode("/content/ProjectTwo/en/new-page/jcr:content/root");
                        session.save();
                    }
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
            }

            logger.info("resource info " + res.getName());
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
