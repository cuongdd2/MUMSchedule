package app.user;

import static app.Application.userDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.getQueryLoginRedirect;

import app.util.Path;
import app.util.ViewUtil;
import java.util.HashMap;
import java.util.Map;
import spark.Route;

public class UserController {
  public static HashMap<String, User> users = new HashMap<>();

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

  public static Route add = (request, response) -> {
    return dataToJson(1);
  };
  public static Route list = (request, response) -> {
    return dataToJson(1);
  };
  public static Route change = (request, response) -> {
    return dataToJson(1);
  };
  public static Route remove = (request, response) -> {
    return dataToJson(1);
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
