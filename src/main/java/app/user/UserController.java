package app.user;

import static app.Application.entryDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.getName;
import static app.util.RequestUtil.getStartDate;

import app.entry.Entry;
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
}
