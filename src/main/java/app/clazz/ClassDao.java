package app.clazz;

import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class ClassDao {

    private Sql2o sql2o;
    private final static String CLASSES_BY_BLOCK = "SELECT * from class WHERE block_id = :blockId";
  private final static String INSERT = "INSERT INTO class(course_id, prof_id, block_id) VALUES(:cid, :pid, :bid)";
  private final static String BLOCK_BY_DATE = "SELECT id, name, start_date startDate, end_date enDate FROM class WHERE start_date > :startDate";
  private final static String LIST_ALL = "SELECT id, name, start_date startDate, end_date endDate FROM class";
  private final static String DELETE = "DELETE FROM class WHERE id = :id";
  private final static String UPDATE = "UPDATE class SET name = :name, start_date = :startDate, end_date = :endDate WHERE id = :id";

  public ClassDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }


  public int create(Class b) throws Sql2oException {
    int id;
    try (Connection conn = sql2o.beginTransaction()) {
      id = conn.createQuery(INSERT)
          .bind(b).executeUpdate().getKey(Integer.class);
      conn.commit();
    }
    return id;
  }

  public int update(Class b) throws Sql2oException {
    try (Connection conn = sql2o.beginTransaction()) {
      int id = conn.createQuery(UPDATE).bind(b).executeUpdate().getResult();
      conn.commit();
      return id;
    }
  }

  public List<Class> getAll() throws Sql2oException {
    List<Class> classes;
    try (Connection conn = sql2o.beginTransaction()) {
      classes = conn.createQuery(LIST_ALL).executeAndFetch(Class.class);
    }
    return classes;
  }

  public void remove(int id) throws Sql2oException {
    try (Connection conn = sql2o.beginTransaction()) {
      conn.createQuery(DELETE).addParameter("id", id).executeUpdate();
      conn.commit();
    }
  }


    public List<Class> getClassByBlock(int blockid) throws Exception {
        List<Class> classes;

        try (Connection conn = sql2o.beginTransaction()) {

            classes = conn.createQuery(CLASSES_BY_BLOCK)
                    .addParameter("blockId", blockid)
                    .executeAndFetch(Class.class);
        }
        return classes;

    }
}
