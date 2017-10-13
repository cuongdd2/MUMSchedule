package app.welcome;

import app.util.Path;
import app.util.ViewUtil;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static app.util.RequestUtil.removeSessionAttrLoggedOut;
import static app.util.RequestUtil.removeSessionAttrLoginRedirect;

public class WelcomeController {


    public static Route welcomePage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("authenticationSucceeded", true);

        return ViewUtil.render(request, model, Path.Template.WELCOME);
    };
}
