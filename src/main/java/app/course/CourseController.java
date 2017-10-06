package app.course;

import static app.Application.courseDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.body;

import java.util.List;
import java.util.Map;
import spark.Route;

public class CourseController {

  public static Route add = (request, response) -> {
    Map<String, String> data = body(request);
    Course c = new Course(data.get("name"), data.get("code"), Level.valueOf(data.get("level")));
    int id = courseDao.create(c);
    return dataToJson(id);
  };
  public static Route list = (request, response) -> {
    List<Course> courses = courseDao.getAll();
    return dataToJson(courses);
  };
  public static Route change = (request, response) -> {
    return dataToJson(1);
  };
  public static Route remove = (request, response) -> {
    return dataToJson(1);
  };
}
