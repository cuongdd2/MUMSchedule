package app.student;

import app.entry.Entry;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class StudentDao {

  private final static String BLOCK_BY_DATE = "SELECT * FROM student WHERE entry_id = :entryId";

  private Sql2o sql2o;

  public StudentDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public List<Student> getStudentsByEntry(Entry e) {
    List<Student> blocks;
    try (Connection conn = sql2o.beginTransaction()) {
      blocks = conn.createQuery(BLOCK_BY_DATE)
          .addParameter("entryId", e.getId())
          .executeAndFetch(Student.class);
    }
    return blocks;
  }
}
