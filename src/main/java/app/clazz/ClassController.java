package app.clazz;

import app.util.Path;
import app.util.ViewUtil;
import org.sql2o.Sql2oException;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Application.*;
import static app.util.JsonUtil.dataToJson;
import static app.util.JsonUtil.jsonData;

public class ClassController {

    public static Route list = (request, response) -> {
        List<Class> sections = classDao.getAll();
        Map<String, Object> model = new HashMap<>();
        model.put("sections",sections);
        model.put("courses",courseDao.getAll());
        model.put("professors",profDao.getAll());
        model.put("blocks",blockDao.getAll());

        return ViewUtil.render(request, model, Path.Template.ALL_SECTIONS);

    };

    public static Route add = (request, response) -> {

        int capacity = Integer.parseInt(request.queryParams("capacity"));
        int course = Integer.parseInt(request.queryParams("section"));
        int professor = Integer.parseInt(request.queryParams("prof"));
        int block = Integer.parseInt(request.queryParams("block"));

        int res = classDao.createWithValues(capacity, course, professor, block);

        if(res !=0)
            return jsonData(true,res);
        else
            return jsonData(false,res);
    };

    public static  Route openCLass = (request , response)->{
        Map<String, String> model = new HashMap<>();
        Class c = classDao.getClazz(Integer.parseInt(request.params(":id")));

        model.put("id", String.valueOf(c.getId()));
        model.put("block", c.getBlock().getName());
        model.put("course", c.getCourse().getName());
        model.put("professor", c.getProfessor().getName());

        response.status(200);
        response.type("application/json");
        return dataToJson(model);
        //return ViewUtil.render(request, model, Path.Template.ALL_COURSES);
    };


    public static Route change = (request, response) -> {

        int clazz = Integer.parseInt(request.queryParams("id"));
        int capacity = Integer.parseInt(request.queryParams("capacity"));
        int course = Integer.parseInt(request.queryParams("section"));
        int professor = Integer.parseInt(request.queryParams("prof"));
        int block = Integer.parseInt(request.queryParams("block"));

        try{
            classDao.updateWithId(clazz, course, professor, block, capacity);
            return dataToJson(1);
        }
        catch (Sql2oException se) {
            return dataToJson(se.getMessage());
        }


    };

    public static Route remove = (request, response) -> {

        int id = Integer.parseInt(request.queryParams("id"));

        try{
            classDao.remove(id);
            return dataToJson(1);
        }
        catch (Sql2oException se) {
            return dataToJson(se.getMessage());
        }

    };
}
