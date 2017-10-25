package app.user;

import static app.Application.registrationDao;
import static app.Application.studentDao;
import static app.Application.userDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.JsonUtil.jsonData;
import static app.util.RequestUtil.body;
import static spark.Spark.halt;

import app.login.LoginController;
import app.student.Student;
import app.util.Path.Template;
import app.util.ViewUtil;
import java.util.HashMap;
import java.util.Map;
import org.sql2o.Sql2oException;
import spark.Filter;
import spark.Route;

public class UserController {


  public static Route get = (request, response) -> {
    try {
      response.status(200);
      response.type("application/json");
      return dataToJson(userDao.get(Integer.parseInt(request.params(":id"))));
    } catch (Sql2oException e) {
      response.status(505);
      return dataToJson(e.getMessage());
    }
  };
    public static Route profile = (request, response) -> {
        Map<String, Object> model = new HashMap<>();

        String email = request.session().attribute("currentUser");
        if (email == null) halt(401, "Un-authorized request");
        User u = LoginController.users.get(email);

        if (u.isStudent())
        {
            Student student = studentDao.getUserByUsername(u.getEmail());
            model.put("registers", registrationDao.getRegisteration(student.getId()));
            model.put("student", student);
        }

        return ViewUtil.render(request, model, Template.PROFILE);
    };
  public static Route list = (request, response) -> {
    Map<String, Object> model = new HashMap<>();
    model.put("users", userDao.list());
    return ViewUtil.render(request, model, Template.ALL_USERS);
  };
  public static Route getAdd = (request, response) -> ViewUtil.render(request, null, Template.ADD_USER);
  public static Route postAdd = (request, response) -> {
    Map<String, String> data = body(request);
    User c = new User(data.get("email"), data.get("password"), Integer.parseInt(data.get("role")));
    try {
      response.status(201);
      return jsonData(true, userDao.add(c));
    } catch (Sql2oException e) {
      response.status(505);
      return jsonData(false, e.getMessage());
    }
  };
  public static Route change = (request, response) -> {
    Map<String, String> data = body(request);
    User user = new User(data.get("email"), data.get("password"), Integer.parseInt(data.get("role")));
    user.setId(Integer.parseInt(data.get("id")));
    try {
      userDao.udpate(user);
      response.status(200);
      return dataToJson(true);
    } catch (Sql2oException e) {
      response.status(505);
      return dataToJson(e.getMessage());
    }
  };
  public static Route remove = (request, response) -> {
    Map<String, String> data = body(request);
    userDao.remove(Integer.parseInt(data.get("id")));
    return dataToJson(true);
  };


  public static Filter isAdmin = (req, res) -> {
    String email = req.session().attribute("currentUser");
    if (email == null) halt(401, "Un-authorized request");
    User u = LoginController.users.get(email);
    if (!u.isAdmin()) halt(401, "Un-authorized request");
  };
  public static Filter isFaculty = (req, res) -> {
    String email = req.session().attribute("currentUser");
    if (email == null) halt(401, "Un-authorized request");
    User u = LoginController.users.get(email);
    if (!u.isFaculty()) halt(401, "Un-authorized request");
  };
  public static Filter isStudent = (req, res) -> {
    String email = req.session().attribute("currentUser");
    if (email == null) halt(401, "Un-authorized request");
    User u = LoginController.users.get(email);
    if (!u.isStudent()) halt(401, "Un-authorized request");
  };
}
