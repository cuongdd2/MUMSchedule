package app.student;

import static app.Application.*;
import static app.util.JsonUtil.dataToJson;
import static app.util.JsonUtil.jsonData;

import app.block.Block;
import app.clazz.Class2;
import app.course.Course;
import app.entry.Entry;
import app.professor.Professor;
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

      List<Class> classes = new ArrayList<>();
      int stid = 1;// get student id from login session that stores student id when student logged in.
      int eid = studentDao.getStudentEntry(stid);

      // Student entry comes here
      Entry entry = entryDao.getEntryById(eid);

      // List of block that belongs to the entry
      List<Block> blocks = blockDao.getBlocksByEntry(entry);


      // Get all available classes in the block
      for(Block b : blocks) {

          List<Class2> cls = classDao.getClassByBlock(b.getId());

          for(Class2 c : cls) {

              Professor prof = profDao.getProfById(c.getProf_id());
              Course course = courseDao.getcourse(c.getCourse_id());

              Class newClass = new Class(course, prof, b);
              newClass.setCapacity(c.getCapacity());
              newClass.setEnrolled(c.getEnrolled());
              newClass.setId(c.getId());

              classes.add(newClass);
          }
      }

      Map<String, Object> model = new HashMap<>();
      model.put("classess",classes);
      return ViewUtil.render(request, model, Path.Template.STUDENT_SCHEDULE);
  };

  public static Route registerCourse = (request, response) -> {

      int cid = Integer.parseInt(request.queryParams("courseid"));
      int sid = Integer.parseInt(request.queryParams("studentid")); // get student id from session

      studentDao.registerToCourse(cid, sid);

      return dataToJson(cid+sid);
  };

  public static boolean authenticate(String email, String password) {
      if (email.isEmpty() || password.isEmpty()) {
          return false;
      }
      Student student = studentDao.getUserByUsername(email);
      if (student == null) {
          return false;
      }

      //String hashedPassword = BCrypt.hashpw(password, student.getSalt());
      return true;//hashedPassword.equals(student.getHashedPassword());
    }

}
