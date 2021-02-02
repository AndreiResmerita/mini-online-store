package wantsome.project.web;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import spark.Request;
import wantsome.project.DAO.CartDAO;

import java.util.Map;

import static wantsome.project.web.RequestUtil.RequestUtil.*;

public class ViewUtil {

    /**
     * Utility method, 'combines' a model with a Velocity template file,
     * to render (build) a response page.
     * Recommended at: http://sparkjava.com/documentation#views-and-templates
     */

    public static String render(Request request, Map<String, Object> model, String templatePath) {
        model.put("cs", CartDAO.productDTOList.size());
        model.put("admin", getSessionAdmin(request));
        model.put("currentUser", getSessionCurrentUser(request));
        return new VelocityTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
