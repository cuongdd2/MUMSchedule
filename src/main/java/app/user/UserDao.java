package app.user;

import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class UserDao {

  private final static String CHECK_LOGIN =
      "SELECT * FROM user WHERE email = :email";


  private Sql2o sql2o;

  public UserDao(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public List<User> list() {
    try (Connection conn = sql2o.beginTransaction()) {
      return conn.createQuery("select * from user")
          .executeAndFetch(User.class);
    }
  }
  public int add(User u) {
    try (Connection conn = sql2o.beginTransaction()) {
      return conn.createQuery("insert into user(email, password, role) values(:email, :password, :role)")
          .bind(u)
          .executeUpdate().getKey(Integer.class);
    }
  }
  public void udpate(User u) {
    try (Connection conn = sql2o.beginTransaction()) {
      conn.createQuery("update user set email = :email, password = :password, role = :role where id = :id")
          .bind(u)
          .executeUpdate().getKey(Integer.class);
    }
  }
  public void remove(int id) throws Exception {
    try (Connection conn = sql2o.beginTransaction()) {
      conn.createQuery("delete from user where id = :id")
          .addParameter("id", id).executeUpdate();
    }
  }

  public User getUserByEmail(String email) {
    try (Connection conn = sql2o.beginTransaction()) {
      return conn.createQuery(CHECK_LOGIN)
          .addParameter("email", email)
          .executeAndFetchFirst(User.class);
    }
  }
}
