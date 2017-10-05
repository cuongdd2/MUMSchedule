package app.course;

import app.block.Block;
import app.professor.Professor;
import java.util.List;
import java.util.stream.Collectors;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class CourseDao {
  private final static String INSERT = "INSERT INTO course(name, code, level, desc) "
      + "VALUES (:name, :code, :level, :desc)";
  private final static String SELECT_ALL = "SELECT * FROM course";
  private final static String SELECT_BY_BLOCK = "SELECT * FROM course where block_id = :blockId";
  private final static String SELECT_BY_PROF = "SELECT c.* FROM course c INNER JOIN prof_course p ON c.id = p.course_id WHERE p.prof_id IN :profIds";

  private Sql2o sql2o;

  public CourseDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public void createCourse(String name, String code, Level level, String desc, List<String> pre) {
    try (Connection conn = sql2o.beginTransaction()) {
      conn.createQuery(INSERT)
          .addParameter("name", name)
          .addParameter("code", code)
          .addParameter("level", level)
          .addParameter("desc", desc)
          .addParameter("pre", pre)
          .executeUpdate();
      conn.commit();
    }
  }

  public List<Course> getAllCourse() {
    try (Connection conn = sql2o.open()) {
      List<Course> courses = conn.createQuery(SELECT_ALL).executeAndFetch(Course.class);
      return courses;
    }
  }

  public List<Course> getCoursesByBlock(Block b) {
    try (Connection conn = sql2o.open()) {
      List<Course> courses = conn.createQuery(SELECT_BY_BLOCK)
          .addParameter("blockId", b.getId())
          .executeAndFetch(Course.class);
      return courses;
    }
  }

  public List<Course> getCoursesByProfList(List<Professor> profList) {
    String profIds = profList.stream().map(Professor::getId).collect(Collectors.toList()).toString();
    try (Connection conn = sql2o.open()) {
      List<Course> courses = conn.createQuery(SELECT_BY_PROF)
          .addParameter("profList", profIds)
          .executeAndFetch(Course.class);
      return courses;
    }
  }



}
