package app.course;

import app.block.Block;
import app.professor.Professor;
import java.util.List;
import java.util.stream.Collectors;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class CourseDao {
  private final static String INSERT = "INSERT INTO course(name, code, level, `desc`, preNo) "
      + "VALUES(:name, :code, :level, :desc, :preNo)";
  private final static String SELECT_ALL = "SELECT * FROM course";
  private final static String SELECT_BY_BLOCK = "SELECT * FROM course WHERE block_id = :blockId";
  private final static String SELECT_BY_PROF =
      "SELECT c.* FROM course c INNER JOIN prof_course p ON c.id = p.course_id WHERE p.prof_id IN :profIds";
  private final static String UPDATE =
      "UPDATE course SET name = :name, code = :code, level = :level, `desc` = :desc, preNo = :preNo WHERE id = :id";

  private Sql2o sql2o;

  public CourseDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public int create(Course c) throws Sql2oException {
    int id;
    try (Connection conn = sql2o.beginTransaction()) {
      id = conn.createQuery(INSERT).bind(c).executeUpdate().getKey(Integer.class);
      conn.commit();
    }
    return id;
  }

  public List<Course> getAll() {
    try (Connection conn = sql2o.open()) {
      return conn.createQuery(SELECT_ALL).executeAndFetch(Course.class);
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

  public int change(Course c) {
    int result;
    try (Connection conn = sql2o.open()) {
      result = conn.createQuery(INSERT)
          .addParameter("name", c.getName())
          .addParameter("code", c.getCode())
          .addParameter("level", c.getLevel())
          .addParameter("desc", c.getDesc())
          .addParameter("preNo", c.getPreNo())
          .executeUpdate().getResult();
      conn.commit();
    }
    return result;
  }



}
