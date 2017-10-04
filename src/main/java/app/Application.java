package app;

import static spark.Spark.*;

import app.course.CourseDao;
import app.professor.ProfController;
import app.professor.ProfDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;

public class Application {

  private static Logger log = LoggerFactory.getLogger("cs425");
  public static ProfDao profDao;
  public static CourseDao courseDao;

  public static void main(String[] args) {
    exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
    staticFiles.location("/public");
    port(9860);

    Sql2o sql2o = new Sql2o("jdbc:mysql://104.207.139.224:3306/cs425", "cs425", "mum");
    profDao = new ProfDao(sql2o);
    courseDao = new CourseDao(sql2o);

    path("/api", () -> {
      before("/*", (q, a) -> log.info("Received api call" + q.url()));
      path("/student", () -> {
        post("/add",        ProfController.add);
        put("/change",     (request, response) -> {return response;});
        delete("/remove",  (request, response) -> {return response;});
      });
      path("/prof", () -> {
        post("/add",       (request, response) -> {return response;});
        put("/change",     (request, response) -> {return response;});
        delete("/remove",  (request, response) -> {return response;});
      });
    });
    get("/hello", (req, res) -> "Hello World");
  }
}
