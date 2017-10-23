package app.block;

import app.entry.Entry;
import app.util.Dao;

import java.sql.Date;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.ResultSetHandler;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class BlockDao implements Dao {

  private final static String INSERT = "INSERT INTO block(name, start_date, end_date) VALUES(:name, :startDate, :endDate)";
  private final static String LIST_ALL = "SELECT id, name, start_date startDate, end_date endDate FROM block";
  private final static String DELETE = "DELETE FROM block WHERE id = :id";
  private final static String UPDATE = "UPDATE block SET name = :name, start_date = :startDate, end_date = :endDate WHERE id = :id";
  private final static String BLOCK_BY_DATE = "SELECT * FROM block WHERE start_date > :startDate";
  private final static String BLOCK_BY_ENTRY = "SELECT * FROM block WHERE entry_id = :entryId";

  private Sql2o sql2o;

  public BlockDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }


  public int create(Block b) {
    int id;
    try (Connection conn = sql2o.beginTransaction()) {
      id = conn.createQuery(INSERT)
          .addParameter("name", b.getName())
          .addParameter("startDate", b.getStartDate())
          .addParameter("endDate", b.getEndDate())
          .executeUpdate().getKey(Integer.class);
      conn.commit();
    }
    return id;
  }

  public int update(Block b) throws Sql2oException {
    try (Connection conn = sql2o.beginTransaction()) {
      int id = conn.createQuery(UPDATE).bind(b).executeUpdate().getResult();
      conn.commit();
      return id;
    }
  }

  public List<Block> getAll() throws Sql2oException {
    List<Block> blocks;
    try (Connection conn = sql2o.beginTransaction()) {
      blocks = conn.createQuery(LIST_ALL).executeAndFetch(new BlockDataTransfer());
    }
    return blocks;
  }

  public void remove(int id) throws Sql2oException {
    try (Connection conn = sql2o.beginTransaction()) {
      conn.createQuery(DELETE).addParameter("id", id).executeUpdate();
      conn.commit();
    }
  }

  public List<Block> getBlocksByEntry(Entry e) {
    List<Block> blocks;
    try (Connection conn = sql2o.beginTransaction()) {
      blocks = conn.createQuery(BLOCK_BY_DATE)
          .addParameter("startDate", e.getStart_date()).throwOnMappingFailure(false)
          .executeAndFetch(Block.class);
    }
    return blocks;
  }

    public List<Integer> getBlocksByStartdate(Date e) {
        List<Integer> blocks;
        try (Connection conn = sql2o.beginTransaction()) {
            blocks = conn.createQuery(BLOCK_BY_DATE)
                    .addParameter("startDate", e).throwOnMappingFailure(false)
                    .executeAndFetch(Integer.class);
        }
        return blocks;
    }

}

class BlockDataTransfer implements ResultSetHandler<Block> {

  @Override
  public Block handle(ResultSet rs) throws SQLException {
    Block b = new Block(rs.getString(2), rs.getDate(3).toLocalDate(), rs.getDate(4).toLocalDate());
    b.setId(rs.getInt(1));
    return b;
  }
}