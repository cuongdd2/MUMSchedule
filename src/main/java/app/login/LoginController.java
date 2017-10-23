package app.login;

import static app.Application.userDao;
import static app.util.RequestUtil.getQueryLoginRedirect;
import static app.util.RequestUtil.removeSessionAttrLoggedOut;
import static app.util.RequestUtil.removeSessionAttrLoginRedirect;

import app.user.User;
import app.util.Path;
import app.util.ViewUtil;
import java.util.HashMap;
import java.util.Map;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginController {
  public static HashMap<String, User> users = new HashMap<>();


  public static Route loginPage = (request, response) -> {
    Map<String, Object> model = new HashMap<>();
    model.put("logout", removeSessionAttrLoggedOut(request));
    model.put("logindirect", removeSessionAttrLoginRedirect(request));

    return ViewUtil.render(request, model, Path.Template.LOGIN);
  };

  public static Route login = (request, response) -> {
    Map<String, Object> model = new HashMap<>();

    if(!authenticate(request.queryParams("username"), request.queryParams("password")) ) {
      model.put("authenticationFailed", true);
      return ViewUtil.render(request, model, Path.Template.LOGIN);
    }

    model.put("authenticationSucceeded", true);
    request.session().attribute("currentUser", request.queryParams("username"));

    if(getQueryLoginRedirect(request) !=null) {
      response.redirect(getQueryLoginRedirect(request));
    }

    return ViewUtil.render(request, model, Path.Template.WELCOME);
  };


  public static Route handleLogoutPost = (Request request, Response response) -> {
    request.session().removeAttribute("currentUser");
    request.session().attribute("loggedOut", true);
    response.redirect(Path.Web.LOGIN);
    return null;
  };

  // The origin of the request (request.pathInfo()) is saved in the session so
  // the user can be redirected back after login
  public static Filter ensureUserIsLoggedIn = (request, response) -> {
    if (request.session().attribute("currentUser") == null) {
      request.session().attribute("loginRedirect", request.pathInfo());
      response.redirect(Path.Web.LOGIN);
    }
  };

  public static boolean authenticate(String email, String password) {
    if (email.isEmpty() || password.isEmpty()) {
      return false;
    }
    if (!users.containsKey(email)) {
      User user = userDao.getUserByEmail(email);
      if (user == null) return false;
      users.put(email, user);
    }
    return password.equals(users.get(email).getPassword());
  }


}
