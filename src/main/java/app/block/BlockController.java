package app.block;

import app.util.Path;
import app.util.ViewUtil;
import org.sql2o.Sql2oException;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Application.blockDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.JsonUtil.jsonData;
import static app.util.RequestUtil.*;

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
      List<Block> blocks = blockDao.getAll();
      Map<String, Object> model = new HashMap<>();
      model.put("blocks",blocks);
      return ViewUtil.render(req, model, Path.Template.ALL_BLOCKS);
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

  public static  Route openBlock = (request , response)->{
    Map<String, Object> model = new HashMap<>();
    Block block = blockDao.getBlock(Integer.parseInt(request.params(":id")));
    model.put("block" , block);
    response.status(200);
    response.type("application/json");
    return dataToJson(block);
    //return ViewUtil.render(request, model, Path.Template.ALL_COURSES);
  };
}
