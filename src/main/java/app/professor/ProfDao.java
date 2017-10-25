package app.professor;

import app.block.Block;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class ProfDao {
  private final static String INSERT = "INSERT INTO professor(name, title, email, phone, about) "
      + "VALUES (:name, :title, :email,:phone ,:about)";
    private static String SELECT_BY_BLOCK = "select * from professor p inner join prof_block b on p.id = b.prof_id where b.block_id = :blockId";

    private static String SELECT_BY_ID = "select * from professor where id = :Id";

    private static String SELECT_ALL = "select * from professor";

  private final static String UPDATE = "UPDATE professor SET name = :name, title = :title, about=:about, email=:email WHERE id = :id";

  private final static String DELETE = "delete from professor where id=:id";

  private Sql2o sql2o;

  public ProfDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public int createProf(Professor prof) {
    int id;
    try (Connection conn = sql2o.beginTransaction()) {
      id = conn.createQuery(INSERT, true)
            .bind(prof)
            .executeUpdate().getKey(Integer.class);
      conn.commit();
    }
    return id;
  }

  public List<Professor> getProfByBlock(Block b) {
    try (Connection conn = sql2o.open()) {
      return conn.createQuery(SELECT_BY_BLOCK)
          .addParameter("blockId", b.getId())
          .executeAndFetch(Professor.class);
    }
  }

    public Professor getProfById(int id) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(SELECT_BY_ID)
                    .addParameter("Id", id)
                    .executeAndFetchFirst(Professor.class);
        }
    }

    public List<Professor> getAll() {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(SELECT_ALL).executeAndFetch(Professor.class);
        }
    }

  public int update(Professor b) throws Sql2oException {
    try (Connection conn = sql2o.beginTransaction()) {
      int id = conn.createQuery(UPDATE).bind(b).executeUpdate().getResult();
      conn.commit();
      return id;
    }
  }

  public void remove(int id) throws Sql2oException {
    try (Connection conn = sql2o.beginTransaction()) {
      conn.createQuery(DELETE).addParameter("id", id).executeUpdate();
      conn.commit();
    }
  }
}
