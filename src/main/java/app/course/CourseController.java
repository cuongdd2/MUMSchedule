package app.course;

import static app.Application.courseDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.body;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.util.Path;
import app.util.ViewUtil;
import spark.Route;

public class CourseController {

    public static Route addPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.ADD_COURSES);
    };

    public static Route add = (request, response) -> {
    Map<String, String> data = body(request);
    Course c =new Course(data.get("name") , data.get("code") , Level.valueOf(data.get("level")).toString());
    if (data.containsKey("desc")) c.setDesc(data.get("desc"));
    if (data.containsKey("noPre")) c.setPreNo(Integer.parseInt(data.get("noPre")));
    int id = courseDao.create(c);
    response.status(201);
    return dataToJson(request.params().toString());
  };

    public static Route list = (request, response) -> {
    List<Course> courses = courseDao.getAll();
    Map<String, Object> model = new HashMap<>();
     model.put("courses",courses);
    return ViewUtil.render(request, model, Path.Template.ALL_COURSES);

  };


    public static  Route openCourse = (request , response)->{
        Map<String, Object> model = new HashMap<>();
        Course c = courseDao.getCourse(Integer.parseInt(request.queryParams("id")));
        model.put("course" , c);
        response.status(200);
        response.type("application/json");
        return dataToJson(c);
        //return ViewUtil.render(request, model, Path.Template.ALL_COURSES);
    };

  public static Route change = (request, response) -> {
    Map<String, String> data = body(request);
    Course c = new Course(data.get("name"), data.get("code"), Level.valueOf(data.get("level")).toString());
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
