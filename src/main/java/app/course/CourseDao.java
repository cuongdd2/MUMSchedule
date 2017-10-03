package app.course;

import app.model.Level;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class CourseDao {
  private final static String INSERT = "INSERT INTO course(name, code, level, description) "
      + "VALUES (:name, :code, :level, :description)";
  private static String SELECT_ALL = "SELECT * FROM course";

  private Sql2o sql2o;

  public CourseDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public void createCourse(String name, String code, Level level, String description, List<String> pre) {
    try (Connection conn = sql2o.beginTransaction()) {
      conn.createQuery(INSERT)
          .addParameter("name", name)
          .addParameter("code", code)
          .addParameter("level", level)
          .addParameter("description", description)
          .addParameter("pre", pre)
          .executeUpdate();
      conn.commit();
    }
  }

  public List<Course> getAllCourse() {
    try (Connection conn = sql2o.open()) {
      List<Course> posts = conn.createQuery(SELECT_ALL)
          .executeAndFetch(Course.class);
      return posts;
    }
  }


}
