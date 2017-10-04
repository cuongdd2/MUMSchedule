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

  public static Route update = (request, response) -> {
    //TODO update professor info


    return dataToJson(false);
  };
}
