package app.index;

import static app.Application.userDao;

import app.util.Path;
import app.util.ViewUtil;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController {
    public static Route serveIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
//        model.put("users", userDao.getAllUserNames());
//        model.put("book", bookDao.getRandomBook());
        return ViewUtil.render(request, model, Path.Template.INDEX);
    };
}
