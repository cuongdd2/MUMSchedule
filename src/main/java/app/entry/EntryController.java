package app.entry;

import static app.Application.*;
import static app.util.JsonUtil.*;
import static app.util.RequestUtil.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import spark.Route;

public class EntryController {

  public static Route add = (request, response) -> {
    Map<String, String> data = body(request);
    DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
    Entry entry = new Entry(data.get("name"), df.parse(data.get("startDate")));
    int id = entryDao.createEntry(entry);
    return dataToJson(id);
  };
  public static Route list = (request, response) -> {
    Entry entry = new Entry(getName(request), getStartDate(request));
    int id = entryDao.createEntry(entry);
    return dataToJson(id);
  };
  public static Route change = (request, response) -> {
    Entry entry = new Entry(getName(request), getStartDate(request));
    int id = entryDao.createEntry(entry);
    return dataToJson(id);
  };
  public static Route remove = (request, response) -> {
    Entry entry = new Entry(getName(request), getStartDate(request));
    int id = entryDao.createEntry(entry);
    return dataToJson(id);
  };
}
