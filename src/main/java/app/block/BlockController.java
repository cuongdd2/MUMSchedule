package app.block;

import static app.Application.blockDao;
import static app.Application.entryDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.body;
import static app.util.RequestUtil.getName;
import static app.util.RequestUtil.getStartDate;
import static app.util.RequestUtil.parseDate;

import app.entry.Entry;
import java.util.Map;
import spark.Route;

public class BlockController {

  public static Route add = (request, response) -> {
    Map<String, String> data = body(request);
    Block block = new Block(data.get("name"), parseDate(data.get("startDate")), parseDate(data.get("endDate")));
    int id = blockDao.create(block);
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
