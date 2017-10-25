package app.entry;

import app.block.Block;
import app.util.Dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import java.util.List;
import org.sql2o.Connection;
import org.sql2o.ResultSetHandler;
import org.sql2o.Sql2o;

public class EntryDao implements Dao {

  private static String INSERT = "INSERT INTO entry(name, start_date) VALUES (:name, :startDate)";
  private static String UPDATE = "UPDATE entry SET name = :name, start_date = :startDate";

  private final static String ENTRY_BY_ID = "SELECT * FROM entry WHERE id = :id";
  private final static String DELETE_BY_ID = "DELETE FROM entry WHERE id= :id";
  private Sql2o sql2o;

  public EntryDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public int create(Entry entry) {
    try (Connection conn = sql2o.beginTransaction()) {
      int id = conn.createQuery(INSERT, true)
          .addParameter("name", entry.getName())
          .addParameter("startDate", entry.getStartDate())
          .executeUpdate().getKey(Integer.class);
      conn.commit();
      return id;
    }
  }

  public int update(Entry entry){
    int result;
    try(Connection conn=sql2o.open()){
      result=conn.createQuery(UPDATE)
              .addParameter("name", entry.getName())
              .addParameter("start_date", entry.getStartDate())
              .executeUpdate().getResult();
      conn.commit();
    }
    return result;
  }

  public int delete(Entry entry){
    int result;
    try(Connection conn=sql2o.open()) {
      result = conn.createQuery(DELETE_BY_ID)
              .addParameter("id", entry.getId())
              .executeUpdate().getResult();
      conn.commit();
    }
    return result;
    }


  public Entry getEntryById(int id) {
      Entry entry;
      try(Connection conn = sql2o.beginTransaction()) {
          entry = conn.createQuery(ENTRY_BY_ID)
                  .addParameter("id", id)
                  .executeAndFetchFirst(new EntryDataTransfer());
      }
      return entry;
  }

  public List<Entry> list() {
    try (Connection conn = sql2o.beginTransaction()) {
      return conn.createQuery("select * from entry order by start_date").executeAndFetch(new EntryDataTransfer());
    }
  }
}

class EntryDataTransfer implements ResultSetHandler<Entry> {

  @Override
  public Entry handle(ResultSet rs) throws SQLException {
    Entry entry = new Entry(rs.getString(2), rs.getDate(3).toLocalDate());
    entry.setId(rs.getInt(1));
    return entry;
  }
}