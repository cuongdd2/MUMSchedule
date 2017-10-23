package app.entry;

import app.util.Dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class EntryDao implements Dao {

  private static String INSERT = "INSERT INTO entry(name, start_date) VALUES (:name, :startDate)";
  private final static String ENTRY_BY_ID = "SELECT * FROM entry WHERE id = :id";

  private Sql2o sql2o;

  public EntryDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public int create(Entry entry) {
    try (Connection conn = sql2o.beginTransaction()) {
      int id = conn.createQuery(INSERT, true)
          .addParameter("name", entry.getName())
          .addParameter("startDate", entry.getStart_date())
          .executeUpdate().getKey(Integer.class);
      conn.commit();
      return id;
    }
  }

  public Entry getEntryById(int id) {
      Entry entry;

      try(Connection conn = sql2o.beginTransaction()) {
          entry = conn.createQuery(ENTRY_BY_ID)
                  .addParameter("id", id).throwOnMappingFailure(false)
                  .executeAndFetchFirst(Entry.class);
      }
      return entry;
  }
}
