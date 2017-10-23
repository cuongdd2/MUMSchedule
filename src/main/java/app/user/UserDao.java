package app.user;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class UserDao {

  private final static String CHECK_LOGIN =
      "SELECT * FROM user WHERE email = :email";


  private Sql2o sql2o;

  public UserDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public User getUserByEmail(String email) {
    try (Connection conn = sql2o.beginTransaction()) {
      return conn.createQuery(CHECK_LOGIN)
          .addParameter("email", email)
          .executeAndFetchFirst(User.class);
    }
  }
}
