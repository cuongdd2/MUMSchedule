package app.professor;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class ProfDao {
  private final static String INSERT = "INSERT INTO professor(name, title, email, phone, about) "
      + "VALUES (:name, :code, :level, :desc)";

  private Sql2o sql2o;

  public ProfDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public int createProf(Professor prof) {
    int id = -1;
    try (Connection conn = sql2o.beginTransaction()) {
      id = (int) conn.createQuery(INSERT, true).bind(prof)
          .executeUpdate().getKey();
      conn.commit();
    }
    return id;
  }


}
