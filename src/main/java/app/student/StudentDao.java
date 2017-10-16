package app.student;

import app.entry.Entry;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class StudentDao {

  private final static String STUDENT_BY_MAIL = "SELECT * FROM student";
  private final static String BLOCK_BY_DATE = "SELECT * FROM student WHERE entry_id = :entryId";
  private final static String STUDENT_BY_ID = "SELECT entry_id FROM student WHERE id = :id";

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

  public Student getUserByUsername(String username) {

      List<Student> student;
      try (Connection conn = sql2o.beginTransaction()) {
          student = conn.createQuery(STUDENT_BY_MAIL).executeAndFetch(Student.class);
      }
      return student.size() !=0 ? student.get(0) : null;
  }

  public int getStudentById(int id) {
      int entry;

      try(Connection conn = sql2o.beginTransaction()) {
          entry = conn.createQuery(STUDENT_BY_ID)
                  .addParameter("id", id).throwOnMappingFailure(false)
                  .executeAndFetchFirst(Integer.class);

      }
      return entry;
  }
}
