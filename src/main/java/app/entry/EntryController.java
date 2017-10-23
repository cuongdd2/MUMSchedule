package app.entry;

import static app.Application.courseDao;
import static app.Application.entryDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.*;

import app.course.Course;
import app.util.Path;
import app.util.Path.Template;
import app.util.ViewUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Route;

public class EntryController {

  public static Route add = (request, response) -> {
    Map<String, String> data = body(request);
    Entry entry = new Entry(data.get("name"), parseDate(data.get("startDate")));
    int id = entryDao.create(entry);
    return dataToJson(id);
  };
  public static Route list = (request, response) -> {
    List<Entry> entries = entryDao.list();
    Map<String, Object> model = new HashMap<>();
    model.put("entries", entries);
    return ViewUtil.render(request, model, Template.ALL_ENTRIES);
  };
  public static Route change = (request, response) -> {
    return dataToJson(1);
  };
  public static Route remove = (request, response) -> {
    return dataToJson(1);
  };
}
