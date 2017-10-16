package app.student;

import static app.Application.*;
import static app.util.JsonUtil.jsonData;

import app.block.Block;
import app.entry.Entry;
import spark.Route;
import app.util.Path;
import app.clazz.Class;
import app.util.ViewUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

      HashMap<Integer, List<Class>> classes = new HashMap<>();

      int studentid = 1;// get student id from login session that stores student id when student logged in.
      int entryid = studentDao.getStudentById(studentid);
      Date startdate = entryDao.getEntryById(entryid);

      List<Integer> blocks = blockDao.getBlocksByStartdate(startdate);

      System.out.println(blocks);

      for(int b : blocks) {

          List<Class> cls = classDao.getClassByBlock(b);
          if(cls.size() !=0) {
              classes.put(b, cls);
          }
      }

      return jsonData(true, classes);
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
