package app.course;

import static app.Application.blockDao;
import static app.Application.courseDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.JsonUtil.jsonData;
import static app.util.RequestUtil.body;
import static app.util.RequestUtil.removeSessionAttrLoggedOut;
import static app.util.RequestUtil.removeSessionAttrLoginRedirect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.professor.ProfDao;
import app.professor.Professor;
import app.util.Path;
import app.util.ViewUtil;
import jdk.nashorn.internal.ir.Block;
import org.sql2o.Sql2oException;
import spark.Route;

public class CourseController {

    public static Route addPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.ADD_COURSES);
    };

    public static Route add = (request, response) -> {
        Map<String, String> data = body(request);
        Course c = new Course(data.get("name"), data.get("code"), Level.valueOf(data.get("level")).toString());
        if (data.containsKey("desc")) c.setDesc(data.get("desc"));
        if (data.containsKey("noPre")) c.setPreNo(Integer.parseInt(data.get("noPre")));
        int id ;//= courseDao.create(c);
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
        List<app.block.Block> blocks = blockDao.getAll();
        Map<String, Object> model = new HashMap<>();
        model.put("courses", courses);
        model.put("blocks",blocks);
        return ViewUtil.render(request, model, Path.Template.ALL_COURSES);

    };


    public static Route openCourse = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        Course c = courseDao.getcourse(Integer.parseInt(request.queryParams("id")));
        model.put("course", c);
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
        Map<String, String> data = body(request);
        Course c = courseDao.getcourse(Integer.parseInt(data.get("id")));
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
