package app.admin;

import static app.Application.profDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.body;
import static app.util.RequestUtil.getName;

import app.professor.Professor;
import spark.Route;

import java.util.Map;

public class AdminController {

  public static Route add = (request, response) -> {
    Map<String, String> data = body(request);
    Professor prof = new Professor(data.get("name"), data.get("title"), data.get("phone"), data.get("about"));
    int id = profDao.createProf(prof);
    return dataToJson(id);
  };



}
