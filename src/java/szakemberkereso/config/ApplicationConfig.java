/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.config;


import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import szakemberkereso.restservices.LoginController;

/**
 *
 * @author developer
 */
@javax.ws.rs.ApplicationPath("/szakemberkereso")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        try {
            resources.add(Class.forName("org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature"));
            resources.add(MultiPartFeature.class);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ApplicationConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically populated with all resources defined in the project. If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(szakemberkereso.restservices.AdminController.class);
        resources.add(szakemberkereso.restservices.LoginController.class);
        resources.add(szakemberkereso.restservices.MainPageController.class);
        resources.add(szakemberkereso.restservices.OfficeController.class);
        resources.add(szakemberkereso.restservices.SearchController.class);
        resources.add(szakemberkereso.restservices.SpecialistController.class);
    }

}
