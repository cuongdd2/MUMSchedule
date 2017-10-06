package app.entry;

import static app.Application.entryDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.*;

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
    return dataToJson(1);
  };
  public static Route change = (request, response) -> {
    return dataToJson(1);
  };
  public static Route remove = (request, response) -> {
    return dataToJson(1);
  };
}
