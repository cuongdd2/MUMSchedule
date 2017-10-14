package app.user;

import static app.Application.userDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.body;

import java.util.Map;
import spark.Route;

public class UserController {

  public static Route login = (request, response) -> {
    Map<String, String> data = body(request);
    User user = new User(data.get("email"), data.get("pw"));
    userDao.checkLogin(user);
    return dataToJson(userDao.checkLogin(user));
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
}
