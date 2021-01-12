package wantsome.project.web;

import org.apache.velocity.app.VelocityEngine;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.*;
import wantsome.project.Controller.CartController;
import wantsome.project.DAO.CartDAOImpl;

import java.util.Map;

import static wantsome.project.web.RequestUtil.RequestUtil.*;

public class SparkUtil {

    /**
     * Utility method, 'combines' a model with a Velocity template file,
     * to render (build) a response page.
     * Recommended at: http://sparkjava.com/documentation#views-and-templates
     */

    public static String render(Request request, Map<String, Object> model, String templatePath) {
        model.put("cs", CartDAOImpl.productDTOList.size());
        model.put("admin",getSessionAdmin(request));
        model.put("currentUser", getSessionCurrentUser(request));
        model.put("WebPath", Paths.Web.class);

        return strictVelocityEngine().render(new ModelAndView(model, templatePath));
    }

    private static VelocityTemplateEngine strictVelocityEngine() {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loaders", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityTemplateEngine(configuredEngine);
    }



}
