package app.student;

import static app.Application.entryDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.getName;
import static app.util.RequestUtil.getStartDate;

import app.entry.Entry;
import spark.Route;

public class StudentController {

  public static Route add = (request, response) -> {
    Entry entry = new Entry(getName(request), getStartDate(request));
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
