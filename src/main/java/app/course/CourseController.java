package app.course;

import static app.Application.courseDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.JsonUtil.jsonData;
import static app.util.RequestUtil.body;

import java.util.List;
import java.util.Map;
import org.sql2o.Sql2oException;
import spark.Route;

public class CourseController {

  public static Route add = (request, response) -> {
    Map<String, String> data = body(request);
    Course c = new Course(data.get("name"), data.get("code"), data.get("level"));
    if (data.containsKey("desc")) c.setDesc(data.get("desc"));
    if (data.containsKey("noPre")) c.setPreNo(Integer.parseInt(data.get("noPre")));
    int id;
    try {
      id = courseDao.create(c);
      response.status(201);
      return jsonData(true, id);
    } catch (Sql2oException e) {
      response.status(505);
      return jsonData(false, e.getMessage());
    }
  };
  public static Route list = (request, response) -> {
    List<Course> courses = courseDao.getAll();
    response.status(200);
    response.type("application/json");
    return jsonData(true, courses);
  };
  public static Route change = (request, response) -> {
    Map<String, String> data = body(request);
    Course c = new Course(data.get("name"), data.get("code"), data.get("level"));
    if (data.containsKey("id")) c.setId(Integer.parseInt(data.get("id")));
    if (data.containsKey("desc")) c.setDesc(data.get("desc"));
    if (data.containsKey("noPre")) c.setPreNo(Integer.parseInt(data.get("noPre")));
    courseDao.change(c);
    return dataToJson(1);
  };
  public static Route remove = (request, response) -> {
    return dataToJson(1);
  };

}