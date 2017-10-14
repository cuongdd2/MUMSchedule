package app.user;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class UserDao {

  private final static String CHECK_LOGIN =
      "SELECT COUNT(*) FROM user WHERE email = :email AND password = :pw";


  private Sql2o sql2o;

  public UserDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public boolean checkLogin(User user) {
    try (Connection conn = sql2o.beginTransaction()) {
      User u = conn.createQuery(CHECK_LOGIN)
          .addParameter("email", user.getEmail())
          .addParameter("pw", user.getPassword())
          .executeAndFetchFirst(User.class);
      return u != null;
    }
  }
}
