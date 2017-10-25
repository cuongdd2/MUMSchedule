package app.student;

import app.entry.Entry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.ResultSetHandler;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import static app.Application.entryDao;

public class StudentDao {

    private Sql2o sql2o;
    private final static String STUDENT_BY_MAIL = "SELECT * FROM student WHERE email = :Email";
    private final static String BLOCK_BY_DATE = "SELECT * FROM student WHERE entry_id = :entryId";
    private final static String STUDENT_BY_ID = "SELECT entry_id FROM student WHERE id = :id";
    private final static String STUDENT_ID = "SELECT * FROM student WHERE id = :id";

    private final static String INSERT = "INSERT INTO student(name, email, dob, entry_id, track) VALUES (:name, :email, :dob, :entry_id, :track)";
    private final static String UPDATE = "UPDATE student SET name = :name, email = :email, dob = :dob, entry_id = :entry_id, track = :track WHERE id = :id";
    private final static String DELETE = "delete from student where id = :id";

    public StudentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public int createStudent(Student student) {
        int id;
        try (Connection conn = sql2o.beginTransaction()) {
            id = conn.createQuery(INSERT, true)
                    .bind(student)
                    .executeUpdate().getKey(Integer.class);
            conn.commit();
        }
        return id;
    }

    public int change(Student c) {
        int result;
        try (Connection conn = sql2o.open()) {
            result = conn.createQuery(UPDATE)
                    .addParameter("id", c.getId())
                    .addParameter("name", c.getName())
                    .addParameter("email", c.getEmail())
                    .addParameter("dob", c.getDob())
                    .addParameter("entry_id", c.getEntry())
                    .addParameter("track", c.getTrack())
                    .executeUpdate().getResult();
            //  conn.commit();
        }
        return result;
    }

    public int delete(Student c) throws Sql2oException {
        int result;
        try (Connection conn = sql2o.open()) {
            result = conn.createQuery(DELETE)
                    .addParameter("id", c.getId())
                    .executeUpdate().getResult();
            conn.commit();
        }
        return result;
    }

    public List<Student> getStudentsByEntry(Entry e) {
        List<Student> blocks;
        try (Connection conn = sql2o.beginTransaction()) {
            blocks = conn.createQuery(STUDENT_BY_ENTRY)
                    .addParameter("entryId", e.getId())
                    .executeAndFetch(new StudentTransfer(e));
        }
        return blocks;
    }

  public Student getUserByUsername(String email) {

    Student student;
    try (Connection conn = sql2o.beginTransaction()) {
      student = conn.createQuery(STUDENT_BY_MAIL)
          .addParameter("Email", email)
          .throwOnMappingFailure(false)
          .executeAndFetchFirst(Student.class);
    }
    return student;
  }

  public int getStudentById(int id) {
    int entry;

    try (Connection conn = sql2o.beginTransaction()) {
      entry = conn.createQuery(STUDENT_BY_ID)
          .addParameter("id", id).throwOnMappingFailure(false)
          .executeAndFetchFirst(Integer.class);
    }
    return entry;
  }

  public int getStudentEntry(int id) {
    int entry;

    try (Connection conn = sql2o.beginTransaction()) {
      entry = conn.createQuery(STUDENT_BY_ID)
          .addParameter("id", id).throwOnMappingFailure(false)
          .executeAndFetchFirst(Integer.class);
    }
    return entry;
  }

}

class StudentTransfer implements ResultSetHandler<Student> {
  private Entry e;
  public StudentTransfer(Entry e) {
    this.e = e;
  }

  @Override
  public Student handle(ResultSet rs) throws SQLException {
    LocalDate dob = rs.getDate("dob") != null ? rs.getDate("dob").toLocalDate() : null;
    Student s = new Student(rs.getString("name"), dob, e);
    s.setId(rs.getInt("id"));
    s.setEmail(rs.getString("email"));
    s.setTrack(rs.getString("track"));
    return s;
  }
}
