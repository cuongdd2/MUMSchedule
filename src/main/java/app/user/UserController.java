package app.user;

import static app.util.JsonUtil.dataToJson;
import static spark.Spark.halt;

import app.login.LoginController;
import spark.Filter;
import spark.Route;

public class UserController {

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


  public static Filter isAdmin = (req, res) -> {
    if (req.attribute("currentUser") == null) halt(401, "Un-authorized request");
    User u = LoginController.users.get(req.attribute("currentUser"));
    if (!u.isAdmin()) halt(401, "Un-authorized request");
  };
  public static Filter isFaculty = (req, res) -> {
    if (req.attribute("currentUser") == null) halt(401, "Un-authorized request");
    User u = LoginController.users.get(req.attribute("currentUser"));
    if (!u.isFaculty()) halt(401, "Un-authorized request");
  };
  public static Filter isStudent = (req, res) -> {
    if (req.attribute("currentUser") == null) halt(401, "Un-authorized request");
    User u = LoginController.users.get(req.attribute("currentUser"));
    if (!u.isStudent()) halt(401, "Un-authorized request");
  };
}
