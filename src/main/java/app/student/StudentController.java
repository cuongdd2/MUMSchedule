package app.student;

import static app.Application.entryDao;
import static app.Application.studentDao;
import static app.Application.userDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.getName;
import static app.util.RequestUtil.getStartDate;

import app.entry.Entry;
import app.user.User;
import app.util.Path;
import app.util.ViewUtil;
import org.mindrot.jbcrypt.BCrypt;
import spark.Route;


import java.util.HashMap;
import java.util.Map;
import static app.util.RequestUtil.*;

public class StudentController {

  public static Route add = (request, response) -> {
    return null;
  };
  public static Route list = (request, response) -> {
    return null;
  };
  public static Route change = (request, response) -> {
    return null;
  };
  public static Route remove = (request, response) -> {
    return null;
  };

  public static Route loginPage = (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("logout", removeSessionAttrLoggedOut(request));
      model.put("logindirect", removeSessionAttrLoginRedirect(request));

      return ViewUtil.render(request, model, Path.Template.LOGIN);
  };
}
