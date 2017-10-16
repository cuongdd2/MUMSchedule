package app;

import static app.util.JsonUtil.dataToJson;
import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import app.block.BlockController;
import app.block.BlockDao;
import app.course.CourseController;
import app.course.CourseDao;
import app.entry.EntryController;
import app.entry.EntryDao;
import app.professor.ProfController;
import app.professor.ProfDao;
import app.student.StudentController;
import app.student.StudentDao;
import app.user.UserController;
import app.user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;
import spark.Route;

public class Application {

  private static Logger log = LoggerFactory.getLogger("cs425");
  public static ProfDao profDao;
  public static CourseDao courseDao;
  public static BlockDao blockDao;
  public static StudentDao studentDao;
  public static EntryDao entryDao;
  public static UserDao userDao;
  public static void main(String[] args) {

    exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
    staticFiles.location("/public");
    port(8080);

    Sql2o sql2o = new Sql2o("jdbc:mysql://104.207.139.224:3306/cs425", "cs425", "mum");
    blockDao = new BlockDao(sql2o);
    courseDao = new CourseDao(sql2o);
    entryDao = new EntryDao(sql2o);
    profDao = new ProfDao(sql2o);
    studentDao = new StudentDao(sql2o);
    userDao = new UserDao(sql2o);

    path("/api", () -> {
      before("/*", (q, a) -> System.out.println("Received api call" + q.url()));

      path("/block", () -> {
        get("/list", BlockController.list);
        post("/add", BlockController.add);
        put("/change", BlockController.change);
        delete("/remove", BlockController.remove);
      });
      path("/course", () -> {
        get("/list", CourseController.list);
        get("/add",CourseController.addPage);
        get("/course",CourseController.openCourse);
        post("/add", CourseController.add);
        put("/change", CourseController.change);
        delete("/remove", CourseController.remove);
      });
      path("/entry", () -> {
        get("/list", EntryController.list);
        post("/add", EntryController.add);
        put("/change", EntryController.change);
        delete("/remove", EntryController.remove);
      });
      path("/prof", () -> {
        get("/remove", ProfController.list);
        post("/add", ProfController.add);
        put("/change", ProfController.change);
        delete("/remove", ProfController.remove);
      });
      path("/student", () -> {
        get("/login", StudentController.loginPage);
        post("/login", StudentController.loginPost);


        get("/list", StudentController.list);
        post("/add", StudentController.add);
        put("/change", StudentController.change);
        delete("/remove", StudentController.remove);
      });
      path("/user", () -> {
        get("/remove", UserController.list);
        post("/add", UserController.add);
        put("/change", UserController.change);
        delete("/remove", UserController.remove);
      });

    });
   enableDebugScreen();
  }
}
