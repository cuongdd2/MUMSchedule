package app.course;

import app.util.Path;
import app.util.ViewUtil;
import org.sql2o.Sql2oException;
import spark.Route;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Application.courseDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.JsonUtil.jsonData;
import static app.util.RequestUtil.body;

public class CourseController {

    public static Route addPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.ADD_COURSES);
    };

    public static Route add = (request, response) -> {
    Map<String, String> data = body(request);
    Course c =new Course(data.get("name") , data.get("code") , data.get("level"));
    if (data.containsKey("desc")) c.setDesc(data.get("desc"));
    if (data.containsKey("noPre")) c.setPreNo(Integer.parseInt(data.get("noPre")));
    int id;// = courseDao.create(c);
        try {
            id = courseDao.create(c);
            if(!data.get("preq").isEmpty()){
                List<String> preq = Arrays.asList(data.get("preq").split("\\s*,\\s*"));
                for (String pre_id:preq
                     ) {
                    courseDao.insertPre(id,Integer.parseInt(pre_id));
                    courseDao.updatepreno(Integer.parseInt(pre_id));
                }
            }
            response.status(201);
            return jsonData(true, id);
        } catch (Sql2oException e) {
            response.status(505);
            return jsonData(false, e.getMessage());
        }
  };

    public static Route list = (request, response) -> {
    List<Course> courses = courseDao.getAll();
        for (Course c: courses
             ) {
            c.setPrerequisite(courseDao.getPre(c.getId()));
        }
    Map<String, Object> model = new HashMap<>();
     model.put("courses",courses);
    return ViewUtil.render(request, model, Path.Template.ALL_COURSES);

  };


    public static  Route openCourse = (request , response)->{
        Map<String, Object> model = new HashMap<>();
        Course c = courseDao.getCourse(Integer.parseInt(request.params(":id")));
        model.put("course" , c);
        c.setPrerequisite(courseDao.getPre(Integer.parseInt(request.params(":id"))));
        response.status(200);
        response.type("application/json");
        return dataToJson(c);
        //return ViewUtil.render(request, model, Path.Template.ALL_COURSES);
    };

  public static Route change = (request, response) -> {
    Map<String, String> data = body(request);
    Course c = new Course(data.get("name"), data.get("code"), data.get("level"));
    if (data.containsKey("id")) c.setId(Integer.parseInt(data.get("id")));
    if (data.containsKey("desc")) c.setDesc(data.get("desc"));
    if (data.containsKey("noPre")) c.setPreNo(Integer.parseInt(data.get("noPre")));
     courseDao.change(c);
      List<String> preq = Arrays.asList(data.get("preq").split("\\s*,\\s*"));
      if(preq.size() > 0 ){
          courseDao.deletePre(c);
          for (String pre_id:preq
                  ) {
              courseDao.insertPre(c.getId(),Integer.parseInt(pre_id));
              courseDao.updatepreno(Integer.parseInt(pre_id));
          }
      }
      if(Integer.parseInt(data.get("noPre")) == 0){
          courseDao.deletePre(c);
      }
    return dataToJson(1);
  };

  public static Route remove = (request, response) -> {
      Map<String, String> data = body(request);
      Course c = courseDao.getCourse(Integer.parseInt(data.get("id")));
      int id;
      try {
          id = courseDao.delete(c);
          response.status(201);
          return jsonData(true, id);
      } catch (Sql2oException e) {
          response.status(505);
          return jsonData(false, e.getMessage());
      }
  };

}