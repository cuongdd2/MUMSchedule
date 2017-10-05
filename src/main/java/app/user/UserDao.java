package app.user;

import app.entry.Entry;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class UserDao {

  private final static String INSERT = "INSERT INTO entry(name, start_date) "
      + "VALUES (:name, :startDate)";


  private Sql2o sql2o;

  public UserDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public int createEntry(Entry entry) {
    try (Connection conn = sql2o.beginTransaction()) {
      return (int) conn.createQuery(INSERT, true)
          .addParameter("name", entry.getName())
          .addParameter("startDate", entry.getStartDate())
          .executeUpdate().getKey(Integer.class);
    }
  }
}
