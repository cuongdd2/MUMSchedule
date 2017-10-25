package app;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

import app.block.BlockController;
import app.block.BlockDao;
import app.clazz.ClassController;
import app.clazz.ClassDao;
import app.course.CourseController;
import app.course.CourseDao;
import app.entry.EntryController;
import app.entry.EntryDao;
import app.index.IndexController;
import app.login.LoginController;
import app.professor.ProfController;
import app.professor.ProfDao;
import app.profile.ProfileController;
import app.register.RegistrationDao;
import app.schedule.ScheduleController;
import app.student.StudentController;
import app.student.StudentDao;
import app.user.UserController;
import app.user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;

public class Application {

  private static Logger log = LoggerFactory.getLogger("cs425");
  public static ProfDao profDao;
  public static CourseDao courseDao;
  public static BlockDao blockDao;
  public static StudentDao studentDao;
  public static EntryDao entryDao;
  public static UserDao userDao;
  public static ClassDao classDao;
  public static RegistrationDao registrationDao;

  public static void main(String[] args) {

    exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
    port(8080);

    Sql2o sql2o = new Sql2o("jdbc:mysql://104.207.139.224:3306/cs425?relaxAutoCommit=true", "cs425", "mum");
    blockDao = new BlockDao(sql2o);
    courseDao = new CourseDao(sql2o);
    entryDao = new EntryDao(sql2o);
    profDao = new ProfDao(sql2o);
    studentDao = new StudentDao(sql2o);
    userDao = new UserDao(sql2o);
    classDao = new ClassDao(sql2o);
    registrationDao = new RegistrationDao(sql2o);

    staticFiles.location("/public");
    staticFiles.expireTime(600L);
//    before("*",                  Filters.addTrailingSlashes);
//    before("*",                  Filters.handleLocaleChange);

    get("/", IndexController.serveIndexPage);
    get("/login/", LoginController.loginPage);
    post("/login/", LoginController.login);
    post("/logout/", LoginController.logout);

    get("/profile", UserController.profile);

    path("", () -> {
      before("/*", (req, res) -> {
        System.out.println("Received api call" + req.url());
      });

      path("/block", () -> {
//        before("/*", UserController.isAdmin);
        get("/list", BlockController.list);
        post("/add", BlockController.add);
        get("/:id",BlockController.openBlock);
        put("/change", BlockController.change);
        delete("/remove", BlockController.remove);
      });
      path("/course", () -> {
//        before("/*", UserController.isAdmin);
        get("/", CourseController.list);
        get("/add",CourseController.addPage);
        get("/:id",CourseController.openCourse);
        post("/add", CourseController.add);
        put("/change", CourseController.change);
        delete("/remove", CourseController.remove);
      });
      path("/section", () -> {
        get("/", ClassController.list);
        post("/add", ClassController.add);
        get("/:id", ClassController.openCLass);
        post("/remove", ClassController.remove);
        put("/change", ClassController.change);

      });
      path("/entry", () -> {
//        before("/*", LoginController.ensureUserIsLoggedIn);
        get("/", EntryController.list);
        post("/add", EntryController.add);
        put("/change", EntryController.change);
        get("/:id",EntryController.opeEntry);
        delete("/remove", EntryController.remove);
      });
      path("/professor", () -> {
      //  before("/*", LoginController.ensureUserIsLoggedIn);
        get("/list", ProfController.list);
        post("/add", ProfController.add);
        get("/:id",ProfController.openProfessor);
        put("/change", ProfController.change);
        delete("/remove", ProfController.remove);
      });
      path("/student", () -> {
        before("/*", LoginController.ensureUserIsLoggedIn);
        get("/schedule", StudentController.schedulePage);
        get("/list", StudentController.list);
        post("/add", StudentController.add);
        put("/change", StudentController.change);
        delete("/remove", StudentController.remove);
      });
      path("/user", () -> {
//        before("/*", UserController.isAdmin);
        get("/", UserController.list);
        get("/:id", UserController.get);
        get("/add", UserController.getAdd);
        post("/add", UserController.postAdd);
        put("/change", UserController.change);
        delete("/remove", UserController.remove);
      });

      path("/schedule", () -> {
        before("/*", UserController.isAdmin);
        get("/", ScheduleController.list);
        delete("/reset", ScheduleController.reset);
        post("/generate", ScheduleController.generate);
      });
      path("/profile", () -> {
          get("/profile", ProfileController.profilePage);

      });
    });
  }
}
