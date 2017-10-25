package app.entry;

import static app.Application.blockDao;
import static app.Application.courseDao;
import static app.Application.entryDao;
import static app.util.JsonUtil.dataToJson;
import static app.util.JsonUtil.jsonData;
import static app.util.RequestUtil.*;

import app.block.Block;
import app.course.Course;
import app.course.Level;
import app.util.Path;
import app.util.Path.Template;
import app.util.ViewUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sql2o.Sql2oException;
import spark.Route;

public class EntryController {

    public static Route addPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.ALL_ENTRIES);
    };
    public static Route add = (request, response) -> {
        Map<String, String> data = body(request);
        Entry entry = new Entry(data.get("name"), parseDate(data.get("startDate")));
        int id = entryDao.create(entry);
        response.status(201);
        response.type("application/json");
        return jsonData(true,id);
    };
    public static Route list = (request, response) -> {
        List<Entry> entries = entryDao.list();
        Map<String, Object> model = new HashMap<>();
        model.put("entries", entries);
        return ViewUtil.render(request, model, Template.ALL_ENTRIES);
    };
    public static Route change = (req, res) -> {
        try {
            Entry entry  = new Entry(getName(req), getStartDate(req));
            entry.setId(getId(req));
            int id = entryDao.update(entry);
            res.status(200);
            return jsonData(true, id);
        } catch (Sql2oException e) {
            res.status(400);
            return jsonData(false, e.getMessage());
        }
    };

    public static Route remove = (req, res) -> {
        try {
            Entry e = entryDao.getEntryById(getId(req));
            entryDao.delete(e);
            res.status(200);
            return jsonData(true, true);
        } catch (Sql2oException e) {
            res.status(500);
            return jsonData(false, e.getMessage());
        }
    };

    public static Route opeEntry = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        Entry e= entryDao.getEntryById(Integer.parseInt(request.params(":id")));
        model.put("entry", e);
        response.status(200);
        response.type("application/json");
        return dataToJson(e);
        //return ViewUtil.render(request, model, Path.Template.ALL_COURSES);
    };
}
