package app.entry;

import java.sql.Timestamp;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class EntryDao {

  private static String INSERT = "INSERT INTO entry(name, start_date) VALUES (:name, :startDate)";


  private Sql2o sql2o;

  public EntryDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public int createEntry(Entry entry) {
    try (Connection conn = sql2o.beginTransaction()) {
      int id = conn.createQuery(INSERT, true)
          .addParameter("name", entry.getName())
          .addParameter("startDate", entry.getTimestamp())
          .executeUpdate().getKey(Integer.class);
      conn.commit();
      return id;
    }
  }
}
