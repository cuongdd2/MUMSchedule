package app.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class User {
  private int id;
  final private String email;
  final private String password;
  final private int role;

  public boolean isAdmin() {
    return (role / 100) == 1;
  }

  public boolean isFaculty() {
    return (role / 10) % 10 == 1;
  }

  public boolean isStudent() {
    return role % 10 == 1;
  }
}
