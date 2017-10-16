package app.block;

import static app.Application.blockDao;
import static app.Application.courseDao;
import static app.Application.entryDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.JsonUtil.jsonData;
import static app.util.RequestUtil.body;
import static app.util.RequestUtil.getEndDate;
import static app.util.RequestUtil.getId;
import static app.util.RequestUtil.getName;
import static app.util.RequestUtil.getStartDate;
import static app.util.RequestUtil.parseDate;

import app.entry.Entry;
import java.util.Map;
import org.sql2o.Sql2oException;
import spark.Route;

public class BlockController {

  public static Route add = (req, res) -> {
    try {
      Block block = new Block(getName(req), getStartDate(req), getEndDate(req));
      int id = blockDao.create(block);
      res.status(201);
      return jsonData(true, id);
    } catch (Sql2oException e) {
      res.status(500);
      return jsonData(false, e.getMessage());
    }
  };
  public static Route list = (req, res) -> {
    try {
      res.status(200);
      return jsonData(true, blockDao.getAll());
    } catch (Sql2oException e) {
      res.status(400);
      return jsonData(false, e.getMessage());
    }
  };
  public static Route change = (req, res) -> {
    try {
      Block block = new Block(getName(req), getStartDate(req), getEndDate(req));
      block.setId(getId(req));
      int id = blockDao.update(block);
      res.status(200);
      return jsonData(true, id);
    } catch (Sql2oException e) {
      res.status(400);
      return jsonData(false, e.getMessage());
    }
  };
  public static Route remove = (req, res) -> {
    try {
      blockDao.remove(getId(req));
      res.status(200);
      return jsonData(true, true);
    } catch (Sql2oException e) {
      res.status(500);
      return jsonData(false, e.getMessage());
    }
  };
}
