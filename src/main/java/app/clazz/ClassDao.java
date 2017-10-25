package app.clazz;

import org.sql2o.Connection;
import org.sql2o.ResultSetHandler;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static app.Application.*;

public class ClassDao {

    private Sql2o sql2o;

    private final static String SELECT_BY_ID = "SELECT * from class WHERE id = :Id";
    private final static String CLASSES_BY_BLOCK = "SELECT * from class WHERE block_id = :blockId";

    private final static String INSERT = "INSERT INTO class(course_id, prof_id, block_id) VALUES(:cid, :pid, :bid)";
    private final static String INSERTW = "INSERT INTO class(course_id, prof_id, block_id,capacity) VALUES(:cid, :pid, :bid, :caps)";

    private final static String BLOCK_BY_DATE = "SELECT id, name, start_date startDate, end_date enDate FROM class WHERE start_date > :startDate";
    private final static String LIST_ALL = "SELECT * FROM class";
    private final static String DELETE = "DELETE FROM class WHERE id = :id";
    private final static String UPDATE = "UPDATE class SET name = :name, start_date = :startDate, end_date = :endDate WHERE id = :id";
    private final static String UPDATES = "UPDATE class SET course_id = :course, prof_id = :prof, block_id = :block, capacity = :capacity WHERE id = :id";

      public ClassDao(Sql2o sql2o) {
        this.sql2o = sql2o;
      }


      public int create(int cid, int pid, int bid) throws Sql2oException {
        int id;
        try (Connection conn = sql2o.beginTransaction()) {
          id = conn.createQuery(INSERT)
              .addParameter("cid", cid)
              .addParameter("pid", pid)
              .addParameter("bid", bid)
              .executeUpdate().getKey(Integer.class);
          conn.commit();
        }
        return id;
      }

    public int createWithValues(int capacity, int course, int prof, int block) throws Sql2oException {
        int id;
        try (Connection conn = sql2o.beginTransaction()) {
            id = conn.createQuery(INSERTW)
                    .addParameter("cid", course)
                    .addParameter("pid", prof)
                    .addParameter("bid", block)
                    .addParameter("caps",capacity)
                    .executeUpdate().getKey(Integer.class);
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

    public int updateWithId(int classid, int course, int prof, int block, int capacity) throws Sql2oException {
        try (Connection conn = sql2o.beginTransaction()) {
            int id = conn.createQuery(UPDATES)
                    .addParameter("id", classid)
                    .addParameter("course", course)
                    .addParameter("prof", prof)
                    .addParameter("block", block)
                    .addParameter("capacity", capacity)
                    .executeUpdate().getResult();
            conn.commit();
            return id;
        }
    }


  public List<Class> getAll() throws Sql2oException {
    List<Class> classes;
    try (Connection conn = sql2o.beginTransaction()) {
      classes = conn.createQuery(LIST_ALL).executeAndFetch(new ClassDataTransfer());
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

    public Class getClazz(int id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery(SELECT_BY_ID)
                    .addParameter("Id" , id)
                    .executeAndFetchFirst(new ClassDataTransfer());
        }
    }
}

class ClassDataTransfer implements ResultSetHandler<Class> {

    @Override
    public Class handle(ResultSet rs) throws SQLException {

        Class c = new Class(
                courseDao.getCourse(rs.getInt("course_id")),
                profDao.getProfById(rs.getInt("prof_id")),
                blockDao.getBlock(rs.getInt("block_id")));

        c.setId(rs.getInt("id"));
        c.setCapacity(rs.getInt("capacity"));
        c.setEnrolled(rs.getInt("enrolled"));

        return c;
    }
}