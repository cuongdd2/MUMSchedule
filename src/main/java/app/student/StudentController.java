package app.student;

import static app.util.JsonUtil.jsonData;

import app.clazz.Class;

import app.util.Path;
import app.util.ViewUtil;
import spark.Route;


import java.util.HashMap;
import java.util.List;
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

  public static Route loginPost = (request, response) -> {
      Map<String, Object> model = new HashMap<>();

      if(!authenticate(request.queryParams("username"), request.queryParams("password")) ) {
          model.put("authenticationFailed", true);
          return ViewUtil.render(request, model, Path.Template.LOGIN);
      }

      request.session().attribute("currentUser", request.queryParams("username"));

      if(getQueryLoginRedirect(request) !=null) {
          response.redirect(getQueryLoginRedirect(request));
      }

      response.redirect("/api/welcome");
      return null;
  };

  public static Route schedulePage = (request, response) -> {

      List<Class> courses = null;



      return jsonData(true, courses);
  };

  public static boolean authenticate(String username, String password) {
      if (username.isEmpty() || password.isEmpty()) {
          return false;
      }
    //        Student student = studentDao.getUserByUsername(username);
    //        if (student == null) {
    //            return false;
    //        }
        //String hashedPassword = BCrypt.hashpw(password, student.getSalt());
      return true;//hashedPassword.equals(student.getHashedPassword());
    }



}
