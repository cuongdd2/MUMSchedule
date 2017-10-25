package app.professor;

import app.course.Course;
import app.util.Path;
import app.util.ViewUtil;
import org.sql2o.Sql2oException;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Application.courseDao;
import static app.Application.profDao;
import static app.util.RequestUtil.*;
import static app.util.JsonUtil.*;

public class ProfController {

  public static Route add = (request, response) -> {
    try {
      Map<String, String> data = body(request);
      Professor prof = new Professor(data.get("name"), data.get("title"), data.get("phone"), data.get("about"));
      int id = profDao.createProf(prof);
      response.status(201);
      return jsonData(true, id);
    }catch (Sql2oException e) {
      response.status(500);
      return jsonData(false, e.getMessage());
    }
  };
  public static Route change = (request, response) -> {
    Map<String, String> data = body(request);
    Professor prof = new Professor( data.get("name") , data.get("title") , data.get("phone"), data.get("about")  );
    prof.setId(Integer.parseInt(data.get("id")));
    int id = profDao.update(prof);
    return dataToJson(id);
  };
  public static Route remove = (request, response) -> {
    try {
      profDao.remove(getId(request));
      response.status(200);
      return jsonData(true, true);
    } catch (Sql2oException e) {
      response.status(500);
      return jsonData(false, e.getMessage());
    }
  };

  public static Route list = (request, response) -> {
    List<Professor> professors = profDao.getAll();
    Map<String, Object> model = new HashMap<>();
    model.put("professors",professors);
    return ViewUtil.render(request, model, Path.Template.ALL_PROFESSORS);
  };


  public static  Route openProfessor = (request , response)->{
    Map<String, Object> model = new HashMap<>();
    Professor p = profDao.getProfById(Integer.parseInt(request.params(":id")));
    model.put("professor" , p);
    response.status(200);
    response.type("application/json");
    return dataToJson(p);
    //return ViewUtil.render(request, model, Path.Template.ALL_COURSES);
  };

}
