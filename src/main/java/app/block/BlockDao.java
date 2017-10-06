package app.block;

import app.entry.Entry;
import app.util.Dao;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class BlockDao implements Dao {

  private final static String INSERT = "insert into block(name, start_date, end_date) values(:name, :startDate, :endDate)";
  private final static String BLOCK_BY_DATE = "SELECT * FROM block WHERE start_date > :startDate";

  private Sql2o sql2o;

  public BlockDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }


  public int create(Block b) {
    int id;
    try (Connection conn = sql2o.beginTransaction()) {
      id = conn.createQuery(INSERT)
          .addParameter("name", b.getName())
          .addParameter("startDate", toTimestamp(b.getStartDate()))
          .addParameter("endDate", toTimestamp(b.getEndDate()))
          .executeUpdate().getKey(Integer.class);
      conn.commit();
    }
    return id;
  }

  public List<Block> getBlocksByEntry(Entry e) {
    List<Block> blocks;
    try (Connection conn = sql2o.beginTransaction()) {
      blocks = conn.createQuery(BLOCK_BY_DATE)
          .addParameter("startDate", e.getStartDate())
          .executeAndFetch(Block.class);
    }
    return blocks;
  }

}
