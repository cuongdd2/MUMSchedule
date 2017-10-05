package app.admin;

import static app.Application.profDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.getName;

import app.professor.Professor;
import spark.Route;

public class AdminController {

  public static Route add = (request, response) -> {
    Professor prof = new Professor(getName(request));
    int id = profDao.createProf(prof);
    return dataToJson(id);
  };



}
