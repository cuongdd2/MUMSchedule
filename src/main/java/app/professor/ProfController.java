package app.professor;

import spark.Route;

import static app.Application.profDao;
import static app.util.RequestUtil.*;
import static app.util.JsonUtil.*;

public class ProfController {

  public static Route add = (request, response) -> {
    Professor prof = new Professor(getName(request));
    int id = profDao.createProf(prof);
    return dataToJson(id);
  };
  public static Route change = (request, response) -> {
    Professor prof = new Professor(getName(request));
    int id = profDao.createProf(prof);
    return dataToJson(id);
  };
  public static Route remove = (request, response) -> {
    Professor prof = new Professor(getName(request));
    int id = profDao.createProf(prof);
    return dataToJson(id);
  };

  public static Route list = (request, response) -> {
    return dataToJson(false);
  };
}
