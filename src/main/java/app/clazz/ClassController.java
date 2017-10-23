package app.clazz;

import app.util.Path;
import app.util.ViewUtil;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Application.classDao;

public class ClassController {

    public static Route list = (request, response) -> {
        List<Class> sections = classDao.getAll();
        Map<String, Object> model = new HashMap<>();
        model.put("sections",sections);
        return ViewUtil.render(request, model, Path.Template.ALL_SECTIONS);

    };
}
